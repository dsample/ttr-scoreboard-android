package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardRules {
	private String name = "Ticket To Ride";
	private int minPlayers = 2;
	
	private ArrayList<BoardBonus> bonuses = new ArrayList<BoardBonus>();
	private ArrayList<Integer> routeScores = new ArrayList<Integer>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public BoardRules(String name, int minPlayers) {
		this.name = name;
		this.minPlayers = minPlayers;
	}

	public BoardRules(String name, int minPlayers, BoardBonus[] bonuses) {
		this.name = name;
		this.minPlayers = minPlayers;
		setBonuses(bonuses);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return players.size();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = new ArrayList<Player>(Arrays.asList(players));
	}

	public ArrayList<BoardBonus> getBonuses() {
		return bonuses;
	}

	public void setBonuses(BoardBonus[] bonuses) {
		this.bonuses = new ArrayList<BoardBonus>(Arrays.asList(bonuses));
	}

	public ArrayList<Integer> getRouteScores() {
		return routeScores;
	}

	/**
	 * 
	 * 
	 * @param routeScores an array of scores for the number of trains in the route, a score of 0 counts as an invalid route length
	 */
	public void setRouteScores(Integer[] routeScores) {
		this.routeScores = new ArrayList<Integer>(Arrays.asList(routeScores));
	}
	
	public int getMinRouteLength() {
		for (int i=0; i < routeScores.size() ;i++) {
			if (routeScores.get(i) > 0) {
				return i + 1;
			}
		}
		return 0;
	}

	public int getMaxRouteLength() {
		for (int i=routeScores.size()-1; i >= 0 ;i--) {
			if (routeScores.get(i) > 0) {
				return i + 1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return name;
	}
}
