package uk.me.sample.android.ttrscoreboard;

import uk.me.sample.android.ttrscoreboard.objects.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	
	Game game;
	int state;
	int gameState;
	Long startedAt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("TTR", "onCreate beginning");
		super.onCreate(savedInstanceState);
		// TODO Load game data from storage
		this.game = Game.load();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		this.startedAt = prefs.getLong("started", 0);
		this.state = prefs.getInt("state", R.id.state_boardselection);
	}

	@Override
	protected void onResume() {
		Log.d("TTR", "onResume beginning");
		super.onResume();

		Intent intent;
		
		Long nowTime = System.currentTimeMillis();
		Long maxSessionLength = (long) (1000 * 60 * 30);
		
		if ((startedAt > 0) && (nowTime - startedAt > maxSessionLength)) {
			setContentView(R.layout.mainmenu);
		} else if (startedAt > 0) {		
			switch (state) {
				case R.id.state_boardselection:
					intent = new Intent(this, BoardSelectionActivity.class);
					break;
				case R.id.state_playerselection:
					intent = new Intent(this, PlayerSelectionActivity.class);
					break;
				case R.id.state_routescoring:
					intent = new Intent(this, RouteScoringActivity.class);
					break;
				case R.id.state_bonusscoring:
					intent = new Intent(this, TicketScoringActivity.class);
					break;
				case R.id.state_finalscores:
					intent = new Intent(this, FinalScoresActivity.class);
					break;
				default:
					intent = new Intent(this, BoardSelectionActivity.class);
					break;
			}
			startActivity(intent);
			finish();
		} else {
			intent = new Intent(this, BoardSelectionActivity.class);
			startActivity(intent);
			finish();
		}

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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_mainmenu_newgame:
				Intent intent = new Intent(this, BoardSelectionActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.button_mainmenu_continuegame:
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				state = prefs.getInt("state", R.id.state_boardselection);
				onResume();
				break;
		}
	}
}
