import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

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
        words.clear(); // clear words ArrayList to save memory
        Dictionary d = new Dictionary(dictionary); // new dictionary class based on dictionary words
        dictionary.clear(); // clear dictionary ArrayList to save memory
        
        debugSupportClasses(d, ana);

        for(int i = 0; i<ana.length; i++){
            anagramHandler(d, ana[i]);
            System.out.println();
        }
            
    }
    // TODO: this method doesn't work correctly, the rest of the code isn't too shabby though
    // Supposed to find anagrams based on the dictionary and the anagram object
    public static void anagramHandler(Dictionary d, AnaObj ana){
        // allows method to see the map of Anagram object
        HashMap<Character, Integer> temp = ana.getMap();
        System.out.println("Anagram Method word: " + ana.getWord());
        ArrayList<String> output = new ArrayList<>();
        // get the starting index of the dictionary based on the length of the anagram (skips unneeded searching)
        int startingIndeces = d.getLengthStartingIndex(ana.charsLeft());
        System.out.println("Starting Search from Index: " + startingIndeces);
        // loop through the dictionary to find possible anagrams
        for(int j = startingIndeces; j<d.dictionaryLength(); j++){
            HashMap<Character, Integer> prospectiveWord = d.getChars(j);
            // if this word is a prospective anagram, 
            if(d.possibleAnagram(temp, j)){
                System.out.println(d.getWord(j) + " - has been deemed possible");
                for(char c : prospectiveWord.keySet()){
                    ana.take(c, prospectiveWord.get(c));
                }
                output.add(d.getWord(j));
                // sets j to look at the index of the next word of the same length
                temp = ana.getMap();
                j = d.getLengthStartingIndex(ana.charsLeft());
            }
            
        }
        for(String s : output){
            System.out.print(s + " ");
        }
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
    public static void debugSupportClasses(Dictionary d, AnaObj[] ana){
        System.out.println("Words:");
        for(int i = 0; i<ana.length; i++){
            System.out.println(ana[i].getWord());
        }
        System.out.println("----\nDictionary:");
        for(int i = 0; i<d.dictionaryLength(); i++){
            System.out.println(d.getWord(i));
        }
        System.out.println("----\nThe Indeces for Sizes!");
        for(int i = 31; i>20; i--){
            System.out.println("Length " + i + " at Index " + d.getLengthStartingIndex(i));
        }
        System.out.println("----");
    }
}