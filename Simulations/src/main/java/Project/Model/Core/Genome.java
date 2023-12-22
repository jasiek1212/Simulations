package Project.Model.Core;

import java.util.Random;

public class Genome {

    private final byte[] genes;

    private int currentGeneIndex;

    public Genome(byte[] genes, int genomeLength){
        this.genes = genes;
        this.currentGeneIndex = new Random().nextInt(this.size());
    }

    public Genome(int genomeLength){ //konstruktor dla zwierzęcia ktore jest stawiane pierwszego dnia
        this(generateRandomGenes(genomeLength), genomeLength);
    }

    public void nextGene(){
        currentGeneIndex = (currentGeneIndex+1)%this.size();
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
