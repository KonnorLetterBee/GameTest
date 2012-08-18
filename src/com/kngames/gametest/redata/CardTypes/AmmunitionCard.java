package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.CardTypes.RECard.Playable;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class AmmunitionCard extends RECard implements Playable {
	private int price;
	private int provAmmo;
	private int provGold;
	
	public AmmunitionCard(String name, int ID, int expans, int price, int quantity, int provAmmo, int provGold) {
		super(name, CardType.Ammunition, "AM", ID, expans, quantity, "");
		this.price = price;
		this.provAmmo = provAmmo;
		this.provGold = provGold;
	}
	
	public int getPrice() { return price; }
	public int getProvAmmo() { return provAmmo; }
	public int getProvGold() { return provGold; }

	//	standard onPlay method for ammunition, simply adds the proper amount of gold and ammo to the playing character
	@Override
	public void onPlay(Game g, Player originPlayer) {
		originPlayer.ammo += provAmmo;
		originPlayer.gold += provGold;
		originPlayer.inPlay().addBack(this);
	}
	
	//	ammunition can be played only if it's your main phase
	public boolean canPlay(Game game, Player actingPlayer) {
		return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.MainPhase;
	}
}
