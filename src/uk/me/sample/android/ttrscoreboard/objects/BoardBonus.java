package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardBonus {
	private int id;
	private int name;
	private int description;
	private int maxNumberOfWinners;

	private int possibleBonusesPerWinner;
	private ArrayList<Integer> scoresPerTicket = new ArrayList<Integer>();

	public BoardBonus(int id, int name) {
		this.id = id;
		this.name = name;
		this.setMaxNumberOfWinners(1);
		this.possibleBonusesPerWinner = 1;
	}
	
	public BoardBonus(int id, int name, int maxNumberOfWinners, int possibleBonusesPerWinner, Integer[] scoresPerTicket) {
		this.id = id;
		this.name = name;
		this.maxNumberOfWinners = maxNumberOfWinners;
		this.possibleBonusesPerWinner = possibleBonusesPerWinner;
		this.setScoresPerTicket(scoresPerTicket);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getMaxNumberOfWinners() {
		return maxNumberOfWinners;
	}

	public void setMaxNumberOfWinners(int numberOfWinners) {
		this.maxNumberOfWinners = numberOfWinners;
	}

	public int getName() {
		return name;
	}

	public void setName(int stringRes) {
		this.name = stringRes;
	}
	
	public int getDescription() {
		return description;
	}

	public void setDescription(int stringRes) {
		this.description = stringRes;
	}

	public int getPossibleBonusesPerWinner() {
		return possibleBonusesPerWinner;
	}

	public void setPossibleBonusesPerWinner(int possibleBonusesPerWinner) {
		this.possibleBonusesPerWinner = possibleBonusesPerWinner;
	}

	public ArrayList<Integer> getScoresPerTicket() {
		return scoresPerTicket;
	}

	public void setScoresPerTicket(Integer[] scoresPerTicket) {
		this.scoresPerTicket = new ArrayList<Integer>(Arrays.asList(scoresPerTicket));
	}

	/**
	 * Calculates the score for the player depending on the number of the given
	 * bonus issued to the player.
	 * 
	 * @param bonuses the number of bonuses issued to the player 
	 * @return the score for the bonus issued to the player
	 */
	public int calculateScore(int bonuses) {
		int score = 0;

		if (bonuses > possibleBonusesPerWinner) {
			bonuses = possibleBonusesPerWinner;
		}
		
		for (int i=0; i < bonuses ;i++) {
			score = score + scoresPerTicket.get(i);
		}
		
		return score;
	}

}
