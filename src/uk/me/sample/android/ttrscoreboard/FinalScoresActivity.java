package uk.me.sample.android.ttrscoreboard;

import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FinalScoresActivity extends Activity {
	Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.game = (Game) getIntent().getParcelableExtra("game");

		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();
	
		for (int i=0; i < game.playerCount() ;i++) {
			container.addView(playerView(game.getPlayer(i)));
		}
	}
	
	private RelativeLayout playerView(Player player) {
		
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.player_justscores, null);

		l.setTag("Player " + player.id);

		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, calcDp(48));
		l.setLayoutParams(layoutParams);

		View colour = (View) l.findViewById(R.id.colour);
		colour.setBackgroundColor(player.colour);
		TextView name = (TextView) l.findViewById(R.id.name);
		name.setText(player.name);
		TextView thisScore = (TextView) l.findViewById(R.id.player_score);
		thisScore.setText(Integer.toString(player.getTotalScore()));
		
		return l;
	}
	
	@SuppressLint("AlwaysShowAction")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		MenuItem finishButton = menu.add(1, R.id.button_mainmenu_newgame, 1, R.string.newgame);
	    finishButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
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
			case R.id.button_mainmenu_newgame:
				Intent intent = new Intent(this, BoardSelectionActivity.class);
				startActivity(intent);
				finish();
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

}
