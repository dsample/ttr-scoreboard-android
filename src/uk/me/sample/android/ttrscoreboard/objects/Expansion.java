package uk.me.sample.android.ttrscoreboard.objects;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class Expansion implements Parcelable {
	// TODO Make Expansion class parcelable
	
	public int id;
	public int parentBoardId;
	
	public int name;
	
	public ArrayList<Integer> removeBonuses;
	public ArrayList<BoardBonus> newBonuses;
	
	public Expansion(int id, int parentBoardId, int nameRef, BoardBonus[] newBonuses) {
		this.id = id;
		this.parentBoardId = parentBoardId;
		this.name = nameRef;
		this.newBonuses = new ArrayList<BoardBonus>(Arrays.asList(newBonuses));
		this.removeBonuses = new ArrayList<Integer>();
	}

	public Expansion(int id, int parentBoardId, int nameRef, BoardBonus[] newBonuses, Integer[] removeBonuses) {
		this.id = id;
		this.parentBoardId = parentBoardId;
		this.name = nameRef;
		this.newBonuses = new ArrayList<BoardBonus>(Arrays.asList(newBonuses));
		this.removeBonuses = new ArrayList<Integer>(Arrays.asList(removeBonuses));
	}
	
	// PARCELABLE
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
	}
	
	public Expansion(Parcel in) {
		Expansion expansion = BoardRules.getExpansion(in.readInt());
		this.id = expansion.id;
		this.parentBoardId = expansion.parentBoardId;
		this.name = expansion.name;
		this.newBonuses = expansion.newBonuses;
		this.removeBonuses = expansion.removeBonuses;
	}
	
	public static final Parcelable.Creator<Expansion> CREATOR = new Parcelable.Creator<Expansion>() {
		public Expansion createFromParcel(Parcel in) {
			return new Expansion(in);
		}

		public Expansion[] newArray(int size) {
			return new Expansion[size];
		}
	};

}
