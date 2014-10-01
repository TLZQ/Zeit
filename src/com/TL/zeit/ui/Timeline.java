package com.TL.zeit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TL.zeit.R;

public class Timeline extends Activity{

	private TextView textView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		

        
//   123     initView();

		
		
	/*	textView1 = (TextView)findViewById(R.id.textView1);
		initView();   */
}



	private void initView() {
		// TODO Auto-generated method stub
		textView1.setText("You are in! baby!");
	}
	
}
