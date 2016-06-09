package game.modelservice;

import java.util.ArrayList;
import java.util.Iterator;

import game.model.*;
import game.model.block.Block_Stub;

public class ModelController_Stub implements ModelService_Stub {
	private static final int length = 8;
	private Board_Stub board;
	private ArrayList<Block_Stub> eliminatelist; // Ïû³ý¶ÓÁÐ

	public ModelController_Stub() {
		board = new Board_Stub(length, length);
	}

	@Override
	public String initGame() {
		return null;
	}

	@Override
	public String swap(String message) {
		return null;
	}

	@Override
	public String animateFinish() {
		return null;
	}

	@SuppressWarnings("unused")
	private String listToStringPos(ArrayList<Block_Stub> list) {
		return null;
	}

	private String listToStringType(ArrayList<Block_Stub> list) {
		return null;
	}

}
