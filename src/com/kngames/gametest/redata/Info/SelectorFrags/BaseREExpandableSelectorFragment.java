package com.kngames.gametest.redata.Info.SelectorFrags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.REInfoFragmentActivity;
import com.kngames.gametest.redata.CardTypes.RECard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class BaseREExpandableSelectorFragment extends Fragment {
	//	UI elements
	protected ArrayList<String> titles;
	protected ExpandableListView listView;
	private SimpleExpandableListAdapter listAdapter;
	protected int fragType;
	protected String[] catTitles;
	protected RECard[][] cardCollection;
	
	private static final String PARENT_KEY = "parent";
	private static final String CHILD_KEY = "child";
	
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
		for(int i = 0; i < cardCollection.length; i++ ) {
			ArrayList<HashMap<String,String>> secList = new ArrayList<HashMap<String,String>>(); 
			for(int n = 0; n < cardCollection[i].length; n++ ) {
				HashMap<String,String> child = new HashMap<String,String>();
				child.put(CHILD_KEY, cardCollection[i][n].getName());			
				secList.add( child );
			}
			result.add(secList);
		}		 
		return result;
	}

	public boolean onClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		openInfoWindow(cardCollection[groupPosition][childPosition].getID());
		return true;
	}
	
	protected void openInfoWindow(int ID) {
		Intent infoIntent = new Intent(getActivity(), REInfoFragmentActivity.class);
		infoIntent.putExtra("cardID", ID);
		infoIntent.putExtra("groupType", REInfoFragmentActivity.INFO_FRAG);
		infoIntent.putExtra("fragType", fragType);
		this.startActivity(infoIntent);
	}
}