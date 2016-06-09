package listeners;

import java.text.DecimalFormat;

public class FileUploadStatus {
	private double percentage = 0.0;
	private DecimalFormat df = new DecimalFormat("#00.0");
	public static final String FILE_UPLOAD_PROGRESS_FIELD = "uploadPercent";
	
	public double getPercentage() {
		return percentage;
	}
	
	public String getPercentageString() {
		return df.format(this.percentage);
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}  
	
}
