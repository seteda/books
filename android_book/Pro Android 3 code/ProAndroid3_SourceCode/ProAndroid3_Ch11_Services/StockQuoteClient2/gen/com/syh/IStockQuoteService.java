/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\chapterTest\\StockQuoteClient2\\src\\com\\syh\\IStockQuoteService.aidl
 */
package com.syh;
public interface IStockQuoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.syh.IStockQuoteService
{
private static final java.lang.String DESCRIPTOR = "com.syh.IStockQuoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.syh.IStockQuoteService interface,
 * generating a proxy if needed.
 */
public static com.syh.IStockQuoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.syh.IStockQuoteService))) {
return ((com.syh.IStockQuoteService)iin);
}
return new com.syh.IStockQuoteService.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getQuote:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
com.syh.Person _arg1;
if ((0!=data.readInt())) {
_arg1 = com.syh.Person.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
java.lang.String _result = this.getQuote(_arg0, _arg1);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.syh.IStockQuoteService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public java.lang.String getQuote(java.lang.String ticker, com.syh.Person requester) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(ticker);
if ((requester!=null)) {
_data.writeInt(1);
requester.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getQuote, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getQuote = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String getQuote(java.lang.String ticker, com.syh.Person requester) throws android.os.RemoteException;
}
