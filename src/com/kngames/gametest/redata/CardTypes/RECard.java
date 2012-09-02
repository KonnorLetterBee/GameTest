package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.data.GameData;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;

public abstract class RECard extends Card {
	protected String name;
	protected String idPrefix;
	protected CardType cardType;
	protected int expansion;
	protected int deckQuantity;
	protected String text;
	
	public static enum CardType {Ammunition, Character, InfecChar, Weapon, Action, Item, Infected, Event, Token, Infection, Mansion}
	public static final String[] CardTypes = {"Ammunition", "Character", "Infected Character", "Weapon", "Action", "Item", "Infected", "Event", "Token", "Infection"};
	
	public RECard(String name, CardType type, String idPrefix, int ID, int expans, int quantity, String text) {
		super(ID);
		this.name = name;
		this.cardType = type;
		this.idPrefix = idPrefix;
		this.expansion = expans;
		this.text = text;
		this.deckQuantity = quantity;
		this.tag = generateTag();
	}
	
	public String getName() { return name; }
	public CardType getCardType() { return cardType; }
	public String getIDPrefix() { return idPrefix; }
	public String getIDString() { return String.format("%s-%03d", idPrefix, cardID); }
	public int getID() { return cardID; }
	public int getExpansion() { return expansion; }
	public int getDeckQuantity() { return deckQuantity; }
	public String getText() { return text; }
	public String getTag() { return tag; }
	
	public static Card parseTag(String tag) {
		return GameData.findByCardTag(tag);
	}
	public String generateTag() {
		return GameData.generateTagString(this);
	}
	
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		switch (cardType) {
		case Ammunition:
			return ((AmmunitionCard)this).canPlay(game, actingPlayer, source);
		case Action:
			return ((ActionCard)this).canPlay(game, actingPlayer, source);
		case Item:
			return ((ItemCard)this).canPlay(game, actingPlayer, source);
		case Weapon:
			return ((WeaponCard)this).canPlay(game, actingPlayer, source);
		default:
			return false;
		}
	}
	
	//	method to be called when a RECard is played
	//	defaults to placing the card into play and doing nothing else
	public void onPlay(Game game, Player actingPlayer) {
		actingPlayer.inPlay().addBack(this);
	}
	
	//	interfaces meant to be used by subclasses
	public interface OnPlayListener {
		public void playAction(RECard card, Game game, Player actingPlayer);
	}
	
	public interface OnFinishListener {
		public void finish(RECard card, Game game, Player actingPlayer);
	}
	
	public interface OnTriggerListener {
		public boolean isTriggered(RECard card, Game game, Player actingPlayer);
	}

	public interface Playable {
		public boolean canPlay(Game game, Player actingPlayer, REDeck source);
	}
	
	public interface MansionCard {
		public void onMansionReveal(Game game);
	}
	
	public interface OnMansionRevealListener {
		public void revealed(RECard card, Game game);
	}
	
	public interface OnMansionDefeatedListener {
		public void defeated(RECard card, Game game);
	}
	
	public interface OnMansionNotDefeatedListener {
		public void notDefeated(RECard card, Game game);
	}
	
	public interface OnMansionFinishListener {
		public void finish(RECard card, Game game);
	}
}
