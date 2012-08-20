package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.CardTypes.RECard.Playable;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class WeaponCard extends RECard implements Playable {
	public static enum Type { none, Knife, Pistol, Explosive, Bow, Melee, Magnum, Minigun, MachineGun, Shotgun, Rifle }
	
	private int price;
	private int ammoRec;
	private int damage;
	private boolean trashFlag;
	public int damageThisRound;
	
	public WeaponCard(String name, int ID, int expans, int price, int ammo, int damage, boolean trashFlag, int quantity, String text) {
		super(name, CardType.Weapon, "WE", ID, expans, quantity, text);
		this.price = price;
		this.ammoRec = ammo;
		this.damage = damage;
		this.trashFlag = trashFlag;
	}
	
	public WeaponCard(WeaponCard other) {
		this(other.name, other.cardID, other.expansion, other.price, other.ammoRec, other.damage, other.trashFlag, other.deckQuantity, other.text);
	}
	
	public int getAmmoRec() { return ammoRec; }
	public int getDamage() { return damage; }
	public boolean trashFlagOn() { return trashFlag; }
	public int getPrice() { return price; }
	
	//	standard onPlay method for weapons
	//	adds the cards to the weapons deck of the current player
	public void onPlay(Game g, Player originPlayer) {
		damageThisRound = damage;
		originPlayer.weapons().addBack(this);
	}
	
	//	weapons can be played only during your ExploreWeapons phase
	public boolean canPlay(Game game, Player actingPlayer) {
		return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.ExploreWeapons;
	}
}
