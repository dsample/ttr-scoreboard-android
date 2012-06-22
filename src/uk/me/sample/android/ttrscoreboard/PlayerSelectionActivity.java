package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;

public class PlayerSelectionActivity extends Activity implements OnCheckedChangeListener {

	Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		game = (Game) extras.getParcelable("game");
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		setContentView(R.layout.main);
    	LinearLayout container = (LinearLayout) findViewById(R.id.container);
    	container.removeAllViews();

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_playerselection, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem menuitem = menu.findItem(R.id.button_playerselection_continue);
		
		if (this.game.playerCount() >= 2) {
			menuitem.setEnabled(true);
		} else {
			menuitem.setEnabled(false);
		}
		return super.onPrepareOptionsMenu(menu);
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
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Check that the minimum number of players has been selected, and enable the button
		// Otherwise, disable the button
		
		if (isChecked) {
			game.addPlayer((Player) buttonView.getTag());
		} else {
			game.removePlayer((Player) buttonView.getTag());
		}
		
		invalidateOptionsMenu();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.button_playerselection_continue:
				Intent intent = new Intent(this, RouteScoringActivity.class);
				intent.putExtra("game", game);
				startActivity(intent);
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
		Log.d("TTR", "calcDp beginning");
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}
}