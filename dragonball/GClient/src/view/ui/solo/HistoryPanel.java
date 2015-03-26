package view.ui.solo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import model.vo.SingleTenRecordVO;
import model.vo.SingleWeekRecordVO;

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

import view.ui.myComponent.IconButton;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HistoryPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	//	JFrame mainFrame;
//	private int width = 650;
//	private int height = 400;
	ArrayList<SingleWeekRecordVO> recordList;
	ArrayList<SingleTenRecordVO> tenRecordList;

//	IconButton shutButton;
//	IconButton forwardButton;
//	IconButton backwardButton;

	IconButton everydatTimesButton;
	IconButton everydayScoreButton;
	IconButton lastTenTimesButton;

	JPanel statisticPanel;// ����ͳ�Ʊ��panel

//	public static void main(String[] args) {
//		// new HistoryPanel();
//	}

	public HistoryPanel(int total, int average, int highestPoint, int highestCombo,
			ArrayList<SingleWeekRecordVO> recordList,
			ArrayList<SingleTenRecordVO> tenRecordList) {
		this.recordList = recordList;
		this.tenRecordList = tenRecordList;

//		mainFrame = new JFrame();
//		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 200);
//		mainFrame.setUndecorated(true);// ȥ�����ڱ߿�
//		mainFrame.setBackground(new Color(0, 0, 0, 0)); // �����ڱ�͸��
		
		this.setOpaque(false);
		this.setLayout(null);

		initFrame(total, average, highestPoint, highestCombo, recordList);
		
		ChartPanel panel = getEverydayTimes(recordList);
		panel.setEnabled(false);
		panel.setFocusable(false);
		statisticPanel.removeAll();
		statisticPanel.add(panel);
		statisticPanel.repaint();
		statisticPanel.setEnabled(false);

//		mainFrame.setVisible(true);
	}

	private void initFrame(int total, int averagePoint, int highestPoint,
			int highestCombo, ArrayList<SingleWeekRecordVO> recordList) {
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

		statisticPanel = new JPanel() {
			ImageIcon statisticImage = new ImageIcon(
					"image/solo/statisticBackground.png");
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				statisticImage.setImage(statisticImage.getImage()
						.getScaledInstance(366, 223, Image.SCALE_DEFAULT));
				g.drawImage(statisticImage.getImage(), 0, 0, null);
			}
		};
		statisticPanel.setLocation(94, 123);
		statisticPanel.setSize(366, 223);
		statisticPanel.setLayout(null);
		this.add(statisticPanel);
		//backgroundPanel.add(statisticPanel);

		everydatTimesButton = new IconButton("image/solo/everydayTimes1.png",
				"image/solo/everydayTimes2.png",
				"image/solo/everydayTimes3.png");
		everydatTimesButton.addActionListener(this);
		everydatTimesButton.setLocation(470, 120);
		everydatTimesButton.setSize(100, 50);
		this.add(everydatTimesButton);
		//backgroundPanel.add(everydatTimesButton);

		everydayScoreButton = new IconButton("image/solo/everydayScore1.png",
				"image/solo/everydayScore2.png",
				"image/solo/everydayScore3.png");
		everydayScoreButton.addActionListener(this);
		everydayScoreButton.setLocation(470, 200);
		everydayScoreButton.setSize(100, 50);
		this.add(everydayScoreButton);
		//backgroundPanel.add(everydayScoreButton);

		lastTenTimesButton = new IconButton("image/solo/lastTenTimes1.png",
				"image/solo/lastTenTimes2.png", "image/solo/lastTenTimes3.png");
		lastTenTimesButton.addActionListener(this);
		lastTenTimesButton.setLocation(470, 280);
		lastTenTimesButton.setSize(100, 50);
		this.add(lastTenTimesButton);
		//backgroundPanel.add(lastTenTimesButton);

		JLabel totalTimes = new JLabel("�ܾ�����");
		totalTimes.setFont(new Font("���ǵ������", Font.BOLD, 18));
		totalTimes.setForeground(new Color(210, 105, 30));
		totalTimes.setLocation(94, 60);
		totalTimes.setSize(81, 20);
		this.add(totalTimes);
		//backgroundPanel.add(totalTimes);

		JLabel totalTimesLabel = new JLabel(total + "");
		totalTimesLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		totalTimesLabel.setForeground(new Color(255, 0, 0));
		totalTimesLabel.setLocation(194, 60);
		totalTimesLabel.setSize(46, 20);
		this.add(totalTimesLabel);
		//backgroundPanel.add(totalTimesLabel);

		JLabel average = new JLabel("ƽ���÷֣�");
		average.setForeground(new Color(210, 105, 30));
		average.setFont(new Font("���ǵ������", Font.BOLD, 18));
		average.setLocation(250, 93);
		average.setSize(100, 20);
		this.add(average);
		//backgroundPanel.add(average);

		JLabel averageLabel = new JLabel(averagePoint + "");
		averageLabel.setForeground(new Color(255, 0, 0));
		averageLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		averageLabel.setLocation(360, 93);
		averageLabel.setSize(60, 20);
		this.add(averageLabel);
		//backgroundPanel.add(averageLabel);

		JLabel best = new JLabel("��ߵ÷֣�");
		best.setFont(new Font("���ǵ������", Font.BOLD, 18));
		best.setForeground(new Color(210, 105, 30));
		best.setLocation(250, 60);
		best.setSize(100, 20);
		this.add(best);
		//backgroundPanel.add(best);

		JLabel bestLabel = new JLabel(highestPoint + "");
		bestLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		bestLabel.setForeground(new Color(255, 0, 0));
		bestLabel.setLocation(360, 60);
		bestLabel.setSize(100, 20);
		this.add(bestLabel);
		//backgroundPanel.add(bestLabel);

		JLabel doubleHit = new JLabel("���������");
		doubleHit.setFont(new Font("���ǵ������", Font.BOLD, 18));
		doubleHit.setForeground(new Color(210, 105, 30));
		doubleHit.setLocation(94, 93);
		doubleHit.setSize(100, 20);
		this.add(doubleHit);
		//backgroundPanel.add(doubleHit);

		JLabel doubleHitLabel = new JLabel(highestCombo + "");
		doubleHitLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		doubleHitLabel.setForeground(new Color(255, 0, 0));
		doubleHitLabel.setLocation(194, 93);
		doubleHitLabel.setSize(32, 20);
		this.add(doubleHitLabel);
		//backgroundPanel.add(doubleHitLabel);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == shutButton) {
//			mainFrame.dispose();
//		}

		if (e.getSource() == everydatTimesButton) {
			ChartPanel panel = getEverydayTimes(recordList);
			panel.setEnabled(false);
			panel.setFocusable(false);
			panel.disable();
			statisticPanel.removeAll();
			statisticPanel.add(panel);
			
			statisticPanel.repaint();
			statisticPanel.setEnabled(false);
		}
		if (e.getSource() == everydayScoreButton) {
			ChartPanel panel = getEverydayScore(recordList);
			panel.setEnabled(false);
			panel.setFocusable(false);
			panel.disable();
			statisticPanel.removeAll();
			statisticPanel.add(panel);
			
			statisticPanel.repaint();
			statisticPanel.setEnabled(false);
		}
		if (e.getSource() == lastTenTimesButton) {
			ChartPanel panel = getLastTenTimes(tenRecordList);
			panel.setEnabled(false);
			panel.setFocusable(false);
			panel.disable();
			statisticPanel.removeAll();
			statisticPanel.add(panel);
			
			statisticPanel.repaint();
			statisticPanel.setEnabled(false);
		}

	}

	private ChartPanel getEverydayTimes(ArrayList<SingleWeekRecordVO> recordList) {// �õ�ÿ�����ͳ������ͼ
		String statistic = "";
		String day[] = new String[7];
		day[0] = "1";
		day[1] = "2";
		day[2] = "3";
		day[3] = "4";
		day[4] = "5";
		day[5] = "6";
		day[6] = "7";

		int num[] = new int[7];// ͨ����������ȡ
		for (int i = 0; i < 7; i++) {
			try {
				SingleWeekRecordVO singleRecord = recordList.get(i);
				num[i] = singleRecord.getTotal();
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

		JFreeChart lineChart = ChartFactory.createLineChart("ÿ�վ�������", "����", "����",
				linedata, PlotOrientation.VERTICAL, false, true, true);
		lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));// ��ͼ�걳����Ϊ͸��
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		this.removeMouseListener(lineChartPanel);
		lineChartPanel.setEnabled(false);
		lineChartPanel.setOpaque(false);
		lineChartPanel.setLocation(0, 20);
		lineChartPanel.setSize(356, 200);
		// statisticPanel.add(lineChartPanel);
		CategoryPlot plot = lineChart.getCategoryPlot();
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		rangeAxis.setAutoRangeIncludesZero(true);
//		rangeAxis.setUpperMargin(0.20);
//		rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		// ����͸��
		plot.setBackgroundPaint(null);
		// x�� �����������Ƿ�ɼ�
		plot.setDomainGridlinesVisible(true);
		// y�� �����������Ƿ�ɼ�
		plot.setRangeGridlinesVisible(true);
		// ����ɫ��
		plot.setRangeGridlinePaint(Color.WHITE);
		// ����ɫ��
		plot.setDomainGridlinePaint(Color.WHITE);
		// ���ñ���ɫ
		plot.setBackgroundPaint(null);

		// ����������֮��ľ���
		CategoryAxis domainAxis = plot.getDomainAxis();
		// �����ϵ�
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		// ���renderer
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// series �㣨�����ݵ㣩�ɼ�
		lineandshaperenderer.setBaseShapesVisible(true);
		// series �㣨�����ݵ㣩�������߿ɼ�
		lineandshaperenderer.setBaseLinesVisible(true);

		return lineChartPanel;
	}

	private ChartPanel getEverydayScore(ArrayList<SingleWeekRecordVO> recordList) {// �õ�ÿ��ƽ���÷ֵ�ͳ������ͼ
		String statistic = "";
		String day[] = new String[7];
		day[0] = "1";
		day[1] = "2";
		day[2] = "3";
		day[3] = "4";
		day[4] = "5";
		day[5] = "6";
		day[6] = "7";

		int num[] = new int[7];// ͨ����������ȡ
		for (int i = 0; i < 7; i++) {
			try {
				SingleWeekRecordVO singleRecord = recordList.get(i);
				num[i] = singleRecord.getAveragePoint();
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

		JFreeChart lineChart = ChartFactory.createLineChart("ÿ�վ�������", "����", "�÷�",
				linedata, PlotOrientation.VERTICAL, false, true, true);
		lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));// ��ͼ�걳����Ϊ͸��
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		removeMouseListener(lineChartPanel);
		lineChartPanel.setEnabled(false);
		lineChartPanel.setOpaque(false);
		lineChartPanel.setLocation(0, 20);
		lineChartPanel.setSize(356, 200);
		// statisticPanel.add(lineChartPanel);
		CategoryPlot plot = lineChart.getCategoryPlot();
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		rangeAxis.setAutoRangeIncludesZero(true);
//		rangeAxis.setUpperMargin(0.20);
//		rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		// ����͸��
		plot.setBackgroundPaint(null);
		// x�� �����������Ƿ�ɼ�
		plot.setDomainGridlinesVisible(true);
		// y�� �����������Ƿ�ɼ�
		plot.setRangeGridlinesVisible(true);
		// ����ɫ��
		plot.setRangeGridlinePaint(Color.WHITE);
		// ����ɫ��
		plot.setDomainGridlinePaint(Color.WHITE);
		// ���ñ���ɫ
		plot.setBackgroundPaint(null);

		// ����������֮��ľ���
		CategoryAxis domainAxis = plot.getDomainAxis();
		// �����ϵ�
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		// ���renderer
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// series �㣨�����ݵ㣩�ɼ�
		lineandshaperenderer.setBaseShapesVisible(true);
		// series �㣨�����ݵ㣩�������߿ɼ�
		lineandshaperenderer.setBaseLinesVisible(true);

		return lineChartPanel;

	}

	private ChartPanel getLastTenTimes(
			ArrayList<SingleTenRecordVO> tenRecordList) {// ����ߴ�ÿ�ε÷�����
		String statistic = "";
		String day[] = new String[10];
		day[0] = "һ";
		day[1] = "��";
		day[2] = "��";
		day[3] = "��";
		day[4] = "��";
		day[5] = "��";
		day[6] = "��";
		day[7] = "��";
		day[8] = "��";
		day[9] = "ʮ";

		int num[] = new int[10];// ͨ����������ȡ
		for (int i = 0; i < 10; i++) {
			try {
				SingleTenRecordVO singleRecord = tenRecordList.get(i);
				num[i] = singleRecord.getScore();
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

		JFreeChart lineChart = ChartFactory.createLineChart("���7������", "����", "�÷�",
				linedata, PlotOrientation.VERTICAL, false, true, true);
		lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));// ��ͼ�걳����Ϊ͸��
		ChartPanel lineChartPanel = new ChartPanel(lineChart);
		lineChartPanel.setEnabled(false);
		this.removeMouseListener(lineChartPanel);
		lineChartPanel.setOpaque(false);
		lineChartPanel.setLocation(0, 20);
		lineChartPanel.setSize(356, 200);
		// statisticPanel.add(lineChartPanel);
		CategoryPlot plot = lineChart.getCategoryPlot();
//		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		rangeAxis.setAutoRangeIncludesZero(true);
//		rangeAxis.setUpperMargin(0.20);
//		rangeAxis.setLabelAngle(Math.PI / 2.0);
		
		// ����͸��
		plot.setBackgroundPaint(null);
		// x�� �����������Ƿ�ɼ�
		plot.setDomainGridlinesVisible(true);
		// y�� �����������Ƿ�ɼ�
		plot.setRangeGridlinesVisible(true);
		// ����ɫ��
		plot.setRangeGridlinePaint(Color.WHITE);
		// ����ɫ��
		plot.setDomainGridlinePaint(Color.WHITE);
		// ���ñ���ɫ
		plot.setBackgroundPaint(null);

		// ����������֮��ľ���
		CategoryAxis domainAxis = plot.getDomainAxis();
		// �����ϵ�
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		// ���renderer
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// series �㣨�����ݵ㣩�ɼ�
		lineandshaperenderer.setBaseShapesVisible(true);
		// series �㣨�����ݵ㣩�������߿ɼ�
		lineandshaperenderer.setBaseLinesVisible(true);

		return lineChartPanel;
	}
	
	public void removeMouseListener(ChartPanel panel) {
		MouseListener ms[] = panel.getMouseListeners();
		//System.out.println("gg");
		for(MouseListener m : ms) {
			panel.removeMouseListener(m);
		}
	}
}
