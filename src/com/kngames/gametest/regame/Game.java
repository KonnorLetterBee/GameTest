package com.kngames.gametest.regame;

import com.kngames.gametest.redata.Scenario;

public class Game {
	private Scenario scen;
	private Player[] players;
	private int activePlayer = 0;
	
	private Game(int numPlayers) {
		players = new Player[numPlayers];
	}
}
