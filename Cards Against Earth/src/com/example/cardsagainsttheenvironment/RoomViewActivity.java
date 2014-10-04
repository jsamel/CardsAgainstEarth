package com.example.cardsagainsttheenvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RoomViewActivity extends Activity {
	ParseObject roomObj;
	Button choose;
    Button judge;
    ParseUser currentUser;
    TextView card;
    boolean isJudge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_view);
		
		Parse.initialize(this, "KfRZwxhtH70yJC1aUGr7kaS53UO7rRjXBuLukpb4", "UP8vGfiIB6kIRrursaeE1A7r59YGYUpWUluTeRoR");
		
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null)
			return;
		
		card = (TextView) findViewById(R.id.blackCard);
		
		Intent sender = getIntent();
        
        String roomId = sender.getExtras().getString("roomId");
        Log.d("roomId", roomId);
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.getInBackground(roomId, new GetCallback<ParseObject>() {
          public void done(ParseObject room, ParseException e) {
            if (e == null) {
            	roomObj = room;
            	int currBlackCardId = roomObj.getInt("currentBlackCard");
            	
            	String currJudge = (String)roomObj.get("currentJudge");
            	Log.d("currJudge in room", currJudge);
            	
            	isJudge = currJudge.equals(currentUser.getUsername());
            	Log.d("isJudge", isJudge + "");
            	
            	choose = (Button) findViewById(R.id.choose_button);
                judge = (Button) findViewById(R.id.judge_button);
                if (isJudge) {
                	judge.setEnabled(false);
                	choose.setText("Begin Next Round");
                	
                	if (currBlackCardId == -1) {
                		choose.setEnabled(true);
                	} else {
                		choose.setEnabled(false);
                	}
                	
                }
                else
                {
                	choose.setEnabled(currBlackCardId != -1);
                	judge.setVisibility(0);
                	judge.setClickable(false);
                	
                	String currPlayer = (String)roomObj.get("currentPlayer");
                	if (currPlayer.equals(currentUser.getUsername())) {
                		choose.setText("Choose Card");
                	} else {
                		choose.setText("View Cards");
                	}
                	
                	
                	ParseQuery<ParseObject> query = ParseQuery.getQuery("BlackCards");
                    query.whereEqualTo("cardId", currBlackCardId);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> cardList, ParseException e) {
                            if (e == null) {
                                if (cardList != null && cardList.size() > 0) {
                                	Log.d("CardText", cardList.get(0).getString("cardContent"));
                                	card.setText(cardList.get(0).getString("cardContent"));
                                }
                            } else {
                                Log.d("CardText", "Error: " + e.getMessage());
                            }
                        }
                    });
                }
                
            } else {
            	Log.d("Error", e.getMessage());
            }
          }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.room_view, menu);
		return true;
	}
	
	
	public void chooseButtonClicked(View v) {
		if (isJudge) {
			//select black card
            List<Integer> blackCards = (List<Integer>)roomObj.get("blackDeck");
            int rand = (int)(Math.random() * (blackCards.size() - 1));
            int selectedCardId = blackCards.get(rand);
            Log.d("cardId", selectedCardId + "");
            roomObj.put("currentBlackCard", selectedCardId);
            roomObj.saveInBackground();
            
            ParseQuery<ParseObject> query = ParseQuery.getQuery("BlackCards");
            query.whereEqualTo("cardId", selectedCardId);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> cardList, ParseException e) {
                    if (e == null) {
                        if (cardList != null && cardList.size() > 0) {
                        	Log.d("CardText", cardList.get(0).getString("cardContent"));
                        	card.setText(cardList.get(0).getString("cardContent"));
                        	choose.setEnabled(false);
                        	
                        	
                        }
                    } else {
                        Log.d("CardText", "Error: " + e.getMessage());
                    }
                }
            });
            
            List<Map> players = (List<Map>)roomObj.get("players");
    		int i = 0;
    		Map user = null;
    		while(i < players.size() && !players.get(i).get("userId").equals(currentUser.getUsername())) {
    			user = players.get(i);
    			i++;
    		}
    		
    		if (i == players.size()) {
    			return;
    		}
    		if (i == players.size() - 1) {
    			user = players.get(0);
    		} else {
    			user = players.get(i + 1);
    		}
    		
    		roomObj.put("currentPlayer", user.get("userId").toString());
            List<String> remainingPlayers = new ArrayList<String>();
            
            for (int j = 0, size = players.size(); j < size; j++) {
            	Map player = players.get(j);
            	
            	if (!player.get("userId").toString().equals(currentUser.getUsername())) {
            		remainingPlayers.add(player.get("userId").toString());
            	}
            	
            	//select white cards
                List<Integer> whiteCards = (List<Integer>)roomObj.get("whiteDeck");
               
                List<Integer> randArr = new ArrayList<Integer>();
                int numCards = 7;
                
                for(int n = 0; n < 7; n++) {
                	int randPos = (int)(Math.random() * (whiteCards.size() - 1));
                	Log.d("selectedPosition", randPos + "");
                	
                	int id = whiteCards.get(randPos);
                	Log.d("cardId", id + "");
                	randArr.add(id);
                	whiteCards.remove(randPos);
                } 
                
                player.put("cardsInHand", randArr);
                roomObj.put("remainingPlayers", remainingPlayers);
                roomObj.saveInBackground();
            }
            
		} else {
			Intent i = new Intent(RoomViewActivity.this, PickWhiteActivity.class);
			i.putExtra("roomId", roomObj.getObjectId());
			startActivity(i);
		}
	}
	
	
	public void judgeClicked(View v) {
		
	}

}
