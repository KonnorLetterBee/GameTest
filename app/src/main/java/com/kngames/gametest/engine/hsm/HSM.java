package com.kngames.gametest.engine.hsm;

import java.util.ArrayList;

public abstract class HSM extends State {
	protected State initialState;
	protected ArrayList<State> states;
	
	public HSM() {
		super();
		initialState = null;
		states = new ArrayList<State>();
	}
	
	public int getAction() {
		//	counts the number of states marked "current"
		int errorCheck = 0;
		State curr = null;
		for(State s : states) {
			if (s.current == true) {
				curr = s;
				errorCheck++;
			}
		}
		assert errorCheck < 2;
		
		if (curr == null) {
			curr = initialState;
			curr.current = true;
		}
		
		return curr.getAction();
	}
	
	//	returns an ArrayList of this HSM's transitions with all of its substates' transitions appended
	public ArrayList<Transition> getTransitions() {
		@SuppressWarnings("unchecked")
		ArrayList<Transition> allTransitions = (ArrayList<Transition>)transitions.clone();
		
		for(State s : states) {
			if (s.current) allTransitions.addAll(s.getTransitions());
		}
		
		return allTransitions;
	}
	
	//	tests the transitions of this HSM and all substates and returns the first transition that tests true, or null if all fail
	public boolean testTransitions() {
		ArrayList<Transition> transList = this.getTransitions();
		
		for(int i = 0; i < transList.size(); i++) {
			Transition tr = transList.get(i);
			if (tr.isTriggered()) {
				tr.source.setCurrentFalse();
				tr.destination.current = true;
				//System.out.printf("transitioning from %s to %s\n", tr.source.getClass(), tr.destination.getClass());
				return true;
			}
		}
		
		return false;
	}
	
	public void setCurrentFalse() {
		current = false;
		for(State s : states) {
			s.setCurrentFalse();
		}
	}
}
