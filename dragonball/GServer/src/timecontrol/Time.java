package timecontrol;

import java.util.Timer;
import java.util.TimerTask;
import businesslogic.bl.Team;

public class Time {

	private Team team;
	private Timer comboTimer;
	private int comboSec = 1;
	private int comboCount;
	private int comboMax = 4;// ���ﳬ��ģʽ���������
	private int comboALL;// ��������
	private int comboALLMax;// �������������������
	private Timer tipTimer;
	private int tipSec = 3;
	private Timer superTimer;
	private int superSec = 5;
	private Timer controlTimer;
	private int ControlSec = 6;
	private Timer lockBTimer;
	private int lockBSec = 2;
	boolean isUseC = false;

	public Time(Team t, String item) {
		this.team = t;
		comboTimer = new Timer();
		tipTimer = new Timer();
		superTimer = new Timer();
		controlTimer = new Timer();
		lockBTimer = new Timer();
		comboCount = 0;
		comboALL = 0;
		comboALLMax = 0;
		if (item.indexOf('c') != -1) {
			comboSec = 2;
			isUseC = true;
		}
		if (item.indexOf('e') != -1) {
			tipSec = 2;
		}
	}

	public int getComboALLMax() {
		return comboALLMax;
	}

	public class Control extends TimerTask {
		public void run() {
			team.timeOut();
			// ��Ϸ����
		}
	}

	public void startControl() {

		controlTimer.schedule(new Control(), ControlSec * 1000);
	}

	/**
	 * B���ߴ�����ĳһ��ɫ������
	 * 
	 * @author Administrator
	 * 
	 */
	public class LockB extends TimerTask {
		private int teamId;

		public LockB(int team) {
			this.teamId = team;
		}

		public void run() {
			team.endLockB(teamId);
			// �������
		}
	}

	public void startLockB(int teamId) {
		lockBTimer.schedule(new LockB(teamId), lockBSec * 1000);
	}

	public class Combo extends TimerTask {
		public void run() {
			comboCount = 0;
			comboALL = 0;
			comboTimer.cancel();
			team.endCombo();
		}
	}

	/**
	 * ����ֵ��һ��ֵΪ���ﳬ��ģʽǰ�����������ڶ���ֵΪ����������������ֵΪ�����������
	 * 
	 * @return
	 */
	public String startCombo() {
		comboCount++;
		comboALL++;
		comboTimer.cancel();
		if (comboALL > comboALLMax) {
			comboALLMax = comboALL;
		}
		if (comboCount == comboMax) {
			comboCount = 0;
			System.out.println("����ģʽ��ʼ��");
			team.startSuper();
			startSuper();
			// ����ģʽ
		}
		comboTimer = new Timer();
		String result = comboCount + "-" + comboALL + "-" + comboALLMax;
		return result;
	}

	public void startComboTime() {
		comboTimer = new Timer();
		comboTimer.schedule(new Combo(), comboSec * 1000);

	}

	class Tip extends TimerTask {
		public void run() {
			System.out.println("��ʾ��");
			team.getTip();
		}
	}

	public void startTip() {
		tipTimer.cancel();
		tipTimer = new Timer();
		tipTimer.schedule(new Tip(), tipSec * 1000);
	}

	class Super extends TimerTask {
		public void run() {
			System.out.println("����ģʽ������");
			team.endSuper();
			// �÷ּ���
			superTimer.cancel();
		}
	}

	public void startSuper() {
		superTimer.cancel();
		superTimer = new Timer();
		superTimer.schedule(new Super(), superSec * 1000);
	}

	public void comboSecTo1() {
		comboSec = 1;
	}

	public void comboSecTo2() {
		if (isUseC) {
			comboSec = 2;
		}
	}

}
