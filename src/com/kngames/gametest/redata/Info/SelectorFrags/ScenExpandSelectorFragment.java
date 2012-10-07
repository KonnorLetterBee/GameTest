package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kngames.gametest.R;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.REInfoFragmentActivity;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.data.Expansion;
import com.kngames.gametest.redata.data.GameData;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class ScenExpandSelectorFragment extends Fragment {
	
	//	UI elements
	protected ArrayList<String> titles;
	protected ExpandableListView listView;
	private SimpleExpandableListAdapter listAdapter;
	protected String[] catTitles;
	protected Scenario[][] scenCollection;
	
	private static final String PARENT_KEY = "parent";
	private static final String CHILD_KEY = "child";
	
	public ScenExpandSelectorFragment() {
		super();
		this.catTitles = generateTitles();
		this.scenCollection = generateCollection();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getActivity().setTitle("Scenario Encyclopedia");
	}
	
	private String[] generateTitles() {
		Expansion[] temp = Expansion.expansObjectsEnabled();
		ArrayList<String> titles = new ArrayList<String>();
		
		//	fill the String array with expansion titles that actually have scenarios in them, and add a "Custom" field
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].scenarios() != null) titles.add(temp[i].expansName());
		}
		titles.add("Custom Scenarios");
		
		String[] titlesArray = new String[titles.size()];
		titles.toArray(titlesArray);
		return titlesArray;
	}
	
	private Scenario[][] generateCollection() {
		ArrayList<ArrayList<Scenario>> dualList = new ArrayList<ArrayList<Scenario>>();
		for (int i = 0; i < Expansion.expansions.length; i++) dualList.add(new ArrayList<Scenario>());
		
		//	add scenarios from game expansions
		for(int i = 0; i < GameData.Scenarios.length; i++) {
			Scenario temp = GameData.Scenarios[i];
			int slot = temp.expans();
			dualList.get(slot).add(temp);
		}
		
		//	add custom scenarios
		for(int i = 0; i < GameData.CustomScenarios.size(); i++) {
			Scenario temp = GameData.CustomScenarios.get(i).second;
			dualList.get(dualList.size() - 1).add(temp);
		}
		
		//	remove scenario lists that have nothing in them
		for (int i = dualList.size() - 1; i >= 0; i--) {
			if (dualList.get(i).size() == 0) dualList.remove(i);
		}
		
		Scenario[][] array = new Scenario[dualList.size()][];
		for (int i = 0; i < dualList.size(); i++) {
			array[i] = new Scenario[dualList.get(i).size()];
			dualList.get(i).toArray(array[i]);
		}
		
		return array;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.card_list_expand, container, false);
		
		listView = (ExpandableListView) view.findViewById(R.id.list);
		listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				return onClick(parent, v, groupPosition, childPosition, id);
			}
		});
		
		listAdapter = new SimpleExpandableListAdapter(
			getActivity(),
			createGroupList(),
			android.R.layout.simple_expandable_list_item_1,		
			new String[] {PARENT_KEY}, new int[] { android.R.id.text1 },					
			createChildList(),
			android.R.layout.simple_list_item_1,
			new String[] {CHILD_KEY}, new int[] { android.R.id.text1 });
		listView.setAdapter(listAdapter);
		
		return view;
	}
	
	private List<HashMap<String,String>> createGroupList() {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < catTitles.length; i++) {
			HashMap<String,String> m = new HashMap<String,String>();
			m.put(PARENT_KEY, catTitles[i]);
	  		result.add(m);
		}
		return result;
	}
	
	private List<ArrayList<HashMap<String,String>>> createChildList() {
		ArrayList<ArrayList<HashMap<String,String>>> result = new ArrayList<ArrayList<HashMap<String,String>>>();
		for(int i = 0; i < scenCollection.length; i++ ) {
			ArrayList<HashMap<String,String>> secList = new ArrayList<HashMap<String,String>>();
			for(int n = 0; n < scenCollection[i].length; n++ ) {
				HashMap<String,String> child = new HashMap<String,String>();
				child.put(CHILD_KEY, scenCollection[i][n].name());			
				secList.add( child );
			}
			result.add(secList);
		}		 
		return result;
	}

	public boolean onClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		openInfoWindow(scenCollection[groupPosition][childPosition].id());
		return true;
	}
	
	protected void openInfoWindow(int ID) {
		Intent infoIntent = new Intent(getActivity(), REInfoFragmentActivity.class);
		infoIntent.putExtra("scenID", ID);
		infoIntent.putExtra("groupType", BaseSingleFragmentActivity.INFO_FRAG);
		infoIntent.putExtra("fragType", BaseSingleFragmentActivity.SCEN_FRAG);
		this.startActivity(infoIntent);
	}
}
