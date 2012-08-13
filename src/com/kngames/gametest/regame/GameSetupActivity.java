package com.kngames.gametest.regame;

import java.util.ArrayList;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.CardTypes.CharacterCard;

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
	private ArrayList<Pair<String,CharacterCard>> charData = getCharacterCards();
	private String[] charNameList = getNameList(charData);
	private String[] charChoiceList = new String[] { "Random", "Choose Between Two", "Player Choice" };
	private String[] playerNumList = new String[] { "1", "2", "3", "4" };
	private ArrayList<Pair<String,Scenario>> scenData = getScenarios();
	private String[] scenNameList = getScenarioNameList(scenData);
	private String[] gameTypeList = ScenData.GameModeString;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_setup_layout);
		
		//	spinners
		Spinner numPlayers = (Spinner)findViewById(R.id.player_spinner);
		Spinner charChoice = (Spinner)findViewById(R.id.char_select_spinner);
		Spinner player1 = (Spinner)findViewById(R.id.player_1_spinner);
		Spinner player2 = (Spinner)findViewById(R.id.player_2_spinner);
		Spinner player3 = (Spinner)findViewById(R.id.player_3_spinner);
		Spinner player4 = (Spinner)findViewById(R.id.player_4_spinner);
		Spinner scenario = (Spinner)findViewById(R.id.scenario_spinner);
		Spinner gameType = (Spinner)findViewById(R.id.gametype_spinner);
		
		//	spinner adapters
		ArrayAdapter<String> characterChoiceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, charChoiceList);
	    characterChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    charChoice.setAdapter(characterChoiceAdapter);
		
		ArrayAdapter<String> numPlayerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playerNumList);
	    numPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numPlayers.setAdapter(numPlayerAdapter);
	    
		ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, charNameList);
	    playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    player1.setAdapter(playerAdapter);
	    player2.setAdapter(playerAdapter);
	    player3.setAdapter(playerAdapter);
	    player4.setAdapter(playerAdapter);
	    
	    ArrayAdapter<String> scenarioAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scenNameList);
	    scenarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    scenario.setAdapter(scenarioAdapter);
	    
	    ArrayAdapter<String> gameTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gameTypeList);
	    gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    gameType.setAdapter(gameTypeAdapter);
		
	    //	check boxes
		CheckBox player1Comp = (CheckBox)findViewById(R.id.player_1_compbox);
		CheckBox player2Comp = (CheckBox)findViewById(R.id.player_2_compbox);
		CheckBox player3Comp = (CheckBox)findViewById(R.id.player_3_compbox);
		CheckBox player4Comp = (CheckBox)findViewById(R.id.player_4_compbox);
		player1Comp.setTextSize(10);
		player2Comp.setTextSize(10);
		player3Comp.setTextSize(10);
		player4Comp.setTextSize(10);
		
		//	start button
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
			data.add(new Pair<String, CharacterCard>(String.format("%s (%s)", temp.getName(), CardData.expansString(temp.getExpansion())), temp));
		}
		return data;
	}
	
	private String[] getNameList(ArrayList<Pair<String,CharacterCard>> pairs) {
		String[] data = new String[pairs.size()];
		for (int i = 0; i < pairs.size(); i++) {
			data[i] = pairs.get(i).first;
		}
		return data;
	}
	
	private ArrayList<Pair<String,Scenario>> getScenarios() {
		ArrayList<Pair<String,Scenario>> data = new ArrayList<Pair<String,Scenario>>();
		for (int i = 0; i < ScenData.Scenarios.length; i++) {
			Scenario temp = ScenData.Scenarios[i];
			data.add(new Pair<String, Scenario>(String.format("%s (%s)", temp.getName(), temp.getMode()), temp));
		}
		return data;
	}
	
	private String[] getScenarioNameList(ArrayList<Pair<String,Scenario >> pairs) {
		String[] data = new String[pairs.size()];
		for (int i = 0; i < pairs.size(); i++) {
			data[i] = pairs.get(i).first;
		}
		return data;
	}
}
