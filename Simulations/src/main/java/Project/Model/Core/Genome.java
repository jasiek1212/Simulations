package Project.Model.Core;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Genome {

    private final byte[] genes;
    private int currentGeneIndex;

    public Genome(byte[] genes){
        this.genes = genes;
        this.currentGeneIndex = new Random().nextInt(this.size());
    }

    public Genome(int genomeLength){
        this.genes = generateRandomGenes(genomeLength);
        this.currentGeneIndex = new Random().nextInt(this.size());
    }

    public static Genome breed(Genome genome1, int energy1, Genome genome2, int energy2, SimulationConfig config){
        int numsOfFirst = energy1/(energy1+energy2)*genome1.size();
        boolean left = Math.random() <= 0.5;
        boolean firstMoreEnergetic = energy1 > energy2;
        byte[] childGenes = new byte[genome1.size()];
        if((left && firstMoreEnergetic) || (!left && !firstMoreEnergetic)) {
            for (int i = 0; i < numsOfFirst; i++) {
                childGenes[i] = genome1.getGenes()[i];
            }
            for (int i = numsOfFirst; i < genome1.size(); i++) {
                childGenes[i] = genome2.getGenes()[i];
            }
        }
        else{
            for(int i=0;i<numsOfFirst;i++){
                childGenes[i] = genome1.getGenes()[i+genome1.size()-numsOfFirst];
            }
            for(int i=0;i<genome2.size()-numsOfFirst;i++){
                childGenes[i+numsOfFirst] = genome2.getGenes()[i];
            }
        }
        childGenes = Genome.mutate(childGenes, config);
        return new Genome(childGenes);
    }

    private static byte[] mutate(byte[] genes, SimulationConfig config){
        int numberOfGenesToMutate = new Random().nextInt(config.getMutationsNo().difference());
        Set<Integer> alreadyMutated = new HashSet<>();
        for(int i=0;i<numberOfGenesToMutate;i++){
            int geneToMutate = new Random().nextInt(genes.length);
            while(alreadyMutated.contains(geneToMutate)){
                geneToMutate = new Random().nextInt(genes.length);
            }
            alreadyMutated.add(geneToMutate);
            byte swapToDifferentGene = (byte) new Random().nextInt(8);
            genes[geneToMutate] = (byte) ((genes[geneToMutate]+swapToDifferentGene)%8);

        }
        return genes;
    }

    //Change active genes
    public void nextGene(){
        currentGeneIndex = (currentGeneIndex+1)%this.size();
    }
    public void prevGene(){
        currentGeneIndex = (currentGeneIndex-1)%this.size();
    }


    //Getters
    public int getCurrentGeneIndex(){
        return currentGeneIndex;
    }
    public byte[] getGenes(){
        return this.genes;
    }



    public int getGene(){
        return this.genes[currentGeneIndex];
    }


    public int size(){
        return this.genes.length;
    }

    public static byte[] generateRandomGenes(int genomeLength){
        byte[] genes = new byte[genomeLength];
        for(int i=0;i<genomeLength;i++){
            genes[i] = (byte) new Random().nextInt(7);
        }
        return genes;
    }
}
