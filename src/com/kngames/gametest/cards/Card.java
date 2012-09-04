package com.kngames.gametest.cards;

import com.kngames.gametest.engine.graphics.DrawObject;

public abstract class Card {
	protected int cardID;
	protected String tag;
	protected DrawObject cardPic;
	
	//	constructs a default card with no picture
	public Card(int ID) {
		this.cardID = ID;
	}
	
	//	constructs a default card with a specified DrawObject
	public Card(int ID, DrawObject draw) {
		this.cardID = ID;
		this.cardPic = draw;
	}
	
	//	accessor methods
	public int getID() { return cardID; }
	public String getTag() { return tag; }
	public DrawObject pic() { return cardPic; }
	
	//	checks to see if two cards have the same tag
	public boolean equals(Object other) {
		Card otherCard;
		try { otherCard = (Card)other; }
		catch (Exception e) { return false; }
		
		return this.tag.equals(otherCard.tag);
	}
	
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
