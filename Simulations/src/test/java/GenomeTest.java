import Project.Model.Core.Genome;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {

    @Test
    void testInitializationWithLength() {
        Genome genome = new Genome(10);
        assertEquals(10, genome.size());
        assertNotNull(genome.getGenes());
    }
    @Test
    void testInitializationWithGeneArray() {
        byte[] genes = new byte[] {1, 2, 3, 4, 5};
        Genome genome = new Genome(genes);
        assertArrayEquals(genes, genome.getGenes());
    }
    @Test
    void testNextGene() {
        Genome genome = new Genome(10);
        int initialIndex = genome.getCurrentGeneIndex();
        genome.nextGene();
        assertEquals((initialIndex + 1) % 10, genome.getCurrentGeneIndex());
    }

    @Test
    void testPrevGene() {
        Genome genome = new Genome(10);
        genome.prevGene();
    }
    @Test
    void testGetGene() {
        byte[] genes = new byte[] {1, 2, 3, 4, 5};
        Genome genome = new Genome(genes);
        assertEquals(genes[genome.getCurrentGeneIndex()], genome.getGene());
    }

}
