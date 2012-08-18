package com.kngames.gametest.regame.screens;

import java.util.ArrayList;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Scenario;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

public class GameSetupActivity extends Activity {
	
	private Activity ins = this;
	private String[] charChoiceList = new String[] { "Random", "Choose Between Two", "Player Choice" };
	private String[] playerNumList = new String[] { "1", "2", "3", "4" };
	private ArrayList<Pair<String,Scenario>> scenData = getScenarios();
	private String[] scenNameList = getScenarioNameList(scenData);
	private String[] gameTypeList = ScenData.GameModeString;
	
	private GameSetupView playerPanel;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.game_setup_layout);
		ScrollView scroll = (ScrollView) getLayoutInflater().inflate(R.layout.game_setup_layout, null, false);
		LinearLayout v = (LinearLayout) scroll.findViewById(R.id.container);
		setContentView(scroll);
		
		//	the panel for each individual player to select their player
		playerPanel = (GameSetupView)findViewById(R.id.game_setup);
		playerPanel.setNumPlayers(1);
		playerPanel.setChoice(false);
		
		//	spinners
		Spinner numPlayers = (Spinner)v.findViewById(R.id.player_spinner);
		Spinner charChoice = (Spinner)v.findViewById(R.id.char_select_spinner);
		Spinner scenario = (Spinner)v.findViewById(R.id.scenario_spinner);
		Spinner gameType = (Spinner)v.findViewById(R.id.gametype_spinner);
		
		//	spinner adapters
		ArrayAdapter<String> characterChoiceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, charChoiceList);
	    characterChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    charChoice.setAdapter(characterChoiceAdapter);
	    charChoice.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int index, long arg3) {
				if (index == 2) playerPanel.setChoice(true);
				else playerPanel.setChoice(false);
			}
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		
		ArrayAdapter<String> numPlayerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, playerNumList);
	    numPlayerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		numPlayers.setAdapter(numPlayerAdapter);
		numPlayers.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int index, long arg3) {
				playerPanel.setNumPlayers(index + 1);
			}
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
	    
	    ArrayAdapter<String> scenarioAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scenNameList);
	    scenarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    scenario.setAdapter(scenarioAdapter);
	    
	    ArrayAdapter<String> gameTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gameTypeList);
	    gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    gameType.setAdapter(gameTypeAdapter);
		
		//	start button
		Button startButton = (Button)v.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ins.finish();
			}
		});
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
