package njuse.ffff.ui.ver2.dataanalize.offensive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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

public class ReboundPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	public final static int OFFENSIVE = 0;
	public final static int DEFENSIVE = 1;

	private static final String[] types = { "进攻篮板", "防守篮板" };

	private String[] header = { null, "排名" };

	private TableView table;
	private LabelEx description;
	private DefaultPieDataset dataset;

	private String majorType;
	private String minorType;

	public ReboundPanel(int type) {
		super(new BorderLayout(20, 20));
		setOpaque(false);

		header[0] = types[type] + "率";
		majorType = "场均" + types[type];
		minorType = "场均" + types[1 - type];

		table = new TableView(null, header);
		table.setPreferredSize(new Dimension(300, 60));
		table.getTable().setRowSorter(null);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setForeground(Color.BLACK);
		description.setVerticalAlignment(LabelEx.BOTTOM);

		PanelEx tadPanel = new PanelEx(new GridLayout(0, 1));
		tadPanel.setOpaque(false);
		add(tadPanel, BorderLayout.WEST);
		tadPanel.add(table);
		tadPanel.add(description);

		dataset = new DefaultPieDataset();
		ChartPanel chart = createChart("场均" + types[type] + "比例", dataset);

		setData(0.5, 1, 0.5);
		add(chart);
	}

	private ChartPanel createChart(String title, DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, false, false, false);
		chart.setBackgroundPaint(null);
		chart.getTitle().setFont(UIConfig.SubTitleFont);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(UIConfig.SmallFont);
		plot.setLabelBackgroundPaint(Color.WHITE);
		plot.setSectionOutlinesVisible(false);
		plot.setLabelShadowPaint(null);
		plot.setShadowPaint(null);
		plot.setOutlineVisible(false);
		plot.setBackgroundAlpha(0);
		plot.setLabelGap(0.02);
		plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0} {2}", NumberFormat.getNumberInstance(),
				new DecimalFormat("#.#%")));
		plot.setSectionPaint(majorType, UIConfig.TitleBgColor.brighter());
		plot.setSectionPaint(minorType, UIConfig.ThemeColor.brighter());

		plot.setExplodePercent(majorType, 0.2);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setOpaque(false);
		return chartPanel;
	}

	public void setData(double ratio, int rank, double avgRatio) {
		Object[][] data = new Object[][] { { ratio, rank } };
		table.setTable(data);

		dataset.setValue(majorType, avgRatio);
		dataset.setValue(minorType, 1 - avgRatio);
	}

	public void setDescription(String text) {
		if (text.length() > 20) {
			text = text.substring(0, 20) + "<br>" + text.substring(20);
		}
		text = "<html>" + text + "</html>";

		description.setText(text);
	}
}
