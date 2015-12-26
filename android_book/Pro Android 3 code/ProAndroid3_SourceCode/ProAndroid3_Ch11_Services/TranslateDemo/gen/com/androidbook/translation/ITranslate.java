/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\chapterTest\\TranslateDemo\\src\\com\\androidbook\\translation\\ITranslate.aidl
 */
package com.androidbook.translation;
// This file is ITranslate.aidl under /src

public interface ITranslate extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.androidbook.translation.ITranslate
{
private static final java.lang.String DESCRIPTOR = "com.androidbook.translation.ITranslate";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.androidbook.translation.ITranslate interface,
 * generating a proxy if needed.
 */
public static com.androidbook.translation.ITranslate asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.androidbook.translation.ITranslate))) {
return ((com.androidbook.translation.ITranslate)iin);
}
return new com.androidbook.translation.ITranslate.Stub.Proxy(obj);
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
case TRANSACTION_translate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _result = this.translate(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.androidbook.translation.ITranslate
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
public java.lang.String translate(java.lang.String text, java.lang.String from, java.lang.String to) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(text);
_data.writeString(from);
_data.writeString(to);
mRemote.transact(Stub.TRANSACTION_translate, _data, _reply, 0);
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
static final int TRANSACTION_translate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String translate(java.lang.String text, java.lang.String from, java.lang.String to) throws android.os.RemoteException;
}
