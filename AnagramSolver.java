import java.util.*;

public class AnagramSolver {
    public static ArrayList<String> words = new ArrayList<>();
    public static ArrayList<String> dictionary = new ArrayList<>();
    public static void main(String[] args){
        stdIn();
        for(String word : words){
            System.out.println(getMap(word).toString());
        }
        System.out.println();
        for(String word : dictionary){
            System.out.println(getMap(word).toString());
        }
    }
    public static ArrayList<String> findAnagrams(){
        return null;
    }
    public static HashMap<Character, Integer> getMap(String word){
        HashMap<Character, Integer> output = new HashMap<>();
        for(char c: word.toCharArray()){
            if(!output.containsKey(c)){ output.put(c, 0); }
            int j = output.get(c);
            output.put(c, j+1);
        }
        return output;
    }
    public static void stdIn(){
        ArrayList<String> rawWords = new ArrayList<String>(); // an ArrayList that holds the contents of the input
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            rawWords.add(sc.nextLine());
        }
        sc.close();
        // processed the rawWords
        String[] lines = inputHandler(rawWords);
        rawWords = null; // clear rawWords ArrayList to save memory
        // flag to check if there has been an empty line seperating the words from the dictionary
        boolean lineCheck = false;
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
        Collections.sort(dictionary, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                if (s1.length() != s2.length()) {
                    return s2.length() - s1.length(); // sort by descending length
                }
                return s1.compareTo(s2); // if lengths are equal, sort alphabetically
            }
        });
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
