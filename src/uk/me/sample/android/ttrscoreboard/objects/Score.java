package uk.me.sample.android.ttrscoreboard.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
	public int reasonId;
	public int reasonData;
	public String reason;
	public Integer score;
	
	public Score(int reasonId, String reason, int score) {
		this.reasonId = reasonId;
		this.reason = reason;
		this.score = score;
		this.reasonData = 1;
	}
	
	public Score(int reasonId, int reasonData, String reason, int score) {
		this.reasonId = reasonId;
		this.reasonData = reasonData;
		this.reason = reason;
		this.score = score;
	}

	public Score(int reasonId, int reasonData, int score) {
		this.reasonId = reasonId;
		this.reasonData = reasonData;
		this.reason = "";
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
