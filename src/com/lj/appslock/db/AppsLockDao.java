package com.lj.appslock.db;

import com.lj.appslock.util.Constant;
import com.lj.appslock.util.StringUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.WebChromeClient.CustomViewCallback;

public class AppsLockDao {
	private AppsLockSQLHelper helper;

	public AppsLockDao(AppsLockSQLHelper helper) {
		this.helper = helper;
	}

	/**
	 * 删
	 */
	public void delete(String packName) {
		if (StringUtils.isEmpty(packName)) {
			return;
		}
		if (!find(packName)) {
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("DELETE FROM appslock WHERE packname=? ",
					new Object[] { packName });
			db.close();
		}

	}

	/**
	 * 可以临时的通过进入app pass 代表是否可以临时通过
	 */
	public void updatePass(String packName) {
		if (StringUtils.isEmpty(packName)) {
			return;
		}
		if (!find(packName)) {
			return;
		}
		SQLiteDatabase db = helper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("UPDATE appslock SET pass =1 WHERE packname=?",
					new Object[] { packName });
			db.close();
		}
		Constant.ISCHANGEDB = true;
	}

	/**
	 * 增
	 * 
	 * @param packName
	 */
	public void add(String packName) {
		 
		if (StringUtils.isEmpty(packName)) {
			 
			return;
		}
		if (find(packName)) {
			return;
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		if (db.isOpen()) {
			db.execSQL("INSERT INTO appslock(packname) VALUES(?)",
					new Object[] { packName });
			db.close();
		}

	}

	/**
	 * 查
	 */
	public boolean find(String packName) {
		boolean flag = false;
		if (StringUtils.isEmpty(packName)) {
			return flag;
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"SELECT packname FROM appslock WHERE packname=?",
					new String[] { packName });
			flag = cursor.moveToNext();
			cursor.close();
			db.close();
		}

		return flag;
	}

	public  boolean checkIsEnter(String packName) {
		boolean flag = false;
		if(Constant.ISCHANGEDB){//如果数据库本身没有任何改变
			return flag;
		}
		if (StringUtils.isEmpty(packName)) {
			return flag;
		}
		SQLiteDatabase db = helper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"SELECT pass FROM appslock WHERE packname=?",
					new String[] { packName });
			// 如果没有packname对应的报名，那么false。 否则再次判断：等于0 为false, 等于 1 为true
			flag = cursor.moveToNext() ? (cursor.getInt(0) == 0 ? false : true)
					: false;
			cursor.close();
			db.close();
		}
		return flag;
	}

}
