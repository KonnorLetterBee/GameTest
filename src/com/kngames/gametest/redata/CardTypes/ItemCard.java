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
	
	private OnPlayListener playListener = null;
	private OnFinishListener playFinishListener = null;
	private OnTriggerListener trigger = null;
	private OnMansionRevealListener mansionListener = null;
	private OnMansionFinishListener mansionFinishListener = null;
	
	//	default ItemCard
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text, CardComp[] comps) {
		super(name, CardType.Item, getItemIDPrefix(origin), "IT", ID, ID, expans, quantity, text, comps);
		this.price = price;
		this.origin = origin;
	}
	
	//	ItemCard with special OnPlayListener
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnPlayListener onPlay) {
		this(name, ID, expans, price, quantity, origin, text, onPlay, null, null);
	}
	
	//	ItemCard with OnPlayListener, OnFinishListener, and OnTriggerListener
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnPlayListener onPlay, OnFinishListener onFinish, OnTriggerListener onTrigger) {
		this(name, ID, expans, price, quantity, origin, text, new CardComp[1]);
		this.playListener = onPlay;
		this.playFinishListener = onFinish;
		this.trigger = onTrigger;
	}
	
	//	ItemCard with OnMansionRevealListener
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnMansionRevealListener onReveal) {
		this(name, ID, expans, price, quantity, origin, text, onReveal, null);
	}
	
	//	ItemCard with OnMansionRevealListener and OnMansionFinishListener
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnMansionRevealListener onReveal, OnMansionFinishListener onFinish) {
		this(name, ID, expans, price, quantity, origin, text, new CardComp[1]);
		this.mansionListener = onReveal;
		this.mansionFinishListener = onFinish;
	}
	
	//	ItemCard with all types of listeners
	public ItemCard(String name, int ID, int expans, int price, int quantity, int origin, String text,
			OnPlayListener onPlay, OnFinishListener onFinish, OnTriggerListener onTrigger,
			OnMansionRevealListener onReveal, OnMansionFinishListener onMansionFinish) {
		this(name, ID, expans, price, quantity, origin, text, new CardComp[1]);
		this.playListener = onPlay;
		this.playFinishListener = onFinish;
		this.trigger = onTrigger;
		this.mansionListener = onReveal;
		this.mansionFinishListener = onMansionFinish;
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
		if (trigger != null && trigger.isTriggered(this, game, actingPlayer)) return true;
		else return game.isActivePlayer(actingPlayer) && game.state().currentState() == State.MainPhase && source == actingPlayer.hand();
	}
	
	public void onPlay(Game game, Player actingPlayer) {
		//	if an extra OnPlayListener is attached, use that effect
		if (playListener != null) playListener.playCard(this, game, actingPlayer);
			
		//	if an extra OnPlayFinishListener is attached, use that effect
		//	otherwise, simply move the card to the field
		if (playFinishListener != null) playFinishListener.finish(this, game, actingPlayer);
		else actingPlayer.inPlay().addBack(this);
	}
	
	public void onMansionReveal(Game game) {
		//	if an extra OnMansionListener is attached, use that effect
		if (mansionListener != null) mansionListener.revealed(this, game);
		
		//	if an extra OnMansionFinishListener is attached, use that effect
		//	otherwise, simply add the card to the mansion removed cards pile
		if (mansionFinishListener != null) mansionFinishListener.finish(this, game);
		else game.mansionRemoved().addBack(this);
	}
	
	public void onMansionFinish(Game game) {
			//	if an extra OnMansionFinishListener is attached, use that effect
			//	otherwise, simply add the card to the mansion removed cards pile
			if (mansionFinishListener != null) mansionFinishListener.finish(this, game);
			else game.mansionRemoved().addBack(this);
	}
}
