package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class RatioPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	protected String[] header;

	protected TableView table;
	protected LabelEx description;

	DefaultCategoryDataset dataset;

	public RatioPanel(String[] header) {
		super(new BorderLayout(10, 10));
		setOpaque(false);

		this.header = header;

		PanelEx tadPanel = new PanelEx(new GridLayout(2, 1));
		tadPanel.setOpaque(false);
		add(tadPanel, BorderLayout.NORTH);

		table = new TableView(null, header);
		table.setPreferredSize(new Dimension(0, 60));
		table.getTable().setRowSorter(null);
		tadPanel.add(table);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setForeground(Color.BLACK);
		tadPanel.add(description);

		PanelEx chartPanel = new PanelEx(new GridLayout(1, 1));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		chartPanel.setOpaque(false);
		add(chartPanel);

		dataset = new DefaultCategoryDataset();
		dataset.addValue(1.0, "球员", header[1]);
		dataset.addValue(2.0, "联盟", header[1]);
		dataset.addValue(2.0, "球员", header[2]);
		dataset.addValue(1.0, "联盟", header[2]);

		ChartPanel chart = createChart("", dataset);
		chartPanel.add(chart);
	}

	private ChartPanel createChart(String title, DefaultCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(title, "", "", dataset);
		chart.setBackgroundPaint(null);
		chart.getTitle().setFont(UIConfig.SubTitleFont);
		chart.getTitle().visible = false;
		chart.getLegend().setItemFont(UIConfig.SmallFont);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.GRAY);
		plot.setDomainGridlinesVisible(false);

		CategoryAxis axis = plot.getDomainAxis();
		axis.setLabelFont(UIConfig.SmallFont);
		axis.setTickLabelFont(UIConfig.SmallFont);
		ValueAxis valueAxis = plot.getRangeAxis();
		valueAxis.setLabelFont(UIConfig.SmallFont);
		valueAxis.setTickLabelFont(UIConfig.SmallFont);
		valueAxis.setUpperMargin(0.2);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setSeriesPaint(0, UIConfig.TitleBgColor.brighter());
		renderer.setSeriesPaint(1, UIConfig.ThemeColor.brighter());
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0.1);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		ChartPanel panel = new ChartPanel(chart);
		panel.setOpaque(false);
		return panel;
	}

	public void setData(Object[][] data) {
		table.setTable(data);
		dataset.setValue((double) data[0][1], "球员", header[1]);
		dataset.addValue((double) data[0][4], "联盟", header[1]);
		dataset.addValue((double) data[0][2], "球员", header[2]);
		dataset.addValue((double) data[0][5], "联盟", header[2]);
	}

	public void setDescription(String text) {
		description.setText(text);
	}
}