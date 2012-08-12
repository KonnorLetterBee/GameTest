package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.RECard;

public class MansionExpandSelectorFragment extends BaseREExpandableSelectorFragment {
	public MansionExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.MANS_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		return new String[] { "Base Set", "Alliances", "Outbreak", "Nightmare" };
	}
	
	public static RECard[][] generateCollection() {
		ArrayList<ArrayList<RECard>> dualList = new ArrayList<ArrayList<RECard>>();
		for (int i = 0; i < 4; i++) dualList.add(new ArrayList<RECard>());
		for(int i = 0; i < CardData.Mansions.length; i++) {
			RECard temp = CardData.Mansions[i];
			int slot = temp.getExpansion();
			dualList.get(slot).add(temp);
		}
		
		RECard[][] array = new RECard[4][];
		array[0] = new RECard[dualList.get(0).size()];
		array[1] = new RECard[dualList.get(1).size()];
		array[2] = new RECard[dualList.get(2).size()];
		array[3] = new RECard[dualList.get(3).size()];
		dualList.get(0).toArray(array[0]);
		dualList.get(1).toArray(array[1]);
		dualList.get(2).toArray(array[2]);
		dualList.get(3).toArray(array[3]);
		
		return array;
	}
}
