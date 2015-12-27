SELECT 'Dropping procedure SHOW_SUPPLIERS' AS ' '|
drop procedure if exists SHOW_SUPPLIERS|

SELECT 'Dropping procedure GET_SUPPLIER_OF_COFFEE' AS ' '|
drop procedure if exists GET_SUPPLIER_OF_COFFEE|

SELECT 'Dropping procedure RAISE_PRICE' AS ' '|
drop procedure if exists RAISE_PRICE|


SELECT 'Creating procedure SHOW_SUPPLIERS' AS ' '|
create procedure SHOW_SUPPLIERS()
  begin
    select SUPPLIERS.SUP_NAME, COFFEES.COF_NAME
    from SUPPLIERS, COFFEES
    where SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    order by SUP_NAME;
  end|

SELECT 'Creating procedure GET_SUPPLIER_OF_COFFEE' AS ' '|  
create procedure GET_SUPPLIER_OF_COFFEE(IN coffeeName varchar(32), OUT supplierName varchar(40))
  begin
    select SUPPLIERS.SUP_NAME into supplierName
      from SUPPLIERS, COFFEES
      where SUPPLIERS.SUP_ID = COFFEES.SUP_ID
      and coffeeName = COFFEES.COF_NAME;
    select supplierName;
  end|
  
SELECT 'Creating procedure RAISE_PRICE' AS ' '|  
create procedure RAISE_PRICE(IN coffeeName varchar(32), IN maximumPercentage float, INOUT newPrice numeric(10,2))
  begin
    main: BEGIN
      declare maximumNewPrice numeric(10,2);
      declare oldPrice numeric(10,2);
      select COFFEES.PRICE into oldPrice
        from COFFEES
        where COFFEES.COF_NAME = coffeeName;
      set maximumNewPrice = oldPrice * (1 + maximumPercentage);
      if (newPrice > maximumNewPrice)
        then set newPrice = maximumNewPrice;
      end if;
      if (newPrice <= oldPrice)
        then set newPrice = oldPrice;
        leave main;
      end if;
      update COFFEES
        set COFFEES.PRICE = newPrice
        where COFFEES.COF_NAME = coffeeName;
      select newPrice;
    END main;
  end|
  
SELECT 'Listing stored procedures ...' AS ' '|  
show procedure status|