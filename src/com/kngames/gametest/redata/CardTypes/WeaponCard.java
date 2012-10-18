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
	
	public WeaponCard(String name, int ID, int expans, WeaponType type, int price, int ammo, int damage, boolean trashFlag, int quantity, String text, CardComp[] comps) {
		super(name, CardType.Weapon, "WE", "WE", ID, ID, expans, quantity, text, comps);
		this.price = price;
		this.ammoRec = ammo;
		this.damage = damage;
		this.trashFlag = trashFlag;
		this.type = type;
	}
	
	public WeaponCard(WeaponCard other) {
		this(other.name, other.cardID, other.expansion, other.type, other.price, other.ammoRec, other.damage, other.trashFlag, other.deckQuantity, 
				other.text, other.components.values().toArray(new CardComp[1]));
	}
	
	public int getAmmoRec() { return ammoRec; }
	public int getDamage() { return damage; }
	public boolean trashFlagOn() { return trashFlag; }
	public int getPrice() { return price; }
	public WeaponType getWeaponType() { return type; }
	
	//	standard onPlay method for weapons
	//	adds the cards to the weapons deck of the current player
	//	if ammo or damage are -1 (X), don't add them to ammo, or set damage
	public void onPlay(Game game, Player actingPlayer) {
		if (damage != -1) damageThisRound = damage;
		if (ammoRec != -1) actingPlayer.ammoRemaining -= this.ammoRec;
		actingPlayer.weapons().addBack(this);
		
		//	if an extra HAND_PLAY component is attached, use that effect
		if (this.compExists(RECard.HAND_PLAY)) this.getComponent(RECard.HAND_PLAY).execute();
	}
	
	public void onExploreFinish(Game game, Player actingPlayer) {
		//	if an extra EXPLORE_FINISH component is attached, use that effect
		//	otherwise, look at the trash flag to determine behavior
		if (this.compExists(RECard.EXPLORE_FINISH)) this.getComponent(RECard.EXPLORE_FINISH).execute();
		else if (trashFlag == true) game.shop().returnCard(this);
		else actingPlayer.inPlay().addBack(this);
	}
	
	//	weapons can be played only during your ExploreWeapons phase and if you have enough ammunition
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.ExploreWeapons && source == actingPlayer.hand() && actingPlayer.ammoRemaining >= this.ammoRec;
	}
}
