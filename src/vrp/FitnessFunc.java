/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;

import java.util.LinkedList;
import org.jgap.*;
/**
 *
 * @author fachrur_122
 */
public class FitnessFunc extends FitnessFunction {
    private ConfigVRP conf;
    public FitnessFunc(ConfigVRP configuration) {
        System.out.println("Initialize fitness function");
        this.conf = configuration;
    }
    
    @Override
    public double evaluate(IChromosome chromosome) {
        double fitness = 0;
        for(int i = 0; i < this.conf.VEHICLE_NUMBER;i++) {
            fitness += this.getDistance(i, chromosome, this.conf)*10;
            fitness += this.getCapacity(i, this.conf.VEHICLE_CAPACITY, chromosome);
        }
                
	if (fitness < 0) {
		return 0;
	}
        fitness = 100000 - fitness;
	return Math.max(1.0d, fitness);
    }
    
    public double isPresent(int vehicleNumber, IChromosome chromosome) {
        LinkedList positions = getPositions(vehicleNumber, chromosome, this.conf);
        if(positions.size() > 0){
            return 0.0;
        }
        return 100.00;
    }
    
    public static double getDistance(int vehicleNumber, IChromosome chromosome, ConfigVRP conf) {
        double totalDistance    = 0.0;
        LinkedList positions    = getPositions(vehicleNumber, chromosome, conf);
        Node deposit           = conf.nodes[0];
        Node lastVisit       = deposit;
        
        while(!positions.isEmpty()) {
            int pos = ((Integer) positions.pop()).intValue();
            Node visit = conf.nodes[pos];
            totalDistance += lastVisit.Distance(visit);
            lastVisit = visit;
        }
        
        totalDistance += lastVisit.Distance(deposit);

        return totalDistance;
    }
    
    public double getCapacity(int vehicleNumber, int vehicleCapacity, IChromosome chromosome) {
        double demandTotal = 0.0;
        LinkedList positions = getPositions(vehicleNumber, chromosome, this.conf);
        while(!positions.isEmpty()){
            int pos = ((Integer) positions.pop()).intValue();
            Node visit  = this.conf.nodes[pos];
            demandTotal += visit.getDemand();
        }
        
        if(demandTotal > vehicleCapacity) {
            return (demandTotal - vehicleCapacity) * 10;
        }
        return (vehicleCapacity - demandTotal) * 2;
    }
    
    public static LinkedList getPositions(int vehicleNumber, IChromosome chromosome, ConfigVRP conf){
        LinkedList p = new LinkedList();
        for(int i=1; i < conf.GRAPH_DIMENSION; i++){
            int valueChromosome = ((Integer) chromosome.getGene(i).getAllele()).intValue();
            if(valueChromosome == vehicleNumber){
               p.add(i);
            }
        }
        return p;
    }
    
    public static double getNumberAtGene(IChromosome a_potentialSolution, int a_position) {
        Integer allocatedNum = ((Integer) a_potentialSolution.getGene(a_position).getAllele()).intValue();
        return allocatedNum.intValue();
    }
}
