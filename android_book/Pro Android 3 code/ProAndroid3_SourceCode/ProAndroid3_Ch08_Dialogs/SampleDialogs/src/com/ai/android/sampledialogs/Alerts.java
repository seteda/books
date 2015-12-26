package com.ai.android.sampledialogs;

import com.ai.android.sampledialogs.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class Alerts 
{
    public static void showAlert(String message, Context ctx)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
    	builder.setTitle("Alert");
    	builder.setMessage(message);
    	EmptyOnClickListener l = new EmptyOnClickListener();
    	builder.setPositiveButton("Ok", l );
    	AlertDialog ad = builder.create();
    	ad.show();
    }
    
    public static Dialog createAlertDialog(String message, Context ctx)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
    	builder.setTitle("Alert");
    	builder.setMessage(message);
    	EmptyOnClickListener l = new EmptyOnClickListener();
    	builder.setPositiveButton("Ok", l );
    	AlertDialog ad = builder.create();
    	return ad;
    }
    public static void Prompt(IStringPrompterCallBack cb
    		,String message 
    		,Context ctx
    		,int actionId)
    {
    	LayoutInflater li = LayoutInflater.from(ctx);
    	View view = li.inflate(R.layout.promptdialog, null);
    	AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
    	builder.setTitle("prompt");
    	builder.setView(view);
    	PromptListener pl = new PromptListener(view,ctx,cb,actionId);
    	builder.setPositiveButton("OK", pl);
    	builder.setNegativeButton("Cancel", pl);
    	AlertDialog ad = builder.create();
    	ad.show();
    }
}//eof-class
