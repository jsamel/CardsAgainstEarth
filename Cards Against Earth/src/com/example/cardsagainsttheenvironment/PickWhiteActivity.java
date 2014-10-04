package com.example.cardsagainsttheenvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PickWhiteActivity extends Activity {
	ParseObject roomObj;
	boolean isJudge;
	ParseUser currentUser;
	List<Integer> whiteCards;
	List<RelativeLayout> cards = new ArrayList<RelativeLayout>();
	RelativeLayout selectedCard;
	int selectedCardId;
	Button play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_white);
		
		Parse.initialize(this, "KfRZwxhtH70yJC1aUGr7kaS53UO7rRjXBuLukpb4", "UP8vGfiIB6kIRrursaeE1A7r59YGYUpWUluTeRoR");
		
		play = (Button) findViewById(R.id.play_card_button);
		play.setEnabled(false);
		
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null)
			return;
		
		Intent i = getIntent();
        
        String roomId = i.getExtras().getString("roomId");
        Log.d("roomId", roomId);
        
        cards.add((RelativeLayout)findViewById(R.id.Card1));
        cards.add((RelativeLayout)findViewById(R.id.Card2));
        cards.add((RelativeLayout)findViewById(R.id.Card3));
        cards.add((RelativeLayout)findViewById(R.id.Card4));
        cards.add((RelativeLayout)findViewById(R.id.Card5));
        cards.add((RelativeLayout)findViewById(R.id.Card6));
        cards.add((RelativeLayout)findViewById(R.id.Card7));
		
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
        query.getInBackground(roomId, new GetCallback<ParseObject>() {
          public void done(ParseObject room, ParseException e) {
            if (e == null) {
            	roomObj = room;
            	
            	String currJudge = (String)roomObj.get("currentJudge");
            	Log.d("currJudge in room", currJudge);
            	
            	isJudge = currJudge.equals(currentUser.getUsername());
            	Log.d("isJudge", isJudge + "");
            	
                if (isJudge) {
                	play.setVisibility(0);
                	play.setClickable(false);
                }
                else
                {
                	String currPlayer = (String)roomObj.get("currentPlayer");
                	if (currPlayer.equals(currentUser.getUsername())) {
                		play.setClickable(true);
                	} else {
                		play.setClickable(false);
                	}
                }
                
                Log.d("List", roomObj.get("whiteDeck").toString());
                
                //display white cards
                
                List<Map> players = (List<Map>)roomObj.get("players");
                for (Map p: players) {
                	if (p.get("userId").equals(currentUser.getUsername())) {
                		whiteCards = (List<Integer>)p.get("cardsInHand");
                	}
                }
                
                if (whiteCards == null || whiteCards.size() != 7) {
                	return;
                }
                
                ParseQuery<ParseObject> cardQuery1 = ParseQuery.getQuery("WhiteCards");
                cardQuery1.whereEqualTo("cardId", whiteCards.get(0));
                cardQuery1.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));
                            	
                            	TextView curr = (TextView) findViewById(R.id.Card1card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery2 = ParseQuery.getQuery("WhiteCards");
                cardQuery2.whereEqualTo("cardId", whiteCards.get(1));
                cardQuery2.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));
                            	
                            	TextView curr = (TextView) findViewById(R.id.Card2card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery3 = ParseQuery.getQuery("WhiteCards");
                cardQuery3.whereEqualTo("cardId", whiteCards.get(2));
                cardQuery3.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));

                            	TextView curr = (TextView) findViewById(R.id.Card3card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery4 = ParseQuery.getQuery("WhiteCards");
                cardQuery4.whereEqualTo("cardId", whiteCards.get(3));
                cardQuery4.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));

                            	TextView curr = (TextView) findViewById(R.id.Card4card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery5 = ParseQuery.getQuery("WhiteCards");
                cardQuery5.whereEqualTo("cardId", whiteCards.get(4));
                cardQuery5.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));

                            	TextView curr = (TextView) findViewById(R.id.Card5card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery6 = ParseQuery.getQuery("WhiteCards");
                cardQuery6.whereEqualTo("cardId", whiteCards.get(5));
                cardQuery6.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));

                            	TextView curr = (TextView) findViewById(R.id.Card6card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                ParseQuery<ParseObject> cardQuery7 = ParseQuery.getQuery("WhiteCards");
                cardQuery7.whereEqualTo("cardId", whiteCards.get(6));
                cardQuery7.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> cardList, ParseException e) {
                        if (e == null) {
                            if (cardList != null && cardList.size() > 0) {
                            	Log.d("CardText", cardList.get(0).getString("cardContent"));

                            	TextView curr = (TextView) findViewById(R.id.Card7card);
                            	
                            	curr.setText(cardList.get(0).getString("cardContent"));
                            }
                        } else {
                            Log.d("CardText", "Error: " + e.getMessage());
                        }
                    }
                });
                
                
            } else {
            	Log.d("Error", e.getMessage());
            }
          }
        });
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_white, menu);
		return true;
	}
	
	
	public void cardSelected(View v) {
		RelativeLayout r = (RelativeLayout)v;
		String currPlayer = (String)roomObj.get("currentPlayer");
    	if (currPlayer.equals(currentUser.getUsername())) {
    		if (selectedCard == null) {
    			selectedCard = r;
    			r.setBackgroundColor(Color.GRAY);
    			play.setEnabled(true);
    		} else if (selectedCard.equals(r)) {
    			selectedCard.setBackground(getResources().getDrawable(R.drawable.white_background_list));
    			selectedCard = null;
    			play.setEnabled(false);
    		} else {
    			selectedCard.setBackground(getResources().getDrawable(R.drawable.white_background_list));
    			selectedCard = r;
    			r.setBackgroundColor(Color.GRAY);
    			play.setEnabled(true);
    		}
    	} else {
    		
    	}
	}
	
	
	public void playCard(View v) {
		if (selectedCard == null) {
			return;
		}
		
		//update cards in play
		List<Map> playingCardPool = (List<Map>)roomObj.get("playingCardPool");
		Map newCard = new HashMap();
		
		int index = cards.indexOf(selectedCard);
		
		newCard.put("cardId", whiteCards.get(index));
		newCard.put("userId", currentUser.getUsername());
		
		Log.d("playingCardPool", playingCardPool.toString());
		Log.d("newCard", newCard.toString());
		
		playingCardPool.add(newCard);
		roomObj.put("playingCardPool", playingCardPool);
		
		//move turn to next person
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
		
		roomObj.saveInBackground();
	}

}
