package fileservice;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import debug.Debug;
import entity.LessonPO;
import entity.AssignmentScore;

public class ScoreExcelCreater {
	public static final String homeworkscorelabel = "��ҵ�ɼ�";
	public static final String examscorelabel = "���Գɼ�";
	public static final String scorelabel = "�γ̳ɼ�";
	public static final String scoredesc = "���㷽ʽ˵��";
	
	public HSSFWorkbook create(List<AssignmentScore> scores, int numOfAssignment, LessonPO lesson) {
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		if(numOfAssignment == 0) {
			return hwb;
		}
		HSSFSheet sheet = hwb.createSheet("�ɼ���");
		HSSFRow firstrow = sheet.createRow(0); // �±�Ϊ0���п�ʼ
		firstrow.createCell(0, Cell.CELL_TYPE_STRING).setCellValue("ѧ��");;
		firstrow.createCell(1, Cell.CELL_TYPE_STRING).setCellValue("����");
		int i = 0;
		for(i = 0; i < numOfAssignment; ++ i) {
			Cell cell = firstrow.createCell(i + 2, Cell.CELL_TYPE_STRING);
			String aname  = scores.get(i).getAssignmentName();
			Debug.print("name: '" + aname + "'");
			if(aname == null || aname.equals("") || aname.equals("null")) {
				Debug.print("true");
				aname = new String("��ҵ" + (i+1)); 
			}
			cell.setCellValue(aname);
		}
		firstrow.createCell((i++ ) +2, Cell.CELL_TYPE_STRING).setCellValue("��ҵ�ɼ�");
		firstrow.createCell((i++ ) +2, Cell.CELL_TYPE_STRING).setCellValue("���Գɼ�");
		firstrow.createCell((i++ ) +2, Cell.CELL_TYPE_STRING).setCellValue("�γ̳ɼ�");
		firstrow.createCell((i++ ) +2, Cell.CELL_TYPE_STRING).setCellValue("���㷽ʽ˵��");
		int numOfStudent = scores.size() / numOfAssignment;
		for(i = 0 ; i < numOfStudent; ++ i) {
			HSSFRow row = sheet.createRow(i + 1);
			int sum = 0;
			int sid = scores.get(i * numOfAssignment).getSid();
			String name = scores.get(i * numOfAssignment).getName();
			row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(sid);
			row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(name);
			int j = 0;
			for(j = 0 ; j < numOfAssignment; ++ j) {
				double score = scores.get(i * numOfAssignment + j).getScore();
				row.createCell(2 + j, Cell.CELL_TYPE_NUMERIC).setCellValue(score);
				sum += score;
			}
			row.createCell(2 + j, Cell.CELL_TYPE_NUMERIC).setCellValue(sum);
		}
		return hwb;
	}
	
	
	//private boolean setColumn(HSSFSheet sheet) {
	//	
	//}
}
