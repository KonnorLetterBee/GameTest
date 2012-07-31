package com.kngames.gametest.redata.CardInfo;

import java.util.ArrayList;

import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.RECard;

public class CharacterSelectorActivity extends BaseSelectorActivity {
	public CharacterSelectorActivity() {
		super(CharacterInfoActivity.class, null);
		RECard[] array = new RECard[CardData.Characters.length + CardData.PromoCharacters.length + CardData.InfectedCharacters.length];
		
		int counter = 0;
		for (int i = 0; i < CardData.Characters.length; i++) {
			array[counter] = CardData.Characters[i];
			counter++;
		}
		for (int i = 0; i < CardData.PromoCharacters.length; i++) {
			array[counter] = CardData.PromoCharacters[i];
			counter++;
		}
		for (int i = 0; i < CardData.InfectedCharacters.length; i++) {
			array[counter] = CardData.InfectedCharacters[i];
			counter++;
		}
		
		cardArray = array;
	}
	
	protected void refreshList() {
		strings = new ArrayList<String>();
		for (int i = 0; i < cardArray.length; i++) {
			strings.add(cardArray[i].getName() + " (" + CardData.expansString(cardArray[i].getExpansion()) + ")");
		}
	}
}
