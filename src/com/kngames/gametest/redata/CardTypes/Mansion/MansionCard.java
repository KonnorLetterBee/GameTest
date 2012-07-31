package com.kngames.gametest.redata.CardTypes.Mansion;

import com.kngames.gametest.redata.CardTypes.RECard;;

public class MansionCard extends RECard {
	public MansionCard(String name, CardType type, int ID, int quantity, int expans, String text) {
		super(name, type, "MA", ID, expans, quantity, text);
	}
}
