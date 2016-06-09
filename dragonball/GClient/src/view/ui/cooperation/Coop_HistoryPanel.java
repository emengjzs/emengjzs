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
//		mainFrame.setUndecorated(true);// ȥ�����ڱ߿�
//		mainFrame.setBackground(new Color(0, 0, 0, 0)); // �����ڱ�͸��
		
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

		JLabel totalTimes = new JLabel("�ܾ�����");
		totalTimes.setFont(new Font("���ǵ������", Font.BOLD, 18));
		totalTimes.setForeground(new Color(210, 105, 30));
		totalTimes.setLocation(94, 60);
		totalTimes.setSize(81, 20);
		this.add(totalTimes);
		//backgroundPanel.add(totalTimes);

		totalTimesLabel = new JLabel(total + "");
		totalTimesLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		totalTimesLabel.setForeground(new Color(255, 0, 0));
		totalTimesLabel.setLocation(204, 60);
		totalTimesLabel.setSize(46, 20);
		this.add(totalTimesLabel);
		//backgroundPanel.add(totalTimesLabel);

		JLabel average = new JLabel("ƽ���÷֣�");
		average.setForeground(new Color(210, 105, 30));
		average.setFont(new Font("���ǵ������", Font.BOLD, 18));
		average.setLocation(298, 93);
		average.setSize(100, 20);
		this.add(average);
		//backgroundPanel.add(average);

		averageLabel = new JLabel(averagePoint + "");
		averageLabel.setForeground(new Color(255, 0, 0));
		averageLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		averageLabel.setLocation(412, 93);
		averageLabel.setSize(60, 20);
		this.add(averageLabel);
		//backgroundPanel.add(averageLabel);

		JLabel best = new JLabel("��ߵ÷֣�");
		best.setFont(new Font("���ǵ������", Font.BOLD, 18));
		best.setForeground(new Color(210, 105, 30));
		best.setLocation(298, 60);
		best.setSize(100, 20);
		this.add(best);
		//backgroundPanel.add(best);

		bestLabel = new JLabel(highestPoint + "");
		bestLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
		bestLabel.setForeground(new Color(255, 0, 0));
		bestLabel.setLocation(412, 60);
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

		doubleHitLabel = new JLabel(highestCombo + "");
		doubleHitLabel.setFont(new Font("���ǵ������", Font.BOLD, 18));
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

		String lastTenTimes = "���ʮ�γɼ�";

		JFreeChart lineChart = ChartFactory.createLineChart(lastTenTimes,
				"����", "�÷�", linedata, PlotOrientation.VERTICAL, false, true,
				true);
		lineChart.setBackgroundPaint(new Color(0, 0, 0, 0));// ��ͼ�걳����Ϊ͸��
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
