package uk.me.sample.android.ttrscoreboard;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class BoardSelectionOnClickListener implements OnClickListener {
	Activity ctx;
	BoardRules board;
	
	public BoardSelectionOnClickListener(Activity ctx) {
		super();
		this.ctx = ctx;
	}
	
	/*
	 * This onClick event is a listener for the board selection screen
	 */
	@Override
	public void onClick(View button) {
		this.board = (BoardRules) button.getTag();
		PlayerSelection playerScreen = new PlayerSelection(ctx, board);
	}

}
