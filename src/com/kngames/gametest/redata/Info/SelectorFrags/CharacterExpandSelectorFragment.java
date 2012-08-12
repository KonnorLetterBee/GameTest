package com.kngames.gametest.redata.Info.SelectorFrags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.REInfoFragmentActivity;
import com.kngames.gametest.redata.CardTypes.RECard;

public class CharacterExpandSelectorFragment extends BaseREExpandableSelectorFragment {
	public CharacterExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.CHAR_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View temp = super.onCreateView(inflater, container, savedInstanceState);
		
		listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				int type = 0;
				if (groupPosition < 4) type = 0;
				else if (groupPosition == 4) type = 1;
				else type = 2;
				
				openInfoWindow(cardCollection[groupPosition][childPosition].getID(), type);
				return true;
			}
		});
		
		return temp;
	}
	
	public static String[] generateTitles() {
		return new String[] { "Base Set", "Alliances", "Outbreak", "Nightmare", "Promo", "Infected" };
	}
	
	public static RECard[][] generateCollection() {
		RECard[][] array = new RECard[6][];
		array[0] = new RECard[10];
		array[1] = new RECard[10];
		array[2] = new RECard[10];
		array[3] = new RECard[10];
		array[4] = new RECard[CardData.PromoCharacters.length];
		array[5] = new RECard[CardData.InfectedCharacters.length];
		
		for (int i = 0; i < 10; i++) {
			array[0][i] = CardData.Characters[i];
		}
		for (int i = 0; i < 10; i++) {
			array[1][i] = CardData.Characters[i+10];
		}
		for (int i = 0; i < 10; i++) {
			array[2][i] = CardData.Characters[i+20];
		}
		for (int i = 0; i < 10; i++) {
			array[3][i] = CardData.Characters[i+30];
		}
		for (int i = 0; i < CardData.PromoCharacters.length; i++) {
			array[4][i] = CardData.PromoCharacters[i];
		}
		for (int i = 0; i < CardData.InfectedCharacters.length; i++) {
			array[5][i] = CardData.InfectedCharacters[i];
		}
		return array;
	}
	
	public boolean onClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		int type = 0;
		if (groupPosition < 4) type = 0;
		else if (groupPosition == 4) type = 1;
		else type = 2;
		
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
