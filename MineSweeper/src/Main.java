import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        JFrame minesweeperFrame = new JFrame("MineSweeperGame");
        minesweeperFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        minesweeperFrame.setLocation(400, 150);
        minesweeperFrame.setSize(340, 360);
        
        //New interface of the game, 9x9
        
        MineSweeperInterface minesweeperPanel = new MineSweeperInterface();
        minesweeperFrame.add(minesweeperPanel);

        //Create new object of the ClickEvents class
        ClickEvents minesweeperClickEvent = new ClickEvents();
        
        
        //Listening for mouse click events 
        minesweeperFrame.addMouseListener(minesweeperClickEvent);

        
        //Sets the minesweeperFrame to visible
        minesweeperFrame.setVisible(true);
        String instructions1 = "How to Play: \nLeft click to a reveal cell. \nUncovering a cell will reveal how many adjacent bombs there are.\n"; 
        String instructions2 = "Right click to flag/unflag cells. \nMiddle click outside the grid to reset the game.\n";
        JOptionPane.showMessageDialog(null, instructions1 + instructions2, "MineSweeperGame", JOptionPane.INFORMATION_MESSAGE);
    }
}