package com.kngames.gametest.regame.gamestruct;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
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
	//	PlayerInput - a special game state that is used for getting input from a player outside the main phase
	//		
	//	EndTurn - draws the active player 5 cards, performs any needed effects, then switches the active player and starts their turn
	public static enum State { 
		StartTurn, MainPhase, PlayCardResponse, PlayCardEffect, 
		ExploreInitial, ExploreWeapons, ExploreRespond, ExploreReveal, ExploreDamage, ExploreEnd, 
		PlayerInput, EndTurn };
	private State currentState;
	public State currentState() { return currentState; }
	
	private Game game;
	//private ZoneManager zones;
	
	public GameState(Game parent, State initialState) {
		this.game = parent;
		setState(initialState, true);
		//zones = ZoneManager.getZoneManager();
	}
	
	public void setState(State newState, boolean useChangeEffects) {
		Log.d(TAG, String.format("Game state changed: player %d, %s", game.activePlayer(), newState));
		currentState = newState;
		onStateChange();
	}
	
	private void onStateChange() {
		switch(currentState) {
		case StartTurn:
			//	TODO:  add search for beginning turn triggers
			this.setState(State.MainPhase, true);
			break;
		case MainPhase:	break;		//	nothing needs to be done here
		case PlayCardResponse:
			game.searchForResponses();
			this.setState(State.PlayCardEffect, true);
			break;
		case PlayCardEffect:  break;
		case ExploreInitial:
			this.setState(State.ExploreWeapons, true);
			break;
		case ExploreWeapons:  break;	//	nothing needs to be done here
		case ExploreRespond:
			game.searchForResponses();
			this.setState(State.ExploreReveal, true);
			break;
		case ExploreReveal:
			//	TODO:  implement revealing infected from mansion
			this.setState(State.ExploreDamage, true);
			break;
		case ExploreDamage:
			//	TODO:  implement infected damage calculation
			
			//	apply explore effects to exploring players, then calculate damage dealt
			game.applyExploreEffects();
			int damage = 0;
			REDeck weapons = game.getActivePlayer().weapons();
			for (int i = 0; i < weapons.size(); i++) {
				int weaponDam = ((WeaponCard)weapons.peek(i)).damageThisRound;
				if (weaponDam > 0) damage += weaponDam;
			}
			Log.d(TAG, String.format("Player %d managed %d damage this explore.", game.activePlayer(), damage));
			game.popupToast(String.format("%d damage", damage));
			this.setState(State.ExploreEnd, true);
			break;
		case ExploreEnd:
			game.getActivePlayer().flushWeaponsDeck();
			this.setState(State.MainPhase, true);
			break;
		case EndTurn:
			//	calls the method to end the current players turn, then advances to the next player's turn
			game.getActivePlayer().resetTurn();
			game.advanceActivePlayer();
			this.setState(State.StartTurn, true);
			break;
		}
	}
	
	
	private State previousState;
	private PlayerInputState playerState;
	public PlayerInputState playerState() { return playerState; }
	
	//	enters into a new PlayerInput state
	public void startPlayerInput(PlayerInputState inputState) {
		//	if the input is legitimate, put the game state to PlayerInput, then start the custom game state
		if (inputState != null) {
			this.previousState = currentState;
			this.setState(State.PlayerInput, false);
			this.playerState = inputState;
			this.playerState.onPlayerInputStart();
		} else Log.e(TAG, "Error: PlayerInput state is null, no state to enter");
	}
	
	//	exits the currently active PlayerInput state
	public void endPlayerInput() {
		if (this.playerState == null) {
			Log.e(TAG, "Error: PlayerInput state is null, no state to exit from");
			return;
		}
		this.playerState.onPlayerInputFinish();
		this.setState(previousState, false);
		this.playerState = null;
	}
	
	//	the abstract class for PlayerInputState
	//	contains methods meant to be called by a specific
	public static abstract class PlayerInputState {
		protected String gameStateMessage;
		protected Game game;
		protected Player actingPlayer;
		
		public PlayerInputState (Game game, Player actingPlayer) {
			this.game = game;
			this.actingPlayer = actingPlayer;
		}
		public abstract void onPlayerInputStart();
		//public abstract boolean isSelectable(RECard card);
		public abstract boolean isSelectable(REDeck source, int index);
		public abstract void onCardSelected(REDeck source, int index);
		public abstract void onPlayerInputFinish();
		public String getStateMessage() { return gameStateMessage; }
	}
	
	//	a PlayerInputState designed to test the functions of the PlayerInputState structure
	@Deprecated
	public static class TestPlayerInput extends PlayerInputState {
		public TestPlayerInput (Game game, Player actingPlayer) { super(game, actingPlayer); }
		public void onPlayerInputStart() {
			Log.d(TAG, "Entering test...");
			if (actingPlayer.hand().size() <= 0) game.state().endPlayerInput();
		}
		public boolean isSelectable(REDeck source, int index) { return ((RECard) source.peek(index)).getCardType() == CardType.Ammunition; }
		public void onCardSelected(REDeck source, int index) {
			RECard card = (RECard) source.peek(index);
			if (isSelectable(source, index)) {
				Log.d(TAG, String.format("Ammunition selected: %s, index %d", card.getName(), index));
				game.state().endPlayerInput();
			} else {
				Log.d(TAG, String.format("Wrong card selected: %s", card.getName()));
			}
		}
		public void onPlayerInputFinish() { Log.d(TAG, "Exiting test..."); }
	}
}
