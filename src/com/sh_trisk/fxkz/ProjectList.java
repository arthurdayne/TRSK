package com.sh_trisk.fxkz;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectList extends Activity {

	private ListView lv;
	private String token, projectsJson;
	private List<Project> list;
	private List<Integer> distances;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_projectslist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);

		lv = (ListView) this.findViewById(R.id.list);
		list = new ArrayList<Project>();
		distances = new ArrayList<Integer>();
		Intent intent = getIntent();
		token = intent.getStringExtra("token");
		projectsJson = intent.getStringExtra("projectsJson");
		try {
			JSONObject jo = new JSONObject(projectsJson);
			JSONArray ja = jo.getJSONArray("info");
			// String name;
			// int type_id;
			for (int i = 0; i < ja.length(); i++) {
				JSONObject data = ja.getJSONObject(i);
				list.add(new Project(data.getInt("type_id"), data.getInt("id"), data.getString("name"),
						data.getString("dir_from"), data.getString("dir_to"), data.getString("start_pos_code"),
						data.getString("end_pos_code")));
				distances.add(data.getInt("end_pos") - data.getInt("start_pos"));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyAdapter adapter = new MyAdapter(list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				int ID = list.get(position).getId();
				sb.append("/api/subprojectlinings?status=1&sub_project_id=").append(ID).append("&x_auth_token=")
						.append(token);
				sb.append("A");
				sb.append("/HorizontalSection/getBinds?spid=").append(ID).append("&x_auth_token=").append(token);
				sb.append("A");
				sb.append("/api/horizontalsections?spid=").append(ID).append("&x_auth_token=").append(token);
				new GetInfoTask().execute(sb.toString(), String.valueOf(position));
			}
		});
		if (!isWorking()) {
			System.out.println("working?"+isWorking());
			Intent serviceintent = new Intent(this, RefreshService.class);
			serviceintent.putExtra("token", token);
			startService(serviceintent);
		}

	}

	private class GetInfoTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String[] queryStrings = params[0].split("A");
			String liningString = HttpUtils.getJson(queryStrings[0]);
			String binds = HttpUtils.getJson(queryStrings[1]);
			String sections = HttpUtils.getJson(queryStrings[2]);

			if (liningString.startsWith("A")) {
				return liningString;
			}
			if (binds.startsWith("A")) {
				return binds;
			}
			if (sections.startsWith("A")) {
				return sections;
			}
			return new StringBuilder().append(liningString).append("A").append(binds).append("A").append(sections)
					.append("A").append(params[1]).toString();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.startsWith("A")) {
				Toast.makeText(ProjectList.this, result.substring(1), Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent(ProjectList.this, InfoActivity.class);
				int p = Integer.parseInt(result.split("A")[3]);
				Project pro = list.get(p);
				intent.putExtra("Json", result);
				intent.putExtra("from", pro.getFrom());
				intent.putExtra("to", pro.getTo());
				intent.putExtra("name", pro.getName());
				intent.putExtra("start", pro.getStart());
				intent.putExtra("end", pro.getEnd());
				intent.putExtra("subproject_id", pro.getId());
				intent.putExtra("token", token);
				intent.putExtra("distance", distances.get(p));
				startActivity(intent);
			}
		}
	}

	private boolean isWorking() {
		ActivityManager myManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(50);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString().equals("com.sh_trisk.fxkz.RefreshService")) {
				return true;
			}
		}
		return false;
	}
}
