// Search for, and populate the menu with matching Activities.
menu.addIntentOptions(
   Menu.CATEGORY_ALTERNATIVE,    // Group
   Menu.CATEGORY_ALTERNATIVE,    // Any unique IDs we might care to add.
   Menu.CATEGORY_ALTERNATIVE,    // order
   getComponentName(),           // Name of the class displaying
   //the menu--here, it's this class.
   null,                         // No specifics.
   criteriaIntent,               // Previously created intent that
   // describes our requirements.
   0,                            // No flags.
   null);                        
