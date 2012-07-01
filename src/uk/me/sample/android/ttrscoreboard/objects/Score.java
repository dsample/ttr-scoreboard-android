package uk.me.sample.android.ttrscoreboard.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
	public int reasonId;
	public int reasonData;
	public Integer score;
	
	public Score(int reasonId, int reasonData, int score) {
		this.reasonId = reasonId;
		this.reasonData = reasonData;
		this.score = score;
	}
	
	// PARCELABLE
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(reasonId);
		dest.writeInt(reasonData);
		dest.writeInt(score);
	}
	
	public Score(Parcel in) {
		reasonId = in.readInt();
		reasonData = in.readInt();
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
