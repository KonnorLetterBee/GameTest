package com.kngames.gametest.cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.MethodNotSupportedException;

import com.kngames.gametest.engine.data.DataOps;

/**
 * This class is a data structure that contains a list of Card objects, and has fields for keeping track of
 * the name of the collection, a description of the cards in the collection, and notes about the collection 
 * that can be modified.  It also contains methods for loading and saving to a text file or a database.
 * 
 * During construction, you have the option of not loading the Card objects immediately.  In this case, cards
 * can be loaded later by using the loadCards() method, which will load all cards corresponding to the current
 * tags array.
 * @author Konnor
 */
public class CardList {
	protected int id;
	protected String name;
	protected String[] tags;
	protected boolean loaded = false;
	protected ArrayList<Card[]> cards;
	protected String desc;
	protected String notes;
	protected String path;
	protected static CardData dataRef = CardData.getCardData();
	
	/**
	 * Constructs a default CardList.
	 */
	public CardList () {
		this.id = -1;
		this.name = "";
		this.tags = null;
		this.cards = null;
		this.desc = "";
		this.notes = "";
		this.path = null;
	}
	
	/**
	 * Constructs a CardList with specified attributes.
	 * @param id     The CardList's ID number
	 * @param name   The name of the CardList
	 * @param tags   The list of tags with which to construct the list
	 * @param desc   The description of the CardList
	 * @param notes  The developer notes attached to the CardList
	 * @param loadImmediately  If true, load the cards during construction rather than manually afterwards
	 */
	public CardList (int id, String name, String[] tags, String desc, String notes, boolean loadImmediately) {
		this.id = id;
		this.name = name;
		this.tags = tags;
		this.cards = null;
		this.desc = desc;
		this.notes = notes;
		this.path = null;
		if (loadImmediately) {
			loadCards();
		}
	}
	
	/**
	 * Constructs a CardList by reading information from either an external file or database table.
	 * @param file  If true, reads from the program's directory on the SD card.  If false, reads from the database.
	 * @param path  If reading from a file, the relative path to the file to be read from; if reading from a database,
	 *   the name of the table to read the list from
	 * @param loadImmediately  If true, load the cards during construction rather than manually afterwards
	 * @throws IOException if the file cannot be found
	 * @throws MethodNotSupportedException if the user attempts to load from a database, as the function is not yet implemented
	 */
	public CardList (boolean file, String path, boolean loadImmediately) throws IOException, MethodNotSupportedException {
		this();
		if (file) {
			loadFromFile(path);
		} else {
			//loadFromDB(path);
			throw new MethodNotSupportedException("Database function not yet implemented.");
		}
		
		if (loadImmediately) {
			loadCards();
		}
	}

	
	///
	///		Getters & Setters
	///
	
	public int ID() { return id; }
	public String name() { return name; }
	public boolean complete() { return loaded; }
	public String[] tags() { return tags; }
	public ArrayList<Card[]> cards() { 
		if (cards == null) loadCards();
		return cards;
	}
	public String description() { return desc; }
	public String notes() { return notes; }
	
	public void setName(String name) { this.name = name; }
	public void setDesc(String desc) { this.desc = desc; }
	public void setNotes(String notes) { this.notes = notes; }
	public void setTags(String[] tags) {
		this.tags = tags;
		if (loaded) loadCards();
	}
	
	
	/**
	 * Loads the Card objects from the tags in the specified tag list into the list of Cards.
	 */
	public boolean loadCards() {
		this.cards = new ArrayList<Card[]>();
		
		//	check for empty tag lists
		if (tags.length == 0 || (tags.length == 1 && tags[0].equals(""))) return loaded;
		else loaded = true;
		
		for (int i = 0; i < tags.length; i++) {
			Card[] temp = parseStack(tags[i]);
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
	
	/**
	 * Loads the CardList information stored in an external file into this CardList.  Also saves the given path in this CardList.
	 * Does not load the Card objects themselves.
	 * @param path  The file from which the CardList is to get information
	 * @throws IOException in the case of a file IO error
	 */
	public void loadFromFile(String path) throws IOException {
		this.path = path;
		HashMap<String, String> map = DataOps.importFromFile(path);
		this.id = Integer.parseInt(map.get("id"));
		this.name = map.get("name");
		this.tags = map.get("tags").split("/");
		this.desc = map.get("desc");
		this.notes = map.get("notes");
	}
	
	public void loadFromDB(String table) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Database function not yet implemented.");
	}
	
	/**
	 * Saves the information of this CardList to an external file at the path stored in this CardList.  Does not save the data 
	 * for the Card objects themselves.
	 * @throws IOException if the file could not be saved
	 * @throws NullPointerException if the CardList hasn't been given a path to save to
	 */
	public void saveToFile() throws IOException, NullPointerException {
		DataOps.exportToFile(path, 
				"id;" + id +
				"\nname;" + name + 
				"\ntags;" + generateSingleTagString() + 
				"\ndesc;" + desc + 
				"\nnotes;" + notes);
	}
	
	/**
	 * Updates the stored path string of this CardList, then saves the information of this CardList to an external file.  Does 
	 * not save the data for the Card objects themselves.
	 * @param path  The file from which the CardList is to write information
	 * @throws IOException if the file could not be saved
	 * @throws NullPointerException if the CardList hasn't been given a path to save to
	 */
	public void saveToFile(String savePath) throws IOException, NullPointerException {
		path = savePath;
		saveToFile();
	}
	
	public void saveToDB(String table) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Database function not yet implemented.");
	}
	
	/**
	 * Generates an array of String objects that correspond to the tags of every card stack in the list.  Each String can
	 * possibly have multiple tags, separated by spaces, which indicate a stack of cards instead of a single card.
	 * @return  The array of tags for this CardList
	 */
	public String[] generateTagArray() {
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
		
		for (int i = 0; i < tags.length; i++) {
			if (i > 0) out.append('/');
			out.append(tags[i]);
		}
		
		return out.toString();
	}
	
	
	/**
	 * Parses a tag (or a set of tags, delimited by a space character) and generates a Card array corresponding to those input tags.  This method is
	 * intended for loading cards from a single tag (or a single stack) into the CardList.
	 * @param tags  The String that contains a tag (or set of tags, each delimited by a space) to be parsed
	 * @return      The constructed Card array
	 */
	public static Card[] parseStack(String tags) {
		String[] tagList = tags.split("\\ ");
		Card[] cardList = new Card[tagList.length];
		for (int i = 0; i < tagList.length; i++) {
			cardList[i] = dataRef.getCard(tagList[i]);
		}
		return cardList;
	}
}
