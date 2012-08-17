package com.kngames.gametest.redata.carddata;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Pair;

import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.OnPlayFinishListener;
import com.kngames.gametest.redata.CardTypes.RECard.OnPlayListener;
import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public class CardEffects {
	//	not implemented
	public static class DeadlyAimEffect implements OnPlayListener {
		public void playAction(RECard card, Game game, Player actingPlayer) { }
	}

	public static class ShatteredMemoriesEffect implements OnPlayListener {
		private Game game;
		private Player actingPlayer;
		private ArrayList<Pair<Integer,RECard>> discard;
		private String[] names;
		private int winNum = 0;
		public void playAction(RECard card, Game game, Player actingPlayer) {
			this.actingPlayer = actingPlayer;
			this.game = game;
			
			displayList();
		}
		private void displayList() {
			//	generate list of discarded cards
			discard = actingPlayer.discard().getAllCardPairs();
					
			//	handle empty lists
			if (discard.size() == 0) {
				return;
			}
					
			names = new String[discard.size()];
			for (int i = 0; i < discard.size(); i++)
				names[i] = discard.get(i).second.getName();
			
			//	build and popup list of discarded weapons
			AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
			builder.setTitle("Choose a card to trash");
			builder.setCancelable(false);
			builder.setItems(names, new itemSelect());
			builder.setNeutralButton("None", new cancelButton());
			AlertDialog effectDialog = builder.create();
			effectDialog.show();
		}
		private class itemSelect implements OnClickListener {
			public void onClick(DialogInterface dialog, int item) {
				onItemSelected(item);
				if (winNum++ == 0) displayList();
			}
		}
		private class cancelButton implements OnClickListener {
			public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
		}
		private void onItemSelected(int item) {
			RECard temp = (RECard)actingPlayer.discard().pop(discard.get(item).first);
			game.shop().returnCard(temp);
		}
	}

	public static class ReloadEffect implements OnPlayListener {
		private Player actingPlayer;
		private ArrayList<Pair<Integer,RECard>> disWeapons;
		private String[] names;
		public void playAction(RECard card, Game game, Player actingPlayer) {
			this.actingPlayer = actingPlayer;
			
			//	generate list of discarded weapons
			disWeapons = actingPlayer.discard().queryType(RECard.CardType.Weapon);
			
			//	handle empty lists, and lists with size 1
			if (disWeapons.size() == 0) return;
			else if (disWeapons.size() == 1) {
				onItemSelected(0);
				return;
			}
			
			names = new String[disWeapons.size()];
			for (int i = 0; i < disWeapons.size(); i++)
				names[i] = disWeapons.get(i).second.getName();
			
			//	build and popup list of discarded weapons
			AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
			builder.setTitle("Return a weapon to hand");
			builder.setCancelable(false);
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					onItemSelected(item);
				}
			});
			AlertDialog effectDialog = builder.create();
			effectDialog.show();
		}
		public void finish(RECard card, Game game, Player actingPlayer) { actingPlayer.inPlay().addBack(card);	}
		private void onItemSelected(int item) {
			RECard temp = (RECard)actingPlayer.discard().pop(disWeapons.get(item).first);
			actingPlayer.hand().addBottom(temp);
		}
	}

	public static class UmbrellaCorporationEffect implements OnPlayListener {
		private Player actingPlayer;
		private ArrayList<Pair<Integer,RECard>> hand;
		private String[] names;
		public void playAction(RECard card, Game game, Player actingPlayer) {
			this.actingPlayer = actingPlayer;
			
			//	generate list of discarded weapons
			hand = actingPlayer.hand().getAllCardPairs();
			
			names = new String[hand.size()];
			for (int i = 0; i < hand.size(); i++)
				names[i] = hand.get(i).second.getName();
			
			//	build and popup list of discarded weapons
			AlertDialog.Builder builder = new AlertDialog.Builder(game.getContext());
			builder.setTitle("Return a card to your deck");
			builder.setCancelable(false);
			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					onItemSelected(item);
				}
			});
			AlertDialog effectDialog = builder.create();
			effectDialog.show();
		}
		public void finish(RECard card, Game game, Player actingPlayer) { actingPlayer.inPlay().addBack(card); }
		private void onItemSelected(int item) {
			RECard temp = (RECard)actingPlayer.hand().pop(hand.get(item).first);
			actingPlayer.deck().addTop(temp);
		}
	}

	public static class TrashOnFinish implements OnPlayFinishListener {
		public void finish(RECard card, Game game, Player actingPlayer) {
			game.shop().returnCard(card);
		}
	}
}
