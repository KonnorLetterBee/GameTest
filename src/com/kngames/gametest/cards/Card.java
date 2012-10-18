package com.kngames.gametest.cards;

import java.util.HashMap;

import com.kngames.gametest.engine.graphics.DrawObject;

public class Card {
	/**
	 * CardComp class, provides to the Card class a way to apply behaviors to the Card that can be searched for
	 * and called using a single method
	 * @author Konnor
	 */
	public static abstract class CardComp {
		protected String name;
		protected Card parent;
		
		public CardComp() { this("", null); }
		public CardComp(String name) { this(name, null); }
		public CardComp(String name, Card parent) { 
			this.name = name;
			this.parent = parent;
		}
		
		public String name() { return name; }
		public Card parent() { return parent; }
		public void setParent(Card parent) { this.parent = parent; }
		
		public abstract void execute();
	}
	
	/**
	 * CardConditionComp, an extension of CardComp that allows a specific condition to be defined and checked
	 * before executing the CardComp's behavior.
	 * @author Konnor
	 */
	public static abstract class CardConditionComp extends CardComp {
		public abstract boolean evaluate();
	}
	
	
	protected int cardID;
	protected String catTag;
	protected int intTag;
	
	protected DrawObject cardPic;
	protected HashMap<String, CardComp> components;
	
	
	/**
	 * Constructs a new Card object.
	 * @param ID  the card's ID number
	 * @param catTag  the card's category tag
	 * @param intTag  the card's integer tag
	 * @param pic  the card's picture
	 * @param comps  the card's initial list of CardComp objects to add
	 */
	public Card(int ID, String catTag, int intTag, DrawObject pic, CardComp[] comps) {
		this.cardID = ID;
		this.catTag = catTag;
		this.intTag = intTag;
		cardPic = pic;
		
		if (comps != null) {
			for (CardComp c : comps) {
				this.addComponent(c);
			}
		}
	}
	
	/**
	 * Constructs a new Card object.
	 * @param ID  the card's ID number
	 * @param tag  the card's tag, separating the category and integer values with a semicolon (;)
	 * @param pic  the card's picture
	 * @param comps  the card's initial list of CardComp objects to add
	 */
	public Card(int ID, String tag, DrawObject pic, CardComp[] comps) {
		this.cardID = ID;
		this.setTags(tag);
		cardPic = pic;
		
		if (comps != null) {
			for (CardComp c : comps) {
				this.addComponent(c);
			}
		}
	}
	
	///
	///		Accessor Methods
	///
	
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
	
	/**
	 * Adds a CardComp to this Card object.
	 * @param comp the CardComp to add
	 */
	public void addComponent(CardComp comp) {
		comp.parent = this;
		components.put(comp.name, comp);
	}
	
	/**
	 * Checks whether or not a CardComp with a specified name exists in this Card object.
	 * @param name  the name of the CardComp to look for
	 * @return  true if a CardComp with the specified name exists, false otherwise
	 */
	public boolean compExists(String name) {
		return components.containsKey(name);
	}
	
	/**
	 * Searches the component list of this Card object and returns the CardComp with the specified name.
	 * @param name  the name of the CardComp to look for
	 * @return  the CardComp with the specified name, null if none exists
	 */
	public CardComp getComponent(String name) {
		return components.get(name);
	}
}
