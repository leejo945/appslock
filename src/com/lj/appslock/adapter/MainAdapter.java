 
package com.lj.appslock.adapter;

import java.util.ArrayList;

import com.lj.appslock.R;
import com.lj.appslock.db.AppsLockDao;
import com.lj.appslock.db.AppsLockSQLHelper;
import com.lj.appslock.entity.AppInfo;
import com.lj.appslock.ui.GuardActivity;
import com.lj.appslock.util.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AppInfo> list;
    private SharedPreferences sp;
    private  AppsLockDao dao;
    public MainAdapter(Context context,ArrayList<AppInfo> list){
    	this.context = context;
    	this.list = list;
    	sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        dao =    new AppsLockDao(new AppsLockSQLHelper(context));
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.gv_item, null);
			holder.appIcon = (ImageView) convertView.findViewById(R.id.gv_item_iv);
			holder.appName = (TextView) convertView.findViewById(R.id.gv_item_tv);
			holder.check  = (ImageView) convertView.findViewById(R.id.gv_item_check_iv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final AppInfo appInfo = list.get(position);
	    holder.appIcon.setImageDrawable(appInfo.appIcon);
	    holder.appName.setText(appInfo.appName);
	    
	    //����Ǳ����ģ���ʾ��ʱ���Ҫ����
	   
	    if(appInfo.isLock){
	    	//������ 
	    	holder.check.setVisibility(View.VISIBLE);
	    }else{
	    	//û�б���
	    	holder.check.setVisibility(View.INVISIBLE);
	    }
	    convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�����û���������룬��ô��������ʱ��Ҫ��������
			     String isSetPwd = sp.getString("pwd","");
			     if(StringUtils.isEmpty(isSetPwd)){//������������ã� 
			    	  Toast.makeText(context, "û�г�ʼ�����룡��", 0).show();
			    	 //��������
			    	 Intent  s = new Intent(context,GuardActivity.class);
			    	 s.putExtra("setPwd", true);
			    	 context.startActivity(s);
			     }else{
			    	
			    	 //����Ѿ��������룬�Ͳ�����ת�ˡ���
			    	 if(appInfo.isLock){//�����ǰ��ʱ������������ˣ��ͽ������Ǿ�ɾ�����ݿ��д洢
						 holder.check.setVisibility(View.INVISIBLE);//����ʾ
						 appInfo.isLock = false;
						 dao.delete(appInfo.packageName);
					 }else{//������ʾ
						 holder.check.setVisibility(View.VISIBLE); //��ʾ
						 appInfo.isLock = true;
					     dao.add(appInfo.packageName);
					 }
					 
			     }
				
				
			
				
			}
		});
	    
		return convertView;
	}
   private static class ViewHolder{
	   ImageView appIcon,check;
	   TextView appName;
   }
}
