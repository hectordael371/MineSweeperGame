import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;
import java.util.Random;

import javax.swing.JOptionPane;
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
    public boolean gameLost = false;
    public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
    public boolean[][] bombArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    public boolean[][] flagArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    public int[][] numberArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
    
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
                flagArray[x][y] = false;
            }
        }
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
        		if(gameLost && bombArray[x][y]){
        			//Game ends.
        			g.setColor(Color.RED);
        			g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);               

        		}
        		else {
        			//Paint the cells.
        			Color c = colorArray[x][y];        		
        			g.setColor(c);
        			g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
        			
        			if(colorArray[x][y].equals(Color.LIGHT_GRAY) && numberArray[x][y] != 0){
        				//If cell is uncovered and there is a bomb surrounding it.
        				String numbers = String.valueOf(numberArray[x][y]); 
        				g.setColor(Color.BLACK);
        				g.drawString(numbers, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 11, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 18);
        			}	
        			if(flagArray[x][y] && !(colorArray[x][y].equals(Color.LIGHT_GRAY))){
        				//User flags the cell.
        				
        				//Flag's pole
        				g.setColor(Color.WHITE);
        				g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 7, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 7, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 7, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 23);
        				g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 8, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 7, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 8, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 23);
        				//Flag
        				Polygon flag = new Polygon();
        				flag.addPoint(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 9, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 7);
        				flag.addPoint(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 23, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 11);
        				flag.addPoint(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 9, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 15);
        				g.setColor(Color.RED);
        				g.fillPolygon(flag);}
        			}
        		}        
        if(gameLost){
        	//Draw all the bombs if the player loses.
        	
			for(int n=0; n<TOTAL_COLUMNS; n++){
				for(int m=0; m<TOTAL_ROWS; m++){
					if(bombArray[n][m]){
				    	Color c = Color.BLACK;
				        g.setColor(c);
				       //Draw body
				        g.fillOval(x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 8, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 8, (INNER_CELL_SIZE+1)/2, (INNER_CELL_SIZE+1)/2);
				       //Draw diagonal lines
				        g.drawLine(x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 8, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 8, x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 23, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 23);
				        g.drawLine(x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 8, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 23, x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 23, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 8);
				       //Draw vertical line.
				        g.drawLine(x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 16, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 4, x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 16, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 27);
				       //Draw horizontal line. 
				        g.drawLine(x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 4, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 15, x1 + GRID_X + (n * (INNER_CELL_SIZE + 1)) + 27, y1 + GRID_Y + (m * (INNER_CELL_SIZE + 1)) + 15);
					}
				}
			}
			
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
    public void placeBombs(){
    	Random r = new Random();
    	int numBombs = 10;
    	
    	//Set all bombs to false
		for(int n=0; n<TOTAL_COLUMNS; n++){
			for(int m=0; m<TOTAL_ROWS; m++){
                bombArray[n][m] = false;
			}
		}  	
    	for(int i=0; i<numBombs; i++){
    		
    		int n = r.nextInt(TOTAL_COLUMNS);
    		int m = r.nextInt(TOTAL_ROWS);
    		
    		while(bombArray[n][m] || (n == mouseDownGridX && m == mouseDownGridY)){ 
    			//This is to avoid setting a random bomb on top of another random bomb or on the clicked grid.
    			n = r.nextInt(TOTAL_COLUMNS);
    			m = r.nextInt(TOTAL_ROWS);
    		}
    		
    		bombArray[n][m] = true;
    	}
    }
    public int searchBombs(int xPos, int yPos){
    int count = 0;
    if(xPos==0 && yPos ==0){
    	//Bottom Top corner
    	for(int n=xPos; n<=xPos+1; n++){
    		for(int m=yPos; m<=yPos+1;m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(xPos==0 && (yPos>0 && yPos<TOTAL_ROWS-1)){
    	//Left wall
    	for(int n=xPos; n<=xPos+1; n++){
    		for(int m=yPos-1; m<=yPos+1; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(xPos==0 && yPos==TOTAL_ROWS-1){
    	//Lower Left corner
    	for(int n=xPos; n<=xPos+1; n++){
    		for(int m=yPos-1; m<=yPos; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(yPos==TOTAL_ROWS-1 && (xPos>0 && xPos<TOTAL_COLUMNS-1)){
    	//Lower wall
    	for(int n=xPos-1; n<=xPos+1;n++){
    		for(int m=yPos-1; m<=yPos; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(xPos==TOTAL_COLUMNS -1 && yPos==TOTAL_ROWS -1){
    	//Lower Right corner
    	for(int n=xPos-1; n<=xPos; n++){
    		for(int m=yPos-1; m<=yPos; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(xPos == TOTAL_COLUMNS -1 && (yPos>0 && yPos<TOTAL_ROWS-1)){
    	//Right wall
    	for(int n=xPos-1; n<=xPos; n++){
    		for(int m=yPos-1; m<=yPos+1; m++){
    			if(!(n==xPos && m==yPos)){
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    			}
    		}
    	}
    }
    else if(xPos == TOTAL_COLUMNS -1 && yPos == 0){
    	//Upper Right corner
    	for(int n=xPos-1; n<=xPos; n++){
    		for(int m=yPos; m<=yPos+1; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(yPos==0 && (xPos>0 && xPos<TOTAL_COLUMNS-1)){
    	//Upper wall
    	for(int n=xPos-1; n<=xPos+1; n++){
    		for(int m=yPos; m<=yPos+1; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else{
    	//Anywhere that's not the border
    	for(int n=xPos-1; n<=xPos+1; n++){
    		for(int m=yPos-1; m<=yPos+1; m++){
    			if(!(n==xPos && m==yPos))
    				//Clicked grid is not evaluated.
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    return count;
    }
    
    public void clearBlocks(int xPos, int yPos){
    Color uncovered = Color.LIGHT_GRAY;
    
    
    if(xPos == 0 && yPos == 0){
    	//Top-left corner.
    	for(int i=xPos; i<=xPos+1; i++){
    		for(int j=yPos; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    			}
    		}
    	}
     if(xPos == 0 && yPos == TOTAL_ROWS-1){
    	//Bottom-left corner.
    	for(int i=xPos; i<=xPos+1; i++){
    		for(int j=yPos-1; j<=yPos; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    			}
    	}
    }
     if(xPos == TOTAL_COLUMNS-1 && yPos == TOTAL_ROWS -1){
    	//Bottom-right corner.
    	for(int i=xPos-1; i<=xPos; i++){
    		for(int j=yPos-1; j<=yPos; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
     if(xPos == TOTAL_COLUMNS-1 && yPos == 0){
    	//Top-right corner.
    	for(int i=xPos-1; i<= xPos; i++){
    		for(int j=yPos; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
    if(xPos == 0 && (yPos>0 && yPos<TOTAL_ROWS-1)){
    	//Left border.
    	for(int i=xPos; i<=xPos+1; i++){
    		for(int j=yPos-1; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
    if(yPos == TOTAL_COLUMNS-1 && (xPos>0 && xPos<TOTAL_COLUMNS-1)){
    	//Bottom border.
    	for(int i=xPos-1; i<=xPos+1; i++){
    		for(int j=yPos-1; j<=yPos; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
    if(xPos == TOTAL_COLUMNS-1 && (yPos>0 && yPos<TOTAL_ROWS-1)){
    	//Right border.
    	for(int i=xPos-1; i<=xPos; i++){
    		for(int j=yPos-1; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
    if(yPos == 0 && (xPos>0 && xPos <TOTAL_COLUMNS-1)){
    	//Upper border.
    	for(int i=xPos-1; i<=xPos+1; i++){
    		for(int j=yPos; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    }
    if(xPos > 0 && xPos < 9 && yPos > 0 && yPos < 9){
    	//Rest of the grid.
    	for(int i=xPos-1; i<=xPos+1; i++){
    		for(int j=yPos-1; j<=yPos+1; j++){
    			if(numberArray[i][j] == 0){
    				colorArray[i][j] = uncovered;
    			}
    			numberArray[i][j] = searchBombs(i,j);
    		}
    	}
    	
    }
    
    
    if(numberArray[xPos][yPos] == 0 && xPos < 9){
    	xPos++;
		clearBlocks(xPos, yPos);
	}
    if(numberArray[xPos][yPos] == 0 && yPos < 9){
    	yPos++;
		clearBlocks(xPos, yPos);
	}
    repaint();
    }
}