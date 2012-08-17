package com.kngames.gametest.redata.carddata;

import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.Mansion.*;
import com.kngames.gametest.redata.carddata.CardEffects;

public class CardData {
	public static enum Expans {Base, Alliaces, Outbreak, Nightmare, Basic, Promo, Custom}
	private static String[] ExpansionString = {"Base Set", "Alliances", "Outbreak", "Nightmare", "Basic", "Promo", "Custom"};
	public static String expansString(int value) {
		if (value < 0 || value >= ExpansionString.length) return "";
		return ExpansionString[value];
	}
	
	public static enum Origin {Inventory, Mansion, MansionWithID}
	private static String[] OriginString = {"Inventory", "Mansion", "Mansion"};
	public static String originString(int value) {
		if (value < 0 || value >= OriginString.length) return "";
		return OriginString[value];
	}
	
	public static final WeaponCard[] Weapons = {
		//	name, ID, expansion, price, ammo, damage, trashFlag, quantity, text
		new WeaponCard("Grenade", 						1,  0, 40,  0,  15, true,  5, "Story Mode or Mercenary Mode:  Deal 5 additional damage to each adjacent Player to the Attacking Player.\nVersus Mode:  Deal an additional 5 Damage to each adjacent Player to the Attacked Player."),
		new WeaponCard("Longbow", 						2,  0, 110, 0,  25, false, 5, ""),
		new WeaponCard("Submission", 					3,  0, 20,  0,  5,  false, 5, "Story Mode or Mercenary Mode:  This Weapon gets +5 Damage while your Character's Health is 80 or higher.\nVersus Mode:  Attacked Player must discard a Weapon from their hand."),
		new WeaponCard("Combat Knife", 					4,  4, 0,   0,  5,  false, 11, ""),
		new WeaponCard("Survival Knife", 				5,  4, 50,  0,  10, false, 1, "This Weapon gets +5 Damage for every other Knife Weapon used this turn."),
		new WeaponCard("Handgun", 						6,  4, 20,  20, 10, false, 9, ""),
		new WeaponCard("Burst-Fire Handgun",			7,  4, 60,  30, 20, false, 1, "While Attacking with more than 1 Weapon, this Weapon gets +20 Damage during this turn."),
		new WeaponCard("Six Shooter", 					8,  0, 90,  50, 50, false, 5, ""),
		new WeaponCard("Gatling Gun", 					9,  0, 110, -1, -1, false, 1, ""),
		new WeaponCard("Rocket Launcher", 				10, 0, 130, 0,  90, true,  1, "When Trashed, shuffle the the \"Rocket Launcher Case\" Token into the Mansion."),
		new WeaponCard("Assault Machine Gun", 			11, 0, 30,  40, 20, false, 4, ""),
		new WeaponCard("Full-Bore Machine Gun", 		12, 0, 100, 70, 40, false, 1, "If you have more than 100 Ammo, this Weapon gets +30 Damage."),
		new WeaponCard("Pump-Action Shotgun", 			13, 0, 40,  40, 25, false, 4, "You get +1 Explore this turn."),
		new WeaponCard("Automatic Shotgun", 			14, 0, 80,  80, 50, false, 1, "The next time an Infected is Revealed this turn, if its Health is 40 or lower, defeat it immediately.  Additionally, you get +1 Explore this turn."),
		new WeaponCard("Bolt-Action Rifle", 			15, 0, 50,  50, 20, false, 4, "Reveal the top card of your Inventory.  If its cost is 40 or more, this Weapon gets +30 Damage this turn."),
		new WeaponCard("Semi-Automatic Rifle", 			16, 0, 90,  70, 30, false, 1, "This Weapon gets +10 Damage for every Action played this turn."),
		new WeaponCard("Flash Grenade", 				17, 1, 20,  0,  0,  true,  5, "The next time a \"Las Plagas\" Infected is Revealed this turn, Defeat it immediately.  While your Character(s) is/are Battling, you can move 1 Infected with 20 or less Health to the bottom of the Mansion."),
		new WeaponCard("Grenade Launcher", 				18, 1, 80,  0,  20, false, 1, "All \"Explosive\" Weapons your Character uses during this turn go to your Discard Pile instead of being Trashed."),
		new WeaponCard("Telescopic Sight Rifle", 		19, 1, 50,  50, 30, false, 7, "During this turn, when your Character Explores, Reveal the bottom of the Mansion instead of the top."),
		new WeaponCard("Riot Shotgun", 					20, 1, 70,  60, 45, false, 7, "You get +1 Explore this turn."),
		new WeaponCard("Triple-Barreled Shotgun", 		21, 1, 90,  80, 50, false, 1, "You get +2 Explore this turn.  This Weapon gets +10 Damage during this turn for every Infected your Character(s) is/are Battling."),
		new WeaponCard("Russian Assault Rifle", 		22, 1, 40,  -1, -1, false, 4, "You cannot use more than 20 Ammo for this Weapon."),
		new WeaponCard("Signature Special", 			23, 1, 70,  -1, -1, false, 1, "You cannot use more than 60 Ammo for this Weapon."),
		new WeaponCard("Flamethrower", 					24, 1, 90,  0,  -1, false, 5, "X = 5 times the number of cards in your Discard Pile."),
		new WeaponCard("Blowback Pistol", 				25, 1, 40,  30, 20, false, 5, "You can Discard any number of \"Pistol\" Weapons from your Hand to give this Weapon +10 Damage during this turn for each \"Pistol\" Weapon Discarded due to this effect."),
		new WeaponCard("Standard Sidearm", 				26, 2, 30,  10, 10, false, 4, "This Weapon gets +5 Damage for each non-Item you Gained this turn."),
		new WeaponCard("Samurai Edge", 					27, 2, 70,  60, 30, false, 1, "This Weapon gets +20 Damage for each card you Gained this turn."),
		new WeaponCard("Stun Rod", 						28, 2, 30,  0,  10, false, 5, "You get +1 Explore this turn.  While your Character is Battling 2 or more Infected, you can move 1 of those Infected with 20 or less Health to the top of the Mansion."),
		new WeaponCard("Lightning Hawk", 				29, 2, 100, 60, 60, false, 4, ""),
		new WeaponCard("Hand Cannon", 					30, 2, 120, 80, 80, false, 1, "Trash 1 Ammunition from your Play Area.  This card cannot be attached to Characters."),
		new WeaponCard("Night Scope Rocket Launcher", 	31, 2, 80,  0,  60, true,  5, "While it is nighttime outside of the game, this Weapon gets +20 Damage."),
		new WeaponCard("Silver Ghost", 					32, 3, 30,  10, 10, false, 4, "You get +1 Card and +1 Action during this turn."),
		new WeaponCard("Punisher", 						33, 3, 70,  30, 30, false, 1, "You get +2 Cards and +1 Action during this turn."),
		new WeaponCard("Mine Thrower", 					34, 3, 120, 0,  0,  false, 5, "This Weapon gets +10 Damage during this turn for each Ammunition in your Play Area."),
		new WeaponCard("Broken Butterfly", 				35, 3, 80,  40, 40, false, 5, "This Weapon gets +20 Damage if the Exploring Character's Player has 10 or more cards in their inventory."),
		new WeaponCard("Single Shot Rifle W/ Scope", 	36, 3, 50,  40, 30, false, 4, "Select a Player.  That Player Discards 1 card from their Hand."),
		new WeaponCard("Special Ops Rifle", 			37, 3, 80,  50, 50, false, 1, "Every non-Exploring Character's Player Discards 1 card from their Hand."),
		new WeaponCard("Machine Pistol W/ Stock", 		38, 3, 40,  30, 20, false, 4, "You get +20 Gold during this turn."),
		new WeaponCard("Gangster's Machine Gun", 		39, 3, 80,  50, 50, false, 1, "This Weapon gets +10 Damage during this turn for each \"Machine Gun\" Weapon in your Discard Pile."),
		new WeaponCard("Flashbang", 					40, 3, 20,  0,  10, true,  5, "You get +1 Explore this turn.  Additionally, while your Character is Exploring, all Revealed Infected with 40 or less Health deal 0 Damage."),
		new WeaponCard("HE Grenade", 					41, 3, 40,  0,  20, true,  5, ""),
		new WeaponCard("Incendiary Grenade", 			42, 3, 30,  0,  10, true,  5, "This Weapon gets +5 Damage this turn for each Infected your Character is Battling against.")
	}; 
	
	public static final ActionCard[] Actions = {
		//	name, ID, expansion, quantity, price, actions, gold, ammo, cards, buys, explores, text
		new ActionCard("Mansion Foyer",						1,  0, 10, 30, 0, 0,  0,  2, 0, 0, ""),
		new ActionCard("Deadly Aim",						2,  0, 5,  50, 0, 0,  20, 0, 0, 0, "All your Weapons get +10 Damage this turn.", new CardEffects.DeadlyAimEffect(), null),
		new ActionCard("Shattered Memories",				3,  0, 5,  20, 0, 0,  0,  0, 0, 0, "Trash up to 2 cards from your Discard Pile.  Then Trash this card.", new CardEffects.ShatteredMemoriesEffect(), new CardEffects.TrashOnFinish()),
		new ActionCard("Escape from the Dead City",			4,  0, 10, 70, 2, 0,  0,  1, 0, 0, ""),
		new ActionCard("Reload",							5,  0, 5,  50, 2, 0,  20, 0, 0, 0, "Move 1 Weapon from your Discard Pile to your Hand.", new CardEffects.ReloadEffect(), null),
		new ActionCard("The Merchant",						6,  0, 6,  50, 0, 20, 0,  1, 1, 0, ""),
		new ActionCard("Umbrella Corporation",				7,  0, 5,  50, 1, 0,  0,  2, 0, 0, "Move 1 card from your Hand to the top of your Inventory.", new CardEffects.UmbrellaCorporationEffect(), null),
		new ActionCard("Back to Back",						8,  0, 5,  30, 1, 10, 0,  0, 0, 0, "When your Character is Attacked, you can Discard this card from your Hand to give 1 Weapon -20 Damage this turn."),
		new ActionCard("Item Management",					9,  0, 5,  30, 0, 0,  0,  0, 0, 0, "Trash 1 Ammunition from your Hand to Gain another Ammunition costing up to 30 Gold more than the Trashed card."),
		new ActionCard("Ominous Battle",					10, 0, 5,  60, 0, 10, 0,  3, 0, 0, "Trash 1 Card from your Hand."),
		new ActionCard("Master of Unlocking",				11, 0, 3,  30, 1, 0,  0,  0, 0, 0, "Each other Player reveals the top card of their Inventory.  You can Gain 1 of the Revealed Weapons.  All the other Revealed cards are Discarded afterwards."),
		new ActionCard("Struggle for Survival",				12, 0, 5,  30, 1, 0,  0,  0, 0, 1, "You can Discard this card from your Hand to lower the Damage of 1 Weapon being used costing 40 Gold or less to 0."),
		new ActionCard("Partners",							13, 1, 5,  30, 2, 0,  20, 0, 0, 0, "Attach this card to your Partner if you have one.  If this card is attached to your Partner at the beginning of your turn, you get +1 Action during this turn."),
		new ActionCard("Star-Crossed Duo",					14, 1, 8,  40, 0, 0,  0,  2, 1, 0, "Attach this card to your Partner if you have one.  When your Character Explores, if your Partner is leading with this card attached, 1 of your Partners Weapons gets +10 Damage during this turn."),
		new ActionCard("Great Ambition",					15, 1, 5,  50, 1, 10, 0,  0, 0, 0, "If you have no Partner, you get +2 cards."),
		new ActionCard("Archrival",							16, 1, 5,  30, 0, 0,  0,  2, 0, 0, "All Players Discard down to 1 or less cards attached to their Partners."),
		new ActionCard("Fierce Battle",						17, 1, 5,  80, 0, 0,  0,  4, 0, 0, "Choose another Player.  That Player gets +1 card."),
		new ActionCard("Uroboros Injection",				18, 1, 5,  60, 2, 0,  0,  0, 1, 0, "During another Player's turn, when an Infected is Revealed, you can Trash this card from your hand to give +20 Health to any Revealed Infected during this turn."),
		new ActionCard("Quirk of Fate",						19, 1, 8,  30, 1, 0,  0,  0, 0, 0, "Trash 1 card from your Hand.  Then you get +1 card."),
		new ActionCard("Cornered",							20, 1, 5,  30, 0, 0,  0,  0, 0, 0, "Attach this card to your Partner if you have one.  At the beginning of your turn, If you have another \"Cornered\" attached to your Partner, Trash both.  Then Gain 3 cards costing a total of 100 or less Gold and move them to the top of your Inventory in any order."),
		new ActionCard("Gathering Forces",					21, 1, 5,  90, 1, 20, 0,  0, 1, 0, "Any cards you Gain this turn go to your Hand instead."),
		new ActionCard("Desperate Escape",					22, 1, 5,  70, 1, 0,  20, 0, 0, 0, "Choose a Weapon Type (Magnum, Pistol, Rifle, etc).  All Weapons of that type get +10 Damage during this turn."),
		new ActionCard("Power of the T-Virus",				23, 2, 5,  20, 0, 0,  0,  0, 0, 0, "Your Character deals an additional 20 Damage this turn.  You can Trash this card to get +20 Gold during this turn.  At the end of the turn, if you Explored or Attacked, increase your Infection Level by 1."),
		new ActionCard("I Have This...",					24, 2, 5,  40, 1, 0,  0,  1, 0, 0, "You can Reveal a Weapon from your Hand.  In that case, if the cost of the Revealed Weapon was 50 or more, you get +1 card."),
		new ActionCard("Wesker\'s Secret",					25, 2, 5,  40, 0, 0,  0,  2, 0, 0, "Look at the top 4 cards of the Mansion.  If there is an \"Antivirus\" card, then you can reveal it, move it to the top of the Mansion, then move the rest to the bottom of the Mansion in a random order."),
		new ActionCard("Injection",							26, 2, 5,  30, 1, 0,  0,  0, 0, 0, "Discard any number of cards from your Hand, then draw the same amount.  Then, you can Trash this card.  In that case, decrease your Character's Infection Level by 1."),
		new ActionCard("By Any Means Necessary",			27, 2, 5,  40, 2, 0,  0,  0, 0, 0, "Decrease your Character's Infection Level by any amount.  Then, increase your character's Infection Level by the same amount, minus 1."),
		new ActionCard("Higher Priorities",					28, 2, 5,  70, 0, 0,  0,  0, 0, 0, "Choose One: +3 Cards, or +2 Actions."),
		new ActionCard("Parting Ways",						29, 2, 5,  30, 1, 0,  0,  0, 0, 0, "You can Trash a card from your Hand.  In that case, Gain a card from the Resource Area costing up to 20 more than the Trashed card."),
		new ActionCard("Returned Favor",					30, 2, 5,  50, 0, 0,  0,  2, 0, 0, "Attach this card to your Character.  At the beginning of your turn, if this card is attached to your Character, you get +1 card, then Discard this card."),
		new ActionCard("The Gathering Darkness",			31, 2, 5,  50, 0, 0,  0,  0, 0, 0, "Trash this card and choose another Player.  That Player Reveals their Hand and Trashes an Ammunition with the highest cost from their Hand.  That Player moves 1 \"Ammo x10\" Ammunition to their Hand for every 10 Ammo the Trashed card provided."),
		new ActionCard("Lonewolf",							32, 3, 5,  80, 0, 0,  20, 0, 0, 0, "When your Character Explores, select 1 Weapon they are using.  That Weapon gets +5 Damage this turn for each card in your Discard Pile."),
		new ActionCard("High Value Targets",				33, 3, 5,  60, 1, 0,  0,  0, 0, 0, "You get +5 Gold and +5 Ammo during this turn for each card in your Inventory."),
		new ActionCard("Raccoon City Police Department",	34, 3, 5,  50, 1, 0,  20, 0, 0, 0, "Discard cards from the top of your Inventory until you Discard a Weapon.  Move that Weapon to your Hand."),
		new ActionCard("PDA",								35, 3, 10, 20, 0, 0,  0,  0, 0, 0, "Look at up to the top 3 cards of your Inventory.  Trash 1 of those cards, then return the others to the top or bottom of your Inventory in any order."),
		new ActionCard("Toe to Toe",						36, 3, 10, 70, 1, 0,  0,  0, 0, 0, "Draw 1 card for every 5 cards in your Inventory."),
		new ActionCard("A Gift?",							37, 3, 5,  30, 0, 0,  0,  2, 0, 0, "Discard your Inventory."),
		new ActionCard("Mind Control",						38, 3, 5,  80, 0, 0,  0,  0, 0, 0, "Choose another Player and they Reveal their Hand.  Choose an Action Revealed this way with a cost of 70 or less and Discard it.  Apply its effect and Bonus Icons twice."),
		new ActionCard("Long Awaited Dawn",					39, 3, 5,  50, 2, 0,  0,  0, 0, 0, "Move up to 2 cards from a Player's Discard Pile to the top of their Inventory in any order."),
		new ActionCard("Vengeful Intention",				40, 3, 5,  40, 0, 0,  0,  2, 0, 0, "Your Character takes 10 Damage.  You may play as many Actions as you like during this turn.  When you play an Action during this turn, your Character takes 10 Damage (this does not include this Action)."),
		new ActionCard("Symbol of Evil",					41, 3, 5,  40, 0, 0,  0,  0, 0, 0, "Attach this card to your Character.  At the beginning of your turn, if the attached Character has 10 or less Health, Trash this card.  Otherwise, the attached Character takes 10 Damage and you get +1 card.")
	};
	
	public static final ItemCard[] Items = {
		//	name, ID, expansion, price, quantity, origin, text
		new ItemCard("Green Herb", 		1,  4, 20, 6, 0, "Trash this Item to Heal a Character's Health by 20, or, you can Trash this Item and another \"Green Herb\" from your Hand to heal your Character's Health by 60."),
		new ItemCard("Yellow Herb", 	2,  0, 0,  3, 1, "When this Item is Gained, attach this Item to your Character instead.  While it is attached, that Character's Maximum Health is increased by 10.  Then, Heal that Character's Health by 10."),
		new ItemCard("First Aid Spray", 3,  0, 60, 5, 0, "Trash this Item to Heal a Character's Health to full."),
		new ItemCard("Red Herb", 		4,  1, 20, 5, 0, "You can Trash this Item and a \"Green Herb\" from your Hand to heal a Character and their Partner's Health to full."),
		new ItemCard("Kevlar Jacket", 	54, 2, 0,  1, 2, "When this Item is Revealed, attach this Item to the Exploring Character.  When the attached Character would receive any Damage, you can move this card to the bottom of the Mansion.  In that case, that Character cannot receive any Damage during this turn."),
		new ItemCard("Antivirus", 		55, 2, 0,  2, 2, "When this Item is Revealed, if the Exploring Character's Infection Level is 6 or higher, decrease it by 3, then move this card to the bottom of the Mansion, or Remove this card from the Game."),
		new ItemCard("Treasure Map",	74, 3, 0,  2, 2, "When this Item is Revealed, the Exploring Character's Player can attach this card to their Character.  During your turn, you can Remove this card from the Game.  In that case, you get +3 cards and +20 Gold during that turn."),
		new ItemCard("Hidden Treasure",	75, 3, 0,  2, 2, "When this Item is Revealed, the Exploring Character's Player gets +30 Gold and +1 Buy during this turn.  Remove this card from the Game afterwards."),
	};
	
	public static final MansionCard[] Mansions = {
		//	Infected - name, ID, quantity, expansion, health, damage, decorations, text
		//	Token    - name, ID, expansion, text
		//	Event	 - name, ID, expansion, quantity, text
		new InfectedCard("Majini", 				1,  3, 0, 15, 10, 1, ""),
		new InfectedCard("Zombie (Male)", 		2,  2, 0, 20, 20, 1, ""),
		new InfectedCard("Zombie (Female)", 	3,  2, 0, 15, 10, 1, "When this Infected is Defeated, choose a Player.  That Player cannot play Actions on their next turn."),
		new InfectedCard("Zombie Butcher", 		4,  3, 0, 15, 10, 1, "When this Infected is Defeated, choose a Player and skip that Player's next turn."),
		new InfectedCard("Bui Kichwa", 			5,  3, 0, 10, 10, 1, ""),
		new InfectedCard("Licker", 				6,  3, 0, 40, 30, 3, ""),
		new InfectedCard("Nemesis T-Type", 		7,  1, 0, 60, 40, 5, "When this Infected is Revealed, each Player takes 20 Damage."),
		new InfectedCard("Hunter", 				8,  2, 0, 40, 30, 4, "If this Infected was not Defeated, randomly Trash 2 cards from the Attacking Player's Discard Pile."),
		new InfectedCard("Mimicry Marcus", 		9,  2, 0, 30, 20, 2, ""),
		new InfectedCard("Uroboros Aheri", 		10, 1, 0, 90, 70, 8, "If this Infected was not Defeated, Shuffle it into the Mansion."),
		new InfectedCard("Dr. Salvador", 		11, 3, 0, 20, 15, 2, ""),
		new TokenCard(   "Rocket Launcher Case",12,    0, "Gain 1 \"Rocket Launcher\" Weapon.  Remove this card from the game afterwards."),
		new TokenCard(   "Gatling Gun Case", 	13,    0, "Gain 1 \"Gatling Gun\" Weapon.  Remove this card from the game afterwards."),
		new InfectedCard("Gatling Gun Majini", 	14, 3, 0, 40, 25, 4, "This Infected gets +5 Damage for every 10 Ammo the Attacking Player has."),
		new InfectedCard("Cerberus", 			15, 3, 0, 25, 10, 2, ""),
		new InfectedCard("El Gigante", 			16, 1, 0, 40, 40, 4, ""),
		new InfectedCard("Executioner", 		17, 1, 0, 30, 25, 3, ""),
		
		new InfectedCard("Lurker", 				22, 3, 1, 10, 10, 1, ""),
		new InfectedCard("Infected Bat", 		23, 3, 1, 15, 20, 1, "While this Infected is being Attacked with another Infected, this Infected gets +20 Health during this turn."),
		new InfectedCard("Licker Beta", 		24, 2, 1, 30, 20, 3, ""),
		new InfectedCard("Duvalia", 			25, 2, 1, 60, 40, 5, true, "When this Infected kills a Character, the Player with the most Decorations Gains 5 \"Ammo x10\" cards."),
		new InfectedCard("Garrador", 			26, 2, 1, 50, 40, 4, ""),
		new InfectedCard("Zombie (Male)", 		27, 3, 1, 20, 10, 1, ""),
		new InfectedCard("Kipepo", 				28, 3, 1, 20, 20, 1, true, ""),
		new InfectedCard("Albert Wesker", 		29, 1, 1, 90, 70, 9, "When this Infected is Revealed, all other Revealed Infected get +10 Health and +10 Damage during this turn."),
		new InfectedCard("Cephalo", 			30, 3, 1, 25, 20, 2, true, "When this Infected is Defeated, choose a Player.  That Player must Explore at least once with their Character(s) on their next turn."),
		new InfectedCard("Proto Tyrant", 		31, 1, 1, 60, 40, 5, "When this Infected is Revealed, all non-Exploring Characters with 50 or more Health take 40 Damage."),
		new InfectedCard("Iron Maiden", 		32, 2, 1, 50, 30, 4, ""),
		new InfectedCard("Zombie Horde", 		33, 3, 1, 30, 20, 2, ""),
		new InfectedCard("Nosferatu",	 		34, 2, 1, 55, 30, 4, ""),
		new InfectedCard("Los Illuminados Monk",35, 2, 1, 15, 20, 3, "When this Infected is Revealed, it gets +5 Health and +5 Damage for each Infected attached to the Exploring Main Character."),
		new InfectedCard("Reaper",		 		36, 2, 1, 35, 40, 4, "When this Infected is Defeated, if it wasn't Defeated with Damage equal to its Health, the Attacking Player Trashes 1 of the cards attached to their Main Character's Partner with the highest cost, if any."),
		new InfectedCard("Guardian of Insanity",37, 3, 1, 30, 20, 2, "When this Infected is Revealed, each Player takes 10 Damage."),
		new EventCard("Explosive Barrel",		38,    1, 3, "When this Event is Revealed, Remove it from the Game.  If the Exploring Character(s) is/are dealing 80 or more total Damage, each of them takes 30 Damage.  Otherwise, that Character deals an additional 30 Damage and you get +1 Explore during this turn."),
		new EventCard("Collapsing Floor Trap",	39,    1, 1, "When this Event is Revealed, Remove it from the Game.  Reveal the next 2 cards of the Mansion.  If any of those cards are Infected, the Exploring Character(s) is/are now Battling it/them.  Shuffle all other Revealed cards into the Mansion."),
		new EventCard("Laser Targeting Device",	40,    1, 1, "When this Event is Revealed, look at the top card of the Mansion.  If it is an Infected, attach it to your Main Character and add its Decorations to your total, then shuffle this Event into the Mansion.  Otherwise, Shuffle this Event and that card into the Mansion."),
		new InfectedCard("Tyrant T-002", 		41, 1, 2, 90, 70, 8, "When this Infected was not Defeated, increase the Exploring Character's Infection Level by 1 plus their current Level, then shuffle this Infected into the Mansion."),
		new InfectedCard("Yawn", 				42, 2, 2, 40, 30, 4, "When this Infected is Revealed, if the Exploring Character's Infection Level is 5 or less, increase it by 2.  Otherwise, increase it by 1."),
		new InfectedCard("Nemesis (2nd Form)", 	43, 1, 2, 60, 40, 5, "When this Infected is Revealed, increase the Infection Level of all Characters by 1."),
		new InfectedCard("Grave Zombie", 		44, 3, 2, 10, 10, 1, ""),
		new InfectedCard("Wasp", 				45, 2, 2, 15, 0 , 2, "When this Infected is Revealed, the Exploring Character takes 20 Damage."),
		new InfectedCard("Web Spinner", 		46, 2, 2, 30, 10, 3, "When this Infected was not Defeated, increase the Exploring Character's Infection Level by 1."),
		new InfectedCard("Reinforced Zombie", 	47, 3, 2, 20, 10, 1, "While this Infected is attached to a Character, that Character's Decoration Requirements for Level 2 is reduced by 1."),
		new InfectedCard("Crimson Head", 		48, 2, 2, 40, 20, 4, "When this Infected is Revealed, if the Exploring Character has 1 or more Infected with \"Zombie\" in its name attached to them, this Infected gets +10 Health and +10 Damage during this turn."),
		new InfectedCard("Reinforced Licker", 	49, 3, 2, 30, 20, 3, "When this Infected is Revealed, Weapons with 0 Ammo Requirement deal 0 Damage during this turn."),
		new InfectedCard("Grave Digger", 		50, 2, 2, 30, 30, 3, ""),
		new InfectedCard("Zombie Cop", 			51, 3, 2, 15, 10, 1, ""),
		new InfectedCard("Eliminator", 			52, 2, 2, 20, 20, 2, ""),
		new InfectedCard("Lisa Trevor", 		53, 1, 2, 40, 20, 0, "When this Infected is Defeated, increase a Character's Infection Level by 1, then move this Infected to the bottom of the Mansion."),
		//	2 item cards listed in the items table
		new EventCard ("Laser Trap", 			56,    2, 1, "When this Event is Revealed, the Exploring Character takes 20 Damage for every different card type (Action, Weapon, Ammunition, etc.) in the controlling Player's Play Area.  Remove this Event from the game afterwards."),
		new EventCard ("Rock Trap", 			57,    2, 1, "When this Event is Revealed, the Exploring Character takes 10 Damage for every Action in the controlling Player's Play Area.  Remove this Event from the game afterwards."),
		new InfectedCard("Osmund Saddler", 			58, 1, 3, 90, 60, 8, "When this Infected is not Defeated, the Exploring Character's Player Gains 1 \"Ammo x10\", then an \"Ammo x10\" for each Level their Character has.  Then shuffle this Infected into the Mansion."),
		new InfectedCard("Bitores Mendez", 			59, 1, 3, 70, 50, 6, "When this Infected is Defeated, the Exploring Character's Player gets an extra turn after this one."),
		new InfectedCard("Regenerator", 			60, 2, 3, 40, 20, 4, "When this Infected is Revealed, all Weapons that are being used get -10 Damage during this turn."),
		new InfectedCard("Jack Krauser", 			61, 1, 3, 50, 30, 4, "When this Infected is Defeated, the Exploring Character's Player can Gain any card from the Resource Area that is on top of a Resource Pile."),
		new InfectedCard("Colmillos", 				62, 3, 3, 30, 20, 2, ""),
		new InfectedCard("Militia (Flail)", 		63, 3, 3, 15, 10, 1, ""),
		new InfectedCard("Militia (Stun Rod)", 		64, 2, 3, 20, 10, 1, ""),
		new InfectedCard("Militia (Rocket Launcher)", 65, 2, 3, 30, 0, 2, "When this Infected is Revealed, the Exploring Character takes 50 Damage minus 10 for each card in that Character's Player's Inventory."),
		new InfectedCard("Novistador", 				66, 3, 3, 15, 10, 1, "When this Infected is Revealed, if the Exploring Character's Player has 3 or more Actions in their Play Area, this Infected cannot receive Damage during this turn."),
		new InfectedCard("Zombie Security Guard",	67, 3, 3, 15, 10, 1, "When this Infected is Defeated, the Exploring Character's Player gets +20 Gold and +1 Buy during this turn."),
		new InfectedCard("Zombie Technician", 		68, 3, 3, 20, 20, 2, ""),
		new InfectedCard("Verdugo", 				69, 2, 3, 40, 30, 4, "When this Infected is Revealed, if the Exploring Character's Player has 5 or less cards in their Inventory, this Infected cannot receive Damage during this turn."),
		new InfectedCard("Ivy", 					70, 2, 3, 20, 20, 1, "When this Infected is Defeated, the Exploring Character Heals their Health by 20."),
		new InfectedCard("Neptune", 				71, 2, 3, 30, 10, 2, "When this Infected is Revealed, if the Exploring Character has 30 or more Damage, this Infected gets +30 Damage during this turn."),
		new InfectedCard("Hunter", 					72, 2, 3, 40, 30, 4, "When this Infected is Revealed, the Player with the most Decorations lowers their Character's Maximum Health by 20."),
		new InfectedCard("Cerberus", 				73, 3, 3, 30, 10, 1, "When this Infected is Revealed, it gets -20 Health during this turn if the Exploring Character is using 2 or more Weapons."),
		new EventCard ("P.R.L 412",					76,    3, 1, "When this Event is Revealed, Remove this card from the Game and the Player with the lowest Decorations looks at the top card of the Mansion and automatically Defeats it if it is an Infected.  Otherwise, shuffle that card and this Event into the Mansion.")
	};
	
	public static final InfectionCard[] Infections = {
		//	name, ID, expansion, quantity, damage, text
		new InfectionCard("Claw", 				1, 3, 5, 10, "The Player controlling the Character you Attacked can only play 1 Action on their next turn."),
		new InfectionCard("Virulent Frenzy", 	2, 3, 6, 0,  "Look at the top 3 cards of your Inventory and Trash all non-Infection cards.  Move the rest to the top of your Inventory in any order."),
		new InfectionCard("Bile Vomit",			3, 3, 6, 5,  "The Character your Character is Attacking takes 5 Damage for each other \"Bile Vomit\" used this turn."),
		new InfectionCard("Slough Armor",		4, 3, 6, 0,  "When your Character is Attacked, you can Discard this card from your Hand.  In that case, reduce the Damage of 1 Weapon neing used by 20."),
		new InfectionCard("Drag", 				5, 3, 6, 0,  "While there are 2 or more non-Infected Characters, the Character your Character is Attacking this turn cannot Attack your Character on their next turn."),
		new InfectionCard("Devour", 			6, 3, 6, 5,  "Heal your Character's Health by 20."),
		new InfectionCard("Infection", 			7, 3, 5, 0,  "Increase the Infection Level of the Character your Character is Attacking by 1."),
	};
	
	public static final CharacterCard[] Characters = {
		//	name, ID, expansion, maxHealth, ability1price, ability1, ability2price, ability2 
		new CharacterCard("Albert Wesker",					1,  0, 100, 
				  2, "When Albert Attacks a Character or Explores, you get +20 Gold for that turn.", 
				  9, "Story Mode: During your turn, choose a Player.  That Player gets +1 Explore and must Explore at least once on their next turn.",
				  ""),
		new CharacterCard("Leon S. Kennedy",				2,  0, 80, 
				  2, "All your \"Pistol\" Weapons get -10 Ammo Requirement.", 
				  6, "All your \"Pistol\" Weapons can be used twice per turn.",
				  ""),
		new CharacterCard("Claire Redfield",				3,  0, 70, 
				  2, "All your Ammunition provides additional +10 Ammo.", 
				  6, "All your Ammunition provides additional +10 Gold.",
				  ""),
		new CharacterCard("Sheva Alomar",					4,  0, 80, 
				  2, "During your turn, you can Exchange 1 card in your Hand with the top card of your inventory.", 
				  4, "All of your \"Rifle\" Weapons get -20 Ammo Requirement.",
				  ""),
		new CharacterCard("Barry Burton",					5,  0, 90, 
				  2, "When Barry Attacks a Character or Explores, you can give +5 damage to 1 Weapon Barry is using this turn.", 
				  7, "All your \"Magnum\" Weapons get +20 damage.",
				  ""),
		new CharacterCard("Ada Wong",	 					6,  0, 70, 
				  4, "When Ada Explores, you can look at the top card of the Mansion and move it to the top or bottom of the Mansion.", 
				  8, "At the beginning of your turn, you can get +1 Action and -1 Buy.",
				  ""),
		new CharacterCard("Jack Krauser",					7,  0, 180, 
				  1, "At the beginning of your turn, you can gain 1 \"Knife\" Weapon.", 
				  7, "All your \"Knife\" Weapons get +5 Damage.",
				  ""),
		new CharacterCard("Chris Redfield",					8,  0, 120, 
				  0, "Chirs cannot be Healed and cannot Attach non-Infected cards.", 
				  4, "1 Weapon Chris uses gets +5 Damage for each Infected he is fighting against.",
				  ""),
		new CharacterCard("Jill Valentine",					9,  0, 80, 
				  5, "All your \"Explosive\" Weapons get +5 Damage.", 
				  8, "All your \"Explosive\" Weapons go to your Discard Pile instead of being Trashed after being used.",
				  ""),
		new CharacterCard("Rebecca Chambers",				10, 0, 70, 
				  2, "When you would Trash any cards, you can move them to another Player's Discard Pile instead", 
				  4, "At the beginning of your turn, you can Trash 1 card from your Discard Pile",
				  ""),
		new CharacterCard("Carlos Oliveira",				11, 1, 90, 
				  2, "When Carlos Explores, you can look at the top card of the Mansion before playing your Weapons.", 
				  8, "If your Character's total Damage is equal to the total Health of all Infected Revealed during your turn, you can move 1 card from your Discard Pile to your Hand after Carlos Explores.",
				  ""),
		new CharacterCard("Josh Stone",						12, 1, 80, 
				  0, "Josh cannot use Weapons with a cost of 50 or more.", 
				  4, "Josh's Partner's maximum number of attachable cards is increased by 2.",
				  ""),
		new CharacterCard("Steve Burnside",					13, 1, 70, 
				  4, "At the beginning of your turn, Heal Steve's Health by 10.", 
				  8, "During your turn, you can Discard a \"Uroboros Injection\" from your Hand.  In that case, Heal Steve's Health by 20 and Steve deals an additional 30 Damage during this turn.",
				  ""),
		new CharacterCard("Jack Krauser",					14, 1, 90, 
				  0, "Jack cannot use more than 1 Weapon.", 
				  6, "Jack deals an additional X Damage during your turn, where X=the number of cards in your Hand times 10.",
				  ""),
		new CharacterCard("Hunk",							15, 1, 70, 
				  3, "When Hunk would take any Damage, you can give that Damage to your Partner instead.", 
				  6, "At the beginning of your turn, if Hunk has no Partner, you get +2 cards.",
				  ""),
		new CharacterCard("Jill Valentine (Brainwashed)",	16, 1, 90, 
				  3, "When Jill Explores, if her Partner has 1 or less cards attached, 1 of Jill's Weapons gets +10 Damage during this turn.", 
				  6, "During another Player's turn, when a \"Las Plagas\" Infected is Revealed, you can Trash a \"Flash Grenade\" from your Hand and apply its effect during this turn.",
				  ""),
		new CharacterCard("Billy Coen",						17, 1, 80, 
				  1, "During your turn, you can Gain 1 \"Ammo x10\".  In that case, +1 But this turn.", 
				  5, "During your turn, if you have 10 or more cards in your Discard Pile, you can move 1 of them to your Hand.",
				  ""),
		new CharacterCard("Leon S. Kennedy",				18, 1, 80, 
				  1, "During your turn, you can move 1 card from you Discard Pile to the bottom of your Inventory.", 
				  7, "During your turn, you can move the bottom card of your Inventory to the top.",
				  ""),
		new CharacterCard("Chris Redfield",					19, 1, 80, 
				  3, "While Chris is using only 1 Weapon, that Weapon gets -20 Ammo Requirement.", 
				  8, "While Chris is using only 1 Weapon type, all of those Weapons get -20 Ammo Requirement.  (A Weapon type would be Shotgun, Pistol, Magnum etc.)",
				  ""),
		new CharacterCard("Claire Redfield",				20, 1, 80, 
				  3, "When you Trash a \"Green Herb\" or a \"Red Herb\" from your Hand, Claire deals an additional 20 Damage during this turn.", 
				  7, "When you Trash a \"Red Herb\" and a \"Green Herb\", double all Damage Claire deals during this turn.",
				  ""),
		new CharacterCard("Excella Gionne",					21, 2, 70, 
				  2, "During your turn, you can increase Excella's Infection Level by 1.  In that case, you get +20 Gold during this turn.", 
				  4, "At the beginning of your turn, if Excella's Infection Level is 8 or higher, you get +3 cards.",
				  ""),
		new CharacterCard("Kevin Ryman",					22, 2, 80, 
				  0, "At the beginning of the game, shuffle 1 \"Samurai Edge\" Weapon from the Resource Area or from outside the game into your Inventory.", 
				  8, "During your turn, you can move 1 \"Pistol\" Weapon in your Play Area to the top of your Inventory.",
				  ""),
		new CharacterCard("David King",						23, 2, 80, 
				  2, "When David Explores, you can Discard a Weapon from your hand.  In that case, add the Discarded Weapon's effect text to 1 Weapon David is using.", 
				  6, "When you play a \"Knife\" Weapon from your Hand, move a \"Knife\" Weapon from your Discard Pile to your Play Area.  David is now using that moved weapon.",
				  ""),
		new CharacterCard("Mark Wilkins",					24, 2, 90, 
				  2, "While Mark is using 2 or more Weapons, Mark deals an additional 10 Damage.", 
				  6, "\"Melee\" Weapons Mark uses get +10 Damage.",
				  ""),
		new CharacterCard("Jill Valentine",					25, 2, 80, 
				  3, "At the beginning of your turn, decrease Jill's Infection Level by 1.", 
				  6, "While Jill's Infection Level is 0, you and Jill cannot be affected by any Infection effects.",
				  ""),
		new CharacterCard("Leon S. Kennedy",				26, 2, 70, 
				  1, "During your turn, you can put a card from your Hand on top of your Inventory.", 
				  7, "During your turn, you can Discard 1 card from your Hand.  In that case, move 1 Action from your Discard Pile to your Hand.",
				  ""),
		new CharacterCard("Ada Wong",						27, 2, 70, 
				  3, "At the beginning of your turn, if Ada's Health is 30 or less, you get +1 Action during this turn.", 
				  7, "At the beginning of your turn, you can Reveal any number of Actions from your Hand.  In that case, +1 card for every card Revealed.",
				  ""),
		new CharacterCard("Chris Redfield",					28, 2, 90, 
				  2, "During your turn, you can Discard any number of cards from your Hand.  Then, +20 Ammo during this turn for every card Discarded.", 
				  8, "When Chris Explores, if you have more than 100 Ammo, Chris deals an additional 20 Damage during this turn.",
				  ""),
		new CharacterCard("Rebecca Chambers",				29, 2, 70, 
				  3, "At the beginning of your turn, you can Discard any number of cards from your Hand.  Then decrease Rebecca's Infection Level by 1 for every card Discarded.", 
				  6, "When an effect decreases another Character's Infection Level, you get +1 card.",
				  ""),
		new CharacterCard("Hunk",							30, 2, 90, 
				  3, "At the beginning of your turn, Hunk can take 10 Damage.  In that case, you get +1 Explore during this turn.", 
				  7, "When Hunk Explores, if his Health is 30 or less, Hunk deals an additional 30 Damage during this turn.",
				  ""),
		new CharacterCard("Chris Redfield",					34, 3, 90, 
				  3, "When an Infected is Revealed, if Chris is Exploring and that Infected's Health is 40 or more, Chris deals an additional 10 Damage this turn.", 
				  0, "",
				  ""),
		new CharacterCard("Sergei Vladimir",				35, 3, 100, 
				  4, "During your turn, while Sergei is not Exploring, you can give 20 Damage to Sergei.  In that case, Sergei deals an additional 10 Damage during this turn.", 
				  6, "When Sergei Defeats an Infected, you can get +1 Explore.",
				  ""),
		new CharacterCard("Luis Sera",						36, 3, 80, 
				  3, "During your turn, you can get +1 card.  In that case, if you have 10 or less cards in your Discard Pile, Discard 1 card.", 
				  5, "When Luis Defeats an Infected, you can move any number of cards from your Discard Pile to the bottom of your Inventory in any order.",
				  ""),
		new CharacterCard("Josh Stone",						37, 3, 80, 
				  0, "When Josh's Health is Healed for the first time during your turn, you get +20 Gold.", 
				  7, "At the beginning of your turn, if Josh is at Maximum Health, you can move 1 Weapon from your Discard Pile to your Hand.",
				  ""),
		new CharacterCard("Mikhail Victor",					38, 3, 80, 
				  0, "Weapons cost you 10 less to Buy.", 
				  5, "While Mikhail is using Weapons with different Weapon types, Mikhail deals an additional 10 Damage.",
				  ""),
		new CharacterCard("Carlos Oliveira",				39, 3, 80, 
				  4, "At the beginning of your turn, you get +20 Gold.", 
				  9, "When you Buy a card with a cost of 80 or more, you can get +1 Explore.",
				  ""),
		new CharacterCard("Mysterious Mask",				40, 3, 80, 
				  4, "When Mysterious Mask Defeats an Infected, you can Trash 1 card from your Discard Pile.", 
				  8, "During your turn, you can Discard 1 Action from your Hand.  In that case, you get +2 cards.",
				  ""),
		new CharacterCard("Albert Wesker",					41, 3, 110, 
				  1, "At the beginning of your turn, if 1 other Player's Character has 30 or less Health, you can get +1 card.", 
				  6, "During your turn, you can have the Player who has a Character with 30 or less Health Discard 1 card.",
				  ""),
		new CharacterCard("Leon S. Kennedy",				42, 3, 80, 
				  3, "When Leon Defeats an Infected, you can Trash a card from your Discard Pile, then Gain a card with a cost of up to 10 more than the Trashed card.", 
				  6, "When you Gain a card, you get +1 card.",
				  ""),
		new CharacterCard("Ada Wong",						43, 3, 70, 
				  1, "During your turn, if Ada takes Damage, you can get +2 cards.", 
				  10, "When Ada's Health would be reduced to 0 or less, set it to 10 instead.",
				  ""),
	};
	
	public static final CharacterCard[] PromoCharacters = {
		//	name, ID, expansion, maxHealth, ability1price, ability1, ability2price, ability2 
		new CharacterCard("Chris Redfield", true,			1, 5, 90, 
				  4, "Reduce the Damage Infected deal to Chris by 10.", 
				  8, "Reduce the Damage Weapons deal to Chris by 10.  Additionally, Chris gets +1 Explore at the beginning of the turn.",
				  ""),
		new CharacterCard("Jill Valentine", true,			2, 5, 80, 
				  8, "At the beginning of your turn, you can Skip your current turn and Discard your Hand.  At the beginning of your next Turn, you get +5 Cards, +1 Buy, +1 Action, and +1 Explore.", 
				  0, "",
				  ""),
		new CharacterCard("George Hamilton", true,			3, 5, 80, 
				  2, "During your turn, you can Trash a card with \"Herb\" in its name from your Hand.  If it was a \"Green Herb\", you get +1 Action this turn.  If it was a \"Red Herb\", you get +1 Explore this turn.", 
				  6, "At the beginning of your turn, you can Reveal X cards with \"Herb\" in its name from your Hand.  In that case, you get +X cards.",
				  ""),
		new CharacterCard("Jim Chapman", true,				4, 5, 70, 
				  3, "When Jim Explores, you can flip a coin.  If heads, 1 Weapon Jim is using deals double Damage this turn.  If tails, Jim takes 10 Damage.", 
				  7, "During your turn, when an Infected is Revealed, you can Gain 2 \"Ammo x10\".  In that case, move all Revealed Infected to the bottom of the Mansion.",
				  ""),
		new CharacterCard("Albert Wesker", true,			5, 5, 100, 
				  2, "During your turn, if Abert's Maximum Health is 80 or less, you can get +1 card.", 
				  6, "During your turn, if Abert's Maximum Health is 60 or less, you can get +1 explore.",
				  ""),
		new CharacterCard("Yoko Suzuki", true,				6, 5, 70, 
				  3, "During your turn, you can attach a card from your Hand to Yoko, face down.  In that case, if another card was already attached to Yoko due to this effect, move that card to your Hand.", 
				  4, "When Yoko's Health would be reduced to 0 or less, you can reduce her maximum Health by 20 then Heal her to full Health instead.",
				  ""),
		new CharacterCard("Alyssa Ashcroft", true,			7, 5, 80, 
				  2, "When Alyssa Explores, if she is only using 1 Weapon, you can pay 20 Ammo.  In that case, that Weapon gets +10 Damage during this turn.", 
				  5, "During your turn, you can name a card type.  In that case, Reveal the top card of your Inventory.  If the Revealed card was the named type, draw it and you get +1 Explore during this turn.",
				  ""),
		new CharacterCard("Cindy Lennox", true,				8, 5, 70, 
				  1, "When you Gain a card with \"Herb\" in its name, you get +1 Buy during this turn.", 
				  5, "At the beginning of your turn, if Cindy is at full Health, Cindy deals an additional 20 Damage during this turn.",
				  ""),
	};
	
	public static final InfectedCharacterCard[] InfectedCharacters = {
		//	name, ID, expansion, maxHealth, ability1price, ability1, ability2price, ability2 
		new InfectedCharacterCard("Zombie A", 31, 2, 140, "When Berserker Attacks, all other non-Infected Characters that Berserker is not Attacking take 10 Damage.", 0),
		new InfectedCharacterCard("Zombie B", 32, 2, 140, "When a Character's Infection Level increases by 1 or more due to an effect, you can Trash an Infection card from your Hand.  In that case, increase that Character's Infection Level by 1 more.", 5),
		new InfectedCharacterCard("Zombie C", 33, 2, 140, "At the beginning of your turn, you can move 1 Infection card in your Hand to the bottom of the Infection Deck.  In that case, move the top card of the Infection Deck to your Hand.", 10),
	};
	
	public static final AmmunitionCard[] Ammunition = {
		//	name, ID, expansion, price, quantity, provided ammo, provided gold 
		new AmmunitionCard("Ammo x10", 1, 4, 0,  28, 10, 10),
		new AmmunitionCard("Ammo x20", 2, 4, 30, 15, 20, 20),
		new AmmunitionCard("Ammo x30", 3, 4, 60, 15, 30, 30),
		new AmmunitionCard("Treasure", 4, 1, 40, 10, 0,  30),
	};
	
	//	searches the Weapons array for a Weapon with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static WeaponCard findWeapon(String name) {
		for (int i = 0; i < Weapons.length; i++) {
			if (Weapons[i].getName().equalsIgnoreCase(name)) return Weapons[i];
		}
		return null;
	}
	
	//	searches the Actions array for an Action with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static ActionCard findAction(String name) {
		for (int i = 0; i < Actions.length; i++) {
			if (Actions[i].getName().equalsIgnoreCase(name)) return Actions[i];
		}
		return null;
	}
	
	//	searches the Items array for an Item with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static ItemCard findItem(String name) {
		for (int i = 0; i < Items.length; i++) {
			if (Items[i].getName().equalsIgnoreCase(name)) return Items[i];
		}
		return null;
	}
	
	//	searches the Mansions array for a Mansion card with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static MansionCard findMansion(String name) {
		for (int i = 0; i < Mansions.length; i++) {
			if (Mansions[i].getName().equalsIgnoreCase(name)) return Mansions[i];
		}
		return null;
	}
	
	//	searches the Characters array for a Character card with the specified name (not case-sensitive)
	//	and the specified expansion (since characters appear multiple times in different expansions)
	//	returns null if nothing was found
	public static CharacterCard findCharacter(String name, int expans) {
		for (int i = 0; i < Characters.length; i++) {
			if (Characters[i].getExpansion() == expans && Characters[i].getName().equalsIgnoreCase(name))
				return Characters[i];
		}
		for (int i = 0; i < PromoCharacters.length; i++) {
			if (PromoCharacters[i].getExpansion() == expans && PromoCharacters[i].getName().equalsIgnoreCase(name))
				return PromoCharacters[i];
		}
		return null;
	}
	
	//	searches the InfectedCharacters array for an InfectedCharacter card with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static InfectedCharacterCard findInfectedCharacter(String name) {
		for (int i = 0; i < InfectedCharacters.length; i++) {
			if (InfectedCharacters[i].getName().equalsIgnoreCase(name)) return InfectedCharacters[i];
		}
		return null;
	}
	
	//	searches the Ammunition array for an Ammunition card with the specified name (not case-sensitive)
	//	returns null if nothing was found
	public static AmmunitionCard findAmmunition(String name) {
		for (int i = 0; i < Ammunition.length; i++) {
			if (Ammunition[i].getName().equalsIgnoreCase(name)) return Ammunition[i];
		}
		return null;
	}
	
	//	searches the Weapons array for a Weapon with the specified id
	//	returns null if nothing was found
	public static WeaponCard findWeapon(int id) {
		for (int i = 0; i < Weapons.length; i++) {
			if (Weapons[i].getID() == id) return Weapons[i];
		}
		return null;
	}
	
	//	searches the Actions array for an Action with the specified id
	//	returns null if nothing was found
	public static ActionCard findAction(int id) {
		for (int i = 0; i < Actions.length; i++) {
			if (Actions[i].getID() == id) return Actions[i];
		}
		return null;
	}
	
	//	searches the Items array for an Item with the specified id
	//	returns null if nothing was found
	public static ItemCard findItem(int id) {
		for (int i = 0; i < Items.length; i++) {
			if (Items[i].getID() == id) return Items[i];
		}
		return null;
	}
	
	//	searches the Mansions array for a Mansion card with the specified id
	//	returns null if nothing was found
	public static MansionCard findMansion(int id) {
		for (int i = 0; i < Mansions.length; i++) {
			if (Mansions[i].getID() == id) return Mansions[i];
		}
		return null;
	}
	
	//	searches the Characters array for a Character card with the specified id
	//	returns null if nothing was found
	public static CharacterCard findCharacter(int id) {
		for (int i = 0; i < Characters.length; i++) {
			if (Characters[i].getID() == id) return Characters[i];
		}
		return null;
	}
	
	//	searches the Promo Characters array for a Character card with the specified id
	//	returns null if nothing was found
	public static CharacterCard findPromoCharacter(int id) {
		for (int i = 0; i < PromoCharacters.length; i++) {
			if (PromoCharacters[i].getID() == id) return PromoCharacters[i];
		}
		return null;
	}
	
	//	searches the InfectedCharacters array for an InfectedCharacter card with the specified id
	//	returns null if nothing was found
	public static InfectedCharacterCard findInfectedCharacter(int id) {
		for (int i = 0; i < InfectedCharacters.length; i++) {
			if (InfectedCharacters[i].getID() == id) return InfectedCharacters[i];
		}
		return null;
	}
	
	//	searches the Ammunition array for an Ammunition card with the specified id
	//	returns null if nothing was found
	public static AmmunitionCard findAmmunition(int id) {
		for (int i = 0; i < Ammunition.length; i++) {
			if (Ammunition[i].getID() == id) return Ammunition[i];
		}
		return null;
	}
	
	//	searches all arrays for a card with the specified 4-character tag
	//	returns null if nothing was found
	public static RECard findByCardTag(String tag) {
		String type = tag.substring(0, 2);
		int id = Integer.parseInt(tag.substring(2));
		
		if (type.equalsIgnoreCase("CH")) {
			RECard temp = (RECard)findCharacter(id);
			if (temp == null) temp = (RECard)findPromoCharacter(id);
			if (temp == null) temp = (RECard)findInfectedCharacter(id);
			return temp;
		}
		else if (type.equalsIgnoreCase("WE")) return (RECard)findWeapon(id);
		else if (type.equalsIgnoreCase("AC")) return (RECard)findAction(id);
		else if (type.equalsIgnoreCase("IT")) return (RECard)findItem(id);
		else if (type.equalsIgnoreCase("MA")) return (RECard)findMansion(id);
		else if (type.equalsIgnoreCase("AM")) return (RECard)findAmmunition(id);
		
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
