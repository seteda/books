private void addSubMenu(Menu menu)
{
   //Secondary items are shown just like everything else
   int base=Menu.FIRST + 100;

   SubMenu sm = menu.addSubMenu(base,base+1,Menu.NONE,"submenu");
   sm.add(base,base+2,base+2,"sub item1");
   sm.add(base,base+3,base+3, "sub item2");
   sm.add(base,base+4,base+4, "sub item3");

   //submenu item icons are not supported
   item1.setIcon(R.drawable.icon48x48_2);

   //the following is ok however
   sm.setIcon(R.drawable.icon48x48_1);

   //This will result in a runtime exception
   //sm.addSubMenu("try this");
}


