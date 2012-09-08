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
	
	private OnPlayListener playListener = null;
	private OnFinishListener playFinishListener = null;
	private OnTriggerListener trigger = null;
	
	public ActionCard(String name, int ID, int expans, int quantity, int price, int actions, int gold, int ammo, int cards, int buys, int explores, String text) {
		super(name, CardType.Action, "AC", ID, expans, quantity, text);
		this.price = price;
		this.extraActions = actions;
		this.extraBuys = buys;
		this.extraCards = cards;
		this.extraExplores = explores;
		this.extraGold = gold;
		this.extraAmmo = ammo;
	}
	
	public ActionCard(String name, int ID, int expans, int quantity, int price, int actions, int gold, int ammo, int cards, int buys, int explores, String text,
			OnPlayListener onPlay) {
		this(name, ID, expans, quantity, price, actions, gold, ammo, cards, buys, explores, text, onPlay, null, null);
	}
	
	public ActionCard(String name, int ID, int expans, int quantity, int price, int actions, int gold, int ammo, int cards, int buys, int explores, String text,
			OnPlayListener onPlay, OnFinishListener onPlayFinish, OnTriggerListener onRespond) {
		this(name, ID, expans, quantity, price, actions, gold, ammo, cards, buys, explores, text);
		playListener = onPlay;
		playFinishListener = onPlayFinish;
		trigger = onRespond;
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
		
		//	if an extra OnPlayListener is attached, use that effect
		if (playListener != null) playListener.playAction(this, game, actingPlayer);
		
		//	if an extra OnPlayFinishListener is attached, use that effect
		//	otherwise, simply move the card to the field
		if (playFinishListener != null) playFinishListener.finish(this, game, actingPlayer);
		else actingPlayer.inPlay().addBack(this);
	}

	public boolean isTriggered(Game game, Player actingPlayer) {
		if (trigger == null) return false;
		return trigger.isTriggered(this, game, actingPlayer);
	}
	
	//	actions can be played if:
	//	it's your main phase, and you have at least one action remaining
	//	the action's trigger is valid, regardless of game state (trigger checks that anyway)
	public boolean canPlay(Game game, Player actingPlayer, REDeck source) {
		if (trigger != null && trigger.isTriggered(this, game, actingPlayer)) return true;
		else return game.isActivePlayer(actingPlayer) && actingPlayer.actions > 0 && game.state().currentState() == State.MainPhase && source == actingPlayer.hand();
	}
}
