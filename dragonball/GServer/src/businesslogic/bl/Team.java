package businesslogic.bl;

import java.util.ArrayList;

import po.ActiveUser;

import gamemodelservice.*;
import timecontrol.Time;

public class Team {
	private ModelService controller;
	private Time time;
	private RoomBL room;
	private int teamId;
	private boolean isEnd;

	public Team(RoomBL room) {
		controller = new ModelController();
		this.room = room;
		isEnd = false;
	}

	public void distibuteMessage(String message, int roomNO, String id) {
		String result;
		String[] string = message.split("@");
		switch (string[0]) {
		case "TimeStart":
			if (room.getUserList(teamId).get(0).getUserID().equals(string[1])) {
				
				time.startControl();
				time.startTip();
			}
			break;
		case "Swap":
			time.startTip();
			result = controller.swap(string[1]);
			String combo1 = "";
			if (result.startsWith("SwapSuccess")) {
				combo1 += time.startCombo();
			}
			result = result + "-" + id + "-" + combo1 + " " + teamId;
			isTriggerB(result);
			room.sendGameMsg(result);
			break;
		case "TriggerItem":
			time.startTip();
			result = controller.triggerItem(string[1]);
			String combo2 = "";// 连击数目
			String[] tem = result.split("&");
			if (result.startsWith("ItemSuccess")) {
				combo2 += time.startCombo();
			}
			result = tem[0] + "-" + id + "-" + combo2 + " " + teamId;
			isTriggerB(result);
			room.sendGameMsg(result);
			break;
		case "AnimateFinish":
			time.startTip();
			result = controller.animateFinish();
			if (result.startsWith("NoResult")) {
				time.startComboTime();
				if (isEnd) {// 如果时间到，全部触发道具
					result = controller.triggerAllItem();
					if (result.startsWith("ItemFail")) {
						// 2秒之后发消息
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						int combo = time.getComboALLMax();
						int point = controller.getScore();
						room.endGame(point, combo, teamId);
						// room.sendGameMsg("EndGame", teamId);// 结束游戏
						room.sendEndGameMsg("EndGame", teamId, point);
						this.isEnd = false;
					} else {
						String combo3 = "";// 连击数目
						combo3 += time.startCombo();
						result = result + "-" + id + "-" + combo3 + " "
								+ teamId;
						isTriggerB(result);
						room.sendGameMsg(result);
					}
					return;
				}
			}
			result = result + "-" + id + " " + teamId;
			isTriggerB(result);
			room.sendGameMsg(result);
			break;
		case "TriggerAllItem":
			result = controller.triggerAllItem();
			if (result.startsWith("ItemFail")) {
				// 2秒之后发消息
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int combo = time.getComboALLMax();
				int point = controller.getScore();
				room.endGame(point, combo, teamId);
				// room.sendGameMsg("EndGame", teamId);// 结束游戏
				room.sendEndGameMsg("EndGame", teamId, point);
				this.isEnd = false;
			} else {
				String combo4 = "";// 连击数目
				combo4 += time.startCombo();
				result = result + "-" + id + "-" + combo4 + " " + teamId;
				isTriggerB(result);
				room.sendGameMsg(result);
			}
			break;
		}
	}

	private void isTriggerB(String result) {
		if (result.contains("fire")) {
			time.startLockB((teamId + 1) % 2);
			int type = (int) (Math.random() * 5);// 随机产生棋子类型
			String message = "LockB " + type;
			room.sendGameMsg(message, (teamId + 1) % 2);
		}

	}

	public String startGame(String message, int teamId) {
		String result;
		String[] string = message.split("@");
		result = controller.initGame(string[1]);
		this.teamId = teamId;
		time = new Time(this, string[1]);
		return result;
	}

	public void getTip() {
		String result = controller.getTip();
		room.sendGameMsg(result, teamId);
	}

	public void startSuper() {
		String result = controller.startSuper() + " " + teamId;
		room.superStart(teamId);
		room.sendGameMsg(result);
	}

	public void endSuper() {
		String result = controller.endSuper() + " " + teamId;
		room.superEnd(teamId);
		room.sendGameMsg(result);
	}

	public void oppoStartSuper() {// 对手开启超级模式
		time.comboSecTo1();
	}

	public void oppoEndSuper() {// 对手结束超级模式
		time.comboSecTo2();
	}

	public void timeOut() {
		isEnd = true;
		ArrayList<ActiveUser> userList = room.getUserList(teamId);
		int len = userList.size();
		int defNo = (int) Math.random() * len;
		if (len > 0) {
			String defId = userList.get(defNo).getUserID();
			room.sendGameMsg("TimeOut " + defId, teamId);
		}
	}

	public void endCombo() {
		room.sendGameMsg("EndCombo", teamId);
	}

	public void endLockB(int teamId) {
		room.sendGameMsg("EndLockB", teamId);
	}

}
