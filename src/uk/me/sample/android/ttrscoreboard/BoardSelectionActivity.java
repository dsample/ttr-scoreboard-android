package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Expansion;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class BoardSelectionActivity extends Activity implements OnClickListener {
	Game game;

	ArrayList<BoardRules> boards;
	ArrayList<Expansion> expansions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.boards = BoardRules.knownBoards();
		this.expansions = BoardRules.knownExpansions();
		super.onCreate(savedInstanceState);

		game = new Game();
		
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(false);
		actionbar.setHomeButtonEnabled(false);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		setContentView(R.layout.main);
    	LinearLayout l = (LinearLayout) findViewById(R.id.container);
    	l.removeAllViews();

    	for (int i=0; i < boards.size() ;i++) {
    		BoardRules board = boards.get(i);
    		Button b = new Button(this);
    		b.setText(board.getName());
    		b.setTag(board);
    		b.setId(R.id.button_boardselection);
    		b.setOnClickListener(this);
    		
    		for (int j=0; j < expansions.size() ;j++) {
    			Expansion expansion = expansions.get(j);
    			Log.d("parentId", Integer.toString(expansion.parentBoardId) + " : " + Integer.toString(board.getId()));
    			if (expansion.parentBoardId == board.getId()) {
        			Log.d("parentId", Integer.toString(expansion.parentBoardId) + " : " + Integer.toString(board.getId()) + " MATCHED");
        			CheckBox checkbox = new CheckBox(this);
        			checkbox.setText(expansion.name);
        			checkbox.setTag("Expansion " + Integer.toString(expansion.id));
        			checkbox.setTag(R.id.expansion_id, expansion.id);
        			checkbox.setTag(R.id.expansion_parent_id, expansion.parentBoardId);
    				
    				l.addView(checkbox);
    			}
    		}
    		
    		l.addView(b);
    	}
    	
    	boolean expansionDividerAdded = false;

    	for (int k=0; k < expansions.size() ;k++) {
    		Expansion exp = expansions.get(k);
    		if (exp.parentBoardId == 0) {
//    			if (! expansionDividerAdded) {}

    			CheckBox cbox = new CheckBox(this);
    			cbox.setText(exp.name);
    			cbox.setTag("Expansion " + Integer.toString(exp.id));
    			cbox.setTag(R.id.expansion_id, exp.id);
    			cbox.setTag(R.id.expansion_parent_id, exp.parentBoardId);
    			
    			l.addView(cbox);
    		}
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
			case R.id.button_boardselection:
				BoardRules board = (BoardRules) v.getTag();
				
				LinearLayout container = (LinearLayout) findViewById(R.id.container);
				
				for (int i=0; i < expansions.size() ;i++) {
					Expansion expansion = expansions.get(i);
					CheckBox checkbox = (CheckBox) container.findViewWithTag("Expansion " + expansion.id);
					
					if (checkbox.isChecked() && (expansion.parentBoardId == 0 || expansion.parentBoardId == board.getId())) {
						game.addExpansion(expansion);
						// Remove bonuses
						board.removeBonuses(expansion.removeBonuses);
						// Add new bonuses
						board.addBonuses(expansion.newBonuses);
					}
				}
				
				this.game.setBoard(board);
				Intent intent = new Intent(this, PlayerSelectionActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				finish();
				break;
		}
	}

}
