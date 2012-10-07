package com.kngames.gametest.redata;

import java.util.ArrayList;

import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.GameData;
import com.kngames.gametest.redata.data.Expansion.Expans;
import com.kngames.gametest.redata.data.GameData.GameMode;
import com.kngames.gametest.redata.CardTypes.RECard;

public class Scenario {
	private int id;
	private static int nextID = 1;
	private String name;
	private GameMode mode;
	private int expans;
	private boolean[] expansRequired;
	private boolean complete = true;
	private ArrayList<RECard[]> cards;
	private boolean basics;
	private String desc;
	private String notes;
	
	//	creates a new scenario object and loads it with cards from a list of tags (see CardData)
	public Scenario (int id, String name, GameMode gameMode, Expans expans, boolean basics, String[] tagList, String desc, String notes) {
		this.id = nextID;
		nextID++;
		this.name = name;
		this.mode = gameMode;
		this.expans = expans.ordinal();
		this.desc = desc;
		this.notes = notes;
		this.basics = basics;
		this.setCards(tagList);
	}
	
	//	creates a new scenario object and loads it with cards from a parsable tag string (see CardData)
	public Scenario (int id, String name, GameMode gameMode, Expans expans, boolean basics, String tagList, String desc, String notes) {
		this(id, name, gameMode, expans, basics, GameData.parseSingleTagString(tagList), desc, notes);
	}
	
	//	creates a new scenario object and loads it with cards from a list of tags (see CardData)
	public Scenario (int id, String name, GameMode gameMode, int expans, boolean basics, String[] tagList, String desc, String notes) {
		this.id = nextID;
		nextID++;
		this.name = name;
		this.mode = gameMode;
		this.expans = expans;
		this.desc = desc;
		this.notes = notes;
		this.basics = basics;
		this.setCards(tagList);
	}
	
	//	creates a new scenario object and loads it with cards from a parsable tag string (see CardData)
	public Scenario (int id, String name, GameMode gameMode, int expans, boolean basics, String tagList, String desc, String notes) {
		this(id, name, gameMode, expans, basics, GameData.parseSingleTagString(tagList), desc, notes);
	}

	public int id() { return id; }
	public String name() { return name; }
	public GameMode mode() { return mode; }
	public int expans() { return expans; }
	public boolean[] expansRequired() { return expansRequired; }
	public boolean complete() { return complete; }
	public ArrayList<RECard[]> cards() { return cards; }
	public boolean useBasics() { return basics; }
	public String description() { return desc; }
	public String notes() { return notes; }
	
	public void setName(String name) { this.name = name; }
	public void setMode(GameMode mode) { this.mode = mode; }
	public void setExpans(int expans) { this.expans = expans; }
	public void setBasics(boolean basics) { this.basics = basics; }
	public void setDesc(String desc) { this.desc = desc; }
	public void setNotes(String notes) { this.notes = notes; }
	
	public void setCards(String[] tagList) {
		this.expansRequired = new boolean[Expansion.Expans.values().length];
		//	adds the list of cards specified by tags into the scenario object, then sets the proper Scenario Required flag to true
		cards = new ArrayList<RECard[]>();
		if (tagList.length == 1 && tagList[0].equals("")) return;
		
		for (int i = 0; i < tagList.length; i++) {
			RECard[] temp = GameData.generateStackByTags(tagList[i]);
			cards.add(temp);
			for(RECard card : temp) {
				if (card != null) expansRequired[card.getExpansion()] = true;
				else {	//	if the card was not found, set complete to false and stop creating the list
					complete = false;
					return;
				}
			}
		}
	}
	
	//	generates an array of Strings that correspond to the tags of the cards in the scenario
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
	
	//	generates a single String containing the tags of the cards in the scenario delimited with a '/' to separate piles
	public String generateSingleTagString() {
		String[] tags = generateTagStrings();
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < tags.length; i++) {
			if (i > 0) out.append("/");
			out.append(tags[i]);
		}
		return out.toString();
	}
}
