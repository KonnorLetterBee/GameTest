package com.kngames.gametest.redata.data;

import java.util.ArrayList;

import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.WeaponCard.WeaponType;
import com.kngames.gametest.redata.CardTypes.Mansion.*;
import com.kngames.gametest.redata.data.CardEffects.*;
import com.kngames.gametest.redata.data.GameData.*;

public class Expansion {
	//	enum to define which expansion a card belongs to
	public static enum Expans {Base, Alliances, Outbreak, Nightmare, Mercenaries, Basic, Promo, Custom}
	private static String[] ExpansionString = {"Base Set", "Alliances", "Outbreak", "Nightmare", "Basic", "Promo", "Custom"};
	public static String expansString(int value) {
		if (value < 0 || value >= ExpansionString.length) return "";
		return ExpansionString[value];
	}
	
	public static final Expansion[] expansions = new Expansion[] {
		new BaseSet(), new AlliancesExpans(), new OutbreakExpans(), new NightmareExpans(), new MercenariesExpans(), new PromoCharacters1()
	};
	public static final boolean[] expansEnabled = new boolean[] { true, true, true, true, true, true };
	public static boolean isExpansEnabled(Expans e) { return expansEnabled[e.ordinal()]; }
	private static Expansion[] expansObjectsEnabled = null;
	
	//	returns a clone of the expansObjectsEnabled array (so it can't be modified by other methods)
	//	if the array is null, generate it
	public static Expansion[] expansObjectsEnabled() {
		if (expansObjectsEnabled == null) {
			ArrayList<Expansion> expans = new ArrayList<Expansion>();
			for (int i = 0; i < expansEnabled.length; i++) {
				if (expansEnabled[i]) expans.add(expansions[i]);
			}
			expansObjectsEnabled = new Expansion[expans.size()];
			expans.toArray(expansObjectsEnabled);
		}
		return expansObjectsEnabled.clone();
	}
	
	public static class BaseSet extends Expansion {
		private int exp = Expans.Base.ordinal();
		private int basicExp = Expans.Basic.ordinal();
		public String expansName() { return "Base Set"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Albert Wesker",					1,  exp, 100, 
					  2, "When Albert Attacks a Character or Explores, you get +20 Gold for that turn.", 
					  9, "Story Mode: During your turn, choose a Player.  That Player gets +1 Explore and must Explore at least once on their next turn.",
					  ""),
			new CharacterCard("Leon S. Kennedy",				2,  exp, 80, 
					  2, "All your \"Pistol\" Weapons get -10 Ammo Requirement.", 
					  6, "All your \"Pistol\" Weapons can be used twice per turn.",
					  ""),
			new CharacterCard("Claire Redfield",				3,  exp, 70, 
					  2, "All your Ammunition provides additional +10 Ammo.", 
					  6, "All your Ammunition provides additional +10 Gold.",
					  ""),
			new CharacterCard("Sheva Alomar",					4,  exp, 80, 
					  2, "During your turn, you can Exchange 1 card in your Hand with the top card of your inventory.", 
					  4, "All of your \"Rifle\" Weapons get -20 Ammo Requirement.",
					  ""),
			new CharacterCard("Barry Burton",					5,  exp, 90, 
					  2, "When Barry Attacks a Character or Explores, you can give +5 damage to 1 Weapon Barry is using this turn.", 
					  7, "All your \"Magnum\" Weapons get +20 damage.",
					  ""),
			new CharacterCard("Ada Wong",	 					6,  exp, 70, 
					  4, "When Ada Explores, you can look at the top card of the Mansion and move it to the top or bottom of the Mansion.", 
					  8, "At the beginning of your turn, you can get +1 Action and -1 Buy.",
					  ""),
			new CharacterCard("Jack Krauser",					7,  exp, 180, 
					  1, "At the beginning of your turn, you can gain 1 \"Knife\" Weapon.", 
					  7, "All your \"Knife\" Weapons get +5 Damage.",
					  ""),
			new CharacterCard("Chris Redfield",					8,  exp, 120, 
					  0, "Chirs cannot be Healed and cannot Attach non-Infected cards.", 
					  4, "1 Weapon Chris uses gets +5 Damage for each Infected he is fighting against.",
					  ""),
			new CharacterCard("Jill Valentine",					9,  exp, 80, 
					  5, "All your \"Explosive\" Weapons get +5 Damage.", 
					  8, "All your \"Explosive\" Weapons go to your Discard Pile instead of being Trashed after being used.",
					  ""),
			new CharacterCard("Rebecca Chambers",				10, exp, 70, 
					  2, "When you would Trash any cards, you can move them to another Player's Discard Pile instead", 
					  4, "At the beginning of your turn, you can Trash 1 card from your Discard Pile",
					  "")
		}; }
		public WeaponCard[] weapons() { return new WeaponCard[] {
			new WeaponCard("Grenade", 					1,  exp,		WeaponType.Explosive,	  40,  0,  15, true,  5, "Story Mode or Mercenary Mode:  Deal 5 additional damage to each adjacent Player to the Attacking Player.\nVersus Mode:  Deal an additional 5 Damage to each adjacent Player to the Attacked Player.", new GrenadeEffect(), null),
			new WeaponCard("Longbow", 					2,  exp,		WeaponType.Bow, 	  	  110, 0,  25, false, 5, ""),
			new WeaponCard("Submission", 				3,  exp, 		WeaponType.Melee,	 	  20,  0,  5,  false, 5, "Story Mode or Mercenary Mode:  This Weapon gets +5 Damage while your Character's Health is 80 or higher.\nVersus Mode:  Attacked Player must discard a Weapon from their hand.", new SubmissionEffect(), null),
			new WeaponCard("Combat Knife", 				4,  basicExp,	WeaponType.Knife,		  0,   0,  5,  false, 11, ""),
			new WeaponCard("Survival Knife", 			5,  basicExp,	WeaponType.Knife,	 	  50,  0,  10, false, 1, "This Weapon gets +5 Damage for every other Knife Weapon used this turn.", new SurvivalKnifeEffect(), null),
			new WeaponCard("Handgun", 					6,  basicExp,	WeaponType.Pistol,	 	  20,  20, 10, false, 9, ""),
			new WeaponCard("Burst-Fire Handgun",		7,  basicExp,	WeaponType.Pistol,	 	  60,  30, 20, false, 1, "While Attacking with more than 1 Weapon, this Weapon gets +20 Damage during this turn.", new BurstFireHandgunEffect(), null),
			new WeaponCard("Six Shooter", 				8,  exp,		WeaponType.Magnum,	 	  90,  50, 50, false, 5, ""),
			new WeaponCard("Gatling Gun", 				9,  exp,		WeaponType.Minigun, 	  110, -1, -1, false, 1, ""),
			new WeaponCard("Rocket Launcher", 			10, exp, 		WeaponType.Explosive,	  130, 0,  90, true,  1, "When Trashed, shuffle the the \"Rocket Launcher Case\" Token into the Mansion."),
			new WeaponCard("Assault Machine Gun", 		11, exp, 		WeaponType.MachineGun,	  30,  40, 20, false, 4, ""),
			new WeaponCard("Full-Bore Machine Gun", 	12, exp, 		WeaponType.MachineGun,	  100, 70, 40, false, 1, "If you have more than 100 Ammo, this Weapon gets +30 Damage."),
			new WeaponCard("Pump-Action Shotgun", 		13, exp, 		WeaponType.Shotgun,	 	  40,  40, 25, false, 4, "You get +1 Explore this turn."),
			new WeaponCard("Automatic Shotgun", 		14, exp, 		WeaponType.Shotgun,		  80,  80, 50, false, 1, "The next time an Infected is Revealed this turn, if its Health is 40 or lower, defeat it immediately.  Additionally, you get +1 Explore this turn."),
			new WeaponCard("Bolt-Action Rifle", 		15, exp, 		WeaponType.Rifle,	 	  50,  50, 20, false, 4, "Reveal the top card of your Inventory.  If its cost is 40 or more, this Weapon gets +30 Damage this turn."),
			new WeaponCard("Semi-Automatic Rifle", 		16, exp, 		WeaponType.Rifle,	 	  90,  70, 30, false, 1, "This Weapon gets +10 Damage for every Action played this turn."),
		}; }
		public ActionCard[] actions() { return new ActionCard[] {
			new ActionCard("Mansion Foyer",					1,  exp, 10, 30, 0, 0,  0,  2, 0, 0, ""),
			new ActionCard("Deadly Aim",					2,  exp, 5,  50, 0, 0,  20, 0, 0, 0, "All your Weapons get +10 Damage this turn.", new CardEffects.DeadlyAimEffect()),
			new ActionCard("Shattered Memories",			3,  exp, 5,  20, 0, 0,  0,  0, 0, 0, "Trash up to 2 cards from your Discard Pile.  Then Trash this card.", new CardEffects.ShatteredMemoriesEffect(), new CardEffects.TrashOnFinish(), null),
			new ActionCard("Escape from the Dead City",		4,  exp, 10, 70, 2, 0,  0,  1, 0, 0, ""),
			new ActionCard("Reload",						5,  exp, 5,  50, 2, 0,  20, 0, 0, 0, "Move 1 Weapon from your Discard Pile to your Hand.", new CardEffects.ReloadEffect()),
			new ActionCard("The Merchant",					6,  exp, 6,  50, 0, 20, 0,  1, 1, 0, ""),
			new ActionCard("Umbrella Corporation",			7,  exp, 5,  50, 1, 0,  0,  2, 0, 0, "Move 1 card from your Hand to the top of your Inventory.", new CardEffects.UmbrellaCorporationEffect()),
			new ActionCard("Back to Back",					8,  exp, 5,  30, 1, 10, 0,  0, 0, 0, "When your Character is Attacked, you can Discard this card from your Hand to give 1 Weapon -20 Damage this turn.", new CardEffects.BackToBackEffect(), null, new CardEffects.BackToBackTrigger()),
			new ActionCard("Item Management",				9,  exp, 5,  30, 0, 0,  0,  0, 0, 0, "Trash 1 Ammunition from your Hand to Gain another Ammunition costing up to 30 Gold more than the Trashed card.", new CardEffects.ItemManagementEffect()),
			new ActionCard("Ominous Battle",				10, exp, 5,  60, 0, 10, 0,  3, 0, 0, "Trash 1 Card from your Hand.", new CardEffects.OminousBattleEffect()),
			new ActionCard("Master of Unlocking",			11, exp, 3,  30, 1, 0,  0,  0, 0, 0, "Each other Player reveals the top card of their Inventory.  You can Gain 1 of the Revealed Weapons.  All the other Revealed cards are Discarded afterwards.", new CardEffects.MasterOfUnlockingEffect()),
			new ActionCard("Struggle for Survival",			12, exp, 5,  30, 1, 0,  0,  0, 0, 1, "You can Discard this card from your Hand to lower the Damage of 1 Weapon being used costing 40 Gold or less to 0.", new CardEffects.StruggleForSurvivalEffect(), null, new CardEffects.StruggleForSurvivalTrigger()),
		}; }
		public ItemCard[] items() { return new ItemCard[] {
			new ItemCard("Green Herb", 		1,  basicExp,	20, 6, 0, "Trash this Item to Heal a Character's Health by 20, or, you can Trash this Item and another \"Green Herb\" from your Hand to heal your Character's Health by 60.", new CardEffects.GreenHerbEffect(), new CardEffects.TrashOnFinish(), null),
			new ItemCard("Yellow Herb", 	2,  exp,		0,  3, 1, "When this Item is Gained, attach this Item to your Character instead.  While it is attached, that Character's Maximum Health is increased by 10.  Then, Heal that Character's Health by 10.", new CardEffects.YellowHerbEffect()),
			new ItemCard("First Aid Spray", 3,  exp,		60, 5, 0, "Trash this Item to Heal a Character's Health to full.", new CardEffects.FirstAidSprayEffect(), new CardEffects.TrashOnFinish(), null)
		}; }
		public MansionCard[] mansion() { return new MansionCard[] {
			new InfectedCard("Majini", 				1,  3, exp, 15, 10, 1, false, ""),
			new InfectedCard("Zombie (Male)", 		2,  2, exp, 20, 20, 1, false, ""),
			new InfectedCard("Zombie (Female)", 	3,  2, exp, 15, 10, 1, false, "When this Infected is Defeated, choose a Player.  That Player cannot play Actions on their next turn."),
			new InfectedCard("Zombie Butcher", 		4,  3, exp, 15, 10, 1, false, "When this Infected is Defeated, choose a Player and skip that Player's next turn."),
			new InfectedCard("Bui Kichwa", 			5,  3, exp, 10, 10, 1, false, ""),
			new InfectedCard("Licker", 				6,  3, exp, 40, 30, 3, false, ""),
			new InfectedCard("Nemesis T-Type", 		7,  1, exp, 60, 40, 5, false, "When this Infected is Revealed, each Player takes 20 Damage."),
			new InfectedCard("Hunter", 				8,  2, exp, 40, 30, 4, false, "If this Infected was not Defeated, randomly Trash 2 cards from the Attacking Player's Discard Pile."),
			new InfectedCard("Mimicry Marcus", 		9,  2, exp, 30, 20, 2, false, ""),
			new InfectedCard("Uroboros Aheri", 		10, 1, exp, 90, 70, 8, false, "If this Infected was not Defeated, Shuffle it into the Mansion."),
			new InfectedCard("Dr. Salvador", 		11, 3, exp, 20, 15, 2, false, ""),
			new TokenCard(   "Rocket Launcher Case",12,    exp, "Gain 1 \"Rocket Launcher\" Weapon.  Remove this card from the game afterwards.", new CardEffects.RocketCaseEffect(), null),
			new TokenCard(   "Gatling Gun Case", 	13,    exp, "Gain 1 \"Gatling Gun\" Weapon.  Remove this card from the game afterwards.", new CardEffects.GGunCaseEffect(), null),
			new InfectedCard("Gatling Gun Majini", 	14, 3, exp, 40, 25, 4, false, "This Infected gets +5 Damage for every 10 Ammo the Attacking Player has."),
			new InfectedCard("Cerberus", 			15, 3, exp, 25, 10, 2, false, ""),
			new InfectedCard("El Gigante", 			16, 1, exp, 40, 40, 4, false, ""),
			new InfectedCard("Executioner", 		17, 1, exp, 30, 25, 3, false, "")
		}; }
		public AmmunitionCard[] ammunition() { return new AmmunitionCard[] {
			new AmmunitionCard("Ammo x10", 1, basicExp, 0,  28, 10, 10),
			new AmmunitionCard("Ammo x20", 2, basicExp, 30, 15, 20, 20),
			new AmmunitionCard("Ammo x30", 3, basicExp, 60, 15, 30, 30),
			new AmmunitionCard("Treasure", 4, exp, 		40, 10, 0,  30),
		}; }
		public Scenario[] scenarios() { return new Scenario[] {
			new Scenario (1, "First Timer", GameMode.Story, exp, true, new String[] {
					"AC07", "AC01", "AC04", "AC02", "AC03", "AC06", "AC05", "WE01", "WE08", "WE13 WE14", "WE15 WE16", "WE11 WE12"},
					"", null),
			new Scenario (2, "Classic Horror", GameMode.Story, exp, true, new String[] {
					"AC07", "AC01", "AC02", "AC03", "AC05", "AC09", "IT03", "WE02", "WE01", "WE13 WE14", "WE11 WE12", "WE03"},
					"Featuring many of the Weapons and Item cards found in the original Resident Evil game.  The Player who manages their Inventory effectively will find themselves closer to escaping.", null),
			new Scenario (3, "Spy Games", GameMode.Story, exp, true, new String[] {
					"AC07", "AC10", "AC04", "AC11", "AC09", "AC02", "IT03", "WE02", "WE01", "WE08", "WE13 WE14", "WE11 WE12"},
					"A few new cards are added to this setup, including a card that allows Players to steal Weaponry from the other survivors, adding to their own armory of Infected destruction.", null),
			new Scenario (4, "Whaddya Buyin?", GameMode.Story, exp, true, new String[] {
					"AC06", "AC10", "AC05", "AC02", "AC04", "AC11", "WE03", "WE01", "WE08", "WE13 WE14", "WE11 WE12", "WE15 WE16"},
					"With a change in scenery comes new Resources available for purchase just in time as the Infected have rallied.  The Merchant appears in the most unlikely of places to offer you \"A lot of good things for sale, Stranger.\"", null),
			new Scenario (5, "Battle-Hardened Veteran", GameMode.Story, exp, true, new String[] {
					"AC06", "AC07", "AC01", "AC03", "AC05", "AC02", "WE03", "WE02", "WE08", "WE13 WE14", "WE11 WE12", "WE15 WE16"},
					"You have survived this long, only to face a final surge of Infected.  Only those with strong wills and brave hearts will prove themselves able to stand at the top and escape with their lives.", null),
			new Scenario (6, "Special Forces", GameMode.Mercenary, exp, true, new String[] {
					"AC02", "AC09", "AC07", "IT03", "AC03", "AC11", "AC10", "AC05", "WE01", "WE13 WE14", "WE11 WE12", "WE08"},
					"Being quick on your feet and managing your Teams Inventory will be the key to this battle.  If you find yourself running low on Health, you can always have one of your teammates Buy you a First Aid Spray.", null),
			new Scenario (7, "Limited Movement", GameMode.Mercenary, exp, true, new String[] {
					"AC02", "AC06", "AC09", "AC01", "AC11", "AC10", "AC03", "WE01", "WE08", "WE15 WE16", "WE11 WE12", "WE13 WE14"},
					"Make no wasted movements as your Team will be down to one or two Actions per turn.  Make every card count and you will come out victorious!", null),
			new Scenario (8, "Jack Of All Trades", GameMode.Mercenary, exp, true, new String[] {
					"AC04", "AC05", "AC02", "AC09", "AC3", "AC11", "AC06", "WE01", "WE13 WE14", "WE15 WE16", "WE11 WE12", "WE08"},
					"", null),
			new Scenario (9, "Quick And Easy", GameMode.Versus, exp, true, new String[] {
					"AC06", "AC01", "AC03", "AC09", "AC05", "AC02", "AC12", "WE01", "WE03", "WE15 WE16", "WE13 WE14", "WE08"},
					"Bigger is not always better, but beware the Magnum, a gun that will certainly make anyone's day.", null),
			new Scenario (10, "Big Guns, Big Explosions", GameMode.Versus, exp, true, new String[] {
					"AC10", "AC12", "AC03", "AC01", "AC08", "WE01", "WE08", "WE02", "WE03", "WE13 WE14", "WE15 WE16", "WE11 WE12"},
					"Use this setup if your play style is to Buy everything that explodes or destroys.  More defensive cards are added, along with more 'Trash' abilities and larger Damage dealing Weapons.", null),
			new Scenario (11, "I've Covered Wars", GameMode.Versus, exp, true, new String[] {
					"AC11", "AC12", "AC08", "AC10", "AC01", "AC03", "WE01", "WE08", "WE02", "WE09", "WE13 WE14", "WE15 WE16", "WE10"},
					"Feel like you can handle it all? With this setup, the most powerful Weapons in the game, the Rocket Launcher and the Gatling Gun, join the Resource Piles. Drop one of these on your friends and they won't be able to recover before you put them down for good.", null)
		}; }
	}
	
	public static class AlliancesExpans  extends Expansion{
		private int exp = Expans.Alliances.ordinal();
		public String expansName() { return "Alliances"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Carlos Oliveira",				11, exp, 90, 
					  2, "When Carlos Explores, you can look at the top card of the Mansion before playing your Weapons.", 
					  8, "If your Character's total Damage is equal to the total Health of all Infected Revealed during your turn, you can move 1 card from your Discard Pile to your Hand after Carlos Explores.",
					  ""),
			new CharacterCard("Josh Stone",						12, exp, 80, 
					  0, "Josh cannot use Weapons with a cost of 50 or more.", 
					  4, "Josh's Partner's maximum number of attachable cards is increased by 2.",
					  ""),
			new CharacterCard("Steve Burnside",					13, exp, 70, 
					  4, "At the beginning of your turn, Heal Steve's Health by 10.", 
					  8, "During your turn, you can Discard a \"Uroboros Injection\" from your Hand.  In that case, Heal Steve's Health by 20 and Steve deals an additional 30 Damage during this turn.",
					  ""),
			new CharacterCard("Jack Krauser",					14, exp, 90, 
					  0, "Jack cannot use more than 1 Weapon.", 
					  6, "Jack deals an additional X Damage during your turn, where X=the number of cards in your Hand times 10.",
					  ""),
			new CharacterCard("Hunk",							15, exp, 70, 
					  3, "When Hunk would take any Damage, you can give that Damage to your Partner instead.", 
					  6, "At the beginning of your turn, if Hunk has no Partner, you get +2 cards.",
					  ""),
			new CharacterCard("Jill Valentine (Brainwashed)",	16, exp, 90, 
					  3, "When Jill Explores, if her Partner has 1 or less cards attached, 1 of Jill's Weapons gets +10 Damage during this turn.", 
					  6, "During another Player's turn, when a \"Las Plagas\" Infected is Revealed, you can Trash a \"Flash Grenade\" from your Hand and apply its effect during this turn.",
					  ""),
			new CharacterCard("Billy Coen",						17, exp, 80, 
					  1, "During your turn, you can Gain 1 \"Ammo x10\".  In that case, +1 But this turn.", 
					  5, "During your turn, if you have 10 or more cards in your Discard Pile, you can move 1 of them to your Hand.",
					  ""),
			new CharacterCard("Leon S. Kennedy",				18, exp, 80, 
					  1, "During your turn, you can move 1 card from you Discard Pile to the bottom of your Inventory.", 
					  7, "During your turn, you can move the bottom card of your Inventory to the top.",
					  ""),
			new CharacterCard("Chris Redfield",					19, exp, 80, 
					  3, "While Chris is using only 1 Weapon, that Weapon gets -20 Ammo Requirement.", 
					  8, "While Chris is using only 1 Weapon type, all of those Weapons get -20 Ammo Requirement.  (A Weapon type would be Shotgun, Pistol, Magnum etc.)",
					  ""),
			new CharacterCard("Claire Redfield",				20, exp, 80, 
					  3, "When you Trash a \"Green Herb\" or a \"Red Herb\" from your Hand, Claire deals an additional 20 Damage during this turn.", 
					  7, "When you Trash a \"Red Herb\" and a \"Green Herb\", double all Damage Claire deals during this turn.",
					  ""),
		}; }
		public WeaponCard[] weapons() { return new WeaponCard[] {
			new WeaponCard("Flash Grenade", 				17, exp, WeaponType.Explosive,  20,  0,  0,  true,  5, "The next time a \"Las Plagas\" Infected is Revealed this turn, Defeat it immediately.  While your Character(s) is/are Battling, you can move 1 Infected with 20 or less Health to the bottom of the Mansion."),
			new WeaponCard("Grenade Launcher", 				18, exp, WeaponType.Explosive,  80,  0,  20, false, 1, "All \"Explosive\" Weapons your Character uses during this turn go to your Discard Pile instead of being Trashed."),
			new WeaponCard("Telescopic Sight Rifle", 		19, exp, WeaponType.Rifle,	  50,  50, 30, false, 7, "During this turn, when your Character Explores, Reveal the bottom of the Mansion instead of the top."),
			new WeaponCard("Riot Shotgun", 					20, exp, WeaponType.Shotgun,	  70,  60, 45, false, 7, "You get +1 Explore this turn."),
			new WeaponCard("Triple-Barreled Shotgun", 		21, exp, WeaponType.Shotgun,	  90,  80, 50, false, 1, "You get +2 Explore this turn.  This Weapon gets +10 Damage during this turn for every Infected your Character(s) is/are Battling."),
			new WeaponCard("Russian Assault Rifle", 		22, exp, WeaponType.MachineGun, 40,  -1, -1, false, 4, "You cannot use more than 20 Ammo for this Weapon."),
			new WeaponCard("Signature Special", 			23, exp, WeaponType.MachineGun, 70,  -1, -1, false, 1, "You cannot use more than 60 Ammo for this Weapon."),
			new WeaponCard("Flamethrower", 					24, exp, WeaponType.none,		  90,  0,  -1, false, 5, "X() { return 5 times the number of cards in your Discard Pile."),
			new WeaponCard("Blowback Pistol", 				25, exp, WeaponType.Pistol,	  40,  30, 20, false, 5, "You can Discard any number of \"Pistol\" Weapons from your Hand to give this Weapon +10 Damage during this turn for each \"Pistol\" Weapon Discarded due to this effect."),
		}; }
		public ActionCard[] actions() { return new ActionCard[] {
			new ActionCard("Partners",							13, exp, 5,  30, 2, 0,  20, 0, 0, 0, "Attach this card to your Partner if you have one.  If this card is attached to your Partner at the beginning of your turn, you get +1 Action during this turn."),
			new ActionCard("Star-Crossed Duo",					14, exp, 8,  40, 0, 0,  0,  2, 1, 0, "Attach this card to your Partner if you have one.  When your Character Explores, if your Partner is leading with this card attached, 1 of your Partners Weapons gets +10 Damage during this turn."),
			new ActionCard("Great Ambition",					15, exp, 5,  50, 1, 10, 0,  0, 0, 0, "If you have no Partner, you get +2 cards."),
			new ActionCard("Archrival",							16, exp, 5,  30, 0, 0,  0,  2, 0, 0, "All Players Discard down to 1 or less cards attached to their Partners."),
			new ActionCard("Fierce Battle",						17, exp, 5,  80, 0, 0,  0,  4, 0, 0, "Choose another Player.  That Player gets +1 card."),
			new ActionCard("Uroboros Injection",				18, exp, 5,  60, 2, 0,  0,  0, 1, 0, "During another Player's turn, when an Infected is Revealed, you can Trash this card from your hand to give +20 Health to any Revealed Infected during this turn."),
			new ActionCard("Quirk of Fate",						19, exp, 8,  30, 1, 0,  0,  0, 0, 0, "Trash 1 card from your Hand.  Then you get +1 card."),
			new ActionCard("Cornered",							20, exp, 5,  30, 0, 0,  0,  0, 0, 0, "Attach this card to your Partner if you have one.  At the beginning of your turn, If you have another \"Cornered\" attached to your Partner, Trash both.  Then Gain 3 cards costing a total of 100 or less Gold and move them to the top of your Inventory in any order."),
			new ActionCard("Gathering Forces",					21, exp, 5,  90, 1, 20, 0,  0, 1, 0, "Any cards you Gain this turn go to your Hand instead."),
			new ActionCard("Desperate Escape",					22, exp, 5,  70, 1, 0,  20, 0, 0, 0, "Choose a Weapon Type (Magnum, Pistol, Rifle, etc).  All Weapons of that type get +10 Damage during this turn."),
		}; }
		public ItemCard[] items() { return new ItemCard[] {
			new ItemCard("Red Herb", 		4,  1, 20, 5, 0, "You can Trash this Item and a \"Green Herb\" from your Hand to heal a Character and their Partner's Health to full."),
		}; }
		public MansionCard[] mansion() { return new MansionCard[] {
			new InfectedCard("Lurker", 				22, 3, exp, 10, 10, 1, false, ""),
			new InfectedCard("Infected Bat", 		23, 3, exp, 15, 20, 1, false, "While this Infected is being Attacked with another Infected, this Infected gets +20 Health during this turn."),
			new InfectedCard("Licker Beta", 		24, 2, exp, 30, 20, 3, false, ""),
			new InfectedCard("Duvalia", 			25, 2, exp, 60, 40, 5, true,  "When this Infected kills a Character, the Player with the most Decorations Gains 5 \"Ammo x10\" cards."),
			new InfectedCard("Garrador", 			26, 2, exp, 50, 40, 4, false, ""),
			new InfectedCard("Zombie (Male)", 		27, 3, exp, 20, 10, 1, false, ""),
			new InfectedCard("Kipepo", 				28, 3, exp, 20, 20, 1, true,  ""),
			new InfectedCard("Albert Wesker", 		29, 1, exp, 90, 70, 9, false, "When this Infected is Revealed, all other Revealed Infected get +10 Health and +10 Damage during this turn."),
			new InfectedCard("Cephalo", 			30, 3, exp, 25, 20, 2, true,  "When this Infected is Defeated, choose a Player.  That Player must Explore at least once with their Character(s) on their next turn."),
			new InfectedCard("Proto Tyrant", 		31, 1, exp, 60, 40, 5, false, "When this Infected is Revealed, all non-Exploring Characters with 50 or more Health take 40 Damage."),
			new InfectedCard("Iron Maiden", 		32, 2, exp, 50, 30, 4, false, ""),
			new InfectedCard("Zombie Horde", 		33, 3, exp, 30, 20, 2, false, ""),
			new InfectedCard("Nosferatu",	 		34, 2, exp, 55, 30, 4, false, ""),
			new InfectedCard("Los Illuminados Monk",35, 2, exp, 15, 20, 3, false, "When this Infected is Revealed, it gets +5 Health and +5 Damage for each Infected attached to the Exploring Main Character."),
			new InfectedCard("Reaper",		 		36, 2, exp, 35, 40, 4, false, "When this Infected is Defeated, if it wasn't Defeated with Damage equal to its Health, the Attacking Player Trashes 1 of the cards attached to their Main Character's Partner with the highest cost, if any."),
			new InfectedCard("Guardian of Insanity",37, 3, exp, 30, 20, 2, false, "When this Infected is Revealed, each Player takes 10 Damage."),
			new EventCard("Explosive Barrel",		38,    exp, 3, "When this Event is Revealed, Remove it from the Game.  If the Exploring Character(s) is/are dealing 80 or more total Damage, each of them takes 30 Damage.  Otherwise, that Character deals an additional 30 Damage and you get +1 Explore during this turn."),
			new EventCard("Collapsing Floor Trap",	39,    exp, 1, "When this Event is Revealed, Remove it from the Game.  Reveal the next 2 cards of the Mansion.  If any of those cards are Infected, the Exploring Character(s) is/are now Battling it/them.  Shuffle all other Revealed cards into the Mansion."),
			new EventCard("Laser Targeting Device",	40,    exp, 1, "When this Event is Revealed, look at the top card of the Mansion.  If it is an Infected, attach it to your Main Character and add its Decorations to your total, then shuffle this Event into the Mansion.  Otherwise, Shuffle this Event and that card into the Mansion."),
		}; }
		public Scenario[] scenarios() { return new Scenario[] {
			new Scenario (12, "Initial Horror", GameMode.Story, exp, true, new String[] {
					"AC17", "AC21", "AC22", "AC19", "AC14", "AC18", "IT04", "WE19", "WE20 WE21", "WE17 WE18", "WE22 WE23"},
					"", null),
			new Scenario (13, "Bound Advance", GameMode.Story, exp, true, new String[] {
					"AM04", "WE19", "AC21", "WE20 WE21", "AC22", "AC19", "IT04", "WE24", "AC14", "WE25", "WE17 WE18", "WE22 WE23"},
					"Providing Players with little to no Drawing ability, this setup will strain resources as they attempt to make the most of their initial 5 cards.", null),
			new Scenario (14, "Backlog", GameMode.Story, exp, true, new String[] {
					"AC18", "WE19", "AC21", "WE20 WE21", "AC22", "AC13", "AC15", "WE24", "AC14", "AC16", "WE17 WE18", "WE22 WE23"},
					"Players will find themselves with a bit too many Resources as they battle to control their Inventory sizes with this setup.", null),
			new Scenario (15, "Fast Action", GameMode.Story, exp, true, new String[] {
					"AM04", "WE19", "AC13", "WE20 WE21", "AC21", "AC17", "AC22", "WE24", "AC14", "AC19", "WE17 WE18", "WE22 WE23"},
					"Feel like ending the game quickly? This setup will allow Players to build up their Inventory the quickest and have everything they need when they decide to Explore the Mansion.", null),
			new Scenario (16, "Lethal Injection", GameMode.Story, exp, true, new String[] {
					"AM04", "WE02", "AC10", "AC02", "WE20 WE21", "WE08", "AC17", "AC19", "AC09", "AC05", "WE11 WE12", "WE22 WE23"},
					"Ignore all those tiny weapons and go for nothing but the best! Use your Treasure to get the large Weaponry you need to annihilate the Infected.", null),
			new Scenario (17, "Top of the Class", GameMode.Story, exp, true, new String[] {
					"WE01 WE17 WE18", "WE02", "AC10", "AC02", "WE20 WE21 WE13 WE14", "WE08", "AC18", "AC03", "AC09", "AC05", "WE22 WE23", "WE19 WE15 WE16"},
					"Combining the best of the best from Sets 1 and 2, this set up will give you everything you need to rip through the Mansion.", null),
			new Scenario (18, "Infested Supply Depot", GameMode.Mercenary, exp, true, new String[] {
					"AC18", "WE19", "AC21", "WE20 WE21", "AC22", "AC15", "AC17", "WE24", "AC16", "AC19", "WE17 WE18", "WE22 WE23"},
					"Featuring all the Weapons you could ever need, this abandoned Supply Depot guarantees one thing: Mass Destruction.", null),
			new Scenario (19, "Infested Shopping Mall", GameMode.Mercenary, exp, true, new String[] {
					"IT04", "WE19", "AC21", "WE20 WE21", "AC22", "AC15", "AC17", "AC13", "AM04", "AC19", "AC14", "WE22 WE23"},
					"Welcome to Raccoon City Mall, where anything great is located, everything is on sale, and everyone greets you with rotting teeth and a monstrous hunger! We've got what cures your hunger for Infected destruction!", null),
			new Scenario (20, "Partners in Escape", GameMode.PartnerStory, exp, true, new String[] {
					"AC13", "WE19", "AC20", "WE20 WE21", "IT04", "AC15", "AC14", "WE24", "WE17 WE18", "AC16", "WE22 WE23", "AC19"},
					"An Introduction to the Partner Modification, many cards that had limited uses before are completely changed when adding in more Characters.", null),
			new Scenario (21, "A Little Gun Goes A Long Way", GameMode.PartnerStory, exp, true, new String[] {
					"AC21", "AC22", "AC20", "AC18", "IT04", "AC13", "AC14", "WE24", "AC16", "WE17 WE18", "WE25", "AC19"},
					"Sometimes, the person who is more accurate is the one who survives, as proven by this Scenario.", null),
			new Scenario (22, "Tactical Advance", GameMode.PartnerStory, exp, true, new String[] {
					"AC22", "WE19", "AC18", "WE20 WE21", "AC17", "AC15", "AC14", "WE24", "AC13", "AM04", "WE22 WE23", "AC19"},
					"Having more cards in your Hand will demand smarter choices be made, less your Partner be killed.", null),
		}; }
	}
	
	public static class OutbreakExpans  extends Expansion{
		private int exp = Expans.Outbreak.ordinal();
		public String expansName() { return "Outbreak"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Excella Gionne",					21, exp, 70, 
					  2, "During your turn, you can increase Excella's Infection Level by 1.  In that case, you get +20 Gold during this turn.", 
					  4, "At the beginning of your turn, if Excella's Infection Level is 8 or higher, you get +3 cards.",
					  ""),
			new CharacterCard("Kevin Ryman",					22, exp, 80, 
					  0, "At the beginning of the game, shuffle 1 \"Samurai Edge\" Weapon from the Resource Area or from outside the game into your Inventory.", 
					  8, "During your turn, you can move 1 \"Pistol\" Weapon in your Play Area to the top of your Inventory.",
					  ""),
			new CharacterCard("David King",						23, exp, 80, 
					  2, "When David Explores, you can Discard a Weapon from your hand.  In that case, add the Discarded Weapon's effect text to 1 Weapon David is using.", 
					  6, "When you play a \"Knife\" Weapon from your Hand, move a \"Knife\" Weapon from your Discard Pile to your Play Area.  David is now using that moved weapon.",
					  ""),
			new CharacterCard("Mark Wilkins",					24, exp, 90, 
					  2, "While Mark is using 2 or more Weapons, Mark deals an additional 10 Damage.", 
					  6, "\"Melee\" Weapons Mark uses get +10 Damage.",
					  ""),
			new CharacterCard("Jill Valentine",					25, exp, 80, 
					  3, "At the beginning of your turn, decrease Jill's Infection Level by 1.", 
					  6, "While Jill's Infection Level is 0, you and Jill cannot be affected by any Infection effects.",
					  ""),
			new CharacterCard("Leon S. Kennedy",				26, exp, 70, 
					  1, "During your turn, you can put a card from your Hand on top of your Inventory.", 
					  7, "During your turn, you can Discard 1 card from your Hand.  In that case, move 1 Action from your Discard Pile to your Hand.",
					  ""),
			new CharacterCard("Ada Wong",						27, exp, 70, 
					  3, "At the beginning of your turn, if Ada's Health is 30 or less, you get +1 Action during this turn.", 
					  7, "At the beginning of your turn, you can Reveal any number of Actions from your Hand.  In that case, +1 card for every card Revealed.",
					  ""),
			new CharacterCard("Chris Redfield",					28, exp, 90, 
					  2, "During your turn, you can Discard any number of cards from your Hand.  Then, +20 Ammo during this turn for every card Discarded.", 
					  8, "When Chris Explores, if you have more than 100 Ammo, Chris deals an additional 20 Damage during this turn.",
					  ""),
			new CharacterCard("Rebecca Chambers",				29, exp, 70, 
					  3, "At the beginning of your turn, you can Discard any number of cards from your Hand.  Then decrease Rebecca's Infection Level by 1 for every card Discarded.", 
					  6, "When an effect decreases another Character's Infection Level, you get +1 card.",
					  ""),
			new CharacterCard("Hunk",							30, exp, 90, 
					  3, "At the beginning of your turn, Hunk can take 10 Damage.  In that case, you get +1 Explore during this turn.", 
					  7, "When Hunk Explores, if his Health is 30 or less, Hunk deals an additional 30 Damage during this turn.",
					  ""),
		}; }
		public InfectedCharacterCard[] infecCharacters() { return new InfectedCharacterCard[] {
				new InfectedCharacterCard("Zombie A", 31, exp, 140, "When Berserker Attacks, all other non-Infected Characters that Berserker is not Attacking take 10 Damage.", 0),
				new InfectedCharacterCard("Zombie B", 32, exp, 140, "When a Character's Infection Level increases by 1 or more due to an effect, you can Trash an Infection card from your Hand.  In that case, increase that Character's Infection Level by 1 more.", 5),
				new InfectedCharacterCard("Zombie C", 33, exp, 140, "At the beginning of your turn, you can move 1 Infection card in your Hand to the bottom of the Infection Deck.  In that case, move the top card of the Infection Deck to your Hand.", 10),
			}; }
		public WeaponCard[] weapons() { return new WeaponCard[] {
			new WeaponCard("Standard Sidearm", 				26, exp, WeaponType.Pistol,	  30,  10, 10, false, 4, "This Weapon gets +5 Damage for each non-Item you Gained this turn."),
			new WeaponCard("Samurai Edge", 					27, exp, WeaponType.Pistol,	  70,  60, 30, false, 1, "This Weapon gets +20 Damage for each card you Gained this turn."),
			new WeaponCard("Stun Rod", 						28, exp, WeaponType.Melee,	  30,  0,  10, false, 5, "You get +1 Explore this turn.  While your Character is Battling 2 or more Infected, you can move 1 of those Infected with 20 or less Health to the top of the Mansion."),
			new WeaponCard("Lightning Hawk", 				29, exp, WeaponType.Magnum,	  100, 60, 60, false, 4, ""),
			new WeaponCard("Hand Cannon", 					30, exp, WeaponType.Magnum,	  120, 80, 80, false, 1, "Trash 1 Ammunition from your Play Area.  This card cannot be attached to Characters."),
			new WeaponCard("Night Scope Rocket Launcher", 	31, exp, WeaponType.Explosive,  80,  0,  60, true,  5, "While it is nighttime outside of the game, this Weapon gets +20 Damage."),
		}; }
		public ActionCard[] actions() { return new ActionCard[] {
			new ActionCard("Power of the T-Virus",				23, exp, 5,  20, 0, 0,  0,  0, 0, 0, "Your Character deals an additional 20 Damage this turn.  You can Trash this card to get +20 Gold during this turn.  At the end of the turn, if you Explored or Attacked, increase your Infection Level by 1."),
			new ActionCard("I Have This...",					24, exp, 5,  40, 1, 0,  0,  1, 0, 0, "You can Reveal a Weapon from your Hand.  In that case, if the cost of the Revealed Weapon was 50 or more, you get +1 card."),
			new ActionCard("Wesker\'s Secret",					25, exp, 5,  40, 0, 0,  0,  2, 0, 0, "Look at the top 4 cards of the Mansion.  If there is an \"Antivirus\" card, then you can reveal it, move it to the top of the Mansion, then move the rest to the bottom of the Mansion in a random order."),
			new ActionCard("Injection",							26, exp, 5,  30, 1, 0,  0,  0, 0, 0, "Discard any number of cards from your Hand, then draw the same amount.  Then, you can Trash this card.  In that case, decrease your Character's Infection Level by 1."),
			new ActionCard("By Any Means Necessary",			27, exp, 5,  40, 2, 0,  0,  0, 0, 0, "Decrease your Character's Infection Level by any amount.  Then, increase your character's Infection Level by the same amount, minus 1."),
			new ActionCard("Higher Priorities",					28, exp, 5,  70, 0, 0,  0,  0, 0, 0, "Choose One: +3 Cards, or +2 Actions."),
			new ActionCard("Parting Ways",						29, exp, 5,  30, 1, 0,  0,  0, 0, 0, "You can Trash a card from your Hand.  In that case, Gain a card from the Resource Area costing up to 20 more than the Trashed card."),
			new ActionCard("Returned Favor",					30, exp, 5,  50, 0, 0,  0,  2, 0, 0, "Attach this card to your Character.  At the beginning of your turn, if this card is attached to your Character, you get +1 card, then Discard this card."),
			new ActionCard("The Gathering Darkness",			31, exp, 5,  50, 0, 0,  0,  0, 0, 0, "Trash this card and choose another Player.  That Player Reveals their Hand and Trashes an Ammunition with the highest cost from their Hand.  That Player moves 1 \"Ammo x10\" Ammunition to their Hand for every 10 Ammo the Trashed card provided."),
		}; }
		public ItemCard[] items() { return new ItemCard[] {
			new ItemCard("Kevlar Jacket", 	54, exp, 0,  1, 2, "When this Item is Revealed, attach this Item to the Exploring Character.  When the attached Character would receive any Damage, you can move this card to the bottom of the Mansion.  In that case, that Character cannot receive any Damage during this turn."),
			new ItemCard("Antivirus", 		55, exp, 0,  2, 2, "When this Item is Revealed, if the Exploring Character's Infection Level is 6 or higher, decrease it by 3, then move this card to the bottom of the Mansion, or Remove this card from the Game."),
		}; }
		public InfectionCard[] infections() { return new InfectionCard[] {
			new InfectionCard("Claw", 				1, 3, 5, 10, "The Player controlling the Character you Attacked can only play 1 Action on their next turn."),
			new InfectionCard("Virulent Frenzy", 	2, 3, 6, 0,  "Look at the top 3 cards of your Inventory and Trash all non-Infection cards.  Move the rest to the top of your Inventory in any order."),
			new InfectionCard("Bile Vomit",			3, 3, 6, 5,  "The Character your Character is Attacking takes 5 Damage for each other \"Bile Vomit\" used this turn."),
			new InfectionCard("Slough Armor",		4, 3, 6, 0,  "When your Character is Attacked, you can Discard this card from your Hand.  In that case, reduce the Damage of 1 Weapon neing used by 20."),
			new InfectionCard("Drag", 				5, 3, 6, 0,  "While there are 2 or more non-Infected Characters, the Character your Character is Attacking this turn cannot Attack your Character on their next turn."),
			new InfectionCard("Devour", 			6, 3, 6, 5,  "Heal your Character's Health by 20."),
			new InfectionCard("Infection", 			7, 3, 5, 0,  "Increase the Infection Level of the Character your Character is Attacking by 1."),
		}; }
		public MansionCard[] mansion() { return new MansionCard[] {
			new InfectedCard("Tyrant T-002", 		41, 1, exp, 90, 70, 8, false, "When this Infected was not Defeated, increase the Exploring Character's Infection Level by 1 plus their current Level, then shuffle this Infected into the Mansion."),
			new InfectedCard("Yawn", 				42, 2, exp, 40, 30, 4, false, "When this Infected is Revealed, if the Exploring Character's Infection Level is 5 or less, increase it by 2.  Otherwise, increase it by 1."),
			new InfectedCard("Nemesis (2nd Form)", 	43, 1, exp, 60, 40, 5, false, "When this Infected is Revealed, increase the Infection Level of all Characters by 1."),
			new InfectedCard("Grave Zombie", 		44, 3, exp, 10, 10, 1, false, ""),
			new InfectedCard("Wasp", 				45, 2, exp, 15, 0 , 2, false, "When this Infected is Revealed, the Exploring Character takes 20 Damage."),
			new InfectedCard("Web Spinner", 		46, 2, exp, 30, 10, 3, false, "When this Infected was not Defeated, increase the Exploring Character's Infection Level by 1."),
			new InfectedCard("Reinforced Zombie", 	47, 3, exp, 20, 10, 1, false, "While this Infected is attached to a Character, that Character's Decoration Requirements for Level 2 is reduced by 1."),
			new InfectedCard("Crimson Head", 		48, 2, exp, 40, 20, 4, false, "When this Infected is Revealed, if the Exploring Character has 1 or more Infected with \"Zombie\" in its name attached to them, this Infected gets +10 Health and +10 Damage during this turn."),
			new InfectedCard("Reinforced Licker", 	49, 3, exp, 30, 20, 3, false, "When this Infected is Revealed, Weapons with 0 Ammo Requirement deal 0 Damage during this turn."),
			new InfectedCard("Grave Digger", 		50, 2, exp, 30, 30, 3, false, ""),
			new InfectedCard("Zombie Cop", 			51, 3, exp, 15, 10, 1, false, ""),
			new InfectedCard("Eliminator", 			52, 2, exp, 20, 20, 2, false, ""),
			new InfectedCard("Lisa Trevor", 		53, 1, exp, 40, 20, 0, false, "When this Infected is Defeated, increase a Character's Infection Level by 1, then move this Infected to the bottom of the Mansion."),
			//	2 item cards listed in the items table
			new EventCard ("Laser Trap", 			56,    exp, 1, "When this Event is Revealed, the Exploring Character takes 20 Damage for every different card type (Action, Weapon, Ammunition, etc.) in the controlling Player's Play Area.  Remove this Event from the game afterwards."),
			new EventCard ("Rock Trap", 			57,    exp, 1, "When this Event is Revealed, the Exploring Character takes 10 Damage for every Action in the controlling Player's Play Area.  Remove this Event from the game afterwards."),
		}; }
		public Scenario[] scenarios() { return new Scenario[] {
			new Scenario (23, "Outbreak", GameMode.Outbreak, exp, true, new String[] {
					"WE15 WE16", "WE29 WE30", "WE28", "WE13 WE14", "AC26", "AC24", "AC01", "AC25", "AC23", "AC02", "AC06", "AC03", "AC04"},
					"", null),
			new Scenario (24, "Dawn of Nightmares", GameMode.Outbreak, exp, true, new String[] {
					"WE19", "WE20 WE21", "AC19", "AC24", "AC23", "AC25", "AC17", "AC21", "AC22", "WE29 WE30", "AC26", "WE22 WE23"},
					"", null),
			new Scenario (25, "Infection Balance", GameMode.Outbreak, exp, true, new String[] {
					"WE02", "WE15 WE16", "AC10", "AC27", "AC02", "AC04", "AC09", "AC23", "AC26", "AC03", "WE29", "WE13 WE14"},
					"Learn to master the Virus within you in order to get the most out of this Scenario.", null),
			new Scenario (26, "Duplicity", GameMode.Outbreak, exp, true, new String[] {
					"WE26 WE27", "AC05", "WE28", "AC02", "WE15", "AC30", "AC04", "AC26", "AC03", "WE03", "WE29 WE30", "WE13 WE14"},
					"Sometimes having multiple copies of the same card isn't a bad idea...", null),
			new Scenario (27, "Embrace the Darkness", GameMode.Outbreak, exp, true, new String[] {
					"WE01", "AC27", "AC10", "AC02", "AC23", "WE08", "AC03", "AC31", "AC28", "WE03", "WE15 WE16", "WE13 WE14"},
					"Being an Infected isn't all that bad..... is it?", null),
			new Scenario (28, "Quick Game", GameMode.Outbreak, exp, true, new String[] {
					"WE22 WE23", "AC29", "WE20 WE21", "WE29 WE30", "AC15", "AM04", "AC19", "AC17", "AC30", "AC21", "WE24", "WE25"},
					"Don't have the full hour to play? Use this Scenario!", null),
			new Scenario (29, "Trouble Around Every Corner", GameMode.Outbreak, exp, true, new String[] {
					"WE22 WE23", "WE25", "AC24", "WE26 WE27", "WE20 WE21", "AC18", "WE29 WE30", "AC19", "WE28", "AC31", "AC25", "AC21"},
					"Find inventive ways to keep your opponent's away from winning!", null),
			new Scenario (30, "Resident Reaper", GameMode.Outbreak, exp, true, new String[] {
					"WE25", "WE26 WE27", "WE24", "AC26", "WE29 WE30", "WE28", "AC19", "WE22 WE23", "WE17", "AC17", "WE19", "WE20 WE21"},
					"Arm yourself to the teeth with this near Weapons only setup!", null),
			new Scenario (31, "Explosions and Flames", GameMode.Outbreak, exp, true, new String[] {
					"WE01", "WE24", "WE03", "WE31", "AC22", "WE28", "AC03", "AC27", "AM04", "WE26 WE27", "WE02", "WE22 WE23"},
					"Hardly any Ammo is required in this quirky Scenario.", null),
			new Scenario (32, "Mega Money", GameMode.Outbreak, exp, true, new String[] {
					"AC09", "AC06", "WE08", "AC29", "WE24", "WE20 WE21", "AM04", "AC03", "AC21", "WE02", "WE29 WE30", "AC30"},
					"", null),
			new Scenario (33, "A Gun Is a Gun", GameMode.Outbreak, exp, true, new String[] {
					"WE01 WE17 WE18", "WE25 WE26 WE27", "AC23", "WE08 WE29 WE30", "WE22 WE23 WE11 WE12", "AC25", "WE13 WE14 WE20 WE21", "WE15 WE16 WE19", "AC28", "AC04", "AC03", "AC21"},
					"Pushing all Weapon types into the same Resource Pile gives Players a much different game than normal...", null),
			new Scenario (34, "Special Forces", GameMode.Mercenary, exp, true, new String[] {
					"IT03", "WE13 WE14", "AC03", "WE01", "WE08", "AC07", "AC02", "AC05", "AC30", "AC29", "AC10", "WE11 WE12"},
					"Being quick on your feet and managing your Team's Inventory will be the key to this battle. If you find yourself running low on Health, you can always have one of your teammates Buy a First Aid Spray.", null),
			new Scenario (35, "Jack of All Trades", GameMode.Mercenary, exp, true, new String[] {
					"AC04", "AC03", "WE08", "WE13 WE14", "WE15 WE16", "WE01", "AC09", "AC29", "AC02", "AC24", "AC30", "WE11 WE12"},
					"A little bit of everything can be found in this scenario.", null),
			new Scenario (36, "Limited Movement", GameMode.Mercenary, exp, true, new String[] {
					"WE15 WE16", "WE13 WE14", "AC06", "AC09", "AC24", "AC11", "AC02", "AC30", "AC03", "WE01", "WE29 WE30", "WE11 WE12"},
					"Make no wasted movements as your Team will be down to one or two Actions per turn. Make every card count and you will come out victorious!", null),
			new Scenario (37, "Infested Supply Depot", GameMode.Mercenary, exp, true, new String[] {
					"WE28", "WE20 WE21", "AC15", "WE24", "WE26 WE27", "AC19", "AC18", "AC21", "AC22", "AC17", "AC24", "WE22 WE23"},
					"Featuring all the Weapons you could ever need, this abandoned Supply Depot guarantees one thing: Mass Destruction.", null),
			new Scenario (38, "Infested Shopping Mall", GameMode.Mercenary, exp, true, new String[] {
					"WE19", "WE20 WE21", "AC15", "AC13", "AC14", "AC19", "IT04", "WE29 WE30", "AC22", "AC17", "AM04", "WE22 WE23"},
					"Welcome to Raccoon City Mall, where anything great is located, everything is on sale, and everyone greets you with rotting teeth and a monstrous hunger! We've got what cures your hunger for Infected destruction!", null),
		}; }
	}
	
	public static class NightmareExpans  extends Expansion{
		private int exp = Expans.Nightmare.ordinal();
		public String expansName() { return "Nightmare"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Chris Redfield",					34, exp, 90, 
					  3, "When an Infected is Revealed, if Chris is Exploring and that Infected's Health is 40 or more, Chris deals an additional 10 Damage this turn.", 
					  0, "",
					  ""),
			new CharacterCard("Sergei Vladimir",				35, exp, 100, 
					  4, "During your turn, while Sergei is not Exploring, you can give 20 Damage to Sergei.  In that case, Sergei deals an additional 10 Damage during this turn.", 
					  6, "When Sergei Defeats an Infected, you can get +1 Explore.",
					  ""),
			new CharacterCard("Luis Sera",						36, exp, 80, 
					  3, "During your turn, you can get +1 card.  In that case, if you have 10 or less cards in your Discard Pile, Discard 1 card.", 
					  5, "When Luis Defeats an Infected, you can move any number of cards from your Discard Pile to the bottom of your Inventory in any order.",
					  ""),
			new CharacterCard("Josh Stone",						37, exp, 80, 
					  0, "When Josh's Health is Healed for the first time during your turn, you get +20 Gold.", 
					  7, "At the beginning of your turn, if Josh is at Maximum Health, you can move 1 Weapon from your Discard Pile to your Hand.",
					  ""),
			new CharacterCard("Mikhail Victor",					38, exp, 80, 
					  0, "Weapons cost you 10 less to Buy.", 
					  5, "While Mikhail is using Weapons with different Weapon types, Mikhail deals an additional 10 Damage.",
					  ""),
			new CharacterCard("Carlos Oliveira",				39, exp, 80, 
					  4, "At the beginning of your turn, you get +20 Gold.", 
					  9, "When you Buy a card with a cost of 80 or more, you can get +1 Explore.",
					  ""),
			new CharacterCard("Mysterious Mask",				40, exp, 80, 
					  4, "When Mysterious Mask Defeats an Infected, you can Trash 1 card from your Discard Pile.", 
					  8, "During your turn, you can Discard 1 Action from your Hand.  In that case, you get +2 cards.",
					  ""),
			new CharacterCard("Albert Wesker",					41, exp, 110, 
					  1, "At the beginning of your turn, if 1 other Player's Character has 30 or less Health, you can get +1 card.", 
					  6, "During your turn, you can have the Player who has a Character with 30 or less Health Discard 1 card.",
					  ""),
			new CharacterCard("Leon S. Kennedy",				42, exp, 80, 
					  3, "When Leon Defeats an Infected, you can Trash a card from your Discard Pile, then Gain a card with a cost of up to 10 more than the Trashed card.", 
					  6, "When you Gain a card, you get +1 card.",
					  ""),
			new CharacterCard("Ada Wong",						43, exp, 70, 
					  1, "During your turn, if Ada takes Damage, you can get +2 cards.", 
					  10, "When Ada's Health would be reduced to 0 or less, set it to 10 instead.",
					  ""),
		}; }
		public WeaponCard[] weapons() { return new WeaponCard[] {
			new WeaponCard("Silver Ghost", 					32, exp, WeaponType.Pistol,	  30,  10, 10, false, 4, "You get +1 Card and +1 Action during this turn."),
			new WeaponCard("Punisher", 						33, exp, WeaponType.Pistol,	  70,  30, 30, false, 1, "You get +2 Cards and +1 Action during this turn."),
			new WeaponCard("Mine Thrower", 					34, exp, WeaponType.Explosive,  120, 0,  0,  false, 5, "This Weapon gets +10 Damage during this turn for each Ammunition in your Play Area."),
			new WeaponCard("Broken Butterfly", 				35, exp, WeaponType.Magnum,	  80,  40, 40, false, 5, "This Weapon gets +20 Damage if the Exploring Character's Player has 10 or more cards in their inventory."),
			new WeaponCard("Single Shot Rifle W/ Scope", 	36, exp, WeaponType.Rifle,	  50,  40, 30, false, 4, "Select a Player.  That Player Discards 1 card from their Hand."),
			new WeaponCard("Special Ops Rifle", 			37, exp, WeaponType.Rifle,	  80,  50, 50, false, 1, "Every non-Exploring Character's Player Discards 1 card from their Hand."),
			new WeaponCard("Machine Pistol W/ Stock", 		38, exp, WeaponType.MachineGun, 40,  30, 20, false, 4, "You get +20 Gold during this turn."),
			new WeaponCard("Gangster's Machine Gun", 		39, exp, WeaponType.MachineGun, 80,  50, 50, false, 1, "This Weapon gets +10 Damage during this turn for each \"Machine Gun\" Weapon in your Discard Pile."),
			new WeaponCard("Flashbang", 					40, exp, WeaponType.Explosive,  20,  0,  10, true,  5, "You get +1 Explore this turn.  Additionally, while your Character is Exploring, all Revealed Infected with 40 or less Health deal 0 Damage."),
			new WeaponCard("HE Grenade", 					41, exp, WeaponType.Explosive,  40,  0,  20, true,  5, ""),
			new WeaponCard("Incendiary Grenade", 			42, exp, WeaponType.Explosive,  30,  0,  10, true,  5, "This Weapon gets +5 Damage this turn for each Infected your Character is Battling against.")
		}; }
		public ActionCard[] actions() { return new ActionCard[] {
			new ActionCard("Lonewolf",							32, exp, 5,  80, 0, 0,  20, 0, 0, 0, "When your Character Explores, select 1 Weapon they are using.  That Weapon gets +5 Damage this turn for each card in your Discard Pile."),
			new ActionCard("High Value Targets",				33, exp, 5,  60, 1, 0,  0,  0, 0, 0, "You get +5 Gold and +5 Ammo during this turn for each card in your Inventory."),
			new ActionCard("Raccoon City Police Department",	34, exp, 5,  50, 1, 0,  20, 0, 0, 0, "Discard cards from the top of your Inventory until you Discard a Weapon.  Move that Weapon to your Hand."),
			new ActionCard("PDA",								35, exp, 10, 20, 0, 0,  0,  0, 0, 0, "Look at up to the top 3 cards of your Inventory.  Trash 1 of those cards, then return the others to the top or bottom of your Inventory in any order."),
			new ActionCard("Toe to Toe",						36, exp, 10, 70, 1, 0,  0,  0, 0, 0, "Draw 1 card for every 5 cards in your Inventory."),
			new ActionCard("A Gift?",							37, exp, 5,  30, 0, 0,  0,  2, 0, 0, "Discard your Inventory."),
			new ActionCard("Mind Control",						38, exp, 5,  80, 0, 0,  0,  0, 0, 0, "Choose another Player and they Reveal their Hand.  Choose an Action Revealed this way with a cost of 70 or less and Discard it.  Apply its effect and Bonus Icons twice."),
			new ActionCard("Long Awaited Dawn",					39, exp, 5,  50, 2, 0,  0,  0, 0, 0, "Move up to 2 cards from a Player's Discard Pile to the top of their Inventory in any order."),
			new ActionCard("Vengeful Intention",				40, exp, 5,  40, 0, 0,  0,  2, 0, 0, "Your Character takes 10 Damage.  You may play as many Actions as you like during this turn.  When you play an Action during this turn, your Character takes 10 Damage (this does not include this Action)."),
			new ActionCard("Symbol of Evil",					41, exp, 5,  40, 0, 0,  0,  0, 0, 0, "Attach this card to your Character.  At the beginning of your turn, if the attached Character has 10 or less Health, Trash this card.  Otherwise, the attached Character takes 10 Damage and you get +1 card.")
		}; }
		public ItemCard[] items() { return new ItemCard[] {
			new ItemCard("Treasure Map",	74, exp, 0,  2, 2, "When this Item is Revealed, the Exploring Character's Player can attach this card to their Character.  During your turn, you can Remove this card from the Game.  In that case, you get +3 cards and +20 Gold during that turn."),
			new ItemCard("Hidden Treasure",	75, exp, 0,  2, 2, "When this Item is Revealed, the Exploring Character's Player gets +30 Gold and +1 Buy during this turn.  Remove this card from the Game afterwards."),
		}; }
		public MansionCard[] mansion() { return new MansionCard[] {
			new InfectedCard("Osmund Saddler", 			58, 1, exp, 90, 60, 8, false, "When this Infected is not Defeated, the Exploring Character's Player Gains 1 \"Ammo x10\", then an \"Ammo x10\" for each Level their Character has.  Then shuffle this Infected into the Mansion."),
			new InfectedCard("Bitores Mendez", 			59, 1, exp, 70, 50, 6, false, "When this Infected is Defeated, the Exploring Character's Player gets an extra turn after this one."),
			new InfectedCard("Regenerator", 			60, 2, exp, 40, 20, 4, false, "When this Infected is Revealed, all Weapons that are being used get -10 Damage during this turn."),
			new InfectedCard("Jack Krauser", 			61, 1, exp, 50, 30, 4, false, "When this Infected is Defeated, the Exploring Character's Player can Gain any card from the Resource Area that is on top of a Resource Pile."),
			new InfectedCard("Colmillos", 				62, 3, exp, 30, 20, 2, false, ""),
			new InfectedCard("Militia (Flail)", 		63, 3, exp, 15, 10, 1, false, ""),
			new InfectedCard("Militia (Stun Rod)", 		64, 2, exp, 20, 10, 1, false, ""),
			new InfectedCard("Militia (Rocket Launcher)",65, 2, exp, 30, 0, 2, false, "When this Infected is Revealed, the Exploring Character takes 50 Damage minus 10 for each card in that Character's Player's Inventory."),
			new InfectedCard("Novistador", 				66, 3, exp, 15, 10, 1, false, "When this Infected is Revealed, if the Exploring Character's Player has 3 or more Actions in their Play Area, this Infected cannot receive Damage during this turn."),
			new InfectedCard("Zombie Security Guard",	67, 3, exp, 15, 10, 1, false, "When this Infected is Defeated, the Exploring Character's Player gets +20 Gold and +1 Buy during this turn."),
			new InfectedCard("Zombie Technician", 		68, 3, exp, 20, 20, 2, false, ""),
			new InfectedCard("Verdugo", 				69, 2, exp, 40, 30, 4, false, "When this Infected is Revealed, if the Exploring Character's Player has 5 or less cards in their Inventory, this Infected cannot receive Damage during this turn."),
			new InfectedCard("Ivy", 					70, 2, exp, 20, 20, 1, false, "When this Infected is Defeated, the Exploring Character Heals their Health by 20."),
			new InfectedCard("Neptune", 				71, 2, exp, 30, 10, 2, false, "When this Infected is Revealed, if the Exploring Character has 30 or more Damage, this Infected gets +30 Damage during this turn."),
			new InfectedCard("Hunter", 					72, 2, exp, 40, 30, 4, false, "When this Infected is Revealed, the Player with the most Decorations lowers their Character's Maximum Health by 20."),
			new InfectedCard("Cerberus", 				73, 3, exp, 30, 10, 1, false, "When this Infected is Revealed, it gets -20 Health during this turn if the Exploring Character is using 2 or more Weapons."),
			new EventCard ("P.R.L 412",					76,    exp, 1, "When this Event is Revealed, Remove this card from the Game and the Player with the lowest Decorations looks at the top card of the Mansion and automatically Defeats it if it is an Infected.  Otherwise, shuffle that card and this Event into the Mansion.")
		}; }
		public Scenario[] scenarios() { return new Scenario[] {
			new Scenario (39, "Nightmare", GameMode.Story, exp, true, new String[] {
					"WE36 WE37", "AC32", "AC37", "AC33", "WE35", "AC39", "WE41", "AC35", "AC36", "AC10", "AC04", "WE38 WE39"},
					"", null),
			new Scenario (40, "Biohazard", GameMode.Story, exp, true, new String[] {
					"AC17", "AC36", "AC22", "AC35", "WE35", "AC37", "AC41", "AC34", "WE20 WE21", "WE36 WE37", "WE38 WE39", "AC33"},
					"", null),
			new Scenario (41, "Bigger is Better", GameMode.Story, exp, true, new String[] {
					"AC04", "WE02", "AC36", "AC33", "AC10", "AC32", "AC06", "WE35", "AC02", "AC03", "WE15 WE16", "WE13 WE14"},
					"Having a Bigger Inventory allows a player more options, and greater power...", null),
			new Scenario (42, "Surging Forward", GameMode.Story, exp, true, new String[] {
					"AC34", "AC36", "AC05", "AC02", "AC03", "AC38", "WE35", "WE32 WE33", "WE38 WE39", "WE02", "WE13 WE14", "WE36 WE37"},
					"Will you rush through the Mansion, or take your time gathering everything?", null),
			new Scenario (43, "Ambition", GameMode.Story, exp, true, new String[] {
					"AC40", "AC35", "AC38", "WE08", "AC10", "AC37", "AC02", "AC41", "WE38 WE39", "WE34", "WE13 WE14", "WE36 WE37"},
					"You only have 1 goal: Complete. Global. Saturation. And you intend to meet it.", null),
			new Scenario (44, "Loaded for Bear", GameMode.Story, exp, true, new String[] {
					"AM04", "WE34", "AC19", "AC17", "AC15", "WE41", "AC21", "WE35", "WE24", "WE20 WE21", "WE36 WE37", "WE32 WE33"},
					"Itching for a fight? Make sure to go in with an ample supply of Weaponry", null),
			new Scenario (45, "Griefing", GameMode.Story, exp, true, new String[] {
					"AC18", "AC19", "WE40", "WE34", "AC37", "AC38", "WE35", "AC36", "AC39", "WE36 WE37", "WE32 WE33", "WE20 WE21"},
					"Hold back the other players from winning, and you may find yourself in the lead...", null),
			new Scenario (46, "Hoarding", GameMode.Story, exp, true, new String[] {
					"AC33", "WE42", "AC36", "AC40", "AC17", "WE34", "AC32", "AC34", "WE38 WE39", "WE32 WE33", "WE20 WE21", "WE19"},
					"No Trash is the centerpiece of this Scenario", null),
			new Scenario (47, "Disposable", GameMode.Story, exp, true, new String[] {
					"WE41", "AC10", "AC06", "WE42", "AM04", "WE40", "AC17", "AC22", "AC34", "AC03", "WE31", "WE29 WE30"},
					"One and done is the easiest way to sum up this odd Scenario", null),
			new Scenario (48, "Millionaire", GameMode.Story, exp, true, new String[] {
					"AM04", "AC09", "AC03", "AC06", "AC21", "AC33", "WE34", "AC36", "WE24", "AC17", "WE29 WE30", "WE20 WE21"},
					"You'll feel like a million bucks when everything is within your grasp.", null),
			new Scenario (49, "Competitive", GameMode.Story, exp, true, new String[] {
					"WE31", "WE34", "WE35", "AC03", "AC29", "AC21", "AC02", "AC17", "AC26", "WE28", "WE32 WE33", "AC06"},
					"There is no one way to win with this Scenario", null),
			new Scenario (50, "Sacrifice", GameMode.Mercenary, exp, true, new String[] {
					"AC03", "AC41", "AC10", "AC37", "AC40", "AC36", "WE01", "WE34", "WE08", "WE38 WE39", "WE36 WE37", "WE32 WE33"},
					"Convert your Health into more beneficail gains, such as cards, ammo, and more. There are multiple ways to establish your team's dominance with this scenario", null),
			new Scenario (51, "Sluggish", GameMode.Mercenary, exp, true, new String[] {
					"AC38", "AC37", "AC06", "AC10", "AC09", "AC35", "WE41", "WE32 WE33", "WE38 WE39", "WE13 WE14", "WE08", "WE36 WE37"},
					"Less Actions demands smarter buting and more tactical decisions on your team's part", null),
			new Scenario (52, "Zombie Hunters", GameMode.Mercenary, exp, true, new String[] {
					"AC09", "AC04", "AC02", "AC33", "AC03", "AC39", "AC36", "WE34", "WE36 WE37", "WE13 WE14", "WE15 WE16", "WE38 WE39"},
					"The hunted becomes the hunter in this scenario", null),
			new Scenario (53, "Infested Police Station", GameMode.Mercenary, exp, true, new String[] {
					"AC17", "AC35", "AC22", "AC36", "AC34", "WE19", "WE20 WE21", "WE32 WE33", "AC39", "WE38 WE39", "WE22 WE23", "WE36 WE37"},
					"Your team has been dropped into a very familiar building: a police department, which has been completely overrun by the Infected!", null),
			new Scenario (54, "Infested Gun Shop", GameMode.Mercenary, exp, true, new String[] {
					"WE38 WE39", "WE35", "WE41", "WE32 WE33", "WE34", "AC19", "AC21", "AC15", "WE36 WE37", "WE22 WE23", "AC39", "WE20 WE21"},
					"Walls and cabinets stand in front of you, filled to the brim with every Weapon imaginable, eagerly awaiting use against those who had wronged their owner...", null)
		}; }
	}
	
	//	TODO: change deck quantities when the set is finally released
	public static class MercenariesExpans extends Expansion {
		private int exp = Expans.Mercenaries.ordinal();
		public String expansName() { return "Mercenaries"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Hunk",			45,  exp, 90, 
					  3, "When Hunk would take any damage, you can lower that Damage by 20.  In that case, Gain 1 \"Ammo x10\".", 
					  0, "",
					  "")
		}; }
		public WeaponCard[] weapons() { return new WeaponCard[] {
			new WeaponCard("Custom Standard Sidearm", 	48,  exp, WeaponType.Pistol,	  20, 20, 10, false,  0, "You can give this Weapon +10 Damage during this turn.  In that case, Trash this Weapon at the end of this turn."),
			new WeaponCard("Reliable Blade", 			50,  exp, WeaponType.Knife,	  0,  0,  5,  false,  0, "When you Defeat 1 or more Infected during this turn, you can Gain +10 Gold.  In that case, Trash this Weapon at the end of the turn."),
		}; }
		//public ActionCard[] actions() { return new ActionCard[] { }; }
		//public ItemCard[] items() { return new ItemCard[] { }; }
		//public MansionCard[] mansion() { return new MansionCard[] { }; }
		public AmmunitionCard[] ammunition() { return new AmmunitionCard[] {
			new AmmunitionCard("Ammo x10", 5, exp, 0,  28, 10, 10),
			new AmmunitionCard("Ammo x20", 6, exp, 30, 15, 20, 20),
			new AmmunitionCard("Ammo x30", 7, exp, 60, 15, 30, 30),
		}; }
		//public Scenario[] scenarios() { return new Scenario[] { }; }
	}
	
	public static class PromoCharacters1  extends Expansion{
		private int exp = Expans.Promo.ordinal();
		public String expansName() { return "Promo Characters"; }
		public CharacterCard[] characters() { return new CharacterCard[] {
			new CharacterCard("Chris Redfield", true,			1, exp, 90, 
					  4, "Reduce the Damage Infected deal to Chris by 10.", 
					  8, "Reduce the Damage Weapons deal to Chris by 10.  Additionally, Chris gets +1 Explore at the beginning of the turn.",
					  ""),
			new CharacterCard("Jill Valentine", true,			2, exp, 80, 
					  8, "At the beginning of your turn, you can Skip your current turn and Discard your Hand.  At the beginning of your next Turn, you get +5 Cards, +1 Buy, +1 Action, and +1 Explore.", 
					  0, "",
					  ""),
			new CharacterCard("George Hamilton", true,			3, exp, 80, 
					  2, "During your turn, you can Trash a card with \"Herb\" in its name from your Hand.  If it was a \"Green Herb\", you get +1 Action this turn.  If it was a \"Red Herb\", you get +1 Explore this turn.", 
					  6, "At the beginning of your turn, you can Reveal X cards with \"Herb\" in its name from your Hand.  In that case, you get +X cards.",
					  ""),
			new CharacterCard("Jim Chapman", true,				4, exp, 70, 
					  3, "When Jim Explores, you can flip a coin.  If heads, 1 Weapon Jim is using deals double Damage this turn.  If tails, Jim takes 10 Damage.", 
					  7, "During your turn, when an Infected is Revealed, you can Gain 2 \"Ammo x10\".  In that case, move all Revealed Infected to the bottom of the Mansion.",
					  ""),
			new CharacterCard("Albert Wesker", true,			5, exp, 100, 
					  2, "During your turn, if Abert's Maximum Health is 80 or less, you can get +1 card.", 
					  6, "During your turn, if Abert's Maximum Health is 60 or less, you can get +1 explore.",
					  ""),
			new CharacterCard("Yoko Suzuki", true,				6, exp, 70, 
					  3, "During your turn, you can attach a card from your Hand to Yoko, face down.  In that case, if another card was already attached to Yoko due to this effect, move that card to your Hand.", 
					  4, "When Yoko's Health would be reduced to 0 or less, you can reduce her maximum Health by 20 then Heal her to full Health instead.",
					  ""),
			new CharacterCard("Alyssa Ashcroft", true,			7, exp, 80, 
					  2, "When Alyssa Explores, if she is only using 1 Weapon, you can pay 20 Ammo.  In that case, that Weapon gets +10 Damage during this turn.", 
					  5, "During your turn, you can name a card type.  In that case, Reveal the top card of your Inventory.  If the Revealed card was the named type, draw it and you get +1 Explore during this turn.",
					  ""),
			new CharacterCard("Cindy Lennox", true,				8, exp, 70, 
					  1, "When you Gain a card with \"Herb\" in its name, you get +1 Buy during this turn.", 
					  5, "At the beginning of your turn, if Cindy is at full Health, Cindy deals an additional 20 Damage during this turn.",
					  ""),
		}; }
	}
	
	public String expansName() { return ""; }
	public CharacterCard[] characters() { return null; }
	public InfectedCharacterCard[] infecCharacters() { return null; }
	public WeaponCard[] weapons() { return null; }
	public ActionCard[] actions() { return null; }
	public ItemCard[] items() { return null; }
	public InfectionCard[] infections() { return null; }
	public MansionCard[] mansion() { return null; }
	public AmmunitionCard[] ammunition() { return null; }
	public Scenario[] scenarios() { return null; }
}
