package com.kngames.gametest.redata.CardTypes;

public class InfectionCard extends RECard {
	private int damage;
	public InfectionCard(String name, int ID, int expans, int quant, int damage, String text, CardComp[] comps) {
		super(name, CardType.Infection, "IN", "IN", ID, ID, expans, quant, text, comps);
		this.damage = damage;
	}
	
	public int getDamage() { return damage; }
}
