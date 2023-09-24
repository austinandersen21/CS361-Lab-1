package fa.dfa;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;

import javax.sound.midi.Sequence;

import fa.dfa.DFAState;

public class DFA implements DFAInterface {
	
	//TreeSet<DFAState> DFA;
	
	/* DFA 5-tuple */
	DFAState startState;
	Set<DFAState> states;
	Set<Character> sigma;
	Set<DFAState> finalStates;
	//HashSet<HashMap<DFAState, Character>> transitions;
	
	/* FIXME
	 * DFA constructor
	 */
	public DFA() {
		//DFA = new TreeSet<State>();
		
		/* Instantiate 5-tuple */
		startState = null;
		states = new LinkedHashSet<DFAState>();
		sigma = new LinkedHashSet<Character>();
		finalStates = new LinkedHashSet<DFAState>();
		//transitions = new TreeSet<TreeMap<DFAState, Character>>();
		
	}

	//DONE
	@Override
	public boolean addState(String name) {
		DFAState newState = new DFAState(name);
		states.add(newState);
	}

	//DONE
	@Override
	public boolean setFinal(String name) {
		
		DFAState curr = null;
		
		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			curr = itr.next();
			if(curr.getName() == name) {
				
				//FIXME Set search to final
				curr.isFinal(); 
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
		HashMap<DFAState, Character> currTransitions = null;

		while(i < s.length()) {

			currTransitions = curr.getTransitions;

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

	@Override
	public State getState(String name) {
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

		return curr.isFinal();

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
		Character symbol = null;

		//Assign values to variables
		fromDFAState = this.getState(fromState);
		toDFAState = this.getState(toState);
		symbol = Character.valueOf(onSymb);

		try {
			fromDFAState.addTransition(toDFAState, symbol);
		} catch (NullPointerException e) {
			//If any of the values are still assigned a null value
			return false;
		}
		
		//Else adding transition worked
		return true;

	}

	//FIXME
	@Override
	public DFA swap(char symb1, char symb2) {

		//Convert char values to Character values
		Character symbol1 = Character.valueOf(symb1);
		Character symbol2 = Character.valueOf(symb2);

		DFA newDFA = new DFA();

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