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
        // initialise all anagram objects
        for(int i=0; i<ana.length; i++){
            ana[i] = new AnaObj(words.get(i));
        }
        words = null; // clear words ArrayList to save memory
        Dictionary d = new Dictionary(dictionary); // new dictionary class based on dictionary words
        dictionary = null; // clear dictionary ArrayList to save memory

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<ana.length; i++){
            sb.append(ana[i].getWord()).append(": ");
            ArrayList<String> anagram = optimal(d, ana[i]);
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
    public static ArrayList<String> optimal(Dictionary d, AnaObj ana) {
        HashMap<Character, Integer> workingMap = ana.getTemplateMap();
        // Calculate total characters before entering the loop
        int totalChars = 0;
        int scroller = 0;
        // Store the starting index based on the length of the input AnaObj
        int starter = ana.charsLeft();
        ArrayList<String> output = new ArrayList<>();
        HashSet<String> tried = new HashSet<>();
        HashMap<Integer, HashSet<String>> tryIdx = new HashMap<>();
        int pass = 0;
        // Iterate through from the first possible index based on length (saves time)
        for (int i = d.getLengthStartingIndex(starter); i < d.dictionaryLength(); i++) {
            if (tryIdx.get(scroller) == null){ tryIdx.put(scroller, new HashSet<>()); }
            if (pass==d.dictionaryLength()){ output.clear(); break; }
            // if word has been used as first b4 or tried contains then continue
            if (tryIdx.get(scroller).contains(d.getWord(i))){ continue; }
            else{ tryIdx.get(scroller).add(d.getWord(i));
                if( tryIdx.get(scroller+1) != null){
                    tryIdx.get(scroller+1).clear();
                }else{
                    tryIdx.put(scroller+1, new HashSet<>());
                }
            }
            HashMap<Character, Integer> prospectiveWord = d.getChars(i);
            boolean wordFits = true;
            // Iterate through all the characters to make sure the word is possible
            for (char c : workingMap.keySet()) {
                Integer workingMapValue = workingMap.get(c);
                Integer prospectiveWordValue = prospectiveWord.get(c);
                if (workingMapValue + prospectiveWordValue > ana.check(c)) {
                    wordFits = false;
                    break;
                }
            }
            if (wordFits) {
                
                output.add(d.getWord(i)); // add to output // because its been used don't use again
                // Update working map based on the characters in the word
                for (char c : prospectiveWord.keySet()) {
                    Integer workingMapValue = workingMap.get(c);
                    Integer prospectiveWordValue = prospectiveWord.get(c);
                    workingMap.put(c, workingMapValue + prospectiveWordValue);
                }
                totalChars += d.getWord(i).length(); // Update total characters incrementally
            }
            // Check if the total characters exceed the remaining characters in AnaObj
            if (ana.getWord().length() != totalChars && i == d.dictionaryLength() - 1) {
                System.out.println("resetting, current words were: " + output.toString());
                if(output.size() == 0){
                    break;
                }
                // if scroller is 0 then anagram isn't possible
                output.clear(); // Clear the output and reset the working map
                workingMap = ana.getTemplateMap();
                ana.reset();
                tried.clear();
                scroller = 0; // add next first word to starting word
                i = d.getLengthStartingIndex(starter);
                totalChars = 0; // Reset the total characters to the initial value
                pass++;
                continue;
            }
            scroller++;
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