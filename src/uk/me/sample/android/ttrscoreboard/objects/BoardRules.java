package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;


public class BoardRules {
	private int id;
	private String name = "Ticket To Ride";
	private int minPlayers = 2;
	
	private ArrayList<BoardBonus> bonuses = new ArrayList<BoardBonus>();
	private ArrayList<Integer> routeScores = new ArrayList<Integer>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	
	public BoardRules(int id, String name, int minPlayers) {
		this.name = name;
		this.minPlayers = minPlayers;
	}

	public BoardRules(int id, String name, int minPlayers, BoardBonus[] bonuses) {
		this.name = name;
		this.minPlayers = minPlayers;
		setBonuses(bonuses);
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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

	// STATIC
	
	public static final int BOARD_USA = 1;
	public static final int BOARD_EUROPE = 2;
	public static final int BOARD_SWITZERLAND = 3;
	public static final int BOARD_INDIA = 4;
	public static final int BOARD_ASIA = 5;
	
	public static ArrayList<BoardRules> knownBoards() {
		ArrayList<BoardRules> boards = new ArrayList<BoardRules>();
		
    	BoardRules europe = new BoardRules(BOARD_EUROPE, "Europe", 2);
    	Player[] players = {
    		new Player(Player.PLAYER_RED, "Red", Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, "Blue", Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, "Yellow", Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, "Green", Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, "Black", Color.parseColor("#000000")) };
    	europe.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 0, 15, 0, 21 };
    	europe.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus("Longest route");
    	bonus.setNumberOfWinners(1);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	BoardBonus[] bonuses = { bonus };
    	europe.setBonuses(bonuses);
    	
    	boards.add(europe);
		
		return boards;
		
	}
	
	public static BoardRules getBoard(int boardId) {
		ArrayList<BoardRules> boards = knownBoards();
		
		for (int i=0; i < boards.size() ;i++) {
			BoardRules board = boards.get(i);
			if (board.getId() == boardId) {
				return board;
			}
		}
		return null;
	}
}
