package com.kngames.gametest.redata.data;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;
import android.util.Pair;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.ScenDBHelper;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.CardTypes.Mansion.*;

public class GameData {
	
	///
	///		Enums, Fields, and Lists
	///
	
	public static final String TAG = GameData.class.getSimpleName();
	
	//	enum to define which expansion a card belongs to
	//	TODO: replace with the Expansion enum permanently
	public static enum Expans {Base, Alliaces, Outbreak, Nightmare, Basic, Promo, Custom}
	private static String[] ExpansionString = {"Base Set", "Alliances", "Outbreak", "Nightmare", "Basic", "Promo", "Custom"};
	public static String expansString(int value) {
		if (value < 0 || value >= ExpansionString.length) return "";
		return ExpansionString[value];
	}
	
	public static final Expansion[] expansions = Expansion.expansions;
	public static final boolean[] expansionsEnabled = Expansion.expansEnabled;
	
	//	enum to define where an ItemCard comes from
	public static enum Origin {Inventory, Mansion, MansionWithID}
	private static String[] OriginString = {"Inventory", "Mansion", "Mansion"};
	public static String originString(int value) {
		if (value < 0 || value >= OriginString.length) return "";
		return OriginString[value];
	}
	
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
	
	public static final CharacterCard[] Characters = buildCharacterList();
	public static final WeaponCard[] Weapons = buildWeaponsList();
	public static final ActionCard[] Actions = buildActionsList();
	public static final ItemCard[] Items = buildItemsList();
	public static final MansionCard[] Mansions = buildMansionsList();
	public static final InfectionCard[] Infections = buildinfectionsList();
	public static final InfectedCharacterCard[] InfectedCharacters = buildInfectedCharacterList();
	public static final AmmunitionCard[] Ammunition = buildAmmunitionList();
	public static final Scenario[] Scenarios = buildScenarioList();
	
	public static Scenario testScenario = new Scenario (0, "test scenario", GameMode.Story, 0, true, new String[] {
			"WE01", "WE02", "WE03", "WE08", "WE09", "WE10", "WE11 WE12", "WE13 WE14", "WE15 WE16", "AC01", "AC02", "AC03", "AC04", "AC05", "AC06", "AC07", "AC08", "AC09", "AC10", "AC11", "AC12", "IT03"}, null, null);
	
	
	///
	///		Custom Scenario Structures
	///
	
	public static Pair<Integer,Scenario> customTempScenario;
	public static ScenDBHelper dbHelper;
	public static ArrayList<Pair<Integer,Scenario>> CustomScenarios = new ArrayList<Pair<Integer,Scenario>>();
	
	public static ArrayList<Pair<Integer,Scenario>> loadCustomScenarios() {
		Log.d(TAG, "loading custom scenarios");
		ArrayList<Pair<Integer,Scenario>> out = new ArrayList<Pair<Integer,Scenario>>();
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
				
				out.add(new Pair<Integer,Scenario>(id, new Scenario(0, name, gameMode, Expans.Custom, usesBasics, contents, desc, notes)));
			} while(scens.moveToNext());
		}
		scens.close();
		Log.d(TAG, "finished loading custom scenarios");
		return out;
	}
	
	
	///
	///		List Building Methods
	///
	
	public static CharacterCard[] buildCharacterList() {
		Log.d(TAG, "loading characters");
		ArrayList<CharacterCard> characters = new ArrayList<CharacterCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].characters() != null) {
				for (int j = 0; j < expansions[i].characters().length; j++) {
					characters.add(expansions[i].characters()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading characters");
		return characters.toArray(new CharacterCard[1]);
	}
	
	public static InfectedCharacterCard[] buildInfectedCharacterList() {
		Log.d(TAG, "loading infected characters");
		ArrayList<InfectedCharacterCard> infected = new ArrayList<InfectedCharacterCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].infecCharacters() != null) {
				for (int j = 0; j < expansions[i].infecCharacters().length; j++) {
					infected.add(expansions[i].infecCharacters()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading infected characters");
		return infected.toArray(new InfectedCharacterCard[1]);
	}
	
	public static WeaponCard[] buildWeaponsList() {
		Log.d(TAG, "loading weapons");
		ArrayList<WeaponCard> weapons = new ArrayList<WeaponCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].weapons() != null) {
				for (int j = 0; j < expansions[i].weapons().length; j++) {
					weapons.add(expansions[i].weapons()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading weapons");
		return weapons.toArray(new WeaponCard[1]);
	}
	
	public static ActionCard[] buildActionsList() {
		Log.d(TAG, "loading actions");
		ArrayList<ActionCard> actions = new ArrayList<ActionCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].actions() != null) {
				for (int j = 0; j < expansions[i].actions().length; j++) {
					actions.add(expansions[i].actions()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading actions");
		return actions.toArray(new ActionCard[1]);
	}
	
	public static ItemCard[] buildItemsList() {
		Log.d(TAG, "loading items");
		ArrayList<ItemCard> items = new ArrayList<ItemCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].items() != null) {
				for (int j = 0; j < expansions[i].items().length; j++) {
					items.add(expansions[i].items()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading items");
		return items.toArray(new ItemCard[1]);
	}
	
	public static MansionCard[] buildMansionsList() {
		Log.d(TAG, "loading mansions");
		ArrayList<MansionCard> mansions = new ArrayList<MansionCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].mansion() != null) {
				for (int j = 0; j < expansions[i].mansion().length; j++) {
					mansions.add(expansions[i].mansion()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading mansions");
		return mansions.toArray(new MansionCard[1]);
	}
	
	public static InfectionCard[] buildinfectionsList() {
		Log.d(TAG, "loading infections");
		ArrayList<InfectionCard> infections = new ArrayList<InfectionCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].infections() != null) {
				for (int j = 0; j < expansions[i].infections().length; j++) {
					infections.add(expansions[i].infections()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading infections");
		return infections.toArray(new InfectionCard[1]);
	}
	
	public static AmmunitionCard[] buildAmmunitionList() {
		Log.d(TAG, "loading ammunition");
		ArrayList<AmmunitionCard> ammunition = new ArrayList<AmmunitionCard>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].ammunition() != null) {
				for (int j = 0; j < expansions[i].ammunition().length; j++) {
					ammunition.add(expansions[i].ammunition()[j]);
				}
			}
		}
		Log.d(TAG, "finished loading ammunition");
		return ammunition.toArray(new AmmunitionCard[1]);
	}
	
	public static Scenario[] buildScenarioList() {
		Log.d(TAG, "loading scenarios");
		ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
		for (int i = 0; i < expansions.length; i++) {
			if (expansionsEnabled[i] == true && expansions[i].scenarios() != null) {
				for (int j = 0; j < expansions[i].scenarios().length; j++) {
					Scenario temp = expansions[i].scenarios()[j];
					if (temp.complete()) {
						scenarios.add(temp);
					}
				}
			}
		}
		Log.d(TAG, "finished loading scenarios");
		return scenarios.toArray(new Scenario[1]);
	}
	
	
	///
	///		Search Methods
	///
	
	//	searches a specified array in the card collection for a card with a specified name in a specified expansion (use -1 to search regardless of expansion)
	public static RECard findCard(String name, CardType type, int expans) {
		switch (type) {
		case Character:
			for (int i = 0; i < Characters.length; i++) {
				if ((expans == -1 || expans == Characters[i].getExpansion()) && Characters[i].getName().equalsIgnoreCase(name)) return Characters[i];
			} break;
		case InfecChar:
			for (int i = 0; i < InfectedCharacters.length; i++) {
				if ((expans == -1 || expans == InfectedCharacters[i].getExpansion()) && InfectedCharacters[i].getName().equalsIgnoreCase(name)) return InfectedCharacters[i];
			} break;
		case Mansion:
			for (int i = 0; i < Mansions.length; i++) {
				if ((expans == -1 || expans == Mansions[i].getExpansion()) && Mansions[i].getName().equalsIgnoreCase(name)) return Mansions[i];
			} break;
		case Weapon:
			for (int i = 0; i < Weapons.length; i++) {
				if ((expans == -1 || expans == Weapons[i].getExpansion()) && Weapons[i].getName().equalsIgnoreCase(name)) return Weapons[i];
			} break;
		case Action:
			for (int i = 0; i < Actions.length; i++) {
				if ((expans == -1 || expans == Actions[i].getExpansion()) && Actions[i].getName().equalsIgnoreCase(name)) return Actions[i];
			} break;
		case Item:
			for (int i = 0; i < Items.length; i++) {
				if ((expans == -1 || expans == Items[i].getExpansion()) && Items[i].getName().equalsIgnoreCase(name)) return Items[i];
			} break;
		case Infection:
			for (int i = 0; i < Infections.length; i++) {
				if ((expans == -1 || expans == Infections[i].getExpansion()) && Infections[i].getName().equalsIgnoreCase(name)) return Infections[i];
			} break;
		case Ammunition:
			for (int i = 0; i < Ammunition.length; i++) {
				if ((expans == -1 || expans == Ammunition[i].getExpansion()) && Ammunition[i].getName().equalsIgnoreCase(name)) return Ammunition[i];
			} break;
		}
		return null;
	}
	
	//	searches a specified array in the card collection for a card with a specified id number in a specified expansion (use -1 to search regardless of expansion)
	public static RECard findCard(int id, CardType type, int expans) {
		switch (type) {
		case Character:
			for (int i = 0; i < Characters.length; i++) {
				if ((expans == -1 || expans == Characters[i].getExpansion()) && Characters[i].getID() == id) return Characters[i];
			} break;
		case InfecChar:
			for (int i = 0; i < InfectedCharacters.length; i++) {
				if ((expans == -1 || expans == InfectedCharacters[i].getExpansion()) && InfectedCharacters[i].getID() == id) return InfectedCharacters[i];
			} break;
		case Mansion:
			for (int i = 0; i < Mansions.length; i++) {
				if ((expans == -1 || expans == Mansions[i].getExpansion()) && Mansions[i].getID() == id) return Mansions[i];
			} break;
		case Weapon:
			for (int i = 0; i < Weapons.length; i++) {
				if ((expans == -1 || expans == Weapons[i].getExpansion()) && Weapons[i].getID() == id) return Weapons[i];
			} break;
		case Action:
			for (int i = 0; i < Actions.length; i++) {
				if ((expans == -1 || expans == Actions[i].getExpansion()) && Actions[i].getID() == id) return Actions[i];
			} break;
		case Item:
			for (int i = 0; i < Items.length; i++) {
				if ((expans == -1 || expans == Items[i].getExpansion()) && Items[i].getID() == id) return Items[i];
			} break;
		case Infection:
			for (int i = 0; i < Infections.length; i++) {
				if ((expans == -1 || expans == Infections[i].getExpansion()) && Infections[i].getID() == id) return Infections[i];
			} break;
		case Ammunition:
			for (int i = 0; i < Ammunition.length; i++) {
				if ((expans == -1 || expans == Ammunition[i].getExpansion()) && Ammunition[i].getID() == id) return Ammunition[i];
			} break;
		}
		return null;
	}
	
	//	searches the Scenarios array for a Scenario with the specified id
	//	returns null if nothing was found
	public static Scenario findScenario(int id, boolean includeCustoms) {
		for (int i = 0; i < Scenarios.length; i++) {
			if (Scenarios[i].getID() == id) return Scenarios[i];
		}
		if (includeCustoms)
			for (int i = 0; i < CustomScenarios.size(); i++) {
				if (CustomScenarios.get(i).second.getID() == id) return CustomScenarios.get(i).second;
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
		for (MansionCard c : GameData.Mansions) {
			if (c.getExpansion() == mansionNum) {
				for (int i = 0; i < c.getDeckQuantity(); i++) {
					mansion.addTop(c);
				}
			}
		}
		
		//	search for matching Item cards
		for (ItemCard c : GameData.Items) {
			ItemCard ic = (ItemCard)c;
			if (c.getExpansion() == mansionNum && (ic.getOrigin() == 1 || ic.getOrigin() == 2)) {
				for (int i = 0; i < c.getDeckQuantity(); i++) {
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
	
	//	searches all arrays for a card with the specified 4-character tag
	//	returns null if nothing was found
	public static RECard findByCardTag(String tag) {
		String type = tag.substring(0, 2);
		int id = Integer.parseInt(tag.substring(2));
		
		if (type.equalsIgnoreCase("CH")) {
			RECard temp = findCard(id, CardType.Character, -1);
			if (temp == null) temp = findCard(id, CardType.InfecChar, -1);
			return temp;
		}
		else if (type.equalsIgnoreCase("PR")) return findCard(id, CardType.Character, 5);
		else if (type.equalsIgnoreCase("WE")) return findCard(id, CardType.Weapon, -1);
		else if (type.equalsIgnoreCase("AC")) return findCard(id, CardType.Action, -1);
		else if (type.equalsIgnoreCase("IT")) return findCard(id, CardType.Item, -1);
		else if (type.equalsIgnoreCase("MA")) return findCard(id, CardType.Mansion, -1);
		else if (type.equalsIgnoreCase("AM")) return findCard(id, CardType.Ammunition, -1);
		
		return null;
	}
	
	//	generates an array of RECard objects that contain the tags, delimited with spaces, of the input
	public static RECard[] generateStackByTags(String tags) {
		String[] tagList = tags.split("\\ ");
		RECard[] cardList = new RECard[tagList.length];
		for (int i = 0; i < tagList.length; i++) {
			cardList[i] = findByCardTag(tagList[i]);
		}
		return cardList;
	}
	
	//	generates an ID tag for this card
	public static String generateTagString(RECard card) {
		StringBuilder out = new StringBuilder();
		switch (card.getCardType()) {
			case Character:  out.append("CH"); break;
			case Weapon: 	 out.append("WE"); break;
			case Action: 	 out.append("AC"); break;
			case Item: 		 out.append("IT"); break;
			case Infected: 	 out.append("MA"); break;
			case Token: 	 out.append("MA"); break;
			case Event:		 out.append("MA"); break;
			case Ammunition: out.append("AM"); break;
		}
		out.append(String.format("%02d", card.getID()));
		return out.toString();
	}
	
	//	generates a single String containing the contents of a tag array delimited with a '/' to separate cards
	public static String generateSingleTagString(String[] tags) {
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
