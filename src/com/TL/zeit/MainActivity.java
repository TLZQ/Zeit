package com.TL.zeit;


import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

	
	/**  
	 * 功能描述：导引界面(每张图片都执行的动画顺序，渐现、放大和渐隐，结束后切换图片和文字  
	 * 又开始执行 渐现、放大和渐隐,当最后一张执行完渐隐，切换到第一张，从而达到循环效果)  
	 */  
	
	//登录按钮  
    private ImageButton loginButton ;
       
    //显示图片的ImageView组件   
    private ImageView ivGuidePicture,guide_background;    
      
    //要展示的一组图片资源   
    private Bitmap[] pictures;   
      
    //每张展示图片要执行的一组动画效果  
    private Animation[] animations;  
    
    
    //当前执行的是第几张图片（资源索引）  
    private int currentItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //设置无标题  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //设置全屏  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                	WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
      
        guide_background = (ImageView)findViewById(R.id.guide_background);
        guide_background.setImageResource(R.drawable.guide_background);
        
        
		initView();  
        
        initData();
        
    }

   
    
    public static Bitmap readBitMap(Context context, int resId){
    	BitmapFactory.Options opt = new BitmapFactory.Options(); 
    	opt.inPreferredConfig = Bitmap.Config.RGB_565; 
    	opt.inPurgeable = true; 
    	opt.inInputShareable = true; 
    	InputStream is = context.getResources().openRawResource(resId); 
    	return BitmapFactory.decodeStream(is,null,opt); 
    }
    
    private void initView() {
		// TODO Auto-generated method stub
		//实例化ImageView引导图片  
        ivGuidePicture = (ImageView) findViewById(R.id.guide_picture);    
          
        //实例化按钮  
        loginButton = (ImageButton) findViewById(R.id.guide_loginButton);       
    
        //实例化引导图片数组  
        pictures = new Bitmap[] {  	BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_china),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_arbic),  
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_english),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_france),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_german),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_japan),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_klingon)};    
    
        //实例化动画效果数组  
        animations = new Animation[] { AnimationUtils.loadAnimation(this, R.anim.guide_fade_in_scale),    
                                       AnimationUtils.loadAnimation(this, R.anim.guide_fade_out) };   
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		//给按钮设置监听  
        loginButton.setOnClickListener(this);  
                       
        //给每个动画效果设置播放时间  
        animations[0].setDuration(1500);    
        animations[1].setDuration(1500);    
       
       
    
        //给每个动画效果设置监听事件  
        animations[0].setAnimationListener(new GuideAnimationListener(0));    
        animations[1].setAnimationListener(new GuideAnimationListener(1));    
         
       
          
        //设置图片动画初始化默认值为0  
        ivGuidePicture.setImageBitmap(pictures[currentItem]);    
        ivGuidePicture.startAnimation(animations[0]);   
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 switch (v.getId()) {          
         case R.id.guide_loginButton:        
        	 Toast.makeText(this, "请登录微博", Toast.LENGTH_SHORT).show();
             break;    
         default:    
             break;    
         }    
	}
	
	
	class GuideAnimationListener implements AnimationListener{
		private int index;    
	    
        public GuideAnimationListener(int index) {    
            this.index = index;    
        }    
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		//重写动画结束时的监听事件，实现了动画循环播放的效果  
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if (index < (animations.length - 1)) {    
                ivGuidePicture.startAnimation(animations[index + 1]);    
            } else {    
                currentItem++;    
                if (currentItem > (pictures.length - 1)) {    
                    currentItem = 0;    
                }    
                ivGuidePicture.setImageBitmap(pictures[currentItem]);    
                ivGuidePicture.startAnimation(animations[0]);    
            }    
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
