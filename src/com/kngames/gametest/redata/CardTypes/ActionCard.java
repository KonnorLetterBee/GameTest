package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public class ActionCard extends RECard {

	private int price;
	private int extraActions;
	private int extraGold;
	private int extraAmmo;
	private int extraExplores;
	private int extraBuys;
	private int extraCards;
	
	private OnPlayListener listener = null;
	
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
		this(name, ID, expans, quantity, price, actions, gold, ammo, cards, buys, explores, text);
		listener = onPlay;
	}
	
	public int getExtraAmmo() { return extraAmmo; }
	public int getExtraCards() { return extraCards; }
	public int getExtraActions() { return extraActions; }
	public int getExtraGold() { return extraGold; }
	public int getExtraExplores() { return extraExplores; }
	public int getExtraBuys() { return extraBuys; }
	public int getPrice() { return price; }
	
	//	adds the action's attributes to the acting player, then performs the action's secondary abilities, if any
	public void onPlay(Game game, Player actingPlayer) {
		actingPlayer.actions--;
		
		actingPlayer.actions += extraActions;
		actingPlayer.ammo += extraAmmo;
		actingPlayer.buys += extraBuys;
		actingPlayer.explores += extraExplores;
		for (int i = 0; i < extraCards; i++) actingPlayer.drawToHand();
		actingPlayer.gold += extraGold;
		
		if (listener != null) listener.playAction(game, actingPlayer);
	}
}
