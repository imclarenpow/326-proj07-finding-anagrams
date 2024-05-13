import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
/**
 * Anagram Class
 * This class is the main class for the Anagram program
 * It takes in stdin and processes it to find the best possible anagram
 * The AnaObj class to store the word and the Dictionary class to store the dictionary
 * optimal() finds the best possible anagram
 * inputHandler() processes the input
 * lineHandler() processes the lines
 */
public class Anagram{
    public static void main(String[] args){
        // an ArrayList that holds the contents of the input
        ArrayList<String> rawWords = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            rawWords.add(sc.nextLine());
        }
        sc.close();

        // processed the rawWords
        String[] lines = inputHandler(rawWords);
        // clear to save memory - we're not going to use it again
        rawWords.clear();

        // flag to check if there has been an empty line seperating the words from the dictionary
        boolean lineCheck = false;
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> dictionary = new ArrayList<>();
        // scroller in case first line is empty
        int scroller = 0;
        for(String line : lines){
            if(!lineCheck){
                if(line.trim().isEmpty() && scroller!=0){
                    lineCheck = true;
                    continue;
                }
                words.add(line);
            }else{
                dictionary.add(line);
            }
            scroller++;
        }
        AnaObj[] ana = new AnaObj[words.size()];
        Dictionary[] d = new Dictionary[words.size()];
        // initialise all anagram objects
        for(int i=0; i<ana.length; i++){
            ana[i] = new AnaObj(words.get(i));
            d[i] = new Dictionary(dictionary);
            d[i].removeImpossibleWords(ana[i].getMap());
        }
        words = null; // clear words ArrayList to save memory // new dictionary class based on dictionary words
        dictionary = null; // clear dictionary ArrayList to save memory

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<ana.length; i++){
            //System.out.println("--------\nWord: " + ana[i].getWord());
            sb.append(ana[i].getWord()).append(": ");
            ArrayList<String> anagram = optimal(d[i], ana[i]);
            for(String s : anagram){
                sb.append(s).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());   
    }
    /** makes (or should) the best possible anagram
     * @param Dictionary d -> the dictionary object instance that is being used throughout the program
     * @param AnaObj ana -> the AnaObj instance that currently being worked on
     * @return returns an ArrayList of strings that are the combination of the best words to be used as an anagrams
     */
    public static ArrayList<String> optimal(Dictionary d, AnaObj ana){
        ArrayList<String> output = new ArrayList<>();
        recursionOpt(ana, d, output, new HashMap<>());
        return output;
    }
    public static void recursionOpt(AnaObj ana, Dictionary d, ArrayList<String> anagram, HashMap<Integer, Integer> failedWords){
        if(ana.charsLeft()==0){
            return;
        }
        for(int i = d.getLengthStartingIndex(ana.charsLeft()); i<d.dictionaryLength(); i++){
            if(anagram.contains(d.getWord(i)) || (failedWords.containsKey(i) && failedWords.get(i)==anagram.size()-1)){
                continue;
            }
            if(possibleAnagram(ana, d.getChars(i))){
                HashMap<Character, Integer> prospectiveWord = d.getChars(i);
                takeAll(ana, prospectiveWord);
                anagram.add(d.getWord(i));
                recursionOpt(ana, d, anagram, failedWords);
                if(ana.charsLeft()==0){
                    return;
                }
                anagram.remove(anagram.size()-1);
                reverseAll(ana, prospectiveWord);
            } else {
                failedWords.put(i, anagram.size());
            }
        }
    }
    public static void takeAll(AnaObj ana, HashMap<Character, Integer> prospectiveWord){
        for (char c : prospectiveWord.keySet()) {
            Integer prospectiveWordValue = prospectiveWord.get(c);
            ana.take(c, prospectiveWordValue);
        }
    }
    public static void reverseAll(AnaObj ana, HashMap<Character, Integer> prospectiveWord){
        for (char c : prospectiveWord.keySet()) {
            Integer prospectiveWordValue = prospectiveWord.get(c);
            ana.reverse(c, prospectiveWordValue);
        }
    }
    public static boolean possibleAnagram(AnaObj ana, HashMap<Character, Integer> word){
        for (char c = 'a'; c <= 'z'; c++) {
            Integer prospectiveWordValue = word.get(c);
            if (prospectiveWordValue > ana.check(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isNotPossible(int i, AnaObj ana, ArrayList<String> anagram, Dictionary d){
        int totalLeng = 0;
        for(int j=i; j<d.dictionaryLength(); j++){
            totalLeng += d.getWord(i).length();
        }
        if(totalLeng<ana.charsLeft()){
            return true;
        }
        return false;
    }

    /** Aux Class for inputHandler()
     * @param String input -> contains raw string from line passed from inputHandler
     * @return String output -> returns a string with only lowercase versions of the letters in the lines
     */
    public static String lineHandler(String input){
        StringBuilder output = new StringBuilder();

        for(int i=0; i<input.length(); i++){
            char c = input.charAt(i);
            if(Character.isLetter(c)){
                output.append(Character.toLowerCase(c));
            }
        }
        return output.toString();
    }
    
    /** Class to handle input of lines
     * @param ArrayList<String> input -> contains the raw stdin
     * @return String[] output -> contains only lines with alpha chars and only the alpha chars in lines
     */
    public static String[] inputHandler(ArrayList<String> input){

        ArrayList<String> output = new ArrayList<>();
        for(int i = 0; i<input.size(); i++){
            // ignores lines that start with # (as this indicates a comment in test files)
            if(input.get(i).startsWith("#")){
            }else{
                String temp = lineHandler(input.get(i));
                output.add(temp);
            }
        }

        return output.toArray(new String[output.size()]);
    }
}