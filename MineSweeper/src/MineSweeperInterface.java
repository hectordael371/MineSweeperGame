import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;
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
    public boolean gameLost = false;
    public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
    public boolean[][] bombArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    public boolean[][] flagArray = new boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    
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
        g.setColor(Color.BLUE);
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
        			g.setColor(Color.RED);
        			g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);               

        		}
        		else {
        			Color c = colorArray[x][y];        		
        			g.setColor(c);
        			g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
        			
        			if(flagArray[x][y] && !(colorArray[x][y].equals(Color.LIGHT_GRAY))){
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
			for(int n=0; n<TOTAL_COLUMNS; n++){
				for(int m=0; m<TOTAL_ROWS; m++){
					if(bombArray[n][m]){
						//Draw all the bombs.
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
    public int searchBombs(){
    int count = 0;
    if(mouseDownGridX==0 && mouseDownGridY ==0){
    	//Bottom Top corner
    	for(int n=mouseDownGridX; n<=mouseDownGridX+1; n++){
    		for(int m=mouseDownGridY; m<=mouseDownGridY+1;m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridX==0 && (mouseDownGridY>0 && mouseDownGridY<TOTAL_ROWS-1)){
    	//Left wall
    	for(int n=mouseDownGridX; n<=mouseDownGridX+1; n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY+1; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridX==0 && mouseDownGridY==TOTAL_ROWS-1){
    	//Lower Left corner
    	for(int n=mouseDownGridX; n<=mouseDownGridX+1; n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridY==TOTAL_ROWS-1 && (mouseDownGridX>0 && mouseDownGridX<TOTAL_COLUMNS-1)){
    	//Lower wall
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX+1;n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridX==TOTAL_COLUMNS -1 && mouseDownGridY==TOTAL_ROWS -1){
    	//Lower Right corner
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX; n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridX == TOTAL_COLUMNS -1 && (mouseDownGridY>0 && mouseDownGridY<TOTAL_ROWS-1)){
    	//Right wall
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX; n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY+1; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY)){
    				if(bombArray[n][m])
    					count++;
    			}
    		}
    	}
    }
    else if(mouseDownGridX == TOTAL_COLUMNS -1 && mouseDownGridY == 0){
    	//Upper Right corner
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX; n++){
    		for(int m=mouseDownGridY; m<=mouseDownGridY+1; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else if(mouseDownGridY==0 && (mouseDownGridX>0 && mouseDownGridX<TOTAL_COLUMNS-1)){
    	//Upper wall
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX+1; n++){
    		for(int m=mouseDownGridY; m<=mouseDownGridY+1; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    else{
    	//Anywhere that's not the border
    	for(int n=mouseDownGridX-1; n<=mouseDownGridX+1; n++){
    		for(int m=mouseDownGridY-1; m<=mouseDownGridY+1; m++){
    			if(!(n==mouseDownGridX && m==mouseDownGridY))
    				if(bombArray[n][m])
    					count++;
    		}
    	}
    }
    //Debugging purposes.
    System.out.println(count);
    return count;
    }
    
    public void clearBlocks(){
    Color uncovered = Color.LIGHT_GRAY;
    if(mouseDownGridX == 0 && mouseDownGridY == 0){
    	for(int i=mouseDownGridX; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    			}
    		}
    	}
    else if(mouseDownGridX == 0 && mouseDownGridY == TOTAL_ROWS-1){
    	for(int i=mouseDownGridX; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridX == TOTAL_COLUMNS-1 && mouseDownGridY == TOTAL_ROWS -1){
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridX == TOTAL_COLUMNS-1 && mouseDownGridY == 0){
    	for(int i=mouseDownGridX-1; i<= mouseDownGridX; i++){
    		for(int j=mouseDownGridY; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridX == 0 && (mouseDownGridY>0 && mouseDownGridY<TOTAL_ROWS-1)){
    	for(int i=mouseDownGridX; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridY == TOTAL_COLUMNS-1 && (mouseDownGridX>0 && mouseDownGridX<TOTAL_COLUMNS-1)){
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridX == TOTAL_COLUMNS-1 && (mouseDownGridY>0 && mouseDownGridY<TOTAL_ROWS-1)){
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else if(mouseDownGridY == 0 && (mouseDownGridX>0 && mouseDownGridX <TOTAL_COLUMNS-1)){
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    else{
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY+1; j++){
    			colorArray[i][j] = uncovered;
    		}
    	}
    }
    }
    public void clearAdjacentBlocks(){
    	for(int i=mouseDownGridX-1; i<=mouseDownGridX+1; i++){
    		for(int j=mouseDownGridY-1; j<=mouseDownGridY+1; j++){
    			if(!(bombArray[i][j])){
    				clearBlocks();
    			}
    		}
    	}
    }

}