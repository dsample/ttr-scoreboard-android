package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;
import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;

public class GameActivity extends Activity 
		implements OnClickListener, OnCheckedChangeListener, OnMenuItemClickListener {

	int state;
	ArrayList<BoardRules> boards;
	Game game;
	ActionBar actionbar;
	Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("TTR", "onCreate beginning");
		super.onCreate(savedInstanceState);
		boards = new ArrayList<BoardRules>();
		this.boards = BoardRules.knownBoards();
		// TODO Load game data from storage
		game = new Game();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		Long started = prefs.getLong("started", 0);
		Long nowTime = System.currentTimeMillis();
		Long maxSessionLength = (long) (1000 * 60 * 30);
		state = prefs.getInt("state", R.id.state_boardselection);

		if ((started > 0) && (nowTime - started > maxSessionLength)) {
			state = R.id.state_mainmenu;
		}
		
		actionbar = getActionBar();
		
		setContentView(R.layout.main);
		Log.d("TTR", "onCreate end");
	}

	@Override
	protected void onResume() {
		Log.d("TTR", "onResume beginning");
		super.onResume();

		switch (state) {
			case R.id.state_boardselection:
				screenBoardSelection();
				break;
			case R.id.state_playerselection:
				screenPlayerSelection();
				break;
			case R.id.state_routescoring:
				screenRouteScoring();
				break;
			case R.id.state_bonusscoring:
				screenBonusScoring();
				break;
			case R.id.state_finalscores:
				screenFinalScores();
				break;
			case R.id.state_mainmenu:
			default:
				// If a game is already started then ask whether to continue or create a new game
				if (true) {
					screenMainmenu();
				} else {
					screenBoardSelection();
				}
				break;
		}
	}

	@Override
	protected void onDestroy() {
		Log.d("TTR", "onDestroy beginning");
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d("TTR", "onPause beginning");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("TTR", "onCreateOptionsMenu beginning");
		this.menu = menu;
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_boardselection, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d("TTR", "onCheckedChanged beginning");
		switch (buttonView.getId()) {
			case R.id.button_playerselection_continue:
				// Check that the minimum number of players has been selected, and enable the button
				// Otherwise, disable the button
				
				if (isChecked) {
					game.addPlayer((Player) buttonView.getTag());
				} else {
					game.removePlayer((Player) buttonView.getTag());
				}
				
				MenuItem continueMenuItem = (MenuItem) findViewById(R.id.player_selection_continuebutton);
				
				if (this.game.playerCount() >= 2) {
					continueMenuItem.setEnabled(true);
				} else {
					continueMenuItem.setEnabled(false);
				}

				break;
		}

	}

	@Override
	public void onClick(View v) {
		Log.d("TTR", "onClick beginning");
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.button_mainmenu_newgame:
				this.game = new Game();
				screenBoardSelection();
				break;
			case R.id.button_mainmenu_continuegame:
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				state = prefs.getInt("state", R.id.state_boardselection);
				onResume();
				break;
			case R.id.button_boardselection_continue:
				this.game.setBoard((BoardRules) v.getTag());
				screenPlayerSelection();
				break;
			case R.id.button_playerselection_continue:
				screenRouteScoring();
				break;
			case R.id.button_routescoring_endgame:
				screenBonusScoring();
				break;
			case R.id.button_bonusscoring_finish:
				screenFinalScores();
				break;
			case R.id.button_finalscores_finish:
				screenMainmenu();
				break;
			case R.id.button_discard:
				// Ask them if they're sure
				// Delete the saved game data
				screenMainmenu();
				break;
		}
	}

	/**
	 * Calculate the DP value for a given dimension in pixels
	 * @param pixels
	 * @return
	 */
	private int calcDp(int pixels) {
		Log.d("TTR", "calcDp beginning");
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}

	// SCREENS
	
	private void screenMainmenu() {
		Log.d("TTR", "screenMainmenu beginning");
		state = R.id.state_mainmenu;
		setContentView(R.layout.main);
    	LinearLayout l = (LinearLayout) findViewById(R.id.container);
    	l.removeAllViews();

		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		menu.clear();

		Button b = new Button(this);
		b.setText(R.string.newgame);
		b.setId(R.id.button_mainmenu_newgame);
		b.setOnClickListener(this);
		l.addView(b);

		b = new Button(this);
		b.setText(R.string.continuegame);
		b.setId(R.id.button_mainmenu_continuegame);
		b.setOnClickListener(this);
		l.addView(b);
	}
	
	private void screenBoardSelection() {
		Log.d("TTR", "screenBoardSelection beginning");
		state = R.id.state_boardselection;
		setContentView(R.layout.main);
    	LinearLayout l = (LinearLayout) findViewById(R.id.container);
    	l.removeAllViews();

		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
    	
    	for (int i=0; i < boards.size() ;i++) {
    		BoardRules board = boards.get(i);
    		Button b = new Button(this);
    		b.setText(board.getName());
    		b.setTag(board);
    		b.setId(R.id.button_boardselection_continue);
    		b.setOnClickListener(this);
    		l.addView(b);
    	}

	}
	
	private void screenPlayerSelection() {
		Log.d("TTR", "screenPlayerSelection beginning");
		state = R.id.state_playerselection;
		setContentView(R.layout.main);
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();

		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		
		menu.clear();
		MenuItem menuitem = menu.add(Menu.NONE, R.id.player_selection_continuebutton, Menu.NONE, R.string.button_continue);

		int rowHeight = calcDp(48);

		ArrayList<Player> players = game.getBoard().getPlayers();
		container.removeAllViews();
		
		for (int i=0; i < players.size() ;i++) {
			Player player = players.get(i);
			
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.HORIZONTAL);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight);
			layoutParams.gravity = 0x10;
			l.setLayoutParams(layoutParams);
			TextView textview = new TextView(this);
			textview.setText("");
			textview.setBackgroundColor(player.colour.hashCode());
			LayoutParams textviewParams = new LayoutParams(calcDp(3), LayoutParams.MATCH_PARENT);
			textviewParams.gravity = 0x10;
			textview.setLayoutParams(textviewParams);
			l.addView(textview);
			
			CheckBox checkbox = new CheckBox(this);
			LayoutParams checkboxParams = new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight);
			checkboxParams.gravity = 0x10;
			checkbox.setLayoutParams(checkboxParams);
			checkbox.setId(R.id.player_selection_checkbox);
			checkbox.setTag(player);
			checkbox.setText(player.name);
			checkbox.setChecked(false);
			checkbox.setOnCheckedChangeListener(this);
			l.addView(checkbox);
			
			container.addView(l);
		}

	}
	
	private void screenRouteScoring() {
		Log.d("TTR", "screenRouteScoring beginning");
		state = R.id.state_routescoring;
		setContentView(R.layout.main);
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();
    	
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		menu.clear();
		MenuItem menuitem = menu.add(Menu.NONE, R.id.button_routescoring_endgame, Menu.NONE, R.string.button_endgame);

	}
	
	private void screenBonusScoring() {
		Log.d("TTR", "screenBonusScoring beginning");
		state = R.id.state_bonusscoring;
		setContentView(R.layout.main);
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();
    	
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		menu.clear();
		MenuItem menuitem = menu.add(Menu.NONE, R.id.button_bonusscoring_finish, Menu.NONE, R.string.button_bonusscoring_finish);

	}
	
	private void screenFinalScores() {
		Log.d("TTR", "screenFinalScores beginning");
		state = R.id.state_finalscores;
		setContentView(R.layout.main);
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();
    	
		actionbar.setHomeButtonEnabled(false);
		actionbar.setDisplayHomeAsUpEnabled(false);
		menu.clear();
		MenuItem menuitem = menu.add(Menu.NONE, R.id.player_selection_continuebutton, Menu.NONE, R.string.button_continue);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Log.d("TTR", "onMenuItemClick beginning");
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
			case R.id.button_mainmenu_newgame:
				this.game = new Game();
				screenBoardSelection();
				break;
			case R.id.button_mainmenu_continuegame:
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				state = prefs.getInt("state", R.id.state_boardselection);
				onResume();
				break;
			case R.id.button_playerselection_continue:
				screenRouteScoring();
				break;
			case R.id.button_routescoring_endgame:
				screenBonusScoring();
				break;
			case R.id.button_bonusscoring_finish:
				screenFinalScores();
				break;
			case R.id.button_finalscores_finish:
				screenMainmenu();
				break;
			case R.id.button_discard:
				// Ask them if they're sure
				// Delete the saved game data
				screenMainmenu();
				break;
		}
		return true;
	}
}