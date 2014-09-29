package com.TL.zeit.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.TL.zeit.MainActivity;
import com.TL.zeit.R;

public class Timeline extends Activity{

	private TextView textView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		textView1 = (TextView)findViewById(R.id.textView1);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		textView1.setText("You are in! baby!");
	}
	
}
