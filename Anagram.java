import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
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
        // initialise all anagram objects
        for(int i=0; i<ana.length; i++){
            ana[i] = new AnaObj(words.get(i));
        }
        words = null; // clear words ArrayList to save memory
        Dictionary d = new Dictionary(dictionary); // new dictionary class based on dictionary words
        dictionary = null; // clear dictionary ArrayList to save memory

        for(int i=0; i<ana.length; i++){
            System.out.print(ana[i].getWord() + ": ");
            ArrayList<String> anagram = optimal(d, ana[i]);
            for(String s : anagram){
                System.out.print(s + " ");
            }
            System.out.println("");
        }   
    }
    /** makes (or should) the best possible anagram
     * @param Dictionary d -> the dictionary object instance that is being used throughout the program
     * @param AnaObj ana -> the AnaObj instance that currently being worked on
     * @return returns an ArrayList of strings that are the combination of the best words to be used as an anagrams
     */
    public static ArrayList<String> optimal(Dictionary d, AnaObj ana){
        // initialise working map
        HashMap<Character, Integer> workingMap = ana.getTemplateMap();
        ArrayList<String> output = new ArrayList<>();
        int startingIndeces = d.getLengthStartingIndex(ana.charsLeft());
        ArrayList<String> tried = new ArrayList<>();
        int totalChars = 0;
        
        // iterate through from the first possible index based on length
        for(int i = startingIndeces; i < d.dictionaryLength(); i++){
            // if tried and it doesn't work we won't try again
            if(tried.contains(d.getWord(i))){
                continue;
            }
            HashMap<Character, Integer> prospectiveWord = d.getChars(i);
            // iterate through all the chars to make sure that this word is possible
            for(char c : workingMap.keySet()){
                if(workingMap.get(c) + prospectiveWord.get(c) > ana.check(c)){
                    if(prospectiveWord.get(c) > ana.check(c)){
                        tried.add(d.getWord(i));
                    }
                    break;
                }
                // if got to the end without breaking
                if(c == 'z'){
                    // add word to the list of words that work
                    output.add(d.getWord(i));
                    if(output.size() == 1){
                        // commented out because it looks like we're allowed double ups of words
                        // tried.add(d.getWord(i));
                    }
                    for(char c2 : prospectiveWord.keySet()){
                        workingMap.put(c2, workingMap.get(c2) + prospectiveWord.get(c2));
                    }
                }
            }
            totalChars = workingMap.values().stream().mapToInt(Integer::intValue).sum();
            if(ana.charsLeft() < totalChars){
                output.clear();
                i = startingIndeces;
                workingMap = ana.getTemplateMap();
            }
        }
        return output;
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