@Override
public boolean onCreateOptionsMenu(Menu menu)
{
   //call the base class to include system menus
   super.onCreateOptionsMenu(menu);
   menu.add(0 // Group
         ,1 // item id
         ,0 //order
         ,"append"); // title
         
   menu.add(0,2,1,"item2");
   menu.add(0,3,2,"clear");
   
   //It is important to return true to see the menu
   return true;
}


