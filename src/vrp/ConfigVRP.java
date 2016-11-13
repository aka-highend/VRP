/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author fachrur_122
 */
public class ConfigVRP {
    public int GRAPH_DIMENSION, VEHICLE_CAPACITY, VEHICLE_NUMBER;
    public Node nodes[];
    
    ConfigVRP() {
        this.GRAPH_DIMENSION = 10;
        this.VEHICLE_CAPACITY = 5;
        this.VEHICLE_NUMBER = 3;
        this.nodes = new Node[GRAPH_DIMENSION];
        this.nodes[0] = new Node(0,0); 
        this.nodes[1] = new Node(1,1);
        this.nodes[2] = new Node(2,2);
        this.nodes[3] = new Node(4,0);
        this.nodes[4] = new Node(-1,1);
        this.nodes[5] = new Node(-3,1);
        this.nodes[6] = new Node(-1,-2);
        this.nodes[7] = new Node(-1,-1);
        this.nodes[8] = new Node(-1,-4);
        this.nodes[9] = new Node(1,-2);
        for(int i=0; i<this.GRAPH_DIMENSION;i++){
            this.nodes[i].setDemand(1);
        }
        System.out.println("Configuration created");
    }
    
    ConfigVRP(String file, int vehicles) throws FileNotFoundException, IOException { 
//        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedReader br = new BufferedReader(new java.io.FileReader(file));
        String line;
        String [] value;
        int i = 0;
        int x, y, d;
        boolean demand = false;
        
        try {
            VEHICLE_NUMBER = vehicles;
            while ((line = br.readLine()) != null) {
                if (line.contains("DIMENSION")) {
                    value = line.split(":");
                    GRAPH_DIMENSION = Integer.parseInt(value[1].trim());
                } else if (line.contains("CAPACITY")) {
                    value = line.split(":");
                    VEHICLE_CAPACITY = Integer.parseInt(value[1].trim());
                    break;
                }
            }
            this.nodes = new Node[GRAPH_DIMENSION];
            line = br.readLine(); // this line is to jump the "NODE_COORD_SECTION" line
            while ((line = br.readLine()) != null) {
                if (line.contains("DEPOT_SECTION"))
                    break;
                if (line.contains("DEMAND_SECTION")) {
                    demand = true;
                    i = 0;
                    line = br.readLine();
                } if (!demand) {
                    value = line.trim().split(" ");
                    x = Integer.parseInt(value[1]);
                    y = Integer.parseInt(value[2]);
                    nodes[i++] = new Node(x, y);
                } else {
                    value = line.trim().split(" ");
                    d = Integer.parseInt(value[1]);
                    nodes[i++].setDemand(d);
                }
            }
            System.out.println("Configuration created");
        } catch (IOException e) { System.err.println(e.toString()); }
        finally {
            try {
                    br.close();
            } catch (IOException ex) {
                System.err.println(ex.toString());
            }
        }
    }
    
    public void print() {
        System.out.println("GRAPH_DIMENSION: " + this.GRAPH_DIMENSION);
        System.out.println("VEHICLE_CAPACITY: " + this.VEHICLE_CAPACITY);
        System.out.println("VEHICLE_NUMBER: " + this.VEHICLE_NUMBER);
        for(int i=0; i<this.GRAPH_DIMENSION; i++){
            System.out.print("Node "+i+" ");
            this.nodes[i].print();
        }
    }
}
