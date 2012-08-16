package com.kngames.gametest;

import java.text.DecimalFormat;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {
	
	private static final String TAG = GameLoopThread.class.getSimpleName();

	private final static int 	MAX_FPS = 60;					// desired fps
	private final static int	MAX_FRAME_SKIPS = 5;			// maximum number of frames to be skipped
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	// the frame period
	
	/* Stuff for stats */
    private DecimalFormat df = new DecimalFormat("0.##");
	private final static double	STAT_INTERVAL = 500;	//	frequency of reading stats in ms
	private final static int	FPS_HISTORY_NR = 10;	//	number of FPS measurements to take average of
	private long statusIntervalTimer		= 0l;		//	the status time counted
	private long totalFramesSkipped			= 0l;		//	number of frames skipped since the game started
	private long framesSkippedPerStatCycle 	= 0l;		//	number of frames skipped in a store cycle (1 sec)

	private int frameCountPerStatCycle = 0;		//	number of rendered frames in an interval
	private long totalFrameCount = 0l;
	private double 	fpsStore[];			//	stored FPS values
	private long 	statsCount = 0;		//	the number of times the stat has been read
	private double 	averageFps = 0.0;	//	the average FPS since the game started
	
	//	structures that hold the Canvas, and the actual View to be drawn
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;

	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public GameLoopThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		
		initTimingElements();
		long beginTime;		// the time when the cycle begun
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped 

		sleepTime = 0;
		
		while (running) {
			canvas = null;
			//	try locking the canvas for exclusive pixel editing in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;	// resetting the frames skipped
					
					// update game state 
					gamePanel.update();
					// draws the canvas on the panel
					this.gamePanel.onDraw(canvas);
					
					// calculate cycle time, and appropriate sleep time
					timeDiff = System.currentTimeMillis() - beginTime;
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					// if sleep time is positive, sleep for that amount of time
					if (sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {}
					}
					
					//	if sleep time is negative, update without rendering a frame
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						this.gamePanel.update();
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
					
					//	log statistics for frame rate
					framesSkippedPerStatCycle += framesSkipped;
					storeStats();
				}
			} finally {
				//	in case of an exception the surface is not left in an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
	}
	
	/**
	 * The statistics - it is called every cycle, it checks if time since last
	 * store is greater than the statistics gathering period and if so it
	 * calculates the FPS for the last period and stores it.
	 * 
	 *  It tracks the number of frames per period. The number of frames since 
	 *  the start of the period are summed up and the calculation takes part
	 *  only if the next period and the frame count is reset to 0.
	 */
	private void storeStats() {
		frameCountPerStatCycle++;
		totalFrameCount++;
		// assuming that the sleep works each call to storeStats
		// happens at 1000/FPS so we just add it up
		statusIntervalTimer += FRAME_PERIOD;
		
		if (statusIntervalTimer >= STAT_INTERVAL) {
			// calculate the actual frames pers status check interval
			double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
			
			//stores the latest fps in the array
			fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
			
			// increase the number of times statistics was calculated
			statsCount++;
			
			double totalFps = 0.0;
			// sum up the stored fps values
			for (int i = 0; i < FPS_HISTORY_NR; i++) {
				totalFps += fpsStore[i];
			}
			
			// obtain the average
			if (statsCount < FPS_HISTORY_NR) {
				// in case of the first 10 triggers
				averageFps = totalFps / statsCount;
			} else {
				averageFps = totalFps / FPS_HISTORY_NR;
			}
			// saving the number of total frames skipped
			totalFramesSkipped += framesSkippedPerStatCycle;
			// resetting the counters after a status record (1 sec)
			framesSkippedPerStatCycle = 0;
			statusIntervalTimer = 0;
			frameCountPerStatCycle = 0;
			gamePanel.setAvgFps("FPS: " + df.format(averageFps));
		}
	}
	
	private void initTimingElements() {
		// initialize timing elements
		fpsStore = new double[FPS_HISTORY_NR];
		for (int i = 0; i < FPS_HISTORY_NR; i++) {
			fpsStore[i] = 0.0;
		}
		Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialized");
	}
}