package com.kngames.gametest.redata.Info.InfoFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.carddata.CardData;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScenInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		int scenID = intent.getIntExtra("scenID", 0);
		Scenario scen = ScenData.findScenario(scenID, true);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", scen.getName());
		
		infoText = generateScenarioInfo(scen);
		
		footerText = "";
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateScenarioInfo(Scenario scen) {
		//	generate the list of cards in the scenario
		StringBuilder cards = new StringBuilder();
		ArrayList<RECard[]> cardList = scen.getCards();
			
		for (int i = 0; i < cardList.size(); i++) {
			cards.append("   ");
			for (int j = 0; j < cardList.get(i).length; j++) {
				if (j > 0) cards.append(",  ");
				cards.append(cardList.get(i)[j].getName());
			}
			cards.append("\n");
		}
			
		//	generate the list of expansions required
		StringBuilder expansReq = new StringBuilder("Expansions required:  ");
		boolean[] expansReqArray = scen.getExpansRequired();
		int expCount = 0;
		for (int i = 0; i < expansReqArray.length; i++) {
			if (expansReqArray[i] == true) {
				if (expCount > 0) expansReq.append(", ");
				expansReq.append(CardData.expansString(i));
				expCount++;
			}
		}
		
		String gameMode = "Intended Game Mode:  " + scen.getMode();
			
		//	set up the info string
		StringBuilder infoString = new StringBuilder();
		if (!scen.getDesc().equals("")) infoString.append(scen.getDesc() + "\n\n");
		infoString.append(expansReq.toString() + "\n" + gameMode + "\n\n" + cards.toString());
		
		return infoString.toString();
	}
}
