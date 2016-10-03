import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class Chart extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private XChartPanel<XYChart> chartPanel;
    private JButton saveAsJPEGButton;

    public Chart() {
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
                ImageSaver.saveComponentToImage(chartPanel);
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
        Chart dialog = new Chart();
        Chart.updateChart(dialog.chartPanel.getChart(), getSeries(sequences));
        dialog.pack();
        dialog.setVisible(true);
    }

    private static Series getSeries(List<Sequence> sequences) {
        int max = 0;
        for (int i = 0; i < sequences.size(); i++) {
            if (max < sequences.get(i).getData().length()) {
                max = sequences.get(i).getData().length();
            }
        }
        Series series = new Series(max);
        for (int i = 0; i < max; i++) {
            series.x[i] = i + 1;
            for (int j = 0; j < sequences.size(); j++) {
                String data = sequences.get(j).getData();
                char c = data.charAt(i);
                switch (c) {
                    case 'a':
                        series.a[i]++;
                        break;
                    case 'c':
                        series.c[i]++;
                        break;
                    case 't':
                        series.t[i]++;
                        break;
                    case 'g':
                        series.g[i]++;
                        break;
                }
            }
        }
        return series;
    }

    private static void updateChart(XYChart chart, Series series) {
        chart.addSeries("A", series.x, series.a);
        chart.addSeries("C", series.x, series.c);
        chart.addSeries("T", series.x, series.t);
        chart.addSeries("G", series.x, series.g);
    }

    private static XYChart buildChart(Series series) {
        XYChartBuilder builder = new XYChartBuilder().xAxisTitle("Position").yAxisTitle("Frequency");
        return builder.build();
    }

    private void createUIComponents() {
        //Series series = Chart.getSeries(sequences);
        XYChart chart = Chart.buildChart(null);
        XChartPanel<XYChart> panel = new XChartPanel<>(chart);
        chartPanel = panel;
    }
}

class Series {
    int[] a;
    int[] c;
    int[] t;
    int[] g;

    int[] x;

    public Series(int max) {
        a = new int[max];
        c = new int[max];
        t = new int[max];
        g = new int[max];
        x = new int[max];
    }
}
