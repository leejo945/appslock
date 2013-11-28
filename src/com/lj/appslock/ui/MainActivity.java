package com.lj.appslock.ui;

import java.util.ArrayList;
import java.util.List;

import com.lj.appslock.App;
import com.lj.appslock.R;
import com.lj.appslock.R.id;
import com.lj.appslock.R.layout;
import com.lj.appslock.adapter.MainAdapter;
import com.lj.appslock.entity.AppInfo;
import com.lj.appslock.util.DataService;
import com.lj.appslock.util.ThreadPool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {
	private GridView mContainerGV;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.e("applock", "发送信息过啦ie ");
			MainAdapter adapter = new MainAdapter(MainActivity.this,
					(ArrayList<AppInfo>) msg.obj);
			mContainerGV.setAdapter(adapter);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((App) getApplication()).setActivity(this);
		mContainerGV = (GridView) findViewById(R.id.main_gv);

		ThreadPool.getInstance().addTask(new Thread() {
			@Override
			public void run() {
				ArrayList<AppInfo> list = DataService
						.getApps(MainActivity.this);
				Log.e("applock",list.size()   +"--------"+!list.isEmpty());
				if (!list.isEmpty()) {
					Message msg = handler.obtainMessage(0, list);
					handler.sendMessage(msg);
				}
				super.run();
			}
		});

	}

}
