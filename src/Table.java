import com.sun.xml.internal.ws.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Table extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable frequencyTable;
    private JButton saveAsJPEGButton;
    private JScrollPane frequencyScroll;
    private List<TableRow> rows;

    public Table() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        saveAsJPEGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ImageSaver.saveComponentToImage(frequencyScroll);
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void showDialog(List<Sequence> sequences) {
        Table dialog = new Table();
        dialog.initTableData(sequences);
        dialog.pack();
        dialog.setVisible(true);
        dialog = null;
    }

    private void initTableData(List<Sequence> sequences) {
        rows = new ArrayList<>();
        int aCount = 0;
        int cCount = 0;
        int tCount = 0;
        int gCount = 0;
        for (Sequence s : sequences) {
            String data = s.getData();
            for (int i = 0; i < data.length(); i++) {
                char character = data.charAt(i);
                switch (character) {
                    case 'a':
                        aCount++;
                        break;
                    case 'c':
                        cCount++;
                        break;
                    case 't':
                        tCount++;
                        break;
                    case 'g':
                        gCount++;
                        break;
                }
            }
        }
        float total = aCount + cCount + tCount + gCount;
        rows.add(new TableRow("A", aCount, (aCount / total) * 100));
        rows.add(new TableRow("C", cCount, (cCount / total) * 100));
        rows.add(new TableRow("T", tCount, (tCount / total) * 100));
        rows.add(new TableRow("G", gCount, (gCount / total) * 100));
    }

    private void createUIComponents() {
        TableModel model = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 4;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public String getColumnName(int i) {
                switch (i) {
                    case 0:
                        return "Nucleotide";
                    case 1:
                        return "Frequency";
                    case 2:
                        return "Percentage";
                }
                return null;
            }

            @Override
            public Object getValueAt(int i, int i1) {
                TableRow row = rows.get(i);
                switch (i1) {
                    case 0:
                        return row.name;
                    case 1:
                        return row.frequency;
                    case 2:
                        return String.valueOf(row.percentage) + "%";
                }
                return null;
            }

        };
        frequencyTable = new JTable(model);
    }
}

class TableRow {
    String name;
    int frequency;
    float percentage;

    public TableRow(String n, int f, float p) {
        name = n;
        frequency = f;
        percentage = p;
    }

}
