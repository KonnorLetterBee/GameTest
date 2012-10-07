package com.kngames.gametest.cards;

import java.util.ArrayList;

import com.kngames.gametest.engine.data.DataOps;
import com.kngames.gametest.redata.CardTypes.RECard;

/**
 * This class is a data structure that contains a list of Card objects, and has fields for keeping track of
 * the name of the collection, a description of the cards in the collection, and notes about the collection 
 * that can be modified.  It also contains methods for loading and saving to a text file or a database.
 * @author Konnor
 */
public class CardList {
	private int id;
	private String name;
	private String[] tagList;
	private boolean loaded = false;
	private ArrayList<Card[]> cards;
	private String desc;
	private String notes;
	private static CardData dataRef = CardData.getCardData();
	
	/**
	 * Constructs a default CardList.
	 */
	public CardList () {
		this.id = -1;
		this.name = "";
		this.tagList = null;
		this.cards = null;
		this.desc = "";
		this.notes = "";
	}

	public int ID() { return id; }
	public String name() { return name; }
	public boolean complete() { return loaded; }
	public ArrayList<Card[]> cards() { return cards; }
	public String description() { return desc; }
	public String notes() { return notes; }
	
	public void setName(String name) { this.name = name; }
	public void setDesc(String desc) { this.desc = desc; }
	public void setNotes(String notes) { this.notes = notes; }
	
	/**
	 * Loads the Card objects from the tags in the specified tag list into the list of Cards.
	 */
	public boolean loadCards() {
		cards = new ArrayList<Card[]>();
		
		//	check for empty tag lists
		if (tagList.length == 0 || (tagList.length == 1 && tagList[0].equals(""))) return loaded;
		else loaded = true;
		
		for (int i = 0; i < tagList.length; i++) {
			Card[] temp = parseStack(tagList[i]);
			cards.add(temp);
			for(Card card : temp) {
				if (card == null) {	//	if the card was not found, set complete to false and stop creating the list
					loaded = false;
					return loaded;
				}
			}
		}
		return loaded;
	}
	
	public void saveToFile(String path) {
		DataOps.exportToFile(path, String.format(
				format, args));
	}
	
	public void saveToDB(String table) {
		
	}
	
	/**
	 * Generates an array of String objects that correspond to the tags of every card in the list.  Each String can possibly
	 * have multiple tags, separated by spaces, which indicate a stack of cards instead of a single card.
	 * @return  The array of tags for this CardList
	 */
	public String[] generateTagStrings() {
		String[] out = new String[cards.size()];
		
		for (int i = 0; i < cards.size(); i++) {
			StringBuilder currStack = new StringBuilder();
			for (int j = 0; j < cards.get(i).length; j++) {
				if (j > 0) currStack.append(" ");
				currStack.append(cards.get(i)[j].generateTag());
			}
			out[i] = currStack.toString();
		}
		
		return out;
	}
	
	/**
	 * Generates a single String object that corresponds to the tags of every card in the list, separating cards (or stacks of cards) with a 
	 * '/' character.  Each part of the split String can possibly have multiple tags, separated by spaces, which indicate a stack of cards instead 
	 * of a single card.
	 * @return  The String with all of the tags for this CardList
	 */
	public String generateSingleTagString() {
		StringBuilder out = new StringBuilder();
		
		for (int i = 0; i < cards.size(); i++) {
			StringBuilder currStack = new StringBuilder();
			for (int j = 0; j < cards.get(i).length; j++) {
				if (j > 0) currStack.append(" ");
				currStack.append(cards.get(i)[j].generateTag());
			}
			if (i > 0) out.append("/");
			out.append(currStack.toString());
		}
		
		return out.toString();
	}
	
	
	/**
	 * Parses a tag (or a set of tags, delimited by a space character) and generates a Card array corresponding to those input tags.
	 * @param tags  The String that contains a tag (or set of tags, each delimited by a space) to be parsed
	 * @return      The constructed Card array
	 */
	public static Card[] parseStack(String tags) {
		String[] tagList = tags.split("\\ ");
		Card[] cardList = new RECard[tagList.length];
		for (int i = 0; i < tagList.length; i++) {
			cardList[i] = dataRef.getCard(tagList[i]);
		}
		return cardList;
	}
}
