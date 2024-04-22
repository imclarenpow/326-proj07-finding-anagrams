import java.util.ArrayList;
import java.util.Scanner;

public class Anagram{
    
    public static void main(String[] args){
        ArrayList<String> rawWords = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            rawWords.add(sc.nextLine());
        }
        sc.close();
        String[] anaLines = inputHandler(rawWords);
        AnaObj[] ana = new AnaObj[anaLines.length];
        // initialise all anagram objects
        for(int i=0; i<ana.length; i++){
            ana[i] = new AnaObj(anaLines[i]);
        }
    }

    /** Class to handle input of lines
     * @author Isaac
     * @param ArrayList<String> input -> contains the raw stdin
     * @return String[] output -> contains only lines with alpha chars and only the alpha chars in lines
     */
    public static String[] inputHandler(ArrayList<String> input){

        ArrayList<String> output = new ArrayList<>();
        for(int i = 0; i<input.size(); i++){
            String temp = lineHandler(input.get(i));
            if(temp.trim().isEmpty()){
                input.remove(i);
                i--;
            }else{
                output.add(temp);
            }
        }

        return output.toArray(new String[output.size()]);
    }
    /** Aux Class for inputHandler()
     * @author Isaac
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
}