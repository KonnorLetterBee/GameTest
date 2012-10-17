package com.kngames.gametest.redata.CardTypes.Mansion;

public class InfectedCard extends MansionCard {
	private int health;
	private int damage;
	private int decorations;
	private boolean lasPlagas;
	
	public InfectedCard(String name, int ID, int quantity, int expans, int health, int damage, int decorations, boolean plagas, String text, CardComp[] comps) {
		super(name, CardType.Infected, ID, quantity, expans, text, comps);
		this.health = health;
		this.damage = damage;
		this.decorations = decorations;
		this.lasPlagas = plagas;
	}
	
	public InfectedCard(String name, int ID, int quantity, int expans, int health, int damage, int decorations, boolean plagas, String text,
			OnMansionRevealListener reveal, OnMansionDefeatedListener defeat, OnMansionNotDefeatedListener notDefeat) {
		super(name, CardType.Infected, ID, quantity, expans, text, reveal, defeat, notDefeat, null);
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
