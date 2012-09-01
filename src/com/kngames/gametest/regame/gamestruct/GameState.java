package com.kngames.gametest.regame.gamestruct;

import com.kngames.gametest.cards.graphics.ButtonZone;
import com.kngames.gametest.cards.graphics.ZoneManager;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.CardTypes.WeaponCard;
import com.kngames.gametest.redata.CardTypes.Mansion.InfectedCard;
import com.kngames.gametest.regame.graphics.REZoneManager;

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
		ExploreInitial, ExploreWeapons, ExploreRespond, ExploreReveal, ExploreAgain, ExploreDamage, ExploreEnd, 
		PlayerInput, EndTurn };
	private State currentState;
	public State currentState() { return currentState; }
	
	private Game game;
	private ZoneManager zones;
	
	public GameState(Game parent, State initialState) {
		this.game = parent;
		setState(initialState, true);
		zones = ZoneManager.getZoneManager();
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
			if (game.getActivePlayer().isDead) {
				game.getActivePlayer().resetTurn();
				this.setState(State.EndTurn, true);
			}
			this.setState(State.MainPhase, true);
			break;
		case MainPhase:	break;		//	nothing needs to be done here
		case PlayCardResponse:
			game.searchForResponses();
			this.setState(State.PlayCardEffect, true);
			break;
		case PlayCardEffect:  break;
		case ExploreInitial:
			game.startExplore();
			game.getActivePlayer().mustExplore = false;
			game.attackingPlayers().add(game.getActivePlayer());
			this.setState(State.ExploreWeapons, true);
			break;
		case ExploreWeapons:  break;	//	nothing needs to be done here
		case ExploreRespond:
			game.searchForResponses();
			this.setState(State.ExploreReveal, true);
			break;
		case ExploreReveal:
			game.flipMansion();
			this.setState(State.ExploreAgain, true);
			break;
		case ExploreAgain:
			if (game.getActivePlayer().explores <= 0) {
				this.setState(State.ExploreDamage, true);
				zones.getZone("action_button").deactivate();
			}
			else {
				zones.getZone("action_button").activate();
			}
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
			
			//	calculate health and damage of all infected, and print out proper information
			int infecHealth = 0;
			int infecDamage = 0;
			REDeck infected = game.defendingInfected();
			StringBuilder infecInfo = new StringBuilder();
			for (int i = 0; i < infected.size(); i++) {
				if (infected.peek(i) instanceof InfectedCard) {
					InfectedCard temp = (InfectedCard)infected.peek(i);
					int infecDam = temp.getDamage();
					if (infecDam > 0) infecDamage += infecDam;
					if (temp.getHealth() > 0) infecHealth += temp.getHealth();
					infecInfo.append(String.format("%s (%d health, %d damage, %d decs)\n", temp.getName(), temp.getHealth(), temp.getDamage(), temp.getDecorations()));
				} else infecInfo.append(String.format("%s\n", ((RECard)infected.peek(i)).getName()));
			}
			
			//	compare damage and health values to determine the outcome
			if (damage >= infecHealth) {	//	player wins
				int decs = 0;
				while(infected.size() > 0) {
					if (infected.peek(0) instanceof InfectedCard) {
						decs += ((InfectedCard)infected.peek(0)).getDecorations();
					}
					game.getActivePlayer().attachedCards().addBack(infected.pop(0));
				}
				game.popupMessage("Explore Results",String.format("%s\nPlayer (%d) -> %d needed\nPlayer receives %d decorations.", 
						infecInfo.toString(), damage, infecHealth, decs));
			} else {	//	player loses
				game.getActivePlayer().health -= infecDamage;
				game.popupMessage("Explore Results",String.format("%s\nPlayer (%d) -> %d needed\nPlayer lost %d health.", 
						infecInfo.toString(), damage, infecHealth, infecDamage));
			}
			
			this.setState(State.ExploreEnd, true);
			break;
		case ExploreEnd:
			game.endExplore();
			
			//	check to see if this explore has led to a game finish (no bosses exist)
			if (game.scanMansionForBoss() == false) {
				game.popupGameResults();
			}
			
			//	check to see if the active player has been killed
			if (game.getActivePlayer().health <= 0) {
				game.getActivePlayer().killPlayer(true);
				this.setState(State.EndTurn, true);
			}
			
			//	check to see if there are any remaining players to play
			if (game.numPlayersRemaining() == 0) {
				game.popupGameLossMessage();
			}
			
			else this.setState(State.MainPhase, true);
			break;
		case EndTurn:
			//	calls the method to end the current players turn, then advances to the next player's turn
			//	if player's alive, reset turn like normal
			if (!game.getActivePlayer().isDead) game.getActivePlayer().resetTurn();
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
		protected ButtonZone actionButton;
		
		public PlayerInputState (Game game, Player actingPlayer) {
			this.game = game;
			this.actingPlayer = actingPlayer;
			this.actionButton = (ButtonZone) REZoneManager.getREZoneManager().getZone("action_button");
		}
		public abstract void onPlayerInputStart();
		public abstract boolean isSelectable(REDeck source, int index);
		public abstract void onCardSelected(REDeck source, int index);
		public abstract void onExtraButtonPressed();
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
		public void onExtraButtonPressed() { }
		public void onPlayerInputFinish() { Log.d(TAG, "Exiting test..."); }
	}
}
