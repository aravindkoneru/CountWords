import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;

public class CountWordsV2{

  ArrayList<String> totalWords = new ArrayList<String>();
  ArrayList<String> uniqueWords = new ArrayList<String>();
  HashMap<String, Integer> uniqueCounter = new HashMap<String, Integer>();

  public CountWordsV2(String fileName){
    File file = new File(fileName);
    try {

      Scanner inFile = new Scanner(file);

      while (inFile.hasNextLine()) {
        String phrase = inFile.nextLine();
        String[] temp = phrase.split(" ");
        for (String current : temp) {
          current = current.replaceAll("[\n,.?!:\";'']", "");
          if(current.contains("'")&&!(current.substring(0,1).equals("'"))){
            current = current.replace("'", "");
          }
          totalWords.add(current);
        }
        while(totalWords.contains("")){
          totalWords.remove("");
        }
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
  }

  public void setUniqueWords(){
    for(String x: totalWords){
      if(!uniqueWords.contains(x.toLowerCase())){
        uniqueWords.add(x.toLowerCase());
      }
    }
    while(uniqueWords.contains("-")){
      uniqueWords.remove("-");
    }
  }

  public ArrayList<String> getTotalWords(){
    return totalWords;
  }

  public ArrayList<String> getUniqueWords(){
    setUniqueWords();
    return uniqueWords;
  }

  public void containsPunctTotal(){
    String pattern = "[a-zA-Z]*";
    for(String x: totalWords){
      if(!x.matches(pattern)){
        System.out.println(x);
      }
    }
  }

  public void containsPunctUnique(){
    String pattern = "[a-zA-Z]*";
    for(String x: uniqueWords){
      if(!x.matches(pattern)){
        System.out.println(x);
      }
    }
  }

  public void countUnique(){
    for(String x: uniqueWords){
      uniqueCounter.put(x, 0);
    }

    for(String x: totalWords){
      String check = x.toLowerCase();
      if(!uniqueCounter.containsKey(check)){
        uniqueCounter.put(check, 1);
      } else{
        int currentCount = uniqueCounter.get(check);
        uniqueCounter.put(check, currentCount + 1);
      }
    }
  }

  public void printMap(){
    System.out.println("CALLED");
    for (Map.Entry<String, Integer> entry : uniqueCounter.entrySet()) {
      System.out.println(entry.getKey()+" : "+entry.getValue());
    }
  }

  public void sortAndPrint(){
    //HashMap<String, Integer> mapTemp = uniqueCounter.clone();
    int[] wordCounts = new int[uniqueCounter.size()];
    Set keys = uniqueCounter.keySet();
    Iterator mapIt = keys.iterator();
    ArrayList<String> finalValues = new ArrayList<String>();
    int index = 0;
    while(mapIt.hasNext()){
      wordCounts[index] = uniqueCounter.get(mapIt.next());
      index++;
    }
    Arrays.sort(wordCounts);
    for (int x = 0; x < uniqueCounter.size(); x++){
      mapIt = keys.iterator();
      while (mapIt.hasNext()){
        String temp = (String)mapIt.next();
        if (wordCounts[x] == uniqueCounter.get(temp)){
          String addVal = temp + " : " + wordCounts[x];
          if(!finalValues.contains(addVal)){
            finalValues.add(addVal);
          }
        }
      }
    }
    System.out.println("WORD : COUNT");
    int target = finalValues.size()-1;
    for(int x = 0; x < 30; x++){
      System.out.println(finalValues.get(target));
      target--;
    }
  }

  public void analyzeText(){
    setUniqueWords();
    countUnique();
    sortAndPrint();
  }

  public static void main(String[]args){
    CountWordsV2 dream = new CountWordsV2("dream.txt");
    dream.analyzeText();
  }
}
