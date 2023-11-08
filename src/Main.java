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
    private int selectAlgorithms = 0;
    Map<Integer, List<Integer>> sourceData = new HashMap<>();

    public Main() {
        setContentPane(panelMain);
        setTitle("Maximum Flow Graph (MFG)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        buttonFordF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAlgorithms = 1;
                buttonEdmondsKarp.setSelected(false);
            }
        });
        buttonEdmondsKarp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAlgorithms = 2;
                buttonFordF.setSelected(false);
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
            for (int j = 0; j < 100; j++) {
                randomList.add(random.nextInt(10) + 1);
            }
            sourceData.put(i, randomList);
        }
        textInputConsole.setText("");
        textInputConsole.append("Loading data test......");
        textInputConsole.append("\nLoaded.");
        textInputConsole.append("\nNumber of Nodes: " + numberOfNodes);
        textInputConsole.append("\nSource: " + source);
        textInputConsole.append("\nSink: " + sink);
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
        int numberOfNodes;
        int source;
        int sink;
        int maxFlow;
        numberOfNodes = 100;

        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                graph[keySetData][destinationVertex] = sourceData.get(keySetData).get(destinationVertex - 1);
            }
        }
        source = 1;
        sink = 100;

        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);
        long startTime = System.currentTimeMillis();
        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        StringBuilder message = new StringBuilder();
        message.append("[").append(formatter.get().format(new Date())).append("] ");
        textOutputConsole.append("\n" + message);
        textOutputConsole.append("\nRunning Ford-Fulkerson Algorithm......");
        textOutputConsole.append("\nMaximum flow is: " + maxFlow);
        textOutputConsole.append("\nRunning time: " + totalTime + " milliseconds");
    }

    private void ActionEdmondsKarpAlgorithm() {
        int maxFlow;
        int numberOfNodes;
        numberOfNodes = 100;
        EdmondsKarp ek = new EdmondsKarp(100, 100);
        for (Map.Entry<Integer, List<Integer>> sourceItem : sourceData.entrySet()) {
            int keySetData = sourceItem.getKey();
            for (int destinationVertex = 0; destinationVertex < numberOfNodes; destinationVertex++) {
                if (sourceData.get(keySetData).get(destinationVertex) != 0) {
                    ek.addEdge(keySetData - 1, destinationVertex, sourceData.get(keySetData).get(destinationVertex));
                }
            }
        }

        long startTime = System.currentTimeMillis();
        maxFlow = ek.getMaxFlow(0, 5);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        StringBuilder message = new StringBuilder();
        message.append("[").append(formatter.get().format(new Date())).append("] ");
        textOutputConsole.append("\n" + message);
        textOutputConsole.append("\nRunning Edmonds-Karp Algorithm......");
        textOutputConsole.append("\nMaximum flow is: " + maxFlow);
        textOutputConsole.append("\nRunning time: " + totalTime + " milliseconds");
    }

    public static void main(String[] args) {
        new Main();
    }

}
