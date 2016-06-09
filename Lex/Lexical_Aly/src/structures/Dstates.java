package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.Vertex;


public class Dstates {
	public static int INVALID_ID = -1;
	private int currentStateId = -1;
	private ArrayList<Set<Vertex<Integer>>> statesSet = new ArrayList<Set<Vertex<Integer>>>();
	
	private ArrayList<Set<Integer>> idSetList = new ArrayList<Set<Integer>>();
	
	// ±ê¼Ç
	private Map<Integer, Boolean> tagMap = new HashMap<Integer, Boolean>();
	
	/**
	 * ·µ»Ø×´Ì¬IDºÅ
	 * @param state
	 * @return
	 */
	public int addState(Set<Vertex<Integer>> state) {
		statesSet.add(state);	
		idSetList.add(getIdSet(state));
		tagMap.put(state.hashCode(), false);
		return idSetList.size() - 1;
	}
	
	public boolean hasUntagState() {
		return tagMap.containsValue(false);	
	}
	
	public void tagState(Set<Vertex<Integer>> state) {
		tagMap.put(state.hashCode(), true);
	}
	
	public Set<Vertex<Integer>> getUntagState() {
		int i = 0;
		Iterator<Set<Vertex<Integer>>> itr = statesSet.iterator();
		Set<Vertex<Integer>> state;
		while(itr.hasNext()) {
			state  = itr.next();
			if(tagMap.get(state.hashCode()) == false) {
				this.currentStateId = i;
				return state;
			}
			++i;
		}
		currentStateId = -1;
		return null;			
	}
	
	public int getCurrentStateID() {
		return this.currentStateId;
	}
	
	public int inState(Set<Vertex<Integer>> state) {
		Set<Integer> iSet = this.getIdSet(state);
		int length = idSetList.size();
		for(int stateID = 0; stateID < length; stateID++) {
			if(idSetList.get(stateID).equals(iSet)) {
				return stateID;
			}
		}
		return INVALID_ID;	
	}
	
	public Set<Vertex<Integer>> getState(int stateID) {
		return statesSet.get(stateID);	
	}
	
	private Set<Integer> getIdSet(Set<Vertex<Integer>> state) {
		Set<Integer> idSet = new HashSet<Integer>();
		Iterator<Vertex<Integer>> itr = state.iterator();
		while(itr.hasNext()) {
			idSet.add(itr.next().getData());
		}
		return idSet;
	}
	
}