/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazegenerator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author jeffe The Class extends JPanel to be able to override the Paint
 * method
 */
public class MazeGenerator extends JPanel {

    // Constant variables
    static int scl = 40; // Scale - the size of the cell (Square)
    static int w = 600; // Width of the window
    static int h = 600; // Height of the window

    // Instance variables
    public int cols;
    public int rows;
    public Cell grid[][];
    public Cell current;
    public ArrayList<Cell> stack = new ArrayList<>();

    /**
     * Main program
     *
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        JFrame frame = new JFrame("Maze Generator");
        MazeGenerator game = new MazeGenerator();
        game.setPreferredSize(new Dimension(w, h));
        frame.add(game);
        frame.pack(); // Resize the frame to the size of its components
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Game Loop
        while (true) {
            game.update();
            game.repaint();
            Thread.sleep(50);
        }
    }

    // Constructor
    public MazeGenerator() {
        cols = w / scl; // Set the Columns
        rows = h / scl; // Set the Rows

        grid = new Cell[cols][rows];  // Create the Grid

        // Fill the grid with Cells
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new Cell(i, j, scl);
            }
        }

        // Add to every Cell its neighbors
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].addNeighbors(grid);
            }
        }

        Random r = new Random();
        current = grid[r.nextInt(cols)][r.nextInt(rows)]; // Randomly choose a cell
        current.visited = true; // Set the current Cell as visited
    }

    private void update() {
        // Return the unvisited neighbors
        ArrayList<Cell> neighbors = current.checkNeighbors();
        // Generate a random number
        Random r = new Random();
        // Check if there is neighbors availables
        if (neighbors.size() > 0) {
            // STEP 1 - Pick a random unvisited neighbor
            Cell neighbor = neighbors.get(r.nextInt(neighbors.size()));

            //STEP 2 - Put the current Cell in the Stack (Backtrack)
            stack.add(current);

            //STEP 3 - Check which neihgbor was chosen and remove the walls between
            if (current.i + 1 == neighbor.i) {
                neighbor.walls[3].up = false;
                current.walls[1].up = false;
            }
            if (current.j + 1 == neighbor.j) {
                neighbor.walls[0].up = false;
                current.walls[2].up = false;
            }
            if (current.i - 1 == neighbor.i) {
                neighbor.walls[1].up = false;
                current.walls[3].up = false;
            }
            if (current.j - 1 == neighbor.j) {
                neighbor.walls[2].up = false;
                current.walls[0].up = false;
            }

            // STEP 4 - Set the chosen neighbor as visited and set as current Cell   
            neighbor.visited = true;
            current = neighbor;

        } else {
            // If the stack is not empty
            if (!stack.isEmpty()) {
                // Return one step in the backtrack and set as the Current Cell
                current = stack.remove(stack.size() - 1);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        // Call the super.paint to Redraw everything
        super.paint(g);

        // Draw all the cells
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].draw(g);
            }
        }

        // Paint the Current cell to green
        g.setColor(Color.green);
        g.fillRect(current.x, current.y, scl, scl);
    }
}
