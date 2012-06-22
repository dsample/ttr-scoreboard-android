package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class BoardSelectionActivity extends Activity implements OnClickListener {
	Game game;

	ArrayList<BoardRules> boards;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.boards = BoardRules.knownBoards();
		super.onCreate(savedInstanceState);

		game = new Game();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		setContentView(R.layout.main);
    	LinearLayout l = (LinearLayout) findViewById(R.id.container);
    	l.removeAllViews();

    	for (int i=0; i < boards.size() ;i++) {
    		BoardRules board = boards.get(i);
    		Button b = new Button(this);
    		b.setText(board.getName());
    		b.setTag(board);
    		b.setId(R.id.button_boardselection_continue);
    		b.setOnClickListener(this);
    		l.addView(b);
    	}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionbar_boardselection, menu);
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_boardselection_continue:
				this.game.setBoard((BoardRules) v.getTag());
				Intent intent = new Intent(this, PlayerSelectionActivity.class);
				intent.putExtra("game", this.game);
				startActivity(intent);
				break;
		}
	}

}