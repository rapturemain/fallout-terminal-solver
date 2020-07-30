package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Console {
    public static void main(String[] args) throws IOException {
        Solver solver = new Solver();
        TerminalSolver tSolver = new TerminalSolver();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Commands:  \"-clear\", \"-get\", \"-remove <word/index>\", \"-add <word> <matches>\",\n" +
                "           \"-solve\", \"-autosolve <true/false>\", \"-terminal <true/false>\", \"-addWords <words>\", \"-exit\"");
        String s = br.readLine();
        boolean autoSolve = false;
        boolean terminalMode = true;
        do {
            String[] parts = s.split(" ");
            if (parts[0].equals("-clear")) {
                solver.clear();
                tSolver.clear();
                System.out.println("Cleaned");
            } else if (parts[0].equals("-get")) {
                List<String> list = terminalMode ? tSolver.getAll() : solver.getAll();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(i + ": " + list.get(i));
                }
            } else if (parts[0].equals("-remove")) {
                if (terminalMode) {
                    if (parts[1].matches("[0-9][0-9]*")) {
                        tSolver.remove(Integer.parseInt(parts[1]));
                        System.out.println("Removed at " + parts[1]);
                    } else {
                        tSolver.remove(parts[1]);
                        System.out.println("Removed " + parts[1]);
                    }
                } else {
                    if (parts[1].matches("[0-9][0-9]*")) {
                        solver.remove(Integer.parseInt(parts[1]));
                        System.out.println("Removed at " + parts[1]);
                    } else {
                        solver.remove(parts[1]);
                        System.out.println("Removed " + parts[1]);
                    }
                }
                if (autoSolve) {
                    if (terminalMode) {
                        solve(tSolver);
                    } else {
                        solve(solver);
                    }
                }
                if (terminalMode) {
                    System.out.println("Up next: " + tSolver.getNext());
                }
            } else if (parts[0].equals("-solve")) {
                if (terminalMode) {
                    solve(tSolver);
                } else {
                    solve(solver);
                }
            } else if (parts[0].equals("-add")) {
                if (terminalMode) {
                    tSolver.add(parts[1], Integer.parseInt(parts[2]));
                } else {
                    solver.add(parts[1], Integer.parseInt(parts[2]));
                }
                System.out.println("Added " + parts[1] + " with " + parts[2] + " matches");
                if (autoSolve) {
                    if (terminalMode) {
                        solve(tSolver);
                    } else {
                        solve(solver);
                    }
                }
                if (terminalMode) {
                    System.out.println("Up next: " + tSolver.getNext());
                }
            } else if (parts[0].equals("-autosolve")) {
                if (parts[1].equals("true")) {
                    autoSolve = true;
                    tSolver.clear();
                } else {
                    autoSolve = false;
                }
                System.out.println("Autosolve = " + autoSolve);
            } else if (parts[0].equals("-terminal")) {
                if (parts[1].equals("true")) {
                    terminalMode = true;
                    solver.clear();
                } else {
                    terminalMode = false;
                }
                System.out.println("Terminal mode = " + terminalMode);
            }else if (parts[0].equals("-addWords")) {
                if (terminalMode) {
                    List<String> list = new ArrayList<String>();
                    for (int i = 1; i < parts.length; i++) {
                        list.add(parts[i]);
                    }
                    tSolver.addWords(list);
                    System.out.println("Words added");
                    System.out.println("Up next: " + tSolver.getNext());
                } else {
                    System.out.println("You are in terminal mode");
                }
            }
            s = br.readLine();
        } while (!s.equals("-exit"));
    }

    public static void solve(Solver solver) {
        System.out.println("Solutions:");
        for (String s : solver.getPossibleSolutions()) {
            System.out.println(s);
        }
    }

    public static void solve(TerminalSolver solver) {
        System.out.println("Solutions:");
        for (String s : solver.getPossibleSolutions()) {
            System.out.println(s);
        }
    }
}
