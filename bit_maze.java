import java.util.*;
import java.io.*;

// Nicholai Benko
// 2/21/21
// uses depth first search to find a 
// path through a grid of 1's and 0'1
// where a '1' is  path and a '0' is a wall


class BitMaze{

    public static void main(String args[]){

        //read file and build 2d arraylist
        ArrayList<ArrayList<Character>> maze = scanFile("test.txt");
        // ArrayList<ArrayList<Character>> maze = scanFile("test_no_path.txt");
        // ArrayList<ArrayList<Character>> maze = scanFile("test_empty.txt");

        if(maze.size() == 0){
            System.out.println("the maze is empty!");
            return;
        }

        //print maze
        for (int r = 0; r < maze.size(); r++) {    
            for (int c = 0; c < maze.get(r).size(); c++) {
                System.out.print(maze.get(r).get(c) + " ");
            }
            System.out.println();
        }
        System.out.println();

        //finds a path through the maze
        findPath(maze);

    }

    /**
     * reads a file and turns it into maze represented by a 2d arraylist
     * @param fileName
     * @return the maze
     */
    public static ArrayList<ArrayList<Character>> scanFile(String fileName) {
        
        ArrayList<ArrayList<Character>> rows = new ArrayList<ArrayList<Character>>();
        try{
            File text = new File(fileName);
            Scanner sc = new Scanner(text);


            while(sc.hasNextLine()){
                ArrayList<Character> temp = new ArrayList<Character>();
                char[] curLine = sc.nextLine().toCharArray();
                for(char x : curLine){
                    if(x == '1' || x == '0'){
                        temp.add(x);
                    }
                }
                rows.add(temp);
            }
            sc.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return rows;
    }


    /**
     * uses depth first search to find a way through the maze
     * @param maze
     */
    public static void findPath(ArrayList<ArrayList<Character>> maze){

        int h = maze.size()-1;
        int l = maze.get(0).size()-1;

        // testing
        // System.out.println("rows: " + (h+1) + "  cols: " + (l+1));

        if (h == 0){
            System.out.println("maze is empty");
            return;
        }

        boolean[][] visited = new boolean[h+1][l+1];
        Stack<String> stack = new Stack<>();


        for(int i = 0; i<maze.get(0).size(); i++){
            if(maze.get(0).get(i) == '1'){
                stack.push("0"+","+(i));
            }
        }

        while (!stack.empty()) {

            //testing
            // System.out.println("-------------------------");
            // System.out.println("start: " + stack.toString());

            // get the coordinates of the current square
            String x = stack.peek();
            int row = Integer.parseInt(x.split(",")[0]);
            int col = Integer.parseInt(x.split(",")[1]);

            //check if at the bottom of the grid
            if(row == maze.size()-1){
                System.out.println("found a path!");
                System.out.println("----------");
                printPath(stack);
                return;
            }
            
            // mark current square as visited
            visited[row][col]=true;
            
            //for testing
            // System.out.println("row:" + row + " h:" + h);
            // System.out.println("current square: (" + row + "," + col + ")" );

            //checks each direction
            // condition to add to stack: in bounds, char is a '1', has not been visited before
            if(col >= 1 && maze.get(row).get(col-1) == '1' && !visited[row][col-1]){
                stack.push(row + "," + (col-1)); //go left
            } else if(col < l && maze.get(row).get(col+1) == '1' && !visited[row][col+1]){
                stack.push(row + "," + (col+1)); //go right
            } else if(row >= 1 && maze.get(row-1).get(col) == '1' && !visited[row-1][col]){
                stack.push((row-1) + "," + col); //go up
            } else if(row < h && maze.get(row+1).get(col) == '1' && !visited[row+1][col]){
                stack.push((row+1) + "," + col); //go down
            } else{
                stack.pop();
            }

            //testing
            // System.out.println("end: " + stack.toString());
        } 
        System.out.println("there was no path!");
        return;
    }

    /**
     * prints the path all pretty like
     * @param stack
     */
    public static void printPath(Stack<String> stack){
        ArrayList<String> path = new ArrayList<String>();

        // loops through stack and adds to arraylist
        while(!stack.empty()){
            String step = stack.pop();
            int row = Integer.parseInt(step.split(",")[0]);
            int col = Integer.parseInt(step.split(",")[1]);
            path.add("("+row + "," + col + ")");
        }
        //loops through arraylist backwards to print the path in the right order
        for(int i = path.size()-1; i >= 0; i--){
            System.out.print(path.get(i) + " ");
        }
        System.out.println("----------");
        System.out.println("");



    }
    

}