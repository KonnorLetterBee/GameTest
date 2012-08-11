package com.kngames.gametest.engine.hsm;

import java.util.ArrayList;

public abstract class State {
	public boolean current;
	public ArrayList<Transition> transitions;
	
	public State() {
		this.current = false;
		this.transitions = new ArrayList<Transition>();
	}

	public abstract int getAction();
	
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
	
	public void transitionOut(Transition trans) {
		assert trans.isTriggered() == true;
		setCurrentFalse();
		trans.destination.current = true;
	}
	
	public void setCurrentFalse() {
		current = false;
	}
}
