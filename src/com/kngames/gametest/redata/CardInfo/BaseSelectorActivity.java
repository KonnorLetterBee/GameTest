package com.kngames.gametest.redata.CardInfo;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardTypes.*;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class BaseSelectorActivity extends Activity {
	//	UI elements
	private ListView listView;
	protected ArrayList<String> strings;
	private ArrayAdapter<String> listAdapter;
	protected Class<?> className;
	protected RECard[] cardArray;
	
	public BaseSelectorActivity(Class<?> cls, RECard[] array) {
		this.className = cls;
		this.cardArray = array;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_list);
		
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
		for (int i = 0; i < cardArray.length; i++) {
			strings.add(cardArray[i].getName());
		}
	}
	
	protected void openInfoWindow(int ID) {
		Intent editorIntent = new Intent(this, className);
		editorIntent.putExtra("cardID", ID);
		this.startActivity(editorIntent);
	}
}
