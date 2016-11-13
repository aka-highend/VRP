/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrp;

import java.util.LinkedList;
import java.util.Scanner;
import org.jgap.*;
import org.jgap.impl.*;

/**
 *
 * @author fachrur_122
 */
public class VRP {
    
    private static final int MAX_EVO = 5000;
    
    public static void VRP() throws Exception {
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
       
        System.out.println("------VRP-----");
        System.out.println("Choose file with the VRP: ");
        System.out.println("0) test [10 destination]");
        System.out.println("1) 45 destination");
        System.out.println("2) 60 destination");
        System.out.println("");
        int selection, van;
        Scanner scanIn = new Scanner(System.in);
        ConfigVRP conf_vrp = null;
        selection = scanIn.nextInt();
        System.out.println("Place the numbers of van : ");
        van = scanIn.nextInt();
//        if (selection == 0) {
//            conf_vrp = new ConfigVRP();
//        } else if (selection == 1) {
//            conf_vrp = new ConfigVRP("nbproject/Result/45 Destination.txt", van);
//        } else if (selection == 2) {
//            conf_vrp = new ConfigVRP("nbproject/Result/60 Destination.txt", van);
//        } else {
//            conf_vrp = new ConfigVRP();
//        }
        if (selection == 0 || selection == 1 || selection == 2) {
            conf_vrp = new ConfigVRP();
        } else { System.out.println("There is no such option."); }
        conf_vrp.print();
        FitnessFunc myFunc = new FitnessFunc(conf_vrp);

        conf.setFitnessFunction(myFunc);
        Gene[] sampleGenes = new Gene[conf_vrp.GRAPH_DIMENSION];
        for(int i=0; i < conf_vrp.GRAPH_DIMENSION; i++){
            sampleGenes[i] = new IntegerGene(conf, 0, (conf_vrp.VEHICLE_NUMBER-1));
        }
        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(60);

        Genotype population;
        population = Genotype.randomInitialGenotype(conf);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < MAX_EVO; i++) {
        	if(i%50 == 0)
        		System.out.print(".");
        	if(i%5000 == 0)
        		System.out.println("");
        	if (!uniqueChromosomes(population.getPopulation())) {
        		throw new RuntimeException("Invalid generation in the evolution " + i);
        	}
        	population.evolve();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("");
        System.out.println("Evolution's total time: " + ( endTime - startTime) + " ms");
        System.out.println("Total number of the evolution " + MAX_EVO);

        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        double v1 = bestSolutionSoFar.getFitnessValue();
        System.out.println("The best fitness solution: " + v1);
        bestSolutionSoFar.setFitnessValueDirectly(-1);
        System.out.println("Result: ");
        for (int i = 0; i < conf_vrp.GRAPH_DIMENSION; i++) {
           System.out.println(i +". " + FitnessFunc.getNumberAtGene(bestSolutionSoFar, i) );  
        }
        Double  distance = 0.0;
        Double  r_distance= 0.0;
        LinkedList routes;
        for(int i = 0; i < conf_vrp.VEHICLE_NUMBER; i++){
            r_distance = FitnessFunc.getDistance(i, bestSolutionSoFar, conf_vrp);
            routes = FitnessFunc.getPositions(i, bestSolutionSoFar, conf_vrp);
            System.out.print("Route #" + i + " :");
            while(!routes.isEmpty()){
                int pos = ((Integer) routes.pop()).intValue();
                System.out.print(pos + ", ");
            }
            System.out.println();
            System.out.println("\t Distance of the route: "+r_distance);
            distance += r_distance;
        }
        System.out.println("The best distance: " + distance);
        System.out.println();
    }
    
    public static boolean uniqueChromosomes(Population a_pop) {
        for(int i=0;i<a_pop.size()-1;i++) {
          IChromosome c = a_pop.getChromosome(i);
          for(int j=i+1;j<a_pop.size();j++) {
            IChromosome c2 =a_pop.getChromosome(j);
            if (c == c2) {
              return false;
            }
          }
        }
        return true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        VRP();
    }
    
}
