SELECT 'Dropping procedure SHOW_SUPPLIERS' AS ' ';

drop procedure if exists SHOW_SUPPLIERS;

SELECT 'Changing delimiter to pipe' AS ' ';

delimiter |

SELECT 'Creating procedure SHOW_SUPPLIERS' AS ' '|

create procedure SHOW_SUPPLIERS()
  begin
    select SUPPLIERS.SUP_NAME, COFFEES.COF_NAME
    from SUPPLIERS, COFFEES
    where SUPPLIERS.SUP_ID = COFFEES.SUP_ID
    order by SUP_NAME;
  end|

delimiter ;