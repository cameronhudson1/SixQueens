/*
 * file: SixQueensView.java 
 * repo: 251GIT/Projects/SixQueens
 */

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.*;

/**
 * Class SixQueensView provides the user interface for the Six Queens Game.
 *
 * @author  CS@RIT.edu
 */
public class SixQueensView implements ModelListener {

    /** GAP between visual components */
    private static final int GAP = 10;

    /** frame is the top level widget for the view. */
    private JFrame frame;

    /** panel for display of the game board */
    private SixQueensJPanel board;

    /** presents status messages to the user. */
    private JTextField messageField;

    /** enable user to start a new game. */
    private JButton newGameButton;

    private ViewListener listener;

/////////////////////////////////////////////////////////
//// Add member fields you need here ////
/////////////////////////////////////////////////////////

    /**
     * SixQueensView
     *
     * Constructs a SixQueensView object
     *
     * @param String name
     * @return None
     */
    private SixQueensView(String name){
        frame = new JFrame( "Six Queens -- " + name );
        JPanel p1 = new JPanel();
        p1.setLayout( new BoxLayout( p1, BoxLayout.Y_AXIS ) );
        p1.setBorder( BorderFactory.createEmptyBorder( GAP, GAP, GAP, GAP ) );
        frame.add( p1 );

        board = new SixQueensJPanel();
        board.setFocusable( false );
        board.setAlignmentX( 0.5f );
        p1.add( board );
        p1.add( Box.createVerticalStrut( GAP ) );

        messageField = new JTextField( 5 );
        messageField.setEditable( false );
        messageField.setFocusable( false );
        messageField.setAlignmentX( 0.5f );
        p1.add( messageField );
        p1.add( Box.createVerticalStrut( GAP ) );

        newGameButton = new JButton( "New Game" );
        newGameButton.setFocusable( false );
        newGameButton.setAlignmentX( 0.5f );
        p1.add( newGameButton );

        board.setListener(new SixQueensJPanelListener(){
            public void squareClicked(int r, int c){
                if (listener != null)
                    listener.squareChosen(r, c, SixQueensView.this);
            }
        });

        newGameButton.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (listener != null)
                    listener.newGame(SixQueensView.this);
            }
        });

        frame.addWindowListener (new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                if (listener != null)
                    listener.quit(SixQueensView.this);
                System.exit (0);
            }
        });

        frame.pack();
        frame.setVisible( true );
    }

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

    /**
     * create
     *
     * Creates and runs a SixQueensView Runnable
     * Includes a UIRef helper class
     *
     * @param String name
     * @
     */
    public static SixQueensView create(String name){
        UIRef uiref = new UIRef();
        onSwingThreadDo(new Runnable(){
                            public void run(){
                                uiref.ui = new SixQueensView(name);
                            }
                        });
        return uiref.ui;
    }
    private static class UIRef{
        public SixQueensView ui;
    }


    /**
     * setViewListener
     * 
     * Sets the model as the ViewListener for this SixQueensView
     * @param ViewListener listener
     * @return None
     */
    public void setViewListener(ViewListener listener){
        onSwingThreadDo (new Runnable(){
            public void run(){
                SixQueensView.this.listener = listener;
            }
        });
    }

    /**
     * newGame
     * 
     * Handles the swing thread generation for a new game
     *
     * @param None
     * @return None
     */
    public void newGame(){
        onSwingThreadDo (new Runnable(){
            public void run(){
                board.clear();
            }
        });
    }

    /**
     * setQueen
     * 
     * Handles the swing thread generation for setting a queen
     *
     * @param int r
     * @param int c
     * @return None
     */
    public void setQueen(int r, int c){
        onSwingThreadDo (new Runnable(){
            public void run(){
                board.setQueen(r, c, true);
            }
        });
    }

    /**
     * setVisible
     * 
     * Handles the swing thread generation for setting a square to visible
     *
     * @param int r
     * @param int c
     * @return None
     */
    public void setVisible(int r, int c){
        onSwingThreadDo (new Runnable(){
            public void run(){
                board.setVisible(r, c, false);
            }
        });
    }

    /**
     * waitingForPartner
     * 
     * Handles the swing thread generation for setting message to
     * "Waiting for Partner"
     *
     * @param None
     * @return None
     */
    public void waitingForPartner(){
        onSwingThreadDo (new Runnable(){
            public void run(){
                messageField.setText ("Waiting for partner");
                newGameButton.setEnabled (false);
            }
        });
    }

    /**
     * yourTurn
     * 
     * Handles the swing thread generation for setting message to
     * "Your Turn"
     *
     * @param None
     * @return None
     */
    public void yourTurn(){
        onSwingThreadDo (new Runnable(){
            public void run(){
                messageField.setText ("Your turn");
                newGameButton.setEnabled (true);
            }
        });
    }

    /**
     * otherTurn
     * 
     * Handles the swing thread generation for setting message to
     * "Other Turn"
     *
     * @param None
     * @return None
     */
    public void otherTurn(String name){
        onSwingThreadDo (new Runnable(){
            public void run(){
                messageField.setText(name + "'s turn");
                newGameButton.setEnabled (true);
            }
        });
    }

    /**
     * youWin
     * 
     * Handles the swing thread generation for setting message to
     * "You Win"
     *
     * @param None
     * @return None
     */
    public void youWin(){
        onSwingThreadDo (new Runnable(){
            public void run(){
                messageField.setText ("You win!");
                newGameButton.setEnabled (true);
            }
        });
    }

    /**
     * otherWin
     * 
     * Handles the swing thread generation for setting message to
     * "Other Win"
     *
     * @param None
     * @return None
     */
    public void otherWin(String name){
        onSwingThreadDo (new Runnable(){
            public void run(){
                messageField.setText (name + " wins!");
                newGameButton.setEnabled (true);
            }
        });
    }

    /**
     * quit
     * 
     * Exits on quit of other view
     *
     * @param None
     * @return None
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * onSwingThreadDo
     * 
     * Wrapper call to swing thread
     *
     * @param Runnable task
     * @return None
     */
    private static void onSwingThreadDo(Runnable task){
        try{
            SwingUtilities.invokeAndWait (task);
        } catch (Throwable exc) {
            exc.printStackTrace (System.err);
            System.exit (1);
        }
    }

}
