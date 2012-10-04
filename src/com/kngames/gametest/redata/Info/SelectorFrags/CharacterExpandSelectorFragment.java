package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.CardData;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.Expansion.Expans;

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
		Card[] chars = CardData.getCardData().getCategory("CH");
		
		for(int i = 0; i < chars.length; i++) {
			RECard temp = (RECard)chars[i];
			int slot = temp.getExpansion();
			if (temp.getCardType() == CardType.InfecChar) {
				//	for infected characters, change the slot to the last available
				slot = dualList.size() - 1;
			}
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
