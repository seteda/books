//…
SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(NOTES_TABLE_NAME, Notes.NOTE, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(
NotePad.Notes.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

