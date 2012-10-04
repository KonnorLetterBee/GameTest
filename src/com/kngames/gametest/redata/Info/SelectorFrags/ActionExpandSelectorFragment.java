package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.data.Expansion;

public class ActionExpandSelectorFragment extends BaseREExpandableSelectorFragment {
	public ActionExpandSelectorFragment() {
		super();
		this.fragType = BaseSingleFragmentActivity.ACTION_FRAG;
		this.catTitles = generateTitles();
		this.cardCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		Expansion[] temp = Expansion.expansObjectsEnabled();
		ArrayList<String> titles = new ArrayList<String>();
		
		//	fill the String array with expansion titles that actually have actions in them
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].actions() != null) titles.add(temp[i].expansName());
		}
		
		String[] titlesArray = new String[titles.size()];
		titles.toArray(titlesArray);
		return titlesArray;
	}
	
	public static RECard[][] generateCollection() {
		ArrayList<ArrayList<RECard>> dualList = new ArrayList<ArrayList<RECard>>();
		for (int i = 0; i < Expansion.expansions.length; i++) dualList.add(new ArrayList<RECard>());
		
		//	add actions from game expansions
		Card[] cards = data.getCategory("AC");
		for(int i = 0; i < cards.length; i++) {
			RECard temp = (RECard)cards[i];
			int slot = temp.getExpansion();
			dualList.get(slot).add(temp);
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
}