package com.kngames.gametest.redata.CardTypes;

public class SkillsCard extends RECard {
	protected int expReq;
	
	public SkillsCard(String name, int ID, int intTag, int expans, int quantity, String text, int expReq, CardComp[] comps) {
		super(name, CardType.Skills, "SK", "SK", ID, intTag, expans, quantity, text, comps);
		this.expReq = expReq;
	}
	
	public int expReq() { return expReq; }
}
