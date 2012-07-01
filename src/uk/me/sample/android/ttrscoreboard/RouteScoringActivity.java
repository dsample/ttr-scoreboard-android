package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RouteScoringActivity extends Activity implements OnClickListener {
	Game game;
	Boolean keepKeypadOpen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Bundle extras = getIntent().getExtras();
		this.game = (Game) extras.getParcelable("game");
		keepKeypadOpen = false;
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setHomeButtonEnabled(false);

		setContentView(R.layout.routescoring);
    	LinearLayout container = (LinearLayout) findViewById(R.id.playerContainer);
    	container.removeAllViews();
		
		for (int i=0; i < game.playerCount() ;i++) {
			container.addView(playerView(game.getPlayer(i)));
		}

		RelativeLayout scoreContainer = (RelativeLayout) findViewById(R.id.scorecontainer);
		LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.buttoncontainer);
		
		buttonContainer.removeAllViews();
		Button button;
		
		ArrayList<Integer> routeScores = this.game.getBoard().getRouteScores();
		for (int i=0; i < routeScores.size() ;i++) {
			Integer routeScore = routeScores.get(i);
			if (routeScore > 0) {
				button = new Button(this, null, android.R.attr.buttonStyleSmall);
				button.setId(R.id.button_routescoring_scorebutton);
				button.setOnClickListener(this);
				button.setText(Integer.toString(i+1));
				//button.setTag(R.id.object_playerid, v.getTag());
				button.setTag(R.id.object_routelength, i+1);
				button.setTag(R.id.object_routescore, routeScore);
				LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				button.setLayoutParams(layoutParams);
				buttonContainer.addView(button);
			}
		}
		
		scoreContainer.setVisibility(RelativeLayout.GONE);

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

/*		
		int rowHeight = calcDp(48);

		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight);
		layoutParams.gravity = 0x10;
		l.setLayoutParams(layoutParams);

		// Left bar
		View leftbar = new View(this);
		leftbar.setBackgroundColor(player.colour);
		layoutParams = new LayoutParams(calcDp(3), LayoutParams.MATCH_PARENT);
		layoutParams.gravity = 0x10;
		leftbar.setLayoutParams(layoutParams);
		l.addView(leftbar);

		TextView textview = new TextView(this);
		textview.setText(player.name);
		layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(calcDp(8), 0, calcDp(8), 0);
		layoutParams.gravity = Gravity.CENTER_VERTICAL + Gravity.LEFT;
		textview.setLayoutParams(layoutParams);
		l.addView(textview);
		
		View vertical_divider = new View(this);
		layoutParams = new LayoutParams(calcDp(1), LayoutParams.MATCH_PARENT);
		layoutParams.setMargins(0, calcDp(8), 0, calcDp(8));
		vertical_divider.setLayoutParams(layoutParams);
		vertical_divider.setBackgroundColor(android.R.attr.dividerVertical);
		layoutParams.gravity = Gravity.CENTER_VERTICAL + Gravity.RIGHT;
		l.addView(vertical_divider);

		ImageButton button = new ImageButton(this);
		button.setId(R.id.button_routescoring_addscore);
		button.setTag(player.id);
		button.setOnClickListener(this);
		//button.setBackgroundColor(android.R.attr.selectableItemBackground);
		button.setImageResource(R.drawable.content_new);
		button.setScaleType(ScaleType.FIT_CENTER);
		button.setPadding(calcDp(8), calcDp(8), calcDp(8), calcDp(8));
		layoutParams = new LayoutParams(calcDp(48), calcDp(48));
		layoutParams.gravity = Gravity.CENTER_VERTICAL + Gravity.RIGHT;
		button.setLayoutParams(layoutParams);
		l.addView(button);
*/
		
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
	    MenuItem discardButton = menu.add(2, R.id.button_discard, 3, R.string.button_discardgame);
	    discardButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER + MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
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
				Intent intent = new Intent(this, TicketScoringActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				finish();
				break;
			case R.id.button_discard:
				Intent discardIntent = new Intent(this, BoardSelectionActivity.class);
				startActivity(discardIntent);
				finish();
				break;
			case R.id.menuitem_keepopenswitch:
				this.keepKeypadOpen = !item.isChecked();
				if (!keepKeypadOpen) {
					findViewById(R.id.scorecontainer).setVisibility(View.GONE);
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
		RelativeLayout scoreContainer = (RelativeLayout) findViewById(R.id.scorecontainer);  
		LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.buttoncontainer);
		Player player;
		switch (v.getId()) {
			case R.id.addButton:
				player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));

				scoreContainer.setVisibility(View.VISIBLE);
				for (int i=0; i < buttonContainer.getChildCount() ;i++) {
					Button button = (Button) buttonContainer.getChildAt(i);
					//button.setBackgroundColor(player.colour);
					button.getBackground().setColorFilter(player.colour, android.graphics.PorterDuff.Mode.MULTIPLY);
					button.setTag(R.id.object_playerid, player.id);
				}
				break;
			case R.id.button_routescoring_scorebutton:
				player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));
				if (!keepKeypadOpen) {
					scoreContainer.setVisibility(View.GONE);
				}
				Integer RouteScore = (Integer) v.getTag(R.id.object_routescore);
				Integer RouteLength = (Integer) v.getTag(R.id.object_routelength);
				
				player.newScore(R.id.score_route, RouteLength, RouteScore);
				RelativeLayout rel = (RelativeLayout) findViewById(R.id.container).findViewWithTag("Player " + v.getTag(R.id.object_playerid).toString());
				TextView score = (TextView) rel.findViewById(R.id.player_score);
				score.setText(Integer.toString(player.getTotalScore()));
				break;
		}
	}

}
