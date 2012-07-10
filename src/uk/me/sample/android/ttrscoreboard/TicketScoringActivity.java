package uk.me.sample.android.ttrscoreboard;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TicketScoringActivity extends SherlockActivity implements OnClickListener {
	Game game;
	Boolean keepKeypadOpen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		keepKeypadOpen = false;
		super.onCreate(savedInstanceState);
		
		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		
		this.game = (Game) getIntent().getParcelableExtra("game");

		setContentView(R.layout.ticketscoring);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		TextView textview = (TextView) findViewById(R.id.scoreEntry);
		textview.setTypeface(typeface);
		textview.setTextSize(16f);

		findViewById(R.id.keypad).setVisibility(View.GONE);

/*
		Bundle extras = getIntent().getExtras();
		this.game = (Game) extras.getParcelable("game");
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setHomeButtonEnabled(false);

		setContentView(R.layout.ticketscoring);
*/
    	LinearLayout container = (LinearLayout) findViewById(R.id.playerContainer);
    	//container.removeAllViews();
	
		for (int i=0; i < game.playerCount() ;i++) {
			container.addView(playerView(game.getPlayer(i)));
		}
		
		ImageButton backspaceButton = (ImageButton) findViewById(R.id.backspace);
		backspaceButton.setOnClickListener(this);
		findViewById(R.id.keypad_button0).setOnClickListener(this);
		findViewById(R.id.keypad_button1).setOnClickListener(this);
		findViewById(R.id.keypad_button2).setOnClickListener(this);
		findViewById(R.id.keypad_button3).setOnClickListener(this);
		findViewById(R.id.keypad_button4).setOnClickListener(this);
		findViewById(R.id.keypad_button5).setOnClickListener(this);
		findViewById(R.id.keypad_button6).setOnClickListener(this);
		findViewById(R.id.keypad_button7).setOnClickListener(this);
		findViewById(R.id.keypad_button8).setOnClickListener(this);
		findViewById(R.id.keypad_button9).setOnClickListener(this);
		findViewById(R.id.keypad_failed).setOnClickListener(this);
		findViewById(R.id.keypad_success).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private RelativeLayout playerView(Player player) {
		
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.routescoring_player, null);

		l.setTag("Player " + player.id);

		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, calcDp(48));
		l.setLayoutParams(layoutParams);

		View colour = (View) l.findViewById(R.id.colour);
		colour.setBackgroundColor(player.colour);
		TextView name = (TextView) l.findViewById(R.id.name);
		name.setText(player.name);
		TextView thisScore = (TextView) l.findViewById(R.id.player_score);
		thisScore.setText(Integer.toString(player.getTotalScore()));
		ImageButton addButton = (ImageButton) l.findViewById(R.id.addButton);
		addButton.setOnClickListener(this);
		addButton.setTag(R.id.object_playerid, player.id);
		
		return l;
	}
	
	@SuppressLint("AlwaysShowAction")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		MenuItem continueButton = menu.add(1, R.id.button_continue, 1, R.string.button_continue);
	    continueButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    MenuItem keepOpenOption = menu.add(2, R.id.menuitem_keepopenswitch, 2, R.string.menuitem_keepopenswitch);
	    keepOpenOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    keepOpenOption.setCheckable(true);
	    keepOpenOption.setChecked(this.keepKeypadOpen);
	    MenuItem discardButton = menu.add(2, R.id.button_discard, 2, R.string.button_discardgame);
	    discardButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem postgameswitch = menu.findItem(R.id.menuitem_keepopenswitch);
		postgameswitch.setChecked(keepKeypadOpen);
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
		switch (item.getItemId()) {
			case R.id.button_continue:
				//Intent intent = new Intent(this, BonusScoringActivity.class);
				Intent intent = new Intent(this, BonusScoringActivity.class);
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
				intent = new Intent(this, RouteScoringActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				finish();
				break;
			case R.id.menuitem_keepopenswitch:
				this.keepKeypadOpen = !item.isChecked();
				if (!keepKeypadOpen) {
					findViewById(R.id.keypad).setVisibility(View.GONE);
				}
				break;
		}
		return true;
	}

	/**
	 * Calculate the DP value for a given dimension in pixels
	 * @param pixels
	 * @return
	 */
	private int calcDp(int pixels) {
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}

	@Override
	public void onClick(View v) {
		Player player;
		switch (v.getId()) {
			case R.id.addButton:
				player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));

				findViewById(R.id.keypad).setVisibility(View.VISIBLE);
				
				TextView scoreEntry = (TextView) findViewById(R.id.scoreEntry);
				scoreEntry.setText("");
				
				findViewById(R.id.keypad).findViewById(R.id.horizontal_divider).setBackgroundColor(player.colour);

				ImageButton button = (ImageButton) findViewById(R.id.keypad_success);
				//button.getBackground().setColorFilter(player.colour, android.graphics.PorterDuff.Mode.MULTIPLY);
				button.setTag(R.id.object_playerid, player.id);

				button = (ImageButton) findViewById(R.id.keypad_failed);
				//button.getBackground().setColorFilter(player.colour, android.graphics.PorterDuff.Mode.MULTIPLY);
				button.setTag(R.id.object_playerid, player.id);
				break;
			case R.id.backspace:
				TextView scoreEntry1 = (TextView) findViewById(R.id.scoreEntry);
				String string = scoreEntry1.getText().toString();
				if (string.length() > 0) {
					scoreEntry1.setText(string.substring(0, scoreEntry1.length()-1));
				}
				break;
			case R.id.keypad_button0:
			case R.id.keypad_button1:
			case R.id.keypad_button2:
			case R.id.keypad_button3:
			case R.id.keypad_button4:
			case R.id.keypad_button5:
			case R.id.keypad_button6:
			case R.id.keypad_button7:
			case R.id.keypad_button8:
			case R.id.keypad_button9:
				Button textview = (Button) v;
				TextView scoreEntry2 = (TextView) findViewById(R.id.scoreEntry);
				scoreEntry2.setText(scoreEntry2.getText().toString() + textview.getText().toString());
				if (Integer.parseInt((String) scoreEntry2.getText()) > 99) {
					scoreEntry2.setText("99");
				}
				break;
			case R.id.keypad_success:
				TextView successScoreEntry = (TextView) findViewById(R.id.scoreEntry);
				
				if (successScoreEntry.length() > 0) {
					player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));
					
					player.newScore(R.id.score_route, 0, Integer.parseInt(successScoreEntry.getText().toString()));
					RelativeLayout successRel = (RelativeLayout) findViewById(R.id.playerContainer).findViewWithTag("Player " + v.getTag(R.id.object_playerid).toString());
					TextView successScore = (TextView) successRel.findViewById(R.id.player_score);
					successScore.setText(Integer.toString(player.getTotalScore()));
					
					successScoreEntry.setText("");
					if (!keepKeypadOpen) {
						findViewById(R.id.keypad).setVisibility(View.GONE);
					}
				}
				break;
			case R.id.keypad_failed:
				TextView failedScoreEntry = (TextView) findViewById(R.id.scoreEntry);
				
				if (failedScoreEntry.length() > 0) {
					player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));
	
					player.newScore(R.id.score_route, 0, Integer.parseInt("-" + failedScoreEntry.getText().toString()));
					RelativeLayout failedRel = (RelativeLayout) findViewById(R.id.playerContainer).findViewWithTag("Player " + v.getTag(R.id.object_playerid).toString());
					TextView failedScore = (TextView) failedRel.findViewById(R.id.player_score);
					failedScore.setText(Integer.toString(player.getTotalScore()));
	
					failedScoreEntry.setText("");
					if (!keepKeypadOpen) {
						findViewById(R.id.keypad).setVisibility(View.GONE);
					}
				}
				break;
		}
	}
}
