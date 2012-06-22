package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;


public class Player implements Parcelable {
	public int id;
	public String name;
	public int colour;
	
	protected ArrayList<Score> scores;

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", colour=" + colour + "]";
	}

	public Player(int id, String name, int colour) {
		super();
		this.id = id;
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
		dest.writeString(name);
		dest.writeInt(colour);
		dest.writeTypedList(scores);
	}
	
	public Player(Parcel in) {
		this.scores = new ArrayList<Score>();

		id = in.readInt();
		name = in.readString();
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
