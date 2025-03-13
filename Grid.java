/*Coded by Timothy Hobbes Glazeski*/
package minesweeper;
//Grid Class: Handles filling the grid with mines and displaying the grid

import java.util.Arrays;

public class Grid {
    //Array that holds characters to represent board
    private char[][] grid;
    //2D array that holds location of ten mines
    //First column is mine's row location. Second column is mine's column location.
    private int[][] mineLocations = new int[10][2];
    
    //Default Constructor
    public Grid(){     
        //Left empty
    }
    
    //Constructor with user-defined height and width values
    public Grid(int height, int width){
        grid = new char[height][width];
        
        //Fills grid with character '*' for visuals
        for(int row=0; row<grid.length; row++)
            for(int column=0; column<grid[row].length; column++)
                grid[row][column]='*';
        
        //Selects locations for mines
        randomFillGrid();
    }
    
    //Method that randomly places mines in the grid. Coordinates are stored in "mineLocations" array
    private void randomFillGrid(){
        for(int index=0; index < 10; index++){
            //Randomly select coordinates within grid  
            int row = (int)(Math.random()*grid.length);
            int col =(int)(Math.random()*grid[0].length);
            
            //Checks if coordinates selected are already taken 
            if(index!=0)
                for(int check=index;check>=1;check--){
                    //If a spot is already occupied, it selects new coordinates and checks again
                    if(mineLocations[check-1][0]==row && mineLocations[check-1][1]==col){
                        row = (int)(Math.random()*grid.length);
                        col =(int)(Math.random()*grid[0].length);
                        check=index+1;                        
                    }
                }            
                            
            //Sets the row location of mine
            mineLocations[index][0] = row;
            //Sets the column location of mine
            mineLocations[index][1] = col;
        }        
    }// randomFillGrid() ends
    
    //Retrieves locations of mines
    public int[][] getMineLocations(){
        return mineLocations;
    }
    
    //Method that displays grid with hidden and revealed cells    
    public void displayGrid(){
        //Informs player about number of mines
        System.out.println("There are 10 mines in the mine field.");
        
        //Heading of board that shows ID number for each column
        System.out.print("\u2588 ");
        for(int top=1;top<=grid[0].length;top++)
            System.out.print(top+" ");
        System.out.println();
        
        //Rows and column characters of board with each row headed by a ID number
        for(int row=0;row<grid.length;row++){
            //ID heading
            if(row<9)
                System.out.print((row+1)+"  ");
            else
                System.out.print((row+1)+" ");
            //Characters in grid array
            for(int column=0;column<grid[row].length;column++)
                System.out.print(grid[row][column]+" ");
            System.out.println();
        }
    }// displayGrid() ends
    
    //Method that displays grid with mine location cells revealed
    public void displayGridData(){
        //Informs player about number of mines
        System.out.println("There are 10 mines in the mine field.");
        
        //Heading of board that shows ID number for each column
        System.out.print("\u2588 ");
        for(int top = 1; top <= grid[0].length; top++)
            System.out.print(top+" ");
        System.out.println();
        
        //Rows and column characters of board with each row headed by a ID number
        for(int row=0;row<grid.length;row++){
            //Row ID heading
            if(row<9)
                System.out.print((row+1)+"  ");
            else
                System.out.print((row+1)+" ");
            
            //Characters in grid array
            for(int column = 0; column < grid[row].length; column++){
                char symbol='.';
                
                //Checks if current printing location is a mine. 
                //If so, sets symbol to be printed as '*'.
                //Otherwise if not a mine, symbol will be printed as '.'. 
                for(int index = 0; index < 10; index++)
                    if(row == mineLocations[index][0] 
                    && column == mineLocations[index][1]){
                        symbol='*';
                        break;
                    }                    
                System.out.print(symbol+" ");
            }
            System.out.println();
        }
    }// displayGridData() ends
    
    //Gets the selected grid cell symbol
    public char getGridCellCharacter(int row, int col){
        return grid[row][col];
    }
    
    //Set the selected grid cell to whatever symbol
    public void setGridCell(int row, int col, char symbol){
        grid[row][col]=symbol;
    }
}
