/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;

/**
 *
 * @author fachrur_122
 */
public class Node {
    private int x, y, demand;
    
    Node() {
        this.x = x;
        this.y = y;
        this.demand = 0;
    }
    
    Node(int ix, int iy) {
        this.x = ix;
        this.y = iy;
        this.demand = 0;
    }
    
    public double Distance(Node n) { return Math.sqrt(Math.pow((n.getX() - this.getX()),2) + Math.pow((n.getY() - this.getY()),2)); }
    
    public void print() { System.out.println("(" + this.x + " , " + this.y + ") - Demand: " + this.demand); }

    private double getX() { return x; }
    
    public void setX(int x) { this.x = x; }

    private double getY() { return y; }
    
    public void setY(int y) { this.y = y; }
    
    public int getDemand() { return demand; }
    
    public void setDemand(int demand) { this.demand = demand; }
}
