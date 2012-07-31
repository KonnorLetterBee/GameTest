package com.kngames.gametest.redata.CardTypes;

public class InfectionCard extends RECard {
	private int damage;
	public InfectionCard(String name, int ID, int expans, int quant, int damage, String text) {
		super(name, CardType.Infection, "IN", ID, expans, quant, text);
		this.damage = damage;
	}
	
	public int getDamage() { return damage; }
}
