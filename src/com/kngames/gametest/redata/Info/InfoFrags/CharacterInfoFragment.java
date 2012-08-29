package com.kngames.gametest.redata.Info.InfoFrags;

import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.redata.CardTypes.InfectedCharacterCard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.carddata.CardData;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CharacterInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		int type = intent.getIntExtra("charType", 0);
		
		//	determine which list the card came from
		if (type == 0 || type == 1) {
			CharacterCard card;
			if (type == 1) {
				card = (CharacterCard) CardData.findCard(cardID, CardType.Character, 5);
			} else {
				card = (CharacterCard) CardData.findCard(cardID, CardType.Character, -1);
			}
			
			//	set the Strings necessary for the BaseInfoActivity to display the information correctly
			titleText = String.format("%s", card.getName());
			
			infoText = String.format(
					"Card Type:  Character\nExpansion Set:  %s\n" +
					"Max Health:  %d\n\nAbilities:\n\n%d:  %s",
					CardData.expansString(card.getExpansion()), 
					card.getMaxHealth(), card.getA1Price(), card.getAbility1());
			if (card.getA2Price() != 0) infoText += String.format("\n\n%d:  %s", card.getA2Price(), card.getAbility2());
			
			footerText = String.format("CARD ID:  %s", card.getIDString());
			
		} else {
			InfectedCharacterCard card = (InfectedCharacterCard)CardData.findCard(cardID, CardType.InfecChar, -1);
			
			//	set the Strings necessary for the BaseInfoActivity to display the information correctly
			titleText = String.format("%s", card.getName());
			
			infoText = String.format(
					"Card Type:  Infected Character\nExpansion Set:  %s\n" +
					"Max Health:  %d\nDamage:  %d\n\n%s",
					CardData.expansString(card.getExpansion()), 
					card.getMaxHealth(), card.getDamage(), card.getText());

			footerText = String.format("CARD ID:  %s", card.getIDString());
			super.onCreate(savedInstanceState);
		}
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
