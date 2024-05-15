import java.util.*;

public class AnagramSolver {
    public static ArrayList<String> words = new ArrayList<>();
    public static ArrayList<String> dictRaw = new ArrayList<>();
    public static ArrayList<HashMap<Character, Integer>> dictionary = new ArrayList<>();
    
    // Maintain a set of used words for each position in the output sequence
    public static ArrayList<HashSet<String>> usedWords = new ArrayList<>();
    
    public static void main(String[] args){
        stdIn();
        for(String word : dictRaw){
            dictionary.add(getMap(word));
        }
        
        for(String word: words){
            HashMap<Character, Integer> workingMap = getMap(word);
            ArrayList<String> prunedWords = new ArrayList<>();
            ArrayList<HashMap<Character, Integer>> pruned = pruneDictionary(workingMap, prunedWords);
            if(pruned.isEmpty()){
                System.out.println(word + ":");
                continue;
            }
            resetUsedWords(word.length());
            ArrayList<String> anagrams = findAnagrams(new HashMap<Character,Integer>(workingMap), pruned, new ArrayList<String>(), prunedWords);
            System.out.print(word + ": ");
            for(String anagram : anagrams){
                System.out.print(anagram + " ");
            }
            System.out.println();
            
        }

    }
    
    public static ArrayList<String> findAnagrams(HashMap<Character, Integer> workingMap, ArrayList<HashMap<Character, Integer>> pruned, ArrayList<String> output, ArrayList<String> prunedWords){
        if(workingMap.isEmpty()){
            return output;
        } int j = 0;
        if(output.size() != 0){
            j = prunedWords.indexOf(output.get(output.size()-1));
        }
        for(int i = j; i < pruned.size(); i++){
            String currentWord = prunedWords.get(i);
            //System.out.println(output.toString() + " " + pruned.get(i).toString() + " " + currentWord + " " + workingMap.toString());
            // Check if the current word has been used for this position
            if (output.size() <= usedWords.size() && !usedWords.get(output.size()).contains(currentWord)) {
                if(isPossible(workingMap, pruned.get(i))){
                    workingMap = remove(workingMap, pruned.get(i));
                    usedWords.get(output.size()).add(currentWord);
                    output.add(currentWord);
                    output = findAnagrams(workingMap, pruned, output, prunedWords);
                    if(workingMap.isEmpty()){
                        return output;
                    }
                    if(!output.isEmpty()){
                        usedWords.get(output.size()-1).remove(currentWord);
                        workingMap = reverse(workingMap, getMap(output.get(output.size()-1)));
                        output.remove(output.size()-1);
                        // Unmark the current word as used for this position during backtracking
                        
                    }
                }
            }
        }
        return output;
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
    public static void resetUsedWords(int until){
        if(!usedWords.isEmpty()){
            usedWords.clear();
        }
        // Initialize the set of used words for each position
        for (int i = 0; i < until; i++) {
            usedWords.add(new HashSet<>());
        }
    }
    public static boolean isPossible(HashMap<Character, Integer> workingMap, HashMap<Character, Integer> dicWord){
        for(char c : dicWord.keySet()){
            if(!workingMap.containsKey(c)){
                return false;
            }else if(workingMap.get(c) < dicWord.get(c)){
                return false;
            }
        }
        return true;
    }
    // the two following methods remove and reverse the dictionary from the workingMap
    public static HashMap<Character, Integer> remove(HashMap<Character, Integer> workingMap, HashMap<Character, Integer> dictMap){
        for(char c : dictMap.keySet()){
            int j = workingMap.get(c);
            workingMap.put(c, j-dictMap.get(c));
            if(workingMap.get(c) == 0){
                workingMap.remove(c);
            }
        }
        return workingMap;
    }
    public static HashMap<Character, Integer> reverse(HashMap<Character, Integer> workingMap, HashMap<Character, Integer> dictMap){
        for(char c : dictMap.keySet()){
            if(!workingMap.containsKey(c)){
                workingMap.put(c, 0);
            }
            int j = workingMap.get(c);
            workingMap.put(c, j+dictMap.get(c));
        }
        return workingMap;
    }
    // make a pruned version of the dictionary for the workingMap (makes iteration faster)
    public static ArrayList<HashMap<Character, Integer>> pruneDictionary(HashMap<Character, Integer> workingMap, ArrayList<String> prunedWords){
        ArrayList<HashMap<Character, Integer>> output = new ArrayList<>();
        for(int i=0; i<dictionary.size(); i++){
            if(isPossible(workingMap, dictionary.get(i))){
                output.add(dictionary.get(i));
                prunedWords.add(dictRaw.get(i));
            }
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
                dictRaw.add(line);
            }
            scroller++;
        }
        Collections.sort(dictRaw, new Comparator<String>() {
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
