package com.sh_trisk.fxkz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sh_trisk.fxkz.bean.Sampler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity {
	private TextView title, from, to, left, right, leftone, lefttwo, leftstart, leftend, rightone, righttwo, rightstart, rightend ;
	private int left0=0, right0=0, right1 = 0, right2 = 0, left1 = 0, left2 = 0, id = 0;
	private float DENSITY = 0f;
	private String token, l1 = "", l2 = "", r1 = "", r2 = "";
	// private Map<Integer, ImageView> ID_IV;
	private SparseArray<ImageView> ID_IV;
	private SparseArray<Sampler> ID_OBJECT;
	private SparseIntArray ID_STATE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_info);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.infotitle);

		ID_IV = new SparseArray<ImageView>();
		ID_OBJECT = new SparseArray<Sampler>();
		ID_STATE = new SparseIntArray();
		Intent intent = getIntent();
		id = intent.getIntExtra("subproject_id", id);
		token = intent.getStringExtra("token");
		String Jsons[] = intent.getStringExtra("Json").split("A");
		DENSITY = InfoActivity.this.getResources().getDisplayMetrics().density;

		// 一二衬位置编号
		JSONObject jo;
		try {
			jo = new JSONObject(Jsons[0]);
			JSONArray ja = jo.getJSONArray("info");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject data = ja.getJSONObject(i);
				if (data.getInt("is_left") == 1 && data.getInt("is_primary") == 1) {
					l1 = data.getString("distance_code");
					left1 = data.getInt("distance");
				}
				if (data.getInt("is_left") == 0 && data.getInt("is_primary") == 1) {
					r1 = data.getString("distance_code");
					right1 = data.getInt("distance");
				}
				if (data.getInt("is_left") == 1 && data.getInt("is_primary") == 0) {
					l2 = data.getString("distance_code");
					left2 = data.getInt("distance");
				}
				if (data.getInt("is_left") == 0 && data.getInt("is_primary") == 0) {
					r2 = data.getString("distance_code");
					right2 = data.getInt("distance");
				}
			}
			System.out.println("left1" + left1 + "-right1" + right1 + "-left2" + left2 + "-right2" + right2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		left = (TextView) this.findViewById(R.id.left);
		right = (TextView) this.findViewById(R.id.right);
		title = (TextView) this.findViewById(R.id.infotitle);
		from = (TextView) this.findViewById(R.id.from);
		to = (TextView) this.findViewById(R.id.to);
		leftstart = (TextView)this.findViewById(R.id.leftstart);
		leftone = (TextView)this.findViewById(R.id.leftone);
		lefttwo = (TextView)this.findViewById(R.id.lefttwo);
		leftend = (TextView)this.findViewById(R.id.leftend);
		rightstart = (TextView)this.findViewById(R.id.rightstart);
		rightone = (TextView)this.findViewById(R.id.rightone);
		righttwo = (TextView)this.findViewById(R.id.righttwo);
		rightend = (TextView)this.findViewById(R.id.rightend);
		leftstart.setText(intent.getStringExtra("start"));
		rightstart.setText(intent.getStringExtra("start"));
		rightend.setText(intent.getStringExtra("end"));
		leftend.setText(intent.getStringExtra("end"));
		leftone.setText(l1);
		lefttwo.setText(l2);
		rightone.setText(r1);
		righttwo.setText(r2);
		right.setText("右线初衬：" + r1 + " 右线二衬：" + r2);
		left.setText("左线初衬：" + l1 + " 左线二衬：" + l2);
		title.setText(intent.getStringExtra("name"));
		from.setText(intent.getStringExtra("from"));
		to.setText(intent.getStringExtra("to"));

		// 添加使用中的采集点
		try {
			JSONObject bindsjs = new JSONObject(Jsons[1]);
			JSONArray binds = bindsjs.getJSONArray("info");
			JSONObject otherjs = new JSONObject(Jsons[2]);
			JSONArray other = otherjs.getJSONArray("info");
			int PID = 0, start = 0, dis = 1;
			for (int i = 0; i < binds.length(); i++) {
				JSONObject data = binds.getJSONObject(i);
				switch (data.getInt("is_left")) {
				case 1:
					PID = R.id.left1chen;
					start = left2;
					dis = left1-left2;
					break;
				case 0:
					PID = R.id.right1chen;
					start = right2;
					dis = right1-right2;
				}
				int id = data.getInt("horizontal_section_id");
				addImageView(data.getInt("distance") - start, id, PID, dis);
				
				for (int j = 0; j < other.length(); j++) {
					JSONObject data2 = other.getJSONObject(j);
					if (data2.getInt("id") == id) {
						ID_OBJECT.put(id,
								new Sampler(data2.getString("name"), data2.getString("vm_no"),
										data2.getString("dig_time"), data.getString("install_time"), null, "安全",
										data.getInt("code_no"), data.getDouble("pos_x"), 0, 0));
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 开启刷新通信任务
		new RefreshTask().execute("/api/devicelogicdatas?sub_project_id=" + id + "&x_auth_token=" + token);
	}

	private void addImageView(int x, int ID, int parentID, int distance) {
		System.out.println("x=" +x);
		System.out.println("density" +DENSITY);
		System.out.println("x*800*DENSITY=" +x*800 * DENSITY);
		System.out.println("addImageView" +(int) (x*800 * DENSITY/distance));
		System.out.println("MARGINTOP=" +10*DENSITY);
		ImageView iv = new ImageView(InfoActivity.this);
		iv.setTag(ID);
		RelativeLayout rl = (RelativeLayout) this.findViewById(parentID);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) (30 * DENSITY), (int) (30 * DENSITY));
		lp.setMargins((int) (x*800 * DENSITY/distance), (int) (10 * DENSITY), 0, 0);
		iv.setLayoutParams(lp);
		rl.addView(iv);
		ID_IV.put(ID, iv);
		ID_STATE.put(ID, 1);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = (Integer) v.getTag();
				new ClickTask().execute(id);
			}
		});
	}

	private class RefreshTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = HttpUtils.getJson(params[0]);
			if (result.startsWith("A")) {
				return result;
			}
			StringBuilder sb = new StringBuilder();
			try {
				JSONObject jo = new JSONObject(result);
				JSONArray ja = jo.getJSONArray("info");
				for (int i = 0; i < ja.length() - 1; i++) {
					JSONObject data = ja.getJSONObject(i);
					int hsid = data.getInt("horizontal_section_id"), status = data.getInt("status");
					sb.append(hsid).append("A").append(status).append("B");
					ID_OBJECT.get(hsid).setCurrent_1(data.getDouble("current_1"));
					ID_OBJECT.get(hsid).setCurrent_2(data.getDouble("current_2"));
					ID_OBJECT.get(hsid).setCreate_time(data.getString("create_time"));
					System.out.println(hsid+"current_1"+data.getDouble("current_1")+"current_2"+data.getDouble("current_2")+"create_time"+data.getString("create_time"));
				}
				JSONObject data = ja.getJSONObject(ja.length() - 1);
				sb.append(data.getInt("horizontal_section_id")).append("A").append(data.getInt("status"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "A信息获取错误";
			}
			return sb.toString();
		}

		@Override
		protected void onPostExecute(String x) {
			// TODO Auto-generated method stub
			super.onPostExecute(x);
			if (x.startsWith("A")) {
				Toast.makeText(InfoActivity.this, x.substring(1), Toast.LENGTH_SHORT).show();
				return;
			}
			String one[] = x.split("B");
			for (int i = 0; i < one.length; i++) {
				String two[] = one[i].split("A");
				int hid = Integer.parseInt(two[0]);
				int status = Integer.parseInt(two[1]);
				if (status != ID_STATE.get(hid)) {
					switch (status) {
					case 0:
						ID_IV.get(hid).setImageResource(R.drawable.normal);
						ID_OBJECT.get(hid).setStatus("安全");
						break;
					case 1:
						ID_IV.get(hid).setImageResource(R.drawable.warning);
						ID_OBJECT.get(hid).setStatus("预警");
						break;
					case 2:
						ID_IV.get(hid).setImageResource(R.drawable.alert);
						ID_OBJECT.get(hid).setStatus("报警");
					}
					ID_STATE.put(hid, status);
				}
			}
			Toast.makeText(InfoActivity.this, "获取信息完毕", Toast.LENGTH_SHORT).show();
		}
	}

	private class ClickTask extends AsyncTask<Integer, Void, String> {

		@Override
		protected String doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int thishid = params[0];
			Intent intent2 = new Intent(InfoActivity.this,Details.class);
			Sampler s = ID_OBJECT.get(thishid);
			intent2.putExtra("vm_no", s.getVm_no());
			intent2.putExtra("dig_time", s.getDig_time());
			intent2.putExtra("install_time", s.getInstall_time());
			intent2.putExtra("name", s.getName());
			intent2.putExtra("create_time", s.getCreate_time());
			intent2.putExtra("status", s.getStatus());
			intent2.putExtra("code_no", s.getCode_no());
			intent2.putExtra("pos_x", s.getPos_x());
			intent2.putExtra("current_1", s.getCurrent_1());
			intent2.putExtra("current_2", s.getCurrent_2());
			System.out.println("code_no:"+s.getCode_no());
			System.out.println("pos_x:"+s.getPos_x());
			System.out.println("current_1:"+s.getCurrent_1());
			startActivity(intent2);
			return null;
		}

	}
}
