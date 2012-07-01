package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;
import java.util.List;

import uk.me.sample.android.ttrscoreboard.objects.BoardBonus;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import uk.me.sample.android.ttrscoreboard.objects.Score;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class BonusScoringActivity extends Activity implements OnCheckedChangeListener, OnItemSelectedListener, OnClickListener {
	Game game;
	ArrayList<BoardBonus> bonuses;
	int currentBonusIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bonusscoring);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		this.game = (Game) getIntent().getParcelableExtra("game");
//		if (getIntent().getBooleanExtra("BackButton", false)) {
//		} else {
			currentBonusIndex = 0;
//		}

//		Bundle extras = getIntent().getExtras();
//		game = (Game) extras.getParcelable("game");
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		bonuses = game.getBoard().getBonuses();
		
		BoardBonus currentBonus = bonuses.get(currentBonusIndex);

		setContentView(R.layout.bonusscoring);
		
		createBonusScreen(currentBonus.getId());
/*		
		LinearLayout playerContainer = (LinearLayout) findViewById(R.id.playerContainer);
		playerContainer.removeAllViews();
		
		if (radioButtons.size() == game.playerCount()) {
			
			for (int i=0; i < game.playerCount() ;i++) {
				RelativeLayout player = playerView(game.getPlayer(i), radioButtons.get(i).isChecked());
				radioButtons.add((RadioButton) player.findViewById(R.id.radioButton));
				playerContainer.addView(player);
			}
		} else {
			radioButtons.clear();
		}
*/
	}
	
	private void createBonusScreen(int bonusId) {
		TextView title = (TextView) findViewById(R.id.bonusTitle);
		TextView description= (TextView) findViewById(R.id.bonusDescription);
		LinearLayout playerContainer = (LinearLayout) findViewById(R.id.playerContainer);
		BoardBonus bonus = game.getBonus(bonusId);
		
		title.setText(bonus.getName());
		description.setText(bonus.getDescription());
				
		// Draw players onto the screen
		playerContainer.removeAllViews();
		for (int i=0; i < game.playerCount() ;i++) {
			Player player = game.getPlayer(i);
			playerContainer.addView(getPlayerView(player, bonus));
		}
	}
	
	private RelativeLayout getPlayerView(Player player, BoardBonus bonus) {
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout l;
		
		if (bonus.getMaxNumberOfWinners() == 1) {
			// If only 1 winner then radiobutton layout
			l = (RelativeLayout) inflater.inflate(R.layout.bonusscoring_player_radio, null);

			RadioButton radiobutton = (RadioButton) l.findViewById(R.id.radioButton);
			radiobutton.setText(player.name);
			radiobutton.setTag(R.id.object_playerid, player.id);
			//radiobutton.setOnCheckedChangeListener(this);
			radiobutton.setChecked(playerBonusState(player, bonus) == 1);
			radiobutton.setOnClickListener(this);
		} else if (bonus.getMaxNumberOfWinners() > 1 && bonus.getPossibleBonusesPerWinner() == 1) {
			// If multiple winners but only 1 per winner then checkbox layout
			l = (RelativeLayout) inflater.inflate(R.layout.bonusscoring_player_checkbox, null);

			CheckBox checkbox = (CheckBox) l.findViewById(R.id.checkbox);
			checkbox.setText(player.name);
			checkbox.setTag(R.id.object_playerid, player.id);
			checkbox.setChecked(playerBonusState(player, bonus) == 1);
			checkbox.setOnCheckedChangeListener(this);
		} else {
			// If multiple winners and multiple per winner then spinner layout
			l = (RelativeLayout) inflater.inflate(R.layout.bonusscoring_player_plusminus, null);

			TextView name = (TextView) l.findViewById(R.id.name);
			name.setText(player.name);
			Spinner spinner = (Spinner) l.findViewById(R.id.count);
			List<CharSequence> spinnerValues = new ArrayList<CharSequence>();
			for (int i=0; i <= bonus.getPossibleBonusesPerWinner() ;i++) {
				spinnerValues.add(Integer.toString(i));
			}
			ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, spinnerValues);
			spinner.setAdapter(spinnerAdapter);
			spinner.setTag(R.id.object_playerid, player.id);			
			spinner.setSelection(playerBonusState(player, bonus));
			spinner.setOnItemSelectedListener(this);
		}

		l.setTag("Player " + Integer.toString(player.id));
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, calcDp(48));
		l.setLayoutParams(layoutParams);

		View colour = (View) l.findViewById(R.id.colour);
		colour.setBackgroundColor(player.colour);

		TextView thisScore = (TextView) l.findViewById(R.id.player_score);
		thisScore.setText(Integer.toString(player.getTotalScore()));

		return l;
	}

	// Find out if the checkbox should be checked when reloading a bonus
	private int playerBonusState(Player player, BoardBonus bonus) {
		Score score = player.getScore(bonus.getId());
		
		if (score != null) {
			if (bonus.getMaxNumberOfWinners() == 1) {
				// RadioButton
				return 1;
			} else if (bonus.getMaxNumberOfWinners() > 1 && bonus.getPossibleBonusesPerWinner() == 1) {
				// CheckBox
				return 1;
			} else {
				// Spinner
				return score.reasonData;
			}
		}
		return 0;
	}

/* Layout type logic
		if (score != null) {
			if (bonus.getMaxNumberOfWinners() == 1) {
				// RadioButton
			} else if (bonus.getMaxNumberOfWinners() > 1 && bonus.getPossibleBonusesPerWinner() == 1) {
				// CheckBox
			} else {
				// Spinner
			}
		}
*/
	

/*
	private void setPlayerState(int bonusId) {
		BoardBonus bonus = game.getBonus(bonusId);
		LinearLayout container = (LinearLayout) findViewById(R.id.playerContainer);
		
		for (int i=0; i < game.playerCount() ;i++) {
			Player player = game.getPlayer(i);
			Score score = player.getScore(bonusId);
			
			if (score != null) {
				if (bonus.getMaxNumberOfWinners() == 1) {
					RadioButton radio1 = (RadioButton) container.findViewWithTag(tag);
				} else {
					CheckBox cbox1 = (CheckBox) container.findViewWithTag(tag);
				}
			} else {
				if (bonus.getMaxNumberOfWinners() == 1) {
					RadioButton radio1 = (RadioButton) container.findViewWithTag(tag);
				} else {
					CheckBox cbox1 = (CheckBox) container.findViewWithTag(tag);
				}
			}
		}
	}
	
	private void setPlayerState(int bonusId, int bonusData) {
		BoardBonus bonus = game.getBonus(bonusId);
		LinearLayout container = (LinearLayout) findViewById(R.id.playerContainer);
		
		for (int i=0; i < game.playerCount() ;i++) {
			Player player = game.getPlayer(i);
			Score score = player.getScore(bonusId);
			
			if (score != null) {
				if (bonus.getMaxNumberOfWinners() == 1) {
					RadioButton radio1 = (RadioButton) container.findViewWithTag(tag);
				} else {
					CheckBox cbox1 = (CheckBox) container.findViewWithTag(tag);
				}
			} else {
				if (bonus.getMaxNumberOfWinners() == 1) {
					RadioButton radio2 = (RadioButton) container.findViewWithTag(tag);
				} else {
					CheckBox cbox2 = (CheckBox) container.findViewWithTag(tag);
				}
			}
		}
	}
*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@SuppressLint({ "AlwaysShowAction" })
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if (currentBonusIndex < (bonuses.size()-1)) {
			MenuItem continueButton = menu.add(1, R.id.button_continue, 1, R.string.button_continue);
		    continueButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		    MenuItem discardButton = menu.add(2, R.id.button_discard, 2, R.string.button_discardgame);
		    discardButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		} else {
			MenuItem continueButton = menu.add(1, R.id.button_finish, 1, R.string.button_finish);
		    continueButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		    MenuItem discardButton = menu.add(2, R.id.button_discard, 2, R.string.button_discardgame);
		    discardButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		
		if (item.getItemId() == R.id.button_continue || item.getItemId() == R.id.button_finish || item.getItemId() == android.R.id.home) {
			LinearLayout l = (LinearLayout) findViewById(R.id.playerContainer);
			for (int i=0; i < game.playerCount() ;i++) {
				Player player = game.getPlayer(i);
				RelativeLayout rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
				BoardBonus bonus = bonuses.get(currentBonusIndex);
				
				if (bonus.getMaxNumberOfWinners() == 1) {
					// RadioButton
					RadioButton radiobutton = (RadioButton) rel.findViewById(R.id.radioButton);
					if (radiobutton.isChecked()) {
						player.updateScore(bonus.getId(), 1, bonus.getScoresPerTicket().get(0));
					} else {
						player.removeScore(bonus.getId());
					}
				} else if (bonus.getMaxNumberOfWinners() > 1 && bonus.getPossibleBonusesPerWinner() == 1) {
					// CheckBox
					CheckBox checkbox = (CheckBox) rel.findViewById(R.id.checkbox);
					if (checkbox.isChecked()) {
						player.updateScore(bonus.getId(), 1, bonus.getScoresPerTicket().get(0));
					} else {
						player.removeScore(bonus.getId());
					}
				} else {
					// Spinner
					Spinner spinner = (Spinner) rel.findViewById(R.id.count);
					int selected = Integer.parseInt(spinner.getSelectedItem().toString());
					if (selected > 0) {
						int thisScore = 0;
						ArrayList<Integer> scoresPerTicket = bonus.getScoresPerTicket();
						for (int j=0; j < selected ;j++) {
							thisScore = thisScore + scoresPerTicket.get(j);
						}
						player.updateScore(bonus.getId(), selected, thisScore);
					} else {
						player.removeScore(bonus.getId());
					}
				}
			}
		}
		switch (item.getItemId()) {
			case R.id.button_continue:
				// Draw new bonus screen
				currentBonusIndex++;
				BoardBonus currentBonusForward = bonuses.get(currentBonusIndex);
				createBonusScreen(currentBonusForward.getId());
				invalidateOptionsMenu();
				break;
			case R.id.button_finish:
				intent = new Intent(this, FinalScoresActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				finish();
				break;
			case R.id.button_discard:
				Intent discardIntent = new Intent(this, BoardSelectionActivity.class);
				startActivity(discardIntent);
				finish();
				break;
			case android.R.id.home:
				if (currentBonusIndex == 0) {
					
					intent = new Intent(this, TicketScoringActivity.class);
					intent.putExtra("game", this.game);
					startActivity(intent);
					finish();
				} else {
					currentBonusIndex--;
					BoardBonus currentBonusBack = bonuses.get(currentBonusIndex);
					createBonusScreen(currentBonusBack.getId());
					invalidateOptionsMenu();
				}
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		LinearLayout l = (LinearLayout) findViewById(R.id.playerContainer);
		RelativeLayout rel;
		Integer playerId = (Integer) v.getTag(R.id.object_playerid);
		
		switch (v.getId()) {
			case R.id.radioButton:				
				for (int i=0; i < game.playerCount() ;i++) {
					Player player = game.getPlayer(i);
					rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
					RadioButton radiobutton = (RadioButton) rel.findViewById(R.id.radioButton); 
					Integer thisPlayerId = (Integer) radiobutton.getTag(R.id.object_playerid);
					
					if (!thisPlayerId.equals(playerId)) {
						radiobutton.setChecked(false);
					}
				}
				break;
		}
		
		invalidateOptionsMenu();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Check that the minimum number of players has been selected, and enable the button
		// Otherwise, disable the button
		
		LinearLayout l = (LinearLayout) findViewById(R.id.playerContainer);
		RelativeLayout rel;
		//Integer playerId = (Integer) buttonView.getTag(R.id.object_playerid);
		BoardBonus bonus = bonuses.get(currentBonusIndex);
		
		switch (buttonView.getId()) {
			case R.id.checkbox:
				int count = 0;
				
				Player player;
				CheckBox checkbox;
				
				for (int i=0; i < game.playerCount() ;i++) {
					player = game.getPlayer(i);
					rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
					checkbox = (CheckBox) rel.findViewById(R.id.checkbox); 
					
					if (checkbox.isChecked()) {
						count++;
					}
				}
				
				if (count == bonus.getMaxNumberOfWinners()) {
					for (int i=0; i < game.playerCount() ;i++) {
						player = game.getPlayer(i);
						rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
						checkbox = (CheckBox) rel.findViewById(R.id.checkbox);
						
						if (!checkbox.isChecked()) {
							checkbox.setEnabled(false);
						}
					}
				} else {
					for (int i=0; i < game.playerCount() ;i++) {
						player = game.getPlayer(i);
						rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
						checkbox = (CheckBox) rel.findViewById(R.id.checkbox);
						
						if (!checkbox.isChecked()) {
							checkbox.setEnabled(true);
						}
					}
				}
				break;
		}
		
		invalidateOptionsMenu();
	}
	
/*	
	private RelativeLayout playerView(Player player, boolean checked) {
		
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.bonusscoring_player_radio, null);

		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, calcDp(48));
		l.setLayoutParams(layoutParams);

		View colour = (View) l.findViewById(R.id.colour);
		colour.setBackgroundColor(player.colour);

		RadioButton radioButton = (RadioButton) l.findViewById(R.id.radioButton);
		radioButton.setText(player.name);
		radioButton.setTag(R.id.object_playerid, player.id);
		radioButton.setOnCheckedChangeListener(this);
		radioButton.setChecked(checked);
		
		TextView thisScore = (TextView) l.findViewById(R.id.player_score);
		thisScore.setText(Integer.toString(player.getTotalScore()));
		
		return l;
	}
*/
	
	/**
	 * Calculate the DP value for a given dimension in pixels
	 * @param pixels
	 * @return
	 */
	private int calcDp(int pixels) {
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		// TODO Auto-generated method stub
		switch (parent.getId()) {
			case R.id.count:
				LinearLayout l = (LinearLayout) findViewById(R.id.playerContainer);
				RelativeLayout rel;
				BoardBonus bonus = bonuses.get(currentBonusIndex);
				int count = 0;
						
				Player player;
				Spinner spinner;
						
				for (int i=0; i < game.playerCount() ;i++) {
					player = game.getPlayer(i);
					rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
					spinner = (Spinner) rel.findViewById(R.id.count);

					if (Integer.parseInt(spinner.getSelectedItem().toString()) > 0) {
						count++;
					}
				}

				Boolean enableOthers = (count < bonus.getMaxNumberOfWinners());
				for (int i=0; i < game.playerCount() ;i++) {
					player = game.getPlayer(i);
					rel = (RelativeLayout) l.findViewWithTag("Player " + Integer.toString(player.id));
					spinner = (Spinner) rel.findViewById(R.id.count);

					if (Integer.parseInt(spinner.getSelectedItem().toString()) == 0) {
						spinner.setEnabled(enableOthers);
					}
				}					

				break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
