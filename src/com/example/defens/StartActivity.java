package com.example.defens;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		
		Button startButton = (Button)findViewById(R.id.button1);
		startButton.setOnClickListener(this);
		
		Button exitButton = (Button)findViewById(R.id.button2);
		exitButton.setOnClickListener(this);
		
		startService(new Intent(this, MyService.class));
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent = new Intent(this, Main.class);
			startActivity(intent);
			break;

		case R.id.button2:
			stopService(new Intent(this, MyService.class));
			finish();
			break;
			
		default:
			break;
		}
	}
}
