package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Set;

public class DFAState extends State {
    private HashMap<Character, String> transitionsOut;
    private boolean isStart;
    private boolean isFinal;
    //private char[] alphabet;


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
        //alphabet = new char[10];
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
        //alphabet = new char[10];
        //updateAlphabet(alphabetChar);
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

        Character transChar = Character.valueOf(alphabetChar);

        transitionsOut.put(transChar, toStateName);
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
     * Same method as printTransitionTableRow() above except it returns the string instead
     * of printing to the terminal
     * @return the toString that represents the State's row in the transition table
     */
    @Override
    public String toString() {

        String returnString = this.getName() + "\n";

        Set<Character> keySet = transitionsOut.keySet();

        for(Character c : keySet) {

            returnString = returnString.concat("\t" + c + " -> " + transitionsOut.get(c) + "\n");

        }

        return returnString;

    }

}