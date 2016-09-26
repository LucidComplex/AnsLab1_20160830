import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tan on 9/26/16.
 */
public class MainMenu {
    private JPanel mainPanel;
    private JTextArea sequenceArea;
    private JButton translateButton;
    private JButton button2;
    private JButton button3;
    private JLabel textAreaLabel;
    private JTextArea resultsArea;
    private Translator translator;

    public MainMenu() {
        translator = new Translator();
        translateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = sequenceArea.getText();
                String[] lines = text.split("\n");
                List<Sequence> sequences = new ArrayList<Sequence>();
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

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainMenu");
        frame.setContentPane(new MainMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
