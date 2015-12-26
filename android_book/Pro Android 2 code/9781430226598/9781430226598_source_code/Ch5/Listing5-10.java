private void add5SecondaryMenuItems(Menu menu)
{
   //Secondary items are shown just like everything else
   int base=Menu.CATEGORY_SECONDARY;
   
   menu.add(base,base+1,base+1,"sec. item 1");
   menu.add(base,base+2,base+2,"sec. item 2");
   menu.add(base,base+3,base+3,"sec. item 3");
   menu.add(base,base+3,base+3,"sec. item 4");
   menu.add(base,base+4,base+4,"sec. item 5");
}


