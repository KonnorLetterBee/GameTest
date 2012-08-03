package com.kngames.gametest.cards;

public abstract class Card {
	protected int cardID;
	protected String tag;
	
	//	constructs a default card
	public Card(int ID) {
		this.cardID = ID;
	}
	
	//	accessor methods
	public int getID() { return cardID; }
	public String getTag() { return tag; }
	
	//	provides a way to get a card based on tag info provided by extended classes
	public static Card parseTag(String tag) {
		return null;
	}
	
	//	provides a way to generate a (usually) unique tag for extended classes
	protected abstract String generateTag();
	
	//	the general toString method of a card is to output its tag that was generated during construction
	@Override
	public String toString() {
		return tag;
	}
}
