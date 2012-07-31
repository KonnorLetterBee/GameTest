package com.kngames.gametest.redata.CardTypes.Mansion;

public class EventCard extends MansionCard {
	public EventCard(String name, int ID, int expans, int quantity, String text) {
		super(name, CardType.Event, ID, quantity, expans, text);
	}
}
