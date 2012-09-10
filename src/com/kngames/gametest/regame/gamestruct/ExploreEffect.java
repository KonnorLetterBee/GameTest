package com.kngames.gametest.regame.gamestruct;

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
}
