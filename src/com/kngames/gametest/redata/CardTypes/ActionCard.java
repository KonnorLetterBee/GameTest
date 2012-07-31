package com.kngames.gametest.redata.CardTypes;

public class ActionCard extends RECard {

	private int price;
	private int extraActions;
	private int extraGold;
	private int extraAmmo;
	private int extraExplores;
	private int extraBuys;
	private int extraCards;
	
	public ActionCard(String name, int ID, int expans, int quantity, int price, int actions, int gold, int ammo, int cards, int buys, int explores, String text) {
		super(name, CardType.Action, "AC", ID, expans, quantity, text);
		this.price = price;
		this.extraActions = actions;
		this.extraBuys = buys;
		this.extraCards = cards;
		this.extraExplores = explores;
		this.extraGold = gold;
		this.extraAmmo = ammo;
	}
	
	public int getExtraAmmo() { return extraAmmo; }
	public int getExtraCards() { return extraCards; }
	public int getExtraActions() { return extraActions; }
	public int getExtraGold() { return extraGold; }
	public int getExtraExplores() { return extraExplores; }
	public int getExtraBuys() { return extraBuys; }
	public int getPrice() { return price; }
}
