 
package com.lj.appslock.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lj.appslock.db.AppsLockDao;
import com.lj.appslock.db.AppsLockSQLHelper;
import com.lj.appslock.entity.AppInfo;

public class DataService {
	/**
	 * 获得设备中所有安装的app，并封装
	 * @return
	 */
    public static ArrayList<AppInfo> getApps(Context context){
    	ArrayList<AppInfo> list = new ArrayList<AppInfo>();
    	PackageManager pManager = context.getPackageManager();
		List<PackageInfo> packageInfos = pManager
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		AppInfo appInfo = null;
		for (PackageInfo packageInfo : packageInfos) {
			appInfo = new AppInfo();
			ApplicationInfo info = packageInfo.applicationInfo;
			appInfo.packageName = packageInfo.packageName;
			appInfo.appIcon = info.loadIcon(pManager);
			appInfo.appName = info.loadLabel(pManager).toString();
			AppsLockDao dao = new AppsLockDao(new AppsLockSQLHelper(context));
			appInfo.isLock = dao.find(appInfo.packageName);
			list.add(appInfo);
		}
    	return list;
    }
}
