package com.kngames.gametest.engine;

public class MovementComponent {
	private float xPos = 0;	//	x position
	private float yPos = 0;	//	y position
	private float xVel = 0;	//	x velocity
	private float yVel = 0;	//	y velocity

	public MovementComponent() { }

	public MovementComponent(float xp, float yp, float xv, float yv) {
		this.xPos = xp;
		this.yPos = yp;
		this.xVel = xv;
		this.yVel = yv;
	}

	public float getXPos() { return xPos; }
	public void setXPos(float xp) { this.xPos = xp; }
	public float getYPos() { return yPos; }
	public void setYPos(float yp) { this.yPos = yp; }
	
	public float getXVel() { return xVel; }
	public void setXVel(float xv) { this.xVel = xv; }
	public float getYVel() { return yVel; }
	public void setYVel(float yv) { this.yVel = yv; }

	public void toggleXDir() { xVel *= -1; }
	public void toggleYDir() { yVel *= -1; }
}