/*
Robert Freitag
8/6/18

This program is version 1 of The Word Game.

Rules:
- player 1 enters a five-letter word with no repeating letters
- player 2 then enters words to guess player 1's word (each guess must be a viable word)
- once player 2 guesses player 1's word, player 2 enters a word for player 1 to guess
- once player 1 guesses player 2's word, the winner is determined by the fewer number of guesses
  between player 1 and player 2
*/


import java.util.Scanner;

public class WordGame_Beta {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int p1GuessCount = 0;
        int p2GuessCount = 0;

        for (int playerNum = 1; playerNum <= 2 ; playerNum++) {
            String p1Word = getCheckWord(playerNum, scan);
        }

    }

    /**
     * getCheckWord -
     * This method calls getWord and wordValidation to check if player's word is valid, and returns
     * word if valid
     * @param playerNum - int indicating current player
     * @param scan - Scanner object
     * @return word - valid word (containing five nonrepeating letters)
     */
    public static String getCheckWord(int playerNum, Scanner scan){
        int valid = 0;
        String word = "";
        while (valid == 0) {
            word = getWord(playerNum, scan);
            System.out.println("Word entered: " + word);
            if (wordValidation(word)) valid = 1;
            else System.out.println("Invalid word.");
        }
        return word;
    }

    /**
     * getWord -
     * This method returns a word entered by a player to caller
     * @param playerNum - int indicating current player
     * @param scan - Scanner object
     * @return word - String containing five non-repeating letters
     */
    public static String getWord(int playerNum, Scanner scan){
        System.out.print("Player " + playerNum + ", enter your word.");
        System.out.println("(Word must contain five non-repeating letters): ");
        String word = scan.nextLine();
        return word;
    }

    /**
     * wordValidation -
     * This method checks if the word is valid, returns true if valid, false if invalid
     * @param word - String entered by player, passed from getWord method
     * @return boolean indicating if word is valid (i.e. five nonrepeating letters
     */
    public static boolean wordValidation(String word){
        if (word.length() == 5){
            char[] letters = word.toCharArray();
            for (int i = 0; i <= word.length()-1; i++) {
                for (int j = i; j <= word.length()-2; j++) {
                    if (letters[i] == letters[j+1]) return false;
                }
            }
            return true;
        }
        else return false;
    }
}
