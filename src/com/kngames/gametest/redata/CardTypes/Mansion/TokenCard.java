package com.kngames.gametest.redata.CardTypes.Mansion;

public class TokenCard extends MansionCard {
	public TokenCard(String name, int ID, int expans, String text) {
		super(name, CardType.Token, ID, 1, expans, text);
	}
}