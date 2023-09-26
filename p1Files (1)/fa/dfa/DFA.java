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

		for (DFAState state: states) {
			if (state.getName().equals(newState.getName())) {
				return false;
			}
		}
		return states.add(newState);
	}

	//DONE
	private boolean addState(DFAState newState) {
		if (!states.contains(newState)) {
			return states.add(newState);
		}
		return false;
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
				return true;
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


	/**
	 * Takes any string and iterates through each character to see if the string
	 * follows a path of valid transitions through the DFA states to a final state.
	 * If the string ends on a valid final state then returns true.
	 *
	 * @param s - the input string
	 * @return boolean confirming if string is accepted by DFA or not
	 */
	@Override
	public boolean accepts(String s) {
		
		int i = 0; // int to track the current char of the string
		DFAState curr = startState; // Tracks the current state in the DFA on each transition
		HashMap<Character, String> currTransitions = null; // Valid transitions for the state

		// Will iterate through the string until all characters are exhausted
		while(i < s.length()) {

			// Get HashMap of valid transitions out of the current state
			currTransitions = curr.getTransitionOut();
			// Get the current char in the string
			char currentCharInString = s.charAt(i);
			// Return false if there are no valid transitions out of the state on the char
			if(!currTransitions.containsKey(currentCharInString)) {
				return false;
			}
			// Get the name of the to state
			String toState = currTransitions.get(currentCharInString);

			// create a state object to store the to state temporarily
			DFAState tempDFAState = new DFAState("temp");

			// Iterate through the states linked hashset to get the to state object
			for (DFAState state: states) {
				if (state.getName().equals(toState)) {
					tempDFAState = state;
					break;
				} else {
					System.out.println("The accepts method couldn't find a to state that should be in the set");
				}
			}
			// Set the current state to the temp state for the next iteration of the loop
			// or loop exit
			curr = tempDFAState;

			i++; // Increment to the next char in the string
		}
		// Will only reach here if the string has been exhausted through valid transitions
		// and is at final state
		if (curr.getIsFinal()) {
			return true;
		}
		// Returns false if a final state is not reached
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
		Iterator<Character> itr = sigma.iterator();
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
		//char addSymb = '1';

		//Create newDFA's sigma set
		Iterator<Character> itr = sigma.iterator();
		while(itr.hasNext()) {

			newDFA.addSigma(itr.next().charValue());

		}
		
		//Now iterate through each state
		Iterator<DFAState> stateItr = states.iterator();
		DFAState curr = null;
		DFAState newState = null;

		while(itr.hasNext()) {

			//Create new state for newDFA
			curr = stateItr.next();
			newState = new DFAState(curr.getName());

			//Determine if is start and/or is final
			newState.setFinal(curr.getIsFinal());
			newState.setStart(curr.getIsStart());

			//Iterate through transitions
			HashMap<Character, String> map = curr.getTransitionOut();

			//Iterate through each transition character in sigma
			for(Character e : sigma) {

				//If transition exists, create transition for newState
				if(map.get(e) != null) {

					if(e == symbol1) {

						//Switch transitions on symb1 to symb2
						newState.setTransitionOut(symb2, map.get(e));
						
					} else if(e == symbol2) {

						//Switch transitions on symb2 to symb1
						newState.setTransitionOut(symb1, map.get(e));

					} else {

						//Add transition as is
						newState.setTransitionOut(e.charValue(), map.get(e));

					}

				}

			}
			
			//Add newState with new transitions
			newDFA.addState(newState);

			/* Finish 5-tuple variables */

			//Set state to startState if state isStart
			if(newState.getIsStart()) {
				newDFA.setStart(newState.getName());
			}

			//Add state to final set if state isFinal
			if(newState.getIsFinal()) {
				newDFA.setFinal(newState.getName());
			}

		}

		//Return swapped DFA
		return newDFA;

	}


	@Override
	public String toString() {
		String printString = "";

		printString += "Q = { ";

		for (DFAState state: states) {
			printString += state.getName() + " ";
		}
		printString += "}\n";

		printString += "Sigma = { ";
		String sigmaString = "";
		for (Character c : sigma) {
			sigmaString += (c + " ");
		}
		printString +="}\n";
		printString +="delta =\n";
		printString += "\t" + sigmaString + "\n";

		for (DFAState state: states) {
			printString += state.toString();
		}
		printString += "q0 = " + startState.getName();

		printString += "F = { ";

		for (DFAState state: finalStates) {
			printString += state.getName() + " ";
		}
		printString += "}\n";

		return printString;
	}
}