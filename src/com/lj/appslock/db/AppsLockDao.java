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
	 * ɾ
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
	 * ������ʱ��ͨ������app pass �����Ƿ������ʱͨ��
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
	 * ��
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
	 * ��
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
		if(Constant.ISCHANGEDB){//������ݿⱾ��û���κθı�
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
			// ���û��packname��Ӧ�ı�������ôfalse�� �����ٴ��жϣ�����0 Ϊfalse, ���� 1 Ϊtrue
			flag = cursor.moveToNext() ? (cursor.getInt(0) == 0 ? false : true)
					: false;
			cursor.close();
			db.close();
		}
		return flag;
	}

}
