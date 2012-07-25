package com.kngames.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

//	small class to hold a Bitmap, and location data of an object to be drawn
public class DrawObject {
	public int x;
	public int y;
	private Bitmap image;
	
	private int halfWidth;
	private int halfHeight;
	
	public DrawObject(int x, int y, Bitmap draw) {
		this.x = x;
		this.y = y;
		this.image = draw;
		
		halfWidth = image.getHeight() / 2;
		halfHeight = image.getHeight() / 2;
	}
	
	public Bitmap getImage() { return image; }
	public void setImage(Bitmap newImage) {
		this.image = newImage;
		halfWidth = image.getHeight() / 2;
		halfHeight = image.getHeight() / 2;
	}
	
	public int halfWidth() { return halfWidth; }
	public int halfHeight() { return halfHeight; }
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(image, x - halfWidth, y - halfHeight, null);
	}
	
	public Point getLocation() { return new Point(x,y); }
	
	//	calculates whether a point is within the bounds of this object
	public boolean isTouched(int eventX, int eventY) {
		return
			eventX >= (x - image.getWidth() / 2) && 
			eventX <= (x + image.getWidth() / 2) &&
			eventY >= (y - image.getHeight() / 2) && 
			eventY <= (y + image.getHeight() / 2);
	}
}