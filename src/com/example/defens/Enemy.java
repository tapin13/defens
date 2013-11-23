package com.example.defens;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy {
	public int x;
	public int y;
	
	public int speed;
	
	public int width;
	public int height;
	
	public GameView gameView;
	public Bitmap bmp;
	
	public Enemy(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		
		Random rnd = new Random();
		
		this.x = this.gameView.getWidth() - this.bmp.getWidth();
		this.y = rnd.nextInt((int)(this.gameView.getHeight() - this.bmp.getHeight()));

		this.speed = 1 + rnd.nextInt(9);
		
		this.width = this.bmp.getWidth() - 10;
		this.height = this.bmp.getHeight() - 10;
	}
	
	public void update() {
		x -= speed;
	}
	
	public void onDraw(Canvas c) {
		update();
		c.drawBitmap(bmp, x, y, null);
	}
}
