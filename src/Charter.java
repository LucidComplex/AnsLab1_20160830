import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by localuser on 9/29/16.
 */
public class Charter {
    public static void createFrequencyChart(List<Sequence> sequencesList) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int[] aCount = new int[sequencesList.size()];
        int[] cCount = new int[sequencesList.size()];
        int[] tCount = new int[sequencesList.size()];
        int[] gCount = new int[sequencesList.size()];

        for (int i = 0; i < sequencesList.get(0).getData().length(); i++) {
            for (Sequence s : sequencesList) {
                char c = s.getData().charAt(i);
                switch (c) {
                    case 'a':
                        aCount[i]++;
                        break;
                    case 'c':
                        cCount[i]++;
                        break;
                    case 't':
                        tCount[i]++;
                        break;
                    case 'g':
                        gCount[i]++;
                        break;
                }
            }
        }
        System.out.println(aCount);
        // panel.add(new XChartPanel());
    }

    private XYChart buildChart(String title, String xTitle, String yTitle) {
        XYChart chart = new XYChartBuilder().width(400).height(300).title(title).xAxisTitle(xTitle).yAxisTitle(yTitle).build();

        return chart;

    }
}
