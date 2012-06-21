package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import uk.me.sample.android.ttrscoreboard.objects.Player;

import android.app.Activity;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class PlayerSelection implements OnClickListener, OnCheckedChangeListener {
	private Activity ctx;
	private BoardRules board;
	
	private Game game;
	
	public PlayerSelection(Activity ctx, BoardRules board) {
		this.ctx = (StartActivity) ctx;
		this.board = board;
		this.game = new Game();
		
		
		ctx.setTitle("Select players");
		ctx.setContentView(R.layout.player_selection);
		drawPlayers();
	}
	
	private int calcDp(int pixels) {
		return (int) (ctx.getResources().getDisplayMetrics().density * pixels + 0.5f);
	}
	
	private void drawPlayers() {
		int rowHeight = calcDp(48);

		ArrayList<Player> players = board.getPlayers();
		LinearLayout container = (LinearLayout) ctx.findViewById(R.id.playerList);
		container.removeAllViews();
		
		for (int i=0; i < players.size() ;i++) {
			Player player = players.get(i);
			
			LinearLayout l = new LinearLayout(ctx);
			l.setOrientation(LinearLayout.HORIZONTAL);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight);
			layoutParams.gravity = 0x10;
			l.setLayoutParams(layoutParams);
			TextView textview = new TextView(ctx);
			textview.setText("");
			textview.setBackgroundColor(player.colour.hashCode());
			LayoutParams textviewParams = new LayoutParams(calcDp(3), LayoutParams.MATCH_PARENT);
			textviewParams.gravity = 0x10;
			textview.setLayoutParams(textviewParams);
			l.addView(textview);
			
			CheckBox checkbox = new CheckBox(ctx);
			LayoutParams checkboxParams = new LayoutParams(LayoutParams.MATCH_PARENT, rowHeight);
			checkboxParams.gravity = 0x10;
			checkbox.setLayoutParams(checkboxParams);
			checkbox.setTag(player);
			checkbox.setText(player.name);
			checkbox.setChecked(false);
			checkbox.setOnCheckedChangeListener(this);
			l.addView(checkbox);
			
			container.addView(l);
		}
		
		Button button = new Button(ctx);
		button.setId(R.id.player_selection_continuebutton);
		button.setText(R.string.button_continue);
		button.setEnabled(false);
		button.setOnClickListener(this);
		container.addView(button);
	}

	@Override
	public void onClick(View v) {
		ctx.setTitle("Route scores");
		ctx.setContentView(R.layout.game);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// Check that the minimum number of players has been selected, and enable the button
		// Otherwise, disable the button
		
		if (isChecked) {
			this.game.addPlayer((Player) buttonView.getTag());
		} else {
			this.game.removePlayer((Player) buttonView.getTag());
		}
		
		Button button = (Button) ctx.findViewById(R.id.player_selection_continuebutton);
		
		if (this.game.playerCount() >= 2) {
			button.setEnabled(true);
		} else {
			button.setEnabled(false);
		}
	}
}
