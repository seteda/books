CREATE PROCEDURE SHOW_SUPPLIERS(supplierName varchar(40))
  PARAMETER STYLE JAVA
  LANGUAGE JAVA
  DYNAMIC RESULT SETS 1
  EXTERNAL NAME 'com.oracle.tutorial.jdbc.StoredProcedureJavaDBSample.showSuppliers';
  
CREATE PROCEDURE GET_SUPPLIER_OF_COFFEE(IN coffeeName varchar(32), OUT supplierName varchar(40))
  PARAMETER STYLE JAVA
  LANGUAGE JAVA
  DYNAMIC RESULT SETS 0
  EXTERNAL NAME 'com.oracle.tutorial.jdbc.StoredProcedureJavaDBSample.getSupplierOfCoffee';
  
CREATE PROCEDURE RAISE_PRICE(IN coffeeName varchar(32), IN maximumPercentage float, INOUT newPrice numeric(10,2))
  PARAMETER STYLE JAVA
  LANGUAGE JAVA
  DYNAMIC RESULT SETS 0
  EXTERNAL NAME 'com.oracle.tutorial.jdbc.StoredProcedureJavaDBSample.raisePrice'
