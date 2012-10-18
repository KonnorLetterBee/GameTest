package com.kngames.gametest.redata.CardTypes.Mansion;

import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.*;
import com.kngames.gametest.regame.gamestruct.Game;

public class MansionCard extends RECard implements InMansion {
	
	public MansionCard(String name, CardType type, int ID, int quantity, int expans, String text, CardComp[] comps) {
		super(name, type, "MA", "MA", ID, ID, expans, quantity, text, comps);
	}
	
	public void onMansionReveal(Game game) {
		//	if a "revealed" component is attached, use that effect
		if (this.compExists("revealed")) this.getComponent("revealed").execute();
	}
	
	//	use this method for cards that have a distinct possibility of winning or losing
	//	default behavior is to attach the infected to the attacking player (index 0) on a win, and
	//	return to the bottom of the mansion on a loss
	public void onMansionResult(Game game, boolean defeated) {
		if (defeated) {
			if (this.compExists("defeated")) this.getComponent("defeated").execute();
			else game.attackingPlayers().get(0).attachedCards().addBack(this);
		}
		else if (this.compExists("notDefeated")) this.getComponent("notDefeated").execute();
		else game.mansion().addBottom(this);
	}
	
	//	use this method for cards that only have a single effect as they're processed
	//	default behavior is simply to remove from the mansion
	public void onMansionFinish(Game game) {
		if (this.compExists("finished")) this.getComponent("finished").execute();
		else game.mansionRemoved().addBack(this);
	}
}
