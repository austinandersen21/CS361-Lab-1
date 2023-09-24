package fa.dfa;

import fa.State;
import java.util.Arrays;
import java.util.HashMap;

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
     * Adds an alphabet character to the state's alphabet array. Then, sorts the array
     * incrementally in order to help the print functions of each state match for a full
     * DFA transition table print
     *
     * @param alphabetChar
     */
    public void updateAlphabet(char alphabetChar) {
        for (int i = 0; i < alphabet.length; i++) {
            // Iterate through the array until the first null character
            // Replace the null character with the new alphabet character
            if (alphabet[i] == '\u0000') {
                alphabet[i] = alphabetChar;

                // Because the alphabet array is initialized empty, need a temp array for sorting
                // Create a temp array and copy all non-null characters into the temp array
                char[] tempArray = new char[i + 1];
                System.arraycopy(alphabet, 0, tempArray, 0, i + 1);

                // Sort the temp array
                Arrays.sort(tempArray);

                // Copy the sorted temp ardray back into the alphabet array
                System.arraycopy(tempArray, 0, alphabet, 0, i + 1);

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
     * Prints the state and all possible transition states. If the states
     * alphabet is kept updated with all possible states in the DFA then it
     * will print a blank space instead of a state name.
     */
    public void printTransitionTableRow() {
        // Print the states name with a tab after
        System.out.print(super.toString() + "\t");
        // Iterate through the alphabet array until the first null character
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == '\u0000') {
                break;
                // Print a blank space on alphabet characters that don't have a transition
                // for this state
            } else if (getNextStateOnAlphabet(alphabet[i]) == null) {
                System.out.print("  ");
                // Print the name of the next state if a transition exists for the character
            } else {
                System.out.print(getNextStateOnAlphabet(alphabet[i]) + " ");
            }
        }
        System.out.println();
    }

    /**
     * Method used for testing if the alphabet array was being updated correctly.
     * Prints all characters in the alphabet to the terminal until the first null
     * character is reached. Adds white space in between each character printed
     */
    public void printAlphabet() {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == '\u0000') {
                System.out.println();
                return;
            } else {
                System.out.print(alphabet[i] + "  ");
            }
        }
        System.out.println();
    }
}