package com.kngames.gametest.regame.gamestruct;

import android.util.Log;

public class GameState {
	private static final String TAG = GameState.class.getSimpleName();
	
	//	GameState explanations
	//	StartTurn - before main phase, activate abilities of attached cards, activate character abilities, etc.
	//	MainPhase - active player can play ammunition, actions, and items
	//	PlayCard - when a card is played, card is put on the stack, then asks for responses, if none, play the top card in the stack and repeat
	//	ExploreInitial - before playing down weapons for exploring, allows specified abilities to trigger
	//	ExploreWeapons - before exploring, the active player plays down Weapon cards they will be exploring with
	//	ExploreRespond - allows all players to discard actions specified for playing when another player explores
	//	ExploreReveal - reveals the necessary cards from the Mansion and applies any needed effects (When this Infected is Revealed)
	//	ExploreDamage - calculates damage dealt in combat
	//	ExploreEnd - end of the explore cycle, deals with Infected (either attach them to attacking character, put on bottom of Mansion, or shuffle into Mansion)
	//	EndTurn - draws the active player 5 cards, performs any needed effects, then switches the active player and starts their turn
	public static enum State { StartTurn, MainPhase, PlayCard, ExploreInitial, ExploreWeapons, ExploreRespond, ExploreReveal, ExploreDamage, ExploreEnd, EndTurn };
	private State currentState;
	public State currentState() { return currentState; }
	
	private Game parent;
	
	public GameState(Game parent, State initialState) {
		this.parent = parent;
		setState(initialState);
	}
	
	public void setState(State newState) {
		Log.d(TAG, String.format("Game state changed: player %d, %s", parent.activePlayer(), newState));
		currentState = newState;
		onStateChange();
	}
	
	private void onStateChange() {
		switch(currentState) {
		case StartTurn:
			//	TODO:  add search for beginning turn triggers
			this.setState(State.MainPhase);
			break;
		case MainPhase:  break;
		case PlayCard:  break;
		case ExploreInitial:  break;
		case ExploreWeapons:  break;
		case ExploreRespond:  break;
		case ExploreReveal:  break;
		case ExploreDamage:  break;
		case ExploreEnd:  break;
		case EndTurn:
			//	calls the method to end the current players turn, then advances to the next player's turn
			parent.getActivePlayer().resetTurn();
			parent.advanceActivePlayer();
			this.setState(State.StartTurn);
			break;
		}
	}
}
