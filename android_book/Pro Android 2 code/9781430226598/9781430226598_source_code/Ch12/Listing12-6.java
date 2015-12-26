MatrixCursor mc = loadNewData(this);
mc.setNotificationUri(getContext().getContentResolver(),
               Uri.parse("content://contacts/people/"));
MyCursor wmc = new MyCursor(mc,this);
