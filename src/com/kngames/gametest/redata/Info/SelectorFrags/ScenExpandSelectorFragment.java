package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kngames.gametest.R;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.Scenario;

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
	
	private static final int CUSTOM_SLOT = 4;
	
	public ScenExpandSelectorFragment() {
		super();
		this.catTitles = generateTitles();
		this.scenCollection = generateCollection();
	}
	
	public static String[] generateTitles() {
		return new String[] { "Base Set", "Alliances", "Outbreak", "Nightmare", "Custom Scenarios" };
	}
	
	public static Scenario[][] generateCollection() {
		ArrayList<ArrayList<Scenario>> dualList = new ArrayList<ArrayList<Scenario>>();
		for (int i = 0; i < 5; i++) dualList.add(new ArrayList<Scenario>());
		for(int i = 0; i < ScenData.Scenarios.length; i++) {
			Scenario temp = ScenData.Scenarios[i];
			int slot = temp.getExpans();
			dualList.get(slot).add(temp);
		}
		
		for(int i = 0; i < ScenData.CustomScenarios.size(); i++) {
			Scenario temp = ScenData.CustomScenarios.get(i).second;
			dualList.get(CUSTOM_SLOT).add(temp);
		}
		
		Scenario[][] array = new Scenario[5][];
		array[0] = new Scenario[dualList.get(0).size()];
		array[1] = new Scenario[dualList.get(1).size()];
		array[2] = new Scenario[dualList.get(2).size()];
		array[3] = new Scenario[dualList.get(3).size()];
		array[4] = new Scenario[dualList.get(4).size()];
		dualList.get(0).toArray(array[0]);
		dualList.get(1).toArray(array[1]);
		dualList.get(2).toArray(array[2]);
		dualList.get(3).toArray(array[3]);
		dualList.get(4).toArray(array[4]);
		
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
				child.put(CHILD_KEY, scenCollection[i][n].getName());			
				secList.add( child );
			}
			result.add(secList);
		}		 
		return result;
	}

	public boolean onClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		openInfoWindow(scenCollection[groupPosition][childPosition].getID());
		return true;
	}
	
	protected void openInfoWindow(int ID) {
		Intent infoIntent = new Intent(getActivity(), BaseSingleFragmentActivity.class);
		infoIntent.putExtra("scenID", ID);
		infoIntent.putExtra("groupType", BaseSingleFragmentActivity.INFO_FRAG);
		infoIntent.putExtra("fragType", BaseSingleFragmentActivity.SCEN_FRAG);
		this.startActivity(infoIntent);
	}
}
