import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MineSweeperInterface extends JPanel {
    private static final long serialVersionUID = 3426940946811133635L;
    private static final int GRID_X = 25;
    private static final int GRID_Y = 25;
    private static final int INNER_CELL_SIZE = 29;
    
    public int TOTAL_COLUMNS = 10;
    public int TOTAL_ROWS = 10;   
    public int x = -1;
    public int y = -1;
    public int mouseDownGridX = 0;
    public int mouseDownGridY = 0;
    public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
    public boolean[][] booleanArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    public int[][] counterArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
    
    public MineSweeperInterface() {   //This is the constructor... this code runs first to initialize
        if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {    //Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("INNER_CELL_SIZE must be positive!");
        }
        if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {    //Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
        }
        if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {    //Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_ROWS must be at least 3!");
        }
        
        for (int x = 0; x < TOTAL_COLUMNS; x++) {  
            for (int y = 0; y < TOTAL_ROWS; y++) {
                colorArray[x][y] = Color.GRAY;
            }
        }
        
        placeBombs();
       
       
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Compute interior coordinates
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        int x2 = getWidth() - myInsets.right - 1;
        int y2 = getHeight() - myInsets.bottom - 1;
        int width = x2 - x1;
        int height = y2 - y1;

        //Paint the background
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, width + 1, height + 1);

        //By default, the grid will be 9x9 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
        g.setColor(Color.BLACK);
        
        for (int y = 0; y <= TOTAL_ROWS ; y++) {
            g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
        }
        for (int x = 0; x <= TOTAL_COLUMNS; x++) {
            g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
        }
        //Paint cell colors
        for (int x = 0; x < TOTAL_COLUMNS; x++) {
            for (int y = 0; y < TOTAL_ROWS; y++) {
            	Color c = colorArray[x][y];
            	counterArray[x][y] = 0;
                g.setColor(c);
                g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);               
            }
        }
    }
    
    public int getGridX(int x, int y) {
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        x = x - x1 - GRID_X;
        y = y - y1 - GRID_Y;
        if (x < 0) {   //To the left of the grid
            return -1;
        }
        if (y < 0) {   //Above the grid
            return -1;
        }
        if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
            return -1;
        }
        x = x / (INNER_CELL_SIZE + 1);
        y = y / (INNER_CELL_SIZE + 1);
        
        if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
            return -1;
        }
        return x;
    }
    public int getGridY(int x, int y) {
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        x = x - x1 - GRID_X;
        y = y - y1 - GRID_Y;
        if (x < 0) {   //To the left of the grid
            return -1;
        }
        if (y < 0) {   //Above the grid
            return -1;
        }
        if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
            return -1;
        }
        x = x / (INNER_CELL_SIZE + 1);
        y = y / (INNER_CELL_SIZE + 1);
        if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
            return -1;
        }
        return y;
    }
    //generates bombs in random grids
    public void placeBombs(){
    	Random r = new Random();
    	int numBombs = 10;
    	
    	//Set all bombs to false
		for(int n=0; n<TOTAL_COLUMNS; n++){
			for(int m=0; m<TOTAL_ROWS; m++){
                booleanArray[n][m] = false;
			}
		}  	
    	for(int i=0; i<numBombs-4; i++){
    		
    		int n = r.nextInt(TOTAL_COLUMNS);
    		int m = r.nextInt(TOTAL_ROWS);
    		booleanArray[0][0] = true;
    		booleanArray[9][0] = true;
    		booleanArray[0][9] = true;
    		booleanArray[9][9] = true;
    		while(booleanArray[n][m] || (n == mouseDownGridX && m == mouseDownGridY)){ 
    			//This is to avoid setting a random bomb on top of another random bomb or on the clicked grid.
    			n = r.nextInt(TOTAL_COLUMNS);
    			m = r.nextInt(TOTAL_ROWS);
    		}
    		
    		booleanArray[n][m] = true;
    	}
    }
    
    public void searchBombs(){
    	
    for(int col=0; col < TOTAL_COLUMNS ; col++){
    	for(int row=0; row < TOTAL_ROWS ; row++){
    		if(booleanArray[col][row]){
    			//if there is a bomb in the mine location then add counter to the nearby mines
		
    			if(col == 0 && row == 0){
    				//bomb is in top left border      			     		
        			counterArray[col+1][row] += 1;
        			counterArray[col][row+1] += 1;
        			counterArray[col+1][row+1] += 1;
    			} if((col == 9) && (row == 0)){
    				//bomb is in top right
    				counterArray[col-1][row] 	+= 1;
    				counterArray[col-1][row+1]	+= 1;
    				counterArray[col][row+1] 	+= 1;
    				
    			} if((col == 9  )&& (row == 9)){
    				//bomb is in bottom right
    				counterArray[col][row-1] 	+= 1;
    				counterArray[col-1][row-1]	+= 1;
    				counterArray[col-1][row] 	+= 1;
    				
    			} if((col == 0 )&& (row == 9 ) ){
    				//bomb is in bottom left
    				counterArray[col+1][row] += 1;
        			counterArray[col][row-1] += 1;
        			counterArray[col+1][row-1] += 1;
    			} if(col == 0 && row > 1 && row < 9){
					//bomb is in left border
					counterArray[col][row-1] += 1;
					counterArray[col+1][row-1] += 1;
					counterArray[col+1][row] += 1;
					counterArray[col+1][row+1] += 1;
					counterArray[col][row+1] += 1;
					
				} if(row == 0 && col > 1 && col < 9){
					//bomb is in top border 
					counterArray[col-1][row] += 1;
					counterArray[col-1][row+1] += 1;
					counterArray[col][row+1] += 1;
					counterArray[col+1][row+1] += 1;
					counterArray[col+1][row] += 1;
				} if(col == 9 && row > 1 && row < 9){
					//bomb is in right border
					counterArray[col][row-1] += 1;
					counterArray[col-1][row-1] += 1;
					counterArray[col-1][row] += 1;
					counterArray[col-1][row+1] += 1;
					counterArray[col][row+1] += 1;
				} if(row == 9 && col > 1 && col < 9){
					//bomb is in bottom border
    				counterArray[col-1][row] += 1;
    				counterArray[col-1][row-1] += 1;
    				counterArray[col][row-1] += 1;
    				counterArray[col+1][row-1] += 1;
    				counterArray[col+1][row] += 1;
				} if(col>0 && col < 9 && row >0 && row < 9){
    				//bomb is not in any border
    				counterArray[col-1][row-1] 	+= 1;
        			counterArray[col][row-1]	+= 1;
        			counterArray[col+1][row-1]	+= 1;
        		
        			counterArray[col-1][row] += 1;
        			counterArray[col+1][row] += 1;
        			
        			counterArray[col-1][row+1] += 1;
        			counterArray[col][row+1] += 1;
        			counterArray[col+1][row+1] += 1;
        			
    			}	
    		}
    	}
    }   
   
    }
    
    public void clearBlocks(int col, int row){
    Color uncovered = Color.LIGHT_GRAY;
    
    int startX = col;
    int startY = row;

    
        
    
  }
    
    
}