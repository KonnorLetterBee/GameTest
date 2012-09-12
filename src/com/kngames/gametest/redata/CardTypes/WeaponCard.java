package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard.Playable;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class WeaponCard extends RECard implements Playable {
	public static enum WeaponType { none, Knife, Pistol, Explosive, Bow, Melee, Magnum, Minigun, MachineGun, Shotgun, Rifle }
	
	private int price;
	private int ammoRec;
	private int damage;
	private boolean trashFlag;
	public int damageThisRound;
	private WeaponType type;
	
	private OnPlayListener playListener = null;
	private OnExploreFinishListener exploreFinishListener = null;
	
	public WeaponCard(String name, int ID, int expans, WeaponType type, int price, int ammo, int damage, boolean trashFlag, int quantity, String text) {
		super(name, CardType.Weapon, "WE", ID, expans, quantity, text);
		this.price = price;
		this.ammoRec = ammo;
		this.damage = damage;
		this.trashFlag = trashFlag;
		this.type = type;
	}
	
	public WeaponCard(String name, int ID, int expans, WeaponType type, int price, int ammo, int damage, boolean trashFlag, int quantity, String text,
		OnPlayListener play, OnExploreFinishListener exploreFinish) {
		this(name, ID, expans, type, price, ammo, damage, trashFlag, quantity, text);
		
		this.playListener = play;
		this.exploreFinishListener = exploreFinish;
	}
	
	public WeaponCard(WeaponCard other) {
		this(other.name, other.cardID, other.expansion, other.type, other.price, other.ammoRec, other.damage, other.trashFlag, other.deckQuantity, 
			other.text, other.playListener, other.exploreFinishListener);
	}
	
	public int getAmmoRec() { return ammoRec; }
	public int getDamage() { return damage; }
	public boolean trashFlagOn() { return trashFlag; }
	public int getPrice() { return price; }
	public WeaponType getWeaponType() { return type; }
	
	//	standard onPlay method for weapons
	//	adds the cards to the weapons deck of the current player
	public void onPlay(Game game, Player actingPlayer) {
		damageThisRound = damage;
		actingPlayer.ammoRemaining -= this.ammoRec;
		actingPlayer.weapons().addBack(this);
		
		//	if an extra OnPlayListener is attached, use that effect
		if (playListener != null) playListener.playCard(this, game, actingPlayer);
	}
	
	public void onExploreFinish(Game game, Player actingPlayer) {
		//	if an extra OnExploreFinishListener is attached, use that effect
		//	otherwise, simply move the card to the field
		if (exploreFinishListener != null) exploreFinishListener.exploreFinish(this, game, actingPlayer);
		else actingPlayer.inPlay().addBack(this);
	}
	
	//	weapons can be played only during your ExploreWeapons phase and if you have enough ammunition
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.ExploreWeapons && source == actingPlayer.hand() && actingPlayer.ammoRemaining >= this.ammoRec;
	}
}
