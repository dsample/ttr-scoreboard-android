package uk.me.sample.android.ttrscoreboard.objects;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
	public long timestamp;
	public int reasonId;
	public int reasonData;
	public Integer score;
	
	public Score(int reasonId, int reasonData, int score) {
		this.timestamp = Calendar.getInstance().getTimeInMillis();
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
		dest.writeLong(timestamp);
		dest.writeInt(reasonId);
		dest.writeInt(reasonData);
		dest.writeInt(score);
	}
	
	public Score(Parcel in) {
		timestamp = in.readLong();
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
