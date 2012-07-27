package com.kngames.gametest;

import java.util.ArrayList;

import com.kngames.gametest.cards.*;
import com.kngames.gametest.engine.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GamePanel.class.getSimpleName();
	
	//	the main game loop thread
	private GameLoopThread thread;

	private ArrayList<DrawObject> drawables;
    private DrawObject selected;
    //private ContentManager content;
	
	public GamePanel(Context context) {
		super(context);
		//	adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		//	initialize ContentManager
		//content = ContentManager.initContentManager(getResources());
		
		//	initialize DrawObject arraylist and fill it with 4 test objects
		drawables = new ArrayList<DrawObject>();
		drawables.add(new TestCard(120, 180, "Card 1", ""));
		drawables.add(new TestCard(120, 520, "Card 2", "This card has\nsome text."));
		drawables.add(new TestCard(420, 180, "Card 3", 
				"This card has\nnumerous lines\nof text, which is\nall manually\nmanaged."));
		drawables.add(new TestCard(420, 520, "Card 4", "This card does\nfuck-all.\nYAY!  :D"));
		
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
	//	handle user touch input
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//	calls the subroutine to detect which object if any was touched
			selected = detectTouchedObject((int)event.getX(), (int)event.getY());
			
			//	calls the handleDownTouch method of the selected object
			if (selected != null) selected.handleDownTouch(event);
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			//	calls the handleMoveTouch method of the selected object
			if (selected != null) selected.handleMoveTouch(event);
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			//	calls the handleUpTouch method of the selected object, then sets selected to null again
			if (selected != null) {
				selected.handleUpTouch(event);
				selected = null;
			}
		}
		return true;
	}
	
	//	updates all elements in this view
	public void update() {
		for (DrawObject d : drawables) {
			d.update();
		}
	}

	@Override
	//	draws the view
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
	
	//	takes a set of coordinates and returns the DrawObject touched at that location
    //	if point is within two or more object's bounding boxes, returns the one whose center is closest to touch location
    private DrawObject detectTouchedObject(float x, float y) {
    	ArrayList<DrawObject> touched = new ArrayList<DrawObject>();
    	//	brute-forces checks with all objects (to be replaced with more efficient code at a later time)
    	for (DrawObject d : drawables) {
    		if (d.isTouched(x, y))	touched.add(d);
    	}
    	
    	if (touched.size() == 1) return touched.get(0);
    	else if (touched.size() == 0) return null;
    	else {
    		DrawObject temp = touched.get(0);
    		double smallestDist = MovementComponent.distBetweenPoints(temp.X(), temp.Y(), x, y);
    		int logTemp = 0;
    		
    		//	test every touched object to find the object that has the closest center to touch location
    		for (int i = 1; i < touched.size(); i++) {
    			DrawObject temp2 = touched.get(i);
    			double tempDist = MovementComponent.distBetweenPoints(temp2.X(), temp2.Y(), x, y);
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
}
