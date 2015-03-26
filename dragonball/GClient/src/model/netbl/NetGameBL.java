package model.netbl;

import common.Mode;
import common.Nameplate;
import common.Service;
import view.ui.gameui.Position;
import model.netservice.NetGameService;

public class NetGameBL implements NetGameService {
	SocketStart sock;
	// final static String NAMEPLATE = Nameplate.CoopRoom.toString();
	static NetGameBL netGameBL = null;
	Mode mode;
	int roomNO;
	String id;
	int teamId;

	public NetGameBL(Mode m, int roomNO, String id, int teamId) {
		// sock = new SocketStart();
		sock = SocketStart.getSocket();
		mode = m;
		this.roomNO = roomNO;
		this.id = id;
		this.teamId = teamId;
	}

	@Override
	public void timeStart() {
		String message = messageHead() + "TimeStart@"+id ;
		sock.sendMessage(message);
	}
	
	@Override
	public void swap(Position p1, Position p2) {
		String message = messageHead() + "Swap@" + p1.toString() + ","
				+ p2.toString();
		System.out.println("swap to server");
		sock.sendMessage(message);
	}

	@Override
	public void animateFinish() {
		String message = messageHead() + "AnimateFinish";
		sock.sendMessage(message);

	}

	@Override
	public void triggerItem(Position p) {
		String message = messageHead() + "TriggerItem@" + p.toString();
		sock.sendMessage(message);
	}

	@Override
	public void triggerAllItem() {
		String message = messageHead() + "TriggerAllItem";
		sock.sendMessage(message);
	}

	@Override
	public void sendStage(int stage) {
		String message = messageHead() + "Stage@" + stage;
		sock.sendMessage(message);
	}

	private String messageHead() {
		String str = "";
		if (mode.compareTo(Mode.Coop) == 0) {
			str = str + Nameplate.CoopRoom + " " + Service.Game + " " + roomNO
					+ " " + id + " ";
		} else if (mode.compareTo(Mode.Battle) == 0) {
			str = str + Nameplate.BattleRoom + " " + Service.Game + " "
					+ roomNO + " " + id + " " + teamId + " ";
		} else if (mode.compareTo(Mode.Single) == 0) {
			str = str + Nameplate.Single + " " + Service.Game + " " + id + " ";
		}
		return str;
	}
}
