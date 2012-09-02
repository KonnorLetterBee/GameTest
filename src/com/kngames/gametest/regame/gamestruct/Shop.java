package com.kngames.gametest.regame.gamestruct;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.data.GameData;

public class Shop {
	private static final String TAG = Shop.class.getSimpleName();
	
	private Game game;
	//private Scenario scen;
	private ArrayList<REDeck> resourcePiles;
	private String[] available;
	
	public Shop(Game g, Scenario scen, int players) {
		this.game = g;
		//this.scen = scen;
		if (scen != null) setupResourcePiles(scen, players);
	}
	
	
	
	///
	///		Shop Operations
	///
	
	//	set the scenario and resource piles
	private void setupResourcePiles(Scenario scen, int players) {
		ArrayList<RECard[]> cards = scen.getCards();
		resourcePiles = new ArrayList<REDeck>();
		//	set up basics piles, if scenario allows basics
		resourcePiles.add(createDeck(GameData.findCard("Ammo x10", CardType.Ammunition, -1)));
		resourcePiles.add(createDeck(GameData.findCard("Ammo x20", CardType.Ammunition, -1)));
		resourcePiles.add(createDeck(GameData.findCard("Ammo x30", CardType.Ammunition, -1)));
		resourcePiles.add(createDeck(new RECard[]{
				GameData.findCard("Combat Knife", CardType.Weapon, -1),
				GameData.findCard("Survival Knife", CardType.Weapon, -1) }, false));
		resourcePiles.add(createDeck(new RECard[]{
				GameData.findCard("Handgun", CardType.Weapon, -1),
				GameData.findCard("Burst-Fire Handgun", CardType.Weapon, -1) }, false));
		resourcePiles.add(createDeck(GameData.findCard("Green Herb", CardType.Item, -1)));
		
		//	set up all scenario piles
		for (int i = 0; i < cards.size(); i++) {
			resourcePiles.add(createDeck(cards.get(i), false));
		}
	}
	
	//	creates a resource pile filled with a single type of card of the amount listed in that card
	private REDeck createDeck(RECard card) {
		REDeck out = new REDeck();
		for (int k = 0; k < card.getDeckQuantity(); k++) {
			if (card.getCardType() == CardType.Weapon) out.addTop(new WeaponCard((WeaponCard)card));
			else out.addTop(card);
		}
		return out;
	}
	
	//	creates a resource pile filled with a single type of card of the amount listed in that card
	private REDeck createDeck(RECard[] cards, boolean shuffle) {
		REDeck out = new REDeck();
		for (int j = 0; j < cards.length; j++) {
			for (int k = 0; k < cards[j].getDeckQuantity(); k++) {
				if (cards[j].getCardType() == CardType.Weapon) out.addTop(new WeaponCard((WeaponCard)cards[j]));
				else out.addTop(cards[j]);
			}
		}
		if (shuffle && cards.length > 1) out.shuffle(1);
		return out;
	}
	
	//	shuffles all piles in this Shop
	public void shuffleAllPiles() {
		for (REDeck d : resourcePiles) {
			d.shuffle(1);
		}
	}
	
	
	
	///
	///		Player Actions
	///
	
	//	searches all piles for a specific card, and removes it from the first pile it's found in
	//	then shuffles the deck
	public RECard gainCardSearch(Player player, String tag) {
		for (int i = resourcePiles.size() - 1; i >= 0; i--) {
			int index = resourcePiles.get(i).indexOf(tag);
			if (index != -1) {
				return (RECard) resourcePiles.get(i).pop(index);
			}
		}
		return null;
	}
	
	//	takes the top card of the specified pile and adds it to the top of the specified player's discard pile
	public void buyCard(Player player, int stackIndex) {
		RECard temp = (RECard)resourcePiles.get(stackIndex).peekTop();
		int price = getPriceOfCard(temp);
		if (Game.DEBUG_MODE) player.discard().addTop(resourcePiles.get(stackIndex).popTop());
		else if (player.buys >= 1 && player.gold >= price) {
			player.buys--;
			player.gold -= price;
			player.discard().addTop(resourcePiles.get(stackIndex).popTop());
		}
	}
	
	//	returns a card to the resource area
	public void returnCard(RECard card) {
		for (int i = 0; i < resourcePiles.size(); i++) {
			if (resourcePiles.get(i).indexContains(card.getTag())) {
				resourcePiles.get(i).addBottom(card);
			}
		}
	}
	
	
	///
	///		Dialog Methods
	///
	
	//	pops up the dialog used to buy cards
	public void popupBuyDialog() {
		if (resourcePiles != null) {
			loadAvailable();
			buyDialog.show();
		}
	}
	
	//	loads available cards to buy from the resource piles
	private void loadAvailable() {
		available = new String[resourcePiles.size()];
		for (int i = 0; i < resourcePiles.size(); i++) {
			RECard temp = (RECard)resourcePiles.get(i).peekTop();
			if (temp == null) available[i] = "(empty)";
			else {
				String name = temp.getName();
				available[i] = String.format("(%d) %s -- %dG", resourcePiles.get(i).size(), name, getPriceOfCard(temp));
			}
		}
		
		setupBuyCardDialog();
	}
	
	public static int getPriceOfCard(RECard card) {
		int price = 99999;
		
		switch (card.getCardType()) {
		case Ammunition: price = ((AmmunitionCard)card).getPrice();  break;
		case Weapon: price = ((WeaponCard)card).getPrice();  break;
		case Action: price = ((ActionCard)card).getPrice();  break;
		case Item:   price = ((ItemCard)card).getPrice();  break;
		default: Log.d(TAG, "Error converting selected card to any type");
		}
		
		return price;
	}
	
	//	sets up a dialog that allows the user to buy a card from the Resource Area and put that card
	//	on the top of their Discard Pile (does NOT show the dialog)
	AlertDialog buyDialog;
	AlertDialog buyLocationDialog;
	private void setupBuyCardDialog() {
		//	build the alert box
		AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
		builder.setTitle("Pick card to buy:");
		if (!Game.DEBUG_MODE) builder.setItems(available, new buyToDiscard());
		else builder.setItems(available, new popupChoice());
		buyDialog = builder.create();
	}
	
	//	pops up a dialog that prompts the user to choose between sending the bought card to Discard or Hand
	int selectedIndex;
	private void popupBuyHandDiscardChoice(int index) {
		selectedIndex = index;
		AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
		builder.setTitle("Put into Discard Pile or Hand?")
			   .setPositiveButton("Discard Pile", new moveToDiscard())
			   .setNegativeButton("Hand", new moveToHand())
			   .setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						buyDialog.show();
					}
			   });
		buyLocationDialog = builder.create();
		buyLocationDialog.show();
	}
	
	private class popupChoice implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int item) {
			if (resourcePiles.get(item).size() != 0)
				popupBuyHandDiscardChoice(item);
		}
	}
	private class moveToHand implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int id) {
			game.getActivePlayer().hand().addTop(resourcePiles.get(selectedIndex).popTop());
			dialog.dismiss();
			buyDialog.dismiss();
		}
	}
	private class moveToDiscard implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int id) {
			game.getActivePlayer().discard().addTop(resourcePiles.get(selectedIndex).popTop());
			dialog.dismiss();
			buyDialog.dismiss();
		}
	}
	private class buyToDiscard implements DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int item) {
			buyCard(game.getActivePlayer(), item);
			dialog.dismiss();
		}
	}
}
