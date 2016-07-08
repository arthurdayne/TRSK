package com.sh_trisk.fxkz;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private List<Project> list;
//	private ListView listview;
	
	public MyAdapter(List<Project> list) {
		super();
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		if(convertView == null){
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, null);
		}else{
			view = convertView;
		}
		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		TextView tv = (TextView) view.findViewById(R.id.projectname);
		Project p = list.get(position);
		tv.setText(p.getName());
		System.out.println(p.getName()+"position"+position);
		if(p.getType()==1){
			iv.setImageResource(R.drawable.tunneling);
		}else{
			iv.setImageResource(R.drawable.sideslope);
		}
		return view;
	}

}
