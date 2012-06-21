package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class BoardSelectionActivity extends Activity {
	Game game;

	ArrayList<BoardRules> boards;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.boards = BoardRules.knownBoards();
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_boardselection, menu);
		return true;
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
