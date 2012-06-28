package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


public class Player implements Parcelable {
	public int id;
	public int name;
	public int colour;
	
	
	protected ArrayList<Score> scores;

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", colour=" + colour + "]";
	}

	public Player(int id, int nameStringRes, int colour) {
		super();
		this.id = id;
		this.name = nameStringRes;
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
	
	public void newScore(int reasonId, String reason, int score) {
		scores.add(new Score(reasonId, reason, score));
	}
	public void newScore(int reasonId, int reasonData, int score) {
		scores.add(new Score(reasonId, reasonData, score));
	}
	
	/**
	 * 
	 * @return 1 = removed, -1 = Score not found, -2 = Not specific enough 
	 */
	public int removeScore(int reasonId) {
		int foundCount = 0;
		int foundPosition = -1;
		for (int i=0; i < scores.size() ;i++) {
			Score score = scores.get(i);
			if (score.reasonId == reasonId) {
				foundPosition = i;
				foundCount++;
			}
		}
		
		switch (foundCount) {
			case 0:
				return -1;
			case 1:
				scores.remove(foundPosition);
				return 1;
			default:
				return -2;
		}
	}
	
	public Score getScore(int reasonId) {
		for (int i=0; i < scores.size() ;i++) {
			Score score = scores.get(i);
			if (score.reasonId == reasonId) {
				return score;
			}
		}
		return null;
	}

	public void updateScore(int reasonId, int reasonData, int score) {
		for (int i=scores.size(); i >= 0 ;i++) {
			Score thisScore = getScore(reasonId);
			if (thisScore != null) {
				removeScore(thisScore.reasonId);
			}
			newScore(reasonId, reasonData, score);
		}
	}

	public ArrayList<Score> getScores(int reasonId) {
		ArrayList<Score> thisScores = new ArrayList<Score>();
		for (int i=0; i < scores.size() ;i++) {
			Score score = scores.get(i);
			if (score.reasonId == reasonId) {
				thisScores.add(score);
			}
		}
		return thisScores;
	}

	// STATIC
	
	public static final int PLAYER_BLUE = 1;
	public static final int PLAYER_RED = 2;
	public static final int PLAYER_YELLOW = 3;
	public static final int PLAYER_GREEN = 4;
	public static final int PLAYER_BLACK = 5;

	// PARCELABLE
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(name);
		dest.writeInt(colour);
		dest.writeTypedList(scores);
	}
	
	public Player(Parcel in) {
		this.scores = new ArrayList<Score>();

		id = in.readInt();
		name = in.readInt();
		colour = in.readInt();
		in.readTypedList(scores, Score.CREATOR);
	}
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}

		public Player[] newArray(int size) {
			return new Player[size];
		}
	};

}
