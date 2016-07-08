package com.sh_trisk.fxkz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class RefreshService extends IntentService {

	public RefreshService() {
		super("RefreshService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		String token = intent.getStringExtra("token"),
				IP1 = "/api/subprojects?all&x_auth_token="+token;
		while(true){
		String json1 = HttpUtils.getJson(IP1);
		if(json1.startsWith("A")){
			System.out.println("刷新失败");
			try {
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		JSONObject jo;
		List<Integer> list = new ArrayList<Integer>();
		try {
			jo = new JSONObject(json1);
			JSONArray ja = jo.getJSONArray("info");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject data = ja.getJSONObject(i);
				list.add(data.getInt("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0;i<list.size();i++){
			String IP2 = "/api/devicelogicdatas?sub_project_id="+list.get(i)+"&x_auth_token="+token;
			String json2 = HttpUtils.getJson(IP2);
			JSONObject jo2;
			try {
				jo2 = new JSONObject(json2);
				JSONArray ja = jo2.getJSONArray("info");
				for (int j = 0; j < ja.length(); j++) {
					JSONObject data = ja.getJSONObject(j);
					if(data.getInt("status")==2){
						System.out.println("应有通知发出");
						NotificationCompat.Builder mBuilder =
						        new NotificationCompat.Builder(this)
						        .setSmallIcon(R.drawable.companylogo)
						        .setContentTitle("风险报警通知")
						        .setContentText("工程ID："+list.get(i)+" 采集点ID："+data.getInt("id"));
						NotificationManager mNotificationManager =
							    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
						mNotificationManager.notify(0, mBuilder.build());
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.currentThread().sleep(1000*60*20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}}

}
