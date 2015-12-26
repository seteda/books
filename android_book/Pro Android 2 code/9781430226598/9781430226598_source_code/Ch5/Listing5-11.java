@Override
public boolean onOptionsItemSelected(MenuItem item) 
{
   if (item.getItemId() == 1) {
   appendText("\nhello");
   }
   else if (item.getItemId() == 2) {
   appendText("\nitem2");
   }
   else if (item.getItemId() == 3) {
   emptyText();
   }
   else if (item.getItemId() == 4) {
   //hide secondary
   this.appendMenuItemText(item);
   this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,false);
   }
   else if (item.getItemId() == 5) {
   //show secondary
   this.appendMenuItemText(item);
   this.myMenu.setGroupVisible(Menu.CATEGORY_SECONDARY,true);
   }
   else if (item.getItemId() == 6) {
   //enable secondary
   this.appendMenuItemText(item);
   this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,true);
   }
   else if (item.getItemId() == 7) {
   //disable secondary
   this.appendMenuItemText(item);
   this.myMenu.setGroupEnabled(Menu.CATEGORY_SECONDARY,false);
   }
   else if (item.getItemId() == 8) {
   //check secondary
   this.appendMenuItemText(item);
   myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,true,false);
   }
   else if (item.getItemId() == 9) {
   //uncheck secondary
   this.appendMenuItemText(item);
   myMenu.setGroupCheckable(Menu.CATEGORY_SECONDARY,false,false);
   }
   else {
   this.appendMenuItemText(item);
   }
   //should return true if the menu item
   //is handled
   return true;
}


