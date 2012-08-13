package com.kngames.gametest.regame;

import com.kngames.gametest.GameLoopActivity;
import com.kngames.gametest.redata.*;
import com.kngames.gametest.redata.Info.MainInfoListActivity;
import com.kngames.gametest.redata.ScenEditor.ScenarioEditorActivity;
import com.kngames.gametest.regame.dialog.ScenarioChooser;
import com.kngames.gametest.testcode.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TableLayout.LayoutParams;

public class MainMenu extends Activity {
	
	MainMenuView view;
	public static boolean TABLET_VIEW = false;
    public static boolean PHONE_VIEW = false;
    public static boolean SMALL_PHONE_VIEW = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeGlobals();
		view = new MainMenuView(getApplicationContext(), this);
		
		view.addButton("Game Test", GameLoopActivity.class);
		view.addButton("Game Setup Test", GameSetupActivity.class);
		view.addButton("Card List", MainInfoListActivity.class);
		view.addButton("Scenario List", new View.OnClickListener() {
			public void onClick(View v) {
				startScenList();
			}
		});
		view.addButton("Scenario Editor", new View.OnClickListener() {
			public void onClick(View v) {
				if (ScenData.CustomScenarios.size() == 0) startScenEditor(-1);
				else popupScenChooserDialog();
			}
		});
		view.addButton("Data Test", DataTestActivity.class);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		setContentView(view);
	}
	
	private void initializeGlobals() {
		ScenData.dbHelper = new ScenDBHelper(this);
		ScenData.CustomScenarios = ScenData.loadCustomScenarios();
		
		Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        //	if either dimension is below 800, assume phone screen
        if (screenHeight < 600 || screenWidth < 600) SMALL_PHONE_VIEW = true;
        else if (screenHeight < 800 || screenWidth < 800) PHONE_VIEW = true;
        else TABLET_VIEW = true;
	}
	
	//	provides a shorthand for starting an activity
	public void startActivity(Class<?> cls) {
		Intent intent = new Intent().setClass(this, cls);
		this.startActivity(intent);
	}
	
	//	pops up a dialog box prompting the user to choose between creating a new scenario or editing an existing one
	private ScenarioChooser chooser;
	private void popupScenChooserDialog() {
		chooser = new ScenarioChooser(this, new ScenarioChooser.DialogEventListener() {
			public void buttonPressed(int value) {
				startScenEditor(value);
				chooser.dismiss();
			}
		});
		chooser.show();
	}
	
	//	provides a shorthand for starting the scenario editor
	private void startScenList() {
		Intent intent = new Intent().setClass(this, REInfoFragmentActivity.class);
		intent.putExtra("groupType", REInfoFragmentActivity.EXPAND_SELECTOR_FRAG);
	    intent.putExtra("fragType", REInfoFragmentActivity.SCEN_FRAG);
		this.startActivity(intent);
	}
	
//	provides a shorthand for starting the scenario editor
	private void startScenEditor(int customIndex) {
		Intent intent = new Intent().setClass(this, ScenarioEditorActivity.class);
		intent.putExtra("scen_index", customIndex);
		this.startActivity(intent);
	}
}
