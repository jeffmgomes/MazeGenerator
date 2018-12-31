/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazegenerator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author jeffe
 */
public class Cell {

    // Instance Variables    
    boolean visited = false;
    int x, y, w;
    int i;
    int j;
    Wall walls[];
    ArrayList<Cell> neighbors = new ArrayList<>();
    int totalNeighbors = 0;

    // Constructor
    public Cell(int i, int j, int w) {
        this.i = i;
        this.j = j;
        this.x = i * w;
        this.y = j * w;
        this.w = w;

        this.walls = new Wall[4]; // Set the array of walls
        
        // Create 4 walls
        for(int a = 0; a < walls.length; a++){
            walls[a] = new Wall();
        }
    }

    // Draw the wall and rectangle if visited
    public void draw(Graphics g) {
        if (visited) {
            g.setColor(Color.blue);
            g.fillRect(x, y, w, w);
        }

        //TOP
        if (walls[0].up) {
            walls[0].draw(g, this.x, this.y, this.x + w, this.y);
        }
        //RIGHT
        if (walls[1].up) {
            walls[1].draw(g, this.x + w, this.y, this.x + w, this.y + w);
        }
        //BOTTOM
        if (walls[2].up) {
            walls[2].draw(g, this.x + w, this.y + w, this.x, this.y + w);
        }
        //LEFT
        if (walls[3].up) {
            walls[3].draw(g, this.x, this.y + w, this.x, this.y);
        }
    }

    // Check if there is any neighbor unvisited and return a List with the availables
    public ArrayList<Cell> checkNeighbors() {
        ArrayList<Cell> n;
        n = new ArrayList<>();
        for (Cell c : neighbors) {
            if (!c.visited) {
                n.add(c); // Add the unvisited neighbor to the list
            }
        }
        return n; // return the list
    }

    // Add all the neighbors of the current cell
    public void addNeighbors(Cell[][] grid) {
        if (j > 0) {
            // TOP
            this.neighbors.add(grid[i][j - 1]);
        }
        if (i < grid.length - 1) {
            // RIGHT
            this.neighbors.add(grid[i + 1][j]);
        }
        if (j < grid[0].length - 1) {
            // BOTTOM
            this.neighbors.add(grid[i][j + 1]);
        }
        if (i > 0) {
            // LEFT
            this.neighbors.add(grid[i - 1][j]);
        }
    }

    // Class Wall
    public class Wall {

        boolean up = true; // If the wall is up or no
        
        // Method to draw the wall
        public void draw(Graphics g, int x1, int y1, int x2, int y2) {
            g.setColor(Color.black);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
