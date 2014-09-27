import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.ArrayList;

public class CountWords {

	private ArrayList<String> totalWords = new ArrayList<String>();
	private ArrayList<String> unique = new ArrayList<String>();

	public CountWords(String FILE_NAME) {
		File file = new File(FILE_NAME);
		try {

			Scanner inFile = new Scanner(file);

			while (inFile.hasNextLine()) {
				String phrase = inFile.nextLine();
				String[] temp = phrase.split(" ");
				for (String current : temp) {
					current = current.replaceAll("[\n,.?!:\";]", "");
					if(current.contains("'")&&!(current.substring(0,1).equals("'"))){
						current = current.replace("'", "");
					}
					totalWords.add(current);
				}
			}

			while (totalWords.contains("")) {
				totalWords.remove("");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}

	public void tester() {
		getUnique();
		System.out.println("Total Words: " + totalWords.size());
		System.out.println("Unique Size: " + unique.size());
		printResults(wordCounter());
		System.out.println(unique);
	}

	public void getUnique() {
		for (String x : totalWords) {
			if (!(unique.contains(x.toLowerCase()))) {
				unique.add(x.toLowerCase());
			}
		}
		
		while(unique.contains("-")){
			unique.remove("-");
		}
	}

	public int[] wordCounter() {
		int[] wordCount = new int[unique.size()];
		for (int x = 0; x < wordCount.length; x++) {
			int counter = 0;
			String keyWord = unique.get(x);
			for (String lookThrough : totalWords) {
				if (keyWord.equals(lookThrough.toLowerCase())) {
					counter++;
				}
			}
			wordCount[x] = counter;
		}
		return wordCount;
	}

	public void printResults(int[] wordCount) {
		Map<String, Integer> connected = new HashMap<String, Integer>();
		ArrayList temp = new ArrayList();
		for (int x = 0; x < wordCount.length; x++) {
			connected.put(unique.get(x), wordCount[x]);
		}
		int[] sorted = wordCounter();
		Arrays.sort(sorted);
		for (int x = sorted.length - 1; x >= 0; x--) {
			int current = sorted[x];
			for (Entry<String, Integer> entry : connected.entrySet()) {
				if (entry.getValue() == current) {
					temp.add(entry);
				}
			}
		}
		ArrayList printVals = new ArrayList();
		for (Object x : temp) {
			if (!(printVals.contains(x))) {
				printVals.add(x);
			}
		}

		int counter = 0;
		for (Object x : printVals) {
			if (counter < 30) {
				System.out.println(x);
				counter++;
			} else {
				break;
			}
		}
	}

	public static void main(String[] args) {
		CountWords test = new CountWords("dream.txt");
		test.tester();

	}
}
