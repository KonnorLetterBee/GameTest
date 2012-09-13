package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.GameData;

public class WeaponExpandSelectorFragment extends BaseREExpandableSelectorFragment {
	public WeaponExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.WEAPON_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		Expansion[] temp = Expansion.expansObjectsEnabled();
		ArrayList<String> titles = new ArrayList<String>();
		
		//	fill the String array with expansion titles that actually have weapons in them
		titles.add("Basic Resources");
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].weapons() != null) titles.add(temp[i].expansName());
		}
		
		String[] titlesArray = new String[titles.size()];
		titles.toArray(titlesArray);
		return titlesArray;
	}
	
	public static RECard[][] generateCollection() {
		ArrayList<ArrayList<RECard>> dualList = new ArrayList<ArrayList<RECard>>();
		for (int i = 0; i < Expansion.expansions.length; i++) dualList.add(new ArrayList<RECard>());
		
		//	add weapons from game expansions
		for(int i = 0; i < GameData.Weapons.length; i++) {
			RECard temp = GameData.Weapons[i];
			int slot = temp.getExpansion();
			
			//	handle basic resources
			if (slot == Expansion.Expans.Basic.ordinal()) {	slot = 0; }
			else { slot++; }
			
			dualList.get(slot).add(temp);
		}
		
		//	remove weapon lists that have nothing in them
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
}