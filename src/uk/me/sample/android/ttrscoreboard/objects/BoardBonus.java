package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardBonus {
	private String name;
	private int numberOfWinners;

	private int possibleBonusesPerWinner;
	private ArrayList<Integer> scoresPerTicket = new ArrayList<Integer>();

	public BoardBonus(String name) {
		this.name = name;
		this.setNumberOfWinners(1);
		this.possibleBonusesPerWinner = 1;
	}

	public int getNumberOfWinners() {
		return numberOfWinners;
	}

	public void setNumberOfWinners(int numberOfWinners) {
		this.numberOfWinners = numberOfWinners;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return name;
	}

}
