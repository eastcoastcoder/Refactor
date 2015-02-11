// PegPuzzle (1-Row)
// Author: Ethan Richardson
// Original Author: Michael Wainer
//
// Creates a simple 1-row peg puzzle game 
// Uses swing elements such as JButton

package puzzleGame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class NewPegPuzzle implements ActionListener {
	int startPosition, currentPosition; // valid moves have 2 clicks
	boolean isMoveInProgress = false;
	public static final String PEG = "[X]";
	public static final String HOLE = "[-]";
	public static final String WINDOW_TITLE = "Peg Puzzle";
	public static final String NEW = "New Game";
	
	// Interface Components
	JFrame frame;
	JButton farLeft, left, center, right, farRight;

	// Invoked from the event-dispatching thread for thread safety
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cont = frame.getContentPane();
		cont.setLayout(new BorderLayout());
		JPanel board = new JPanel();
		cont.add(board,BorderLayout.CENTER);
		NewPegPuzzle app = new NewPegPuzzle();
		app.makeBoard(board);
		JButton newGameBut = new JButton(NEW);
		newGameBut.addActionListener(app);
		cont.add(newGameBut, BorderLayout.SOUTH);
		
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public void makeBoard(Container cont) {
		cont.setLayout(new GridLayout(1, 0));
		
		farLeft = createAndAddLoc(cont,PEG, -2 );
		left = createAndAddLoc(cont,PEG, -1 );
		center = createAndAddLoc(cont,HOLE, 0 );
		right = createAndAddLoc(cont,PEG, 1 );
		farRight = createAndAddLoc(cont,PEG, 2 );
	}
	
	private JButton createAndAddLoc(Container cont, String initValue, int locID) {
		JButton locButton = new JButton(initValue);
		locButton.addActionListener(this);
		cont.add(locButton);
		return locButton;
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
		JButton buttonContents = (JButton) ae.getSource();
		boolean occupied = buttonContents.getText().equals("[X]");
		
		// Reset
		if (buttonContents.getText() == NEW){
			writeButton(farLeft, PEG,
					 	left, PEG,
					 	center, HOLE,
					 	right, PEG,
					 	farRight, PEG);
		}
		
		// 2nd Click
		if (isMoveInProgress) {
			isMoveInProgress = false;
			if (occupied) return;
			currentPosition = buttonSet(buttonContents);
			if (isMoveLegal(startPosition, currentPosition))
				doMove(startPosition, currentPosition);
		} 
		// 1st Click
		else if (occupied) {
			startPosition = buttonSet(buttonContents);
			isMoveInProgress = true;
		}
		return;
	}
	
	// Associates positioning data for button
	private int buttonSet(JButton button){
		if (button == farLeft) return -2;
		if (button == left) return -1;
		if (button == center) return 0;
		if (button == right) return 1;
		if (button == farRight) return 2;
		return 0;
	}
	
	// Based on 1st location, determine if 2nd location and
	// "jumped" location give a legal move.
	private boolean isMoveLegal(int startPosition, int currentPosition){
		switch (currentPosition){
			case -2:
				if (startPosition != 0) return false;
				return left.getText().equals(PEG);
				
			case -1:
				if (startPosition != 1) return false;
				return center.getText().equals(PEG);
		
			case 0:
				if(startPosition == -2) return left.getText().equals(PEG);
				else if(startPosition == 2) return right.getText().equals(PEG);
			
			case 1:
				if(startPosition != -1) return false;
				return center.getText().equals(PEG);
		
			case 2:
				if(startPosition != 0) return false;	
				return right.getText().equals(PEG);
			
			default:
				return false;
		}
	}


	// Updates the board:
	// Moves peg over and removes jumped pegs
	private void doMove(int startPosition, int currentPosition){	
		switch (startPosition) {
			// farLeft
			case -2:
				writeButton(farLeft, HOLE, left, HOLE, center, PEG);
				break;
			// left
			case -1:
				writeButton(left, HOLE, center, HOLE, right, PEG);
				break;
			// center
			case 0:
				if (currentPosition == -2) {
					writeButton(farLeft, PEG, left, HOLE, center, HOLE);
				} else if (currentPosition == 2) {
					writeButton(center, HOLE, right, HOLE, farRight, PEG);
				}
				break;
			// right
			case 1:
				writeButton(left, PEG, center, HOLE, right, HOLE);
				break;
			// farRight
			case 2:
				writeButton(center, PEG, right, HOLE, farRight, HOLE);
				break;
		}
	}
		
	private void writeButton(JButton first, String firstContents,
							JButton second, String secondContents,
							JButton third, String thirdContents){
			first.setText(firstContents);
			second.setText(secondContents);
			third.setText(thirdContents);
			
	}

	private void writeButton(JButton farLeft, String farLeftContents,
							JButton left, String leftContents,
							JButton center, String centerContents,
							JButton right, String rightContents,
							JButton farRight, String farRightContents){
			writeButton(farLeft, farLeftContents,
						left, leftContents,
						center, centerContents);

			right.setText(rightContents);
			farRight.setText(farRightContents);
	}
}