package businesslogic.bl;

import java.io.PrintWriter;
import java.util.ArrayList;

import common.Mode;
import common.Nameplate;
import common.Service;
import data.dataimpl.SingleGameDataServiceImpl;
import data.dataimpl.UserDataServiceImpl;
import data.dataservice.SingleGameDataService;
import data.dataservice.UserDataService;
import po.ActiveUser;
import po.UserPO;

public class SingleBL extends RoomBL {
	private ActiveUser user;
	int stage;
	static String nameplate = Nameplate.Single.toString();
	static String service = "";
	boolean prop1;
	boolean prop2;
	boolean prop3;
	int NumberOfProp1 = 0;
	int NumberOfProp2 = 0;
	int NumberOfProp3 = 0;
	Team team;
	SingleGameDataService singleGameData = new SingleGameDataServiceImpl();
	UserDataService userData = new UserDataServiceImpl();

	public SingleBL(String userID, int stage, PrintWriter writer) {
		UserPO po = userData.FindByID(userID);
		this.stage = stage;
		user = new ActiveUser(userID, writer, po.getMoney(), po.getExp(),
				po.getPower());
		team = new Team(this);
		System.out.println("SingleBL established");
	}

	public ActiveUser getUser() {
		return user;
	}

	public void playGame(String message, String id) {
		if (message.startsWith("Start")) {
			if (prop1) {
				message += "c";
			}
			if (prop2) {
				message += "d";
			}
			if (prop3) {
				message += "e";
			}
			String result = team.startGame(message, 0);
			int dir = userData.FindDirection(user.getUserID());
			String power = user.getUserID() + "=" + user.getPower() + "~";
			String str = result + "-" + 0 + "-" + user.getUserID() + "-"
					+ power + "-" + stage + " 0 " + dir;
			user.sendMessage(Service.Game + " " + str + " " + Mode.Single);
			return;
		}

		team.distibuteMessage(message, 0, id);
	}

	public String getPropList(String userID) {
		String propList = "";
		int prop1 = userData.FindProp1(userID);
		NumberOfProp1 = prop1;
		int prop2 = userData.FindProp2(userID);
		NumberOfProp2 = prop2;
		int prop3 = userData.FindProp3(userID);
		NumberOfProp3 = prop3;

		propList = prop1 + " " + prop2 + " " + prop3;
		return propList;
	}

	public void buyProp(String userID, int propNO) {
		System.out.println("In Single Room" + userID + " " + propNO);
		if (propNO == 1) {
			if (NumberOfProp1 > 1) {
				NumberOfProp1--;
				userData.UpdateProp1(userID, -1);
				prop1 = true;
			} else {
				if (user.getMoney() >= 388) {
					userData.UpdateMoney(userID, -388);
					user.setMoney(user.getMoney()-388);
					prop1 = true;
				}
			}
		} else if (propNO == 2) {
			if (NumberOfProp2 > 1) {
				NumberOfProp2--;
				userData.UpdateProp2(userID, -1);
				prop2 = true;
			} else {
				if (user.getMoney() >= 488) {
					userData.UpdateMoney(userID, -488);
					user.setMoney(user.getMoney()-488);
					prop2 = true;
				}
			}
		} else if (propNO == 3) {
			if (NumberOfProp3 > 1) {
				NumberOfProp3--;
				userData.UpdateProp3(userID, -1);
				prop3 = true;
			} else {
				if (user.getMoney() >= 88) {
					userData.UpdateMoney(userID, -88);
					user.setMoney(user.getMoney()-88);
					prop3 = true;
				}
			}
		}
	}

	@Override
	public void sendGameMsg(String message) {

		user.sendMessage(Service.Game + " " + message);

	}

	@Override
	public void sendGameMsg(String message, int teamId) {
		if (teamId == 0) {
			user.sendMessage(Service.Game + " " + message + " " + teamId);
		}
	}

	@Override
	public void superStart(int teamId) {
	}

	@Override
	public void superEnd(int teamId) {
	}

	@Override
	public ArrayList<ActiveUser> getUserList(int teamId) {
		ArrayList<ActiveUser> list = new ArrayList<ActiveUser>();
		list.add(user);
		return list;
	}

	@Override
	public void endGame(int point, int combo, int teamId) {
		singleGameData.InsertGameRecord(user.getUserID(), point, combo);
	}

	private int checkUpgrade(String userID, int preExp, int addExp) {
		int postExp = preExp + addExp;
		int rank = 0;
		int expList[] = { 30, 80, 180, 400, 700, 1000, 1300, 1600, 2000 };
		int rewardList[] = { 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000,
				9999 };
		int powerList[] = { 1, 2, 4, 7, 15, 24, 35, 48, 63 };

		for (int i = 0; i < expList.length; i++) {
			try {
				if (preExp < expList[i] && postExp >= expList[i]) {
					rank = i + 1;
					int reward = rewardList[i];
					int power = powerList[i];

					user.setPower(power + user.getPower());
					user.setMoney(reward + user.getMoney());

					userData.UpdateMoney(userID, reward);
					userData.UpdatePower(userID, power);
					userData.UpdateProp1(userID, 1);
					userData.UpdateProp2(userID, 1);
					userData.UpdateProp3(userID, 1);
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		return rank;
	}

	public int[] getResult(int point) {
		int[] result = new int[2];
		// 是否过关 0 未过，1过了
		result[0] = 0;
		// 星级评价
		result[1] = 0;
		int power = user.getPower();
		int[] enemyList = { 100, 500, 1000, 5000, 10000, 30000, 50000, 100000,
				120000 };
		int playerAttack = point / 10 * power;

		if (playerAttack < enemyList[stage - 1]) {

		} else {
			result[0] = 1;
			userData.UpdateStage(user.getUserID(), stage, "pass");
			if ((playerAttack >= enemyList[stage - 1])
					&& (playerAttack < enemyList[stage - 1] * 1.1)) {
				result[1] = 1;
				int formerResult = userData.FindStageStar(user.getUserID(),
						stage);
				if (result[1] > formerResult) {
					userData.UpdateEvaluate(user.getUserID(), stage, result[1]);
				}
			} else if ((playerAttack >= enemyList[stage - 1] * 1.1)
					&& (playerAttack < enemyList[stage - 1] * 1.2)) {
				result[1] = 2;
				int formerResult = userData.FindStageStar(user.getUserID(),
						stage);
				if (result[1] > formerResult) {
					userData.UpdateEvaluate(user.getUserID(), stage, result[1]);
				}
			} else if (playerAttack >= enemyList[stage - 1] * 1.2) {
				result[1] = 3;
				int formerResult = userData.FindStageStar(user.getUserID(),
						stage);
				if (result[1] > formerResult) {
					userData.UpdateEvaluate(user.getUserID(), stage, result[1]);
				}
			}
			String state = userData.FindStageState(user.getUserID(), stage + 1);
			if (state.equals("notpass")) {
				userData.UpdateStage(user.getUserID(), stage + 1, "current");
			}
		}

		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public void sendEndGameMsg(String message, int teamId, int point) {
		// TODO Auto-generated method stub
		int money = point / 100;
		int exp = point / 1000;
		int[] info = getResult(point);
		int upgrade = checkUpgrade(user.getUserID(), user.getExp(), exp);
		user.setMoney(money + user.getMoney());
		user.setExp(exp + user.getExp());
		userData.UpdateMoney(user.getUserID(), money);
		userData.UpdateExp(user.getUserID(), exp);
		int powerList[] = { 1, 2, 4, 7, 15, 24, 35, 48, 63 };
		int power = 0;
		try {
			int index = upgrade - 1;
			power = powerList[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			power = 0;
		}

		String result = Service.Game + " " + "EndGame" + " " + teamId + " "
				+ money + " " + exp + " " + upgrade + " " + user.getUserID()
				+ " " + power + " ";
		user.sendMessage(result);
	}

}
