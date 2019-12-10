package com.example.cs125finalproject;

//Does not check for repeat guesses
//Assumes Mastermind text input makes it all numbers
public class Checker {

    /*
     *  Note on syntax:
     *      "code" (an int) is used to refer to user input for Mastermind methods.
     *      "text" (a String) is used to refer to user input for Jotto methods.
     */

    /**
     * Check that all of the characters in string are part of the alphabet.
     * False for: "nmlos3", "what's up?", "hi hello", "\w{}"
     * True for: "hEllo" (Not case sensitive)
     * @param text that user inputs
     * @return boolean
     */
    public boolean allLetters(String text) {
        char[] chars = text.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that size of string is equal to length (Jotto).
     * False: (cat, 4)
     * True: (house, 5)
     * @param text user input
     * @param length size that user selected
     * @return boolean
     */
    public boolean correctLength(String text, int length) {

        if(text.length() != length) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check that letters do not repeat in text (Jotto).
     * True: house, dorm, glyph
     * False: boolean, hello, tree
     * @param text user's input
     * @return boolean
     */
    public boolean noRepeat(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            for (int x = i + 1; x < text.length(); x++) {
                if (text.charAt(i) == text.charAt(x)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds the number of letters in text that is also in the answer (Jotto).
     * Note: not location specific like mastermind is!
     * (house, glyph) returns 0; (lions, house) returns 2; (cat, act) returns 3
     * @param text user input
     * @param answer the actual word
     * @return int
     */
    public int numberCorrect(String text, String answer) {
        int count = 0;
        char[] charArray = new char[answer.length()];

        for (int i = 0; i < answer.length(); i++) {
            charArray[i] = answer.charAt(i);
        }
        for (int i = 0; i < text.length(); i++) {
            for (int x = 0; x < charArray.length; x++) {
                if (charArray[x] == text.charAt(i)) {
                    charArray[x] = '!';
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Finds the number of digits in the right place (Mastermind).
     * (1234, 1423) returns 1; (1234, 1234) returns 4; (9876, 9123) returns 1
     * @param code user input
     * @param answer the actual code
     * @return int
     */
    public int numberCorrectMastermind(String code, String answer) {
        int count = 0;
        int length = code.length();
        for (int i = 0; i < length; i++) {
            if (code.charAt(i) == answer.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}
