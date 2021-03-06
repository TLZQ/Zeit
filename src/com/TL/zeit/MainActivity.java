package com.TL.zeit;


import java.io.InputStream;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.TL.zeit.auth.AccessTokenKeeper;
import com.TL.zeit.auth.Constants;
import com.TL.zeit.ui.Timeline;
import com.TL.zeit.R;
import com.sina.weibo.sdk.exception.WeiboException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.TL.zeit.ui.Timeline;


public class MainActivity extends Activity implements OnClickListener {

	
	/** 微博 Web 授权类，提供登陆等功能  */
    private WeiboAuth mWeiboAuth;
    
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    public static Oauth2AccessToken mAccessToken;
    
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;
	
    private ImageButton loginButton ;
       
    
    private ImageView ivGuidePicture,guide_background;    
      
   
    private Bitmap[] pictures;   
      
    private Animation[] animations;  
    
 
    private int currentItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
       
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                	WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        
     // 创建微博实例
        mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
    
        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        
        guide_background = (ImageView)findViewById(R.id.guide_background);
        guide_background.setImageResource(R.drawable.guide_background);    
        
/*        initBackgroundChange();			*/
        
        if (mAccessToken.isSessionValid()){
        		enterTimeline();
		}else {
			
		}
        
		initView();  
        
        initData();
     
    }

   
/*   private void initBackgroundChange() {
		// TODO Auto-generated method stub
    	RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.BackgroundChange);
		AnimationView animationView=new AnimationView(this);
		relativeLayout.addView(animationView);
	}

    public class AnimationView extends View{
		public AnimationView(Context context) {
			super(context);
		    ObjectAnimator objectAnimator=
		    (ObjectAnimator) AnimatorInflater.loadAnimator(MainActivity.this, R.anim.coloranimation);
		    objectAnimator.setEvaluator(new ArgbEvaluator());
		    objectAnimator.setTarget(this);
		    objectAnimator.start();
		}
		
	}          */

    private void enterTimeline(){
    	Intent intent = new Intent(MainActivity.this, Timeline.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    }
    
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                
                
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                Toast.makeText(MainActivity.this, 
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                enterTimeline();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, 
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(MainActivity.this, 
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
       
	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
  	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
    
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
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
		
        ivGuidePicture = (ImageView) findViewById(R.id.guide_picture);    
          
       
        loginButton = (ImageButton) findViewById(R.id.guide_loginButton);       
    
       
        pictures = new Bitmap[] {  	BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_china),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_arbic),  
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_english),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_france),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_german),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_japan),
        							BitmapFactory.decodeResource(getResources(),R.drawable.guide_background_klingon)};    
    
       
        animations = new Animation[] { AnimationUtils.loadAnimation(this, R.anim.guide_fade_in_scale),    
                                       AnimationUtils.loadAnimation(this, R.anim.guide_fade_out) };   
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		 
        loginButton.setOnClickListener(this);  
                       
   
        animations[0].setDuration(1500);    
        animations[1].setDuration(1500);    
       
       
    
        animations[0].setAnimationListener(new GuideAnimationListener(0));    
        animations[1].setAnimationListener(new GuideAnimationListener(1));    
         
       
          
      
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
		 mWeiboAuth.anthorize(new AuthListener());
         // 或者使用：mWeiboAuth.authorize(new AuthListener(), Weibo.OBTAIN_AUTH_TOKEN);
		 mSsoHandler = new SsoHandler(MainActivity.this, mWeiboAuth);
         mSsoHandler.authorize(new AuthListener());    
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
