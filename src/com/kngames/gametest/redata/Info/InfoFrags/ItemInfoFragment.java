package com.kngames.gametest.redata.Info.InfoFrags;

import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CardTypes.ItemCard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.data.GameData;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		ItemCard card = (ItemCard)GameData.findCard(cardID, CardType.Item, -1);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateItemInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateItemInfo(ItemCard card) {
		return String.format(
				"Card Type:  Item\nExpansion Set:  %s\nQuantity in Deck:  %d\nPrice:  %d\n" +
				"Found In:  %s\n\n%s",
				GameData.expansString(card.getExpansion()), card.getDeckQuantity(),card.getPrice(),
				GameData.originString(card.getOrigin()), card.getText());
	}
}
