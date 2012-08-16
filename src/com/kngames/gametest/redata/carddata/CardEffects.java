package com.kngames.gametest.redata.carddata;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Pair;

import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.OnPlayListener;
import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public class CardEffects {
	public static class DeadlyAimEffect implements OnPlayListener {
		public void playAction(Game game, Player actingPlayer) {
			
		}
	}
	public static class ShatteredMemoriesEffect implements OnPlayListener {
		public void playAction(Game game, Player actingPlayer) {
			
		}
	}
	
	//	working
	public static class ReloadEffect implements OnPlayListener {
		private Game game;
		private Player actingPlayer;
		private ArrayList<Pair<Integer,RECard>> disWeapons;
		private String[] names;
		public void playAction(Game game, Player actingPlayer) {
			this.game = game;
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
		private void onItemSelected(int item) {
			RECard temp = (RECard)actingPlayer.discard().pop(disWeapons.get(item).first);
			actingPlayer.hand().addBottom(temp);
		}
	}
	public static class UmbrellaCorporationEffect implements OnPlayListener {
		public void playAction(Game game, Player actingPlayer) {
			
		}
	}
}
