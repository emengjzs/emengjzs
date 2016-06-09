package view.ui.login;

/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */  
  
import java.awt.AlphaComposite;  
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.Rectangle;  
import java.awt.SplashScreen;  

import javax.swing.JFrame;
import javax.swing.UIManager;  
  
/** 
 * 
 * ���� 
 */  
public class Splash {  
  
    //��������ܻ��ͨ��-splash:���ø������splash screen��ʵ��  
    private final SplashScreen splash = SplashScreen.getSplashScreen();  
    private Rectangle splashBounds;  
    private Graphics2D splashGraphics;  
  
    /** 
     * ��ʼ��splash 
     */  
    protected void initSplash() {  
        if (splash != null) {  
            //�����splash screen��һ���߿�  
            splashBounds = splash.getBounds();  
            //�������ʼ��ͼ�ζ��󣬸�ͼ�ζ���ȡ��splash  
            splashGraphics = (Graphics2D) splash.createGraphics();  
            if (splashGraphics != null) {  
                splashGraphics.setColor(Color.BLUE);  
                splashGraphics.drawRect(0, 0, splashBounds.width - 1, splashBounds.height - 1);  
            }  
        }  
    }  
  
    /** 
     * ����splash��������� 
     */  
    protected void updateSplash(String status, int progress) {  
        if (splash == null || splashGraphics == null) {  
            return;  
        }  
        //�ػ�splash����Ľ��Ȳ�֪ͨsplash���½���  
        drawSplash(splashGraphics, status, progress);  
        splash.update();  
    }  
  
    /** 
     * ���������һ�������������������� 
     */  
    protected void drawSplash(Graphics2D splashGraphics, String status, int progress) {  
        int barWidth = splashBounds.width;//����������  
        splashGraphics.setComposite(AlphaComposite.Clear);  
        splashGraphics.fillRect(1, 10, splashBounds.width - 2, 20);//�����߿�  
        splashGraphics.setPaintMode();//ģ��  
        splashGraphics.setColor(Color.ORANGE);//�ַ�����ɫ  
        splashGraphics.drawString(status, 10, 20);//���ַ���  
        splashGraphics.setColor(Color.red);//�� ���������ɫ  
        int width = progress * barWidth / 100;//��������ǰֵ  
        splashGraphics.fillRect(0, splashBounds.height - 20, width, 6);  
    }  
  
    /** 
     * ����Ǽ��س���Ĺ��� 
     */  
    protected void loadApplication() {  
        //��ʼ��splash  
        initSplash();  
        //ģ��ļ��ؽ�����ʾ��Ϣ  
        final String[] stages = {"��������", "���ڶ�ȡ����", "���ڼ�������ĵ�", "�������"};  
        int stage = 0;  
        //���������ʼ������Ĺ����е���updateSplash������splash  
        for (int i = 0; i <= 100; i += 1) {  
            String status = stages[stage];  
            if (splash != null) {  
                //��������ͼ��  
                updateSplash(status, i);  
            }  
            if (i == 30) {  
                stage = 1;  
            } else if (i == 70) {  
                stage = 2;  
            } else if (i == 90) {  
                stage = 3;  
            }  
            try {  
                //����ȴ�  
                Thread.sleep(20);  
            } catch (Exception e) {  
                //�쳣��������  
            }  
        }  
        try {  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
        }  
        MainWindow window = new MainWindow();  
        //���������������ر�splash  
        if (splash != null) {  
            splash.close();  
        }  
        //������ʼ����ĵ�¼�������������         
        window.setVisible(true);  
    }  
  
    public static void main(String args[]) {  
        new Splash().loadApplication();  
    }  
    
    @SuppressWarnings("serial")
	class MainWindow extends JFrame{
    	public MainWindow(){
    		this.setSize(400, 400);
    		
    		
    	}
    }
}  