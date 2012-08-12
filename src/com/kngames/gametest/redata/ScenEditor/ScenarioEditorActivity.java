package com.kngames.gametest.redata.ScenEditor;

import java.util.ArrayList;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardData.Expans;
import com.kngames.gametest.redata.ScenData.GameMode;
import com.kngames.gametest.redata.Scenario;
import com.kngames.gametest.redata.ScenData;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.regame.MainMenu;
import com.kngames.gametest.regame.Dialog.CardChooserDialog;
//import com.kngames.gametest.regame.Dialog.CombineSplitChooserDialog;
import com.kngames.gametest.regame.Dialog.ScenarioChooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ScenarioEditorActivity extends Activity {
	
	ListView availableList;
	ListView usedList;
	TextView scenLabel;
	TextView inUseLabel;
	Button newButton;
	Button saveButton;
	Button loadButton;
	Button previewButton;
	Button deleteButton;
	Button infoButton;
	
	private int customIndex;
	private ArrayList<ArrayList<Pair<RECard,Integer>>> availableDataList;
	private ArrayList<ArrayList<Pair<RECard,Integer>>> usedDataList;
	private ArrayAdapter<String> availableAdapter;
	private ArrayAdapter<String> usedAdapter;
	private ArrayList<String> availableStrings;
	private ArrayList<String> usedStrings;
	
	private ScenarioChooser scenChooser;
	private CardChooserDialog cardChooser;
	//private CombineSplitChooserDialog combineSplitChooser;
	private ArrayList<ArrayList<Pair<RECard,Integer>>> selectedStackList;
	private int selectedStack;
	
	private int listResID = android.R.layout.simple_list_item_1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
		customIndex = intent.getIntExtra("scen_index", -1);

        if (MainMenu.TABLET_VIEW == false) {
        	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        	listResID = R.layout.listentry;
        }
        setContentView(R.layout.scenario_editor);
        
        availableList = (ListView)findViewById(R.id.scenario_list_available);
        usedList = (ListView)findViewById(R.id.scenario_list_using);
        scenLabel = (TextView)findViewById(R.id.name_view);
        inUseLabel = (TextView)findViewById(R.id.using_label);
        
        availableList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				ArrayList<Pair<RECard, Integer>> temp = availableDataList.get(index);
				availableDataList.remove(index);
				addToStackList(usedDataList, temp);
			}
		});
        
        availableList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> a, View v, int index, long l) {
				popupStackOptionsDialog(availableDataList, index);
				return true;
			}
		});
        
        usedList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				ArrayList<Pair<RECard,Integer>> temp = usedDataList.get(index);
				usedDataList.remove(index);
				addToStackList(availableDataList, temp);
			}
		});
        
        usedList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> a, View v, int index, long l) {
				popupStackOptionsDialog(usedDataList, index);
				return true;
			}
		});
        
        newButton = (Button)findViewById(R.id.scen_edit_new_button);
        saveButton = (Button)findViewById(R.id.scen_edit_save_button);
        loadButton = (Button)findViewById(R.id.scen_edit_load_button);
        previewButton = (Button)findViewById(R.id.scen_edit_preview_button);
        deleteButton = (Button)findViewById(R.id.scen_edit_delete_button);
        infoButton = (Button)findViewById(R.id.scen_edit_info_button);
        
        newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				generateInitialList();
				customIndex = -1;
		        ScenData.customTempScenario = new Pair<Integer,Scenario>(-1, new Scenario(0, "", GameMode.Story, Expans.Custom, true, "", "", ""));
		        updateViews();
				popupToast("New scenario created.");
			}
		});
        saveButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				saveScenario();
			}
		});
        loadButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupScenChooserDialog();
			}
		});
        previewButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupPreviewDialog();
			}
		});
        deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				deleteScenario();
			}
		});
        infoButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startScenInfoEditor();
			}
		});
        
        generateInitialList();
        //	determine what to do based on customIndex
        
        if (customIndex == -1) {
        	ScenData.customTempScenario = new Pair<Integer,Scenario>(-1, new Scenario(0, "", GameMode.Story, Expans.Custom, true, "", "", ""));
        } else if (customIndex >= 0) {
        	ScenData.customTempScenario = ScenData.CustomScenarios.get(customIndex);
        	loadScenarioData();
        } else {
        	loadScenarioData();
        }
        updateViews();
    }
	
	//	generates the initial list of cards in the availableDataList array
	private void generateInitialList() {
		int listCounter = 0;
		availableDataList = new ArrayList<ArrayList<Pair<RECard,Integer>>>();
		usedDataList = new ArrayList<ArrayList<Pair<RECard,Integer>>>();
		for (int i = 0; i < CardData.Weapons.length; i++, listCounter++) {
			//	detect and remove basics
			if (i != 3 && i != 4 && i != 5 && i != 6) {
				Pair<RECard,Integer> tempCard = new Pair<RECard, Integer>((RECard)CardData.Weapons[i], listCounter);
				//	detect and combine weapons to be initially combined
				if (i != 11 && i != 13 && i != 15 && i != 17 && i != 20 && i != 22 && i != 26 && i != 29 && i != 32 && i != 36 && i != 38) {
					ArrayList<Pair<RECard,Integer>> temp = new ArrayList<Pair<RECard,Integer>>();
					temp.add(tempCard);
					availableDataList.add(temp);
				} else {
					addToStack(availableDataList.get(availableDataList.size() - 1), tempCard);
				}
			}
    	}
    	for (int i = 0; i < CardData.Actions.length; i++, listCounter++) {
    		ArrayList<Pair<RECard,Integer>> temp = new ArrayList<Pair<RECard,Integer>>();
			temp.add(new Pair<RECard,Integer>((RECard)CardData.Actions[i], listCounter));
    		availableDataList.add(temp);
    	}
    	for (int i = 0; i < CardData.Items.length; i++, listCounter++) {
    		if (i != 0 && i != 1 && i != 4 && i != 5 && i != 6 && i != 7) {
    			ArrayList<Pair<RECard,Integer>> temp = new ArrayList<Pair<RECard,Integer>>();
				temp.add(new Pair<RECard,Integer>((RECard)CardData.Items[i], listCounter));
    			availableDataList.add(temp);
    		}
    	}
	}
	
	//	loads scenario data from the temp variable in ScenInfo
	private void loadScenarioData() {
		ArrayList<RECard[]> tempArray = ScenData.customTempScenario.second.getCards();
		//	iterate through piles of cards
		for (int i = 0; i < tempArray.size(); i++) {
			//	iterate through cards in pile
			for (int j = 0; j < tempArray.get(i).length; j++) {
				//	search for card in available list
				for (int k = 0; k < availableDataList.size(); k++) {
					//	if matches, move card over to used and break out
					ArrayList<Pair<RECard,Integer>> tempCard = availableDataList.get(k);
					if (tempArray.get(i)[j].getTag().equals(tempCard.get(0).first.getTag())) {
						availableDataList.remove(k);
						//	place card in correct position on used side
						int l;
						for (l = 0; l < usedDataList.size(); l++) {
							if (tempCard.get(0).second.intValue() < usedDataList.get(l).get(0).second.intValue()) {
								break;
							}
						}
						usedDataList.add(l, tempCard);
						break;
					}
				}
			}
		}
	}
	
	//	saves the scenario to the database
	private void saveScenario() {
		updateTempScenario();
		if (customIndex == -1) {
			//	adds the current temp scenario to the list of custom scenarios
			customIndex = ScenData.CustomScenarios.size();
			ScenData.dbHelper.addScenario(ScenData.customTempScenario.second);
			ScenData.CustomScenarios = ScenData.loadCustomScenarios();
	    	ScenData.customTempScenario = ScenData.CustomScenarios.get(customIndex);
		}
		else {
			//	replaces a scenario in the database
			ScenData.dbHelper.updateScenario(ScenData.customTempScenario.first, ScenData.customTempScenario.second);
	    	ScenData.CustomScenarios.set(customIndex, ScenData.customTempScenario);
		}
		popupToast("Scenario saved.");
	}
	
	//	deletes the current scenario from the database
	private void deleteScenario() {
		if (customIndex == -1) {
			popupToast("Scenario is not saved.");
		} else {
			ScenData.dbHelper.deleteScenario(ScenData.customTempScenario.first);
			ScenData.CustomScenarios = ScenData.loadCustomScenarios();
			generateInitialList();
			customIndex = -1;
	        ScenData.customTempScenario = new Pair<Integer,Scenario>(-1, new Scenario(0, "", GameMode.Story, Expans.Custom, true, "", "", ""));
	        updateViews();
	        popupToast("Scenario erased.");
		}
	}
	
	//	updates the temp scenario with the cards currently in the used list
	private void updateTempScenario() {
		String[] tags = new String[usedDataList.size()];
		for (int i = 0; i < usedDataList.size(); i++) {
			StringBuilder tempTag = new StringBuilder();
			for (int j = 0; j < usedDataList.get(i).size(); j++) {
				if (j > 0) tempTag.append(" ");
				tempTag.append(usedDataList.get(i).get(j).first.getTag());
			}
			tags[i] = tempTag.toString();
		}
		ScenData.customTempScenario.second.setCards(tags);
	}
	
    //	updates the UI views to reflect current values
    private void updateViews() {
    	refreshAvailable();
    	refreshUsed();
    	
    	inUseLabel.setText("In-Use Cards  (" + usedDataList.size() + ")");
    	try {
	    	String scenName = ScenData.customTempScenario.second.getName();
	    	if (scenName.equals("")) scenLabel.setText("Untitled Scenario");
	    	else scenLabel.setText(scenName);
    	} catch (NullPointerException e) {
    		scenLabel.setText("Untitled Scenario");
    	}
    }
    
    //	refreshes the hand String list
    private void refreshAvailable() {
		availableStrings = new ArrayList<String>();
		for (int i = 0; i < availableDataList.size(); i++) {
			availableStrings.add(getStackString(availableDataList.get(i)));
		}
		availableAdapter = new ArrayAdapter<String>(this, listResID, availableStrings);
		availableList.setAdapter(availableAdapter);
	}
    
    //	refreshes the play area String list
    private void refreshUsed() {
		usedStrings = new ArrayList<String>();
		for (int i = 0; i < usedDataList.size(); i++) {
			usedStrings.add(getStackString(usedDataList.get(i)));
		}
		usedAdapter = new ArrayAdapter<String>(this, listResID, usedStrings);
		usedList.setAdapter(usedAdapter);
	}
    
    //	adds a stack of cards to a list of stacks
    private void addToStackList(ArrayList<ArrayList<Pair<RECard,Integer>>> stackList, ArrayList<Pair<RECard,Integer>> stack) {
    	int i;
		for (i = 0; i < stackList.size(); i++) {
			if (stack.get(0).second.intValue() < stackList.get(i).get(0).second.intValue()) {
				break;
			}
		}
		stackList.add(i, stack);
		updateViews();
    }
    
    //	adds a card to a stack of cards
    private void addToStack(ArrayList<Pair<RECard,Integer>> stack, Pair<RECard,Integer> card) {
    	int i;
		for (i = 0; i < stack.size(); i++) {
			if (card.second.intValue() < stack.get(i).second.intValue()) {
				break;
			}
		}
		stack.add(i, card);
		updateViews();
    }
    
    //	combines the contents of two stacks to preserve numeric order
    private ArrayList<Pair<RECard,Integer>> combineStacks(ArrayList<Pair<RECard,Integer>> first, ArrayList<Pair<RECard,Integer>> other) {
    	ArrayList<Pair<RECard,Integer>> out = new ArrayList<Pair<RECard,Integer>>();
    	int firstPos = 0, otherPos = 0;
    	
    	//	merge sort the lists
    	while(firstPos < first.size() || otherPos < other.size()) {
    		if (firstPos < first.size() && otherPos < other.size()) {	//	both lists have extra elements, compare
    			if (first.get(firstPos).second.intValue() < other.get(otherPos).second.intValue()) {
    				out.add(first.get(firstPos));
    				firstPos++;
    			} else {
    				out.add(other.get(otherPos));
    				otherPos++;
    			}
    		} else if (firstPos < first.size()) {
    			out.add(first.get(firstPos));
				firstPos++;
    		} else {
    			out.add(other.get(otherPos));
				otherPos++;
    		}
    	}
    	
    	return out;
    }
    
	//	provides a shorthand for starting the scenario editor
    private void startScenInfoEditor() {
		Intent intent = new Intent().setClass(this, ScenarioInfoEditorActivity.class);
		intent.putExtra("scen_index", customIndex);
		this.startActivity(intent);
	}
	
	//	pops up a dialog box prompting the user to choose between creating a new scenario or editing an existing one
	private void popupScenChooserDialog() {
		scenChooser = new ScenarioChooser(this, new ScenarioChooser.DialogEventListener() {
			public void buttonPressed(int value) {
				customIndex = value;
				generateInitialList();
		        ScenData.customTempScenario = ScenData.CustomScenarios.get(customIndex);
		        loadScenarioData();
		        updateViews();
				scenChooser.dismiss();
			}
		});
		scenChooser.show();
	}
	
	//	generates a single string for representing a stack of cards
	private String getStackString(ArrayList<Pair<RECard,Integer>> stack) {
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < stack.size(); i++) {
			if (i > 0) 
				temp.append(", ");
			temp.append("(" + stack.get(i).first.getIDString() + ")  " + stack.get(i).first.getName());
		}
		return temp.toString();
	}
	
	//	displays a dialog that allows the user to combine piles of cards, or split them
	private void popupStackOptionsDialog(ArrayList<ArrayList<Pair<RECard,Integer>>> stackList, int stack) {
		selectedStackList = stackList;
		selectedStack = stack;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getStackString(selectedStackList.get(stack)));
		
		int listSize = selectedStackList.size();
		int stackSize = selectedStackList.get(stack).size();
		
		if (listSize <= 1 && stackSize <= 1) {
			popupToast("No actions available");
			return;
		}
		
		if (listSize > 1)
			builder.setPositiveButton("Combine Stacks...", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   popupStackCombineDialog(selectedStack);
					   dialog.cancel();
				   }
			   });
		if (stackSize > 1)
			builder.setNeutralButton("Split Stack", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   ArrayList<Pair<RECard,Integer>> stack = selectedStackList.get(selectedStack);
					   while (stack.size() > 1) {
						   ArrayList<Pair<RECard,Integer>> temp = new ArrayList<Pair<RECard,Integer>>();
						   temp.add(stack.get(1));
						   stack.remove(1);
						   addToStackList(selectedStackList, temp);
					   }
					   dialog.dismiss();
				   }
			   });
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   dialog.cancel();
				   }
			   });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	//	displays a dialog that allows the user to combine piles of cards, or split them
	private void popupNewStackOptionsDialog(ArrayList<ArrayList<Pair<RECard,Integer>>> stackList, int stack) {
		selectedStackList = stackList;
		selectedStack = stack;
		/*combineSplitChooser = new CombineSplitChooserDialog(this, new CombineSplitChooserDialog.DialogEventListener() {
			public void buttonPressed(int value) {
				
			}
		});*/
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getStackString(selectedStackList.get(stack)));
		
		int listSize = selectedStackList.size();
		int stackSize = selectedStackList.get(stack).size();
		
		if (listSize <= 1 && stackSize <= 1) {
			popupToast("No actions available");
			return;
		}
		
		if (listSize > 1)
			builder.setPositiveButton("Combine Stacks...", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   popupStackCombineDialog(selectedStack);
					   dialog.cancel();
				   }
			   });
		if (stackSize > 1)
			builder.setNeutralButton("Split Stack", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   ArrayList<Pair<RECard,Integer>> stack = selectedStackList.get(selectedStack);
					   while (stack.size() > 1) {
						   ArrayList<Pair<RECard,Integer>> temp = new ArrayList<Pair<RECard,Integer>>();
						   temp.add(stack.get(1));
						   stack.remove(1);
						   addToStackList(selectedStackList, temp);
					   }
					   dialog.dismiss();
				   }
			   });
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
					   dialog.cancel();
				   }
			   });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	//	displays a dialog that allows the user to combine piles of cards, or split them
	private void popupStackCombineDialog(int stack) {
		CharSequence[] otherUsed = new CharSequence[selectedStackList.size() - 1];
		for (int i = 0, temp = 0; i < selectedStackList.size(); i++) {
			if (i != stack) {
				otherUsed[temp] = getStackString(selectedStackList.get(i));
				temp++;
			}
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Combine with which pile")
			   .setItems(otherUsed, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					int otherStack = which;
					if (which >= selectedStack) otherStack++;
					ArrayList<Pair<RECard,Integer>> tempStack1 = selectedStackList.get(selectedStack);
					ArrayList<Pair<RECard,Integer>> tempStack2 = selectedStackList.get(otherStack);
					
					//	remove old entries from the list
					selectedStackList.remove(selectedStack);
					if (otherStack >= selectedStack) otherStack--;
					selectedStackList.remove(otherStack);
					
					//	add the new combined entry to the list and update the views
					addToStackList(selectedStackList, combineStacks(tempStack1, tempStack2));
					updateViews();
					dialog.dismiss();
				}
			});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	//	pops up a dialog box to preview the scenario in a CardChooserDialog
	private void popupPreviewDialog() {
		cardChooser = new CardChooserDialog(this, new CardChooserDialog.DialogEventListener() {
			public void buttonPressed(int value) {} });
		if (ScenData.customTempScenario.second.useBasics()) {
			cardChooser.addButton("Ammo x10");
			cardChooser.addButton("Ammo x20");
			cardChooser.addButton("Ammo x30");
			cardChooser.addButton("Combat Knife");
			cardChooser.addButton("Survival Knife");
			cardChooser.addButton("Handgun");
			cardChooser.addButton("Blowback Pistol");
			cardChooser.addButton("Green Herb");
		}
		for (int i = 0; i < usedDataList.size(); i++) {
			cardChooser.addButton(usedDataList.get(i).get(0).first.getName());
		}
		cardChooser.show();
	}
	
	private void popupToast(String message) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, message, duration);
		toast.show();
	}
}