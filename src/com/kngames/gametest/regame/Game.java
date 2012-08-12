package com.kngames.gametest.regame;

import java.util.ArrayList;

import android.util.Log;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;

public class Game {
	private static final String TAG = Game.class.getSimpleName();
	
	private static Game game;
	
	private Scenario scen;
	public Scenario scen() { return scen; }
	
	private Player[] players;
	public Player[] players() { return players; }
	
	private int activePlayer = 0;
	private int viewingPlayer = 0;
	public int activePlayer() { return activePlayer; }
	public int viewingPlayer() { return viewingPlayer; }
	
	private ArrayList<REDeck> resourcePiles;
	
	private Game(CharacterCard[] chars, Scenario scen) {
		//	initialize all players with their proper characters
		players = new Player[chars.length];
		for (int i = 0; i < chars.length; i++) {
			players[i] = new Player(chars[i], null);
		}
		
		//	set the scenario and resource piles
		if (scen != null) setupResourcePiles(scen);
	}
	
	//	instantiates a new Game if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static Game startGame(CharacterCard[] chars, Scenario scen) {
		if (game == null) return new Game(chars, scen);
		else {
			Log.e(TAG, "Game already instantiated!");
			return game;
		}
	}
	
	//	set the scenario and resource piles
	private void setupResourcePiles(Scenario scen) {
		this.scen = scen;
		ArrayList<RECard[]> cards = scen.getCards();
		resourcePiles = new ArrayList<REDeck>();
		//	set up all piles
		for (int i = 0; i < cards.size(); i++) {
			REDeck tempDeck = new REDeck();
			RECard[] temp = cards.get(i);
			//	set up individual piles with multiple kinds of cards
			for (int j = 0; j < temp.length; j++) {
				for (int k = 0; k < temp[j].getDeckQuantity(); k++) {
					tempDeck.addTop(temp[j]);
				}
			}
			//	shuffle the deck if there is more than one kind of card in a stack
			if (temp.length > 1) tempDeck.shuffle(1);
			resourcePiles.add(tempDeck);
		}
	}
}
