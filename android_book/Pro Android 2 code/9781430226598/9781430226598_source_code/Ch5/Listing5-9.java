private void addRegularMenuItems(Menu menu)
{
   int base=Menu.FIRST; // value is 1

   menu.add(base,base,base,"append");
   menu.add(base,base+1,base+1,"item 2");
   menu.add(base,base+2,base+2,"clear");

   menu.add(base,base+3,base+3,"hide secondary");
   menu.add(base,base+4,base+4,"show secondary");

   menu.add(base,base+5,base+5,"enable secondary");
   menu.add(base,base+6,base+6,"disable secondary");

   menu.add(base,base+7,base+7,"check secondary");
   menu.add(base,base+8,base+8,"uncheck secondary");
}


