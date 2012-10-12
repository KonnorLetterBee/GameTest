package com.kngames.gametest.redata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.MethodNotSupportedException;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.CardList;
import com.kngames.gametest.engine.data.DataOps;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.data.Expansion;

public class Scenario extends CardList {
	private int mode;
	private int expans;
	private boolean[] expansRequired;
	private boolean basics;
	
	/**
	 * Constructs a default Scenario
	 */
	public Scenario () {
		super();
		this.mode = -1;
		this.expans = -1;
		this.basics = false;
	}
	
	/**
	 * Constructs a Scenario with specified attributes.
	 * @param id     The Scenario's ID number
	 * @param name   The name of the Scenario
	 * @param tags   The list of tags with which to construct the scenario
	 * @param desc   The description of the Scenario
	 * @param notes  The developer notes attached to the Scenario
	 * @param loadImmediately  If true, load the cards during construction rather than manually afterwards
	 */
	public Scenario (int id, String name, String[] tags, String desc, String notes, int mode, int expans, boolean basics, boolean loadImmediately) {
		super(id, name, tags, desc, notes, loadImmediately);
		this.mode = mode;
		this.expans = expans;
		this.basics = basics;
	}
	
	/**
	 * Constructs a Scenario by reading information from either an external file or database table.
	 * @param file  If true, reads from the program's directory on the SD card.  If false, reads from the database.
	 * @param path  If reading from a file, the relative path to the file to be read from; if reading from a database,
	 *   the name of the table to read the list from
	 * @throws IOException if the file cannot be found
	 * @throws MethodNotSupportedException if the user attempts to load from a database, as the function is not yet implemented
	 */
	public Scenario (boolean file, String path, boolean loadImmediately) throws IOException, MethodNotSupportedException {
		super(file, path, loadImmediately);
	}

	///
	///		Getters and Setters
	///
	
	public int mode() { return mode; }
	public int expans() { return expans; }
	public boolean[] expansRequired() { return expansRequired; }
	public boolean useBasics() { return basics; }
	
	public void setMode(int mode) { this.mode = mode; }
	public void setExpans(int expans) { this.expans = expans; }
	public void setBasics(boolean basics) { this.basics = basics; }
	
	/**
	 * Loads the Card objects from the tags in the specified tag list into the list of Cards.
	 */
	public boolean loadCards() {
		this.cards = new ArrayList<Card[]>();
		this.expansRequired = new boolean[Expansion.Expans.values().length];
		
		//	check for empty tag lists
		if (tags == null || tags.length == 0 || (tags.length == 1 && tags[0].equals(""))) return loaded;
		else loaded = true;
		
		for (int i = 0; i < tags.length; i++) {
			Card[] temp = parseStack(tags[i]);
			cards.add(temp);
			for(Card card : temp) {
				if (card == null) {	//	if the card was not found, set complete to false and stop creating the list
					loaded = false;
					return loaded;
				} else {
					expansRequired[((RECard)card).getExpansion()] = true;
				}
			}
		}
		return loaded;
	}
	
	/**
	 * Loads the Scenario information stored in an external file into this Scenario.  Does not load the Card objects themselves.
	 * @param path  The file from which the Scenario is to get information
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
		this.mode = Integer.parseInt(map.get("mode"));
		this.expans = Integer.parseInt(map.get("expans"));
		this.basics = Boolean.parseBoolean(map.get("basics"));
	}
	
	/**
	 * Saves the information of this Scenario to an external file at the path stored in this Scenario.  Does not save the data 
	 * for the Card objects themselves.
	 * @throws IOException if the file could not be saved
	 * @throws NullPointerException if the Scenario hasn't been given a path to save to
	 */
	public void saveToFile() throws IOException, NullPointerException {
		DataOps.exportToFile(path, 
				"id;" + id +
				"\nname;" + name + 
				"\ntags;" + generateSingleTagString() + 
				"\ndesc;" + desc + 
				"\nnotes;" + notes + 
				"\nmode;" + mode + 
				"\nexpans;" + expans + 
				"\nbasics;" + basics);
	}
	
	/**
	 * Updates the stored path string of this Scenario, then saves the information of this Scenario to an external file.  Does 
	 * not save the data for the Card objects themselves.
	 * @param path  The file from which the Scenario is to write information
	 * @throws IOException if the file could not be saved
	 * @throws NullPointerException if the Scenario hasn't been given a path to save to
	 */
	public void saveToFile(String savePath) throws IOException, NullPointerException {
		path = savePath;
		saveToFile();
	}
}
