package com.kngames.gametest.redata.ScenInfo;

import java.util.ArrayList;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Scenario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ScenSelectorActivity extends Activity {
	//	UI elements
	private ListView listView;
	protected ArrayList<String> strings;
	private ArrayAdapter<String> listAdapter;
	protected Scenario[] scenArray;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_list);
		
		scenArray = ScenData.Scenarios;
		
		//	set up UI
		listView = (ListView)findViewById(R.id.cardList);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				openInfoWindow(index);
			}
		});
		
		refreshList();
		int resID = android.R.layout.simple_list_item_1;
		listAdapter = new ArrayAdapter<String>(this, resID, strings);
		listView.setAdapter(listAdapter);
	}
	
	protected void refreshList() {
		strings = new ArrayList<String>();
		for (int i = 0; i < scenArray.length; i++) {
			strings.add(scenArray[i].getName());
		}
	}
	
	protected void openInfoWindow(int ID) {
		Intent infoIntent = new Intent(this, BaseSingleFragmentActivity.class);
		infoIntent.putExtra("scenID", ID);
		infoIntent.putExtra("fragType", BaseSingleFragmentActivity.SCEN_FRAG);
		this.startActivity(infoIntent);
	}
}
