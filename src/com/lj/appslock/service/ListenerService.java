package com.lj.appslock.service;

import java.util.List;

import com.lj.appslock.db.AppsLockDao;
import com.lj.appslock.db.AppsLockSQLHelper;
import com.lj.appslock.ui.GuardActivity;
import com.lj.appslock.util.Constant;
import com.lj.appslock.util.StringUtils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

public class ListenerService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
    
	@Override
	public void onCreate() {

		final ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		final AppsLockDao dao = new AppsLockDao(new AppsLockSQLHelper(
				getApplicationContext()));
		new Thread() {
			public void run() {
				while (true) {
					String curRunPackName = am.getRunningTasks(1).get(0).baseActivity.getPackageName(); // ��ǰ�������е�
					if (!Constant.isOpen&&dao.checkIsEnter(curRunPackName)) {//
						Log.e("View", "�򿪡���������ui");
						Intent i = new Intent(ListenerService.this,
								GuardActivity.class);
						i.putExtra("setPwd", false);
						i.putExtra("packName", curRunPackName);
						// ����icon. name
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
					
					} else {
						Log.e("app", "����ֱ�ӽ���");
					}

				}
			};
		}.start();

		super.onCreate();
	}

}
