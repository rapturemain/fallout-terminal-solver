package logic;

import java.util.*;

public class TerminalSolver {
    public TerminalSolver() {

    }

    private int length = -1;
    private List<String> allWords = new ArrayList<String>();
    private List<String> checkedWords = new ArrayList<String>();
    private List<Integer> matches = new ArrayList<Integer>();

    public void clear() {
        length = -1;
        allWords.clear();
        checkedWords.clear();
        matches.clear();
    }

    public void addWords(List<String> words) {
        for (String s : words) {
            if (length == -1) {
                length = s.length();
            } else {
                if (s.length() != length) {
                    throw new IllegalArgumentException("Length error");
                }
            }
        }
        allWords.addAll(words);
    }

    public void add(String word, int matches) {
        if (length == -1) {
            length = word.length();
        } else {
            if (word.length() != length) {
                throw new IllegalArgumentException("Length error");
            }
        }
        checkedWords.add(word);
        this.matches.add(matches);
        allWords = getPossibleSolutions();
    }

    public String getNext() {
        if (length == -1) {
            throw new UnsupportedOperationException("No word added");
        }
        int bestIndex = 0;
        int bestMatches = 0;
        for (int i = 0; i < allWords.size(); i++) {
            int matches = 0;
            if (checkedWords.contains(allWords.get(i))) {
                continue;
            }
            for (int j = 0; j < allWords.size(); j++) {
                if (i == j) {
                    continue;
                }
                int m = 0;
                for (int k = 0; k < length; k++) {
                    if (allWords.get(i).charAt(k) == allWords.get(j).charAt(k)) {
                        m++;
                    }
                }
                matches += m;
            }
            if (matches > bestMatches) {
                bestMatches = matches;
                bestIndex = i;
            }
        }
        return allWords.get(bestIndex);
    }

    public List<String> getAllWords() {
        return allWords;
    }

    public List<String> getAll() {
        return checkedWords;
    }

    public void remove(String str) {
        checkedWords.remove(str.toLowerCase());
    }

    public void remove(int index) {
        checkedWords.remove(index);
        matches.remove(index);
    }

    public List<String> getPossibleSolutions() {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < checkedWords.size(); i++) {
            String s = checkedWords.get(i);
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
}
