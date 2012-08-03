package com.kngames.gametest.redata.CardInfo.InfoFrags;

import com.kngames.gametest.redata.BaseInfoFragment;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.ItemCard;

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
		ItemCard card = CardData.findItem(cardID);
		
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
				CardData.expansString(card.getExpansion()), card.getDeckQuantity(),card.getPrice(),
				CardData.originString(card.getOrigin()), card.getText());
	}
}
