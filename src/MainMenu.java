import oracle.jrockit.jfr.JFRImpl;

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
    private Translator translator;

    private List<Sequence> sequences;

    public MainMenu() {
        translator = new Translator();
        sequences = new ArrayList<Sequence>();

        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = sequenceArea.getText();
                String[] lines = text.split("\n");
                for (String line : lines) {
                    sequences.add(new Sequence(line));
                }
                List<List<Protein>> proteins = new ArrayList<List<Protein>>();
                for (Sequence s : sequences) {
                    proteins.add(translator.translate(s));
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
                Charter.createFrequencyChart(sequences);
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
                        while ((line = reader.readLine()) != null) {

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
