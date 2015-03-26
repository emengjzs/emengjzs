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
	// 是否循环放音乐
	private boolean loop = true;

	// 构造方法
	public Music(String paths, boolean loops) {
		this.path = paths;
		this.loop = loops;
//		try {
//			is = new FileInputStream(path);// 创建文件流，读取音乐
//			ap = new AdvancedPlayer(is);// 将这个文件流放在播放器里
//		} catch (Exception e) {
//			System.out.println("没有找到该文件！");
//			e.printStackTrace();
//		}
	}

	// 线程类
	private class MusicThread extends Thread {
		public void run() {
			do {
				try {
					try {
						is = new FileInputStream(path);
						// 创建文件流，读取音乐
						ap = new AdvancedPlayer(is);
						// 将这个文件流放在播放器里
					} catch (Exception e) {
						System.out.println("没有找到该文件！");
					}
					ap.play();
					
					try {
						ap.close();
						is.close();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			} while (loop);
		}
	
	}

	// 播放音乐
	public void play() {
		mt = new MusicThread();
		mt.start();
	}

	// 关闭音乐
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