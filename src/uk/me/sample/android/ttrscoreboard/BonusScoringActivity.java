package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardBonus;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class BonusScoringActivity extends Activity implements OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {
	Game game;
	ArrayList<BoardBonus> bonuses;
	int currentBonusIndex;
	BoardBonus currentBonus;
	
	ArrayList<RadioButton> radioButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		this.game = (Game) getIntent().getParcelableExtra("game");
		radioButtons = new ArrayList<RadioButton>();

		currentBonusIndex = 0;

//		Bundle extras = getIntent().getExtras();
//		game = (Game) extras.getParcelable("game");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		bonuses = game.getBoard().getBonuses();
		
		currentBonus = bonuses.get(currentBonusIndex);

		setContentView(R.layout.bonusscoring);
		
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.playerContainer);
		radioGroup.removeAllViews();
		
		if (radioButtons.size() == game.playerCount()) {
			
			for (int i=0; i < game.playerCount() ;i++) {
				RelativeLayout player = playerView(game.getPlayer(i), radioButtons.get(i).isChecked());
				radioButtons.add((RadioButton) player.findViewById(R.id.radioButton));
				radioGroup.addView(player);
			}
		} else {
			radioButtons.clear();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		MenuItem continueButton = menu.add(1, R.id.button_continue, 1, R.string.button_continue);
	    continueButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    MenuItem discardButton = menu.add(2, R.id.button_discard, 2, R.string.button_discardgame);
	    discardButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
		switch (item.getItemId()) {
			case R.id.button_continue:
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
				intent = new Intent(this, TicketScoringActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				finish();
				break;
		}
		return true;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Check that the minimum number of players has been selected, and enable the button
		// Otherwise, disable the button
		
		Log.d("checked", "compoundbutton");
		
		if (isChecked) {
			//game.addPlayer((Player) buttonView.getTag());
		} else {
			//game.removePlayer((Player) buttonView.getTag());
		}
		
		invalidateOptionsMenu();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		Log.d("checked", "radiogroup");
	}
	
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

	/**
	 * Calculate the DP value for a given dimension in pixels
	 * @param pixels
	 * @return
	 */
	private int calcDp(int pixels) {
		Log.d("TTR", "calcDp beginning");
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}

}
