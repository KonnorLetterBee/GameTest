package com.kngames.gametest.regame.gamestruct;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class Game {
	private static final String TAG = Game.class.getSimpleName();
	public static final boolean DEBUG_MODE = false;
	
	private Context context;
	public Context getContext() { return context; }
	
	private static Game game;
	
	private Scenario scen;
	public Scenario scen() { return scen; }
	
	private int numPlayers;
	private Player[] players;
	public Player[] players() { return players; }
	
	private int activePlayer = 0;	//	the player currently taking a turn
	private int visiblePlayer = 0;	//	the player who's hand is showing
	private int tempPlayer = 0;		//	a temporary counter for when other players need to be tested in order, but are not actually taking a turn
	public int activePlayer() { return activePlayer; }
	public int visiblePlayer() { return visiblePlayer; }
	public int tempPlayer() { return tempPlayer; }
	public Player getActivePlayer() { return players[activePlayer]; }
	public Player getVisiblePlayer() { return players[visiblePlayer]; }
	public Player getTempPlayer() { return players[tempPlayer]; }
	
	private Shop shop;
	public Shop shop() { return shop; }
	
	private GameState state;
	public GameState state() { return state; }
	
	private ArrayList<ExploreEffect> exploreEffects;
	public ArrayList<ExploreEffect> exploreEffects() { return exploreEffects; }
	
	private Game(Context context, CharacterCard[] chars, Scenario scen) {
		this.context = context;
		
		//	set the scenario and resource piles
		shop = new Shop(this, scen, chars.length);
		
		//	initialize all players with their proper characters
		numPlayers = chars.length;
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i, this, chars[i], null);
		}
		
		state = new GameState(this, State.StartTurn);
		exploreEffects = new ArrayList<ExploreEffect>();
		
		shop.shuffleAllPiles();
	}
	
	//	instantiates a new Game if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static Game startGame(Context context, CharacterCard[] chars, Scenario scen) {
		if (game == null) return new Game(context, chars, scen);
		else {
			Log.e(TAG, "Game already instantiated!");
			return game;
		}
	}
	
	//	checks whether it's the current player's turn
	public boolean isActivePlayer(Player play) {
		return play.playerId() == activePlayer;
	}
	
	//	checks whether the specified player is set as temp
	public boolean isTempPlayer(Player play) {
		return play.playerId() == tempPlayer;
	}
	
	//	advances the active player to the next player
	public void advanceActivePlayer() {
		activePlayer++;
		if (activePlayer == numPlayers) activePlayer = 0;
		tempPlayer = activePlayer;
	}
	
	//	advances the temp player to the next player
	//	returns true if the counter is back to the active player (where it started)
	public boolean advanceTempPlayer() {
		tempPlayer++;
		if (tempPlayer == numPlayers) tempPlayer = 0;
		return tempPlayer == activePlayer;
	}
	
	//	searches all player's play areas for responses, and allows them to play any of them
	public void searchForResponses () {
		for (Player p : players)
			p.searchForResponses();
	}
	
	//	applies all explore effects to the currently exploring characters, then removes them from the effects list
	public void applyExploreEffects() {
		while (exploreEffects.size() > 0) {
			exploreEffects.get(0).applyEffect();
			exploreEffects.remove(0);
		}
	}
	
	public void popupToast(String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
}
