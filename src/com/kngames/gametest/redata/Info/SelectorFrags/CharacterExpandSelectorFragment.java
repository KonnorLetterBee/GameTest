package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.REInfoFragmentActivity;
import com.kngames.gametest.redata.CardTypes.InfectedCharacterCard;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.Expansion.Expans;
import com.kngames.gametest.redata.data.GameData;

public class CharacterExpandSelectorFragment extends BaseREExpandableSelectorFragment {
	public CharacterExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.CHAR_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		Expansion[] temp = Expansion.expansObjectsEnabled();
		ArrayList<String> titles = new ArrayList<String>();
		
		//	fill the String array with expansion titles that actually have characters in them, and add an "Infected" field
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].characters() != null) titles.add(temp[i].expansName());
		}
		if (Expansion.isExpansEnabled(Expans.Outbreak)) {
			titles.add("Infected Characters");
		}
		
		String[] titlesArray = new String[titles.size()];
		titles.toArray(titlesArray);
		return titlesArray;
	}
	
	public static RECard[][] generateCollection() {
		ArrayList<ArrayList<RECard>> dualList = new ArrayList<ArrayList<RECard>>();
		for (int i = 0; i <= Expansion.expansions.length + 1; i++) dualList.add(new ArrayList<RECard>());
		
		//	add characters from game expansions
		for(int i = 0; i < GameData.Characters.length; i++) {
			RECard temp = GameData.Characters[i];
			int slot = temp.getExpansion();
			dualList.get(slot).add(temp);
		}
		
		//	add infected characters if Outbreak expansion is present
		if (Expansion.isExpansEnabled(Expans.Outbreak)) {
			for(int i = 0; i < GameData.InfectedCharacters.length; i++) {
				RECard temp = GameData.InfectedCharacters[i];
				dualList.get(dualList.size() - 1).add(temp);
			}
		}
		
		//	remove character lists that have nothing in them
		for (int i = dualList.size() - 1; i >= 0; i--) {
			if (dualList.get(i).size() == 0) dualList.remove(i);
		}
		
		RECard[][] array = new RECard[dualList.size()][];
		for (int i = 0; i < dualList.size(); i++) {
			array[i] = new RECard[dualList.get(i).size()];
			dualList.get(i).toArray(array[i]);
		}
		
		return array;
	}
	
	public boolean onClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		int type = 0;
		RECard temp = cardCollection[groupPosition][childPosition];
		if (temp instanceof InfectedCharacterCard) {
			type = 2;
		} else if (temp.getExpansion() == Expans.Promo.ordinal()) {
			type = 1;
		}
		
		openInfoWindow(cardCollection[groupPosition][childPosition].getID(), type);
		return true;
	}
	
	protected void openInfoWindow(int ID, int charType) {
		Intent infoIntent = new Intent(getActivity(), REInfoFragmentActivity.class);
		infoIntent.putExtra("cardID", ID);
		infoIntent.putExtra("groupType", REInfoFragmentActivity.INFO_FRAG);
		infoIntent.putExtra("fragType", fragType);
		infoIntent.putExtra("charType", charType);
		this.startActivity(infoIntent);
	}
}
