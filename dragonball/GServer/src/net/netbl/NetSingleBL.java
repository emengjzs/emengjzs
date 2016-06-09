package net.netbl;

import java.io.PrintWriter;

import businesslogic.bl.GameHallBL;

import common.Nameplate;
import common.Service;

import net.netservice.*;

public class NetSingleBL implements NetSingleBLService {
	GameHallBL gameHallBL = GameHallBL.getGameHall();
	final static String NAMEPLATE = Nameplate.Single.toString();

	@Override
	public void selectService(String message, PrintWriter writer) {
		String[] result = message.split(" ");

		if (result[1].equals(Service.EnterSingleGame.toString())) {
			Login(result[2], Integer.parseInt(result[3]), writer);
		} else if (result[1].equals(Service.Game.toString())) {
			playGame(result[3], result[2]);
		} else if (result[1].equals(Service.RequestSingleProp.toString())) {
			chooseSingleProp(result[2], writer);
		} else if (result[1].equals(Service.BuyProp.toString())) {
			buyProp(result[2], Integer.parseInt(result[3]));
		} else if(result[1].equals(Service.QuitSingleGame.toString())){
			Leave(result[2]);
		}
	}
	
	private void Leave(String userID){
		gameHallBL.removeSingleUser(userID);
	}

	private void Login(String userID, int stage, PrintWriter writer) {
		gameHallBL.addSingleUser(userID, stage, writer);
	}

	private void playGame(String message, String id) {
		gameHallBL.playSingleGame(message, id);
	}

	private void chooseSingleProp(String userID, PrintWriter writer) {
		String service = Service.ReplySingleProp.toString();
		String result = gameHallBL.getSingleProp(userID);

		String message = NAMEPLATE + " " + service + " " + result;

		writer.println(message);
		writer.flush();
	}

	private void buyProp(String userID, int propNO) {
		gameHallBL.buySingleProp(userID, propNO);
	}

}
