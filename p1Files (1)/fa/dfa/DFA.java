package fa.dfa;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;
import javax.sound.midi.Sequence;
import fa.dfa.DFAState;
import fa.State;

/**
 * This class is intended to allow a driver class to create a DFA object
 * using the DFAState class. It implements DFAInterface methods, as well
 * as FAInterface methods
 * @class CS361
 * @date September 25th 2023
 * @author Austin Andersen and Shane Ball
 */
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

		// Search through all states in the DFA to see if the state name exists
		for (DFAState state: states) {
			if (state.getName().equals(newState.getName())) {
				return false; // if it does exist exit without adding
			}
		}
		return states.add(newState); // if it doesn't exist add and return true
	}

	/**
	 * Given a state object will check the DFA to see if the state is added or not
	 * @param newState to be added
	 * @return boolean of if state is added or not
	 */
	private boolean addState(DFAState newState) {
		// Check if the state is already in the DFA
		if (!states.contains(newState)) {
			return states.add(newState); // If not add the state and return true
		}
		return false; // If yes exit and return false
	}

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

			// Get HashMap of valid transitions out of the current state and set to currTransitions
			// Also exit if curr or curr.getTransitionOut is null
			if (curr == null || (currTransitions = curr.getTransitionOut()) == null) {
				return false;
			}

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


	@Override
	public Set<Character> getSigma() {
		
		return sigma;
	}


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

		while(stateItr.hasNext()) {

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
		String printString = "Q = { ";

		// Iterate through states and add them all the names to Q
		for (DFAState state: states) {
			printString += state.getName() + " ";
		}
		printString += "}\n";

		printString += "Sigma = { ";
		String sigmaString = ""; // Create a string that stores sigma since it is used later
		for (Character c : sigma) { // Iterate through the sigma set and add each char to sigmaString
			sigmaString += c + " ";
		}
		printString += sigmaString;
		printString +="}\n";
		printString +="delta =\n";
		printString += "\t" + sigmaString + "\n";

		// Iterate through the states and get each states toString for their transition table
		// based on the order of the DFAs sigma
		for (DFAState state: states) {
			printString += state.toString(sigma) + "\n";
		}
		printString += "q0 = " + startState.getName() + "\n";

		printString += "F = { ";

		// Iterate through finalStates and get the name of all final states
		for (DFAState state: finalStates) {
			printString += state.getName() + " ";
		}
		printString += "}\n";

		return printString;
	}
}