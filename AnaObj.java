import java.util.HashMap;
/** Class that makes a line of text into an object
 * @author Isaac
 * size per object will be around 3000 bytes of memory:
 * 2080 bytes for the hashmap + 4 bytes for the int + 16 bytes for object overhead
 */
public class AnaObj {
    // map of all letters in line. This hashmap will take up 2080 bytes of memory
    private static HashMap<Character, Integer> letters = new HashMap<>();
    // keeping track of the characters left
    private int amtChars = 0;
    /**
     * constructor for object, inits a new hashmap and adds all characters from the inputted string
     * @param input -> line that we will make anagrams out of
     */
    public AnaObj(String input){
        initMap();
        storeChars(input);
    }
    /** initialises a HashMap with keys of every lowercase letter.
    */
    public void initMap(){
        for (char c = 'a'; c <= 'z'; c++) {
            letters.put(c, 0);
        }
    }
    /** adds all chars present in the line to the map.
     * @param input -> string thats been given to the constructor
    */
    public void storeChars(String input){
        char[] inChar = input.toCharArray();
        for(char c : inChar){
            int temp = letters.get(c);
            letters.put(c, temp+1);
            amtChars++;
        }
    }
    /** method to check amount of a char
     */
    public int check(char c){
        return letters.get(c);
    }
    /** Method to take a char 
     * Always run check before this method so you're actually taking a char that exists.
     * 
     * I'm not adding error handling in here to make the support class as simple as possible and to limit
     * uneeded resource use.
    */
    public void take(char c){
        int temp = letters.get(c);
        letters.put(c, temp-1);
        amtChars--;
    }
    /** method to return the total number of chars left
     */
    public int charsLeft(){
        return amtChars;
    }
}
