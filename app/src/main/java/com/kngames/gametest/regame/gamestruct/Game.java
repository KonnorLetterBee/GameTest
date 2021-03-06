package com.kngames.gametest.regame.gamestruct;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.kngames.gametest.redata.*;
import com.kngames.gametest.redata.data.GameData.GameMode;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.Mansion.InfectedCard;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public class Game {
	private static final String TAG = Game.class.getSimpleName();
	public static final boolean DEBUG_MODE = true;
	
	private GameMode gameMode;
	public GameMode gameMode() { return gameMode; }
	
	private Context context;
	public Context getContext() { return context; }
	
	private static Game game;
	
	private Scenario scen;
	public Scenario scen() { return scen; }
	
	private int numPlayers;
	private Player[] players;
	public Player[] players() { return players; }
	public Player getPlayer(int index) { return players[index]; }
	
	private int activePlayer = 0;	//	the player currently taking a turn
	private int visiblePlayer = 0;	//	the player who's hand is showing
	private int tempPlayer = 0;		//	a temporary counter for when other players need to be tested in order, but are not actually taking a turn
	public int activePlayer() { return activePlayer; }
	public int visiblePlayer() { return visiblePlayer; }
	public int tempPlayer() { return tempPlayer; }
	public Player getActivePlayer() { return players[activePlayer]; }
	public Player getVisiblePlayer() { return players[visiblePlayer]; }
	public Player getTempPlayer() { return players[tempPlayer]; }
	
	public int playerBefore(int compare) { 
		int temp = compare - 1;
		if (temp < 0) temp = numPlayers - 1;
		return temp;
	}
	public int playerAfter(int compare) { 
		int temp = compare + 1;
		if (temp >= numPlayers) temp = 0;
		return temp;
	}
	
	private Shop shop;
	public Shop shop() { return shop; }
	
	private REDeck mansion;		//	the mansion deck being used in this game
	public REDeck mansion() { return mansion; }
	private REDeck mansionRemoved;	//	the pile of cards removed from the mansion
	public REDeck mansionRemoved() { return mansionRemoved; }
	private ArrayList<String> mansionItems;	//	a list of item names to be displayed during an explore, since they usually don't stay around after revealing
	public ArrayList<String> mansionItems() { return mansionItems; }
	
	private GameState state;
	public GameState state() { return state; }
	
	public boolean gameConcluded;
	
	private ArrayList<Player> attackingPlayers;		//	list of players attacking this explore
	public ArrayList<Player> attackingPlayers() { return attackingPlayers; }
	private Player defendingPlayer;		//	list of players being attacked this explore (Versus mode only)
	public Player defendingPlayer() { return defendingPlayer; }
	private ArrayList<ExploreEffect> exploreEffects;	//	list of effects to apply during this explore (to both attackers and defenders)
	public ArrayList<ExploreEffect> exploreEffects() { return exploreEffects; }
	private REDeck defendingInfected;		//	list of infected being attacked this explore
	public REDeck defendingInfected() { return defendingInfected; }
	
	private int gameTurn;
	public int gameTurn() { return gameTurn; }
	
	private Game(Context context, GameMode mode, CharacterCard[] chars, Scenario scen, REDeck mansion) {
		this.context = context;
		
		this.gameMode = mode;
		
		//	set the scenario and resource piles
		shop = new Shop(this, scen, chars.length);
		shop.shuffleAllPiles();
		
		//	initialize all players with their proper characters
		numPlayers = chars.length;
		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(i, this, chars[i], null);
		}
		
		//	set the mansion
		this.mansion = mansion;
		this.mansionRemoved = new REDeck();
		
		//	set the game state to StartTurn
		state = new GameState(this, State.StartTurn);
		
		//	instantiate the list of explore effects for later use
		exploreEffects = new ArrayList<ExploreEffect>();
		
		//	set game turn to 1
		gameTurn = 1;
		gameConcluded = false;
	}
	
	//	instantiates a new Game if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static Game startGame(Context context, CharacterCard[] chars, Scenario scen, REDeck mansion) {
		if (game == null)  game = new Game(context, GameMode.Story, chars, scen, mansion);
		else  Log.e(TAG, "Game already instantiated!");
		return game;
	}
	
	//	returns the currently instantiated Game object, even if it is null
	public static Game getGame() {
		return game;
	}
	
	//	returns true if there is a Game object, false otherwise
	public static boolean exists() {
		return game != null;
	}
	
	//	sets the context of this Game object, in case the original context is missing
	public void setContext(Context context) {
		this.context = context;
	}
	
	//	sets the stored Game to null
	public static void destroy() {
		game = null;
	}
	
	//	checks whether it's the current player's turn
	public boolean isActivePlayer(Player play) {
		return play.playerId() == activePlayer;
	}
	
	//	checks whether the specified player is set as temp
	public boolean isTempPlayer(Player play) {
		return play.playerId() == tempPlayer;
	}
	
	//	advances the active player to the next player
	public void advanceActivePlayer() {
		activePlayer++;
		if (activePlayer == numPlayers) {
			activePlayer = 0;
			gameTurn++;
		}
		tempPlayer = activePlayer;
		
		//	recursively call if the newly selected player has been eliminated
		if (numPlayersRemaining() > 0 && getActivePlayer().isEliminated) advanceActivePlayer();
	}
	
	//	advances the temp player to the next player
	//	returns true if the counter is back to the active player (where it started)
	public boolean advanceTempPlayer() {
		tempPlayer++;
		if (tempPlayer == numPlayers) tempPlayer = 0;
		
		//	recursively call if the newly selected player has been eliminated
		if (numPlayersRemaining() > 0 && getTempPlayer().isEliminated) return advanceTempPlayer();
		else return tempPlayer == activePlayer;
	}
	
	//	counts the number of players playing that haven't yet been eliminated
	public int numPlayersRemaining() {
		int remain = 0;
		for (Player p : players) if (!p.isEliminated) remain++;
		return remain;
	}
	
	//	searches all player's play areas for responses, and allows them to play any of them
	public void searchForResponses () {
		for (Player p : players)
			p.searchForResponses();
	}
	
	
	///
	///		Explore Methods
	///
	
	//	preps all explore-specific structures for explore
	public void startExplore() {
		attackingPlayers = new ArrayList<Player>();
		defendingPlayer = null;
		defendingInfected = new REDeck();
		mansionItems = new ArrayList<String>();
	}
	
	//	applies all explore effects to the currently exploring characters, then removes them from the effects list
	public void applyExploreEffects() {
		while (exploreEffects.size() > 0) {
			exploreEffects.get(0).applyEffect();
			exploreEffects.remove(0);
		}
	}
	
	//	takes the top card of the mansion and adds it to the list of revealed mansion cards for this explore
	//	returns the flipped over card
	public RECard flipMansion() {
		if (mansion.size() > 0) {
			defendingInfected.addBack(mansion.popTop());
			return (RECard) defendingInfected.peekLast();
		}
		return null;
	}
	
	//	takes the bottom card of the mansion and adds it to the list of revealed mansion cards for this explore
	public RECard flipBottomMansion() {
		if (mansion.size() > 0) {
			defendingInfected.addBack(mansion.popTop());
			return (RECard) defendingInfected.peekLast();
		}
		return null;
	}
	
	//	ends the explore by moving undefeated infected to the bottom of the mansion, and emptying the exploring character's weapon pile
	public void endExplore() {
		if (attackingPlayers != null) {
			for (Player p : attackingPlayers) p.flushWeaponsDeck();
		}
		defendingPlayer = null;
		if (defendingInfected != null) {
			while (defendingInfected.size() > 0) mansion.addBottom(defendingInfected.popFirst());
			defendingInfected = null;
		}
		mansionItems = null;
	}
	
	
	///
	///		Misc. Methods
	///
	
	//	updates the gameStateMessage field depending on which phase the GameState is in
	//	TODO: update with more meaningful names
	public String gameStateMessage;
	public void update() {
		switch(state.currentState()) {
		case StartTurn: gameStateMessage = "<beginning of turn>";  break;
		case MainPhase: 
			if (getActivePlayer().actions > 0) gameStateMessage = "Play ammunition, actions, and items.";
			else gameStateMessage = "Play ammunition and items.";
			break;
		case ExploreWeapons: gameStateMessage = "Play weapons to explore with.";  break;
		case ExploreAgain: gameStateMessage = String.format("You may explore up to %d more time(s).", getActivePlayer().explores);  break;
		case EndTurn: gameStateMessage = "<end of turn>";  break;
		case PlayerInput: gameStateMessage = state.playerState().gameStateMessage;  break;
		default: gameStateMessage = "";  break;
		}
	}
	
	public void popupToast(String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void popupMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
			   .setMessage(message)
			   .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   dialog.dismiss();
				   }
			   });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	///
	///		Game Conclusion Methods
	///
	
	
	//	scans the mansion for boss characters
	//	TODO: remove the hardcoded boss health number and use a value in the InfectedCard class to determine boss value
	public boolean scanMansionForBoss() {
		for (int i = 0; i < mansion.size(); i++) {
			if (mansion.peek(i) instanceof InfectedCard && ((InfectedCard)mansion.peek(i)).getHealth() >= 90) return true;
		}
		return false;
	}
	
	//	scans the game for possible conclusion states, and displays the appropriate message
	public void scanForConclusion() {
		//	check to see if this explore has led to a game finish (no bosses exist)
		if (scanMansionForBoss() == false) {
			this.gameConcluded = true;
			popupGameResults();
		}
				
		//	check to see if there are any remaining players to play
		if (numPlayersRemaining() == 0) {
			this.gameConcluded = true;
			popupGameLossMessage();
		}
	}
	
	//	pops up a dialog box that shows the game results and a winner, then ends the game
	public void popupGameResults() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		int winningIndex = 0;
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			if (p.countDecorations() > players[winningIndex].countDecorations()) winningIndex = i;
		}
		
		StringBuilder message = new StringBuilder();
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			message.append(String.format("Player %d finished with %d decorations.\n", i+1, p.countDecorations()));
		}
		message.append(String.format("\nPlayer %d had the most decorations\nand wins the game.", winningIndex+1));
		
		builder.setTitle("Game Over")
			   .setMessage(message.toString())
			   .setNeutralButton("Finish Game", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   ((Activity)context).finish();
					   dialog.dismiss();
				   }
			   }).setCancelable(false);
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	//	pops up a dialog box that shows that every player has been eliminated, then ends the game
	public void popupGameLossMessage() {
		StringBuilder message = new StringBuilder();
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			message.append(String.format("Player %d finished with %d decorations.\n", i+1, p.countDecorations()));
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Game Over")
			   .setMessage("All players have been permanently eliminated.\n\n" + message.toString())
			   .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   ((Activity)context).finish();
					   dialog.dismiss();
				   }
			   }).setCancelable(false);
		AlertDialog alert = builder.create();
		alert.show();
	}
}
