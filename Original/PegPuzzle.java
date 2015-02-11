package puzzleGame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PegPuzzle implements ActionListener {
	int moving = 0; // no move has started yet
	// Interface Components
	JButton le;
	JButton lm;
	JButton c;
	JButton rightMid;
	JButton farRight;
	
	int c1, c2; // valid moves have 2 clicks

	/**
	 * Create the GUI and show it. Invoked from the event-dispatching thread for
	 * thread safety.
	 */
	private static void createAndShowGUI() {

		// Create and set up the window.
		JFrame frame = new JFrame("Peg Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cont = frame.getContentPane();
		cont.setLayout(new BorderLayout());
		JPanel board = new JPanel();
		cont.add(board,BorderLayout.CENTER);
		PegPuzzle app = new PegPuzzle();
		app.makeBoard(board);
		JButton newGameBut = new JButton("New Game");
		newGameBut.addActionListener(app);
		cont.add(newGameBut, BorderLayout.SOUTH);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void makeBoard(Container cont) {
		le = new JButton("[X]");
		le.addActionListener(this);
		lm = new JButton("[X]");
		lm.addActionListener(this);
		c = new JButton("[-]");
		c.addActionListener(this);
		rightMid = new JButton("[X]");
		rightMid.addActionListener(this);
		farRight = new JButton("[X]");
		farRight.addActionListener(this);

		cont.setLayout(new GridLayout(1, 0));

		// Add the buttons to form the game grid
		cont.add(le);
		cont.add(lm);
		cont.add(c);
		cont.add(rightMid);
		cont.add(farRight);
	}

	public static void main(String[] args) {
		// Schedule App's GUI create & show for event-dispatching thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	

	public void actionPerformed(ActionEvent ae) {
		// Find which button was pressed
		JButton b = (JButton) ae.getSource();
		
		// determine if pressed button is "not empty" (notMT)
		int notMT = b.getText().indexOf("[X]")+1; // 1 if matches peg string
		boolean occupyed = b.getText().equals("[X]");
		// record location of move's 1st click
		if (moving == 0) {
			if (notMT < 1) // if peg not occupied, not a valid move
				return;
			// Code first click location for future processing
			// c1 is the first click location, c2 is 2nd
			c1 = (b==lm) ? -1 : -2; // assume left edge but check if left mid
			c1 = (b==c) ? 0: c1; // update if button was actually middle location
			c1 = (b == rightMid) ? 1: c1; // change if button pressed was rt mid
			if (b == farRight)
				c1 = 2;
			moving = 1; // good start
			return;
		} else { // 2nd click since move has already started
			// and after this, no matter what happens, the move has ended
			moving = 0; // moving has finished
			if (notMT == 1) {
				// move invalidated (should be empty)
				return;
			}
			// 2nd location empty, set its location code
			if (b == le) c2 = -2;
			if (b == lm) c2 = -1;
			if (b == c) c2 = 0;
			if (b == rightMid) c2 = 1;
			if (b == farRight) c2 = 2;
			// Based on 1st location, determine if 2nd location and
			// "jumped" location give a legal move.
			// If so update the board and reset for next move
			boolean mid;
			switch (c1) {
			case -2: // start from extreme left
				if (c2 != 0)
					return;
				// check if jumped a peg (leftMid)
				mid = lm.getText().equals("[X]");
				if (mid == true) {
					// move peg over and remove jumped peg
					le.setText("[-]");
					lm.setText("[-]");
					c.setText("[X]");
				}
				break;
			case -1: // start from center left
				if (c2 != 1)
					return;
				// check if jumped a peg (center)
				mid = c.getText().equalsIgnoreCase("[X]");
				if (mid == true) {
					// move peg over and remove jumped peg
					lm.setText("[-]");
					c.setText("[-]");
					rightMid.setText("[X]");
				}
				break;

			case 0: // start from center 0
				if (c2 == -2) {
					// check if jumped a peg leftward (leftMid)
					mid = lm.getText().equalsIgnoreCase("[X]");
					if (mid == true) {
						// move peg over and remove jumped peg
						c.setText("[-]");
						lm.setText("[-]");
						le.setText("[X]");
					}
				} else if (c2 == 2) {
					// check if jumped a peg rightward (rightMid)
					mid = rightMid.getText().equals("[X]");
					if (mid == true) {
						// move peg over and remove jumped peg
						c.setText("[-]");
						rightMid.setText("[-]");
						farRight.setText("[X]");
					}
				}
				break;

			case 1: // start from center right
				if (c2 != -1)
					return;
				// check if jumped a peg (center)
				mid = c.getText().equals("[X]");
				if (mid == true) {
					// move peg over and remove jumped peg
					rightMid.setText("[-]");
					c.setText("[-]");
					lm.setText("[X]");
				}
				break;
			case 2: // start from extreme right
				if (c2 != 0)
					return;
				// check if jumped a peg (rightMid)
				mid = rightMid.getText().equals("[X]");
				if (mid == true) {
					// move peg over and remove jumped peg
					farRight.setText("[-]");
					rightMid.setText("[-]");
					c.setText("[x]");
				}
				break;

			} // end of switch
		} // end move has already started block

	} // end of actionPerformed method
}