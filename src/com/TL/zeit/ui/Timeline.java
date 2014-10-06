package com.TL.zeit.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TL.zeit.R;
/**
 * 这是Timeline主界面的类
 * @author Troy Liu
 *
 */
public class Timeline extends Activity{

	private TextView textView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
	}
}
