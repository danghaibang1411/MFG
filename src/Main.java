import Algorithms.Dinics;
import Algorithms.EdmondsKarp;
import Algorithms.FordFulkerson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Main extends JFrame {
    private static final ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm:ss"));
    private JPanel panelMain;
    private JRadioButton buttonFordF;
    private JRadioButton buttonEdmondsKarp;
    private JButton runAlgorithmButton;
    private JTextArea textOutputConsole;
    private JTextArea textInputConsole;
    private JScrollPane scrollPanel;
    private JButton generateButton;
    private JRadioButton buttondinicSAlgorithm;
    private int selectAlgorithms = 0;
    Map<Integer, List<Integer>> sourceData = new HashMap<>();

    public Main() {
        setContentPane(panelMain);
        setTitle("Maximum Flow Graph (MFG)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        buttonFordF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAlgorithms = 1;
                buttonEdmondsKarp.setSelected(false);
                buttondinicSAlgorithm.setSelected(false);
            }
        });
        buttonEdmondsKarp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAlgorithms = 2;
                buttonFordF.setSelected(false);
                buttondinicSAlgorithm.setSelected(false);
            }
        });

        buttondinicSAlgorithm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAlgorithms = 3;
                buttonFordF.setSelected(false);
                buttonEdmondsKarp.setSelected(false);
            }
        });

        runAlgorithmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sourceData == null || sourceData.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please generate data before run the algorithms",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (selectAlgorithms == 1) {
                    ActionRunFordFulkersonAlgorithm();
                } else if (selectAlgorithms == 2) {
                    ActionEdmondsKarpAlgorithm();
                } else if (selectAlgorithms == 3) {
                    ActionDinicsAlgorithm();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select the algorithm before select running option!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        textOutputConsole.setText("");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSourceData();
            }
        });

    }

    private void generateSourceData() {
        int numberOfNodes = 100;
        int source = 1;
        int sink = 100;
        for (int i = 1; i < numberOfNodes + 1; i++) {
            ArrayList<Integer> randomList = new ArrayList<>();

            // Create a Random object to generate random numbers
            Random random = new Random();

            // Add 100 random numbers to the ArrayList
            for (int j = 0; j < numberOfNodes; j++) {
                if (j == i - 1 || (Math.abs(j - i + 1)) > 10) randomList.add(0);
                else randomList.add(random.nextInt(10) + 1);
            }
            sourceData.put(i, randomList);
        }
        textInputConsole.setText("");
        textInputConsole.append("Generating data test......");
        textInputConsole.append("\nNumber of Nodes: " + numberOfNodes);
        textInputConsole.append("\nGraph data:");

        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            textInputConsole.append("\n");
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                textInputConsole.append(sourceData.get(keySetData).get(destinationVertex - 1) + " ");
            }
        }
    }

    private void ActionRunFordFulkersonAlgorithm() {
        int[][] graph;
        int numberOfNodes = 100;
        int source = 1;
        int sink = 100;
        int maxFlow;

        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                graph[keySetData][destinationVertex] = sourceData.get(keySetData).get(destinationVertex - 1);
            }
        }

        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);
        long startTime = System.nanoTime();
        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        StringBuilder message = new StringBuilder();
        message.append("[").append(formatter.get().format(new Date())).append("] ");
        textOutputConsole.append("\n" + message);
        textOutputConsole.append("\nRunning Ford-Fulkerson Algorithm......");
        textOutputConsole.append("\nMaximum flow is: " + maxFlow);
        textOutputConsole.append("\nRunning time: " + (float) (totalTime / (float) 1000000) + " milliseconds");
    }

    private void ActionEdmondsKarpAlgorithm() {
        int maxFlow;
        int numberOfNodes = 100;

        EdmondsKarp ek = new EdmondsKarp(100, 100);
        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 0; destinationVertex < numberOfNodes; destinationVertex++) {
                if (sourceData.get(keySetData).get(destinationVertex) != 0) {
                    ek.addEdge(keySetData - 1, destinationVertex, sourceData.get(keySetData).get(destinationVertex));
                }
            }
        }

        long startTime = System.nanoTime();
        maxFlow = ek.getMaxFlow(0, 5);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        StringBuilder message = new StringBuilder();
        message.append("[").append(formatter.get().format(new Date())).append("] ");
        textOutputConsole.append("\n" + message);
        textOutputConsole.append("\nRunning Edmonds-Karp Algorithm......");
        textOutputConsole.append("\nMaximum flow is: " + maxFlow);
        textOutputConsole.append("\nRunning time: " + (float) (totalTime / (float) 1000000) + " milliseconds");
    }

    private void ActionDinicsAlgorithm() {
        int numberOfNodes = 100;
        int source = 0;
        int sink = 99;
        long maxFlow;

        Dinics dnc = new Dinics(numberOfNodes, source, sink);

        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 0; destinationVertex < numberOfNodes; destinationVertex++) {
                if (sourceData.get(keySetData).get(destinationVertex) != 0) {
                    dnc.addEdge(keySetData - 1, destinationVertex, sourceData.get(keySetData).get(destinationVertex));
                }
            }
        }

        long startTime = System.nanoTime();
        maxFlow = dnc.getMaxFlow();
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        StringBuilder message = new StringBuilder();
        message.append("[").append(formatter.get().format(new Date())).append("] ");
        textOutputConsole.append("\n" + message);
        textOutputConsole.append("\nRunning Dinic's Algorithm......");
        textOutputConsole.append("\nMaximum flow is: " + maxFlow);
        textOutputConsole.append("\nRunning time: " + (float) (totalTime / (float) 1000000) + " milliseconds");
    }

    public static void main(String[] args) {
        new Main();
    }

}
