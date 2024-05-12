import java.util.*;

public class AnagramSolver {
    private static ArrayList<String> dictionary = new ArrayList<>();
    private static ArrayList<String> words = new ArrayList<>();
    private static ArrayList<String> anagram = new ArrayList<>();
    private static HashMap<Character, Integer> charMap = new HashMap<>();
    public static void main(String[] args){
        stdIn();
        for(int i = 0; i < words.size(); i++){
            anagram = new ArrayList<>();
            HashMap<Character, Integer> startMap = makeMap("");
            HashMap<Character, Integer> goalMap = makeMap(words.get(i));
            aStar(startMap, goalMap, words.get(i));
        }
    }

    public static void aStar(HashMap<Character, Integer> startMap, HashMap<Character, Integer> goalMap, String word){
        int lengthWanted = mapLen(goalMap);
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        Map<HashMap<Character, Integer>, Integer> visited = new HashMap<>();
        Map<HashMap<Character, Integer>, State> cameFrom = new HashMap<>();
        pq.add(new State(startMap, "", 0));
        visited.put(startMap, 0);
        while(!pq.isEmpty()){
            State curr = pq.poll();
            if(curr.map.equals(goalMap)){
                ArrayList<String> path = new ArrayList<>();
                while(curr != null){
                    path.add(curr.word);
                    curr = cameFrom.get(curr.map);
                }
                Collections.reverse(path);
                for(String s : path){
                    anagram.add(s);
                }
                return;
            }
            for(int i = 0; i < dictionary.size(); i++){
                HashMap<Character, Integer> next = new HashMap<>(curr.map);
                next = addWord(next, dictionary.get(i));
                System.out.println(next.toString());
                int newCost = curr.cost + dictionary.get(i).length();
                if(!visited.containsKey(next) || newCost < visited.get(next)){
                    visited.put(next, newCost);
                    int priority = newCost + Math.abs(mapLen(next) - lengthWanted);
                    pq.add(new State(next, dictionary.get(i), priority));
                    cameFrom.put(next, curr);
                }
            }
        }
    }

    public static HashMap<Character, Integer> addWord(HashMap<Character, Integer> input, String word){
        for(char c : word.toCharArray()){
            if(input.containsKey(c)){
                input.put(c, input.get(c)+1);
            }else{
                input.put(c, 1);
            }
        }
        return input;
    }

    public static int mapLen(HashMap<Character, Integer> map){
        int sum = 0;
        for(int i : map.values()){
            sum += i;
        }
        return sum;
    }

    public static HashMap<Character, Integer> makeMap(String word){
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c : word.toCharArray()){
            if(map.containsKey(c)){
                map.put(c, map.get(c)+1);
            }else{
                map.put(c, 1);
            }
        }
        return map;
    }
    /* simple stdIn() method to sort into needed arraylists etc. put data in right place and such */
    public static void stdIn(){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> rawInput = new ArrayList<>();
        while(sc.hasNextLine()){
            rawInput.add(sc.nextLine());
        }
        sc.close();
        boolean dict = false;
        for(String line : rawInput){
            if(line.trim().startsWith("#")){continue;}
            if(line.trim().isEmpty() && rawInput.size()!=0 && !dict){
                dict = true;
                continue;
            }
            if(dict){
                dictionary.add(line);
            }else{
                words.add(line);
            }
        }
        Collections.sort(dictionary, (s1, s2) -> Integer.compare(s2.length(), s1.length()));
    }
    static class State {
        HashMap<Character, Integer> map;
        String word;
        int cost;
        public State(HashMap<Character, Integer> map, String word, int cost){
            this.map = map;
            this.word = word;
            this.cost = cost;
        }
    }
}
