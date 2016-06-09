package fileservice;

import helper.IntHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import logic.LessonService;
import message.Message;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import data.LessonDao;
import entity.Lesson;
import entity.LessonRecord;
@Stateless
@LocalBean
public class ScoreExcelReader {
	
	@EJB
	LessonService lessonservice;
	
	@EJB
	LessonDao lessondao;
	
	public Message read(FileItem excel, int lid) {
		POIFSFileSystem fs;
		HSSFWorkbook wb = null;
		boolean find = false;
		try {
			fs = new POIFSFileSystem(excel.getInputStream());
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int homeworkscorecol = 0;
			int examworkscorecol = 0;
			int scorecol = 0;
			String scoredesc;
			HSSFRow row = sheet.getRow(0);
			int colNum = row.getPhysicalNumberOfCells();
			
			for (short i = 0; i < colNum; i++) {
	            
	           HSSFCell cell =  row.getCell(i,  Row.RETURN_BLANK_AS_NULL);
	           if(cell != null) {
	        	   if(cell.getStringCellValue().equals(ScoreExcelCreater.homeworkscorelabel)) {
	        		   homeworkscorecol = i;
	        		   find = true;
	        		   break;
	        	   } 	
	        	   
	           }          
	        }
			if(! find) {
				Message m = Message.UpdateMessage.UPDATE_FAILED;
				m.setInfo("Excel Format Error!"); 
				return m;
			}
			
			examworkscorecol = homeworkscorecol + 1;
			scorecol = examworkscorecol + 1;
			scoredesc = sheet.getRow(1).getCell(scorecol + 1,  Row.RETURN_BLANK_AS_NULL).getStringCellValue();
			
			
			int rownum = sheet.getLastRowNum();
			Map<Integer, LessonRecord> map = getData(lid);
			for (int i = 1; i <= rownum; i++) {
	            row = sheet.getRow(i);
	            LessonRecord r = map.get((int) row.getCell(0).getNumericCellValue());
	            if(r != null) {
	            	r.setHomeworkScore(row.getCell(homeworkscorecol).getNumericCellValue());
	            	r.setExamScore(row.getCell(examworkscorecol).getNumericCellValue());
	            	r.setScore(row.getCell(scorecol).getNumericCellValue());
	            }         
			}
			updateLesson(scoredesc, lid);
			updateData(map);
			updaterank(lid);
			return Message.UpdateMessage.UPDATE_SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
			Message m = Message.UpdateMessage.UPDATE_FAILED;
			m.setInfo("Read Error!"); 
			return m;
		} finally {
			if(wb!= null)
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		
	}
	
	private void updateLesson(String scoredesc, int lid) {
		Lesson l = (Lesson) this.lessondao.getById(lid);
		if(l != null) {
			l.setScoreDescription(scoredesc);
			lessondao.update(l);
		}
			
	}

	private Map<Integer, LessonRecord> getData(int lid) {
		Map<Integer, LessonRecord> map = new Hashtable<Integer, LessonRecord>();
		List<LessonRecord> l =  this.lessondao.getLessonRecordById(lid, "sid", true);
		for(LessonRecord r : l) {
			map.put(r.getSid(), r);
		}
		return map;
	}
	
	private void updateData(Map<Integer, LessonRecord> map) {
		Iterator<LessonRecord> itr = map.values().iterator();
		while(itr.hasNext()) {
			lessondao.update(itr.next());
		}
		
		
	}
	
	private void updaterank(int lid) {
		lessonservice.updateLessonRecordRank(lid);
	}
}
