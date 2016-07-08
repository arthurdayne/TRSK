package com.sh_trisk.fxkz;

import org.json.JSONException;
import org.json.JSONObject;

import com.sh_trisk.fxkz.bean.Token;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText user_name, password;
	private Button login;
	private String token;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		user_name = (EditText) this.findViewById(R.id.user_name);
		password = (EditText) this.findViewById(R.id.password);
		login = (Button) this.findViewById(R.id.login);
		intent = new Intent(LoginActivity.this, ProjectList.class);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String info  = "/api/doLogin?use_token=1&user_name="+user_name.getText().toString().trim()+"&password="+password.getText().toString().trim();
				new GetTokenTask().execute(info);
			}
		});
//		DisplayMetrics metric = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metric);
//		int width = metric.widthPixels;
//		int height = metric.heightPixels;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class GetTokenTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String loginJsonString = HttpUtils.getJson(params[0]);
			if(loginJsonString.startsWith("A")){
				return loginJsonString;
			}
			JSONObject jo = null;
			String info = "";
			try {
				jo = new JSONObject(loginJsonString);
				info = jo.getString("info");
				if(jo.getInt("status")==0){
					return "A"+info;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "A‘À––¥ÌŒÛ";
			} 
			System.out.println(info);
			token = info;
			intent.putExtra("token", token);
			String queryString = "/api/subprojects?x_auth_token="+token;
			String projectsJson = HttpUtils.getJson(queryString);
//			System.out.println(projectsJson);
			return projectsJson;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.startsWith("A")){
				Toast.makeText(LoginActivity.this, result.substring(1), Toast.LENGTH_SHORT).show();
			}else{
				intent.putExtra("projectsJson", result);
//				Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
				startActivity(intent);
				LoginActivity.this.finish();
			}
		}
	}
}
