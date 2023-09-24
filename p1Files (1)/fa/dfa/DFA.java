package fa.dfa;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;

import javax.sound.midi.Sequence;

import fa.dfa.DFAState;
import fa.State;

public class DFA implements DFAInterface {
	
	/* DFA 5-tuple */
	private DFAState startState;
	private Set<DFAState> states;
	private Set<Character> sigma;
	private Set<DFAState> finalStates;
	
	public DFA() {

		/* Instantiate 5-tuple */
		startState = null;
		states = new LinkedHashSet<DFAState>();
		sigma = new LinkedHashSet<Character>();
		finalStates = new LinkedHashSet<DFAState>();

	}

	//DONE
	@Override
	public boolean addState(String name) {

		DFAState newState = null;
		newState = new DFAState(name);
		return states.add(newState);

	}

	//DONE
	@Override
	public boolean setFinal(String name) {
		
		DFAState curr = null;
		
		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			curr = itr.next();
			if(curr.getName() == name) {
				
				curr.setFinal(true); 
				finalStates.add(curr);
				
			}
			
		}	
		
		return false;
		
	}

	//DONE
	@Override
	public boolean setStart(String name) {
		
		DFAState curr = null;

		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			curr = itr.next();
			if(curr.getName() == name) {

				startState = curr;
				curr.setStart(true);
				return true;

			}
		}
		
		return false;
	}

	//DONE
	@Override
	public void addSigma(char symbol) {

		Character newSymbol = Character.valueOf(symbol);
		sigma.add(newSymbol);
		
	}

	//FIXME
	@Override
	public boolean accepts(String s) {
		
		int i = 0;
		DFAState curr = startState;
		HashMap<Character, String> currTransitions = null;

		while(i < s.length()) {

			currTransitions = curr.getTransitionOut();

			if(currTransitions.get(Character.valueOf(s.charAt[i])) != null) {

				//Take transition

			}

			int++;

		}
		return false;
	}

	//DONE
	@Override
	public Set<Character> getSigma() {
		
		return sigma;
	}

	//DONE
	@Override
	public DFAState getState(String name) {
		//Search states array to find one with matching name
		
		DFAState curr = null;

		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			curr = itr.next();
			if(curr.getName() == name) {

				return curr;

			}
		}

		// If not found
		return null;

	}

	//DONE
	@Override
	public boolean isFinal(String name) {
		
		DFAState curr = this.getState(name);
		return curr.getIsFinal();

	}


	@Override
	public boolean isStart(String name) {
		
		if(startState.getName() == name) {
			return true;
		} else {
			return false;
		}

	}

	//DONE
	@Override
	public boolean addTransition(String fromState, String toState, char onSymb) {
		
		//Instantiate variables
		DFAState fromDFAState = null;
		DFAState toDFAState = null;
		Character symbol = Character.valueOf(onSymb);

		//Get state with matching name
		fromDFAState = this.getState(fromState);
		toDFAState = this.getState(toState);

		//Check if fromState and toState exists as a state
		if(fromDFAState == null || toDFAState == null) {
			return false;
		}

		boolean hasSymb = false;

		//Check if onSymb is in sigma
		Iterator itr = sigma.iterator();
		while(itr.hasNext()) {
			if(itr.next() == symbol) {
				hasSymb = true;
			}
		}

		//Return false if onSymb is not in sigma
		if(!hasSymb) {
			return false;
		}

		//Add transition and return true
		fromDFAState.setTransitionOut(onSymb, toState);
		return true;

	}

	//FIXME
	@Override
	public DFA swap(char symb1, char symb2) {

		//Convert char values to Character values
		Character symbol1 = Character.valueOf(symb1);
		Character symbol2 = Character.valueOf(symb2);

		DFA newDFA = new DFA();
		char addSymb = '1';

		//Create newDFA's sigma set
		Iterator itr = sigma.iterator();
		while(itr.hasNext()) {


			newDFA.addSigma(itr.next().charValue());
		}
		
		itr = states.iterator();
		DFAState curr = null;

		while(itr.hasNext()) {

			curr = itr.next();

			

		}

		// TODO Auto-generated method stub
		return null;
	}

}