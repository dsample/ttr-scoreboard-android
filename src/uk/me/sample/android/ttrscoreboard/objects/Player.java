package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;


public class Player implements Parcelable {
	public int id;
	public String name;
	public Integer colour;
	
	protected ArrayList<Score> scores;

	public Player(int id, String name, Integer colour) {
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
		dest.writeParcelableArray((Score[]) scores.toArray(), flags);
	}
	
	public Player(Parcel in) {
		id = in.readInt();
		name = in.readString();
		colour = in.readInt();
		Score[] scoreArray;
		in.readParcelableArray(scoreArray);
		scores = new ArrayList<Score>((ArrayList<Score>) Arrays.asList(scoreArray));
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
