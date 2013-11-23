package com.example.defens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
	private GameThread mThread;
	
	public int shotX;
	public int shotY;
	
	private boolean running = false;
	
	private List<Bullet> ball = new ArrayList<Bullet>();
	
	private Player player; 
	
	private List<Enemy> enemy = new ArrayList<Enemy>();
	Bitmap enemies;
	
	private Thread thread = new Thread(this);
	
	private SoundPool sounds;
	private int sExplosion;
	
	public class GameThread extends Thread {
		private GameView view;
		
		public GameThread(GameView view) {
			this.view = view;
		}
		
		public void setRunning(boolean run) {
			running = run;
		}
		
		public void run() {
			while(running) {
				Canvas canvas = null;
				
				try {
					canvas = view.getHolder().lockCanvas();
					synchronized (view.getHolder()) {
						onDraw(canvas);
						testCollision();
					}
				} catch (Exception e) {
					
				} finally {
					if(canvas != null) {
						view.getHolder().unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
	
	public GameView(Context context) {
		super(context);
		
		thread.start();
		
		mThread = new GameThread(this);
		
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		sExplosion = sounds.load(context, R.raw.explosion, 1);
		
		getHolder().addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				mThread.setRunning(false);
				while(retry) {
					try {
						mThread.join();
						retry = false;
					} catch (Exception e) {
					}
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				mThread.setRunning(true);
				mThread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		});
		
		enemies = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);

		player = new Player(this, BitmapFactory.decodeResource(getResources(), R.drawable.ninja));
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		
		player.onDraw(canvas);
		
		Iterator<Bullet> j = ball.iterator();
		while(j.hasNext()) {
			Bullet b = j.next();
			if(b.x >= 1000 || b.y <=1000) {
				b.onDraw(canvas);
			} else {
				j.remove();
			}
		}
		
		Iterator<Enemy> i = enemy.iterator();
		while(i.hasNext()) {
			Enemy e = i.next();
			if(e.x > (e.width * -1)) {
				e.onDraw(canvas);
			} else {
				i.remove();
			}
		}
	}
	
	public Bullet createSprite(int resource) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		return new Bullet(this, bmp);
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		shotX = (int) e.getX();
		shotY = (int) e.getY();
		
		if(e.getAction() == MotionEvent.ACTION_DOWN) {
			ball.add(createSprite(R.drawable.bomb));
		}
		
		return true;
	}

	@Override
	public void run() {
		while(true) {
			Random rnd = new Random();
			try {
				Thread.sleep(rnd.nextInt(2000));
				enemy.add(new Enemy(this, enemies));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void testCollision() {
		Iterator<Bullet> b = ball.iterator();
		while(b.hasNext()) {
			Bullet balls = b.next();
			Iterator<Enemy> i = enemy.iterator();
			while(i.hasNext()) {
				Enemy enemies = i.next();
				if((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f)
						&& (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height) /2f)) {
					sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
					i.remove();
					b.remove();
				}
			}
		}
	}
}
