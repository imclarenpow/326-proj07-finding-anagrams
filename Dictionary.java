import java.util.*;
/** class for the dictionary that adds the info from the dictionary txt files
 * @author Isaac
 */
public class Dictionary {

    private HashMap<Character, Integer> template = new HashMap<>();
    private ArrayList<String> words = new ArrayList<>();
    /** constructor to construct all the datafields we want! */
    public Dictionary(ArrayList<String> input){
        orderByLength(input);
        initTempHash();
    }
    public void removeImpossibleWords(HashMap<Character, Integer> input){
        for(int i=0; i<words.size(); i++){
            HashMap<Character, Integer> word = getChars(i);
            for(char c = 'a'; c <= 'z'; c++){
                if(input.get(c)<word.get(c)){
                    words.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
    public void orderByLength(ArrayList<String> input){
        // Sort lines by length
        Collections.sort(input, Comparator.comparingInt(String::length).reversed());
        // Print the sorted lines
        for (String line : input) {
            words.add(line);
        }
    }
    /** initialises a template of the hashmap we will use for words */
    public void initTempHash(){
        for (char c = 'a'; c <= 'z'; c++) {
            template.put(c, 0);
        }
    }
    /** method that returns the index of the first line of the given length */
    public int getLengthStartingIndex(int size){
        for(int i = 0; i<words.size(); i++){
            if(words.get(i).length()<=size){
                return i;
            }
        }
        return 0;
    }
    /** @returns the word from the index specified */
    public String getWord(int i){
        return words.get(i);
    }
    /** @returns a HashMap of the chars of the specified word */
    public HashMap<Character, Integer> getChars(int i){
        HashMap<Character, Integer> output = new HashMap<>(template);
        char[] temp = words.get(i).toCharArray();
        for(char c : temp){
            int j = output.get(c);
            output.put(c, j+1);
        }
        return output;
    }
    /** @returns the length of the dictionary! */
    public int dictionaryLength(){
        return words.size();
    }
    public boolean possibleAnagram(HashMap<Character, Integer> input, int index){
        for(char c : input.keySet()){
            if(input.get(c)>getChars(index).get(c)){
                return false;
            }
        }
        return true;
    }
}
