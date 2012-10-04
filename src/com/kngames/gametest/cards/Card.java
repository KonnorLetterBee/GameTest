package com.kngames.gametest.cards;

import com.kngames.gametest.engine.graphics.DrawObject;

public class Card {
	protected int cardID;
	
	protected String catTag;
	protected int intTag;
	
	protected DrawObject cardPic;
	
	//	constructs a card object with no picture
	public Card(int ID) {
		this.cardID = ID;
	}
	
	//	constructs a card object with a specified tag and no picture
	public Card(int ID, String tag) {
		this.cardID = ID;
		this.setTags(tag);
	}
	
	//	constructs a card object with a specified picture
	public Card(int ID, DrawObject pic) {
		this.cardID = ID;
		this.cardPic = pic;
	}
	
	//	constructs a card object with a specified tag and specified picture
	public Card(int ID, String tag, DrawObject pic) {
		this.cardID = ID;
		this.setTags(tag);
	}
	
	//	accessor methods
	public int getID() { return cardID; }
	public String getCatTag() { return catTag; }
	public int getIntTag() { return intTag; }
	public DrawObject pic() { return cardPic; }
	
	//	checks to see if two cards have the same tag
	public boolean equals(Object other) {
		Card otherCard;
		try { otherCard = (Card)other; }
		catch (Exception e) { return false; }
		
		return 	(this.catTag.equals(otherCard.catTag) && this.intTag == otherCard.intTag);
	}
	
	//	provides a way to get a card based on tag info provided by extended classes
	public static Card parseTag(String tag) {
		return null;
	}
	
	//	splits the tag string by the semicolon and sets the appropriate tag fields
	public void setTags(String fullTag) {
		String[] fields = fullTag.split(";");
		this.catTag = fields[0];
		this.intTag = Integer.parseInt(fields[1]);
	}
	
	//	provides a way to generate a (usually) unique tag for extended classes
	public String generateTag() { return catTag + ";" + intTag; }
	
	//	the general toString method of a card is to output its tag that was generated during construction
	@Override
	public String toString() {
		return generateTag();
	}
}
