package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

import uk.me.sample.android.ttrscoreboard.R;

import android.graphics.Color;


public class BoardRules {
	private int id;
	private int name;
	private int minPlayers;
	
	private ArrayList<BoardBonus> bonuses = new ArrayList<BoardBonus>();
	private ArrayList<Integer> routeScores = new ArrayList<Integer>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	
	public BoardRules(int id, int nameStringRes, int minPlayers) {
		this.name = nameStringRes;
		this.minPlayers = minPlayers;
	}

	public BoardRules(int id, int nameStringRes, int minPlayers, BoardBonus[] bonuses) {
		this.name = nameStringRes;
		this.minPlayers = minPlayers;
		setBonuses(bonuses);
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public int getName() {
		return name;
	}

	public void setName(int nameStringRes) {
		this.name = nameStringRes;
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
	
	public BoardBonus getBonus(int bonusId) {
		for (int i=0; i < bonuses.size() ;i++) {
			BoardBonus bonus = bonuses.get(i);
			if (bonus.getId() == bonusId) {
				return bonus;
			}
		}
		return null;
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

	// STATIC
	
	public static final int BOARD_USA = 1;
	public static final int BOARD_EUROPE = 2;
	public static final int BOARD_SWITZERLAND = 3;
	public static final int BOARD_INDIA = 4;
	public static final int BOARD_ASIA = 5;
	
	public static ArrayList<BoardRules> knownBoards() {
		ArrayList<BoardRules> boards = new ArrayList<BoardRules>();
		
    	BoardRules europe = new BoardRules(BOARD_EUROPE, R.string.map_europe, 2);
    	Player[] players = {
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, R.string.colour_blue, Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, R.string.colour_green, Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	europe.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 0, 15, 0, 21 };
    	europe.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_longestroute, R.string.bonus_longestroute_title);
    	bonus.setDescription(R.string.bonus_longestroute_description);
    	bonus.setMaxNumberOfWinners(1);
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
