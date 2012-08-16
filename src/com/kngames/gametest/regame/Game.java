package com.kngames.gametest.regame;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.carddata.CardData;

public class Game {
	private static final String TAG = Game.class.getSimpleName();
	
	private Context context;
	public Context getContext() { return context; }
	
	private static Game game;
	
	private Scenario scen;
	public Scenario scen() { return scen; }
	
	private Player[] players;
	public Player[] players() { return players; }
	public Player getActivePlayer() { return players[activePlayer]; }
	
	private int activePlayer = 0;
	private int viewingPlayer = 0;
	public int activePlayer() { return activePlayer; }
	public int viewingPlayer() { return viewingPlayer; }
	
	private ArrayList<REDeck> resourcePiles;
	
	private Game(Context context, CharacterCard[] chars, Scenario scen) {
		this.context = context;
		
		//	initialize all players with their proper characters
		players = new Player[chars.length];
		for (int i = 0; i < chars.length; i++) {
			players[i] = new Player(this, chars[i], null);
		}
		
		//	set the scenario and resource piles
		if (scen != null) setupResourcePiles(scen);
		else loadFullStore();
	}
	
	//	instantiates a new Game if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static Game startGame(Context context, CharacterCard[] chars, Scenario scen) {
		if (game == null) return new Game(context, chars, scen);
		else {
			Log.e(TAG, "Game already instantiated!");
			return game;
		}
	}
	
	//	set the scenario and resource piles
	private void setupResourcePiles(Scenario scen) {
		this.scen = scen;
		ArrayList<RECard[]> cards = scen.getCards();
		resourcePiles = new ArrayList<REDeck>();
		//	set up all piles
		for (int i = 0; i < cards.size(); i++) {
			REDeck tempDeck = new REDeck();
			RECard[] temp = cards.get(i);
			//	set up individual piles with multiple kinds of cards
			for (int j = 0; j < temp.length; j++) {
				for (int k = 0; k < temp[j].getDeckQuantity(); k++) {
					tempDeck.addTop(temp[j]);
				}
			}
			//	shuffle the deck if there is more than one kind of card in a stack
			if (temp.length > 1) tempDeck.shuffle(1);
			resourcePiles.add(tempDeck);
		}
		loadScenarioStore();
	}
	
	//	loads every available card to buy into the store
	private void loadFullStore() {
		store = new ArrayList<RECard>();
		store.add(CardData.Ammunition[0]);
		store.add(CardData.Ammunition[1]);
		store.add(CardData.Ammunition[2]);
		for (int i = 0; i < CardData.Weapons.length; i++) {
			store.add((RECard)CardData.Weapons[i]);
		}
		for (int i = 0; i < CardData.Actions.length; i++) {
			store.add((RECard)CardData.Actions[i]);
		}
		for (int i = 0; i < CardData.Items.length; i++) {
			if (i != 1 && i != 4 && i != 5 && i != 6 && i != 7)
			store.add((RECard)CardData.Items[i]);
		}
		
		setupBuyCardDialog();
	}
	
	//	loads a Store based on the selected active Scenario
	private ArrayList<RECard> store = new ArrayList<RECard>();
	private void loadScenarioStore() {
		store = new ArrayList<RECard>();
		
		if (scen.useBasics()) {
			store.add(CardData.Ammunition[0]);
			store.add(CardData.Ammunition[1]);
			store.add(CardData.Ammunition[2]);
			store.add(CardData.Weapons[3]);
			store.add(CardData.Weapons[4]);
			store.add(CardData.Weapons[5]);
			store.add(CardData.Weapons[6]);
			store.add(CardData.Items[0]);
		}
		
		ArrayList<RECard[]> stackList = scen.getCards();
		for (int i = 0; i < stackList.size(); i++) {
			RECard[] currStack = stackList.get(i);
			for (int j = 0; j < currStack.length; j++) {
				store.add(currStack[j]);
			}
		}
		
		setupBuyCardDialog();
	}
	
	//	sets up a dialog that allows the user to buy a card from the Resource Area and put that card
	//	on the top of their Discard Pile (does NOT show the dialog)
	AlertDialog buyDialog;
	AlertDialog buyLocationDialog;
	private void setupBuyCardDialog() {
		CharSequence[] storeStrings = new CharSequence[store.size()];
		for (int i = 0; i < store.size(); i++) {
			storeStrings[i] = store.get(i).getName();
		}
		
		//	build the alert box
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Pick card to buy:");
		builder.setItems(storeStrings, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				popupBuyHandDiscardChoice(item);
			}
		});
		buyDialog = builder.create();
	}
	
	public void popupBuyDialog() {
		buyDialog.show();
	}
	
	//	pops up a dialog that prompts the user to choose between sending the bought card to Discard or Hand
	int selectedIndex;
	private void popupBuyHandDiscardChoice(int index) {
		selectedIndex = index;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Put into Discard Pile or Hand?")
			   .setPositiveButton("Discard Pile", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   getActivePlayer().discard().addTop(store.get(selectedIndex));
					   dialog.dismiss();
					   buyDialog.dismiss();
				   }
			   })
			   .setNegativeButton("Hand", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   getActivePlayer().hand().addTop(store.get(selectedIndex));
					   dialog.dismiss();
					   buyDialog.dismiss();
				   }
			   })
			   .setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						buyDialog.show();
					}
			   });
		buyLocationDialog = builder.create();
		buyLocationDialog.show();
	}
}
