package Project.Model.Core;

import java.util.Random;

public class Genome {

    private final byte[] genes;

    private int currentGeneIndex;

    public Genome(byte[] genes){
        this.genes = genes;
    }

//    [1,6,5,2,6,1,3,2] 50 [4,2,5,1,5,1,6,3] 150
//    ->prawa
//    [1,6,5,1,5,1,6,3]
//    -> mutacja
//    [1,6,5,2,5,1,6,3]
    public Genome(int genomeLength){ //konstruktor dla zwierzęcia ktore jest stawiane pierwszego dnia
        this.genes = generateRandomGenes(genomeLength);
        this.currentGeneIndex = new Random().nextInt(this.size());
    }

    public static Genome breed(Genome genome1, int energy1, Genome genome2, int energy2){
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
        childGenes = Genome.mutate(childGenes);
        return new Genome(childGenes);
    }

    private static byte[] mutate(byte[] genes){
        int geneToMutate = new Random().nextInt(genes.length);
        byte swapToDifferentGene = (byte) new Random().nextInt(7);
        genes[geneToMutate] = (byte) ((genes[geneToMutate]+swapToDifferentGene)%7);
        return genes;
    }

    public void nextGene(){
        currentGeneIndex = (currentGeneIndex+1)%this.size();
    }

    public int getCurrentGeneIndex(){
        return currentGeneIndex;
    }

    public void prevGene(){
        currentGeneIndex = (currentGeneIndex-1)%this.size();
    }

    public int getGene(){
        return this.genes[currentGeneIndex];
    }

    public byte[] getGenes(){
        return this.genes;
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
