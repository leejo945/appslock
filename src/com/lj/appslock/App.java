package com.lj.appslock;

import java.util.ArrayList;
import java.util.List;

import com.lj.appslock.receiver.LockScreenReceiver;
import com.lj.appslock.service.ListenerService;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

public class App extends Application {
	private LockScreenReceiver receiver;
	private static List<Activity> mActivitys;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Intent service = new Intent(this, ListenerService.class);
		startService(service);
		// <action android:name="android.intent.action.SCREEN_OFF"/>
		// <action android:name="android.intent.action.SCREEN_ON"/>
		receiver = new LockScreenReceiver();
		IntentFilter off_filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		IntentFilter on_filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, off_filter);
		registerReceiver(receiver, on_filter);
		super.onCreate();
	}

	public void setActivity(Activity activity) {
		if (mActivitys == null) {
			mActivitys = new ArrayList<Activity>();
		}
		mActivitys.add(activity);
	}

	public void exit() {
		if (mActivitys != null) {
			for (Activity acitivty : mActivitys) {
				acitivty.finish();
			}

		}
	}

}
