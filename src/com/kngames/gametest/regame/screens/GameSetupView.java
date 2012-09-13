package com.kngames.gametest.regame.screens;

import java.util.ArrayList;

import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.GameData;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class GameSetupView extends LinearLayout {
	
	private ArrayList<Pair<String,CharacterCard>> charData = getCharacterCards();
	private String[] charNameList = getNameList(charData);
	
	protected Context context;
	
	private class PlayerEntry extends RelativeLayout {
		TextView label;
		CheckBox computer;
		Spinner selector;
		
		private boolean selectorActive = true;
		
		private RelativeLayout.LayoutParams selOnLabelParams() {
			RelativeLayout.LayoutParams labelParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    labelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		    labelParams.addRule(RelativeLayout.ALIGN_BOTTOM, computer.getId());
		    return labelParams;
		}
		
		private RelativeLayout.LayoutParams selOffLabelParams() {
			RelativeLayout.LayoutParams labelParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    labelParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		    labelParams.addRule(RelativeLayout.ALIGN_BASELINE, computer.getId());
		    return labelParams;
		}
		
		public PlayerEntry(int playerNum) {
			super(context);
			
			//	initialize components and set text
			label = new TextView(context);
			label.setId(1000);
			computer = new CheckBox(context);
			computer.setId(1001);
			selector = new Spinner(context);
			selector.setId(1002);
			
			label.setText("Player " + playerNum);
			computer.setText("CPU player");
			
			//	set adapter for choosing character
			ArrayAdapter<String> playerAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, charNameList);
		    playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    selector.setAdapter(playerAdapter);
		    
		    //	LayoutParams for the label
		    this.addView(label, selOnLabelParams());
		    
		    //	LayoutParams for the checkbox
		    RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(
		    		RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    checkParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		    checkParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		    this.addView(computer, checkParams);
		    
		    //	LayoutParams for the spinner
		    RelativeLayout.LayoutParams spinnerParams = new RelativeLayout.LayoutParams(
		    		RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    spinnerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		    spinnerParams.addRule(RelativeLayout.BELOW, computer.getId());
		    this.addView(selector, spinnerParams);
		}
		
		//	sets whether the selector should be active or not
		//	enables or disables it only if there is a change from the current state
		public void setSelector(boolean active) {
			if (active != selectorActive) {
				selectorActive = active;
				if (selectorActive) enableSelector();
				else disableSelector();
			}
		}
		public void disableSelector() {
			this.removeView(selector);
			label.setLayoutParams(selOffLabelParams());
		}
		public void enableSelector() {
			label.setLayoutParams(selOnLabelParams());
			
			RelativeLayout.LayoutParams spinnerParams = new RelativeLayout.LayoutParams(
		    		RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		    spinnerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		    spinnerParams.addRule(RelativeLayout.BELOW, computer.getId());
		    this.addView(selector, spinnerParams);
		}
	}
	
	private int numPlayers;
	private PlayerEntry[] players;
	
	public GameSetupView(Context context) {
		super(context);
		init(context);
	}
	
	public GameSetupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	//	initialize the structure
	void init(Context context) {
		this.context = context;
    	setupLayout();
    	setNumPlayers(1);
    	setFocusable(true);
	}
	
	//	set up the layout
	void setupLayout() {
		this.setOrientation(LinearLayout.VERTICAL);
		players = new PlayerEntry[4];
		players[0] = new PlayerEntry(1);
		players[1] = new PlayerEntry(2);
		players[2] = new PlayerEntry(3);
		players[3] = new PlayerEntry(4);
	}
	
	public void setNumPlayers(int num) {
		if (num <= 4 && num >= 0) {
			numPlayers = num;
			this.removeAllViews();
			for (int i = 0; i < numPlayers; i++) {
				this.addView(players[i], new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			}
		}
	}
	
	public void setChoice (boolean canChoose) {
		for (int i = 0; i < players.length; i++) {
			players[i].setSelector(canChoose);
		}
	}
	
	
	///
	///		Utility Methods
	///
	
	private ArrayList<Pair<String,CharacterCard>> getCharacterCards() {
		ArrayList<Pair<String,CharacterCard>> data = new ArrayList<Pair<String,CharacterCard>>();
		for (int i = 0; i < GameData.Characters.length; i++) {
			CharacterCard temp = GameData.Characters[i];
			data.add(new Pair<String, CharacterCard>(String.format("%s (%s)", temp.getName(), Expansion.expansString(temp.getExpansion())), temp));
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
}
