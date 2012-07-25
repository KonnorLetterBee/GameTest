package com.kngames.gametest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GamePanel.class.getSimpleName();
	
	private GameLoopThread thread;

	private ArrayList<DrawObject> drawables;
    private DrawObject selected;
	
	public GamePanel(Context context) {
		super(context);
		//	adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		//	initialize DrawObject arraylist and fill it with 4 test objects
		drawables = new ArrayList<DrawObject>();
		drawables.add(new DrawObject(0, 0, loadScaledBitmap(R.drawable.red_square, 350, 350)));
		drawables.add(new DrawObject(120, 420, loadScaledBitmap(R.drawable.blue_square, 350, 350)));
		drawables.add(new DrawObject(420, 120, loadScaledBitmap(R.drawable.green_square, 350, 350)));
		drawables.add(new DrawObject(420, 420, loadScaledBitmap(R.drawable.yellow_square, 350, 350)));
		
		//	create the game loop thread
		thread = new GameLoopThread(getHolder(), this);
				
		//	make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//	surface is created, so game loop can be safely started
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		//	tell the thread to shut down and wait for it to finish (clean shutdown)
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				retry = false;
			} catch (InterruptedException e) { }
			//	try again shutting down the thread if retry hasn't been set to false (exception caught)
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//	calls the subroutine to detect which object if any was touched
			selected = detectTouchedObject((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (selected != null) {
				selected.x = (int)event.getX();
				selected.y = (int)event.getY();
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			selected = null;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (canvas != null) {
			//	draw black background
			canvas.drawColor(Color.BLACK);
			
			//	draw each object in the drawables array
			for (DrawObject d : drawables) {
				d.draw(canvas);
			}
		}
	}
	
	//	loads a bitmap from resources and scales it to the specified dimensions
	private Bitmap loadScaledBitmap(int id, int width, int height) {
		return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), id), width, height, false);
	}
	
	//	takes a set of coordinates and returns the DrawObject touched at that location
    //	if point is within two or more object's bounding boxes, returns the one whose center is closest to touch location
    private DrawObject detectTouchedObject(int x, int y) {
    	ArrayList<DrawObject> touched = new ArrayList<DrawObject>();
    	//	brute-forces checks with all objects (to be replaced with more efficient code at a later time)
    	for (DrawObject d : drawables) {
    		if (d.isTouched(x, y))	touched.add(d);
    	}
    	
    	if (touched.size() == 1) return touched.get(0);
    	else if (touched.size() == 0) return null;
    	else {
			//	*** this code is known to be buggy ***
    		Point touch = new Point(x, y);
    		DrawObject temp = touched.get(0);
    		double smallestDist = distBetweenPoints(temp.getLocation(), touch);
    		int logTemp = 0;
    		
    		//	test every touched object to find the object that has the closest center to touch location
    		for (int i = 1; i < touched.size(); i++) {
    			DrawObject temp2 = touched.get(i);
    			double tempDist = distBetweenPoints(temp2.getLocation(), touch);
    			Log.d(TAG, String.format("%d: %f vs %d: %f", logTemp, smallestDist, i, tempDist));
    			if (tempDist < smallestDist) {
    				smallestDist = tempDist;
    				temp = temp2;
    				logTemp = i;
    			}
    		}
    		return temp;
    	}
    }
    
    //	utility method to find the distance between two points in space
    private static double distBetweenPoints(Point a, Point b) {
    	return Math.sqrt((double)Math.exp(a.x - b.x) + (double)Math.exp(a.y - b.y));
    }
}
