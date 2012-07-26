package com.kngames.gametest.engine;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
//import android.util.Log;
import android.view.MotionEvent;

//	small class to hold a Bitmap, and location data of an object to be drawn
public class DrawObject implements Touchable {
	//	logging structures
	private static int classIds = 0;
	@SuppressWarnings("unused")
	private int id;
	//private static final String TAG = DrawObject.class.getSimpleName();
	
	//	main structures
	private Bitmap image;
	private MovementComponent move;
	private AnimationComponent anim;
	
	//	convenient half-calculations for length and width
	private int halfWidth;
	private int halfHeight;
	
	//	constructs a DrawObject with a specified Bitmap centered at a specified x-y coordinate
	public DrawObject(int x, int y, Bitmap draw) {
		id = classIds;
		classIds++;
		
		this.image = draw;
		
		halfWidth = image.getHeight() / 2;
		halfHeight = image.getHeight() / 2;
		
		move = new MovementComponent(x, y, 0, 0);
		anim = new AnimationComponent(move);
	}
	
	///
	///	Getters and Setters
	///
	
	public float X() { return move.getX(); }
	public float Y() { return move.getY(); }
	public void setX(float x) { move.setX(x); }
	public void setY(float y) { move.setY(y); }
	
	public Bitmap getImage() { return image; }
	public void setImage(Bitmap newImage) {
		this.image = newImage;
		halfWidth = image.getHeight() / 2;
		halfHeight = image.getHeight() / 2;
	}
	
	public int halfWidth() { return halfWidth; }
	public int halfHeight() { return halfHeight; }
	
	
	//	updates this DrawObject by first updating its AnimationComponent, then its MovementComponent
	public void update() {
		anim.update();
		move.update();
	}
	
	//	draws this DrawObject to the screen
	public void draw(Canvas canvas) {
		canvas.drawBitmap(image, move.getX() - halfWidth, move.getY() - halfHeight, null);
	}

	//	calculates whether a point is within the bounds of this object
	public boolean isTouched(float x, float y) {
		return
			x >= (move.getX() - image.getWidth() / 2) && 
			x <= (move.getX() + image.getWidth() / 2) &&
			y >= (move.getY() - image.getHeight() / 2) && 
			y <= (move.getY() + image.getHeight() / 2);
	}

	///
	///	Touch Events
	///
	public void handleDownTouch(MotionEvent event) {
		//	sends the DrawObject to a random part of the screen currently
		Random r = new Random();
		anim.moveToPoint(r.nextInt(600), r.nextInt(400), 3);
	}
	public void handleMoveTouch(MotionEvent event) {}
	public void handleUpTouch(MotionEvent event) {}
}