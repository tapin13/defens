package com.example.defens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet {
	private Bitmap bmp;
	
	public int x;
	public int y;
	
	private int mSpeed = 25;
	
	public double angel;
	
	public int width;
	public int height;
	
	public GameView gameView;
	
	public Bullet(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		
		this.x = 0;
		this.y = this.gameView.getHeight() / 2;
		
		this.width = this.bmp.getWidth() / 2;
		this.height = this.bmp.getHeight() / 2;
		
		angel = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX));
	}
	
	private void update() {
		x += mSpeed * Math.cos(angel);
		y += mSpeed * Math.sin(angel);
	}
	
	public void onDraw(Canvas canvas) {
		update();
		canvas.drawBitmap(bmp, x, y, null);
	}
}
