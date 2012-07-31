package com.kngames.gametest.redata.CardTypes;

public class WeaponCard extends RECard {
	public static enum Type { none, Knife, Pistol, Explosive, Bow, Melee, Magnum, Minigun, MachineGun, Shotgun, Rifle }
	
	private int price;
	private int ammoRec;
	private int damage;
	private boolean trashFlag;
	
	public WeaponCard(String name, int ID, int expans, int price, int ammo, int damage, boolean trashFlag, int quantity, String text) {
		super(name, CardType.Weapon, "WE", ID, expans, quantity, text);
		this.price = price;
		this.ammoRec = ammo;
		this.damage = damage;
		this.trashFlag = trashFlag;
	}
	
	public int getAmmoRec() { return ammoRec; }
	public int getDamage() { return damage; }
	public boolean trashFlagOn() { return trashFlag; }
	public int getPrice() { return price; }
}
