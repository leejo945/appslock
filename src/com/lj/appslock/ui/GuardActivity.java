package com.lj.appslock.ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.lj.appslock.App;
import com.lj.appslock.R;
import com.lj.appslock.db.AppsLockDao;
import com.lj.appslock.db.AppsLockSQLHelper;
import com.lj.appslock.util.Constant;
import com.lj.appslock.util.MD5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class GuardActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Constant.isOpen = true;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guard);
		((App)getApplication()).setActivity(this);
		final boolean isSetPwd = getIntent().getBooleanExtra("setPwd", false);
	
		
		final String packName  = getIntent().getStringExtra("");
		final SharedPreferences sp = getSharedPreferences("config",
				MODE_PRIVATE);

		try {
			final EditText mPwdET = (EditText) findViewById(R.id.guard_pwd_et);
			findViewById(R.id.guard_sure_bt).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							String curPwd = mPwdET.getText().toString().trim();
							if (isSetPwd) {// 是设置密码
                                Editor editor = sp.edit();
                                editor.putString("pwd", MD5.GetMD5Code(curPwd)); 
                                editor.commit();
                                finish();
							}else{
								String memoryPwd = sp.getString("pwd", "");
								// 如果输入的密码加密后和存储的密码一样，那么我们就通过了
								if (memoryPwd.equals(MD5.GetMD5Code(curPwd))) {
									((App)getApplication()).exit();
									//如果密码相同了，说明密码正确，所以退出app的所有activity
									//并且这时候，要临时的让该app通过。
								    AppsLockDao dao = new AppsLockDao(new AppsLockSQLHelper(GuardActivity.this));
									dao.updatePass(packName);
								}
							}

							

						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Constant.isOpen = false;
		super.onDestroy();
	}

}
