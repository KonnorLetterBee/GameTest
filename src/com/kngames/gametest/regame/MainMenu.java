package com.kngames.gametest.regame;

import com.kngames.gametest.GameLoopActivity;
import com.kngames.gametest.fragtest.BasicFragmentActivity;
import com.kngames.gametest.redata.CardInfo.MainInfoListActivity;
import com.kngames.gametest.redata.ScenInfo.ScenSelectorActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.widget.TableLayout.LayoutParams;

public class MainMenu extends Activity {
	
	MainMenuView view;
    public static boolean PHONE_VIEW = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();
        //	if either dimension is below 800, assume phone screen
        if (screenHeight < 800 || screenWidth < 800) PHONE_VIEW = true;
		
		view = new MainMenuView(getApplicationContext(), this);
		
		view.addButton("Game Test", GameLoopActivity.class);
		view.addButton("Card List", MainInfoListActivity.class);
		view.addButton("Scenario List", ScenSelectorActivity.class);
		view.addButton("FragmentTest", BasicFragmentActivity.class);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		setContentView(view);
	}
	
	//	provides a shorthand for starting an activity
	public void startActivity(Class<?> cls) {
		Intent intent = new Intent().setClass(this, cls);
		this.startActivity(intent);
	}
	
	/*
	
	//	pops up a dialog box prompting the user to choose between creating a new scenario or editing an existing one
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
	private void startScenEditor(int customIndex) {
		Intent intent = new Intent().setClass(this, ScenarioEditorActivity.class);
		intent.putExtra("scen_index", customIndex);
		this.startActivity(intent);
	}
	*/
}
