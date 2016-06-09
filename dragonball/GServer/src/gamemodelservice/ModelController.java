package gamemodelservice;

import gamemodel.*;
import gamemodel.block.Block;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelController implements ModelService {
	private static final int length = 9;
	private Board board;
	private ScoreController sc;
	private BoardStatus status;

	// private ArrayList<Block> eliminatelist; // 消除队列
	// 多线程不要放全局变量
	@Override
	public String initGame(String item) {
		board = new Board(length, length);
		status = board.status;
		sc = new ScoreController();
		board.setScoreController(sc);
		board.start();
		if (item.indexOf('d') != -1) {
			sc.openEItemEffect();
		}
		String result = "GameStart " + board.toString();
		board.show();// //////////////////
		return result;
	}

	@Override
	public String swap(String message) {
		String[] s = message.split(",");
		int row1 = Integer.parseInt(s[0]);
		int col1 = Integer.parseInt(s[1]);
		int row2 = Integer.parseInt(s[2]);
		int col2 = Integer.parseInt(s[3]);
		Position p1 = new Position(col1, row1);
		Position p2 = new Position(col2, row2);
		String result = p1.getY() + "," + p1.getX() + ":" + p2.getY() + ","
				+ p2.getX();
		Direction d = p1.getDirection(p2);
		synchronized (board) {
			if (board.isIn(p2)) {
				board.swap(p1, p2);
				ArrayList<Block> eliminatelist;
				if ((eliminatelist = board.checkMatchAfterSwap(row1, col1, d))
						.size() > 0) {
					result = "SwapSuccess " + result + "-";
					board.showF();
					ArrayList<Block> temp = new ArrayList<Block>(eliminatelist);
					ArrayList<Block> item = board.elimilate(eliminatelist);
					String itemStr = listToStringType(item);

					ArrayList<Block> list = board.fall();
					// list.addAll(item);
					result += listToStringPos(temp);
					result += "-";

					result += listToStringType(list);
					result += itemStr;

					result += "-";
					result += sc.addUp();
					result += "-";
					result += isBItem();// 判断是否触发b道具
				} else {
					board.swap(p1, p2);
					result = "SwapFail " + result;
				}
			}
			board.show();// //////////////////
		}
		return result;
	}

	@Override
	public String triggerItem(String message) {
		String[] s = message.split(",");
		int row = Integer.parseInt(s[0]);
		int col = Integer.parseInt(s[1]);
		// Position p = new Position(col, row);
		String result = "";
		synchronized (board) {
			ArrayList<Block> eliminatelist = board
					.checkMatchAfterItem(row, col);
			if (eliminatelist.size() > 0) {
				result = "ItemSuccess " + result;
				board.showF();
				ArrayList<Block> temp = new ArrayList<Block>(eliminatelist);
				board.elimilate(eliminatelist);
				ArrayList<Block> list = board.fall();
				result += listToStringPos(temp);
				result += "-";
				result += listToStringType(list);
				result += "-";
				result += sc.addUp();
				result += "-";
				result += isBItem();// 判断是否触发b道具
			} else {
				result = "ItemFail";
			}
			// what do you want to do 棋子都消完了拿个毛线????????????????
			// Block b = board.getBlock(p);
			// result = result + "&" + b.type.toString();
			// //////////////////////////////////////////
			board.show();// //////////////////////
		}
		return result;
	}

	@Override
	public String animateFinish() {
		String result = "";
		ArrayList<Block> eliminatelist;
		synchronized (board) {
			if ((eliminatelist = board.checkInstable()).size() > 0) {
				result = "SecondEliminate ";
				board.showF();
				ArrayList<Block> temp = new ArrayList<Block>(eliminatelist);
				ArrayList<Block> item = board.elimilate(eliminatelist);
				String itemStr = listToStringType(item);
				ArrayList<Block> list = board.fall();
				// list.addAll(item);
				result += listToStringPos(temp);
				result += "-";
				result += listToStringType(list);
				result += itemStr;
				result += "-";
				result += sc.addUp();
				result += "-";
				result += isBItem();// 判断是否触发b道具
			} else {
				result = "NoResult";
			}
			board.show();// //////////////////
		}
		return result;
	}

	private String listToStringPos(ArrayList<Block> list) {
		String result = "";
		Iterator<Block> iterator = list.iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			result = result + block.toString() + ":";
		}
		return result;
	}

	private String listToStringType(ArrayList<Block> list) {
		String result = "";
		Iterator<Block> iterator = list.iterator();
		while (iterator.hasNext()) {
			Block block = iterator.next();
			result = result + block.toStringType() + ":";
		}
		return result;
	}

	@Override
	public String getTip() {
		Tip tip = board.getTip();
		Position p2 = tip.getAnotherPosition();
		String str = "Tip " + tip.p1.getY() + "," + tip.p1.getX() + ":"
				+ p2.getY() + "," + p2.getX();
		return str;
	}

	@Override
	public String startSuper() {
		String str = "StartSuper";
		sc.startSuperMode();
		return str;
	}

	@Override
	public String endSuper() {
		String str = "EndSuper";
		sc.endSuperMode();
		return str;
	}

	@Override
	public String triggerAllItem() {
		ArrayList<Block> eliminatelist = board.endup();
		String result = "";
		if (eliminatelist.size() > 0) {
			result = "ItemSuccess " + result;
			board.showF();
			ArrayList<Block> temp = new ArrayList<Block>(eliminatelist);
			board.elimilate(eliminatelist);
			ArrayList<Block> list = board.fall();
			result += listToStringPos(temp);
			result += "-";
			result += listToStringType(list);
			result += "-";
			result += sc.addUp();
			result += "-";
			result += isBItem();// 判断是否触发b道具
		} else {
			result = "ItemFail";
		}
		board.show();// //////////////////////
		return result;
	}

	@Override
	public int getScore() {
		return sc.getScore();
	}

	private String isBItem() {
		String result = "";
		if (status.getBItemRecord()) {
			result = "fire";
		} else {
			result = "null";
		}
		return result;
	}

}
