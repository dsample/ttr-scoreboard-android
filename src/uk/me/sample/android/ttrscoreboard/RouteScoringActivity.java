package uk.me.sample.android.ttrscoreboard;

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
			case R.id.button_routescoring_endgame:
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.addButton:
				Player player = this.game.getPlayerById((Integer) v.getTag());
				player.newScore("Route", 8);
				RelativeLayout l = (RelativeLayout) v.getParent();
				TextView score = (TextView) l.findViewById(R.id.player_score);
				score.setText(Integer.toString(player.getTotalScore()));
				break;
		}
	}

}
