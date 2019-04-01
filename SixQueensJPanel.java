/*
 * file: SixQueensJPanel.java 
 * repo: 251GIT/Projects/SixQueens
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * Class SixQueensJPanel provides a Swing widget for displaying a Six Queens
 * game board.
 *
 * @author  CS@RIT.edu
 */
public class SixQueensJPanel extends JPanel {

    /** serialVersionUID is version 2 of this component. */
    public static final long serialVersionUID = 2L;

    /** ROWS sets the game board height in cells. */
    public static final int ROWS = 6;

    /** COLS sets the game board width in cells. */
    public static final int COLS = 6;

    /** L is the starting line. */
    private static final int L = 1;

    /** W is the width. */
    private static final int W = 40;

    /** D is the diameter of Queen to draw. */
    private static final int D = 20;

    /** LINE_COLOR is the grid line. */
    private static final Color LINE_COLOR = Color.BLACK;

    /** SQUARE_COLOR is the cell's color. */
    private static final Color SQUARE_COLOR = Color.WHITE;

    /** QUEEN_COLOR is the color of the 'dot' representing the queen. */
    private static final Color QUEEN_COLOR = Color.BLUE;

    /** LINE_STROKE is gridline. */
    private static final Stroke LINE_STROKE =
        new BasicStroke( L, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL );

    /** visible grid tracks which cells in the game board are visible. */
    private boolean[][] visible = new boolean [ROWS] [COLS];

    /** queen grid tracks the attackable queens on board. */
    private boolean[][] queen = new boolean [ROWS] [COLS];

    /** listener is the listener to notify when a cell is clicked. */
    private SixQueensJPanelListener listener;

    /**
     * Construct a new Six Queens game board widget.
     */
    public SixQueensJPanel() {

        super();
        Dimension d = new Dimension( COLS*W, ROWS*W );
        setMinimumSize( d );
        setPreferredSize( d );
        setMaximumSize( d );
        addMouseListener( new MouseAdapter() {

            public void mouseClicked( MouseEvent e ) {
                if ( listener != null ) {
                    int r = e.getY()/(L+W);
                    int c = e.getX()/(L+W);
                    if ( visible[r][c] && ! queen[r][c] )
                        listener.squareClicked( r, c );
                }
            }
        });
    }

    /**
     * Set this game board widget's listener.
     *
     * @param  listener  Board listener.
     */
    public void setListener( SixQueensJPanelListener listener ) {
        this.listener = listener;
    }

    /**
     * Clear this game board widget.
     */
    public void clear( ) {
        for ( int r = 0; r < ROWS; ++r )
            for ( int c = 0; c < COLS; ++c )
                {
                visible[r][c] = true;
                queen[r][c] = false;
                }
        repaint();
    }

    /**
     * Set the given square visible or invisible.
     *
     * @param  r  Row.
     * @param  c  Column.
     * @param  v  True for visible, false for invisible.
     */
    public void setVisible( int r, int c, boolean v ) {
        visible[r][c] = v;
        repaint();
    }

    /**
     * Set the given square with a queen or no queen.
     *
     * @param  r  Row.
     * @param  c  Column.
     * @param  q  True for queen, false for no queen.
     */
    public void setQueen( int r, int c, boolean q ) {
        queen[r][c] = q;
        repaint();
    }

    /**
     * Paint this component.
     *
     * @param  g  Graphics context.
     */
    protected void paintComponent( Graphics g ) {

        super.paintComponent( g );
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON );

        // Draw squares.
        for ( int r = 0; r < ROWS; ++r ) {
            for ( int c = 0; c < COLS; ++c ) {
                if ( visible[r][c] ) {
                    // Draw interior.
                    g2d.setColor( SQUARE_COLOR );
                    g2d.fill( new Rectangle2D.Float( c*W, r*W, W, W ) );

                    // Draw border.
                    g2d.setColor( LINE_COLOR );
                    g2d.setStroke( LINE_STROKE );
                    g2d.draw( new Rectangle2D.Float( c*W, r*W, W-L, W-L ) );

                    // Draw queen.
                    if ( queen[r][c] ) {
                        g2d.setColor( QUEEN_COLOR );
                        g2d.fill( new Ellipse2D.Float( c*W+(W-D)/2,
                                                       r*W+(W-D)/2, D, D ) );
                    }
                }
            }
        }
    }

}
