package njuse.ffff.ui.ver2.dataanalize.offensive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ShotPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private static final String[] header = {
			"两分", "0-3尺", "3-10尺", "10-16尺", "16尺–三分线", "三分"
	};

	TableView table;
	LabelEx description;

	DefaultPieDataset shotset;

	public ShotPanel() {
		super(new BorderLayout());
		setOpaque(false);

		PanelEx tablePanel = new PanelEx(new BorderLayout());
		tablePanel.setOpaque(false);
		add(tablePanel, BorderLayout.NORTH);

		LabelEx title = new LabelEx("各距离段得分分布");
		title.setOpaque(false);
		title.setFont(UIConfig.ContentFont);
		tablePanel.add(title, BorderLayout.NORTH);

		table = new TableView(null, header);
		table.setPreferredSize(new Dimension(0, 70));
		table.getTable().setRowSorter(null);
		tablePanel.add(table);

		PanelEx chartPanel = new PanelEx(new GridLayout(1, 2, 40, 40));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
		chartPanel.setOpaque(false);
		add(chartPanel);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setVerticalAlignment(LabelEx.BOTTOM);
		description.setForeground(Color.BLACK);
		chartPanel.add(description);

		shotset = new DefaultPieDataset();
		for (int i = 0; i < header.length; i++) {
			shotset.setValue(header[i], 1);
		}
		ChartPanel shotChart = createChart("各距离段得分比例【运动战】", shotset);

		chartPanel.add(shotChart);
	}

	private ChartPanel createChart(String title, DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, false, false);
		chart.setBackgroundPaint(null);
		chart.getTitle().setFont(UIConfig.SubTitleFont);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(UIConfig.SmallFont);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setLabelShadowPaint(null);
		plot.setShadowPaint(null);
		plot.setOutlineVisible(false);
		plot.setBackgroundAlpha(0);
		plot.setLabelGap(0.02);
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} {2}", NumberFormat.getNumberInstance(),
				new DecimalFormat("#.#%")));
		plot.setSectionOutlinesVisible(false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	public void setData(Object[][] data) {
		table.setTable(data);

		setShotChart(data);
	}

	public void setDescription(String text) {
		if (text.length() > 20) {
			text = text.substring(0, 20) + "<br>" + text.substring(20);
		}
		text = "<html>" + text + "</html>";

		description.setText(text);
	}

	private void setShotChart(Object[][] data) {
		for (int i = 0; i < header.length; i++)
			shotset.setValue(header[0], (double) data[0][i]);
	}
}
