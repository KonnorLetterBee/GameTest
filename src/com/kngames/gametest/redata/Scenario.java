package com.kngames.gametest.redata;

import java.util.ArrayList;

import com.kngames.gametest.redata.CardData.Expans;
import com.kngames.gametest.redata.ScenData.GameMode;
import com.kngames.gametest.redata.CardTypes.RECard;

public class Scenario {
	private int id;
	private static int nextID = 1;
	private String name;
	private GameMode mode;
	private int expans;
	private boolean[] expansRequired;
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
		this(id, name, gameMode, expans, basics, CardData.parseSingleTagString(tagList), desc, notes);
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
		this(id, name, gameMode, expans, basics, CardData.parseSingleTagString(tagList), desc, notes);
	}

	public int getID() { return id; }
	public String getName() { return name; }
	public GameMode getMode() { return mode; }
	public int getExpans() { return expans; }
	public boolean[] getExpansRequired() { return expansRequired; }
	public ArrayList<RECard[]> getCards() { return cards; }
	public boolean useBasics() { return basics; }
	public String getDesc() { return desc; }
	public String getNotes() { return notes; }
	
	public void setName(String name) { this.name = name; }
	public void setMode(GameMode mode) { this.mode = mode; }
	public void setExpans(int expans) { this.expans = expans; }
	public void setBasics(boolean basics) { this.basics = basics; }
	public void setDesc(String desc) { this.desc = desc; }
	public void setNotes(String notes) { this.notes = notes; }
	
	public void setCards(String[] tagList) {
		this.expansRequired = new boolean[CardData.Expans.values().length];
		//	adds the list of cards specified by tags into the scenario object, then sets the proper Scenario Required flag to true
		cards = new ArrayList<RECard[]>();
		if (tagList.length == 1 && tagList[0].equals("")) return;
		
		for (int i = 0; i < tagList.length; i++) {
			RECard[] temp = CardData.generateStackByTags(tagList[i]);
			cards.add(temp);
			for(RECard card : temp)
				expansRequired[card.getExpansion()] = true;
		}
	}
	
	//	generates an array of Strings that correspond to the tags of the cards in the scenario
	public String[] generateTagStrings() {
		String[] out = new String[cards.size()];
		
		for (int i = 0; i < cards.size(); i++) {
			StringBuilder currStack = new StringBuilder();
			for (int j = 0; j < cards.get(i).length; j++) {
				if (j > 0) currStack.append(" ");
				currStack.append(CardData.generateTagString(cards.get(i)[j]));
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
