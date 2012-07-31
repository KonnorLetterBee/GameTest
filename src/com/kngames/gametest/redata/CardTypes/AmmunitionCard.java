package com.kngames.gametest.redata.CardTypes;

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
}
