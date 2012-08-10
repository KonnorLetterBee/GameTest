package com.kngames.gametest.engine.graphics;

import android.util.FloatMath;

//	AnimationComponent - 
//
//	provides a framework for keeping track of animations (speeds, destinations, velocity changes, etc.)
//	currently only does movement towards a single destination point

public class AnimationComponent {
	private MovementComponent moverRef;
	
	private float xTar;
	private float yTar;
	private boolean targetActive;

	//	creates a default AnimationComponent, passing in the MovementComponent that this AnimationComponent will modify
	public AnimationComponent(MovementComponent move) {
		moverRef = move;
	}
	
	//	sets the associated MovementComponent to move at a set speed towards a specified point
	public void moveToPoint(float xTar, float yTar, float speed) {
		float theta = (float) Math.atan((yTar - moverRef.getY())/(xTar - moverRef.getX()));
		float xComp = (float) (speed * FloatMath.cos(theta));
		float yComp = (float) (speed * FloatMath.sin(theta));
		
		changeTarget(xTar, yTar);
		if (xTar - moverRef.getX() < 0) {
			moverRef.setXVel(-xComp);
			moverRef.setYVel(-yComp);
		} else {
			moverRef.setXVel(xComp);
			moverRef.setYVel(yComp);
		}
	}
	
	//	changes the current target point
	public void changeTarget(float newXTar, float newYTar) {
		targetActive = true;
		xTar = newXTar;
		yTar = newYTar;
	}
	
	//	updates this animation component
	public void update() {
		if (targetActive) {
			//	if the distance to the target is less than the speed, place the object at the target and stop it from moving
			if (checkTargetProx()) {
				targetActive = false;
				moverRef.setXVel(0);
				moverRef.setYVel(0);
				moverRef.setX(xTar);
				moverRef.setY(yTar);
			}
		}
	}
	
	//	checks the proximity of the object to the target point
	//	returns true if the object's current speed is less than the distance to the target
	private boolean checkTargetProx() {
		return MovementComponent.distBetweenPoints(xTar, yTar, moverRef.getX(), moverRef.getY()) < moverRef.speed();
	}
}
