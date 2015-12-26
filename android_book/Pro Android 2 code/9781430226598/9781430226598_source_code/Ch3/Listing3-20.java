// An array specifying which columns to return.
string[] projection = new string[] {
   People._ID,
   People.NAME,
   People.NUMBER,
};

// Get the base URI for People table in Contacts Content Provider.
// ie. content://contacts/people/
Uri mContactsUri = People.CONTENT_URI;

// Best way to retrieve a query; returns a managed query.
Cursor managedCursor = managedQuery( mContactsUri,
                  projection, //Which columns to return.
                  null, // WHERE clause
                  People.NAME + " ASC"); // Order-by clause.


