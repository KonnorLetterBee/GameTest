package com.kngames.gametest.regame;

public class Game {
	private Player[] players;
	private int activePlayer = 0;
	
	private Game(int numPlayers) {
		players = new Player[numPlayers];
	}
}
