		    Java/CORBA LDAP Schema Tools

This note describes the tools for creating schema entries for storing
Java and CORBA objects.

The Java schema is described in RFC 2713.
      http://www.ietf.org/rfc/rfc2713.txt

The CORBA schema is described in RFC 2714.
      http://www.ietf.org/rfc/rfc2714.txt


1. UPDATING THE DIRECTORY SCHEMA

   You can use your directory's administration tool to update the schema,
   Or, you can use the CreateJavaSchema and CreateCorbaSchema programs.

      java [-Djava.naming.provider.url=<provider_url>] \
         CreateCorbaSchema|CreateJavaSchema \
	   [-h|-s[n]] [-n<dn>] [-p<passwd>] [-a<auth>] 
       
     -h		  Print the usage message
     -l		  List the relevant schema in the directory
     -s[n|n41|ad] Update schema:
                    -sn   means use a workaround for schema bugs in
                          pre-4.1 releases of Netscape Directory Server

		    -sn41 means use a workaround for schema bugs in
                          Netscape Directory Server version 4.1

		    -sad  means use a workaround for schema bugs in
			  Microsoft Windows 2000 Active Directory

     -n<dn> 	  Use <dn> as the distinguished name for authentication
     -p<passwd>	  Use <passwd> as the password for authentication
     -a<auth>	  Use <auth> as the authentication mechanism.
		  Default is "simple".
 
   If neither -s, -l, nor -h has been specified, the default is "-l".

   NOTE: If you are using Netscape Directory Server 4.0 (or earlier),
         you need to use the "-sn" option to workaround schema bugs in
         the server.

   NOTE: If you are using Netscape Directory Server 4.1, 
         you need to use the "-sn41" option to workaround schema bugs in
         the server. Also, before making any changes to the schema, you 
         must make the following schema modification. Locate the ns-schema.conf
         file in the server installation at the directory named:

             <NETSCAPE-DIRECTORY-HOME>/slapd-<SERVER-ID>/config/

         Comment out the line that contains 'java-object-schema.conf'
         because that schema is now out-of-date. Then re-start the
         server and use the CreateJavaSchema program to install the
         updated schema. It is necessary to manually remove the
         reference to the old schema from the list of built-in schemas
         in ns-schema.conf because the server does not permit such
         built-in schemas to be modified via the LDAP protocol.

   NOTE: If you are using Microsoft Active Directory you need to use
         the "-sad" option to workaround schema bugs in the server.
         Also, to enable any schema modifications the following registry
         property must be created and set to (DWORD) 1:

             HKEY_LOCAL_MACHINE
	       System
	         CurrentControlSet
	           Services
	             NTDS
	               Parameters
	                 Schema Update Allowed

   If the directory's administration tool supports turning off schema
   checking then do so before running the CreateJavaSchema or
   CreateCorbaSchema programs.

   When you run these programs, you need to login as the directory
   administrator, quoting the "-n" option if there are blanks in the
   distinguished name. After updating the schema, you should use the
   directory server's administration tool to verify that the changes
   have been properly applied. The Netscape Directory Server 3.0, for
   example, ignores some attributes. If the schema has not been properly
   updated, use the administration tool to correct it.

   For example, to create the Java schema in a Netscape Directory Server
   version 4.1,

       java -Djava.naming.provider.url=ldap://myserver CreateJavaSchema\
           -sn41 "-ncn=Directory Administrator" -psecret

   For example, to create the CORBA schema in a Netscape Directory Server
   version 4.0,

       java -Djava.naming.provider.url=ldap://myserver CreateCorbaSchema\
           -sn "-ncn=Directory Administrator" -psecret

   For example, to create the Java schema in a Windows 2000 Active Directory,

       java -Djava.naming.provider.url=ldap://myserver CreateJavaSchema\
           -sad "-ncn=administrator,cn=users,dc=xxx,dc=yyy,dc=zzz" -psecret
----
