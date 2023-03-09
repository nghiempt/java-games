package Models;

import java.util.HashSet;
import java.util.Random;

public class Genome implements Comparable<Genome> {

    int state[][] = new int[9][9];
    static Random random = new Random();
    public long length;
    public int crossoverPoint;
    public int mutationIndex;
    public float currentFitness = 0.0f;
    int theMin = 0;
    int theMax = 1000;
    HashSet<Integer> rowMap = new HashSet<Integer>();
    HashSet<Integer> columnMap = new HashSet<Integer>();
    HashSet<Integer> squareMap = new HashSet<Integer>();

    public void setSate(int[][] state) {
        this.state = state;
    }

    @Override
    public int compareTo(Genome o) {
        if (this.currentFitness < o.currentFitness) {
            return 1;
        } else if (this.currentFitness > o.currentFitness) {
            return -1;
        } else {
            return 0;
        }
    }

    public void setCrossoverPoint(int crossoverPoint) {
        this.crossoverPoint = crossoverPoint;
    }

    public Genome() {

    }

    public Genome(long length, int min, int max) {
        this.length = length;
        this.theMax = max;
        this.theMin = min;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.state[i][j] = 1 + random.nextInt(8);
            }
        }
    }

    public boolean canDie(float fitness) {
        if (currentFitness <= (int) (fitness * 100.0f)) {
            return true;
        }
        return false;
    }

    public boolean canReproduce(float fitness) {
        if (random.nextInt(100) >= (int) (fitness * 100.0f)) {
            return true;
        }
        return false;
    }

    public void mutate() {
        int mutationIndex1 = random.nextInt(9);
        int mutationIndex2 = random.nextInt(9);
        int mutationIndex3 = random.nextInt(9);
        if (random.nextInt(2) == 1) {
            this.state[mutationIndex1][mutationIndex2] = mutationIndex3 + 1;
        } else {
            int temp = 0;
            if (random.nextInt(2) == 1) {
                temp = this.state[mutationIndex1][mutationIndex2];
                this.state[mutationIndex1][mutationIndex2] = this.state[mutationIndex3][mutationIndex2];
                this.state[mutationIndex3][mutationIndex2] = temp;
            } else {
                temp = this.state[mutationIndex2][mutationIndex1];
                this.state[mutationIndex2][mutationIndex1] = this.state[mutationIndex2][mutationIndex3];
                this.state[mutationIndex2][mutationIndex3] = temp;
            }
        }
    }

    public float calculateFitness() {
        float fitnesssRows = 0;
        float fitnessColumns = 0;
        float fitnessSquare = 0;
        // Rows and Columns
        for (int i = 0; i < 9; i++) {
            rowMap.clear();
            columnMap.clear();
            for (int j = 0; j < 9; j++) {
                rowMap.add(state[i][j]);
                columnMap.add(state[j][i]);
            }
            fitnesssRows += (float) (1.0f / (float) (9 + 1 - rowMap.size())) / 9.0f;
            fitnessColumns += (float) (1.0f / (float) (9 + 1 - columnMap.size())) / 9.0f;
        }
        // Square
        for (int l = 0; l < 3; l++) {
            for (int k = 0; k < 3; k++) {
                squareMap.clear();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        squareMap.add(state[i + k * 3][j + l * 3]);
                    }
                }
                fitnessSquare += (float) (1.0f / (float) (9 + 1 - squareMap.size())) / 9.0f;
            }
        }
        return currentFitness = fitnesssRows * fitnessColumns * fitnessSquare;
    }

    public int[][] getState() {
        return state;
    }

    public float calFitness() {
        calculateFitness();
        return currentFitness;
    }

    public void copy(Genome dest) {
        Genome gene = dest;
        gene.length = length;
        gene.theMin = theMin;
        gene.theMax = theMax;
    }

    public void toStrings() {
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                if (k == 0) {
                    System.out.print("{" + this.state[j][k] + ", ");
                } else if (k == 8) {
                    System.out.print(this.state[j][k] + "}, ");
                } else {
                    System.out.print(this.state[j][k] + ", ");
                }
            }
            System.out.println();
        }
        System.out.println("--> Fitness: " + this.currentFitness);

    }

    public Genome crossOver(Genome g) {
        Genome gene1 = new Genome();
        Genome gene2 = new Genome();
        g.copy(gene1);
        g.copy(gene2);

        if (random.nextInt(2) == 1) {
            for (int j = 0; j < 9; j++) {
                crossoverPoint = random.nextInt(8) + 1;// 1 2 3 4 5 6 7 5 9
                for (int k = 0; k < crossoverPoint; k++) {
                    gene1.state[k][j] = g.state[k][j];
                    gene2.state[k][j] = this.state[k][j];
                }
                for (int k = crossoverPoint; k < 9; k++) {
                    gene2.state[k][j] = g.state[k][j];
                    gene1.state[k][j] = this.state[k][j];
                }
            }
        } else {
            for (int j = 0; j < 9; j++) {
                crossoverPoint = random.nextInt(8) + 1;
                for (int k = 0; k < crossoverPoint; k++) {
                    gene1.state[j][k] = g.state[j][k];
                    gene2.state[j][k] = this.state[j][k];
                }
                for (int k = crossoverPoint; k < 9; k++) {
                    gene2.state[j][k] = g.state[j][k];
                    gene1.state[j][k] = this.state[j][k];
                }
            }
        }
        Genome gene = null;
        if (random.nextInt(2) == 1) {
            gene = gene1;
        } else {
            gene = gene2;
        }

        return gene;

    }
}
