# Simulations

This is a project completed throughout the Object Oriented Programming course at AGH UST Kraków. 

# Table of Contents
- [Project Functionality](#project-functionality)
- [Project Setup in IntelliJ](#project-setup-in-intellij)
- [Run the Program](#run-the-program)

## Project Functionality

The simulation begins once the user selects their preferred configuration. A simulation window will appear, displaying a grid-based map populated with two types of objects: food and animals. Depending on the chosen map variant, the grid may feature different backgrounds and images to visually distinguish between the two environments. The simulation can be paused and a specific animal can be clicked to see its personal statistics. Each animal operates on a different thread, reducing computation time.

### Animal Behavior

All animals in the simulation move autonomously according to their genotype—a predefined sequence of movements unique to each animal. Every movement expends energy, and animals can only regain energy by stepping on fields populated with food. If an animal runs out of energy and cannot make the next move, it will die.

In cases where two animals meet on the same field and have sufficient energy, they can reproduce. The offspring inherits a genotype derived from a combination of its parents' genotypes. This inheritance process ensures a dynamic population with genetic diversity.

### Map Variants
There are two types of map configurations that affect how food grows:

**Standard Map**:
The probability of plant growth is uniform across all fields on the grid. This results in a balanced distribution of food resources throughout the entire map.

**Equator Map**:
In this variant, food grows more frequently in the middle rows of the grid (simulating an "equator" region), where the probability of plant growth is significantly higher. This creates a strategic challenge for the animals, as they are more likely to find food in central areas.

### Animal Behavior Variants
The way animals execute their genotype sequence also depends on the selected behavior mode:

**Standard Behavior**:
Animals follow their genotype sequence in a linear loop. Once the last move in their genotype is completed, they restart from the beginning (front to back).

**Crazy Behavior**:
In this mode, once an animal finishes the last move in its genotype, it reverses direction and executes the moves in reverse order (back to front). This creates more erratic movement patterns, making the simulation less predictable.

## Project setup in IntelliJ

### 1. Clone the Repository
First, clone the project repository from GitHub (or another source):

```bash
git https://github.com/jasiek1212/Simulations.git
```
Navigate into the project directory:
```bash
cd Simulations
```
### 2. Open the Project in IntelliJ
Launch IntelliJ IDEA.
In the welcome window, click on "Open".
Navigate to the folder where you cloned the project and select the root project directory.
IntelliJ will automatically detect the project and prompt you to import the project structure (a small prompt saying to load gradle build).

### 3. Import Dependencies 
IntelliJ will automatically detect build.gradle file and download the necessary dependencies.

If IntelliJ doesn't automatically sync the dependencies, right-click the build.gradle file and select "Reload Gradle Project".

### 4. Configure Project SDK
In IntelliJ, navigate to Simulations/Simulations/src/main/java/World class and double click it. In the top right corner a box will appear saying that project SDk is not defined. Click `select SDK` and choose your SDK. During the development of the project we used  Corretto-17.

### 5. Run the project

Now, click the green arrow at the top of the screen (still having the World class open) to run the program.

## Run the Program

### 1. Configuration

There are two options available:
* input your own configurations
* run with previous configuration. If this is you first time running the program, this option will open the simulation with a default configuration.

### 2. Simulation

Once the configuration is confirmed, the simulation window will appear with objects as discussed in the  [Project Functionality](#project-functionality) chapter. You can pause the simulation with the pause/resume button and click on any of the animals to see its personal statistics (resume the simulation first). They will be shown on the left side of the grid. The simulation will keep running as long as any animal is alive and will stop once all the animals are dead.
