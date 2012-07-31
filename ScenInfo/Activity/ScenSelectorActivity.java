package com.cperrigo.rehelper.ScenInfo.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.cperrigo.rehelper.R;
import com.cperrigo.rehelper.ScenData;
import com.cperrigo.rehelper.ScenInfo.Scenario;

public class ScenSelectorActivity extends Activity {
	//	UI elements
	private ListView listView;
	protected ArrayList<String> strings;
	private ArrayAdapter<String> listAdapter;
	protected final Class<?> className = ScenInfoActivity.class;
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
		Intent editorIntent = new Intent(this, className);
		editorIntent.putExtra("scenID", ID);
		this.startActivity(editorIntent);
	}
}
