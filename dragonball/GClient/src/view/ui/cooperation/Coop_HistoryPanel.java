package view.ui.cooperation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.vo.CoopRecordVO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("serial")
public class Coop_HistoryPanel extends JPanel implements ActionListener {
//	JFrame mainFrame;
//	private int width = 650;
//	private int height = 400;

//	IconButton shutButton;

	JLabel totalTimesLabel;
	JLabel averageLabel;
	JLabel bestLabel;
	JLabel doubleHitLabel;

//	public static void main(String[] args) {
//		// new Coop_HistoryPanel();
//	}

	public Coop_HistoryPanel(int total, int average, int highestPoint,
			int highestCombo, ArrayList<CoopRecordVO> recordList) {
//		mainFrame = new JFrame();
//		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 200);
//		mainFrame.setUndecorated(true);// 去除窗口边框
//		mainFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明
		
		this.setOpaque(false);
		this.setLayout(null);

		initFrame(total, average, highestPoint, highestCombo, recordList);

//		mainFrame.setVisible(true);
	}

	private void initFrame(int total, int averagePoint, int highestPoint,
			int highestCombo, ArrayList<CoopRecordVO> recordList) {
//		Container contentPane = mainFrame.getContentPane();
//
//		final ImageIcon backgroundImage = new ImageIcon(
//				"image/solo/historyBackground.png");
//		JPanel backgroundPanel = new JPanel() {
//			public void paintComponent(Graphics g) {
//				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
//						null);
//			}
//		};
//		backgroundPanel.setLayout(null);
//		backgroundPanel.setSize(width, height);
//		contentPane.add(backgroundPanel);
//
//		shutButton = new IconButton("image/solo/shut1.png",
//				"image/solo/shut2.png", "image/solo/shut3.png");
//		shutButton.addActionListener(this);
//		shutButton.setLocation(514, 30);
//		shutButton.setSize(50, 50);
//		backgroundPanel.add(shutButton);

		JLabel totalTimes = new JLabel("总局数：");
		totalTimes.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		totalTimes.setForeground(new Color(210, 105, 30));
		totalTimes.setLocation(94, 60);
		totalTimes.setSize(81, 20);
		this.add(totalTimes);
		//backgroundPanel.add(totalTimes);

		totalTimesLabel = new JLabel(total + "");
		totalTimesLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		totalTimesLabel.setForeground(new Color(255, 0, 0));
		totalTimesLabel.setLocation(204, 60);
		totalTimesLabel.setSize(46, 20);
		this.add(totalTimesLabel);
		//backgroundPanel.add(totalTimesLabel);

		JLabel average = new JLabel("平均得分：");
		average.setForeground(new Color(210, 105, 30));
		average.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		average.setLocation(298, 93);
		average.setSize(100, 20);
		this.add(average);
		//backgroundPanel.add(average);

		averageLabel = new JLabel(averagePoint + "");
		averageLabel.setForeground(new Color(255, 0, 0));
		averageLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		averageLabel.setLocation(412, 93);
		averageLabel.setSize(60, 20);
		this.add(averageLabel);
		//backgroundPanel.add(averageLabel);

		JLabel best = new JLabel("最高得分：");
		best.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		best.setForeground(new Color(210, 105, 30));
		best.setLocation(298, 60);
		best.setSize(100, 20);
		this.add(best);
		//backgroundPanel.add(best);

		bestLabel = new JLabel(highestPoint + "");
		bestLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		bestLabel.setForeground(new Color(255, 0, 0));
		bestLabel.setLocation(412, 60);
		bestLabel.setSize(100, 20);
		this.add(bestLabel);
		//backgroundPanel.add(bestLabel);

		JLabel doubleHit = new JLabel("最高连击：");
		doubleHit.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		doubleHit.setForeground(new Color(210, 105, 30));
		doubleHit.setLocation(94, 93);
		doubleHit.setSize(100, 20);
		this.add(doubleHit);
		//backgroundPanel.add(doubleHit);

		doubleHitLabel = new JLabel(highestCombo + "");
		doubleHitLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		doubleHitLabel.setForeground(new Color(255, 0, 0));
		doubleHitLabel.setLocation(204, 93);
		doubleHitLabel.setSize(32, 20);
		this.add(doubleHitLabel);
		//backgroundPanel.add(doubleHitLabel);

		JPanel statisticPanel = new JPanel() {
			ImageIcon statisticImage = new ImageIcon(
					"image/solo/statisticBackground.png");
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				statisticImage.setImage(statisticImage.getImage()
						.getScaledInstance(418, 223, Image.SCALE_DEFAULT));
				g.drawImage(statisticImage.getImage(), 0, 0, null);
			}
		};
		statisticPanel.setLocation(94, 123);
		statisticPanel.setSize(418, 223);
		statisticPanel.setLayout(null);
		this.add(statisticPanel);
		//backgroundPanel.add(statisticPanel);

		String statistic = "";
		String day[] = new String[10];
		day[0] = "一";
		day[1] = "二";
		day[2] = "三";
		day[3] = "四";
		day[4] = "五";
		day[5] = "六";
		day[6] = "七";
		day[7] = "八";
		day[8] = "九";
		day[9] = "十";

		int num[] = new int[10];// 通过方法来获取
		for (int i = 0; i < 10; i++) {
			try {
				CoopRecordVO record = recordList.get(i);
				num[i] = record.getPoint();
			} catch (IndexOutOfBoundsException e) {
				num[i] = 0;
			}

		}

		DefaultCategoryDataset linedata = new DefaultCategoryDataset();
		// ChartPanel lineChartPanel = null;
		linedata.addValue(num[0], statistic, day[0]);
		linedata.addValue(num[1], statistic, day[1]);
		linedata.addValue(num[2], statistic, day[2]);
		linedata.addValue(num[3], statistic, day[3]);
		linedata.addValue(num[4], statistic, day[4]);
		linedata.addValue(num[5], statistic, day[5]);
		linedata.addValue(num[6], statistic, day[6]);
		linedata.addValue(num[7], statistic, day[7]);
		linedata.addValue(num[8], statistic, day[8]);
		linedata.addValue(num[9], statistic, day[9]);

		String lastTenTimes = "最近十次成绩";

		JFreeChart lineChart = ChartFactory.createLineChart(lastTenTimes,
				"天数", "得分", linedata, PlotOrientation.VERTICAL, false, true,
				true);
		lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));// 将图标背景设为透明
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		removeMouseListener(lineChartPanel);
		lineChartPanel.setOpaque(false);
		lineChartPanel.setLocation(0, 20);
		lineChartPanel.setSize(400, 200);
		statisticPanel.add(lineChartPanel);
		CategoryPlot plot = lineChart.getCategoryPlot();
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		rangeAxis.setAutoRangeIncludesZero(true);
//		rangeAxis.setUpperMargin(0.20);
//		rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		// 背景透明
		plot.setBackgroundPaint(null);
		// x轴 分类轴网格是否可见
		plot.setDomainGridlinesVisible(true);
		// y轴 数据轴网格是否可见
		plot.setRangeGridlinesVisible(true);
		// 虚线色彩
		plot.setRangeGridlinePaint(Color.WHITE);
		// 虚线色彩
		plot.setDomainGridlinePaint(Color.WHITE);
		// 设置背景色
		plot.setBackgroundPaint(null);

		// 设置轴和面板之间的距离
		CategoryAxis domainAxis = plot.getDomainAxis();
		// 横轴上的
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		// 获得renderer
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// series 点（即数据点）可见
		lineandshaperenderer.setBaseShapesVisible(true);
		// series 点（即数据点）间有连线可见
		lineandshaperenderer.setBaseLinesVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//mainFrame.dispose();
	}


	public void removeMouseListener(ChartPanel panel) {
		MouseListener ms[] = panel.getMouseListeners();
		//System.out.println("gg");
		for(MouseListener m : ms) {
			panel.removeMouseListener(m);
		}
	}
}
