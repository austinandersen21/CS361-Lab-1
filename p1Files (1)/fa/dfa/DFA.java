package fa.dfa;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.HashMap;

import fa.dfa.DFAState;
import fa.State;

/**
 * @author Austin Andersen and Shane Ball
 * @class CS361
 * @date September 25th 2023
 * This class is intended to allow a driver class to create a DFA object 
 * using the DFAState class. It implements DFAInterface methods, as well
 * as FAInterface methods
 */
public class DFA implements DFAInterface {
	
	/* DFA 5-tuple */
	private DFAState startState;
	private Set<DFAState> states;
	private Set<Character> sigma;
	private Set<DFAState> finalStates;
	
	/**
	 * @author Austin Andersen
	 * This is the DFA constructor, which instantiates the DFA 5-tuple minus
	 * the transitions which are already stored in each state
	 */
	public DFA() {

		/* Instantiate 5-tuple */
		startState = null;
		states = new LinkedHashSet<DFAState>();
		sigma = new LinkedHashSet<Character>();
		finalStates = new LinkedHashSet<DFAState>();

	}

	@Override
	public boolean addState(String name) {

		DFAState newState = null;
		newState = new DFAState(name);
		return states.add(newState);

	}

	/**
	 * @author Austin Andersen
	 * @param newState - the new state to be added to the DFA
	 * @return boolean value representing if the state was added to the set or not
	 * This second addState class was added for convenience so we could add a
	 * state directly with it's transitions
	 */
	private boolean addState(DFAState newState) {

		return states.add(newState);

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

			// Get HashMap of valid transitions out of the current state
			currTransitions = curr.getTransitionOut();
			// Get the current char in the string
			char currentCharInString = s.charAt(i);
			// Return false if there are no valid transitions out of the state on the char
			if(!currTransitions.containsKey(currentCharInString)) {
				return false;
			}
			// Get the name of the to state
			String toStateName = currTransitions.get(Character.valueOf(currentCharInString));

			// Set the current state to the temp state for the next iteration of the loop
			// or loop exit
			curr = this.getState(toStateName);

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

	/**
	 * @author Austin Andersen
	 * @param name - Name of DFAState
	 * @return true if state with name value "name" exists, and false if not
	 */
	private boolean exists(String name) {

		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			if(itr.next().getName() == name) {
				return true;
			}
		}
		
		return false;

	}

	@Override
	public boolean addTransition(String fromState, String toState, char onSymb) {
		
		//Instantiate variables
		Character symbol = Character.valueOf(onSymb);

		//Check if fromState and toState exists as a state
		if(!exists(fromState) || !exists(toState)) {
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

		DFAState fromDFAState = getState(fromState);

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
				if(map.containsKey(e)) {

					if(e.equals(symbol1)) {

						//Switch transitions on symb1 to symb2
						newState.setTransitionOut(symb2, map.get(e));
						
					} else if(e.equals(symbol2)) {

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

	/**
	 * @author
	 * @param curr - current state whose transitions are being printed
	 * @return String of the curr state, followed by each destination on a given character
	 */
	private String transitionTableRow(DFAState curr) {

		String returnString = curr.getName();

		HashMap<Character, String> map = curr.getTransitionOut();

		for(Character c : sigma) {

			returnString = returnString.concat("\t");

			if(!map.containsKey(c)) {
				returnString = returnString.concat("{}");
			} else {
			returnString = returnString.concat(map.get(c));
			}

		}

		return returnString;

	}

	/**
	 * @author Austin Andersen
	 * @return String representation of the transition table
	 * Relies on transitionTableRow class to build each row
	 */
	public String transitionTable() {

		String returnString = "State";

		for(Character c : sigma) {
			returnString = returnString.concat("\t" + c);
		}

		returnString = returnString.concat("\n");

		Iterator<DFAState> itr = states.iterator();
		while(itr.hasNext()) {
			returnString = returnString.concat(transitionTableRow(itr.next()) + "\n");
		}

		return returnString;

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

	public static void main(String[] args) {

		System.out.println("Hello World");

		DFA thing = new DFA();

		DFAState zero = new DFAState("0");
		DFAState one = new DFAState("1");
		DFAState two = new DFAState("2");

		thing.addState(zero);
		thing.addState(one);
		thing.addState(two);

		thing.addSigma('a');
		thing.addSigma('b');
		thing.addSigma('c');

		thing.addTransition("0", "1", 'a');
		thing.addTransition("0", "0", 'b');
		thing.addTransition("0", "2", 'c');

		thing.addTransition("1", "2", 'b');
		thing.addTransition("1", "2", 'c');
		thing.addTransition("1", "0", 'a');

		thing.addTransition("2", "2", 'c');

		thing.setFinal("2");
		thing.setStart("0");

		System.out.println(thing.transitionTable());

		System.out.println(thing.accepts("ab"));


	}

}

