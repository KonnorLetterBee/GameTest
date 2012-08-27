package com.kngames.gametest.engine.graphics;

import android.util.FloatMath;

//	MovementComponent - 
//
//	provides a structure for position and velocity of an object drawn on the screen

public class MovementComponent {
	private float xPos = 0;	//	x position
	private float yPos = 0;	//	y position
	private float xVel = 0;	//	x velocity
	private float yVel = 0;	//	y velocity

	//	construct a default MovementComponent with position (0,0) and velocity 0
	public MovementComponent() { }

	//	construct a MovementComponent
	public MovementComponent(float xp, float yp, float xv, float yv) {
		this.xPos = xp;
		this.yPos = yp;
		this.xVel = xv;
		this.yVel = yv;
	}

	///
	///	Getters and Setters
	///
	
	public float x() { return xPos; }
	public void setX(float xp) { this.xPos = xp; }
	public float y() { return yPos; }
	public void setY(float yp) { this.yPos = yp; }
	
	public float xVel() { return xVel; }
	public void setXVel(float xv) { this.xVel = xv; }
	public void addXVel(float xv) { this.xVel += xv; }
	public float yVel() { return yVel; }
	public void setYVel(float yv) { this.yVel = yv; }
	public void addYVel(float yv) { this.yVel += yv; }
	
	public void invertXDir() { xVel *= -1; }
	public void invertYDir() { yVel *= -1; }
	
	//	updates the movement/location of this component
	public void update() {
		xPos += xVel;
		yPos += yVel;
	}
	
	//	utility method to find the distance between two points in space
    public static float distBetweenPoints(float x1, float y1, float x2, float y2) {
    	return FloatMath.sqrt((float)Math.pow(x1-x2, 2) + (float)Math.pow(y1-y2, 2));
    }
    
    //	utility method to get the speed (not velocity) of this component
    public float speed() {
		return FloatMath.sqrt((xVel*xVel) + (yVel*yVel));
	}
}