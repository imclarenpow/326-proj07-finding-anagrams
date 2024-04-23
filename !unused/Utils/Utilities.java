package Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Utilities {
    /** Simple Utilities for Organising the words.txt file in a way that will work better for the program
     * @author ChatGPT and Isaac
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> in = new ArrayList<String>();
        while(sc.hasNextLine()){
            in.add(sc.nextLine());
        }
        sc.close();
        if(args.length > 0 && args[0].equals("-a")){
            orderByLength(in);
        }else if(args.length > 0 && args[0].equals("-az")){
            makeAZ(in);
        }else if(args.length > 0 && args[0].equals("-i")){
            dictionaryIndeces(in);
        }
    }
    public static void orderByLength(ArrayList<String> input){
        // Sort lines by length
        Collections.sort(input, Comparator.comparingInt(String::length).reversed());
        
        // Print the sorted lines
        for (String line : input) {
            System.out.println(line);
        }
    }
    public static void makeAZ(ArrayList<String> input){
        // Sort characters within each line alphabetically
        for (int i = 0; i < input.size(); i++) {
            char[] chars = input.get(i).toCharArray();
            ArrayList<Character> charList = new ArrayList<>();
            for (char c : chars) {
                charList.add(c);
            }
            Collections.sort(charList);
            StringBuilder sortedLine = new StringBuilder();
            for (char c : charList) {
                sortedLine.append(c);
            }
            input.set(i, sortedLine.toString());
        }

        // Print the sorted lines
        for (String line : input) {
            System.out.println(line);
        }
    }
    public static void dictionaryIndeces(ArrayList<String> input){
        int temp = 0;
        for(int i=0; i<input.size(); i++){
            if(input.get(i).length()!=temp){
                temp = input.get(i).length();
                System.out.println(temp + " " + i);
            }
        }
    }
}
