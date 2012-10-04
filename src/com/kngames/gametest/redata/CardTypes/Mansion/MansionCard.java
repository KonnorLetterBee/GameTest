package com.kngames.gametest.redata.CardTypes.Mansion;

import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.*;
import com.kngames.gametest.regame.gamestruct.Game;

public class MansionCard extends RECard implements InMansion {
	
	protected OnMansionRevealListener mansionListener = null;
	protected OnMansionDefeatedListener defeatListener = null;
	protected OnMansionNotDefeatedListener notDefeatListener = null;
	protected OnMansionFinishListener finishListener = null;
	
	public MansionCard(String name, CardType type, int ID, int quantity, int expans, String text) {
		super(name, type, "MA", "MA", ID, ID, expans, quantity, text);
	}
	public MansionCard(String name, CardType type, int ID, int quantity, int expans, String text,
			OnMansionRevealListener reveal, OnMansionDefeatedListener defeat, 
			OnMansionNotDefeatedListener notDefeat, OnMansionFinishListener finish) {
		super(name, type, "MA", "MA", ID, ID, expans, quantity, text);
		this.mansionListener = reveal;
		this.defeatListener = defeat;
		this.notDefeatListener = notDefeat;
		this.finishListener = finish;
	}
	
	public void onMansionReveal(Game game) {
		//	if an extra OnMansionListener is attached, use that effect
		if (mansionListener != null) mansionListener.revealed(this, game);
	}
	
	//	use this method for cards that have a distinct possibility of winning or losing
	//	default behavior is to attach the infected to the attacking player (index 0) on a win, and
	//	return to the bottom of the mansion on a loss
	public void onMansionResult(Game game, boolean defeated) {
		if (defeated) {
			if (defeatListener != null) defeatListener.defeated(this, game);
			else game.attackingPlayers().get(0).attachedCards().addBack(this);
		}
		else if (notDefeatListener != null) notDefeatListener.notDefeated(this, game);
		else game.mansion().addBottom(this);
	}
	
	//	use this method for cards that only have a single effect as they're processed
	//	default behavior is simply to remove from the mansion
	public void onMansionFinish(Game game) {
		if (finishListener != null) finishListener.finish(this, game);
		else game.mansionRemoved().addBack(this);
	}
}
