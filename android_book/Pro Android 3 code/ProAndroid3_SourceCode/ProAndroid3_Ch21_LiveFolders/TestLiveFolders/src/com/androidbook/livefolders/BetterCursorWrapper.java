package com.androidbook.livefolders;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.CrossProcessCursor;
import android.database.CursorWindow;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

public class BetterCursorWrapper implements CrossProcessCursor
{
   //Holds the internal cursor to delegate methods to
   protected CrossProcessCursor internalCursor;
   
   //Constructor takes a crossprocesscursor as an input
   public BetterCursorWrapper(CrossProcessCursor inCursor)
   {
      this.setInternalCursor(inCursor);
   }
   
   //You can reset in one of the derived classes methods
   public void setInternalCursor(CrossProcessCursor inCursor)
   {
      internalCursor = inCursor;
   }
   
   //All delegated methods follow
   public void fillWindow(int arg0, CursorWindow arg1) {
      internalCursor.fillWindow(arg0, arg1);
   }
   public CursorWindow getWindow() {
      return internalCursor.getWindow();
   }
   public boolean onMove(int arg0, int arg1) {
      return internalCursor.onMove(arg0, arg1);
   }

   public void close() {
      internalCursor.close();
   }

   public void copyStringToBuffer(int arg0, CharArrayBuffer arg1) {
      internalCursor.copyStringToBuffer(arg0, arg1);
   }

   public void deactivate() {
      internalCursor.deactivate();
   }

   public byte[] getBlob(int columnIndex) {
      return internalCursor.getBlob(columnIndex);
   }

   public int getColumnCount() {
      return internalCursor.getColumnCount();
   }

   public int getColumnIndex(String columnName) {
      return internalCursor.getColumnIndex(columnName);
   }

   public int getColumnIndexOrThrow(String columnName)
         throws IllegalArgumentException {
      return internalCursor.getColumnIndexOrThrow(columnName);
   }

   public String getColumnName(int columnIndex) {
      return internalCursor.getColumnName(columnIndex);
   }

   public String[] getColumnNames() {
      return internalCursor.getColumnNames();
   }

   public int getCount() {
      return internalCursor.getCount();
   }

   public double getDouble(int columnIndex) {
      return internalCursor.getDouble(columnIndex);
   }

   public Bundle getExtras() {
      return internalCursor.getExtras();
   }

   public float getFloat(int columnIndex) {
      return internalCursor.getFloat(columnIndex);
   }

   public int getInt(int columnIndex) {
      return internalCursor.getInt(columnIndex);
   }

   public long getLong(int columnIndex) {
      return internalCursor.getLong(columnIndex);
   }

   public int getPosition() {
      return internalCursor.getPosition();
   }

   public short getShort(int columnIndex) {
      return internalCursor.getShort(columnIndex);
   }

   public String getString(int columnIndex) {
      return internalCursor.getString(columnIndex);
   }

   public boolean getWantsAllOnMoveCalls() {
      return internalCursor.getWantsAllOnMoveCalls();
   }

   public boolean isAfterLast() {
      return internalCursor.isAfterLast();
   }

   public boolean isBeforeFirst() {
      return internalCursor.isBeforeFirst();
   }

   public boolean isClosed() {
      return internalCursor.isClosed();
   }

   public boolean isFirst() {
      return internalCursor.isFirst();
   }

   public boolean isLast() {
      return internalCursor.isLast();
   }

   public boolean isNull(int columnIndex) {
      return internalCursor.isNull(columnIndex);
   }

   public boolean move(int offset) {
      return internalCursor.move(offset);
   }

   public boolean moveToFirst() {
      return internalCursor.moveToFirst();
   }

   public boolean moveToLast() {
      return internalCursor.moveToLast();
   }

   public boolean moveToNext() {
      return internalCursor.moveToNext();
   }

   public boolean moveToPosition(int position) {
      return internalCursor.moveToPosition(position);
   }

   public boolean moveToPrevious() {
      return internalCursor.moveToPrevious();
   }

   public void registerContentObserver(ContentObserver observer) {
      internalCursor.registerContentObserver(observer);
   }

   public void registerDataSetObserver(DataSetObserver observer) {
      internalCursor.registerDataSetObserver(observer);
   }

   public boolean requery() {
      return internalCursor.requery();
   }

   public Bundle respond(Bundle extras) {
      return internalCursor.respond(extras);
   }

   public void setNotificationUri(ContentResolver cr, Uri uri) {
      internalCursor.setNotificationUri(cr, uri);
   }

   public void unregisterContentObserver(ContentObserver observer) {
      internalCursor.unregisterContentObserver(observer);
   }

   public void unregisterDataSetObserver(DataSetObserver observer) {
      internalCursor.unregisterDataSetObserver(observer);
   }
}


