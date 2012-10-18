package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard.Playable;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.GameState.State;
import com.kngames.gametest.regame.gamestruct.Player;

public class ActionCard extends RECard implements Playable {

	private int price;
	private int extraActions;
	private int extraGold;
	private int extraAmmo;
	private int extraExplores;
	private int extraBuys;
	private int extraCards;
	
	public ActionCard(String name, int ID, int expans, int quantity, int price, int actions, int gold, int ammo, int cards, int buys, int explores, String text, CardComp[] comps) {
		super(name, CardType.Action, "AC", "AC", ID, ID, expans, quantity, text, comps);
		this.price = price;
		this.extraActions = actions;
		this.extraBuys = buys;
		this.extraCards = cards;
		this.extraExplores = explores;
		this.extraGold = gold;
		this.extraAmmo = ammo;
	}
	
	public int getExtraAmmo() { return extraAmmo; }
	public int getExtraCards() { return extraCards; }
	public int getExtraActions() { return extraActions; }
	public int getExtraGold() { return extraGold; }
	public int getExtraExplores() { return extraExplores; }
	public int getExtraBuys() { return extraBuys; }
	public int getPrice() { return price; }
	
	//	adds the action's attributes to the acting player, then performs the action's secondary abilities, if any
	@Override
	public void onPlay(Game game, Player actingPlayer) {
		actingPlayer.actions--;
		
		actingPlayer.actions += extraActions;
		actingPlayer.ammo += extraAmmo;
		actingPlayer.ammoRemaining += extraAmmo;
		actingPlayer.buys += extraBuys;
		actingPlayer.explores += extraExplores;
		for (int i = 0; i < extraCards; i++) actingPlayer.drawToHand();
		actingPlayer.gold += extraGold;
		
		//	if an extra HAND_PLAY component is attached, use that effect
		if (this.compExists(RECard.HAND_PLAY)) this.getComponent(RECard.HAND_PLAY).execute();
		
		//	if an extra HAND_FINISH component is attached, use that effect
		//	otherwise, simply move the card to the field
		if (this.compExists(RECard.HAND_FINISH)) this.getComponent(RECard.HAND_FINISH).execute();
		else actingPlayer.inPlay().addBack(this);
	}

	public boolean isTriggered(Game game, Player actingPlayer) {
		CardComp trigger = this.getComponent(RECard.HAND_FINISH);
		if (trigger != null && trigger instanceof CardConditionComp) return ((CardConditionComp)trigger).evaluate();
		else return false;
	}
	
	//	actions can be played if:
	//	it's your main phase, and you have at least one action remaining
	//	the action's trigger is valid, regardless of game state (trigger checks that anyway)
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		/*if (trigger != null && isTriggered(game, actingPlayer)) return true;
		else */
		return game.isActivePlayer(actingPlayer) && 
				actingPlayer.actions > 0 && 
				game.state().currentState() == State.MainPhase && 
				source == actingPlayer.hand();
	}
}
