package com.kngames.gametest;

import java.util.ArrayList;

import com.kngames.gametest.cards.graphics.*;
import com.kngames.gametest.cards.graphics.test.*;
import com.kngames.gametest.engine.graphics.*;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.graphics.DeckZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GamePanel.class.getSimpleName();
	
	//	the main game loop thread
	private GameLoopThread thread;
	
	private ArrayList<DrawObject> drawables;
    private DrawObject selected;
    private GameZone selectedZone;
    
    //private ContentManager content;
	private ZoneManager zones;
	private Game game;
    
	@SuppressWarnings("deprecation")
	public GamePanel(Context context) {
		super(context);
		//	adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		//	initialize ContentManager
		//content = ContentManager.initContentManager(getResources());
		
		//	initialize DrawObject arraylist and fill it with 4 test objects
		drawables = new ArrayList<DrawObject>();
		/*
		drawables.add(new TestCard(120, 180, "Card 1", ""));
		drawables.add(new TestCard(120, 520, "Card 2", "This card has\nsome text."));
		drawables.add(new TestCard(420, 180, "Card 3", 
				"This card has\nnumerous lines\nof text, which is\nall manually\nmanaged."));
		drawables.add(new TestCard(420, 520, "Card 4", "This card does\nfuck-all.\nYAY!  :D"));
		*/

		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		GameZone.initZones(display.getWidth(), display.getHeight());
		
		//	initialize ZoneManager and Game, then set ZoneManager's Game to the instantiated Game
		zones = ZoneManager.initZoneManager();
		game = Game.startGame(new CharacterCard[] {CardData.Characters[0]}, null);
		zones.setGame(game);
		
		TestZone a = new TestZone(0, 0, GameZone.TOP_LEFT, 0.75f, 0.48f, GameZone.PRESERVE_HEIGHT);
		TestZone b = new TestZone(0, display.getHeight(), GameZone.BOTTOM_LEFT, 0.75f, 0.48f, GameZone.PRESERVE_HEIGHT);
		DeckZone d = new DeckZone(display.getWidth(), display.getHeight(), GameZone.BOTTOM_RIGHT, 0.75f, 0.48f, GameZone.PRESERVE_HEIGHT);
		
		a.addData("data 1");
		b.addData("data 2");
		b.addData("data 3");
		
		a.setOtherZone(b);
		b.setOtherZone(a);
		
		d.setTestTag("zone_b");
		
		zones.addZone("zone_a", a);
		zones.addZone("zone_b", b);
		zones.addZone("zone_d", d);
		
		Log.d(TAG, String.format("zone_a (%s) id: %d", a.getName(), a.getId()));
		Log.d(TAG, String.format("zone_b (%s) id: %d", b.getName(), b.getId()));
		Log.d(TAG, String.format("zone_d (%s) id: %d", d.getName(), d.getId()));
		
		zones.postInit();
		
		//	create the game loop thread
		thread = new GameLoopThread(getHolder(), this);
				
		//	make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void pauseGameThread() {
		
	}
	
	public void resumeGameThread() {
		
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

	public void surfaceCreated(SurfaceHolder holder) {
		//	surface is created, so game loop can be safely started
		thread.setRunning(true);
		thread.start();
	}

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
			selectedZone = detectTouchedZone((int)event.getX(), (int)event.getY());
			
			//	calls the handleDownTouch method of the selected object
			if (selected != null) selected.handleDownTouch(event);
			if (selectedZone != null) selectedZone.handleDownTouch(event);
			
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
		zones.updateZones();
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
			
			//	draw each zone in the zones array
			GameZone[] allZones = zones.getAllZones();
			for (GameZone z : allZones) {
				z.draw(canvas);
			}
			
			displayFps(canvas, avgFps);
		}
	}
	
	private String avgFps;
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}
	
	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
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
    
    //	takes a set of coordinates and returns the DrawObject touched at that location
    //	if point is within two or more object's bounding boxes, returns the one whose center is closest to touch location
    private GameZone detectTouchedZone(float x, float y) {
    	ArrayList<GameZone> touched = new ArrayList<GameZone>();
    	//	brute-forces checks with all objects (to be replaced with more efficient code at a later time)
    	GameZone[] allZones = zones.getAllZones();
    	for (GameZone z : allZones) {
    		if (z.isTouched(x, y))	touched.add(z);
    	}
    	
    	if (touched.size() == 1) return touched.get(0);
    	else if (touched.size() == 0) return null;
    	else {
    		GameZone temp = touched.get(0);
    		double smallestDist = MovementComponent.distBetweenPoints(temp.centerX(), temp.centerY(), x, y);
    		int logTemp = 0;
    		
    		//	test every touched object to find the object that has the closest center to touch location
    		for (int i = 1; i < touched.size(); i++) {
    			GameZone temp2 = touched.get(i);
    			double tempDist = MovementComponent.distBetweenPoints(temp2.centerX(), temp2.centerY(), x, y);
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
