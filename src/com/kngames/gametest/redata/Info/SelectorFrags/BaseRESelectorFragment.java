package com.kngames.gametest.redata.Info.SelectorFrags;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.REInfoFragmentActivity;
import com.kngames.gametest.redata.CardTypes.*;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class BaseRESelectorFragment extends Fragment {
	//	UI elements
	private ListView listView;
	protected ArrayList<String> strings;
	private ArrayAdapter<String> listAdapter;
	protected int fragType;
	protected RECard[] cardArray;
	
	public BaseRESelectorFragment(int fragType, RECard[] array) {
		this.fragType = fragType;
		this.cardArray = array;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.card_list, container, false).findViewById(R.id.linearLayout);
		
		//	set up UI
		listView = (ListView) view.findViewById(R.id.cardList);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				openInfoWindow(cardArray[index].getID());
			}
		});
		
		refreshList();
		int resID = android.R.layout.simple_list_item_1;
		listAdapter = new ArrayAdapter<String>(getActivity(), resID, strings);
		listView.setAdapter(listAdapter);
		
		return view;
	}
	
	protected void refreshList() {
		strings = new ArrayList<String>();
		for (int i = 0; i < cardArray.length; i++) {
			strings.add(cardArray[i].getName());
		}
	}
	
	protected void openInfoWindow(int ID) {
		Intent infoIntent = new Intent(getActivity(), REInfoFragmentActivity.class);
		infoIntent.putExtra("cardID", ID);
		infoIntent.putExtra("groupType", REInfoFragmentActivity.INFO_FRAG);
		infoIntent.putExtra("fragType", fragType);
		this.startActivity(infoIntent);
	}
}
