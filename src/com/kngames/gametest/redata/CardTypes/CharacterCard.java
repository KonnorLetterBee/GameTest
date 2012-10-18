package com.kngames.gametest.redata.CardTypes;

public class CharacterCard extends RECard {
	private int maxHealth;
	private int a1price;
	private int a2price;
	private String ability2;
	private String customInventory;
	
	public CharacterCard(String name, String idPrefix, int ID, int tagID, int expans, int maxHealth, int a1price, String ability1, int a2price, String ability2, String customInv, CardComp[] comps) {
		super(name, CardType.Character, idPrefix, "CH", ID, tagID, expans, 1, ability1, comps);
		this.maxHealth = maxHealth;
		this.a1price = a1price;
		this.a2price = a2price;
		this.ability2 = ability2;
		this.customInventory = customInv;
	}
	
	public CharacterCard(String name, String idPrefix, int ID, int expans, int maxHealth, int a1price, String ability1, int a2price, String ability2, String customInv, CardComp[] comps) {
		this(name, idPrefix, ID, ID, expans, maxHealth, a1price, ability1, a2price, ability2, customInv, comps);
	}
	
	public int getMaxHealth() { return maxHealth; }
	public int getA1Price() { return a1price; }
	public String getAbility1() { return text; }
	public int getA2Price() { return a2price; }
	public String getAbility2() { return ability2; }
	public String getCustomInventory() { return customInventory; }
}
