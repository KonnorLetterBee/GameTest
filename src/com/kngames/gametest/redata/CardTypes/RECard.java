package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.CardData;

public abstract class RECard {
	protected String name;
	protected String idPrefix;
	protected int cardID;
	protected CardType cardType;
	protected int expansion;
	protected int deckQuantity;
	protected String text;
	protected String tag;
	
	public static enum CardType {Ammunition, Character, InfecChar, Weapon, Action, Item, Infected, Event, Token, Infection}
	public static final String[] CardTypes = {"Ammunition", "Character", "Infected Character", "Weapon", "Action", "Item", "Infected", "Event", "Token", "Infection"};
	
	public RECard(String name, CardType type, String idPrefix, int ID, int expans, int quantity, String text) {
		this.name = name;
		this.cardType = type;
		this.idPrefix = idPrefix;
		this.cardID = ID;
		this.expansion = expans;
		this.text = text;
		this.deckQuantity = quantity;
		this.tag = CardData.generateTagString(this);
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
}
