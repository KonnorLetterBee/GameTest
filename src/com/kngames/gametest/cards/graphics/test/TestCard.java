package com.kngames.gametest.cards.graphics.test;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.kngames.gametest.engine.graphics.AnimationComponent;
import com.kngames.gametest.engine.graphics.DrawObject;
import com.kngames.gametest.engine.graphics.MovementComponent;

public class TestCard extends DrawObject {
	//	logging structures
	protected static int classIds = 0;
	protected int id;
	//private static final String TAG = TestCard.class.getSimpleName();
	
	private int width;
	private int height;
	private String name;
	private String[] text;
	
	//	constructs a DrawObject with a specified Bitmap centered at a specified x-y coordinate
	public TestCard(int x, int y, String name, String text) {
		super(x, y, null);
		id = classIds;
		classIds++;
		
		this.name = name;
		this.text = text.split("\\r?\\n");
		
		width = 300;
		height = 100;
		halfWidth = width / 2;
		halfHeight = height / 2;
		
		move = new MovementComponent(x, y, 0, 0);
		anim = new AnimationComponent(move);
	}
	
	//	draws this DrawObject to the screen
	private final int TITLE_TEXT_SIZE = 40;
	private final int EFFECT_TEXT_SIZE = 25;
	public void draw(Canvas canvas) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		canvas.drawRect(move.getX() - halfWidth, 
				move.getY() - halfHeight, 
				move.getX() + halfWidth, 
				move.getY() + halfHeight, paint);

		paint.setColor(Color.BLACK); 
		paint.setTextSize(TITLE_TEXT_SIZE);
		canvas.drawText(name, X() - halfWidth + 5, Y() - halfHeight + TITLE_TEXT_SIZE + 5, paint);
		paint.setTextSize(EFFECT_TEXT_SIZE);
		for (int i = 0; i < text.length; i++) {
			canvas.drawText(text[i], X() - halfWidth + 5, 
					Y() - halfHeight + TITLE_TEXT_SIZE + ((EFFECT_TEXT_SIZE + 5) * (i+1)) + 15, paint);
		}
		canvas.drawPoint(X(), Y(), paint);
	}

	///
	///	Touch Events
	///
	public void handleDownTouch(MotionEvent event) {
		//	sends the DrawObject to a random part of the screen currently
		Random r = new Random();
		anim.moveToPoint(r.nextInt(600), r.nextInt(400), 5);
	}
	public void handleMoveTouch(MotionEvent event) {}
	public void handleUpTouch(MotionEvent event) {}
}
