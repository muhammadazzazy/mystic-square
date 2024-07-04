import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class Puzzle
{
    public static final int SIZE = 4;
    private int[][] board = new int[SIZE][SIZE];
    
    public Puzzle()
    {
        ArrayList<Integer> nums = new ArrayList<>();
        for(int i = 0; i < SIZE*SIZE; i++)
        {
            nums.add(i);
        }

        Collections.shuffle(nums);

        int idx = 0;

        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                board[i][j] = nums.get(idx++);
            }
        }
    }
    
    public void updateBoardData(String move)
    {
        int row = move.charAt(0) - 'a';
        int col = move.charAt(1) - '0' - 1;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(board[i][j] == 0)
                {
                    int temp = board[row][col];
                    board[row][col] = 0;
                    board[i][j] = temp;
                }
            }
        }
    }
    
    public int[][] queryBoardData()
    {
        return board;
    }
    
    public boolean validateMove(String move)
    {
        int row = move.charAt(0) - 'a';
        int col = move.charAt(1) - '0' - 1;
        if(row >= 0 && col >= 0 && row < SIZE && col < SIZE)
        {
            if(board[row][col] != 0)
            {
                if(row == 0 && col == 0)
                {
                    return board[row][col+1] == 0 || board[row+1][col] == 0;
                }
                else if(row == 0 && col == SIZE-1)
                {
                    return board[row][col-1] == 0 || board[row+1][col] == 0;
                }
                else if(row == SIZE-1 && col == 0)
                {
                    return board[row][col+1] == 0 || board[row-1][col] == 0;
                }
                else if(row == SIZE-1 && col == SIZE-1)
                {
                    return board[row][col-1] == 0 || board[row-1][col] == 0;
                }
                else if(row == 0)
                {
                    return board[row][col-1] == 0 || board[row+1][col] == 0 || board[row][col+1] == 0;
                }
                else if(row < SIZE-1 && col == 0)
                {
                    return board[row][col+1] == 0 || board[row-1][col] == 0 || board[row+1][col] == 0;
                }
                else if(row == SIZE-1)
                {
                    return board[row][col-1] == 0 || board[row-1][col] == 0 || board[row][col+1] == 0;
                }
                else if(col == SIZE-1)
                {
                    return board[row][col-1] == 0 || board[row-1][col] == 0 || board[row+1][col] == 0;
                }
                else
                {
                    return board[row][col-1] == 0 || board[row][col+1] == 0 || board[row-1][col] == 0 || board[row+1][col] == 0;
                }
            }
        }
        return false;
    }

    public boolean checkSorted()
    {
        int count = 1;
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(!(i == SIZE-1 && j == SIZE-1) && board[i][j] != count)
                {
                    return false;
                }
                count++;
            }
        }
        return true;
    }
}

class View
{
    public String readUserInput()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Your move: ");
        String userInput = scanner.nextLine();
        return userInput;
    }

    public void printBoard(int[][] board)
    {
        int ascii = 97;
        for(int i = 0; i < Puzzle.SIZE; i++)
        {
            if(i == 0)
            {
                System.out.print("\t");
            }
            System.out.print((i+1) + "\t");
        }
        System.out.println();
        for(int i = 0; i < Puzzle.SIZE; i++)
        {
            System.out.print((char)ascii + "\t");
            for(int j = 0; j < Puzzle.SIZE; j++)
            {
                if(board[i][j] != 0)
                {
                    System.out.print(board[i][j] + "\t");
                }
                else
                {
                    System.out.print(" \t");
                }
            }
            ascii++;
            System.out.println();
        }
    }
}

class Ctrl
{
    private static Puzzle puzzle = new Puzzle();
    private static View view = new View();

    public void play()
    {
        int [][] board = puzzle.queryBoardData();
        int moves = 0;
        char choice = 'Y';
        do
        {
            if(puzzle.checkSorted())
            {
                System.out.println("Well done! You solved the puzzle in " + moves + " moves!");
                System.out.println("Play again (Y/N)?");
                Scanner scanner = new Scanner(System.in);
                choice = scanner.next().charAt(0);
                puzzle = new Puzzle();
                board = puzzle.queryBoardData();
            }
            view.printBoard(board);
            String userInput = view.readUserInput();
            if(puzzle.validateMove(userInput))
            {
                puzzle.updateBoardData(userInput);
                moves++;
            }
            else
            {
                System.out.println("Invalid move!");
            }
        } while(choice == 'Y');
    }
}

public class Game
{
    public static void main(String []args)
    {
        Ctrl ctrl = new Ctrl();
        ctrl.play();
    }
}
