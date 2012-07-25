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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_loop, menu);
        return true;
    }
    
    @Override
    //	triggers a log message
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

	@Override
	//	triggers a log message
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		super.onStop();
	}
}
