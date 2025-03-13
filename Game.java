/*Coded by Timothy Hobbes Glazeski*/
package minesweeper;
import java.util.Scanner;
//Game Class: This class handles the game loop logic
public class Game {    
    //Game Variables 
    private Grid gridGame;
    private Scanner input;
    //Kept to validate user inputs for cell location
    private int height;
    private int width;
    //Handles if user is still able to play. True, keep playing. False, user selected a mine
    private boolean alive;
    
    //Game Object Constructor
    public Game(){
        input=new Scanner(System.in);
    }
    
    //Method that prompts user to enter height and width for game grid
    public void start(){
        //Greets user
        System.out.println("Welcome to Minesweeper!");
        
        //Prompt for height
        System.out.println("Enter an integer grid height between 3 and 101:");
        height = getValidInt(4,100);
        
        //Prompt for width
        System.out.println("Enter an integer grid width between 3 and 101:");
        width = getValidInt(4,100);
        
        //If user values are valid, a grid is created
        gridGame = new Grid(height,width);
        
        //Starts the Minesweeper game logic loop
        System.out.println("Let's begin!");
        alive=true;
        gameLoop();
    }// start() ends
    
    //Method that handles the game logic loop
    private void gameLoop(){
        //If player hasn't selected a mine, player is "alive" & game continues
        while(alive){
        //Displays grid
            gridGame.displayGrid();
            
        //Checks if user won. If so, displays congratulation message and stops game
            if(checkVictory() && alive){
                System.out.println("YOU WIN!!!");
                alive=false;
            }
            else{
        //Prompts user to enter a row and column value to check
                System.out.println("Select a spot to check for a mine."
                +"\nFirst, enter the row number. Then, enter the column number");

                int row = getValidInt(1,height)-1;
                int col = getValidInt(1,width)-1;
            
        //If "checkLocation()" returns true, user loses and is shown where mines were.
        //Otherwise the algorithmn of clearing spaces begins
                if(checkLocation(row, col)){
                    alive=false;
                    System.out.println("GAMEOVER!!!");
                    gridGame.displayGridData();
                }
            }
        }
    }// gameLoop() ends
    
    //Method that checks cell given by user 
    private boolean checkLocation(int row, int col){
        //Checks if cell is a mine. If true, checkLocation returns true and user loses
        for(int index=0; index<10; index++)
         //1st mine column is x value, 2nd mine column is y value
            if(row == gridGame.getMineLocations()[index][0]
            && col == gridGame.getMineLocations()[index][1])
                return true;
        //If cell wasn't a mine, the cell is cleared
        clearBlanks(row,col);        
        return false;        
    }// checkLocation(int row, int col) ends    
    
    //Method that reveals cell adjacent to cell given by user
    private void clearBlanks(int row, int col){
        //When this method is called via recursion, 
        //this if statement vertifies if new location is in bounds of grid and not cleared
        if((row >= 0 && row < height) && (col >= 0 && col < width) 
            && gridGame.getGridCellCharacter(row, col)=='*'){
            
        //Counts number of bombs adjacent to cell. 
        //If zero, cell becomes empty
            byte amount = (byte) numberOfMines(row,col);
            if(amount==0){
                gridGame.setGridCell(row, col, ' ');
            //Left and right side cells
                clearBlanks(row,col+1);
                clearBlanks(row,col-1);
            //Top side cells
                clearBlanks(row+1,col);
                clearBlanks(row+1,col+1);
                clearBlanks(row+1,col-1);
            //Bottom side cells
                clearBlanks(row-1,col);
                clearBlanks(row-1,col+1);
                clearBlanks(row-1,col-1);
            }
        //Else fills spot with number of adjacent mines
            else{
                System.out.println("well that just happp");
                gridGame.setGridCell(row, col, (char)(amount+'0'));
            }
        }
    }// clearBlanks(int row, int col) ends
    
    //Method that counts number of mines adjacent to a cell 
    private int numberOfMines(int row, int col){
        //Counts number of mines
        int counterMine=0;
        //For loop that checks and counts bombs around a selected cell
        //Starts with row just above, ends with row just below 
        //while checking each column, starting just to the left,
        //ending with just to the right 
        for(int rowCheck = -1; rowCheck <= 1; rowCheck++){
            //If the check value is out of bounds, it is skipped
            if(row + rowCheck < 0)
                continue;
            else if(row + rowCheck == height)
                continue;
            
            for(int colCheck = -1; colCheck <= 1; colCheck++){
                //If the check value is out of bounds, it is skipped
                if(col + colCheck < 0)
                    continue;
                else if(col + colCheck == width)
                    continue;
                
                //counterMine increments by one if checked spot has a mine
                for(int index=0; index < 10; index++){
                    if(gridGame.getMineLocations()[index][0] == (row + rowCheck)
                    && gridGame.getMineLocations()[index][1] == (col + colCheck))
                        counterMine++;
                }            
            }
        }
        return counterMine;
    }// numberOfMines(int row, int col) ends
    
    //Method that checks if player has discerned mine locations
    private boolean checkVictory(){
        boolean clear=true;
        //Goes through grid to check if each cell except mines has been cleared
        row: for(int row=0; row < height; row++)
        column: for(int column=0; column < width; column++){
            //If current selected cell is a mine, it goes to the next column
            bomb: for(int index = 0; index < 10; index++)
                    if(row == gridGame.getMineLocations()[index][0] 
                    && column == gridGame.getMineLocations()[index][1]){
                        continue column;
                    }
            //If current selected cell isn't clear,
            //checking loop breaks and game continues 
                if(gridGame.getGridCellCharacter(row, column)=='*'){
                    clear=false;
                    break row;
                }
            }
        return clear;
    }// checkVictory() ends
           
    //Method that validates if user input is an integer value within a range
    private int getValidInt(int min, int max){
        
        int userValue=0;
        boolean isValid = false;
        
        while(!isValid){
            if(input.hasNextInt()){            
                userValue = input.nextInt();
            
                if(userValue<min) 
                    System.out.println("Entered value is < "+min+". Please enter a value >= "+min+".");  
                else if(userValue>max)
                    System.out.println("Entered value is > "+max+". Please enter a value <= "+max+".");
                else
                    isValid=true;            
            }          
            else
                System.out.println("Entered value isn't an integer. Please enter an integer.");
            input.nextLine();
        }        
        return userValue;
    }// getValidInt(int min, int max) ends
    
}
