package timecontrol;

import java.util.Timer;
import java.util.TimerTask;
import businesslogic.bl.Team;

public class Time {

	private Team team;
	private Timer comboTimer;
	private int comboSec = 1;
	private int comboCount;
	private int comboMax = 4;// 到达超级模式最大连击数
	private int comboALL;// 总连击数
	private int comboALLMax;// 总连击数的最大连击数
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
			// 游戏结束
		}
	}

	public void startControl() {

		controlTimer.schedule(new Control(), ControlSec * 1000);
	}

	/**
	 * B道具触发锁某一颜色的棋子
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
			// 解除锁定
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
	 * 返回值第一个值为到达超级模式前段连击数，第二个值为总连击数，第三个值为总最大连击数
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
			System.out.println("超级模式开始！");
			team.startSuper();
			startSuper();
			// 超级模式
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
			System.out.println("提示！");
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
			System.out.println("超级模式结束！");
			team.endSuper();
			// 得分减半
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
