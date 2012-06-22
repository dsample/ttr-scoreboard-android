package uk.me.sample.android.ttrscoreboard.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
	String reason;
	Integer score;
	
	public Score(String reason, int score) {
		this.reason = reason;
		this.score = score;
	}
	
	// PARCELABLE
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(reason);
		dest.writeInt(score);
	}
	
	public Score(Parcel in) {
		reason = in.readString();
		score = in.readInt();
	}
	
	public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
		public Score createFromParcel(Parcel in) {
			return new Score(in);
		}

		public Score[] newArray(int size) {
			return new Score[size];
		}
	};

}
