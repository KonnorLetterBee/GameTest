package com.kngames.gametest.redata.CardInfo;

import com.kngames.gametest.redata.BaseInfoActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.redata.CardTypes.InfectedCharacterCard;

import android.content.Intent;
import android.os.Bundle;

public class CharacterInfoActivity extends BaseInfoActivity {

	public void onCreate(Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		
		int mainLength = CardData.Characters.length;
		int promoLength = CardData.PromoCharacters.length;
		
		//	determine which list the card came from
		if (cardID < mainLength + promoLength) {
			CharacterCard card;
			if (cardID < mainLength) {
				card = CardData.Characters[cardID];
			} else {
				card = CardData.PromoCharacters[cardID - mainLength];
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
			InfectedCharacterCard card = CardData.InfectedCharacters[cardID - mainLength - promoLength];
			
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
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		super.onCreate(savedInstanceState);
	}
}
