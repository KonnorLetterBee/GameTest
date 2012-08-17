package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.redata.carddata.CardData;
import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public abstract class RECard extends Card {
	protected String name;
	protected String idPrefix;
	protected CardType cardType;
	protected int expansion;
	protected int deckQuantity;
	protected String text;
	
	public static enum CardType {Ammunition, Character, InfecChar, Weapon, Action, Item, Infected, Event, Token, Infection}
	public static final String[] CardTypes = {"Ammunition", "Character", "Infected Character", "Weapon", "Action", "Item", "Infected", "Event", "Token", "Infection"};
	
	public RECard(String name, CardType type, String idPrefix, int ID, int expans, int quantity, String text) {
		super(ID);
		this.name = name;
		this.cardType = type;
		this.idPrefix = idPrefix;
		this.expansion = expans;
		this.text = text;
		this.deckQuantity = quantity;
		this.tag = generateTag();
	}
	
	public String getName() { return name; }
	public CardType getCardType() { return cardType; }
	public String getIDPrefix() { return idPrefix; }
	public String getIDString() { return String.format("%s-%03d", idPrefix, cardID); }
	public int getID() { return cardID; }
	public int getExpansion() { return expansion; }
	public int getDeckQuantity() { return deckQuantity; }
	public String getText() { return text; }
	public String getTag() { return tag; }
	
	public static Card parseTag(String tag) {
		return CardData.findByCardTag(tag);
	}
	public String generateTag() {
		return CardData.generateTagString(this);
	}
	
	//	method to be called when a RECard is played
	public void onPlay(Game game, Player actingPlayer) { }
	
	public interface OnPlayListener {
		public void playAction(RECard card, Game game, Player actingPlayer);
	}
	
	public interface OnPlayFinishListener {
		public void finish(RECard card, Game game, Player actingPlayer);
	}
}
