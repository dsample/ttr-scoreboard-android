package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class GameActivity extends Activity 
		implements OnClickListener, OnCheckedChangeListener {

	int state;
	ArrayList<BoardRules> boards;
	Game game;
	ActionBar actionbar;
	Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boards = new ArrayList<BoardRules>();
		// TODO Load game data from storage
		game = new Game();
		
		actionbar = getActionBar();
		
		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		switch (state) {
			case R.id.state_boardselection:
				break;
			case R.id.state_playerselection:
				break;
			case R.id.state_routescoring:
				break;
			case R.id.state_bonusscoring:
				break;
			case R.id.state_finalscores:
				break;
			case R.id.state_mainmenu:
			default:
				if (true) {
					screenMainmenu();
				} else {
					screenBoardSelection();
				}
				break;
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		this.menu = menu;
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	// SCREENS
	
	private void screenMainmenu() {
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		MenuInflater inflater = new MenuInflater();
		inflater.inflate(R.menu.actionbar_game, menu);
	}
	
	private void screenBoardSelection() {
		
	}
	
	private void screenPlayerSelection() {
	}
	
	private void screenRouteScoring() {
		
	}
	
	private void screenBonusScoring() {
		
	}
	
	private void screenFinalScores() {
		
	}
}
