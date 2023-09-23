package fa.dfa;

import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;

import fa.State;

public class DFA implements DFAInterface {
	
	TreeSet<State> DFA;
	
	/* DFA 5-tuple */
	State startState;
	State[] states;
	char[] alphabet;
	State[] finalStates;
	//Transition transitions;
	
	/* FIXME
	 * DFA constructor
	 */
	public DFA() {
		DFA = new TreeSet<State>();
		
		/* Instantiate 5-tuple */
		startState = null;
		states = new State[10];
		alphabet = new char[5];
		finalStates = new State[10];
		//transitions = new Transition
		
	}

	@Override
	public boolean addState(String name) {
		State newState = new State(name);
		DFA.add(newState);
	}

	@Override
	public boolean setFinal(String name) {
		for(int i = 0; i < states.length; i++) {
			if(states[i].getName() == name) {
				finalStates[].add(states[i]);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean setStart(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSigma(char symbol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accepts(String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Character> getSigma() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getState(String name) {
		//Search states array to find one with matching name
		for (int i = 0; i < states.length; i++) {
			if(states[i].getName() == name) {
				return states[i];
			}
		}
		// If not found
		return null;
	}

	@Override
	public boolean isFinal(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStart(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTransition(String fromState, String toState, char onSymb) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DFA swap(char symb1, char symb2) {
		// TODO Auto-generated method stub
		return null;
	}

}
