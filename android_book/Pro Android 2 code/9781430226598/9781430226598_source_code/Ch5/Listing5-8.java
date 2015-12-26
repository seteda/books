@Override
public boolean onCreateOptionsMenu(Menu menu)
{
   //call the parent to attach any system level menus
   super.onCreateOptionsMenu(menu);
   this.myMenu = menu;
   
   //add a few normal menus
   addRegularMenuItems(menu);
   
   //add a few secondary menus
   add5SecondaryMenuItems(menu);
   
   //it must return true to show the menu
   //if it is false menu won't show
   return true;
}


