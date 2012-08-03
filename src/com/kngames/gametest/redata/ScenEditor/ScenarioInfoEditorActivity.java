package com.kngames.gametest.redata.ScenEditor;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.ScenData.GameMode;

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
	    nameField.setText(ScenData.customTempScenario.second.getName());
	    descField = (EditText)findViewById(R.id.descField);
	    descField.setText(ScenData.customTempScenario.second.getDesc());
	    notesField = (EditText)findViewById(R.id.notesField);
	    notesField.setText(ScenData.customTempScenario.second.getNotes());
	    basicsBox = (CheckBox)findViewById(R.id.basicsBox);
	    basicsBox.setChecked(ScenData.customTempScenario.second.useBasics());
	    
	    typeChooser = (Spinner)findViewById(R.id.typeSpinner);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ScenData.GameModeString);
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
		ScenData.customTempScenario.second.setName(nameField.getText().toString());
		ScenData.customTempScenario.second.setDesc(descField.getText().toString());
		ScenData.customTempScenario.second.setNotes(notesField.getText().toString());
		ScenData.customTempScenario.second.setMode(GameMode.values()[typeChooser.getSelectedItemPosition()]);
		ScenData.customTempScenario.second.setBasics(basicsBox.isChecked());
		this.finish();
	}
}