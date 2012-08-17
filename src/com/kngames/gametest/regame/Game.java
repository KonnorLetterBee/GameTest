package com.kngames.gametest.regame;

import android.content.Context;
import android.util.Log;

import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;

public class Game {
	private static final String TAG = Game.class.getSimpleName();
	
	private Context context;
	public Context getContext() { return context; }
	
	private static Game game;
	
	private Scenario scen;
	public Scenario scen() { return scen; }
	
	private Player[] players;
	public Player[] players() { return players; }
	public Player getActivePlayer() { return players[activePlayer]; }
	
	private int activePlayer = 0;
	private int viewingPlayer = 0;
	public int activePlayer() { return activePlayer; }
	public int viewingPlayer() { return viewingPlayer; }
	
	private Shop shop;
	public Shop shop() { return shop; }
	
	private Game(Context context, CharacterCard[] chars, Scenario scen) {
		this.context = context;
		
		//	set the scenario and resource piles
		shop = new Shop(this, scen, chars.length);
		
		//	initialize all players with their proper characters
		players = new Player[chars.length];
		for (int i = 0; i < chars.length; i++) {
			players[i] = new Player(this, chars[i], null);
		}
		
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
}
