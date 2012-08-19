package com.kngames.gametest.regame.gamestruct;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.WeaponCard;

public class ExploreEffect {
	
	private Game game;
	public Game game() { return game; }
	
	private Player actingPlayer;
	public Player actingPlayer() { return actingPlayer; }
	
	private OnApplyEffectListener effect;
	
	public ExploreEffect(Game game, Player actingPlayer, OnApplyEffectListener effect) {
		this.game = game;
		this.actingPlayer = actingPlayer;
		this.effect = effect;
	}
	
	public void applyEffect() {
		if (effect != null) effect.applyEffect(game, actingPlayer);
	}
	
	//	interface to be implemented by combat effects to determine what effect they actually have
	public interface OnApplyEffectListener {
		public void applyEffect(Game game, Player actingPlayer);
	}
	
	//	an effect that buffs (or nerfs for negative values) all weapons used in this explore
	public static class BuffAllWeaponsEffect implements OnApplyEffectListener {
		int amountChange;
		public BuffAllWeaponsEffect (int amountChange) {
			this.amountChange = amountChange;
		}
		public void applyEffect(Game game, Player actingPlayer) {
			REDeck weapons = actingPlayer.weapons();
			for(int i = 0; i < weapons.size(); i++) {
				((WeaponCard)weapons.peek(i)).damageThisRound += amountChange;
			}
		}
	}
}
