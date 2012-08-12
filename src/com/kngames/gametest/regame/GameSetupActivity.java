package com.kngames.gametest.regame;

import java.util.ArrayList;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.redata.CardTypes.RECard;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class GameSetupActivity extends Activity {
	
	private Activity ins = this;
	private ArrayList<Pair<String,CharacterCard>> characterData = getCharacterCards();
	private String[] nameList = getNameList(characterData);
	private String[] playerNumList = new String[] { "1", "2", "3", "4" };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_setup_layout);
		
		Spinner numPlayers = (Spinner)findViewById(R.id.player_spinner);
		Spinner player1 = (Spinner)findViewById(R.id.player_1_spinner);
		Spinner player2 = (Spinner)findViewById(R.id.player_2_spinner);
		Spinner player3 = (Spinner)findViewById(R.id.player_3_spinner);
		Spinner player4 = (Spinner)findViewById(R.id.player_4_spinner);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerNumList);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numPlayers.setAdapter(adapter);
	    
		ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
	    playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    player1.setAdapter(playerAdapter);
	    player2.setAdapter(playerAdapter);
	    player3.setAdapter(playerAdapter);
	    player4.setAdapter(playerAdapter);
		
		CheckBox player1Comp = (CheckBox)findViewById(R.id.player_1_compbox);
		CheckBox player2Comp = (CheckBox)findViewById(R.id.player_2_compbox);
		CheckBox player3Comp = (CheckBox)findViewById(R.id.player_3_compbox);
		CheckBox player4Comp = (CheckBox)findViewById(R.id.player_4_compbox);
		
		Button startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ins.finish();
			}
		});
	}
	
	private ArrayList<Pair<String,CharacterCard>> getCharacterCards() {
		ArrayList<Pair<String,CharacterCard>> data = new ArrayList<Pair<String,CharacterCard>>();
		for (int i = 0; i < CardData.Characters.length; i++) {
			CharacterCard temp = CardData.Characters[i];
			data.add(new Pair<String, CharacterCard>(temp.getName(), temp));
		}
		return data;
	}
	
	private String[] getNameList(ArrayList<Pair<String,CharacterCard>> pairs) {
		String[] data = new String[pairs.size()];
		for (int i = 0; i < pairs.size(); i++) {
			data[i] = pairs.get(i).second.getName();
		}
		return data;
	}
}
