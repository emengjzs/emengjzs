package view.ui.gameui;

import setup.GameClient;
import common.Mode;
import controller.netlistenerbl.NetUserListenerBL;

public class Controller {
	GameController[] gamePanel;
	MainFrame mainFrame;
	int money;
	int exp;
	int upgrade;
	String userID;
	int powerBounce;
	int numberOfBall = -1;
	int rewardNO;
	int UnLock = -1;

	public void init(String message, String mode, int teamId, int dirction) {
		mainFrame = new MainFrame();

		if (GameClient.MusicOn) {
			if (GameClient.music != null)
				GameClient.music.stop();
		}
		
		if (mode.equals(Mode.Coop.toString())) {
			gamePanel = new GameController[1];
			gamePanel[0] = new GameController();
			gamePanel[0].addSetting(new BoardSettings(Direction
					.valueOf(dirction), 50));
			gamePanel[0].init(message, teamId, mode);
			//
			// gamePanel[0].LockBlock(1);
			//
			mainFrame.setMainController(gamePanel[0]);
		} else if (mode.equals(Mode.Battle.toString())) {
			gamePanel = new GameController[2];
			gamePanel[0] = new GameController();
			gamePanel[1] = new GameController();
			String[] msg = message.split("@");
			gamePanel[teamId].addSetting(new BoardSettings(Direction
					.valueOf(dirction), 50));
			gamePanel[teamId].init(msg[0], teamId, mode);
			mainFrame.setMainController(gamePanel[teamId]);
			gamePanel[(teamId + 1) % 2].addSetting(new BoardSettings(Direction
					.valueOf(dirction), 30));
			gamePanel[(teamId + 1) % 2].init(msg[1], teamId, mode);
			mainFrame.setMinorController(gamePanel[(teamId + 1) % 2]);
		} else if (mode.equals(Mode.Single.toString())) {
			gamePanel = new GameController[1];
			gamePanel[0] = new GameController();
			gamePanel[0].addSetting(new BoardSettings(Direction
					.valueOf(dirction), 50));
			gamePanel[0].init(message, teamId, mode);
			mainFrame.setMainController(gamePanel[0]);
		}
		mainFrame.game_Start();

		mainFrame.game_Ready();
	}

	/* 交换成功处理 */
	public void swapSuccess(String message, int teamId) {
		gamePanel[teamId].swapSuccess(message);
	}

	/* 交换失败处理 */
	public void swapFail(String message, int teamId) {
		gamePanel[teamId].swapFail(message);
	}

	public void ItemSuccess(String message, int teamId) {
		gamePanel[teamId].ItemSuccess(message);
	}

	public void SecondEliminate(String message, int teamId) {
		gamePanel[teamId].SecondEliminate(message);
	}

	public void hint(String message, int teamId) {
		gamePanel[teamId].hint(message);
	}

	public void StartSuper(int teamId) {
		gamePanel[teamId].StartSuper();
	}

	public void EndSuper(int teamId) {
		gamePanel[teamId].EndSuper();
	}

	public void timeOut(String defId, int teamId) {
		gamePanel[teamId].EndGame(defId);
	}

	public void lockBlock(int type, int teamId) {
		gamePanel[teamId].LockBlock(type);
	}

	public void unLock(int teamId) {
		gamePanel[teamId].unLock();
	}

	@SuppressWarnings("unused")
	public void endGame(String message) {
		System.out.println(message);
		String infomation[] = message.split(" ");
		int teamID = Integer.parseInt(infomation[2]);
		ScorePanel mine;
		int mineScore = -1;
		ScorePanel rival;
		int rivalScore = -1;

		int kind = 1;

		
//		mainFrame.removeListener();
		
		if (teamID == 0) {
			mine = gamePanel[0].getScore();
			mineScore = mine.getScore();

			try {
				rival = gamePanel[1].getScore();
				rivalScore = rival.getScore();
			} catch (ArrayIndexOutOfBoundsException e) {

			}
			if (rivalScore != -1 && mineScore > rivalScore) {
				kind = 2;
			} else if (rivalScore != -1 && mineScore < rivalScore) {
				kind = 3;
			} else if (rivalScore != -1 && mineScore == rivalScore) {
				kind = 4;
			}
		} else {
			mine = gamePanel[1].getScore();
			mineScore = mine.getScore();

			rival = gamePanel[0].getScore();
			rivalScore = rival.getScore();
			if (rivalScore != -1 && mineScore > rivalScore) {
				kind = 2;
			} else if (rivalScore != -1 && mineScore < rivalScore) {
				kind = 3;
			} else if (rivalScore != -1 && mineScore == rivalScore) {
				kind = 4;
			}
		}

		System.out.println("Mine: " + mineScore + " Rival: " + rivalScore);
		try {
			String info[] = message.split(" ");
			money = Integer.parseInt(info[3]);
			exp = Integer.parseInt(info[4]);
			upgrade = Integer.parseInt(info[5]);
			userID = info[6];
			powerBounce = Integer.parseInt(info[7]);
			numberOfBall = Integer.parseInt(info[8]);
			rewardNO = Integer.parseInt(info[9]);
			UnLock = Integer.parseInt(info[10]);
		} catch (ArrayIndexOutOfBoundsException e) {
			// e.printStackTrace();
		}
		mainFrame.dispose();
		String[] result = new String[5];
		for (int i = 0; i < result.length; i++) {
			result[i] = "";
		}
		int rewardList[] = { 500, 1000, 2000, 3000, 4000, 5000, 6000, 7000,
				9999 };
		String dragonBallList[] = { "一星珠", "二星珠", "三星珠", "四星珠", "五星珠", "六星珠",
				"七星珠", "一 ~ 七星珠" };

		result[0] = mineScore + "";
		result[1] = exp + " ";
		if (upgrade != 0) {
			result[1] = result[1] + "升到LV" + upgrade;
			result[3] = "三种道具各一个";
		}
		result[2] = money + "";
		if (numberOfBall != -1 && numberOfBall != 0) {
			result[3] = result[3] + " " + dragonBallList[numberOfBall - 1];
		} else {
			result[3] = "";
		}

		String[] rewardNOList = { "攻击力提高35%", "5000金币", "攻击力提升30%+3种道具各1" };

		if (UnLock == 0) {
			if (rewardNO != -1) {
				result[4] = rewardNOList[rewardNO];
			} else {
				result[4] = "";
			}
		} else if (UnLock == 1) {
			result[4] = "解锁单人游戏最后一关;";
			if (rewardNO != 0) {
				result[4] = result[4] + " " + rewardNOList[rewardNO];
			}
		}

		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}

		gameOver gO = new gameOver(kind, result);
		NetUserListenerBL gameController = NetUserListenerBL
				.getUserControllerListener();
		gameController.setResultController(gO);
		gO.requestUser(userID);
		money = 0;
		exp = 0;
		upgrade = 0;
		userID = "";
		powerBounce = 0;
		numberOfBall = -1;
		rewardNO = -1;
		UnLock = -1;
		// new gameOver();
		// 进入游戏房间界面
	}

	public void showResult(String message) {
		System.out.println(message);
		try {
			String info[] = message.split(" ");
			money = Integer.parseInt(info[2]);
			System.out.println("--------------------" + money);
			exp = Integer.parseInt(info[3]);
			upgrade = Integer.parseInt(info[4]);
			userID = info[5];
			powerBounce = Integer.parseInt(info[6]);
			numberOfBall = Integer.parseInt(info[7]);
			rewardNO = Integer.parseInt(info[8]);
			UnLock = Integer.parseInt(info[9]);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}

	}

	public void endCombo(int teamId) {
		gamePanel[teamId].endCombo();

	}
}
