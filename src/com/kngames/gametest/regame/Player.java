package com.kngames.gametest.regame;

import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.CharacterCard;

public class Player {
	private int health;
	private int maxHealth;
	
	private int actions;
	private int ammo;
	private int buys;
	private int explores;
	private boolean mustExplore;
	private int gold;
	
	private REDeck deck;
	private REDeck hand;
	private REDeck inPlay;
	private REDeck discard;
	private REDeck attachedCards;
	
	public REDeck deck() { return deck; }
	public REDeck hand() { return hand; }
	public REDeck inPlay() { return inPlay; }
	public REDeck discard() { return discard; }
	public REDeck attachedCards() { return attachedCards; }
	
	private String customInventory;
	
	//	constructs a Player with a specified Character card
	public Player(CharacterCard ch, String customInventory) {
		health = ch.getMaxHealth();
		maxHealth = ch.getMaxHealth();
		this.customInventory = customInventory;
		
		resetGame();
		resetTurn();
	}
	
	//	initializes the preset deck for this Player
	public REDeck initializeDeck() {
		REDeck inventory = new REDeck();
		
		//	add 7 "Ammo x10" cards to the Inventory
		for (int i = 0; i < 7; i++) {
			inventory.addBottom(CardData.Ammunition[0]);
		}
		
		//	add the two "Combat Knives" and "Handgun" to the Inventory
		inventory.addBottom(CardData.Weapons[3]);
		inventory.addBottom(CardData.Weapons[3]);
		inventory.addBottom(CardData.Weapons[5]);
		
		//	shuffle the mansion, flip it, and return the new Deck object
		inventory.shuffle(2);
		inventory.flipDeck();
		return inventory;
	}
	
	//	initializes a custom decklist
	public REDeck initializeCustomDeck(String decklist) {
		return new REDeck();
	}
	
	//	resets the turn
	//		resets all stats to their default values
	//		clears the hand and field of all cards and puts them into the discard pile
	//		draws a new hand of 5 cards
	public void resetTurn() {
		//	reset all stats
		actions = 1;
		ammo = 0;
		buys = 1;
		explores = 1;
		mustExplore = false;
		gold = 0;
		
		//	clear hand and field, then draw 5 cards
		while(hand.size() > 0) {
			discard.addTop(hand.popFirst());
		}
		while(inPlay.size() > 0) {
			discard.addTop(inPlay.popFirst());
		}
		while (hand.size() < 5) {
			drawToHand();
		}
	}
	
	//	resets the game, clearing all fields and starting the deck with the standard starting inventory
	private void resetGame() {
		if (customInventory == null || customInventory.equals("")) deck = initializeDeck();
		else deck = initializeCustomDeck(customInventory);
		
		hand = new REDeck();
		inPlay = new REDeck();
		discard = new REDeck();
	}
	
	//	draws a card from Deck and puts it into Hand, shuffling Discard and replacing it with Deck if the Deck is empty
	//	if Discard is also empty, don't draw a card
	public void drawToHand() {
		if (deck.size() == 0) {
			deck = discard;
			deck.flipDeck();
			deck.shuffle(1);
			discard = new REDeck();
		}
		if (deck.size() > 0) hand.addTop(deck.popTop());
	}
}
