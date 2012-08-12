package com.kngames.gametest.regame;

import com.kngames.gametest.GameLoopActivity;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Info.MainInfoListActivity;
import com.kngames.gametest.testcode.DataTestActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout.LayoutParams;

public class GameSetupActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		/*
		super.onCreate(savedInstanceState);
		view = new MainMenuView(getApplicationContext(), this);
		
		view.addButton("Game Test", GameLoopActivity.class);
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
		*/
	}
}
