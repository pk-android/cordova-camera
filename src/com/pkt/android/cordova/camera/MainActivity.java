package com.pkt.android.cordova.camera;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pkt.android.cordova.camera.R;

public class MainActivity extends Activity implements CordovaInterface{
	CordovaWebView cwv;
	protected Context ctx;
	private CordovaPlugin activityResultCallback;
	private Object activityResultKeepRunning;
    private Object keepRunning;
    protected ExecutorService  threadPool=Executors.newCachedThreadPool();
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ExecutorService getThreadPool() {
		 return threadPool;
	}

	@Override
	public Object onMessage(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActivityResultCallback(CordovaPlugin plugin) {
		this.activityResultCallback = plugin;
		
	}

	@Override
	public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
		this.activityResultCallback = command;
	    this.activityResultKeepRunning = this.keepRunning;


	// If multitasking turned on, then disable it for activities that return results
	if (command != null) {
	    this.keepRunning = false;
	}


	// Start activity
	super.startActivityForResult(intent, requestCode);
		
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cwv = (CordovaWebView) findViewById(R.id.launcherWebView);
        Config.init(this);
        cwv.loadUrl("http://10.73.41.17:8091/Cordova/html/camera.html");
        //cwv.loadDataWithBaseURL("http://10.73.41.17.8091/Cordova/html/camera.html", "", "", "utf-8", "");
    }
	@Override
	/**
	 * Called when an activity you launched exits, giving you the requestCode you started it with,
	 * the resultCode it returned, and any additional data from it.
	 *
	 * @param requestCode       The request code originally supplied to startActivityForResult(),
	 *                          allowing you to identify who this result came from.
	 * @param resultCode        The integer result code returned by the child activity through its setResult().
	 * @param data              An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    super.onActivityResult(requestCode, resultCode, intent);
	    CordovaPlugin callback = this.activityResultCallback;
	    if (callback != null) {
	        callback.onActivityResult(requestCode, resultCode, intent);
	    }
	}

}
