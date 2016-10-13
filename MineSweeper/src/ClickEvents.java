import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class ClickEvents extends MouseAdapter {
	int counter = 0;
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
        case 1:        //Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MineSweeperInterface msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			msPanel.mouseDownGridX = msPanel.getGridX(x, y);
			msPanel.mouseDownGridY = msPanel.getGridY(x, y);
			msPanel.repaint();
            break;
        case 3:        //Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			msPanel.mouseDownGridX = msPanel.getGridX(x, y);
			msPanel.mouseDownGridY = msPanel.getGridY(x, y);
			msPanel.repaint();
            break;
        default:    //Some other button (2 = Middle mouse button, etc.)
        	c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			msPanel.mouseDownGridX = msPanel.getGridX(x, y);
			msPanel.mouseDownGridY = msPanel.getGridY(x, y);
			msPanel.repaint();
            break;
        }
    }
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
        case 1:        //Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MineSweeperInterface msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			int gridX = msPanel.getGridX(x, y);
			int gridY = msPanel.getGridY(x, y);
		
			
			
			if ((msPanel.mouseDownGridX == -1) || (msPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing				
					} 
				else {
					if ((msPanel.mouseDownGridX != gridX) || (msPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					}
					else {
						//Released the mouse button on the same cell where it was pressed
						if(msPanel.booleanArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY]){
							//Clicks a bomb
							for(int n=0; n<msPanel.TOTAL_COLUMNS; n++){
								for(int m=0; m<msPanel.TOTAL_ROWS; m++){
									if(msPanel.booleanArray[n][m])
										msPanel.colorArray[n][m] = Color.BLACK;
								}
							}
							msPanel.repaint();
						 }
						else{
							msPanel.searchBombs();
							if(false){
								//Clear adjacent blocks
								msPanel.clearBlocks(); 
							}
							else{
								msPanel.colorArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY] = Color.LIGHT_GRAY;
								System.out.println(msPanel.numbersArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY]);

							}
							
							msPanel.repaint();
							}
						}
					}
				}
            break;
        case 3:        //Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame)c;
			msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			gridX = msPanel.getGridX(x, y);
			gridY = msPanel.getGridY(x, y);
			if ((msPanel.mouseDownGridX == -1) || (msPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing				
					} 
				else {
					if ((msPanel.mouseDownGridX != gridX) || (msPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					}
					else {
						//Released the mouse button on the same cell where it was pressed
						if(msPanel.colorArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY].equals(Color.GRAY)){
							Color newColor = Color.RED;
							msPanel.colorArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY] = newColor;
							msPanel.repaint();
						}
						else if(msPanel.colorArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY].equals(Color.RED)){
							Color newColor = Color.GRAY;
							msPanel.colorArray[msPanel.mouseDownGridX][msPanel.mouseDownGridY] = newColor;
							msPanel.repaint();
						}
					}
				}
			}
			break;
        default:    //Some other button (2 = Middle mouse button, etc.)
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame)c;
			msPanel = (MineSweeperInterface) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			msPanel.x = x;
			msPanel.y = y;
			gridX = msPanel.getGridX(x, y);
			gridY = msPanel.getGridY(x, y);
			if ((msPanel.mouseDownGridX == -1) || (msPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Resets the game.
				counter = 0;
				for(int n=0; n<msPanel.TOTAL_COLUMNS; n++){
					for(int m=0; m<msPanel.TOTAL_ROWS; m++){
						msPanel.colorArray[n][m] = Color.GRAY;
					}
				}
			}
            break;
    }
  }
}