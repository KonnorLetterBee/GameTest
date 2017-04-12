package com.kngames.gametest.regame.gamestruct;

//import android.util.Log;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.Mansion.InfectedCard;

public class Player {
	//private static final String TAG = Player.class.getSimpleName();
	
	private int playerId;
	public int playerId() { return playerId; }
	
	private Game game;
	
	private CharacterCard character;
	public CharacterCard character() { return character; }
	
	private int health;
	private int maxHealth;
	public int health() { return health; }
	public int maxHealth() { return maxHealth; }
	
	public int actions;
	public int ammo;
	public int ammoRemaining;
	public int buys;
	public int explores;
	public boolean mustExplore;
	public int gold;
	
	public boolean isDead;
	public boolean isEliminated;
	
	private REDeck deck;
	private REDeck hand;
	private REDeck inPlay;
	private REDeck discard;
	private REDeck attachedCards;
	private REDeck weapons;
	
	public REDeck deck() { return deck; }
	public REDeck hand() { return hand; }
	public REDeck inPlay() { return inPlay; }
	public REDeck discard() { return discard; }
	public REDeck attachedCards() { return attachedCards; }
	public REDeck weapons() { return weapons; }
	
	private String customInventory;
	
	//	constructs a Player with a specified Character card
	public Player(int id, Game g, CharacterCard ch, String customInventory) {
		playerId = id;
		this.game = g;
		character = ch;
		health = ch.getMaxHealth();
		maxHealth = ch.getMaxHealth();
		this.customInventory = customInventory;
		
		isDead = false;
		
		resetGame();
		resetTurn();
	}
	
	//	initializes the preset deck for this Player
	public REDeck initializeDeck() {
		REDeck inventory = new REDeck();
		
		//	add 7 "Ammo x10" cards to the Inventory
		for (int i = 0; i < 7; i++) {
			inventory.addTop(game.shop().gainCardSearch(this, "AM;1"));
		}
		
		//	add the two "Combat Knives" and "Handgun" to the Inventory
		inventory.addTop(game.shop().gainCardSearch(this, "WE;04"));
		inventory.addTop(game.shop().gainCardSearch(this, "WE;04"));
		inventory.addTop(game.shop().gainCardSearch(this, "WE;06"));
		
		//	shuffle the mansion, flip it, and return the new Deck object
		inventory.shuffle(2);
		inventory.flipDeck();
		assert (inventory.size() == 10);
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
		//	reset all stats and revives the player
		actions = 1;
		ammo = 0;
		ammoRemaining = 0;
		buys = 1;
		explores = 1;
		mustExplore = false;
		gold = 0;
		isDead = false;
		
		//	clear hand and field, then draw 5 cards
		while(hand.size() > 0) {
			discard.addTop(hand.popFirst());
		}
		while(inPlay.size() > 0) {
			discard.addTop(inPlay.popFirst());
		}
		while (hand.size() < 5) {
			boolean drawn = drawToHand();
			if (!drawn)
				break;
		}
	}
	
	//	resets the game, clearing all fields and starting the deck with the standard starting inventory
	private void resetGame() {
		if (customInventory == null || customInventory.equals("")) deck = initializeDeck();
		else deck = initializeCustomDeck(customInventory);
		
		hand = new REDeck();
		inPlay = new REDeck();
		discard = new REDeck();
		attachedCards = new REDeck();
		weapons = new REDeck();
	}
	
	//	draws a card from Deck and puts it into Hand, shuffling Discard and replacing it with Deck if the Deck is empty
	//	if Discard is also empty, don't draw a card
	//	returns true if a card was actually drawn, false otherwise (deck and discard are both empty)
	public boolean drawToHand() {
		if (deck.size() == 0) {
			deck = discard;
			deck.flipDeck();
			deck.shuffle(1);
			discard = new REDeck();
		}
		if (deck.size() > 0) {
			hand.addBack(deck.popTop());
			return true;
		} else {
			return false;	// if no card was drawn
		}
	}
	
	//	plays a card from this player's hand, using the correct action determined by the game state
	public void playCard(int handPos) {
		RECard temp = (RECard)hand.peek(handPos);
		if (temp != null) {
			if (temp.canPlay(game, this, hand)) {
				((RECard)hand.pop(handPos)).onPlay(game, this);
			}
		}
	}
	
	//	searches this player's play areas for responses, and allows them to play any of them
	public void searchForResponses () {
		//	TODO:  implement the search
	}
	
	//	returns all weapons from the weapons deck to the inPlay area, meant to be called after combat has finished
	public void flushWeaponsDeck() {
		while (weapons.size() > 0) {
			((WeaponCard)weapons.popFirst()).onExploreFinish(game, this);
		}
	}
	
	//	changes the player's health by a set amount, and handles if the health goes above the maximum, or hits zero
	//	decMaxIfKilled, if true, will decrement the player's maximum health by 20 if killed
	public void changeHealth(int amountChange, boolean decMaxIfKilled) {
		health += amountChange;
		if (health <= 0) killPlayer(decMaxIfKilled);
		else if (health > maxHealth) health = maxHealth;
	}
	
	//	changes the player's maximum health by a set amount, and handles if the max hits zero
	public void changeMaxHealth(int amountChange) {
		maxHealth += amountChange;
		if (maxHealth <= 0) isEliminated = true;
		else if (maxHealth > maxHealth) health = maxHealth;
	}
	
	//	kills the player (sets their life to zero, combines all cards into deck, sets isDead to true)
	//	and check for elimination
	public void killPlayer(boolean subtractMax) {
		isDead = true;
		if (subtractMax) changeMaxHealth(-20);
		health = maxHealth;
		while (hand.size() > 0) deck.addBottom(hand.pop(0));
		while (discard.size() > 0) deck.addBottom(discard.pop(0));
	}
	
	//	counts the number of decorations this player currently has
	public int countDecorations() {
		int decs = 0;
		for (int i = 0; i < attachedCards.size(); i++) {
			if (attachedCards.peek(i) instanceof InfectedCard) decs += ((InfectedCard)attachedCards.peek(i)).getDecorations();
		}
		return decs;
	}
	
	public String[] generateInfoStrings() {
		String[] data = new String[] {
				character().getName(),
				String.format("health:   %d / %d", health, maxHealth),
				String.format("actions:  %d", actions),
				String.format("buys:     %d", buys),
				String.format("explores: %d", explores),
				String.format("ammo:     %d   (remaining: %d)", ammo, ammoRemaining),
				String.format("gold:     %d", gold),
		};
		if (mustExplore) data[4] += "  (must explore)";
		return data;
	}
}
