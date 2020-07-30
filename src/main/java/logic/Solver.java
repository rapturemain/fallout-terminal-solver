package logic;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solver {
    public Solver() throws IOException {
        if (allWords == null) {
            allWords = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(new File("src\\main\\java\\logic\\words.txt")));
            StringBuilder sb = new StringBuilder();
            int i = br.read();
            while (i != -1) {
                if (i == ' ') {
                    allWords.add(sb.toString().toLowerCase());
                    sb.delete(0, sb.length());
                } else {
                    sb.append((char) i);
                }
                i = br.read();
            }
            br.close();
        }
    }

    private static List<String> allWords = null;
    private List<String> list = new ArrayList<String>();
    private List<Integer> matches = new ArrayList<Integer>();
    private int length = -1;

    public List<String> getPossibleSolutions() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            int m = matches.get(i);
            if (result.size() == 0) {
                for (String w : allWords) {
                    if (w.length() != length) {
                        continue;
                    }
                    int mW = 0;
                    for (int j = 0; j < length; j++) {
                        if (s.charAt(j) == w.charAt(j)) {
                            mW++;
                        }
                    }
                    if (mW == m) {
                        result.add(w);
                    }
                }
            } else {
                Iterator<String> iterator = result.iterator();
                while (iterator.hasNext()) {
                    String w = iterator.next();
                    int mW = 0;
                    for (int j = 0; j < length; j++) {
                        if (s.charAt(j) == w.charAt(j)) {
                            mW++;
                        }
                    }
                    if (mW != m) {
                        iterator.remove();
                    }
                }
            }
        }
        return result;
    }

    public void add(String str, int matches) {
        if (length == -1) {
            length = str.length();
        } else {
            if (str.length() != length) {
                throw new IllegalArgumentException("String length doesn't match to the " + length);
            }
        }
        this.list.add(str.toLowerCase());
        this.matches.add(matches);
    }

    public List<String> getAll() {
        return list;
    }

    public void remove(String str) {
        list.remove(str.toLowerCase());
    }

    public void remove(int index) {
        list.remove(index);
        matches.remove(index);
    }

    public void clear() {
        list.clear();
        matches.clear();
        length = -1;
    }
}
