import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/** class for the dictionary that adds the info from the dictionary txt files
 * @author Isaac
 */
public class Dictionary {
    // file paths - change if you move the text files or change their names
    private String indecesFP = "Dictionary/dictionaryIndeces.txt";
    private String wordsFP = "Dictionary/wordsByLeng.txt";

    private HashMap<Integer, Integer> indeces = new HashMap<>();
    private HashMap<Character, Integer> template = new HashMap<>();
    private ArrayList<String> words = new ArrayList<>();
    /** constructor to construct all the datafields we want! */
    public Dictionary(){
        try {
            setIndeces();
            setWords();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find Indeces File");
            e.printStackTrace();
        }
        initTempHash();

    }
    /** initialises a template of the hashmap we will use for words */
    public void initTempHash(){
        for (char c = 'a'; c <= 'z'; c++) {
            template.put(c, 0);
        }
    }
    /** creates a HashMap of the indeces as to easily tell Anagram where to start looking for matches */
    public void setIndeces() throws FileNotFoundException{
        File file = new File(indecesFP);
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()){
            String[] temp = sc.nextLine().split(" ");
            int length = Integer.parseInt(temp[1]);
            int index = Integer.parseInt(temp[0]);
            indeces.put(index, length);
        }
        sc.close();
    }
    /** method that returns the index of the first line of the given length */
    public int getLengthStartingIndex(int size){
        return indeces.get(size);
    }
    /** initialises the words in the dictionary */
    public void setWords() throws FileNotFoundException{
        File file = new File(wordsFP);
        Scanner sc = new Scanner(file);
        while(sc.hasNextLine()){
            words.add(sc.nextLine());
        }
        sc.close();
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
}
