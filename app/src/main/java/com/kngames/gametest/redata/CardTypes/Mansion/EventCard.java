package com.kngames.gametest.redata.CardTypes.Mansion;

public class EventCard extends MansionCard {
	public EventCard(String name, int ID, int expans, int quantity, String text, CardComp[] comps) {
		super(name, CardType.Event, ID, quantity, expans, text, comps);
	}
	public EventCard(String name, int ID, int expans, int quantity, String text,
			OnMansionRevealListener reveal, OnMansionFinishListener finish) {
		super(name, CardType.Event, ID, quantity, expans, text, reveal, null, null, finish);
	}
}
