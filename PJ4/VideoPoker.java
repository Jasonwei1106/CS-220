package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.freeslots.com/poker.htm
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. One Pair: one pair of the same card
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the video poker game class.
 * It uses Decks and Card objects to implement video poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class VideoPoker {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and playerHand types
    private static final int[] multipliers={1,2,3,5,6,10,25,50,1000};
    private static final String[] goodHandTypes={ 
	  "One Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private final Decks gameDeck;

    // holding current poker 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = startingBalance */
    public VideoPoker()
    {
	this(startingBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.playerBalance= balance;
        gameDeck = new Decks(1, false);
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current playerHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
        if(isRoyalFlush()){
    		System.out.println("Royal Flush!");
    		playerBalance += (multipliers[8] * playerBet);
    	}
    	else if(isStraightFlush()){
    		System.out.println("Straight Flush!");
    		playerBalance += (multipliers[7] * playerBet);	
    	}
    	else if(isStraight()){
    		System.out.println("Straight!");
    		playerBalance += (multipliers[3] * playerBet);
    	}
    	else if(isFlush()){
    		System.out.println("Flush!");
    		playerBalance += (multipliers[4] * playerBet);
    	}
    	else if(isFullHouse()){
    		System.out.println("Full House");
    		playerBalance += (multipliers[5] * playerBet);
    	}
    	else if(isFour()){
    		System.out.println("Four of a Kind!");
    		playerBalance += (multipliers[6] * playerBet);
    	}
    	else if(isThree()){
    		System.out.println("Three of a Kind!");
    		playerBalance += (multipliers[2] * playerBet);
    	}
    	else if(isTwoPair()){
    		System.out.println("Two Pairs!");
    		playerBalance += (multipliers[1] * playerBet);
    	}
    	else if(isTwo()){
    		System.out.println("Royal Pair!");
    		playerBalance += (multipliers[0] * playerBet);
    	}
    	else
    		System.out.println("Sorry, you lost!");
        
	// implement this method!
    }

    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/

    private boolean isRoyalFlush(){
        int firstsuit = playerHand.get(0).getSuit();
        List<Integer> RoyalFlush = Arrays.asList(1,10,11,12,13);
        for(Card card: playerHand){
            if(card.getSuit()!=firstsuit||!RoyalFlush.contains(card.getRank()))
            return false;
        
        }
        return true;
    }
    private boolean isStraightFlush(){
        int firstsuit = playerHand.get(0).getSuit();
        
        List<Integer> Straight = new ArrayList<>();
        for(Card card : playerHand){
        Straight.add(card.getRank());
        Collections.sort(Straight);
                }
        for(Card card:playerHand){
            if(card.getSuit()!=firstsuit)
                return false;
            for (int i=0; i< 4; i++){
                if(Straight.get(i)!=Straight.get(i+1)-1)
                    return false;
            }
        }
            return true;
       
    }
    private boolean isStraight(){
        List<Integer> Straight = new ArrayList<>();
        for(Card card : playerHand){
        Straight.add(card.getRank());
        Collections.sort(Straight);
        }
        if(Straight.get(0)!=1){
        for (int i=0; i< 4; i++){
                if(Straight.get(i)!=Straight.get(i+1)-1){
                    return false;
                    
                }
        }
                    
        }else{      for(int i=1 ;i<4;i++){
         if(Straight.get(i)!=Straight.get(i+1)-1){
                    return false;
                }
                }
        }
        return true;
    
    }
        
    private boolean isFlush(){
           int firstsuit = playerHand.get(0).getSuit();
                
        for(Card card:playerHand){
            if(card.getSuit()!=firstsuit)
                return false;
        }
            return true;
        }
    private boolean isFullHouse(){
        
            List<Integer> Fullhouse = new ArrayList<>();
        for(Card card : playerHand){
        Fullhouse.add(card.getRank());
        Collections.sort(Fullhouse);
        }
            if (!isThree()||Fullhouse.get(0)!=Fullhouse.get(1) || Fullhouse.get(3)!=Fullhouse.get(4)){
                return false;}

        return true;
    }
    
    private int Numberofsame(){
        int[] arrayRank ={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    	int num = 0;
    	for(int i = 0; i <= 4; i++)
    	{
    		arrayRank[playerHand.get(i).getRank()]++;
    	}
    	for(int j = 0; j < arrayRank.length; j++)
    	{
    		if(num<arrayRank[j])
    		{
    			num = arrayRank[j];
    		}
    	}
    	return num;
    
    }
    private boolean isFour(){
        if(Numberofsame() ==4 ){
            return true;
        }
        return false;
    }
     private boolean isThree(){
        if(Numberofsame() ==3 ){
            return true;
        }
        return false;
    }
      private boolean isTwo(){
        if(Numberofsame() ==2 ){
            return true;
        }
        return false;
    }
        private boolean isTwoPair(){
            
      List<Integer> pair = new ArrayList<>();
        for(Card card : playerHand){
        pair.add(card.getRank());
        Collections.sort(pair);
        }
           if(pair.get(0)==pair.get(1)||pair.get(1)==pair.get(2))
    	{
    		if(pair.get(2)==pair.get(3)||pair.get(3)==pair.get(4))
    		{
    			return true;
    		}
    	}
    	return false;
            
                
      
        }
    public void play() 
    {
        while(playerBalance != 0){
        showPayoutTable();
        System.out.println("Balance: $"+playerBalance);
        Scanner input= new Scanner(System.in);
        System.out.println("Enter bet: ");
        playerBet=input.nextInt();
        playerBalance -=playerBet;
        gameDeck.reset();
        gameDeck.shuffle();
        try{
        playerHand = gameDeck.deal(5);
        }catch(PlayingCardException e){
            System.out.println(e.getMessage());
            
        }
        System.out.println("Hand: "+ playerHand);
       replacement();
       checkHands();
       if(playerBalance==0||playerBalance<playerBet){
           break;
       }
       System.out.println("Your balance:$"+playerBalance+", one more game (y or n)?");
                Scanner input2=new Scanner(System.in);
    		String ifContinue = input2.nextLine();
    		if(ifContinue.equalsIgnoreCase("n"))
    		{
                    System.out.println("Bye!");
    			break;
    		}
    		else
    		{
    			System.out.println("Want to see payout table (y or n)");
    			String ifSeeTable = input.nextLine();
    			if(ifSeeTable.equalsIgnoreCase("y"))
    			{
    				showPayoutTable();
    			}
    		
        }
        }
        
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to replace 
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

        // implement this method!
        

    }

    private void replacement(){
         System.out.println("Enter positions of card to replace");
         Scanner input2 = new Scanner(System.in);
    		String allKeptPosition = input2.nextLine();
    		 
    	    Scanner changedPosition = new Scanner(allKeptPosition).useDelimiter("\\s*");

    	    int replacedCounter = 0;
     		int [] postion = {0,0,0,0,0,0};

    	    while (changedPosition.hasNextInt()) 
    	    {
    	    	postion[changedPosition.nextInt()]=1;
    	    	replacedCounter++; 
    	    }
    		
			List<Card> replacedCards = new ArrayList<>();
    		try
    		{
        		replacedCards = gameDeck.deal(replacedCounter);
    		}
    		catch(PlayingCardException e)
    		{
    			System.out.println("CAN NOT DEAL NEGATIVE NUM CARDS");
    		}
    		
    		for(int i = 1; i <= 5; i++)
    		{
    			if(postion[i]==1)
    			{
    	    		playerHand.set(i-1, replacedCards.remove(0));
    			}
    		}
    		
    		System.out.println("Hand:"+playerHand);

    		
    }
    /****************************************************************
    /* Do not modify methods below
    /****************************************************************

     /* testCheckHands is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 
    public void testCheckHands()
    {
	System.out.println("**** Test checkHands method ****\n");
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(1,3));
		playerHand.add(new Card(10,3));
		playerHand.add(new Card(12,3));
		playerHand.add(new Card(11,3));
		playerHand.add(new Card(13,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(9,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(5,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(8,3));
		playerHand.add(new Card(8,0));
		playerHand.add(new Card(12,3));
		playerHand.add(new Card(8,1));
		playerHand.add(new Card(8,2));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(11,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(11,1));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(9,1));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set One Pair
		playerHand.set(0, new Card(3,1));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set One Pair
		playerHand.set(2, new Card(3,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set no Pair
		playerHand.set(2, new Card(6,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker pokergame = new VideoPoker();
	pokergame.testCheckHands();
    }
}
