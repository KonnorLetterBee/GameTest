package com.kngames.gametest.redata.CardTypes;

public class CharacterCard extends RECard {
	private int maxHealth;
	private int a1price;
	private int a2price;
	private String ability2;
	public CharacterCard(String name, int ID, int expans, int maxHealth, int a1price, String ability1, int a2price, String ability2) {
		super(name, CardType.Character, "CH", ID, expans, 1, ability1);
		this.maxHealth = maxHealth;
		this.a1price = a1price;
		this.a2price = a2price;
		this.ability2 = ability2;
	}
	
	public CharacterCard(String name, boolean isPromo, int ID, int expans, int maxHealth, int a1price, String ability1, int a2price, String ability2) {
		super(name, CardType.Character, getExpPrefix(isPromo), ID, expans, 1, ability1);
		this.maxHealth = maxHealth;
		this.a1price = a1price;
		this.a2price = a2price;
		this.ability2 = ability2;
	}
	
	private static String getExpPrefix(boolean promo) {
		if (promo) return "PR";
		else return "CH";
	}
	public int getMaxHealth() { return maxHealth; }
	public int getA1Price() { return a1price; }
	public String getAbility1() { return text; }
	public int getA2Price() { return a2price; }
	public String getAbility2() { return ability2; }
}
