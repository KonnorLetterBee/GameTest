package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public class AmmunitionCard extends RECard {
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
}
