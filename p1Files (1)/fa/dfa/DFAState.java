package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Austin Andersen and Shane Ball
 * @class CS361
 * @date September 25th 2023
 * This class maintains a state that can be used in a DFA. The state holds
 * information that identifies if it is a starting and/or a final state and
 * all of its possible outgoing transitions.
 */
public class DFAState extends State {
    private HashMap<Character, String> transitionsOut;
    private boolean isStart;
    private boolean isFinal;
    private char[] alphabet;


    /**
     * Initialize a new state with a name string
     *
     * @param name
     */
    public DFAState(String name) {
        super(name);
        transitionsOut = new HashMap<>();
        isFinal = false;
        isStart = false;
        alphabet = new char[10];
    }

    /**
     * Initialize a new state with a name and 1 transition which includes the alphabet
     * character and the string name of the state the transition goes to
     *
     * @param name
     * @param alphabetChar
     * @param toStateName
     */
    public DFAState(String name, char alphabetChar, String toStateName) {
        super(name);
        transitionsOut = new HashMap<>();
        transitionsOut.put(alphabetChar, toStateName);
        isFinal = false;
        isStart = false;
        alphabet = new char[10];
        updateAlphabet(alphabetChar);
    }

    /**
     * Add a transition to the transitionsOut HashMap of the state that tracks
     * the transition character and the state the transition goes to. Also, update
     * the alphabet array of the state
     *
     * @param alphabetChar
     * @param toStateName
     */
    public void setTransitionOut(char alphabetChar, String toStateName) {
        transitionsOut.put(alphabetChar, toStateName);
        updateAlphabet(alphabetChar);
    }

    /**
     * Adds an alphabet character to the state's alphabet array.
     * Will not add a duplicative character that is already in the array
     * @param alphabetChar
     */
    public void updateAlphabet(char alphabetChar) {
        for (int i = 0; i < alphabet.length; i++) {
            // While iterating through the array, if the character is found in the array
            // exit without adding it again.
            if (alphabet[i] == alphabetChar) {
                return;
            }
            // Add the character in the first empty index in the array
            if (alphabet[i] == '\u0000') {
                alphabet[i] = alphabetChar;
                return;
            }
        }
    }

    /**
     * Sets the state as a starting state or removes it as one based on the isStart
     * boolean value
     * @param isStart
     */
    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    /**
     * Returns the isStart status of the state
     * @return boolean isStart
     */
    public boolean getIsStart() {
        return isStart;
    }

    /**
     * Returns the isFinal status of the state
     * @return boolean isFinal
     */
    public boolean getIsFinal() {
        return isFinal;
    }

    /**
     * Sets the state as a final state or removes it as one based on the isFinal
     * boolean value
     * @param isFinal
     */
    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }


    /**
     * @return the entire HashMap of transitions out of the state
     */
    public HashMap<Character, String> getTransitionOut() {
        return transitionsOut;
    }

    /**
     * Given an alphabet character this method will check the HashMap to see if
     * a transition exists and will provide the name of the next state if it does
     * @param alphabetChar
     * @return The next state given the transition character
     */
    public String getNextStateOnAlphabet(char alphabetChar) {
        return transitionsOut.get(alphabetChar);
    }


    /**
     * Uses the alphabet known to the state return the transitions out of the state.
     * This is based on the known alphabet to the state so may not return the full alphabet
     * of the DFA. Also, the alphabet is created in the order of transitions that are added
     * to the state so may not be in the order of the sigma known to the DFA.
     * @return the toString that represents the State's row in the transition table
     */
    @Override
    public String toString() {
        String printString = super.toString() + "\t";
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == '\u0000') {
                break;
            } else if (getNextStateOnAlphabet(alphabet[i]) == null) {
                printString += "  ";
            } else {
                System.out.println("On: " + alphabet[i] + " Go to: " + getNextStateOnAlphabet(alphabet[i]));
                printString += getNextStateOnAlphabet(alphabet[i]) + " ";
            }
        }
        printString += "\n";
        return printString;
    }

    /**
     * The state's alphabet array may be updated to a different order of sigma than the DFA
     * holds. When printing the entire transition table for the DFA, this method can be called
     * by the DFA to get the transitions for the transition table in the DFA's order of sigma
     * @param sigma of the DFA
     * @return String representation of that State's row in the transition table
     */
    public String toString(Set<Character> sigma) {
        String printString = super.toString() + "\t";

        // Iterate through the sigma set and check if the sigma character is in the state's
        // known alphabet
        for (char c : sigma) {
            for (int i = 0; i < alphabet.length; i++) {
                if (alphabet[i] == c) { // if the sigma character is in the state's alphabet
                    // add the to state and a space to the String
                    printString += getNextStateOnAlphabet(alphabet[i]) + " ";
                    break;
                }
                // If it isn't in the alphabet go insert a double space to represent
                // that there is no outgoing transition on that character
                if (alphabet[i] == '\u0000') {
                    printString += "  ";
                    break;
                }
            }
        }
        return printString;
    }
}