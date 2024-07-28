import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class TNode{
    boolean isEoW;
    TNode []children;
    public TNode(){
        this.isEoW=false;
        this.children=new TNode[26];
    }   
}

class Trie{
    TNode root;
    public Trie() {
        this.root=new TNode();
    }

    public boolean insertWord(String word) {
        TNode temp=root;
        for(char ch:word.toCharArray()){
            int idx=ch-'a';
            if(temp.children[idx]==null){
                TNode nn= new TNode();
                temp.children[idx]=nn;
            }
            temp=temp.children[idx];
        }
        temp.isEoW=true;
        return temp.isEoW;
    }
    
    public boolean searchWord(String word) {
        TNode temp=root;
        for(char ch:word.toCharArray()){
            int index=ch-'a';
            if(temp.children[index]==null){
               return false;
            }
            temp=temp.children[index];
        }
       return  temp.isEoW;
    }    

   public List<String> getWordsWithPrefix(String prefix) {
    TNode temp = root;
    for (char ch : prefix.toCharArray()) {
        int idx = ch - 'a';
        if (temp.children[idx] == null) {
            return new ArrayList<>();
        }
        temp = temp.children[idx];
    }
    List<String> ans = new ArrayList<>();
    StringBuilder path = new StringBuilder(prefix); 
    h(temp, path, ans);
    return ans;
}

    public List<String> getAllWords() {
        List<String> ans = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        h(root, path, ans);
        return ans;
    }

    void h(TNode root, StringBuilder path, List<String> ans) {
        if (root.isEoW == true) {
            ans.add(path.toString());
        }
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                char ch = (char) (i + 'a');
                path.append(ch);
                h(root.children[i], path, ans);
                path.setLength(path.length() - 1);
            }
        }
    }
}
    
class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Trie trieTree = new Trie();
        String fileName = "words.txt"; // replace with your file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                trieTree.insertWord(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Trie trieTree = new Trie();
        while(true){
            System.out.println("1 : Add a Word");
            System.out.println("2 : Search a Word in Trie");
            System.out.println("3 : Print all Words which are present");
            System.out.println("4 : Print all words with given Prefix");
            System.out.println("5 : Exit");
            System.out.println("Enter the number of operations you want to perform");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the word to add: ");
                    String wordToAdd = sc.next();
                    boolean insert = trieTree.insertWord(wordToAdd);
                    System.out.println(insert ? "Word Inserted!" : "Word not Inserted.");
                    break;
                case 2:
                    System.out.print("Enter the word to search: ");
                    String wordToSearch = sc.next();
                    boolean found = trieTree.searchWord(wordToSearch);
                    System.out.println(found ? "Word found!" + wordToSearch : "Word not found.");
                    break;
                case 3:
                        System.out.println("Words in the Trie:");
                        List<String> words = trieTree.getAllWords();
                        for(String word : words) {
                            System.out.println(word);
                        }
                        break;
               case 4:
                        System.out.print("Enter the prefix: ");
                        String prefix = sc.next();
                        System.out.println("Words with prefix '" + prefix + "':");
                        List<String> wordsWithPrefix = trieTree.getWordsWithPrefix(prefix);
                        for(String word : wordsWithPrefix) {
                            System.out.println(word);
                        }
                        break;
                case 5:
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        
    }
}