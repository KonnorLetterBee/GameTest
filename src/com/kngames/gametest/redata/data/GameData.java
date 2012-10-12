package com.kngames.gametest.redata.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.Pair;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.CardData;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.ScenDBHelper;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.Mansion.*;
import com.kngames.gametest.redata.data.Expansion.Expans;

public class GameData {
	
	///
	///		Enums, Fields, and Lists
	///
	
	public static final String TAG = GameData.class.getSimpleName();
	
	public static final Expansion[] expansions = Expansion.expansions;
	public static final boolean[] expansionsEnabled = Expansion.expansEnabled;
	
	//	enum to define what game mode a scenario is intended for
	public static enum GameMode {Story, Mercenary, Versus, Outbreak, PartnerStory}
	public static final String[] GameModeString = {"Story", "Mercenary", "Versus", "Outbreak", "Story (Partners)"};
	public static final boolean[] GameModesAllowed = { true, false, false, false, false };
	public static String getGameModeString(int value) {
		if (value < 0 || value >= GameModeString.length) return "";
		return GameModeString[value];
	}
	
	///
	///		Data Lists
	///
	
	private static CardData data;
	
	public static Scenario[] Scenarios;
	public static Scenario testScenario;
	
	
	///
	///		Custom Scenario Structures
	///
	
	public static Pair<Integer,Scenario> customTempScenario;
	public static ScenDBHelper dbHelper;
	public static ArrayList<Pair<Integer,Scenario>> CLCustomScenarios = new ArrayList<Pair<Integer,Scenario>>();
	
	public static void loadCustomScenarios() {
		Log.d(TAG, "loading custom scenarios");
		CLCustomScenarios = new ArrayList<Pair<Integer,Scenario>>();
		Cursor scens = dbHelper.getAllScenarios();
		//	extract data from Cursor object
		if (scens.moveToFirst()) {
			do {
				int id = scens.getInt(0);
				String name = scens.getString(1);
				String contents = scens.getString(2);
				String desc = scens.getString(3);
				String notes = scens.getString(4);
				GameMode gameMode = GameMode.values()[scens.getInt(5)];
				
				boolean usesBasics;
				int temp = scens.getInt(6);
				if (temp == 0) usesBasics = false;
				else usesBasics = true;
				
				CLCustomScenarios.add(new Pair<Integer,Scenario>(id, new Scenario(id, name, contents.split("\\/"), desc, notes, gameMode.ordinal(), Expans.Custom.ordinal(), usesBasics, false)));
			} while(scens.moveToNext());
		}
		scens.close();
		Log.d(TAG, "finished loading custom scenarios");
	}
	
	
	///
	///		Initialization
	///
	
	private static boolean initialized = false;
	public static void initialize(Context context) {
		if (!initialized) {
			data = CardData.initCardData();
			
			//	load characters and infected characters
			Log.d(TAG, "loading characters");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true) {
					if (expansions[i].characters() != null) {
						for (int j = 0; j < expansions[i].characters().length; j++) {
							data.addCard(expansions[i].characters()[j]);
					}}
					if (expansions[i].infecCharacters() != null) {
						for (int j = 0; j < expansions[i].infecCharacters().length; j++) {
							data.addCard(expansions[i].infecCharacters()[j]);
					}}
				}
			}	Log.d(TAG, "finished loading characters");
			
			//	load weapons
			Log.d(TAG, "loading weapons");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].weapons() != null) {
					for (int j = 0; j < expansions[i].weapons().length; j++) {
						data.addCard(expansions[i].weapons()[j]);
					}
				}
			}	Log.d(TAG, "finished loading weapons");
			
			//	load actions
			Log.d(TAG, "loading actions");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].actions() != null) {
					for (int j = 0; j < expansions[i].actions().length; j++) {
						data.addCard(expansions[i].actions()[j]);
					}
				}
			}	Log.d(TAG, "finished loading actions");
			
			//	load items
			Log.d(TAG, "loading items");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].items() != null) {
					for (int j = 0; j < expansions[i].items().length; j++) {
						data.addCard(expansions[i].items()[j]);
					}
				}
			}	Log.d(TAG, "finished loading items");
			
			//	load mansion cards
			Log.d(TAG, "loading mansions");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].mansion() != null) {
					for (int j = 0; j < expansions[i].mansion().length; j++) {
						data.addCard(expansions[i].mansion()[j]);
					}
				}
			}	Log.d(TAG, "finished loading mansions");
			
			//	load infections
			Log.d(TAG, "loading infections");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].infections() != null) {
					for (int j = 0; j < expansions[i].infections().length; j++) {
						data.addCard(expansions[i].infections()[j]);
					}
				}
			}	Log.d(TAG, "finished loading infections");
			
			//	load ammunition
			Log.d(TAG, "loading ammunition");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].ammunition() != null) {
					for (int j = 0; j < expansions[i].ammunition().length; j++) {
						data.addCard(expansions[i].ammunition()[j]);
					}
				}
			}	Log.d(TAG, "finished loading ammunition");
			
			//	load skills
			Log.d(TAG, "loading skills");
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].skills() != null) {
					for (int j = 0; j < expansions[i].skills().length; j++) {
						data.addCard(expansions[i].skills()[j]);
					}
				}
			}	Log.d(TAG, "finished loading skills");
			
			//	load clscenarios
			Log.d(TAG, "loading Scenarios");
			ArrayList<Scenario> clscenarios = new ArrayList<Scenario>();
			for (int i = 0; i < expansions.length; i++) {
				if (expansionsEnabled[i] == true && expansions[i].clScenarios() != null) {
					for (int j = 0; j < expansions[i].clScenarios().length; j++) {
						clscenarios.add(expansions[i].clScenarios()[j]);
					}
				}
			}	Log.d(TAG, "finished loading Scenarios");
			Scenarios = clscenarios.toArray(new Scenario[1]);
					
			dbHelper = new ScenDBHelper(context);
			GameData.loadCustomScenarios();
			
			testScenario = new Scenario (0, "test scenario", new String[] {"WE;01", "WE;02", "WE;03", "WE;08", "WE;09", "WE;10", "WE;11 WE;12", "WE;13 WE;14", "WE;15 WE;16", 
					"AC;01", "AC;02", "AC;03", "AC;04", "AC;05", "AC;06", "AC;07", "AC;08", "AC;09", "AC;10", "AC;11", "AC;12", "IT;03"}, null, null, GameMode.Story.ordinal(), 0, true, false);
			initialized = true;
		}
	}
	
	
	///
	///		Search Methods
	///
	
	//	searches the Scenarios array for a Scenario with the specified id
	//	returns null if nothing was found
	public static Scenario findScenario(int id, boolean includeCustoms) {
		for (int i = 0; i < Scenarios.length; i++) {
			if (Scenarios[i].ID() == id) return Scenarios[i];
		}
		return null;
	}
	
	
	///
	///		Misc. Methods
	///
	
	//	searches the GameData's Mansion list and Item list for every card with the selected expansions (and in 
	//	Item's case, if it should be in the Mansion) and creates a new Deck with those cards, preshuffled
	//	uses the array mansionsToUse
	public static REDeck populateMansion(int mansionNum) {
		REDeck mansion = new REDeck();
		
		//	search for matching Mansion cards
		Card[] cards = CardData.getCardData().getCategory("MA");
		for (Card c : cards) {
			if (((MansionCard)c).getExpansion() == mansionNum) {
				for (int i = 0; i < ((MansionCard)c).getDeckQuantity(); i++) {
					mansion.addTop(c);
				}
			}
		}
		
		//	search for matching Item cards
		cards = CardData.getCardData().getCategory("IT");
		for (Card c : cards) {
			ItemCard ic = (ItemCard)c;
			if (((ItemCard)c).getExpansion() == mansionNum && (ic.getOrigin() == 1 || ic.getOrigin() == 2)) {
				for (int i = 0; i < ((ItemCard)c).getDeckQuantity(); i++) {
					mansion.addTop(c);
				}
			}
		}
				
		//	shuffle the mansion, flip it, and return the new Deck object
		mansion.shuffle(2);
		mansion.flipDeck();
		return mansion;
	}
	
	
	///
	///		Tag Methods
	///
	
	//	generates an array of RECard objects that contain the tags, delimited with spaces, of the input
	public static RECard[] generateStackByTags(String tags) {
		String[] tagList = tags.split("\\ ");
		RECard[] cardList = new RECard[tagList.length];
		for (int i = 0; i < tagList.length; i++) {
			cardList[i] = (RECard) data.getCard(tagList[i]);
		}
		return cardList;
	}
	
	//	generates a single String containing the contents of a tag array delimited with a '/' to separate cards
	public static String generateStackTags(String[] tags) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < tags.length; i++) {
			if (i > 0) out.append("/");
			out.append(tags[i]);
		}
		return out.toString();
	}
	
	//	generates a single String containing the contents of a tag array delimited with a '/' to separate cards
	public static String[] parseSingleTagString(String tags) {
		return tags.split("\\/");
	}
}
