package Project.Model.WorldElements;

import java.util.Arrays;
import java.util.Random;

public class Genome {

    private final byte[] genes;

    public Genome(byte[] genes){
        this.genes = genes;
    }

    public Genome(){
        this.genes = generateRandomGenes();
    }


    public byte[] generateRandomGenes(){
        byte[] genes = new byte[8];
        for(int i=0;i<8;i++){
            genes[i] = (byte) new Random().nextInt(7);
        }
        return genes;
    }
}
