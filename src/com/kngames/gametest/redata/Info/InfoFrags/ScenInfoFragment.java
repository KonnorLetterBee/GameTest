package com.kngames.gametest.redata.Info.InfoFrags;

import java.util.ArrayList;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CLScenario;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.GameData;

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
		CLScenario scen = GameData.findCLScenario(scenID, true);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", scen.name());
		
		infoText = generateScenarioInfo(scen);
		
		footerText = "";
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateScenarioInfo(CLScenario scen) {
		//	generate the list of cards in the scenario
		StringBuilder cards = new StringBuilder();
		ArrayList<Card[]> cardList = scen.cards();
			
		for (int i = 0; i < cardList.size(); i++) {
			cards.append("   ");
			for (int j = 0; j < cardList.get(i).length; j++) {
				if (j > 0) cards.append(",  ");
				cards.append(((RECard) cardList.get(i)[j]).getName());
			}
			cards.append("\n");
		}
			
		//	generate the list of expansions required
		StringBuilder expansReq = new StringBuilder("Expansions required:  ");
		boolean[] expansReqArray = scen.expansRequired();
		int expCount = 0;
		for (int i = 0; i < expansReqArray.length; i++) {
			if (expansReqArray[i] == true) {
				if (expCount > 0) expansReq.append(", ");
				expansReq.append(Expansion.expansNames()[i]);
				expCount++;
			}
		}
		
		String gameMode = "Intended Game Mode:  " + scen.mode();
			
		//	set up the info string
		StringBuilder infoString = new StringBuilder();
		if (!scen.description().equals("")) infoString.append(scen.description() + "\n\n");
		infoString.append(expansReq.toString() + "\n" + gameMode + "\n\n" + cards.toString());
		
		return infoString.toString();
	}
}
