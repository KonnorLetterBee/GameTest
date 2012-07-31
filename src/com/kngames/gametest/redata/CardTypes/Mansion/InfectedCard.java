package com.kngames.gametest.redata.CardTypes.Mansion;

public class InfectedCard extends MansionCard {
	private int health;
	private int damage;
	private int decorations;
	private boolean lasPlagas;
	
	public InfectedCard(String name, int ID, int quantity, int expans, int health, int damage, int decorations, String text) {
		super(name, CardType.Infected, ID, quantity, expans, text);
		this.health = health;
		this.damage = damage;
		this.decorations = decorations;
		this.lasPlagas = false;
	}
	
	public InfectedCard(String name, int ID, int quantity, int expans, int health, int damage, int decorations, boolean plagas, String text) {
		super(name, CardType.Infected, ID, quantity, expans, text);
		this.health = health;
		this.damage = damage;
		this.decorations = decorations;
		this.lasPlagas = plagas;
	}

	public int getHealth() { return health; }
	public int getDamage() { return damage; }
	public int getDecorations() { return decorations; }
	public boolean isLasPlagas() { return lasPlagas; }
}
