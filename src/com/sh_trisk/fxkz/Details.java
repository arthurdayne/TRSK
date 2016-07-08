package com.sh_trisk.fxkz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Details extends Activity {

	private TextView vm_no, name, dig_time, code_no, pos_x, install_time, status, current1, current2, create_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		Intent intent = getIntent();
		vm_no = (TextView)this.findViewById(R.id.vm_no);
		name = (TextView)this.findViewById(R.id.name);
		dig_time = (TextView)this.findViewById(R.id.dig_time);
		code_no = (TextView)this.findViewById(R.id.code_no);
		pos_x = (TextView)this.findViewById(R.id.pos_x);
		install_time = (TextView)this.findViewById(R.id.install_time);
		status = (TextView)this.findViewById(R.id.status);
		current1 = (TextView)this.findViewById(R.id.current1);
		current2 = (TextView)this.findViewById(R.id.current2);
		create_time = (TextView)this.findViewById(R.id.create_time);
		vm_no.setText(intent.getStringExtra("vm_no"));
		name.setText(intent.getStringExtra("name"));
		dig_time.setText(intent.getStringExtra("dig_time"));
		code_no.setText(String.valueOf(intent.getIntExtra("code_no", 0)));
		pos_x.setText(String.valueOf(intent.getDoubleExtra("pos_x", 0)));
		install_time.setText(intent.getStringExtra("install_time"));
		status.setText(intent.getStringExtra("status"));
		current1.setText(String.valueOf(intent.getDoubleExtra("current_1", 0)));
		current2.setText(String.valueOf(intent.getDoubleExtra("current_2", 0)));
		create_time.setText(intent.getStringExtra("create_time"));
		
		
	}

}
