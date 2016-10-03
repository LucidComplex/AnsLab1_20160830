import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 9/26/16.
 */
public class MainMenu {
    private JPanel mainPanel;
    private JTextArea sequenceArea;
    private JButton translateButton;
    private JButton nucleotideTrendsButton;
    private JButton frequencyAndPercentageTableButton;
    private JLabel textAreaLabel;
    private JTextArea resultsArea;
    private JButton chooseFile;
    private JButton saveAsFASTAFileButton;
    private Translator translator;

    private List<Sequence> sequences;

    public MainMenu() {
        translator = new Translator();
        sequences = new ArrayList<Sequence>();

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadSequences();
                List<List<Protein>> proteins = new ArrayList<List<Protein>>();
                try {
                    for (Sequence s : sequences) {
                        proteins.add(translator.translate(s));
                    }
                } catch (StringIndexOutOfBoundsException ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            resultsArea.setText("Invalid input -- indivisible by 3.");
                        }
                    });
                }
                StringBuilder builder = new StringBuilder();
                for (List<Protein> proteinList : proteins) {
                    builder.append(proteinList.toString());
                    builder.append("\n");
                }
                resultsArea.setText(builder.toString());
            }
        });

        frequencyAndPercentageTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadSequences();
                Table.showDialog(sequences);
            }
        });
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int ret = fc.showOpenDialog(mainPanel);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File fasta = fc.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(fasta));
                        String line;
                        StringBuilder builder = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                            builder.append("\n");
                        }

                        sequenceArea.setText(builder.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        nucleotideTrendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadSequences();
                Chart.showDialog(sequences);
            }
        });
        saveAsFASTAFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int ret = fc.showSaveDialog(mainPanel);
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File fasta = fc.getSelectedFile();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fasta));
                        String sequences = sequenceArea.getText();
                        writer.write(sequences);
                        writer.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadSequences() {
        sequences.clear();
        String text = sequenceArea.getText();
        String[] lines;
        lines = text.split("\n");
        StringBuilder sequenceBuilder = new StringBuilder();
        for (String line : lines) {
            if (line.length() == 0 || line.startsWith(">")) {
                if (sequenceBuilder.length() != 0) {
                    sequences.add(new Sequence(sequenceBuilder.toString()));
                    sequenceBuilder.setLength(0);
                }
                continue;
            }
            sequenceBuilder.append(line);
        }
        sequences.add(new Sequence(sequenceBuilder.toString()));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
