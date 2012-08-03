package com.kngames.gametest.redata.CardInfo.SelectorFrags;

import java.util.ArrayList;

import com.kngames.gametest.redata.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardInfo.BaseExpandableSelectorFragment;
import com.kngames.gametest.redata.CardTypes.RECard;

public class WeaponExpandSelectorFragment extends BaseExpandableSelectorFragment {
	public WeaponExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.WEAPON_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		return new String[] { "Basic Resources", "Base Set", "Alliances", "Outbreak", "Nightmare" };
	}
	
	public static RECard[][] generateCollection() {
		ArrayList<ArrayList<RECard>> dualList = new ArrayList<ArrayList<RECard>>();
		for (int i = 0; i < 5; i++) dualList.add(new ArrayList<RECard>());
		for(int i = 0; i < CardData.Weapons.length; i++) {
			RECard temp = CardData.Weapons[i];
			int slot = temp.getExpansion() + 1;
			if (slot == 5) slot = 0;	//	to account for basics
			dualList.get(slot).add(temp);
		}
		
		RECard[][] array = new RECard[5][];
		array[0] = new RECard[dualList.get(0).size()];
		array[1] = new RECard[dualList.get(1).size()];
		array[2] = new RECard[dualList.get(2).size()];
		array[3] = new RECard[dualList.get(3).size()];
		array[4] = new RECard[dualList.get(4).size()];
		dualList.get(0).toArray(array[0]);
		dualList.get(1).toArray(array[1]);
		dualList.get(2).toArray(array[2]);
		dualList.get(3).toArray(array[3]);
		dualList.get(4).toArray(array[4]);
		
		return array;
	}
}