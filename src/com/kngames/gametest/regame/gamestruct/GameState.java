package com.kngames.gametest.regame.gamestruct;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.WeaponCard;

import android.util.Log;

public class GameState {
	private static final String TAG = GameState.class.getSimpleName();
	
	//	GameState explanations
	//	StartTurn - before main phase, activate abilities of attached cards, activate character abilities, etc.
	//	MainPhase - active player can play ammunition, actions, and items
	//	PlayCardResponse - when a card is played, card is put on the stack, then asks for responses, if none, play the top card in the stack and repeat
	//	PlayCardEffect - when a card is played, apply the effect (asking for player input if necessary), then check the stack for additional cards
	//	ExploreInitial - before playing down weapons for exploring, allows specified abilities to trigger
	//	ExploreWeapons - before exploring, the active player plays down Weapon cards they will be exploring with
	//	ExploreRespond - allows all players to discard actions specified for playing when another player explores
	//	ExploreReveal - reveals the necessary cards from the Mansion and applies any needed effects (When this Infected is Revealed)
	//	ExploreDamage - calculates damage dealt in combat
	//	ExploreEnd - end of the explore cycle, deals with Infected (either attach them to attacking character, put on bottom of Mansion, or shuffle into Mansion)
	//	EndTurn - draws the active player 5 cards, performs any needed effects, then switches the active player and starts their turn
	public static enum State { StartTurn, MainPhase, PlayCardResponse, PlayCardEffect, ExploreInitial, ExploreWeapons, ExploreRespond, ExploreReveal, ExploreDamage, ExploreEnd, EndTurn };
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
		case MainPhase:  break;		//	nothing needs to be done here
		case PlayCardResponse:
			parent.searchForResponses();
			this.setState(State.PlayCardEffect);
			break;
		case PlayCardEffect:  break;
		case ExploreInitial:
			this.setState(State.ExploreWeapons);
			break;
		case ExploreWeapons:  break;	//	nothing needs to be done here
		case ExploreRespond:
			parent.searchForResponses();
			this.setState(State.ExploreReveal);
			break;
		case ExploreReveal:
			//	TODO:  implement revealing infected from mansion
			this.setState(State.ExploreDamage);
			break;
		case ExploreDamage:
			//	TODO:  implement infected damage calculation
			
			//	apply explore effects to exploring players, then calculate damage dealt
			parent.applyExploreEffects();
			int damage = 0;
			REDeck weapons = parent.getActivePlayer().weapons();
			for (int i = 0; i < weapons.size(); i++) {
				int weaponDam = ((WeaponCard)weapons.peek(i)).damageThisRound;
				if (weaponDam > 0) damage += weaponDam;
			}
			Log.d(TAG, String.format("Player %d managed %d damage this explore.", parent.activePlayer(), damage));
			parent.popupToast(String.format("%d damage", damage));
			this.setState(State.ExploreEnd);
			break;
		case ExploreEnd:
			parent.getActivePlayer().flushWeaponsDeck();
			this.setState(State.MainPhase);
			break;
		case EndTurn:
			//	calls the method to end the current players turn, then advances to the next player's turn
			parent.getActivePlayer().resetTurn();
			parent.advanceActivePlayer();
			this.setState(State.StartTurn);
			break;
		}
	}
}
