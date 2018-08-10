/* Exercises 15.1 and 15.5
 * 
 * The following code contains solutions for Exercises 15.1 and 15.5.  For exercise 15.1, 
 * the merge() method in the Deck class is implemented as an object method rather than a
 * class method (although the original class method is still shown).
 * 
 * For Exercise 15.5, the following code implements all the required elements of the exercise.
 * It provides a PokerHand class (extending Deck) that can be dealt with a deal() method.  For 7 
 * card hands, a deal7Hand() method is available.  Additionally, the PokerHand class has a 
 * hasFlush() method that checks if a certain hand has a flush, and a hasThreeKind() method to 
 * check if a certain hand has a three of a kind.  There are 6 other similar methods that check 
 * if a hand satisfies a particular category of poker hand (e.g. full house, straight).  Many of 
 * these methods rely on the use of the arrays numRank and numSuit, which respectively correspond 
 * to the number of cards in each rank there are and the number of cards in each suit there are 
 * in the hand.  Finally, all these methods are built to work for a poker hand of size 7, where one
 * can only choose the best 5 cards to satisfy the requirement.
 * 
 * In the main class, 4 random poker hands are generated and printed to demonstrate the deal() 
 * method.  Some specific hands are also created to demonstrate the methods corresponding to
 * specific poker hand categories (e.g. hasFullHouse()).  Finally, as required by the exercise,
 * the program loops through 100,000 randomly generated PokerHands and records the number of 
 * flushes and three-of-a-kinds to estimate the probability of obtaining each.
 * 
 * Thanks to Allen B. Downey's textbook Think AP Java.
 * The base CardSoln3 code can be found here: 
 * https://github.com/AllenDowney/thinkjava5/tree/master/code/Cards
 * 
 */

public class CardSoln3 {
	
	public static void main(String[] args) {
		Deck deck = new Deck();

		// deal 4 poker hands
		for (int i = 1; i <= 4; i++) {
			System.out.println("Hand " + i + ": ");
			deck.shuffle();
			PokerHand p = deck.deal();
			p.updateArrays();
			p.print();
			System.out.println();
		}

		// test hasFlush() and hasStraightFlush() methods
		System.out.println("Test Hand 1:");
		PokerHand p2 = new PokerHand(5);
		p2.cards[0] = new Card(1, 3); // 3 of diamonds
		p2.cards[1] = new Card(1, 5); // 5 of diamonds
		p2.cards[2] = new Card(1, 4); // 4 of diamonds
		p2.cards[3] = new Card(1, 6); // 6 of diamonds
		p2.cards[4] = new Card(1, 7); // 7 of diamonds
		p2.updateArrays();
		p2.print();
		System.out.println("hasFlush: " + p2.hasFlush());
		System.out.println("hasStraight: " + p2.hasStraight());
		System.out.println("hasStraightFlush: " + p2.hasStraightFlush());
		System.out.println();

		System.out.println("Test Hand 2:");
		p2.cards[0] = new Card(1, 3); // 3 of diamonds
		p2.cards[1] = new Card(2, 3); // 3 of hearts
		p2.cards[2] = new Card(1, 4); // 4 of diamonds
		p2.cards[3] = new Card(3, 3); // 3 of spades
		p2.cards[4] = new Card(2, 4); // 4 of hearts
		p2.updateArrays();
		p2.print();
		System.out.println("hasPair: " + p2.hasPair());
		System.out.println("hasDoublePair: " + p2.hasDoublePair());
		System.out.println("hasThreeKind: " + p2.hasThreeKind());
		System.out.println("hasFullHouse: " + p2.hasFullHouse());
		System.out.println("hasFourKind: " + p2.hasFourKind());
		System.out.println();

		System.out.println("Test Hand 3:");
		PokerHand p3 = new PokerHand(7);
		p3.cards[0] = new Card(1, 3); // 3 of diamonds
		p3.cards[1] = new Card(2, 3); // 3 of hearts
		p3.cards[2] = new Card(1, 4); // 4 of diamonds
		p3.cards[3] = new Card(3, 3); // 3 of spades
		p3.cards[4] = new Card(2, 4); // 4 of hearts
		p3.cards[5] = new Card(0, 3); // 3 of clubs
		p3.cards[6] = new Card(2, 5); // 5 of hearts
		p3.updateArrays();
		p3.print();
		System.out.println("hasPair: " + p3.hasPair());
		System.out.println("hasDoublePair: " + p3.hasDoublePair());
		System.out.println("hasThreeKind: " + p3.hasThreeKind());
		System.out.println("hasFullHouse: " + p3.hasFullHouse());
		System.out.println("hasFourKind: " + p3.hasFourKind());
		System.out.println();

		// loops through 100000 random poker hands, sees how many
		// flushes/three-of-a-kinds occur
		int numFlushes = 0, numThreeKind = 0;
		for (int i = 1; i <= 100000; i++) {
			deck.shuffle();
			PokerHand test = deck.deal();

			if (test.hasFlush())
				numFlushes++;
			if (test.hasThreeKind())
				numThreeKind++;
		}

		// probabilities calculated from the test
		System.out.println("Approximate probability of flush: " + numFlushes / 100000.0);
		// real probability approx. 0.00198079

		System.out.println("Approximate probability of three-of-a-kind: " + numThreeKind / 100000.0);
		// real probability approx. 0.021128

	}

	/*
	 * Checks that the deck is sorted.
	 */
	public static void checkSorted(Deck deck) {
		for (int i = 0; i < 51; i++) {
			int flag = deck.cards[i].compareTo(deck.cards[i + 1]);
			if (flag != -1) {
				System.out.println("Not sorted!");
			}
		}
	}
}

/*
 * A Card represents a playing card with rank and suit.
 */
class Card {
	int suit, rank;

	static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
	static String[] ranks = { "narf", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

	/*
	 * No-argument constructor.
	 */
	public Card() {
		this.suit = 0;
		this.rank = 0;
	}

	/*
	 * Constructor with arguments.
	 */
	public Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	/*
	 * Prints a card in human-readable form.
	 */
	public void print() {
		System.out.println(ranks[rank] + " of " + suits[suit]);
	}

	/*
	 * Prints a card in human-readable form.
	 */
	public String toString() {
		return ranks[rank] + " of " + suits[suit];
	}

	/*
	 * Return true if the cards are equivalent.
	 */
	public boolean equals(Card that) {
		return (this.suit == that.suit && this.rank == that.rank);
	}

	/*
	 * Compares two cards: returns 1 if the first card is greater -1 if the
	 * seconds card is greater, and 0 if they are the equivalent.
	 */
	public int compareTo(Card that) {

		// first compare the suits
		if (this.suit > that.suit)
			return 1;
		if (this.suit < that.suit)
			return -1;

		// if the suits are the same,
		// use modulus arithmetic to rotate the ranks
		// so that the Ace is greater than the King.
		// WARNING: This only works with valid ranks!
		int rank1 = (this.rank + 11) % 13;
		int rank2 = (that.rank + 11) % 13;

		// compare the rotated ranks

		if (rank1 > rank2)
			return 1;
		if (rank1 < rank2)
			return -1;
		return 0;
	}
}

/*
 * A Deck contains an array of cards.
 */
class Deck {
	Card[] cards;

	/*
	 * Makes a Deck with room for n Cards (but no Cards yet).
	 */
	public Deck(int n) {
		this.cards = new Card[n];
	}

	/*
	 * Makes an array of 52 cards.
	 */
	public Deck() {
		this.cards = new Card[52];

		int index = 0;
		for (int suit = 0; suit <= 3; suit++) {
			for (int rank = 1; rank <= 13; rank++) {
				this.cards[index] = new Card(suit, rank);
				index++;
			}
		}
	}

	/*
	 * Prints a deck of cards.
	 */
	public void print() {
		for (int i = 0; i < cards.length; i++) {
			cards[i].print();
		}
	}

	/*
	 * Returns index of the first location where card appears in deck. Or -1 if
	 * it does not appear. Uses a linear search.
	 */
	public int findCard(Card card) {
		for (int i = 0; i < cards.length; i++) {
			if (card.equals(cards[i])) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * Returns index of a location where card appears in deck. Or -1 if it does
	 * not appear. Uses a bisection search.
	 *
	 * Searches from low to high, including both.
	 *
	 * Precondition: the cards must be sorted from lowest to highest.
	 */
	public int findBisect(Card card, int low, int high) {
		if (high < low)
			return -1;

		int mid = (high + low) / 2;
		int comp = card.compareTo(cards[mid]);

		if (comp == 0) {
			return mid;
		} else if (comp < 0) {
			// card is less than cards[mid]; search the first half
			return findBisect(card, low, mid - 1);
		} else {
			// card is greater; search the second half
			return findBisect(card, mid + 1, high);
		}
	}

	/*
	 * Chooses a random integer between low and high, including low, not
	 * including high.
	 */
	public int randInt(int low, int high) {
		// Because Math.random can't return 1.0, and (int)
		// always rounds down, this method can't return high.
		int x = (int) (Math.random() * (high - low) + low);
		return x;
	}

	/*
	 * Swaps two cards in a deck of cards.
	 */
	public void swapCards(int i, int j) {
		Card temp = cards[i];
		cards[i] = cards[j];
		cards[j] = temp;
	}

	/*
	 * Shuffles the cards in a deck.
	 */
	public void shuffle() {
		for (int i = 0; i < cards.length; i++) {
			int j = randInt(i, cards.length - 1);
			swapCards(i, j);
		}
	}

	/*
	 * Sorts a deck from low to high.
	 */
	public void sort() {
		for (int i = 0; i < cards.length; i++) {
			int j = indexLowestCard(i, cards.length - 1);
			swapCards(i, j);
		}
	}

	/*
	 * Finds the index of the lowest card between low and high, including both.
	 */
	public int indexLowestCard(int low, int high) {
		int winner = low;
		for (int i = low + 1; i <= high; i++) {
			if (cards[i].compareTo(cards[winner]) < 0) {
				winner = i;
			}
		}
		return winner;
	}

	/*
	 * Makes a new deck of cards with a subset of cards from the original. The
	 * old deck and new deck share references to the same card objects.
	 */
	public Deck subdeck(int low, int high) {
		Deck sub = new Deck(high - low + 1);

		for (int i = 0; i < sub.cards.length; i++) {
			sub.cards[i] = cards[low + i];
		}
		return sub;
	}

	/*
	 * Merges two sorted decks into a new sorted deck.
	 */
	public static Deck merge(Deck d1, Deck d2) {
		// create the new deck
		Deck result = new Deck(d1.cards.length + d2.cards.length);

		int choice; // records the winner (1 means d1, 2 means d2)
		int i = 0; // traverses the first input deck
		int j = 0; // traverses the second input deck

		// k traverses the new (merged) deck
		for (int k = 0; k < result.cards.length; k++) {
			choice = 1;

			// if d1 is empty, d2 wins; if d2 is empty, d1 wins; otherwise,
			// compare the two cards
			if (i == d1.cards.length)
				choice = 2;
			else if (j == d2.cards.length)
				choice = 1;
			else if (d1.cards[i].compareTo(d2.cards[j]) > 0)
				choice = 2;

			// make the new deck refer to the winner card
			if (choice == 1) {
				result.cards[k] = d1.cards[i];
				i++;
			} else {
				result.cards[k] = d2.cards[j];
				j++;
			}
		}
		return result;
	}
	
	// merge, transformed into an object method
	public Deck merge(Deck d2) {
		// create the new deck
		Deck result = new Deck(this.cards.length + d2.cards.length);

		int choice; // records the winner (1 means d1, 2 means d2)
		int i = 0; // traverses the first input deck
		int j = 0; // traverses the second input deck

		// k traverses the new (merged) deck
		for (int k = 0; k < result.cards.length; k++) {
			choice = 1;

			// if d1 is empty, d2 wins; if d2 is empty, d1 wins; otherwise,
			// compare the two cards
			if (i == this.cards.length)
				choice = 2;
			else if (j == d2.cards.length)
				choice = 1;
			else if (this.cards[i].compareTo(d2.cards[j]) > 0)
				choice = 2;

			// make the new deck refer to the winner card
			if (choice == 1) {
				result.cards[k] = this.cards[i];
				i++;
			} else {
				result.cards[k] = d2.cards[j];
				j++;
			}
		}
		return result;
	}

	/*
	 * Sort the Deck using merge sort.
	 */
	public Deck mergeSort() {
		if (cards.length < 2) {
			return this;
		}
		int mid = (cards.length - 1) / 2;

		// divide the deck roughly in half
		Deck d1 = subdeck(0, mid);
		Deck d2 = subdeck(mid + 1, cards.length - 1);

		// sort the halves
		d1 = d1.mergeSort();
		d2 = d2.mergeSort();

		// merge the two halves and return the result
		// (d1 and d2 get garbage collected)
		return d1.merge(d2);
	}

	public PokerHand deal() {
		PokerHand hand = new PokerHand(5);
		for (int i = 0; i < 5; i++) {
			hand.cards[i] = this.cards[i];
		}
		hand.updateArrays();
		return hand;
	}

	public PokerHand deal7Hand() {
		PokerHand hand = new PokerHand(7);
		for (int i = 0; i < 7; i++) {
			hand.cards[i] = this.cards[i];
		}
		return hand;
	}
}

class PokerHand extends Deck {
	int[] numRank = new int[13]; // number of each rank e.g. numRank[0] = 4
									// means there are 4 aces

	int[] numSuit = new int[4]; // number of each suit e.g. numSuit[0] = 4
								// implies there are 4 clubs

	public PokerHand(int n) {
		super(n);

		for (int i = 0; i < 4; i++)
			numSuit[i] = 0;
		for (int i = 0; i < 13; i++)
			numRank[i] = 0;

	}

	// updates the numRank and numSuit arrays
	public void updateArrays() {
		for (int i = 0; i < 13; i++)
			numRank[i] = 0;
		for (int i = 0; i < 4; i++)
			numSuit[i] = 0;

		// update numRank and numSuit
		for (int i = 0; i < this.cards.length; i++) {
			numRank[this.cards[i].rank - 1]++;
			numSuit[this.cards[i].suit]++;
		}

	}

	// returns true if this hand has a pair, false if not
	public boolean hasPair() {
		for (int i = 0; i < 13; i++)
			if (numRank[i] == 2)
				return true;
		return false;
	}
	
	// returns true if this hand has two pairs, false if not
	public boolean hasDoublePair() {
		int numPairs = 0;
		for (int i = 0; i < 13; i++)
			if (numRank[i] >= 2)
				numPairs++;

		if (numPairs >= 2)
			return true;
		return false;
	}

	// returns true if this hand has a three-of-a-kind, false if not
	public boolean hasThreeKind() {
		for (int i = 0; i < 13; i++) {
			if (numRank[i] >= 3)
				return true;
		}

		return false;
	}
	
	// returns true if this hand has a straight, false if not
	public boolean hasStraight() {
		this.sort(); // sort in ascending order

		for (int i = 0; i < this.cards.length - 4; i++) {
			// idea: see if 4th card from current card is +4 from current card

			if (cards[i].rank < 10) {
				if (cards[i + 4].rank == cards[i].rank + 4)
					return true;
			} else if (cards[i].rank == 10) { // could be 10, J, Q, K, A
				if (cards[i + 4].rank == 1)
					return true;
			}
		}

		// the lowest straight in this deck must start with a royal,
		// which is illegal
		return false;
	}

	// returns true if this hand has a flush, false if not
	public boolean hasFlush() {
		for (int i = 0; i < 4; i++) {
			if (numSuit[i] == 5) {
				return true;
			}
		}
		return false;
	}

	// returns true if this hand has a full house, false if not
	public boolean hasFullHouse() {
		boolean has2 = false, has3 = false;
		for (int i = 0; i < 13; i++) {
			if (numRank[i] >= 3)
				has3 = true;
			else if (numRank[i] >= 2)
				has2 = true;
		}
		return (has2 && has3);
	}

	// returns true if this hand has a four-of-a-kind, false if not
	public boolean hasFourKind() {
		for (int i = 0; i < 13; i++) {
			if (numRank[i] == 4)
				return true;
		}
		return false;
	}

	// returns true if this hand has a straight flush, false if not
	public boolean hasStraightFlush() {
		this.sort(); // sort in ascending order

		for (int i = 0; i < this.cards.length - 4; i++) {
			// idea: see if 4th card from current card is +4 from current card

			if (cards[i].rank < 10) {
				if (cards[i + 4].rank == cards[i].rank + 4) {// found a straight
					boolean isFlush = true;
					for (int j = i + 1; j <= i + 4; j++) {
						if (cards[j].suit != cards[i].suit)
							isFlush = false;
					}
					if (isFlush)
						return true;
				}
			} else if (cards[i].rank == 10) { // could be10, J, Q, K, A
				if (cards[i + 4].rank == 1) { // found straight
					boolean isFlush = true;
					for (int j = i + 1; j <= i + 4; j++) {
						if (cards[j].suit != cards[i].suit)
							isFlush = false;
					}
					if (isFlush)
						return true;
				}
			}
		}
		return false;
	}

}