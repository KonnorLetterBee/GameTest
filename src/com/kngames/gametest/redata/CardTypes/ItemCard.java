package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.*;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class ItemCard extends RECard implements Playable, InMansion {
	private int price;
	private int origin;
	
	//	default ItemCard
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text, CardComp[] comps) {
		super(name, CardType.Item, getItemIDPrefix(origin), "IT", ID, ID, expans, quantity, text, comps);
		this.price = price;
		this.origin = origin;
	}
	
	private static String getItemIDPrefix(int origin) {
		if (origin == 2) return "MA";
		else return "IT";
	}
	public int getPrice() { return price; }
	public int getOrigin() { return origin; }

	
	///
	///		Gameplay Hooks
	///
	
	//	items from a player's hand can be played during your main phase, or when the trigger is true
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		/*if (trigger != null && trigger.isTriggered(this, game, actingPlayer)) return true;
		else */return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.MainPhase && source == actingPlayer.hand();
	}
	
	public void onPlay(Game game, Player actingPlayer) {
		//	if an extra OnPlayListener is attached, use that effect
		if (this.compExists(RECard.HAND_PLAY)) this.getComponent(RECard.HAND_PLAY).execute();
			
		//	if an extra OnPlayFinishListener is attached, use that effect
		//	otherwise, simply move the card to the field
		if (this.compExists(RECard.HAND_FINISH)) this.getComponent(RECard.HAND_FINISH).execute();
		else actingPlayer.inPlay().addBack(this);
	}
	
	public void onMansionReveal(Game game) {
		//	if an extra OnMansionListener is attached, use that effect
		if (this.compExists(RECard.MANSION_REVEAL)) this.getComponent(RECard.MANSION_REVEAL).execute();
		
		//	if an extra OnMansionFinishListener is attached, use that effect
		//	otherwise, simply add the card to the mansion removed cards pile
		if (this.compExists(RECard.MANSION_FINISH)) this.getComponent(RECard.MANSION_FINISH).execute();
		else game.mansionRemoved().addBack(this);
	}
	
	public void onMansionFinish(Game game) {
		//	if an extra OnMansionFinishListener is attached, use that effect
		//	otherwise, simply add the card to the mansion removed cards pile
		if (this.compExists(RECard.MANSION_FINISH)) this.getComponent(RECard.MANSION_FINISH).execute();
		else game.mansionRemoved().addBack(this);
	}
}
