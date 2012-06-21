package uk.me.sample.android.ttrscoreboard;

import java.util.ArrayList;

import uk.me.sample.android.ttrscoreboard.objects.BoardBonus;
import uk.me.sample.android.ttrscoreboard.objects.BoardRules;
import uk.me.sample.android.ttrscoreboard.objects.Player;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class StartActivity extends Activity {
	ArrayList<BoardRules> boards;
	SharedPreferences prefs;
	
	Object currentGame = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//this.prefs = this.getSharedPreferences("gamedata", MODE_PRIVATE);
    	
    	this.createBoards();
    	
        super.onCreate(savedInstanceState);
        if (currentGame != null) {
        	setTitle("Ticket To Ride");
        	setContentView(R.layout.mainmenu);
        } else {
        	setTitle("Select board");
        	setContentView(R.layout.select_board);
        	showBoardButtons();
        }
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void newGame(View view) {
    	setTitle("Select board");
    	setContentView(R.layout.select_board);
    }
    private void showBoardButtons() {
    	LinearLayout l = (LinearLayout) findViewById(R.id.boardlist);
    	l.removeAllViews();
    	for (int i=0; i < boards.size() ;i++) {
    		BoardRules board = boards.get(i);
    		Button b = new Button(this);
    		b.setText(board.getName());
    		b.setTag(board);
    		b.setOnClickListener(new BoardSelectionOnClickListener(this));
    		l.addView(b);
    	}
    }
    
    public void createBoards() {
    	this.boards = new ArrayList<BoardRules>();
    	
    	BoardRules europe = new BoardRules("Europe", 2);
    	Player[] players = {
    		new Player("Red", Color.parseColor("#CC0000")),
    		new Player("Blue", Color.parseColor("#0099CC")),
    		new Player("Yellow", Color.parseColor("#FFBB33")),
    		new Player("Green", Color.parseColor("#669900")),
    		new Player("Black", Color.parseColor("#000000")) };
    	europe.setPlayers(players);
    	Integer[] routeScores = { 1, 2, 4, 7, 0, 15, 0, 21 };
    	europe.setRouteScores(routeScores);

    	BoardBonus bonus = new BoardBonus("Longest route");
    	bonus.setNumberOfWinners(1);
    	bonus.setPossibleBonusesPerWinner(1);
    	Integer[] bonusScores = { 10 };
    	bonus.setScoresPerTicket(bonusScores);
    	BoardBonus[] bonuses = { bonus };
    	europe.setBonuses(bonuses);
    	
    	this.boards.add(europe);
    }
    
}