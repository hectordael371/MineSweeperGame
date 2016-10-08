import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame minesweeperFrame = new JFrame("MineSweeperGame");
        minesweeperFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        minesweeperFrame.setLocation(400, 150);
        minesweeperFrame.setSize(370, 390);
        
        //New interface of the game, 9x9
        MineSweeperInterface minesweeperPanel = new MineSweeperInterface();
        minesweeperFrame.add(minesweeperPanel);

        //Create new object of the ClickEvents class
        ClickEvents minesweeperClickEvent = new ClickEvents();
        
        
        //Listening for mouse click events 
        minesweeperFrame.addMouseListener(minesweeperClickEvent);

        
        //Sets the minesweeperFrame to visible
        minesweeperFrame.setVisible(true);
    }
}