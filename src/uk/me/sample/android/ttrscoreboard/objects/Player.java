package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;


public class Player {
	public String name;
	public Integer colour;
	
	protected ArrayList<Score> scores;

	public Player(String name, Integer colour) {
		super();
		this.name = name;
		this.colour = colour;
		this.scores = new ArrayList<Score>();
	}
	
	public int getTotalScore() {
		int score = 0;
		for (int i=0; i < scores.size() ;i++) {
			score = score + scores.get(i).score;
		}
		return score;
	}
	
	public void newScore(String reason, int score) {
		scores.add(new Score(reason, score));
	}
}
