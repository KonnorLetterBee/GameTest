package com.kngames.gametest.redata.ScenEditor;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.data.GameData;
import com.kngames.gametest.redata.data.GameData.GameMode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class ScenarioInfoEditorActivity extends Activity {
	EditText nameField;
	Spinner typeChooser;
	EditText descField;
	EditText notesField;
	CheckBox basicsBox;
	
	Button cancelButton;
	Button acceptButton;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.scenario_info_editor);
	    
	    nameField = (EditText)findViewById(R.id.nameField);
	    nameField.setText(GameData.customTempScenario.second.name());
	    descField = (EditText)findViewById(R.id.descField);
	    descField.setText(GameData.customTempScenario.second.description());
	    notesField = (EditText)findViewById(R.id.notesField);
	    notesField.setText(GameData.customTempScenario.second.notes());
	    basicsBox = (CheckBox)findViewById(R.id.basicsBox);
	    basicsBox.setChecked(GameData.customTempScenario.second.useBasics());
	    
	    typeChooser = (Spinner)findViewById(R.id.typeSpinner);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GameData.GameModeString);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    typeChooser.setAdapter(adapter);
	    
	    cancelButton = (Button)findViewById(R.id.cancelButton);
	    acceptButton = (Button)findViewById(R.id.acceptButton);
	    
	    cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	finishWithoutSaving();
            }
        });
	    acceptButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	saveAndFinish();
            }
        });
	}
	
	//	ends the activity without saving data
	private void finishWithoutSaving() {
		this.finish();
	}
	
	//	ends the activity and saves the proper data
	private void saveAndFinish() {
		GameData.customTempScenario.second.setName(nameField.getText().toString());
		GameData.customTempScenario.second.setDesc(descField.getText().toString());
		GameData.customTempScenario.second.setNotes(notesField.getText().toString());
		GameData.customTempScenario.second.setMode(GameMode.values()[typeChooser.getSelectedItemPosition()]);
		GameData.customTempScenario.second.setBasics(basicsBox.isChecked());
		this.finish();
	}
}