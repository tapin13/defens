package com.example.defens;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player {
	GameView gameView;
	
	Bitmap bmp;
	
	int x;
	
	public Player(GameView gameView, Bitmap bmp) {
		this.gameView = gameView;
		this.bmp = bmp;
		
		this.x = 0;
	}
	
	public void onDraw(Canvas c) {
		c.drawBitmap(bmp, x, c.getHeight() / 2, null);
	}
}
