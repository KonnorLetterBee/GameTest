package com.kngames.gametest.redata;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Pair;

import com.kngames.gametest.redata.CardData.Expans;

public class ScenData {
	public static enum GameMode {Story, Mercenary, Versus, Outbreak, PartnerStory}
	public static final String[] GameModeString = {"Story", "Mercenary", "Versus", "Outbreak", "Story (Partners)"};
	public static String getGameModeString(int value) {
		if (value < 0 || value >= GameModeString.length) return "";
		return GameModeString[value];
	}
	
	public static final Scenario[] Scenarios = {
		new Scenario (1, "First Timer", GameMode.Story, 0, true, new String[] {
				"AC07", "AC01", "AC04", "AC02", "AC03", "AC06", "AC05", "WE01", "WE08", "WE13 WE14", "WE15 WE16", "WE11 WE12"},
				"", null),
		new Scenario (2, "Classic Horror", GameMode.Story, 0, true, new String[] {
				"AC07", "AC01", "AC02", "AC03", "AC05", "AC09", "IT03", "WE02", "WE01", "WE13 WE14", "WE11 WE12", "WE03"},
				"Featuring many of the Weapons and Item cards found in the original Resident Evil game.  The Player who manages their Inventory effectively will find themselves closer to escaping.", null),
		new Scenario (3, "Spy Games", GameMode.Story, 0, true, new String[] {
				"AC07", "AC10", "AC04", "AC11", "AC09", "AC02", "IT03", "WE02", "WE01", "WE08", "WE13 WE14", "WE11 WE12"},
				"A few new cards are added to this setup, including a card that allows Players to steal Weaponry from the other survivors, adding to their own armory of Infected destruction.", null),
		new Scenario (4, "Whaddya Buyin?", GameMode.Story, 0, true, new String[] {
				"AC06", "AC10", "AC05", "AC02", "AC04", "AC11", "WE03", "WE01", "WE08", "WE13 WE14", "WE11 WE12", "WE15 WE16"},
				"With a change in scenery comes new Resources available for purchase just in time as the Infected have rallied.  The Merchant appears in the most unlikely of places to offer you \"A lot of good things for sale, Stranger.\"", null),
		new Scenario (5, "Battle-Hardened Veteran", GameMode.Story, 0, true, new String[] {
				"AC06", "AC07", "AC01", "AC03", "AC05", "AC02", "WE03", "WE02", "WE08", "WE13 WE14", "WE11 WE12", "WE15 WE16"},
				"You have survived this long, only to face a final surge of Infected.  Only those with strong wills and brave hearts will prove themselves able to stand at the top and escape with their lives.", null),
		new Scenario (6, "Special Forces", GameMode.Mercenary, 0, true, new String[] {
				"AC02", "AC09", "AC07", "IT03", "AC03", "AC11", "AC10", "AC05", "WE01", "WE13 WE14", "WE11 WE12", "WE08"},
				"Being quick on your feet and managing your Teams Inventory will be the key to this battle.  If you find yourself running low on Health, you can always have one of your teammates Buy you a First Aid Spray.", null),
		new Scenario (7, "Limited Movement", GameMode.Mercenary, 0, true, new String[] {
				"AC02", "AC06", "AC09", "AC01", "AC11", "AC10", "AC03", "WE01", "WE08", "WE15 WE16", "WE11 WE12", "WE13 WE14"},
				"Make no wasted movements as your Team will be down to one or two Actions per turn.  Make every card count and you will come out victorious!", null),
		new Scenario (8, "Jack Of All Trades", GameMode.Mercenary, 0, true, new String[] {
				"AC04", "AC05", "AC02", "AC09", "AC3", "AC11", "AC06", "WE01", "WE13 WE14", "WE15 WE16", "WE11 WE12", "WE08"},
				"", null),
		new Scenario (9, "Quick And Easy", GameMode.Versus, 0, true, new String[] {
				"AC06", "AC01", "AC03", "AC09", "AC05", "AC02", "AC12", "WE01", "WE03", "WE15 WE16", "WE13 WE14", "WE08"},
				"Bigger is not always better, but beware the Magnum, a gun that will certainly make anyone's day.", null),
		new Scenario (10, "Big Guns, Big Explosions", GameMode.Versus, 0, true, new String[] {
				"AC10", "AC12", "AC03", "AC01", "AC08", "WE01", "WE08", "WE02", "WE03", "WE13 WE14", "WE15 WE16", "WE11 WE12"},
				"Use this setup if your play style is to Buy everything that explodes or destroys.  More defensive cards are added, along with more 'Trash' abilities and larger Damage dealing Weapons.", null),
		new Scenario (11, "I've Covered Wars", GameMode.Versus, 0, true, new String[] {
				"AC11", "AC12", "AC08", "AC10", "AC01", "AC03", "WE01", "WE08", "WE02", "WE09", "WE13 WE14", "WE15 WE16", "WE10"},
				"Feel like you can handle it all? With this setup, the most powerful Weapons in the game, the Rocket Launcher and the Gatling Gun, join the Resource Piles. Drop one of these on your friends and they won't be able to recover before you put them down for good.", null),
		new Scenario (12, "Initial Horror", GameMode.Story, 1, true, new String[] {
				"AC17", "AC21", "AC22", "AC19", "AC14", "AC18", "IT04", "WE19", "WE20 WE21", "WE17 WE18", "WE22 WE23"},
				"", null),
		new Scenario (13, "Bound Advance", GameMode.Story, 1, true, new String[] {
				"AM04", "WE19", "AC21", "WE20 WE21", "AC22", "AC19", "IT04", "WE24", "AC14", "WE25", "WE17 WE18", "WE22 WE23"},
				"Providing Players with little to no Drawing ability, this setup will strain resources as they attempt to make the most of their initial 5 cards.", null),
		new Scenario (14, "Backlog", GameMode.Story, 1, true, new String[] {
				"AC18", "WE19", "AC21", "WE20 WE21", "AC22", "AC13", "AC15", "WE24", "AC14", "AC16", "WE17 WE18", "WE22 WE23"},
				"Players will find themselves with a bit too many Resources as they battle to control their Inventory sizes with this setup.", null),
		new Scenario (15, "Fast Action", GameMode.Story, 1, true, new String[] {
				"AM04", "WE19", "AC13", "WE20 WE21", "AC21", "AC17", "AC22", "WE24", "AC14", "AC19", "WE17 WE18", "WE22 WE23"},
				"Feel like ending the game quickly? This setup will allow Players to build up their Inventory the quickest and have everything they need when they decide to Explore the Mansion.", null),
		new Scenario (16, "Lethal Injection", GameMode.Story, 1, true, new String[] {
				"AM04", "WE02", "AC10", "AC02", "WE20 WE21", "WE08", "AC17", "AC19", "AC09", "AC05", "WE11 WE12", "WE22 WE23"},
				"Ignore all those tiny weapons and go for nothing but the best! Use your Treasure to get the large Weaponry you need to annihilate the Infected.", null),
		new Scenario (17, "Top of the Class", GameMode.Story, 1, true, new String[] {
				"WE01 WE17 WE18", "WE02", "AC10", "AC02", "WE20 WE21 WE13 WE14", "WE08", "AC18", "AC03", "AC09", "AC05", "WE22 WE23", "WE19 WE15 WE16"},
				"Combining the best of the best from Sets 1 and 2, this set up will give you everything you need to rip through the Mansion.", null),
		new Scenario (18, "Infested Supply Depot", GameMode.Mercenary, 1, true, new String[] {
				"AC18", "WE19", "AC21", "WE20 WE21", "AC22", "AC15", "AC17", "WE24", "AC16", "AC19", "WE17 WE18", "WE22 WE23"},
				"Featuring all the Weapons you could ever need, this abandoned Supply Depot guarantees one thing: Mass Destruction.", null),
		new Scenario (19, "Infested Shopping Mall", GameMode.Mercenary, 1, true, new String[] {
				"IT04", "WE19", "AC21", "WE20 WE21", "AC22", "AC15", "AC17", "AC13", "AM04", "AC19", "AC14", "WE22 WE23"},
				"Welcome to Raccoon City Mall, where anything great is located, everything is on sale, and everyone greets you with rotting teeth and a monstrous hunger! We've got what cures your hunger for Infected destruction!", null),
		new Scenario (20, "Partners in Escape", GameMode.PartnerStory, 1, true, new String[] {
				"AC13", "WE19", "AC20", "WE20 WE21", "IT04", "AC15", "AC14", "WE24", "WE17 WE18", "AC16", "WE22 WE23", "AC19"},
				"An Introduction to the Partner Modification, many cards that had limited uses before are completely changed when adding in more Characters.", null),
		new Scenario (21, "A Little Gun Goes A Long Way", GameMode.PartnerStory, 1, true, new String[] {
				"AC21", "AC22", "AC20", "AC18", "IT04", "AC13", "AC14", "WE24", "AC16", "WE17 WE18", "WE25", "AC19"},
				"Sometimes, the person who is more accurate is the one who survives, as proven by this Scenario.", null),
		new Scenario (22, "Tactical Advance", GameMode.PartnerStory, 1, true, new String[] {
				"AC22", "WE19", "AC18", "WE20 WE21", "AC17", "AC15", "AC14", "WE24", "AC13", "AM04", "WE22 WE23", "AC19"},
				"Having more cards in your Hand will demand smarter choices be made, less your Partner be killed.", null),
		new Scenario (23, "Outbreak", GameMode.Outbreak, 2, true, new String[] {
				"WE15 WE16", "WE29 WE30", "WE28", "WE13 WE14", "AC26", "AC24", "AC01", "AC25", "AC23", "AC02", "AC06", "AC03", "AC04"},
				"", null),
		new Scenario (24, "Dawn of Nightmares", GameMode.Outbreak, 2, true, new String[] {
				"WE19", "WE20 WE21", "AC19", "AC24", "AC23", "AC25", "AC17", "AC21", "AC22", "WE29 WE30", "AC26", "WE22 WE23"},
				"", null),
		new Scenario (25, "Infection Balance", GameMode.Outbreak, 2, true, new String[] {
				"WE02", "WE15 WE16", "AC10", "AC27", "AC02", "AC04", "AC09", "AC23", "AC26", "AC03", "WE28", "WE29", "WE13 WE14"},
				"Learn to master the Virus within you in order to get the most out of this Scenario.", null),
		new Scenario (26, "Duplicity", GameMode.Outbreak, 2, true, new String[] {
				"WE26 WE27", "AC05", "WE28", "AC02", "WE15", "AC30", "AC04", "AC26", "AC03", "WE03", "WE29 WE30", "WE13 WE14"},
				"Sometimes having multiple copies of the same card isn't a bad idea...", null),
		new Scenario (27, "Embrace the Darkness", GameMode.Outbreak, 2, true, new String[] {
				"WE01", "AC27", "AC10", "AC02", "AC23", "WE08", "AC03", "AC31", "AC28", "WE03", "WE15 WE16", "WE13 WE14"},
				"Being an Infected isn't all that bad..... is it?", null),
		new Scenario (28, "Quick Game", GameMode.Outbreak, 2, true, new String[] {
				"WE22 WE23", "AC29", "WE20 WE21", "WE29 WE30", "AC15", "AM04", "AC19", "AC17", "AC30", "AC31", "WE24", "WE25"},
				"Don't have the full hour to play? Use this Scenario!", null),
		new Scenario (29, "Trouble Around Every Corner", GameMode.Outbreak, 2, true, new String[] {
				"WE22 WE23", "WE25", "AC24", "WE26 WE27", "WE20 WE21", "AC18", "WE29 WE30", "AC19", "WE28", "AC31", "AC25", "AC21"},
				"Find inventive ways to keep your opponent's away from winning!", null),
		new Scenario (30, "Resident Reaper", GameMode.Outbreak, 2, true, new String[] {
				"WE25", "WE26 WE27", "WE24", "AC26", "WE29 WE30", "WE28", "AC19", "WE22 WE23", "WE17", "AC17", "WE19", "WE20 WE21"},
				"Arm yourself to the teeth with this near Weapons only setup!", null),
		new Scenario (31, "Explosions and Flames", GameMode.Outbreak, 2, true, new String[] {
				"WE01", "WE24", "WE03", "WE31", "AC22", "WE28", "AC03", "AC27", "AM04", "WE26 WE27", "WE02", "WE22 WE23"},
				"Hardly any Ammo is required in this quirky Scenario.", null),
		new Scenario (32, "Mega Money", GameMode.Outbreak, 2, true, new String[] {
				"AC09", "AC06", "WE08", "AC29", "WE24", "WE20 WE21", "AM04", "AC03", "AC21", "WE02", "WE29 WE30", "AC30"},
				"", null),
		new Scenario (33, "A Gun Is a Gun", GameMode.Outbreak, 2, true, new String[] {
				"WE01 WE17 WE18", "WE25 WE26 WE27", "AC23", "WE08 WE29 WE30", "WE22 WE23 WE11 WE12", "AC25", "WE13 WE14 WE20 WE21", "WE15 WE16 WE19", "AC28", "AC04", "AC03", "AC21"},
				"Pushing all Weapon types into the same Resource Pile gives Players a much different game than normal...", null),
		new Scenario (34, "Special Forces", GameMode.Mercenary, 2, true, new String[] {
				"IT03", "WE13 WE14", "AC03", "WE01", "WE08", "AC07", "AC02", "AC05", "AC30", "AC29", "AC10", "WE11 WE12"},
				"Being quick on your feet and managing your Team's Inventory will be the key to this battle. If you find yourself running low on Health, you can always have one of your teammates Buy a First Aid Spray.", null),
		new Scenario (35, "Jack of All Trades", GameMode.Mercenary, 2, true, new String[] {
				"AC04", "AC03", "WE08", "WE13 WE14", "WE15 WE16", "WE01", "AC09", "AC29", "AC02", "AC24", "AC30", "WE11 WE12"},
				"A little bit of everything can be found in this scenario.", null),
		new Scenario (36, "Limited Movement", GameMode.Mercenary, 2, true, new String[] {
				"WE15 WE16", "WE13 WE14", "AC06", "AC09", "AC24", "AC11", "AC02", "AC30", "AC03", "WE01", "WE29 WE30", "WE11 WE12"},
				"Make no wasted movements as your Team will be down to one or two Actions per turn. Make every card count and you will come out victorious!", null),
		new Scenario (37, "Infested Supply Depot", GameMode.Mercenary, 2, true, new String[] {
				"WE28", "WE20 WE21", "AC15", "WE24", "WE26 WE27", "AC19", "AC18", "AC21", "AC22", "AC17", "AC24", "WE22 WE23"},
				"Featuring all the Weapons you could ever need, this abandoned Supply Depot guarantees one thing: Mass Destruction.", null),
		new Scenario (38, "Infested Shopping Mall", GameMode.Mercenary, 2, true, new String[] {
				"WE19", "WE20 WE21", "AC15", "AC13", "AC14", "AC19", "IT04", "WE29 WE30", "AC22", "AC17", "AM04", "WE22 WE23"},
				"Welcome to Raccoon City Mall, where anything great is located, everything is on sale, and everyone greets you with rotting teeth and a monstrous hunger! We've got what cures your hunger for Infected destruction!", null),
		new Scenario (39, "Nightmare", GameMode.Story, 3, true, new String[] {
			"WE36 WE37", "AC32", "AC37", "AC33", "WE35", "AC39", "WE41", "AC35", "AC36", "AC10", "AC04", "WE38 WE39"},
			"", null),
		new Scenario (40, "Biohazard", GameMode.Story, 3, true, new String[] {
			"AC17", "AC36", "AC22", "AC35", "WE35", "AC37", "AC41", "AC34", "WE20 WE21", "WE36 WE37", "WE38 WE39"},
			"", null),
		new Scenario (41, "Bigger is Better", GameMode.Story, 3, true, new String[] {
			"AC04", "WE02", "AC36", "AC33", "AC10", "AC32", "AC06", "WE35", "AC02", "AC03", "WE15 WE16", "WE13 WE14"},
			"Having a Bigger Inventory allows a player more options, and greater power...", null),
		new Scenario (42, "Surging Forward", GameMode.Story, 3, true, new String[] {
			"AC34", "AC36", "AC05", "AC02", "AC03", "AC38", "WE35", "WE32 WE33", "WE38 WE39", "WE02", "WE13 WE14", "WE36 WE37"},
			"Will you rush through the Mansion, or take your time gathering everything?", null),
		new Scenario (43, "Ambition", GameMode.Story, 3, true, new String[] {
			"AC40", "AC35", "AC38", "WE08", "AC10", "AC37", "AC02", "AC41", "WE38 WE39", "WE34", "WE13 WE14", "WE36 WE37"},
			"You only have 1 goal: Complete. Global. Saturation. And you intend to meet it.", null),
		new Scenario (44, "Loaded for Bear", GameMode.Story, 3, true, new String[] {
			"AM04", "WE34", "AC19", "AC17", "AC15", "WE41", "AC21", "WE35", "WE24", "WE20 WE21", "WE36 WE37", "WE32 WE33"},
			"Itching for a fight? Make sure to go in with an ample supply of Weaponry", null),
		new Scenario (45, "Griefing", GameMode.Story, 3, true, new String[] {
			"AC18", "AC19", "WE40", "WE34", "AC37", "AC38", "WE35", "AC36", "AC39", "WE36 WE37", "WE32 WE33", "WE20 WE21"},
			"Hold back the other players from winning, and you may find yourself in the lead...", null),
		new Scenario (46, "Hoarding", GameMode.Story, 3, true, new String[] {
			"AC33", "WE42", "AC36", "AC40", "AC17", "WE34", "AC32", "AC34", "WE38 WE39", "WE32 WE33", "WE20 WE21", "WE19"},
			"No Trash is the centerpiece of this Scenario", null),
		new Scenario (47, "Disposable", GameMode.Story, 3, true, new String[] {
			"WE41", "AC10", "AC06", "WE42", "AM04", "WE40", "AC17", "AC22", "AC34", "AC03", "WE31", "WE29 WE30"},
			"One and done is the easiest way to sum up this odd Scenario", null),
		new Scenario (48, "Millionaire", GameMode.Story, 3, true, new String[] {
			"AM04", "AC09", "AC03", "AC06", "AC21", "AC33", "WE34", "AC36", "WE24", "AC17", "WE29 WE30", "WE20 WE21"},
			"You'll feel like a million bucks when everything is within your grasp.", null),
		new Scenario (49, "Competitive", GameMode.Story, 3, true, new String[] {
			"WE31", "WE34", "WE35", "AC03", "AC29", "AC21", "AC02", "AC17", "AC26", "WE28", "WE32 WE33", "AC06"},
			"There is no one way to win with this Scenario", null),
		new Scenario (50, "Sacrifice", GameMode.Mercenary, 3, true, new String[] {
			"AC03", "AC41", "AC10", "AC37", "AC40", "AC36", "WE01", "WE34", "WE08", "WE38 WE39", "WE36 WE37", "WE32 WE33"},
			"Convert your Health into more beneficail gains, such as cards, ammo, and more. There are multiple ways to establish your team's dominance with this scenario", null),
		new Scenario (51, "Sluggish", GameMode.Mercenary, 3, true, new String[] {
			"AC38", "AC37", "AC06", "AC10", "AC09", "AC35", "WE41", "WE32 WE33", "WE38 WE39", "WE13 WE14", "WE08", "WE36 WE37"},
			"Less Actions demands smarter buting and more tactical decisions on your team's part", null),
		new Scenario (52, "Zombie Hunters", GameMode.Mercenary, 3, true, new String[] {
			"AC09", "AC04", "AC02", "AC33", "AC03", "AC39", "AC36", "WE34", "WE36 WE37", "WE13 WE14", "WE15 WE16", "WE38 WE39"},
			"The hunted becomes the hunter in this scenario", null),
		new Scenario (53, "Infested Police Station", GameMode.Mercenary, 3, true, new String[] {
			"AC17", "AC35", "AC22", "AC36", "AC34", "WE19", "WE20 WE21", "WE32 WE33", "AC39", "WE38 WE39", "WE22 WE23", "WE36 WE37"},
			"Your team has been dropped into a very familiar building: a police department, which has been completely overrun by the Infected!", null),
		new Scenario (54, "Infested Gun Shop", GameMode.Mercenary, 3, true, new String[] {
			"WE38 WE39", "WE35", "WE41", "WE32 WE33", "WE34", "AC19", "AC21", "AC15", "WE36 WE37", "WE22 WE23", "AC39", "WE20 WE21"},
			"Walls and cabinets stand in front of you, filled to the brim with every Weapon imaginable, eagerly awaiting use against those who had wronged their owner...", null)

	};
	
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
	
	public static Pair<Integer,Scenario> customTempScenario;
	public static ScenDBHelper dbHelper;
	public static ArrayList<Pair<Integer,Scenario>> CustomScenarios = new ArrayList<Pair<Integer,Scenario>>();
			
	public static ArrayList<Pair<Integer,Scenario>> loadCustomScenarios() {
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
		return out;
	}
	/*
	public static void addTestScenario() {
		int id = dbHelper.addScenario("Test", "WE01/WE02/AC03", "description, yo", "notes", GameMode.Story.ordinal(), 0);
		CustomScenarios.add(new Pair<Integer,Scenario>(id, new Scenario("Test", GameMode.values()[GameMode.Story.ordinal()], Expans.Custom, false, 
				"AM01/AM02/AM03", "description, yo", "notes")));
	}
	*/
}
