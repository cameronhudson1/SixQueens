/*
 * file: SixQueensJPanelListener.java 
 * repo: 251GIT/Projects/SixQueens
 */

/**
 * Interface SixQueensJPanelListener is for an object that
 * listens for clicks on a {@linkplain SixQueensJPanel}.
 *
 * @author  CS@RIT.EDU
 */
public interface SixQueensJPanelListener {

    /**
     * Report that the given board square was clicked.
     *
     * @param  r  Row.
     * @param  c  Column.
     */
    public void squareClicked( int r, int c );

}
