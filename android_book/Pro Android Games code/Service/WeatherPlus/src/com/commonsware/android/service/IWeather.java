/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/mmurphy/stuff/CommonsWare/books/Android/src/samples/Service/WeatherPlus/src/com/commonsware/android/service/IWeather.aidl
 */
package com.commonsware.android.service;
import java.lang.String;
import android.os.RemoteException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;
import android.os.Parcel;
// Declare the interface.

public interface IWeather extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.commonsware.android.service.IWeather
{
private static final java.lang.String DESCRIPTOR = "com.commonsware.android.service.IWeather";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an IWeather interface,
 * generating a proxy if needed.
 */
public static com.commonsware.android.service.IWeather asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.commonsware.android.service.IWeather))) {
return ((com.commonsware.android.service.IWeather)iin);
}
return new com.commonsware.android.service.IWeather.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getForecastPage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getForecastPage();
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.commonsware.android.service.IWeather
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
public java.lang.String getForecastPage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getForecastPage, _data, _reply, 0);
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
static final int TRANSACTION_getForecastPage = (IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String getForecastPage() throws android.os.RemoteException;
}
