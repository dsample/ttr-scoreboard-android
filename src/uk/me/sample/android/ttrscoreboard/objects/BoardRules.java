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
		this.id = id;
		this.name = nameStringRes;
		this.minPlayers = minPlayers;
	}

	public BoardRules(int id, int nameStringRes, int minPlayers, BoardBonus[] bonuses) {
		this.id = id;
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
	
	public void addBonus(BoardBonus bonus) {
		this.bonuses.add(bonus);
	}

	public void addBonuses(ArrayList<BoardBonus> bonuses) {
		for (int i=0; i < bonuses.size() ;i++) {
			addBonus(bonuses.get(i));
		}
	}
	
	public void removeBonus(int bonusId) {
		for (int i=0; i < bonuses.size() ;i++) {
			if (bonuses.get(i).getId() == bonusId) {
				bonuses.remove(i);
				break;
			}
		}
	}
	
	public void removeBonuses(ArrayList<Integer> bonusIds) {
		for (int i=0; i < bonusIds.size() ;i++) {
			removeBonus(bonusIds.get(i));
		}
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
	public static final int BOARD_LEGENDARY_ASIA = 6;
	public static final int BOARD_MARKLIN = 7;
	public static final int BOARD_NORDIC = 8;
	
	public static ArrayList<BoardRules> knownBoards() {
		ArrayList<BoardRules> boards = new ArrayList<BoardRules>();
		
    	boards.add(rulesEurope());
    	boards.add(rulesUsa());
    	boards.add(rulesNordic());
    	boards.add(rulesSwiss());
    	boards.add(rulesIndia());
    	boards.add(rulesMarklin());
    	
		return boards;
		
	}
	
	private static BoardRules rulesEurope() {
    	BoardRules board = new BoardRules(BOARD_EUROPE, R.string.map_europe, 2);
    	Player[] players = {
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, R.string.colour_blue, Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, R.string.colour_green, Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	board.setPlayers(players);
    	Integer[] europeRouteScores = { 1, 2, 4, 7, 0, 15, 0, 21 };
    	board.setRouteScores(europeRouteScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_longestroute, R.string.bonus_longestroute_title);
    	bonus.setDescription(R.string.bonus_longestroute_description);
    	bonus.setMaxNumberOfWinners(5);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] europeBonusScores = { 10 };
    	bonus.setScoresPerTicket(europeBonusScores);

    	BoardBonus bonusStations = new BoardBonus(R.id.score_bonus_stations, R.string.bonus_stations_title);
    	bonusStations.setDescription(R.string.bonus_stations_description);
    	bonusStations.setMaxNumberOfWinners(5);
    	bonusStations.setPossibleBonusesPerWinner(3);
    	Integer[] europeBonusStationsScores = { 4, 4, 4 };
    	bonusStations.setScoresPerTicket(europeBonusStationsScores);

    	BoardBonus[] bonuses = { bonus, bonusStations };
    	board.setBonuses(bonuses);
    	
    	return board;
	}
	
	private static BoardRules rulesUsa() {
    	BoardRules board = new BoardRules(BOARD_USA, R.string.map_usa, 2);
    	Player[] players = {
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, R.string.colour_blue, Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, R.string.colour_green, Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	board.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 10, 15 };
    	board.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_longestroute, R.string.bonus_longestroute_title);
    	bonus.setDescription(R.string.bonus_longestroute_description);
    	bonus.setMaxNumberOfWinners(5);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	BoardBonus[] bonuses = { bonus };
    	board.setBonuses(bonuses);
    	
    	return board;
	}
	
	private static BoardRules rulesNordic() {
    	BoardRules board = new BoardRules(BOARD_NORDIC, R.string.map_nordic, 2);
    	Player[] players = {
    		new Player(Player.PLAYER_WHITE, R.string.colour_white, Color.parseColor("#FFFFFF")),
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_PURPLE, R.string.colour_purple, Color.parseColor("#8000FF")) };
    	board.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 10, 15, 0, 0, 27 };
    	board.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_globetrotter, R.string.bonus_globetrotter_title);
    	bonus.setDescription(R.string.bonus_globetrotter_description);
    	bonus.setMaxNumberOfWinners(3);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);

    	BoardBonus[] bonuses = { bonus };
    	board.setBonuses(bonuses);
    	
    	return board;
	}
	
	private static BoardRules rulesSwiss() {
    	// Switzerland
    	BoardRules board = new BoardRules(BOARD_SWITZERLAND, R.string.map_switzerland, 2);
    	// board.setMaxPlayers = 3;
    	Player[] players = {
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, R.string.colour_blue, Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, R.string.colour_green, Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	board.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 10, 15 };
    	board.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_longestroute, R.string.bonus_longestroute_title);
    	bonus.setDescription(R.string.bonus_longestroute_description);
    	bonus.setMaxNumberOfWinners(5);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	BoardBonus[] bonuses = { bonus };
    	board.setBonuses(bonuses);

    	return board;
	}
	
	private static BoardRules rulesIndia() {
    	BoardRules board = new BoardRules(BOARD_INDIA, R.string.map_india, 2);
    	// board.setMaxPlayers = 4;
    	Player[] players = { 
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_BLUE, R.string.colour_blue, Color.parseColor("#0099CC")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_GREEN, R.string.colour_green, Color.parseColor("#669900")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	board.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 0, 15, 0, 21 };
    	board.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_longestroute, R.string.bonus_indiaexpress_title);
    	bonus.setDescription(R.string.bonus_indiaexpress_description);
    	bonus.setMaxNumberOfWinners(5);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	
    	BoardBonus mandala = new BoardBonus(R.id.score_bonus_mandala, R.string.bonus_mandala_title);
    	mandala.setDescription(R.string.bonus_mandala_description);
    	mandala.setMaxNumberOfWinners(4);
    	mandala.setPossibleBonusesPerWinner(5);
    	Integer[] mandalaScores = { 5, 5, 10, 10, 10 };
    	mandala.setScoresPerTicket(mandalaScores);
    	
    	BoardBonus[] bonuses = { bonus, mandala };
    	board.setBonuses(bonuses);
    	
    	return board;
	}
	
	private static BoardRules rulesMarklin() {
    	BoardRules board = new BoardRules(BOARD_MARKLIN, R.string.map_marklin, 2);
    	Player[] players = {
    		new Player(Player.PLAYER_WHITE, R.string.colour_white, Color.parseColor("#FFFFFF")),
    		new Player(Player.PLAYER_RED, R.string.colour_red, Color.parseColor("#CC0000")),
    		new Player(Player.PLAYER_PURPLE, R.string.colour_purple, Color.parseColor("#8000FF")),
    		new Player(Player.PLAYER_YELLOW, R.string.colour_yellow, Color.parseColor("#FFBB33")),
    		new Player(Player.PLAYER_BLACK, R.string.colour_black, Color.parseColor("#000000")) };
    	board.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 10, 15, 18 };
    	board.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus(R.id.score_bonus_globetrotter, R.string.bonus_mostcompletedtickets_title);
    	bonus.setDescription(R.string.bonus_mostcompletedtickets_description);
    	bonus.setMaxNumberOfWinners(5);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	BoardBonus[] bonuses = { bonus };
    	board.setBonuses(bonuses);

    	return board;
	}
	
	//private static BoardRules rulesAsia() {}
	//private static BoardRules rulesLegendaryAsia() {}
	
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

	public static ArrayList<Expansion> knownExpansions() {
		ArrayList<Expansion> expansions = new ArrayList<Expansion>();
		
		expansions.add(expansionEuropa1912());
		expansions.add(expansionUsa1910());
		expansions.add(expansionAlvinAndDexter());
		expansions.add(expansionWarehousesAndDepots());
		
		return expansions;
	}
	
	public static int EXPANSION_ALVINANDDEXTER = 1;
	private static Expansion expansionAlvinAndDexter() {
		BoardBonus alvin = new BoardBonus(R.id.score_bonus_alvin, R.string.bonus_alvin_title);
		alvin.setDescription(R.string.bonus_alvin_description);
		alvin.setMaxNumberOfWinners(5);
		alvin.setPossibleBonusesPerWinner(1);
		Integer[] alvinScores = { 10 };
		alvin.setScoresPerTicket(alvinScores);

		BoardBonus dexter = new BoardBonus(R.id.score_bonus_dexter, R.string.bonus_dexter_title);
		dexter.setDescription(R.string.bonus_dexter_description);
		dexter.setMaxNumberOfWinners(5);
		dexter.setPossibleBonusesPerWinner(1);
		Integer[] dexterScores = { 10 };
		alvin.setScoresPerTicket(dexterScores);

		BoardBonus[] bonuses = { alvin, dexter }; 
		Expansion expansion = new Expansion(EXPANSION_ALVINANDDEXTER, 0, R.string.expansion_alvinanddexter, bonuses);
		return expansion;
	}

	public static int EXPANSION_EUROPA1912 = 2;
	private static Expansion expansionEuropa1912() {
		BoardBonus bonus = new BoardBonus(R.id.score_bonus_unuseddepots, R.string.bonus_unuseddepots_title);
		bonus.setDescription(R.string.bonus_unuseddepots_description);
		bonus.setMaxNumberOfWinners(5);
		bonus.setPossibleBonusesPerWinner(1);
		Integer[] bonusScores = { 10 };
		bonus.setScoresPerTicket(bonusScores);

		BoardBonus[] bonuses = { bonus }; 
		Expansion expansion = new Expansion(EXPANSION_EUROPA1912, BOARD_EUROPE, R.string.expansion_europa1912, bonuses);
		return expansion;
	}

	public static int EXPANSION_WAREHOUSESANDDEPOTS = 4;
	private static Expansion expansionWarehousesAndDepots() {
		BoardBonus bonus = new BoardBonus(R.id.score_bonus_unuseddepots, R.string.bonus_unuseddepots_title);
		bonus.setDescription(R.string.bonus_unuseddepots_description);
		bonus.setMaxNumberOfWinners(5);
		bonus.setPossibleBonusesPerWinner(1);
		Integer[] bonusScores = { 10 };
		bonus.setScoresPerTicket(bonusScores);

		BoardBonus[] bonuses = { bonus };

		// This will remove the Unused Depots bonus if the Europe1912 expansion is also selected
		Integer[] removeBonuses = { R.id.score_bonus_unuseddepots };
		
		Expansion expansion = new Expansion(EXPANSION_WAREHOUSESANDDEPOTS, 0, R.string.expansion_warehousesanddepots, bonuses, removeBonuses);
		return expansion;
	}
	
	public static int EXPANSION_USA1910 = 5;
	private static Expansion expansionUsa1910() {
		BoardBonus bonus = new BoardBonus(R.id.score_bonus_globetrotter, R.string.bonus_globetrotter_title);
		bonus.setDescription(R.string.bonus_globetrotter_description);
		bonus.setMaxNumberOfWinners(5);
		bonus.setPossibleBonusesPerWinner(1);
		Integer[] bonusScores = { 10 };
		bonus.setScoresPerTicket(bonusScores);
		
		BoardBonus[] newBonuses = { bonus };
		
		Integer[] removeBonuses = { R.id.score_bonus_longestroute };

		Expansion expansion = new Expansion(EXPANSION_USA1910, BOARD_USA, R.string.expansion_usa1910, newBonuses, removeBonuses);
		return expansion;
	}

	public static Expansion getExpansion(int expansionId) {
		ArrayList<Expansion> expansions = knownExpansions();
		
		for (int i=0; i < expansions.size() ;i++) {
			Expansion expansion = expansions.get(i);
			if (expansion.id == expansionId) {
				return expansion;
			}
		}
		return null;
	}
}
