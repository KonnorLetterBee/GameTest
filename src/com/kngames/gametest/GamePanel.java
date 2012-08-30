package com.kngames.gametest;

import java.util.ArrayList;

import com.kngames.gametest.cards.graphics.*;
import com.kngames.gametest.engine.ContentManager;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.redata.data.GameData;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.graphics.*;

import android.content.Context;
import android.graphics.Bitmap;
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
	
    private GameZone selectedZone;
    
    //private int screenWidth;
    //private int screenHeight;
    
    private ContentManager content;
	private REZoneManager zoneManager;
	private Game game;
    
	private float margin = 0.02f;
	
	@SuppressWarnings("deprecation")
	public GamePanel(Context context) {
		super(context);
		//	adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		//	initialize ContentManager
		content = ContentManager.initContentManager(getResources());
		Bitmap card_back = content.getBitmap(R.drawable.card_back_small);

		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		GameZone.initZones(display.getWidth(), display.getHeight());
		
		//	initialize ZoneManager and Game, then set ZoneManager's Game to the instantiated Game
		zoneManager = REZoneManager.initREZoneManager();
		game = Game.startGame(context, new CharacterCard[] {GameData.Characters[0]}, GameData.testScenario);
		zoneManager.setGame(game);
		
		DeckZone deck = new DeckZone(1.0f, 0.95f, GameZone.BOTTOM_RIGHT, 
				0.75f, 0.29f, GameZone.PRESERVE_HEIGHT, 0);
		GameStateZone message = new GameStateZone(0.01f, 0.9875f, GameZone.BOTTOM_LEFT,
				deck.percLeft(), 0.025f, GameZone.STRETCH, 0);
		
		DiscardZone discard = new DiscardZone(1.0f, deck.percTop() - 0.02f, GameZone.BOTTOM_RIGHT, 
				0.75f, 0.29f, GameZone.PRESERVE_HEIGHT, 0);
		BuyCardButtonZone shop = new BuyCardButtonZone(1.0f, discard.percTop() - margin, GameZone.BOTTOM_RIGHT, 
				deck.percWidth(), 0.15f, GameZone.STRETCH, 0);
		ExploreButtonZone explore = new ExploreButtonZone(shop.percLeft() - 0.02f, discard.percTop() - margin, GameZone.BOTTOM_RIGHT, 
				deck.percWidth(), 0.15f, GameZone.STRETCH, 0);
		EndTurnButtonZone end = new EndTurnButtonZone(1.0f, shop.percTop() - 0.02f, GameZone.BOTTOM_RIGHT, 
				deck.percWidth(), shop.percHeight(), GameZone.STRETCH, 0);
		ActionButtonZone action = new ActionButtonZone(shop.percLeft() - 0.02f, shop.percTop() - 0.02f, GameZone.BOTTOM_RIGHT, 
				deck.percWidth(), shop.percHeight(), GameZone.STRETCH, 0);
		
		REDeckViewZone hand = new REDeckViewZone(0f, deck.percTop(), GameZone.TOP_LEFT, 
				deck.percLeft() - 0.01f, deck.percHeight(), GameZone.STRETCH, 0, card_back, 
				new REDeckViewZone.REViewZoneCallback() {
					public void onNonPINDownTouch(int index) { game.getVisiblePlayer().playCard(index); }
					public REDeck getCompareStack() { return game.getActivePlayer().hand(); }
				});
		REDeckViewZone inPlay = new REDeckViewZone(0f, discard.percBottom(), GameZone.BOTTOM_LEFT,
				hand.percWidth(), discard.percHeight(), GameZone.STRETCH, 0, card_back, 
				new REDeckViewZone.REViewZoneCallback() {
					public void onNonPINDownTouch(int index) { }
					public REDeck getCompareStack() { return game.getActivePlayer().inPlay(); }
				});
		REDeckViewZone discardView = new REDeckViewZone(0.15f, discard.percBottom() - 0.04f, 1.01f, discard.percBottom() - 0.04f, GameZone.BOTTOM_LEFT,
				hand.percWidth() - 0.15f, discard.percHeight() - 0.08f, GameZone.STRETCH, 10, card_back, 
				new REDeckViewZone.REViewZoneCallback() {
					public void onNonPINDownTouch(int index) { }
					public REDeck getCompareStack() { return game.getActivePlayer().discard(); }
				});
		discardView.setActive(false);
		
		InfoZone info = new InfoZone (0f, 0f, GameZone.TOP_LEFT, 0.3f, inPlay.percTop() - 0.02f, GameZone.STRETCH, 0);
		
		zoneManager.addZone("deck", deck);
		zoneManager.addZone("state_message", message);
		zoneManager.addZone("hand", hand);
		zoneManager.addZone("in_play", inPlay);
		zoneManager.addZone("discard", discard);
		zoneManager.addZone("discard_view", discardView);
		zoneManager.addZone("info_button", info);
		zoneManager.addZone("shop_button", shop);
		zoneManager.addZone("explore_button", explore);
		zoneManager.addZone("action_button", action);
		zoneManager.addZone("end_turn_button", end);
		zoneManager.postInit();
		
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
			selectedZone = detectTouchedZone((int)event.getX(), (int)event.getY());
			
			//	calls the handleDownTouch method of the selected object
			if (selectedZone != null) selectedZone.handleDownTouch(event);
			
			Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			//	calls the handleMoveTouch method of the selected object
			if (selectedZone != null) selectedZone.handleMoveTouch(event);
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			//	calls the handleUpTouch method of the selected object, then sets selected to null again
			if (selectedZone != null) {
				selectedZone.handleUpTouch(event);
				selectedZone = null;
			}
		}
		return true;
	}
	
	//	updates all elements in this view
	public void update() {
		game.update();
		zoneManager.update();
	}

	@Override
	//	draws the view
	protected void onDraw(Canvas canvas) {
		if (canvas != null) {
			//	draw black background
			canvas.drawColor(Color.BLACK);
			
			zoneManager.draw(canvas);
			
			displayFps(canvas, avgFps);
			if (Game.DEBUG_MODE) displayDebugMode(canvas);
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
	
	private void displayDebugMode(Canvas canvas) {
		if (canvas != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			paint.setTextSize(20);
			canvas.drawText("DEBUG MODE", this.getWidth() - 140, 45, paint);
		}
	}
	
	//	takes a set of coordinates and returns the DrawObject touched at that location
    //	if point is within two or more object's bounding boxes, returns the one whose center is closest to touch location
    /*private DrawObject detectTouchedObject(float x, float y) {
    	ArrayList<DrawObject> touched = new ArrayList<DrawObject>();
    	//	brute-forces checks with all objects (to be replaced with more efficient code at a later time)
    	for (DrawObject d : drawables) {
    		if (d.isTouched(x, y, true))	touched.add(d);
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
    }*/
    
    //	takes a set of coordinates and returns the DrawObject touched at that location
    //	if point is within two or more object's bounding boxes, returns the one whose center is closest to touch location
    private GameZone detectTouchedZone(float x, float y) {
    	ArrayList<GameZone> touched = new ArrayList<GameZone>();
    	//	brute-forces checks with all objects (to be replaced with more efficient code at a later time)
    	GameZone[] allZones = zoneManager.getAllZones();
    	for (GameZone z : allZones) {
    		if (z.isTouched(x, y, true))	touched.add(z);
    	}
    	
    	if (touched.size() == 1) return touched.get(0);
    	else if (touched.size() == 0) return null;
    	else {
    		GameZone temp = touched.get(0);
    		double dPri = temp.drawPriority();
    		int logTemp = 0;
    		
    		//	test every touched object to find the object that has the closest center to touch location
    		for (int i = 1; i < touched.size(); i++) {
    			GameZone temp2 = touched.get(i);
    			double tempDPri = temp.drawPriority();
    			Log.d(TAG, String.format("%d: %f vs %d: %f", logTemp, dPri, i, tempDPri));
    			if (tempDPri > dPri) {
    				dPri = tempDPri;
    				temp = temp2;
    				logTemp = i;
    			}
    		}
    		return temp;
    	}
    }
}
