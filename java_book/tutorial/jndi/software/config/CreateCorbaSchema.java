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
 * Creates the schema for storing CORBA object references according
 * to RFC 2714. After running this program, you should verify that
 * the schema has been updated correctly by using the directory server's
 * administration tool. If the schema has not been properly updated,
 * use the administration tool to correct it.
 *
 * You should first turn off schema-checking at the directory server 
 * before running this program.
 *
 * usage:
 * java [-Djava.naming.provider.url=<ldap_server_url>] \
 *     CreateCorbaSchema [-h|-l|-s[n|n41|ad]] [-n<dn>] [-p<passwd>] [-a<auth>] 
 *      
 * -h		Print the usage message
 *
 * -l		List the CORBA schema in the directory
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
 * The following example inserts the CORBA schema from RFC 2714 in a
 * Netscape Directory (using the workaround for 4.1 schema bugs),
 * logging in as "cn=directory manager" with the password "secret".
 *
 *     java CreateCorbaSchema -sn41 "-ncn=directory manager" -psecret
 *
 * @author Rosanna Lee
 */

import javax.naming.*;
import javax.naming.directory.*;

public class CreateCorbaSchema extends CreateJavaSchema{

    private static String[] allAttrs = {
	"corbaIor",
	"corbaRepositoryId"
    };

    private static String[] allOCs = {
	"corbaObject",
	"corbaObjectReference",
	"corbaContainer"
    };

    public static void main(String[] args) {
	new CreateCorbaSchema().run(args, allAttrs, allOCs);
    }

    CreateCorbaSchema() {
    }

    /**
     * Add new attributes:
     * 	corbaIor
     * 	corbaRepositoryId
     */
    protected void updateAttributes(DirContext attrRoot, String[] attrIDs) 
	throws NamingException {

	/* Get rid of old attr IDs */
	for (int i = 0; i < attrIDs.length; i++) {
	    attrRoot.destroySubcontext(attrIDs[i]);
	}

	/* Add new and updated attr definitions */
// corbaIor
	Attributes attrs = new BasicAttributes(true); // ignore case
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.14");
	attrs.put("NAME", "corbaIor");
	attrs.put("DESC", "Stringified interoperable object reference of a CORBA object");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
	attrs.put("EQUALITY", "caseIgnoreIA5Match");
	attrs.put("SINGLE-VALUE", "true");
	attrRoot.createSubcontext("corbaIor", attrs);
	System.out.println("Created corbaIor attribute");

// corbaRepositoryId
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.1.15");
	attrs.put("NAME", "corbaRepositoryId");
	attrs.put("DESC", "Repository ids of interfaces implemented by a CORBA object");
	attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.15");
	attrs.put("EQUALITY", "caseExactMatch");
	attrRoot.createSubcontext("corbaRepositoryId", attrs);
	System.out.println("Created corbaRepositoryId attribute");
    }

    // Object Classes
    protected void updateObjectClasses(DirContext ocRoot, String[] ocIDs) 
	throws NamingException {

	/* Get rid of old OCs - reverse order */
	for (int i = ocIDs.length - 1; i >= 0; i--) {    
	    ocRoot.destroySubcontext(ocIDs[i]);
	}

// corbaObject
	Attributes attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.9");
	attrs.put("NAME", "corbaObject");
	attrs.put("DESC", "CORBA object representation");
	attrs.put("SUP", "top");
	attrs.put("ABSTRACT", "true");
	Attribute optional = new BasicAttribute("MAY", "corbaRepositoryId");
	optional.add("description");
	attrs.put(optional);
	ocRoot.createSubcontext("corbaObject", attrs);
	System.out.println("Created corbaObject object class");

// corbaObjectReference
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.11");
	attrs.put("NAME", "corbaObjectReference");
	attrs.put("DESC", "CORBA interoperable object reference");
	attrs.put("SUP", "corbaObject");
	attrs.put("AUXILIARY", "true");
	Attribute corMust = new BasicAttribute("MUST", "corbaIor");

	if (netscape41bug) {
	    corMust.add("objectclass");
	}

	if (netscapebug) {
	    // Netscape ignores 'SUP' so we must add explicitly
	    attrs.put(optional);
	}
	attrs.put(corMust);
	ocRoot.createSubcontext("corbaObjectReference", attrs);
	System.out.println("Created corbaObjectReference object class");

// corbaContainer
	attrs = new BasicAttributes(true);
	attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.4.2.10");
	attrs.put("NAME", "corbaContainer");
	attrs.put("DESC", "Container for a CORBA object");
	attrs.put("SUP", "top");
	attrs.put("STRUCTURAL", "true");
	Attribute ccMust = new BasicAttribute("MUST", "cn");

	if (netscape41bug) {
	    ccMust.add("objectclass");
	}

	attrs.put(ccMust);
	ocRoot.createSubcontext("corbaContainer", attrs);
	System.out.println("Created corbaContainer object class");
    }

    /**
     * Inserts attribute definitions from RFC 2714 into the schema.
     *
     * This method maps the LDAP schema definitions in RFC 2714 onto the
     * proprietary attributes required by the Active Directory schema.
     *
     * The resulting attribute definitions are identical to those of RFC 2714.
     */
    protected void insertADAttributes(DirContext rootCtx, DirContext schemaCtx)
	throws NamingException {

System.out.println("  [inserting new attribute definitions ...]");

	String dn = schemaCtx.getNameInNamespace();
	String attrID;


	attrID = new String("corbaIor");
	Attributes attrs1 = new BasicAttributes();

	attrs1.put(new BasicAttribute("adminDescription", attrID));
	attrs1.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.14"));
	attrs1.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
	attrs1.put(new BasicAttribute("cn", attrID));
	attrs1.put(new BasicAttribute("description",
	    "Stringified interoperable object reference of a CORBA object"));
	attrs1.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs1.put(new BasicAttribute("isSingleValued", "TRUE"));
	attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs1.put(new BasicAttribute("name", attrID));
	attrs1.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs1.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs1.put(new BasicAttribute("oMSyntax", "22"));
	attrs1.put(new BasicAttribute("searchFlags", "0"));
	attrs1.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs1);
System.out.println("    [" + attrID + "]");


	attrID = new String("corbaRepositoryId");
	Attributes attrs2 = new BasicAttributes();

	attrs2.put(new BasicAttribute("adminDescription", attrID));
	attrs2.put(new BasicAttribute("attributeID",
	    "1.3.6.1.4.1.42.2.27.4.1.15"));
	attrs2.put(new BasicAttribute("attributeSyntax", "2.5.5.12"));
	attrs2.put(new BasicAttribute("cn", attrID));
	attrs2.put(new BasicAttribute("description",
	    "Repository ids of interfaces implemented by a CORBA object"));
	attrs2.put(new BasicAttribute("distinguishedName", "CN=" + attrID +
	    "," + dn));
	attrs2.put(new BasicAttribute("isSingleValued", "FALSE"));
	attrs2.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs2.put(new BasicAttribute("name", attrID));
	attrs2.put(new BasicAttribute("objectCategory", "CN=Attribute-Schema," +
	    dn));
	attrs2.put(new BasicAttribute("objectClass", "attributeSchema"));
	attrs2.put(new BasicAttribute("oMSyntax", "64"));
	attrs2.put(new BasicAttribute("searchFlags", "0"));
	attrs2.put(new BasicAttribute("systemOnly", "FALSE"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs2);
System.out.println("    [" + attrID + "]");


        flushADSchemaMods(rootCtx); // finally
    }


    /**
     * Inserts object class definitions from RFC 2714 into the schema.
     *
     * This method maps the LDAP schema definitions in RFC 2714 onto the
     * proprietary attributes required by the Active Directory schema.
     *
     * The resulting object class definitions differ from those of RFC 2714
     * in the following ways:
     *
     *     - Abstract and auxiliary classes are now defined as structural.
     *     - The corbaObject class now inherits from corbaContainer.
     *     - The corbaObjectReference class now inherits from corbaObject.
     *
     * The effect of these differences is that CORBA object references
     * cannot be mixed-in with other directory entries, they may only be
     * stored as stand-alone entries.
     *
     * The reason for these differences is due to the way auxiliary classes
     * are supported in Active Directory. Only the names of structural
     * classes (not auxiliary) may appear in the object class attribute of
     * an entry. Therefore, the abstract and auxiliary classes in the CORBA
     * schema definition is re-defined as structural.
     */
    protected void insertADObjectClasses(DirContext rootCtx,
	DirContext schemaCtx) throws NamingException {

System.out.println("  [inserting new object class definitions ...]");

	String dn = schemaCtx.getNameInNamespace();
	String attrID;


	attrID = new String("corbaContainer");
	Attributes attrs1 = new BasicAttributes();

	attrs1.put(new BasicAttribute("cn", attrID));
	attrs1.put(new BasicAttribute("objectClass", "classSchema"));
	attrs1.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs1.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.10"));
	attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs1.put(new BasicAttribute("mustContain", "cn"));
	attrs1.put(new BasicAttribute("objectClassCategory", "1"));
	attrs1.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs1.put(new BasicAttribute("subclassOf", "top"));
	attrs1.put(new BasicAttribute("possSuperiors", "top")); //any superior
	attrs1.put(new BasicAttribute("description",
	    "Container for a CORBA object"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs1);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // corbaObject relys on corbaContainer


	attrID = new String("corbaObject");
	Attributes attrs2 = new BasicAttributes();

	attrs2.put(new BasicAttribute("cn", attrID));
	attrs2.put(new BasicAttribute("objectClass", "classSchema"));
	attrs2.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs2.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.9"));
	attrs2.put(new BasicAttribute("lDAPDisplayName", attrID));

	Attribute coMay = new BasicAttribute("mayContain");
	coMay.add("corbaRepositoryId");
	coMay.add("description");
	attrs2.put(coMay);

	attrs2.put(new BasicAttribute("objectClassCategory", "1"));
	attrs2.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs2.put(new BasicAttribute("subclassOf", "corbaContainer"));
	attrs2.put(new BasicAttribute("description",
	    "CORBA object representation"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs2);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // corbaObjectReference relys on corbaObject


	attrID = new String("corbaObjectReference");
	Attributes attrs3 = new BasicAttributes();

	attrs3.put(new BasicAttribute("cn", attrID));
	attrs3.put(new BasicAttribute("objectClass", "classSchema"));
	attrs3.put(new BasicAttribute("defaultHidingValue", "FALSE"));
	attrs3.put(new BasicAttribute("governsID",
	    "1.3.6.1.4.1.42.2.27.4.2.11"));
	attrs3.put(new BasicAttribute("lDAPDisplayName", attrID));
	attrs3.put(new BasicAttribute("mustContain", "corbaIor"));
	attrs3.put(new BasicAttribute("objectClassCategory", "1"));
	attrs3.put(new BasicAttribute("systemOnly", "FALSE"));
	attrs3.put(new BasicAttribute("subclassOf", "corbaObject"));
	attrs3.put(new BasicAttribute("description",
	    "CORBA interoperable object reference"));

	schemaCtx.createSubcontext("cn=" + attrID, attrs3);
System.out.println("    [" + attrID + "]");

	flushADSchemaMods(rootCtx); // finally
    }

    protected void printUsage(String msg) {
	printUsageAux(msg, "Corba");
    }
}
