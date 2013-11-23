package com.example.defens;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
	MediaPlayer player;
	
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public void onCreate() {
		player = MediaPlayer.create(this, R.raw.bg);
		player.setLooping(true);
	}
	
	public void onDestroy() {
		player.stop();
	}
	
	public void onStart(Intent intent, int startid) {
		player.start();
	}
}
