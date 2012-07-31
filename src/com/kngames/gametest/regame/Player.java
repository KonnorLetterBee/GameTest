package com.kngames.gametest.regame;

import java.util.ArrayList;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.Deck;

public class Player {
	private int health;
	private int maxHealth;
	
	private int actions;
	private int ammo;
	private int buys;
	private int explores;
	private boolean mustExplore;
	private int gold;
	
	private Deck deck;
	private Deck hand;
	private ArrayList<Card> inPlay;
	private Deck discard;
	private ArrayList attachedCards;
	
	public Player() {
		
	}
}
