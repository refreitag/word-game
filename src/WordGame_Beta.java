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


import java.util.Arrays;
import java.util.Scanner;

public class WordGame_Beta {
    public static void main(String[] args) {

        //declarations
        Scanner scan = new Scanner(System.in);
        int p1GuessCount = 0;
        int p2GuessCount = 0;
        int roundCount = 0;
        boolean p1Correct = false;
        boolean p2Correct = false;
        String[][] history = new String[50][4];

        for (int i = 0; i < 50; i++) { //fill history array with blanks
            for (int j = 0; j < 4; j++) {
                history[i][j] = "";
            }
        }

        welcome(); //display welcome message and rules

        System.out.println("Both players must first enter their master word (the word their opponent " +
                "will be trying to guess).");
        System.out.println();
        String p1MasterWord = getCheckWord(1, scan); //set player 1 master word
        String p2MasterWord = getCheckWord(2, scan); //set player 2 master word

        System.out.println("Now players will guess their opponents master word.");

        while (p1Correct == false && p2Correct == false) {
            int result = executeRound(history, scan, p1MasterWord, p2MasterWord, roundCount, p1GuessCount, p2GuessCount);
            roundCount++;

            if (result == 1) {
                p1Correct = true;
                System.out.println("Player 1 wins!");
                System.out.println("Player 1's master word: " + p1MasterWord);
            }
            else if (result == 2) {
                p2Correct = true;
                System.out.println("Player 2 wins!");
                System.out.println("Player 2's master word: " + p2MasterWord);
            }
            else if (result == 3) {
                p1Correct = true;
                p2Correct = true;
                System.out.println("Tie!");
            }
            else {
                p1Correct = false;
                p2Correct = false;
            }
        }


    } //end of main

    /**
     * executeRound -
     * This method executes one round, updates the guess-count history, round count and guess count
     * @param history - 2D String array of both players guesses and correct letter count
     * @param scan - Scanner object
     * @param p1MasterWord - player 1's master word as String
     * @param p2MasterWord - player 2's master word as String
     * @param roundCount - int indicating the round count
     * @param p1GuessCount - int indicating player 1's guess count
     * @param p2GuessCount - int indicating player 2's guess count
     * @return result - int indicating the result of the round (ie. both correct, p1 correct, p2 correct, neither correct)
     */
    public static int executeRound(String[][] history, Scanner scan, String p1MasterWord,
                                    String p2MasterWord, int roundCount, int p1GuessCount,
                                    int p2GuessCount){

        int result;
        boolean p1Correct = false;
        boolean p2Correct = false;

        String p1Guess = getCheckWord(1, scan);
        p1GuessCount++;
        String p1Count = Integer.toString(getCorrectLetters(p2MasterWord, p1Guess));
        if (p1Guess.equals(p2MasterWord)) p1Correct = true; // if p1 guess is correct

        String p2Guess = getCheckWord(2, scan);
        p2GuessCount++;
        String p2Count = Integer.toString(getCorrectLetters(p1MasterWord, p2Guess));
        if (p2Guess.equals(p1MasterWord)) p2Correct = true; //if p2 guess is correct

        updateGuessCountHistory(history, roundCount, p1Guess, p1Count, p2Guess, p2Count);
        displayHistory(history, roundCount);

        if (p1Correct == true && p2Correct == false) result = 1; //p1 wins
        else if (p1Correct == false && p2Correct == true) result = 2; //p2 wins
        else if (p1Correct == true && p2Correct == true) result = 3; //tie
        else result = 0;

        return result;
    }

    /**
     * displayHistory -
     * This method displays the guess-count history for each player
     * @param history - 2D String array of both players guesses and correct letter count
     * @param roundCount - int indicating the round number
     */
    public static void displayHistory(String[][] history, int roundCount){
        System.out.println();
        System.out.println("Player 1   |   Player 2");
        System.out.println("----------   ----------");
        for (int i = 0; i <= roundCount; i++) {
            System.out.print(history[i][0] + ": " + history[i][1] + "   |   ");
            System.out.println(history[i][2] + ": " + history[i][3]);
        }
    }

    /**
     * updateGuessCountHistory -
     * This method updates the guess-count history array for each player
     * @param history - 2D String array of both players guesses and correct letter count
     * @param roundCount - int indicating the round number
     * @param p1Guess - player 1's most recent guess as String
     * @param p1Count - num of correct letters in p1's guess as String
     * @param p2Guess - player 2's most recent guess as String
     * @param p2Count - num of correct letters in p2's guess as String
     */
    public static void updateGuessCountHistory(String[][] history, int roundCount,
                                               String p1Guess, String p1Count, String p2Guess, String p2Count){
        //place guesses and corresponding counts into history array
        history[roundCount][0] = p1Guess;
        history[roundCount][1] = p1Count;
        history[roundCount][2] = p2Guess;
        history[roundCount][3] = p2Count;
    }

    /**
     * welcome -
     * This method displays the rules of the game.
     */
    public static void welcome() {
        System.out.println("Welcome to The Word Game!");
        System.out.println("The rules are simple:");
        System.out.println("Each player chooses a word with five non-repeating letters, and the goal " +
                "of the game is for each player to guess their opponents word.");
        System.out.println("A round consists of each player guessing their opponents word, followed " +
                "by the console displaying the number of correct letters in each guess.");
        System.out.println("The game ends when both players have successfully guessed their opponents word" +
                ", and the winner is determined by whichever player guessed the correct word in the fewest" +
                "number of rounds.");
        System.out.println();
        System.out.println("------------------");
        System.out.println();

    }

    /**
     * getCorrectLetters -
     * This method compares the players guess to their opponents master word and returns the
     * number of correctly guessed letters
     *
     * @param masterWord - master word for p1 or p2 as String
     * @param guess      - p2 or p1 guess as String
     * @return count - number of matching letters between guess and masterWord as int
     */
    public static int getCorrectLetters(String masterWord, String guess) {
        int count = 0;

        //search through master word letters
        for (int i = 0; i < masterWord.length(); i++) {
            //search through guess letters
            for (int j = 0; j < guess.length(); j++) {
                //if a guess letter matches a master code letter
                if (guess.charAt(j) == masterWord.charAt(i)) {
                    count++;
                    char[] masterWordArr = masterWord.toCharArray(); //create char array of masterword
                    masterWordArr[i] = ' ';  //replace correct letter in array with a blank
                    masterWord = new String(masterWordArr);  //replace masterword with updated blanks

                    char[] guessWordArr = guess.toCharArray(); //create char array of guess word
                    //replace correct letter match with lowercase version
                    guessWordArr[j] = Character.toLowerCase(guessWordArr[j]);
                    guess = new String(guessWordArr); //replace guess with updated lowercase colors
                }
            }
        }
        return count;
    }

    /**
     * getCheckWord -
     * This method calls getWord and wordValidation to check if player's word is valid, and returns
     * word if valid
     *
     * @param playerNum - int indicating current player
     * @param scan      - Scanner object
     * @return word - valid word (containing five nonrepeating letters)
     */
    public static String getCheckWord(int playerNum, Scanner scan) {
        int valid = 0;
        String word = "";
        while (valid == 0) {
            word = getWord(playerNum, scan);
            //System.out.println("Word entered: " + word);
            if (wordValidation(word)) valid = 1;
            else System.out.println("Invalid word.");
        }
        return word;
    }

    /**
     * getWord -
     * This method returns a word entered by a player to caller
     *
     * @param playerNum - int indicating current player
     * @param scan      - Scanner object
     * @return word - String containing five non-repeating letters
     */
    public static String getWord(int playerNum, Scanner scan) {
        System.out.print("Player " + playerNum + ", enter your word.");
        System.out.println("(Word must contain five non-repeating letters): ");
        String word = scan.nextLine().toLowerCase();
        return word;
    }

    /**
     * wordValidation -
     * This method checks if the word is valid, returns true if valid, false if invalid
     *
     * @param word - String entered by player, passed from getWord method
     * @return boolean indicating if word is valid (i.e. five nonrepeating letters
     */
    public static boolean wordValidation(String word) {
        if (word.length() == 5) {
            char[] letters = word.toCharArray();
            for (int i = 0; i <= word.length() - 1; i++) {
                for (int j = i; j <= word.length() - 2; j++) {
                    if (letters[i] == letters[j + 1]) return false;
                }
            }
            return true;
        } else return false;
    }
}
