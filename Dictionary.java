import java.util.*;
/** class for the dictionary that adds the info from the dictionary txt files
 * @author Isaac
 */
public class Dictionary {

    private HashMap<Integer, Integer> indeces = new HashMap<>();
    private HashMap<Character, Integer> template = new HashMap<>();
    private ArrayList<String> words = new ArrayList<>();
    /** constructor to construct all the datafields we want! */
    public Dictionary(ArrayList<String> input){
        orderByLength(input);
        setIndeces(words);
        initTempHash();
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
    /** creates a HashMap of the indeces as to easily tell Anagram where to start looking for matches */
    public void setIndeces(ArrayList<String> input){
        int temp = 0;
        for(int i=0; i<input.size(); i++){
            if(input.get(i).length()!=temp){
                temp = input.get(i).length();
                indeces.put(temp, i);
            }
        }
    }
    /** method that returns the index of the first line of the given length */
    public int getLengthStartingIndex(int size){
        // if the length is bigger than the biggest number just return 0 to start from the start.
        Iterator<HashMap.Entry<Integer, Integer>> it = indeces.entrySet().iterator();
        HashMap.Entry<Integer, Integer> first = it.next();
        do{
            first = it.next();
        }while((first.getKey()==null));
        if(indeces.get(size)<first.getKey()){
            return 0;
        }else if(indeces.get(size)==null){
            getLengthStartingIndex(size+1);
        }
        return indeces.get(size);
    }
    /** @returns the word from the index specified */
    public String getWord(int i){
        return words.get(i);
    }
    /** @returns a HashMap of the chars of the specified word */
    public HashMap<Character, Integer> getChars(int i){
        HashMap<Character, Integer> output = template;
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
