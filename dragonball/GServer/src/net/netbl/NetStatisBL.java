package net.netbl;

import java.io.PrintWriter;

import businesslogic.bl.StatisticBL;

import common.Nameplate;
import common.Service;

import net.netservice.NetStatisBLService;

public class NetStatisBL implements NetStatisBLService {

	StatisticBL statisticBL = new StatisticBL();
	final static String NAMEPLATE = Nameplate.Statis.toString();

	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub
		String[] result = message.split(" ");

		if (result[1].equals(Service.RequestCoopStatis.toString())) {
			getCoopStatis(result[2], writer);
		} else if (result[1].equals(Service.RequestSingleStatis.toString())) {
			getSingleStatis(result[2], writer);
		} else if (result[1].equals(Service.RequestSingleTen.toString())) {
			getSingleTen(result[2], writer);
		} else if (result[1].equals(Service.RequestSingleDaily.toString())) {
			getSingleDaily(result[2], writer);
		} else if (result[1].equals(Service.RequestBattleStatis.toString())) {
			getBattleStatis(result[2], writer);
		} else if (result[1].equals(Service.RequestSingleTop.toString())) {
			getSingleTop(result[2], writer);
		}
	}

	private void getCoopStatis(String userID, PrintWriter writer) {
		String service = Service.ReplyCoopStatis.toString();

		String info = statisticBL.getCoopData(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private void getSingleStatis(String userID, PrintWriter writer) {
		String service = Service.ReplySingleStatis.toString();

		String info = statisticBL.getSingleData(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private void getSingleTen(String userID, PrintWriter writer) {
		String service = Service.ReplySingleTen.toString();

		String info = statisticBL.getSingleTen(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private void getSingleTop(String userID, PrintWriter writer) {
		String service = Service.ReplySingleTop.toString();

		String info = statisticBL.getSingleTop(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private void getSingleDaily(String userID, PrintWriter writer) {
		String service = Service.ReplySingleDaily.toString();

		String info = statisticBL.getSingleDaily(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

	private void getBattleStatis(String userID, PrintWriter writer) {
		String service = Service.ReplyBattleStatis.toString();

		String info = statisticBL.getBattleStatis(userID);
		String message = NAMEPLATE + " " + service + " " + info;

		writer.println(message);
		writer.flush();
	}

}
