/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/*
 * Creates a schema for storing Java objects according to RFC 2713
 * After running this program, you should verify that the schema 
 * has been updated correctly by using the directory server's 
 * administration tool. If the schema has not been properly updated, 
 * use the administration tool to correct it.
 *
 * You should first turn off schema-checking at the directory server 
 * before running this program.
 *
 * usage:
 * java [-Djava.naming.provider.url=<ldap_server_url>] \
 *     CreateJavaSchema [-h|-l|-s[n|n41|ad]] [-n<dn>] [-p<passwd>] [-a<auth>] 
 *      
 * -h		Print the usage message
 * 
 * -l		List the Java schema in the directory
 * 
 * -s[n|n41|ad]	Update schema:
 *                -sn   means use a workaround for schema bugs in
 *                      pre-4.1 releases of Netscape Directory Server;
 * 
 *		  -sn41 means use a workaround for schema bugs in
 *                      Netscape Directory Server version 4.1;
 *
 *		  -sad  means use a workaround for schema bugs in
 *                      Microsoft Windows 2000 Active Directory
 *
 * -n<dn> 	Use <dn> as the distinguished name for authentication
 * 
 * -p<passwd>	Use <passwd> as the password for authentication
 * 
 * -a<auth>	Use <auth> as the authentication mechanism. Default is "simple".
 * 
 *
 * If neither -s, -l, nor -h has been specified, the default is "-l".
 *
 * The following example inserts the Java schema from RFC 2713 in a
 * Netscape Directory (using the workaround for 4.1 schema bugs),
 * logging in as "cn=directory manager" with the password "secret":
 * 
 *     java CreateJavaSchema -sn41 "-ncn=directory manager" -psecret
 *
 * @author Rosanna Lee
 */

import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;

public class CreateJavaSchema {

    protected static String dn, passwd, auth;
    protected static boolean netscapebug;

    // NS 4.1 has problems parsing an object class definition which contains
    // a MUST clause without parentheses. The workaround is to add a
    // superfluous value (objectClass) to each MUST clause.
    // 
    // It also doesn't like the Octet String syntax (use Binary instead)
    //
    protected static boolean netscape41bug = false;

    // AD supports auxiliary classes in a peculiar way.
    protected static boolean activeDirectorySchemaBug = false;

    protected static boolean traceLdap = false;
    protected static final int LIST = 0;
    protected static final int UPDATE = 1;

    private static String[] allAttrs = {
	"javaSerializedObject",
	"javaFactoryLocation",
	"javaReferenceAddress",
	"javaFactory",
	"javaClassName",
	"javaClassNames",
	"javaDoc",
	"javaSerializedData",
	"javaCodebase",
	"javaFactory",
	"javaReferenceAddress"};

    private static String[] allOCs = {
	"javaObject",
	"javaNamingReference",
	"javaSerializedObject",
	"javaRemoteObject",
	"javaMarshalledObject",
	"javaContainer"};

    public static void main(String[] args) {
	new CreateJavaSchema().run(args, allAttrs, allOCs);
    }

    CreateJavaSchema () {
    }

    protected void run(String[] args, String[] attrIDs, String[] ocIDs) {
	int cmd = processCommandLine(args);
	try {
	    DirContext ctx = signOn();
	    switch (cmd) {
	    case UPDATE:
		updateSchema(ctx, attrIDs, ocIDs);
		break;
	    default:
		showSchema(ctx, attrIDs, ocIDs);
	    }
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Signs on to directory server using parameters supplied to program.
     * @return The initial context to the server.
     */
    private DirContext signOn() throws NamingException {
	if (dn != null && auth == null) {
	    auth = "simple"; 	// use simple for Netscape
	}

	Hashtable env = new Hashtable();
	env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");

	env.put(Context.REFERRAL, "follow");

	if (auth != null) {
	    env.put(Context.SECURITY_AUTHENTICATION, auth);
	    env.put(Context.SECURITY_PRINCIPAL, dn);
	    env.put(Context.SECURITY_CREDENTIALS, passwd);
	}

	// Workaround for Netscape schema bugs
	if (netscapebug) {
	    env.put("com.sun.naming.netscape.schemaBugs", "true");
	}

	// LDAP protocol tracing
	if (traceLdap) {
	    env.put("com.sun.jndi.ldap.trace.ber", System.err);
	}

	return new InitialDirContext(env);
    }

    void showSchema(DirContext ctx, String[] attrs, String[] ocs) 
	throws NamingException {
	DirContext attrRoot = 
	    (DirContext)ctx.getSchema("").lookup("AttributeDefinition");
	printSchema(attrRoot, attrs);
    
	DirContext ocRoot = 
	    (DirContext)ctx.getSchema("").lookup("ClassDefinition");
	printSchema(ocRoot, ocs);
    }

    private void printSchema(DirContext ctx, String[] ids) {
	for (int i = 0; i < ids.length; i++) {
	    try {
		System.out.print(ids[i] + ": ");
		System.out.print(ctx.getAttributes(ids[i]));
	    } catch (NamingException e) {
	    } finally {
		System.out.println();
	    }
	}
    }


    /**
     * Updates the schema:
     *
     * Delete obsolete attributes:
     * 	javaSerializedObject
     * 	javaFactoryLocation
     * 	javaReferenceAddress
     * 	javaFactory
     * 	javaClassName
     *  + all the new ones that we're going to add
     * Add new and updated attributes:
     * 	javaSerializedData
     * 	javaCodebase
     * 	javaClassName
     * 	javaClassNames
     *  javaFactory
     *  javaReferenceAddress
     *  javaDoc
     *
     * Delete obsolete object classes:
     *  javaNamingReference
     *  javaObject
     *  + all the new ones that we're going to add
     * Add new and updated object classes:
     *  javaObject
     *  javaSerializedObject
     *  javaMarshalledObject
     *  javaNamingReference
     */
    private void updateSchema(DirContext ctx, 
	String[] attrIDs, String[] ocIDs) throws NamingException {

	if (activeDirectorySchemaBug) {
	    updateADSchema(ctx);

	} else {
	    updateAttributes((DirContext)
		ctx.getSchema("").lookup("AttributeDefinition"), attrIDs);

	    updateObjectClasses((DirContext)
		ctx.getSchema("").lookup("ClassDefinition"), ocIDs);
	}

	System.out.println(
"Please use your directory server's administration tool to verify");
	System.out.println(
"the correctness of the schema.");
    }


    /* Add new and updated attr definitions */
    protected void updateAttributes(DirContext attrRoot, String[] attrIDs) 
	throws NamingException {

	/* Get rid of old attr IDs */
        for (int i = 0; i < attrIDs.length; i++) {
	    attrRoot.destroySubcontext(attrIDs[i]);
	}

// javaSerializedData
	Attributes attrs = new BasicAttributes(true); // ignore case
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.8");
	attrs.put("NAME", "javaSerializedData");
	attrs.put("DESC", "Serialized form of a Java object");
	if (netscape41bug) {
	    // DS 4.1 doesn't like Octet String
	    attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.5");
	} else {
	    attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.40");
	}

	attrs.put("SINGLE-VALUE", "true");
	attrRoot.createSubcontext("javaSerializedData", attrs);
	System.out.println("Created javaSerializedData attribute");

// javaCodebase
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.7");
	attrs.put("NAME", "javaCodebase");
	attrs.put("DESC", "URL(s) specifying the location of class definition");
	attrs.put("EQUALITY", "caseExactIA5Match");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
	attrRoot.createSubcontext("javaCodebase", attrs);
	System.out.println("Created javaCodebase attribute");

// javaClassName
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.6");
	attrs.put("NAME", "javaClassName");
	attrs.put("DESC", "Fully qualified name of distinguished class or interface");
	attrs.put("EQUALITY", "caseExactMatch");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15");
	attrs.put("SINGLE-VALUE", "true");
	attrRoot.createSubcontext("javaClassName", attrs);
	System.out.println("Created javaClassName attribute");

// javaClassNames
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.13");
	attrs.put("NAME", "javaClassNames");
	attrs.put("DESC", "Fully qualified Java class or interface name");
	attrs.put("EQUALITY", "caseExactMatch");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15");
	attrRoot.createSubcontext("javaClassNames", attrs);
	System.out.println("Created javaClassNames attribute");

// javaFactory
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.10");
	attrs.put("NAME", "javaFactory");
	attrs.put("DESC", "Fully qualified Java class name of a JNDI object factory");
	attrs.put("EQUALITY", "caseExactMatch");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15");
	attrs.put("SINGLE-VALUE", "true");
	attrRoot.createSubcontext("javaFactory", attrs);
	System.out.println("Created javaFactory attribute");

// javaReferenceAddress
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.11");
	attrs.put("NAME", "javaReferenceAddress");
	attrs.put("DESC", "Addresses associated with a JNDI Reference");
	attrs.put("EQUALITY", "caseExactMatch");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15");
	attrRoot.createSubcontext("javaReferenceAddress", attrs);
	System.out.println("Created javaReferenceAddress attribute");

// javaDoc
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.12");
	attrs.put("NAME", "javaDoc");
	attrs.put("DESC", "The Java documentation for the class");
	attrs.put("EQUALITY", "caseExactIA5Match");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
	attrRoot.createSubcontext("javaDoc", attrs);
	System.out.println("Created javaDoc attribute");
    }

    // Object Classes
    protected void updateObjectClasses(DirContext ocRoot, String[] ocIDs)
	throws NamingException {
	/* Get rid of old OCs - reverse order */
	for (int i = ocIDs.length - 1; i >= 0; i--) {
	    ocRoot.destroySubcontext(ocIDs[i]);
	}

// javaContainer
	Attributes attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.1");
	attrs.put("NAME", "javaContainer");
	attrs.put("DESC", "Container for a Java object");
	attrs.put("SUP", "top");
	attrs.put("STRUCTURAL", "true");
	Attribute jcMust = new BasicAttribute("MUST", "cn");

	if (netscape41bug) {
	    jcMust.add("objectClass");
	}
	attrs.put(jcMust);

	ocRoot.createSubcontext("javaContainer", attrs);
	System.out.println("Created javaContainer object class");

// javaObject
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.4");
	attrs.put("NAME", "javaObject");
	attrs.put("DESC", "Java object representation");
	attrs.put("SUP", "top");
	attrs.put("ABSTRACT", "true");
	Attribute joMust = new BasicAttribute("MUST", "javaClassName");

	if (netscape41bug) {
	    joMust.add("objectClass");
	}
	attrs.put(joMust);

	Attribute optional = new BasicAttribute("MAY", "javaCodebase");
	optional.add("javaClassNames");
	optional.add("javaDoc");
	optional.add("description");
	attrs.put(optional);
	ocRoot.createSubcontext("javaObject", attrs);
	System.out.println("Created javaObject object class");

// javaSerializedObject
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.5");
	attrs.put("NAME",  "javaSerializedObject");
	attrs.put("DESC", "Java serialized object");
	attrs.put("SUP", "javaObject");
	attrs.put("AUXILIARY", "true");
	Attribute jsoMust = new BasicAttribute("MUST", "javaSerializedData");

	if (netscape41bug) {
	    jsoMust.add("objectClass");
	}

	if (netscapebug) {
	    // Netscape ignores 'SUP' so we must add explicitly
	    attrs.put(optional);
	    jsoMust.add("javaClassName");
	}
	attrs.put(jsoMust);
	ocRoot.createSubcontext("javaSerializedObject", attrs);
	System.out.println("Created javaSerializedObject object class");

// javaMarshalledObject
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.8");
	attrs.put("NAME",  "javaMarshalledObject");
	attrs.put("DESC", "Java marshalled object");
	attrs.put("SUP", "javaObject");
	attrs.put("AUXILIARY", "true");

	if (netscapebug) {
	    // Netscape ignores 'SUP' so we must add explicitly
	    attrs.put(optional);
	}
	attrs.put(jsoMust);  // re-use the MUST from javaSerializedObject
	ocRoot.createSubcontext("javaMarshalledObject", attrs);
	System.out.println("Created javaMarshalledObject object class");

// javaNamingReference
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.7");
	attrs.put("NAME", "javaNamingReference");
	attrs.put("DESC", "JNDI reference");
	attrs.put("SUP", "javaObject");
	attrs.put("AUXILIARY", "true");

	if (netscapebug) {
	    // Netscape ignores 'SUP' so we must add explicitly
	    attrs.put("MUST",  "javaClassName" );
	} else {
	    optional = new BasicAttribute("MAY");
	}

	optional.add("javaReferenceAddress");
	optional.add("javaFactory");
	attrs.put(optional);
	ocRoot.createSubcontext("javaNamingReference", attrs);
	System.out.println("Created javaNamingReference object class");
    }

    /**
     * Updates the Active Directory schema.
     *
     * Modification of the (RFC 2252) schema descriptions is not supported
     * in Active Directory. Instead, the Active Directory (internal) schema
     * must be modified.
     */
    private void updateADSchema(DirContext rootCtx) throws NamingException {

System.out.println("[updating Active Directory schema ...]");

	// acquire schema context
	DirContext schemaCtx = getADSchema(rootCtx);

	// insert attribute definitions
	insertADAttributes(rootCtx, schemaCtx);

	// insert object class definitions
	insertADObjectClasses(rootCtx, schemaCtx);

System.out.println("[update completed]\n");
    }

    /**
     * Locates the Active Directory schema.
     * @return A context for the root of the Active Directory schema.
     */
    private DirContext getADSchema(DirContext rootCtx) throws NamingException {

System.out.println("  [locating the schema]");
	String snc = "schemaNamingContext"; // DSE attribute
	Attributes attrs =
	    rootCtx.getAttributes("", new String[]{ snc });
	return (DirContext) rootCtx.lookup((String) attrs.get(snc).get());
    }

    /**
     * Inserts attribute definitions from RFC 2713 into the schema.
     *
     * This method maps the LDAP schema definitions in RFC 2713 onto the
     * proprietary attributes required by the Active Directory schema.
     *
     * The resulting attribute definitions are identical to those of RFC 2713.
     */
    protected void insertADAttributes(DirContext rootCtx, DirContext schemaCtx)
	throws NamingException {

System.out.println("  [inserting new attribute definitions ...]");

	String dn = schemaCtx.getNameInNamespace();
	String attrID;


	attrID = new String("javaClassName");
	Attributes attrs1 = new BasicAttributes();

	attrs1.put(new BasicAttribute("adminDescription", attrID));
	attrs1.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.6"));
	attrs1.put(new BasicAttribute("attributeSyntax", "2.5.5.12"));
	attrs1.put(new BasicAttribute("cn", attrID));
	attrs1.put(new BasicAttribute("description",
	    "Fully qualified name of distinguished Java class or interface"));
	attrs1.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs1.put(new BasicAttribute("isSingleValued", "TRUE"));
	attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs1.put(new BasicAttribute("name", attrID));
	attrs1.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs1.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs1.put(new BasicAttribute("oMSyntax", "64"));
	attrs1.put(new BasicAttribute("searchFlags", "0"));
	attrs1.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs1);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaCodeBase");
	Attributes attrs2 = new BasicAttributes();

	attrs2.put(new BasicAttribute("adminDescription", attrID));
	attrs2.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.7"));
	attrs2.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
	attrs2.put(new BasicAttribute("cn", attrID));
	attrs2.put(new BasicAttribute("description",
	    "URL(s) specifying the location of class definition"));
	attrs2.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs2.put(new BasicAttribute("isSingleValued", "FALSE"));
	attrs2.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs2.put(new BasicAttribute("name", attrID));
	attrs2.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs2.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs2.put(new BasicAttribute("oMSyntax", "22"));
	attrs2.put(new BasicAttribute("searchFlags", "0"));
	attrs2.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs2);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaSerializedData");
	Attributes attrs3 = new BasicAttributes();

	attrs3.put(new BasicAttribute("adminDescription", attrID));
	attrs3.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.8"));
	attrs3.put(new BasicAttribute("attributeSyntax", "2.5.5.10"));
	attrs3.put(new BasicAttribute("cn", attrID));
	attrs3.put(new BasicAttribute("description",
	    "Serialized form of a Java object"));
	attrs3.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs3.put(new BasicAttribute("isSingleValued", "TRUE"));
	attrs3.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs3.put(new BasicAttribute("name", attrID));
	attrs3.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs3.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs3.put(new BasicAttribute("oMSyntax", "4"));
	attrs3.put(new BasicAttribute("searchFlags", "0"));
	attrs3.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs3);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaFactory");
	Attributes attrs4 = new BasicAttributes();

	attrs4.put(new BasicAttribute("adminDescription", attrID));
	attrs4.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.10"));
	attrs4.put(new BasicAttribute("attributeSyntax", "2.5.5.12"));
	attrs4.put(new BasicAttribute("cn", attrID));
	attrs4.put(new BasicAttribute("description",
	    "Fully qualified Java class name of a JNDI object factory"));
	attrs4.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs4.put(new BasicAttribute("isSingleValued", "TRUE"));
	attrs4.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs4.put(new BasicAttribute("name", attrID));
	attrs4.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs4.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs4.put(new BasicAttribute("oMSyntax", "64"));
	attrs4.put(new BasicAttribute("searchFlags", "0"));
	attrs4.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs4);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaReferenceAddress");
	Attributes attrs5 = new BasicAttributes();

	attrs5.put(new BasicAttribute("adminDescription", attrID));
	attrs5.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.11"));
	attrs5.put(new BasicAttribute("attributeSyntax", "2.5.5.12"));
	attrs5.put(new BasicAttribute("cn", attrID));
	attrs5.put(new BasicAttribute("description",
	    "Addresses associated with a JNDI Reference"));
	attrs5.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs5.put(new BasicAttribute("isSingleValued", "FALSE"));
	attrs5.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs5.put(new BasicAttribute("name", attrID));
	attrs5.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs5.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs5.put(new BasicAttribute("oMSyntax", "64"));
	attrs5.put(new BasicAttribute("searchFlags", "0"));
	attrs5.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs5);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaDoc");
	Attributes attrs6 = new BasicAttributes();

	attrs6.put(new BasicAttribute("adminDescription", attrID));
	attrs6.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.12"));
	attrs6.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
	attrs6.put(new BasicAttribute("cn", attrID));
	attrs6.put(new BasicAttribute("description",
	    "The Java documentation for the class"));
	attrs6.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs6.put(new BasicAttribute("isSingleValued", "FALSE"));
	attrs6.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs6.put(new BasicAttribute("name", attrID));
	attrs6.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs6.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs6.put(new BasicAttribute("oMSyntax", "22"));
	attrs6.put(new BasicAttribute("searchFlags", "0"));
	attrs6.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs6);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaClassNames");
	Attributes attrs7 = new BasicAttributes();

	attrs7.put(new BasicAttribute("adminDescription", attrID));
	attrs7.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.13"));
	attrs7.put(new BasicAttribute("attributeSyntax", "2.5.5.12"));
	attrs7.put(new BasicAttribute("cn", attrID));
	attrs7.put(new BasicAttribute("description",
	    "Fully qualified Java class or interface name"));
	attrs7.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs7.put(new BasicAttribute("isSingleValued", "FALSE"));
	attrs7.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs7.put(new BasicAttribute("name", attrID));
	attrs7.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs7.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs7.put(new BasicAttribute("oMSyntax", "64"));
	attrs7.put(new BasicAttribute("searchFlags", "0"));
	attrs7.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs7);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // finally
    }

    /**
     * Inserts object class definitions from RFC 2713 into the schema.
     *
     * This method maps the LDAP schema definitions in RFC 2713 onto the
     * proprietary attributes required by the Active Directory schema.
     *
     * The resulting object class definitions differ from those of RFC 2713
     * in the following ways:
     *
     *     - Abstract and auxiliary classes are now defined as structural.
     *     - The javaObject class now inherits from javaContainer.
     *     - The javaNamingReference, javaSerializedObject and
     *       javaMarshalledObject now inherit from javaObject.
     *
     * The effect of these differences is that Java objects cannot be
     * mixed-in with other directory entries, they may only be stored as
     * stand-alone entries.
     *
     * The reason for these differences is due to the way auxiliary classes
     * are supported the Active Directory. Only the names of structural
     * classes (not auxiliary) may appear in the object class attribute of
     * an entry. Therefore, the abstract and auxiliary classes in the Java
     * schema definition are re-defined as structural.
     */
    protected void insertADObjectClasses(DirContext rootCtx,
	DirContext schemaCtx) throws NamingException {

System.out.println("  [inserting new object class definitions ...]");

	String dn = schemaCtx.getNameInNamespace();
	String attrID;


	attrID = new String("javaContainer");
	Attributes attrs1 = new BasicAttributes();

	attrs1.put(new BasicAttribute("objectClass", "classSchema"));
	attrs1.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs1.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.1"));
	attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs1.put(new BasicAttribute("mustContain", "cn"));
	attrs1.put(new BasicAttribute("objectClassCategory", "1"));
	attrs1.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs1.put(new BasicAttribute("subclassOf", "top"));
	attrs1.put(new BasicAttribute("possSuperiors", "top")); //any superior
	attrs1.put(new BasicAttribute("description",
	    "Container for a Java object"));

	schemaCtx.createSubcontext("CN=" + attrID, attrs1);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // because javaObject relys on javaContainer


	attrID = new String("javaObject");
	Attributes attrs2 = new BasicAttributes();

	attrs2.put(new BasicAttribute("objectClass", "classSchema"));
	attrs2.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs2.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.4"));
	attrs2.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs2.put(new BasicAttribute("mustContain", "javaClassName"));

	Attribute joMay = new BasicAttribute("mayContain");
	joMay.add("javaClassNames");
	joMay.add("javaCodeBase");
	joMay.add("javaDoc");
	joMay.add("description");
	attrs2.put(joMay);

	attrs2.put(new BasicAttribute("objectClassCategory", "1"));
	attrs2.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs2.put(new BasicAttribute("subclassOf", "javaContainer"));
	attrs2.put(new BasicAttribute("description",
	    "Java object representation"));

	schemaCtx.createSubcontext("CN=" + attrID, attrs2);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // because next 3 rely on javaObject


	attrID = new String("javaSerializedObject");
	Attributes attrs3 = new BasicAttributes();

	attrs3.put(new BasicAttribute("objectClass", "classSchema"));
	attrs3.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs3.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.5"));
	attrs3.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs3.put(new BasicAttribute("mustContain", "javaSerializedData"));
	attrs3.put(new BasicAttribute("objectClassCategory", "1"));
	attrs3.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs3.put(new BasicAttribute("subclassOf", "javaObject"));
	attrs3.put(new BasicAttribute("description", "Java serialized object"));

	schemaCtx.createSubcontext("CN=" + attrID, attrs3);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaNamingReference");
	Attributes attrs4 = new BasicAttributes();

	attrs4.put(new BasicAttribute("objectClass", "classSchema"));
	attrs4.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs4.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.7"));
	attrs4.put(new BasicAttribute("lDAPDisplayName", attrID));

	Attribute jnrMay = new BasicAttribute("mayContain");
	jnrMay.add("javaReferenceAddress");
	jnrMay.add("javaFactory");
	attrs4.put(jnrMay);

	attrs4.put(new BasicAttribute("objectClassCategory", "1"));
	attrs4.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs4.put(new BasicAttribute("subclassOf", "javaObject"));
	attrs4.put(new BasicAttribute("description", "JNDI reference"));

	schemaCtx.createSubcontext("CN=" + attrID, attrs4);
System.out.println("    [" + attrID + "]");


	attrID = new String("javaMarshalledObject");
	Attributes attrs5 = new BasicAttributes();

	attrs5.put(new BasicAttribute("objectClass", "classSchema"));
	attrs5.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs5.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.8"));
	attrs5.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs5.put(new BasicAttribute("mustContain", "javaSerializedData"));
	attrs5.put(new BasicAttribute("objectClassCategory", "1"));
	attrs5.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs5.put(new BasicAttribute("subclassOf", "javaObject"));
	attrs5.put(new BasicAttribute("description", "Java marshalled object"));

	schemaCtx.createSubcontext("CN=" + attrID, attrs5);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // finally
    }

    /**
     * Writes schema modifications to the Active Directory schema immediately.
     */
    protected void flushADSchemaMods(DirContext rootCtx)
	throws NamingException {

	rootCtx.modifyAttributes("", new ModificationItem[] {
	    new ModificationItem(DirContext.ADD_ATTRIBUTE,
		new BasicAttribute("schemaUpdateNow", "1")) });
    }

    private int processCommandLine(String[] args) {
	String option;
	boolean schema = false;
	boolean list = false;

	for (int i = 0; i < args.length; i++) {
	    option = args[i];
	    if (option.startsWith("-h")) {
		printUsage(null);
	    }
	    if (option.startsWith("-s")) {
		schema = true;
		netscapebug = option.equals("-sn");
		netscape41bug = option.equals("-sn41");
		activeDirectorySchemaBug = option.equals("-sad");
	    } else if (option.startsWith("-l")) {
		list = true;
	    } else if (option.startsWith("-a")) {
		auth = option.substring(2);
	    } else if (option.startsWith("-n")) {
		dn = option.substring(2);
	    } else if (option.startsWith("-p")) {
		passwd = option.substring(2);
	    } else if (option.startsWith("-trace")) {
		traceLdap = true;
	    } else {
		// invalid option
		printUsage("Invalid option");
	    }
	}

	if (!schema) {
	    return LIST;
	} else {
	    return UPDATE;
	}
    }


    protected void printUsage(String msg) {
	printUsageAux(msg, "Java");
    }

    protected void printUsageAux(String msg, String key) {
	if (msg != null) {
	    System.out.println(msg);
	}

System.out.print("Usage: ");
System.out.println("java [-Djava.naming.provider.url=<ldap_server_url>] \\");
System.out.println("  Create" + key + "Schema [-h|-l|-s[n|n41|ad]] [-n<dn>] [-p<passwd>] [-a<auth>]");
System.out.println();
System.out.println("  -h\t\tPrint the usage message");
System.out.println("  -l\t\tList the " + key + " schema in the directory");
System.out.println("  -s[n|n41|ad]\tUpdate schema:");
System.out.println(
    "\t\t -sn   use workaround for Netscape Directory pre-4.1 schema bug");
System.out.println(
    "\t\t -sn41 use workaround for Netscape Directory 4.1 schema bug");
System.out.println(
    "\t\t -sad  use workaround for Active Directory schema bug");
System.out.println("  -n<dn>\tUse <dn> as the distinguished name for authentication");
System.out.println("  -p<passwd>\tUse <passwd> as the password for authentication");
System.out.println("  -a<auth>\tUse <auth> as the authentication mechanism");
System.out.println("\t\t Default is 'simple' if dn specified; otherwise 'none'");
	System.exit(-1);
    }
}
