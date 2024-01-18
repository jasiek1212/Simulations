package Project.Model.Util;

import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Maps.WorldMap;
import java.util.HashSet;
import java.util.Set;

public class GeneralStatistics {
    private final WorldMap map;
    private final Set<Animal> deadList;
    public GeneralStatistics(WorldMap map){
        this.map = map;
        this.deadList = new HashSet<>();
    }

    //Statistics
    public int aliveAnimalsCount(){return map.getAnimals().size();}
    public int grassesNumber(){
        return map.getGrassCount();}
    public double averageEnergy(){
        if(map.getAnimals().isEmpty()){
            return 0;
        }
        double sum = 0;
        for(Animal animal : map.getAnimals()){
            sum += animal.getEnergy();
        }
        return sum/map.getAnimals().size();
    }
    public double averageAgeOfDeath(){
        if(this.deadList.isEmpty()){
            return 0;
        }
        double sum = 0;
        for(Animal animal : deadList){
            sum += animal.getStats().whenDied()-animal.getStats().whenBorn();
        }
        return sum/deadList.size();
    }
    public double averageChildrenCount(){
        if(map.getAnimals().isEmpty()){
            return 0;}
        double sum = 0;
        for(Animal animal : map.getAnimals()){
            sum += animal.getStats().childrenCount();
        }
        return sum/map.getAnimals().size();
    }
    public int emptySpaces(){
        int num = (map.getHeight()+1)*(map.getWidth()+1)-this.aliveAnimalsCount()-this.grassesNumber();
        for(Animal animal : map.getAnimals()){
            if(map.getMapState().getPlants().containsKey(animal.getPosition())){
                num += 1;
            }
        }
        return num;
    }

    //Helper
    public void registerDeath(Animal animal){
        animal.die();
        this.deadList.add(animal);
    }
}
