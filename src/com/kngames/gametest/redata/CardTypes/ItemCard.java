package com.kngames.gametest.redata.CardTypes;

public class ItemCard extends RECard {
	private int price;
	private int origin;
	
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text) {
		super(name, CardType.Item, getItemIDPrefix(origin), ID, expans, quantity, text);
		this.price = price;
		this.origin = origin;
	}
	
	private static String getItemIDPrefix(int origin) {
		if (origin == 2) return "MA";
		else return "IT";
	}
	public int getPrice() { return price; }
	public int getOrigin() { return origin; }
}
