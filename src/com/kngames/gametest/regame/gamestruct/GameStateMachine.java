package com.kngames.gametest.regame.gamestruct;

import java.util.HashMap;

import android.util.Log;

import com.kngames.gametest.engine.hsm.State;

public class GameStateMachine {
	private static GameStateMachine gsm = null;
	
	private static final String TAG = GameStateMachine.class.getSimpleName();
	
	public static enum GameState { StartTurn, MainPhase, PlayCard, PlayCardRespond, EndTurn };
	private State currentState;
	
	private HashMap<GameState, State> state;
	
	//	constructs a new GameStateMachine
	//	since GameStateMachine is a singleton, instantiate the GameStateMachine using initGSM
	private GameStateMachine() {
		Log.d(TAG, "GameStateMachine instantiated");
	}
	
	//	instantiates a new GameStateMachine if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static GameStateMachine initGSM() {
		if (gsm == null) return new GameStateMachine();
		else {
			Log.e(TAG, "GameStateMachine already instantiated!");
			return gsm;
		}
	}
	
	//	returns the instance of the GameStateMachine that already exists (even if there is none)
	public static GameStateMachine getGSM() {
		return gsm;
	}
	
	public void changeState(GameState state) {
		
	}
}
