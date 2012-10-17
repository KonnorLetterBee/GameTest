package com.kngames.gametest.redata.CardTypes.Mansion;

public class TokenCard extends MansionCard {
	public TokenCard(String name, int ID, int expans, String text, CardComp[] comps) {
		super(name, CardType.Token, ID, 1, expans, text, comps);
	}
	public TokenCard(String name, int ID, int expans, String text,
			OnMansionRevealListener reveal, OnMansionFinishListener finish) {
		super(name, CardType.Token, ID, 1, expans, text, reveal, null, null, finish);
	}
}
