package view.ui.myComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

public class Music {
	private String path;
	private InputStream is = null;
	private AdvancedPlayer ap = null;
	
	private MusicThread mt = new MusicThread();;
	// �Ƿ�ѭ��������
	private boolean loop = true;

	// ���췽��
	public Music(String paths, boolean loops) {
		this.path = paths;
		this.loop = loops;
//		try {
//			is = new FileInputStream(path);// �����ļ�������ȡ����
//			ap = new AdvancedPlayer(is);// ������ļ������ڲ�������
//		} catch (Exception e) {
//			System.out.println("û���ҵ����ļ���");
//			e.printStackTrace();
//		}
	}

	// �߳���
	private class MusicThread extends Thread {
		public void run() {
			do {
				try {
					try {
						is = new FileInputStream(path);
						// �����ļ�������ȡ����
						ap = new AdvancedPlayer(is);
						// ������ļ������ڲ�������
					} catch (Exception e) {
						System.out.println("û���ҵ����ļ���");
					}
					ap.play();
					
					try {
						ap.close();
						is.close();
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
					
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			} while (loop);
		}
	
	}

	// ��������
	public void play() {
		mt = new MusicThread();
		mt.start();
	}

	// �ر�����
	@SuppressWarnings("deprecation")
	public void stop() {
		loop = false;
		mt.stop();
	}
	
	public void restart(){
		loop = true;
	}

//	public static void main(String[] args) {
//		Music m = new Music("test.mp3", true);
//		m.play();
//	}
}