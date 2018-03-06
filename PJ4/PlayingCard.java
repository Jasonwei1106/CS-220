package PJ4;

import java.util.*;


//=================================================================================
/** class PlayingCardException: It is used for errors related to Card and Deck objects
 *  Do not modify this class!
 */
class PlayingCardException extends Exception {

    /* Constructor to create a PlayingCardException object */
    PlayingCardException (){
		super ();
    }

    PlayingCardException ( String reason ){
		super ( reason );
    }
}



//=================================================================================
/** class Card : for creating playing card objects
 *  it is an immutable class.
 *  Rank - valid values are 1 to 13
 *  Suit - valid values are 0 to 4
 *  Do not modify this class!
 */
class Card {
	
    /* constant suits and ranks */
    static final String[] Suit = {"Clubs", "Diamonds", "Hearts", "Spades","Joker" };
    static final String[] Rank = {"","A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    /* Data field of a card: rank and suit */
    private int cardRank;  /* values: 1-13 (see Rank[] above) */
    private int cardSuit;  /* values: 0-4  (see Suit[] above) */

    /* Constructor to create a card */
    /* throw PlayingCardException if rank or suit is invalid */
    public Card(int rank, int suit) throws PlayingCardException { 

        // suit=4 is joker, rank must be 1 or 2
	if (suit==4) {
	    if ((rank <1) || (rank >2))
	       throw new PlayingCardException("Invalid rank for Joker:"+rank);
	    cardRank=rank;
	    cardSuit=4;
        } else {

	    if ((rank < 1) || (rank > 13))
		throw new PlayingCardException("Invalid rank:"+rank);
	    else
        	cardRank = rank;

	    if ((suit < 0) || (suit > 3))
		throw new PlayingCardException("Invalid suit:"+suit);
	    else
        	cardSuit = suit;
   	}
    }

    /* Accessor and toString */
    /* You may impelemnt equals(), but it will not be used */
    public int getRank() { return cardRank; }
    public int getSuit() { return cardSuit; }
    public String toString() { 
	if (cardSuit == 4) return Suit[cardSuit]+" #"+cardRank;
        else return Rank[cardRank] + " " + Suit[cardSuit]; 
    }

    
    /* Few quick tests here */
    public static void main(String args[])
    {
	try {
	    Card c1 = new Card(1,3);    // A Spades
	    System.out.println(c1);
	    c1 = new Card(10,0);	// 10 Clubs
	    System.out.println(c1);
	    c1 = new Card(2,4);		// Joker #2
	    System.out.println(c1);
	    c1 = new Card(10,4);        // generate exception here
	}
	catch (PlayingCardException e)
	{
	    System.out.println("PlayingCardException: "+e.getMessage());
	}
    }
}



//=================================================================================
/** class Decks represents : n decks of 52 playing cards
 *  Use class Card to construct n * 52 playing cards!
 *
 *  Do not add new data fields!
 *  Do not modify any methods
 *  You may add private methods 
 */

class Decks {

    /* this is used to track of original n*52 or n*54 cards */
    private List<Card> trackingDecks;   

    /* this starts with copying cards from trackingDecks */
    /* it is used to play the card game                  */
    /* see reset(): resets playingDecks to trackingDecks    */
    private List<Card> playingDecks;

    /* number of decks in this object */
    private int numberDecks;
    private boolean jokers; // with or without jockers


    /**
     * default constructor: creates one deck of 54 playing cards (with jokers) 
     *       in trackingDecks and copy them to playingDecks.
     *       initialize numberDecks=1
     * Note: You need to catch PlayingCardException from Card constructor
     *	     Use ArrayList for both trackingDecks & playingDecks
     */
    public Decks()
    {
        
        numberDecks =1;
        jokers = true;
        try{
        trackingDecks = new ArrayList<Card>(); 
       
                for (int rank = 1; rank <= 2; rank++) {
                    
                int suit =4;
                   
                        Card tempCard = new Card(rank, suit);
                        
                        trackingDecks.add(tempCard);
                }
        for (int suit = 0; suit <= 3; suit++) {
                for (int rank = 1; rank <= 13; rank++) {
                   
                        Card tempCard = new Card(rank, suit);
                        
                        trackingDecks.add(tempCard);
                    
                }
        }
        }catch (PlayingCardException e){
            System.err.println("invalid number");
        }
        
        
        playingDecks = new ArrayList<Card>();
        playingDecks.addAll(trackingDecks);
        
        // implement this method!
}



    /**
     * Constructor: Creates n decks (54 or 52 cards each deck - with or without Jokers) 
     *              of playing cards in trackingDecks and copy them to playingDecks.
     *              initialize numberDecks=n
     * Note: You need to catch PlayingCardException from Card constructor
     *	     Use ArrayList for both trackingDecks & playingDecks
     */
    public Decks(int n, boolean jokers)
    {
        numberDecks = n;
        this.jokers = jokers;
        try{
        trackingDecks = new ArrayList<Card>();
        for (int i = 0; i < n; i++) {
                for (int rank = 1; rank <= 13; rank++) {
                    for (int suit = 0; suit <= 3; suit++) {
                        Card tempCard = new Card(rank, suit);
                        
                        trackingDecks.add(tempCard);
                    }
                }
                if (jokers){
            for (int rank = 1; rank <= 2; rank++) {
                    
                int suit =4;
                   
                        Card tempCard = new Card(rank, suit);
                        
                        trackingDecks.add(tempCard);
            }
        }
        }
        
        }catch (PlayingCardException e){
            System.err.println("invalid number");
        }
        
        playingDecks = new ArrayList<Card>();
        playingDecks.addAll(trackingDecks);
        
        // implement this method!
    }


    /**
     * Task: Shuffles cards in playingDecks.
     * Hint: Look at java.util.Collections
     */
    public void shuffle()
    {
        Collections.shuffle(playingDecks);
        // implement this method!
    }

    /**
     * Task: Deals cards from the playingDecks.
     *
     * @param numberCards number of cards to deal
     * @return a list containing cards that were dealt
     * @throw PlayingCardException if numberCard > number of remaining cards
     *
     * Note: You need to create ArrayList to stored dealt cards
     *       and should removed dealt cards from playingDecks
     *
     */
    public List<Card> deal(int numberCards) throws PlayingCardException
    {
        ArrayList<Card> dealt = new ArrayList<Card>();
        if(numberCards > remainSize()){
            throw new PlayingCardException("Not enough cards to deal");
        }else{

        
        for(int i=0; i < numberCards ; i++){
            int size= remainSize();
            Card temp = playingDecks.remove(size - numberCards + i);
        dealt.add(temp);
        }
        }
        // implement this method!
        return dealt;
    }

    /**
     * Task: Resets playingDecks by getting all cards from the trackingDecks.
     */
    public void reset()
    {
        playingDecks.clear();
        playingDecks.addAll(trackingDecks);
        // implement this method!

    }

    /**
     * Task: Return number of decks.
     */
    public int getNumberDecks()
    {
	return numberDecks;
    }

    /**
     * Task: Return jokers.
     */
    public boolean getJockers()
    {
	return jokers;
    }

    /**
     * Task: Return number of remaining cards in playingDecks.
     */
    public int remainSize()
    {
	return playingDecks.size();
    }

    /**
     * Task: Returns a string representing cards in the playingDecks 
     */
    public String toString()
    {
	return ""+playingDecks;
    }


    /* Quick test                               */
    /*                                          */
    /* Do not modify these tests:               */
    /* Generate 1 deck with default constructor */
    /* Generate 2 decks of 54 cards             */
    /* Loop 2 times:                            */
    /*   Deal 27 cards for 5 times              */
    /*   Expect exception 5th time              */
    /*   reset()                                */

    public static void main(String args[]) {

        System.out.println("*******    Create 1 decks of cards      ********\n");
        Decks decks  = new Decks();
        System.out.println("getNumberDecks:" + decks.getNumberDecks());
        System.out.println("\nprint deck:" + decks);
        System.out.println("\nSize:"+decks.remainSize()+" cards");

        System.out.println("\n*******    Create 2 decks of cards      ********\n");
        decks  = new Decks(2, true);
        System.out.println("getNumberDecks:" + decks.getNumberDecks());
        System.out.println("getJockers:" + decks.getJockers());
         
	for (int j=0; j < 2; j++)
	{
        	System.out.println("\n************************************************\n");
        	System.out.println("Loop # " + j + "\n");
		System.out.println("Before shuffle:"+decks.remainSize()+" cards");
		System.out.println("\n\t"+decks);
        	System.out.println("\n==============================================\n");

                int numHands = 5;
                int cardsPerHand = 27;

        	for (int i=0; i < numHands; i++)
	 	{
	    		decks.shuffle();
		        System.out.println("After shuffle:"+decks.remainSize()+" cards");
		        System.out.println("\n\t"+decks);
			try {
            		    System.out.println("\n\nHand "+i+":"+cardsPerHand+" cards");
            		    System.out.println("\n\t"+decks.deal(cardsPerHand));
            		    System.out.println("\n\nRemain:"+decks.remainSize()+" cards");
		            System.out.println("\n\t"+decks);
        	            System.out.println("\n==============================================\n");
			}
			catch (PlayingCardException e) 
			{
		 	        System.out.println("*** In catch block:PlayingCardException:Error Msg: "+e.getMessage());
			}
		}


		decks.reset();
	}
    }

}
