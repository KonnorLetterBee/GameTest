package com.kngames.gametest.redata.CardTypes;

public class InfectedCharacterCard extends RECard {
	private int maxHealth;
	private int damage;
	public InfectedCharacterCard(String name, int ID, int expans, int maxHealth, String text, int damage) {
		super(name, CardType.InfecChar, "CH", "CH", ID, ID, expans, 1, text);
		this.maxHealth = maxHealth;
		this.damage = damage;
	}
	
	public int getMaxHealth() { return maxHealth; }
	public int getDamage() { return damage; }
}
