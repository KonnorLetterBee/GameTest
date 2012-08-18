package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;

public class ActionCard extends RECard {

	private int price;
	private int extraActions;
	private int extraGold;
	private int extraAmmo;
	private int extraExplores;
	private int extraBuys;
	private int extraCards;
	
	private OnPlayListener playListener = null;
	private OnPlayFinishListener playFinishListener = null;
	private OnRespondListener respondListener = null;
	
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
			OnPlayListener onPlay, OnPlayFinishListener onPlayFinish, OnRespondListener onRespond) {
		this(name, ID, expans, quantity, price, actions, gold, ammo, cards, buys, explores, text);
		playListener = onPlay;
		playFinishListener = onPlayFinish;
		respondListener = onRespond;
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
}
