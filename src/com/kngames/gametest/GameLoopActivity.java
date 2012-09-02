package com.kngames.gametest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class GameLoopActivity extends Activity {

	//	logging tag
	private static final String TAG = GameLoopActivity.class.getSimpleName();
	
	//	instance of GamePanel
	private GamePanel mainPanel;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //	set window preferences (fullscreen)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //	create the main GamePanel and set it to the content view
        mainPanel = new GamePanel(this);
        setContentView(mainPanel);
        Log.d(TAG, "View added");
    }
    
    @Override
    protected void onPause() {
    	Log.d(TAG, "Pausing...");
		super.onPause();
    }
    
    @Override
    protected void onResume() {
    	Log.d(TAG, "Resuming...");
		super.onResume();
    }
    
    @Override
    //	triggers a log message, and if the game is over, set mainPanel to null
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		if (mainPanel.gameConcluded()) {
			mainPanel = null;
			Log.d(TAG, "Game concluded, destroying main panel...");
		}
		super.onDestroy();
	}

	@Override
	//	triggers a log message
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
}
