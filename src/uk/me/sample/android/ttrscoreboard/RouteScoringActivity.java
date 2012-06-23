package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class RouteScoringActivity extends Activity implements OnClickListener {
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
		
		for (int i=0; i < game.playerCount() ;i++) {
			container.addView(playerView(game.getPlayer(i)));
		}

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
		addButton.setTag(player.id);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_ticketscoring, menu);
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
		return (int) (getResources().getDisplayMetrics().density * pixels + 0.5f);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.addButton:
				LinearLayout mainContainer = (LinearLayout) findViewById(R.id.container);
				RelativeLayout panel = (RelativeLayout) findViewById(R.id.routeselector);
				if (panel != null) {
					mainContainer.removeView(panel);
				}
				
				LayoutInflater inflater = getLayoutInflater();
				RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.routescore_selector, null);
				RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				relParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				l.setLayoutParams(relParams);
				LinearLayout container = (LinearLayout) l.findViewById(R.id.buttoncontainer);
				
				container.removeAllViews();
				Button button;
				
				ArrayList<Integer> routeScores = this.game.getBoard().getRouteScores();
				for (int i=0; i < routeScores.size() ;i++) {
					Integer routeScore = routeScores.get(i);
					if (routeScore > 0) {
						button = new Button(this);
						button.setId(R.id.button_routescoring_scorebutton);
						button.setText(Integer.toString(i+1));
						button.setTag(R.id.object_playerid, v.getTag());
						button.setTag(R.id.object_routelength, i+1);
						button.setTag(R.id.object_routescore, routeScore);
						LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						button.setLayoutParams(layoutParams);
						container.addView(button);
					}
				}
				
				mainContainer.addView(l);
				break;
			case R.id.button_routescoring_scorebutton:
				Log.d("BUTTON", "Score button");
				Integer RouteScore = (Integer) v.getTag(R.id.object_routescore);
				Integer RouteLength = (Integer) v.getTag(R.id.object_routelength);
				
				Player player = this.game.getPlayerById((Integer) v.getTag(R.id.object_playerid));
				player.newScore("Route (" + RouteLength + ")", RouteScore);
				RelativeLayout rel = (RelativeLayout) v.findViewWithTag("Player " + v.getTag(R.id.object_playerid).toString());
				TextView score = (TextView) rel.findViewById(R.id.player_score);
				score.setText(Integer.toString(player.getTotalScore()));
				break;
		}
	}

}
