package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Calendar;


public class Game {
	private int timeInSeconds;
	private BoardRules board;
	private ArrayList<Player> players;
	
	public Game() {
		this.players = new ArrayList<Player>();
		Calendar c = Calendar.getInstance(); 
		timeInSeconds = c.get(Calendar.SECOND);
	}
	
	public int playerCount() {
		return players.size();
	}
	
	public void setBoard(BoardRules board) {
		this.board = board;
	}
	public BoardRules getBoard() {
		return board;
	}
	
	private int indexOfPlayer(Player player) {
		for (int i=0; i < this.players.size() ;i++) {
			if (this.players.get(i).colour == player.colour) {
				return i;
			}
		}
		return -1;
	}
	
	public void addPlayer(Player player) {
		if (indexOfPlayer(player) == -1) {
			players.add(player);
		}
	}
	
	public void removePlayer(Player player) {
		int position = indexOfPlayer(player);
		if (position > -1) {
			players.remove(position);
		}
	}
}
