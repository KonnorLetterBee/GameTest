package com.kngames.gametest.redata.CardTypes;

import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class ItemCard extends RECard implements RECard.Playable {
	private int price;
	private int origin;
	
	private OnPlayListener playListener = null;
	private OnPlayFinishListener playFinishListener = null;
	private OnTriggerListener trigger = null;
	
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text) {
		super(name, CardType.Item, getItemIDPrefix(origin), ID, expans, quantity, text);
		this.price = price;
		this.origin = origin;
	}
	
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnPlayListener onPlay) {
		this(name, ID, expans, price, quantity, origin, text, onPlay, null, null);
	}
	
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnPlayListener onPlay, OnPlayFinishListener onFinish, OnTriggerListener onTrigger) {
		super(name, CardType.Item, getItemIDPrefix(origin), ID, expans, quantity, text);
		this.price = price;
		this.origin = origin;
		this.playListener = onPlay;
		this.playFinishListener = onFinish;
		this.trigger = onTrigger;
	}
	
	private static String getItemIDPrefix(int origin) {
		if (origin == 2) return "MA";
		else return "IT";
	}
	public int getPrice() { return price; }
	public int getOrigin() { return origin; }

	//	items can be played during your main phase, or when the trigger is true
	public boolean canPlay(Game game, Player actingPlayer) {
		if (trigger != null && trigger.isTriggered(this, game, actingPlayer)) return true;
		else return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.MainPhase;
	}
}
