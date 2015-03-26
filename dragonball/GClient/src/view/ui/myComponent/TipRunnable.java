package view.ui.myComponent;

public class TipRunnable implements Runnable{
	String imagePath;
	int loc_x;
	int loc_y;
	int width;
	int height;
	int time;
	public TipRunnable(String path,int loc_x,int loc_y,int width,int height,int time){
		this.imagePath = path;//获得图片的地址	
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.width = width;
		this.height = height;
		this.time = time;
	}
	public void run() {
		MySplashScreen window = new MySplashScreen(imagePath);
		window.setLocation(loc_x, loc_y);
		window.setSize(width, height);
		window.setVisible(true);
		
		int i=0;
		while(true){
			i++;
			if(i==time){
				window.dispose();
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
