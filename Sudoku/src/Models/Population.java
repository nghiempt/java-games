package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class Population {

    protected int kLength = 9;
    protected int kCrossover = kLength / 2;
    protected int kInitialPopulation = 1000;
    protected int kPopulationLimit = 50;
    protected int kMin = 1;
    protected int kMax = 1000;
    protected float kMutationFrequency = 0.33f;
    protected float kDeathFitness = -1.00f;
    protected float kReproductionFitness = 0.0f;

    protected ArrayList<Genome> Genomes = new ArrayList<Genome>();
    protected ArrayList<Genome> GenomeReproducers = new ArrayList<Genome>();
    protected ArrayList<Genome> GenomeResults = new ArrayList<Genome>();
    protected ArrayList<Genome> GenomeFamily = new ArrayList<Genome>();

    protected int CurrentPopulation = kInitialPopulation;
    protected int generation = 1;
    protected boolean best2 = true;
    protected Genome lastGenome;
    public int[][] board = new int[9][9];
    static final int SIZE = 9;
    static Random r = new Random();
    boolean isMakeNewGameSuccess = true;

    public Population() {

    }

    public void makePopulation() {
        for (int i = 0; i < kInitialPopulation; i++) {
            Genome aGenome = new Genome(kLength, kMin, kMax);
            aGenome.setCrossoverPoint(kCrossover);
            aGenome.calFitness();
            Genomes.add(aGenome);
        }
    }

    public void mutate(Genome aGene) {
        if (Genome.random.nextInt(100) < (int) (kMutationFrequency * 100.0)) {//33%
            aGene.mutate();
        }
    }

    public void calcculateFitnessForAll(ArrayList<Genome> genes) {
        for (Genome g : genes) {
            g.calculateFitness();
        }
    }

    public void doCrossover(ArrayList<Genome> genes) {
        ArrayList<Genome> geneMoms = new ArrayList<Genome>();
        ArrayList<Genome> geneDads = new ArrayList<Genome>();
        for (int i = 0; i < genes.size(); i++) {
            if (Genome.random.nextInt(100) % 2 > 0) {
                geneMoms.add(genes.get(i));
            } else {
                geneDads.add(genes.get(i));
            }
        }
        // Can bang
        if (geneMoms.size() > geneDads.size()) {
            while (geneMoms.size() > geneDads.size()) {
                geneDads.add(geneMoms.get(geneMoms.size() - 1));
                geneMoms.remove(geneMoms.size() - 1);
            }
            if (geneDads.size() > geneMoms.size()) {
                geneDads.remove(geneDads.size() - 1);
            }
        } else {
            while (geneDads.size() > geneMoms.size()) {
                geneMoms.add(geneDads.get(geneDads.size() - 1));
                geneDads.remove(geneDads.size() - 1);
            }
            if (geneMoms.size() > geneDads.size()) {
                geneMoms.remove(geneMoms.size() - 1);
            }
        }
        // Lai ghep
        for (int i = 0; i < geneDads.size(); i++) {
            Genome childGene1 = geneDads.get(i).crossOver(geneMoms.get(i));
            Genome childGene2 = geneMoms.get(i).crossOver(geneDads.get(i));
            GenomeFamily.clear();
            GenomeFamily.add(geneDads.get(i));
            GenomeFamily.add(geneMoms.get(i));
            GenomeFamily.add(childGene1);
            GenomeFamily.add(childGene2);
            calcculateFitnessForAll(GenomeFamily);
            Collections.sort(GenomeFamily);
            GenomeResults.add(GenomeFamily.get(0));
            GenomeResults.add(GenomeFamily.get(1));
        }
    }

    public void nextGeneration() {
        generation++;
        GenomeResults.clear();
        doCrossover(Genomes);
        Genomes = (ArrayList<Genome>) GenomeResults.clone();
        for (int i = 0; i < Genomes.size(); i++) {
            mutate(Genomes.get(i));
        }
        for (int i = 0; i < Genomes.size(); i++) {
            Genomes.get(i).calFitness();
        }
        Collections.sort(Genomes);

        for (int i = Genomes.size() - 1; i > kPopulationLimit; i--) {
            Genomes.remove(i);

        }
        CurrentPopulation = Genomes.size();

    }

    public void CclculateFitnessForAll(ArrayList<Genome> genes) {
        for (Genome lg : genes) {
            lg.calFitness();
        }
    }

    public Genome getGenome() {
        return lastGenome;
    }

    public void makeNewGame() {
        makePopulation();
        for (int i = 0; i < 1000; i++) {
            nextGeneration();
            print();
        }
        int[][] lastState = lastGenome.getState();
        makeProblem(lastState);
        display2(lastState);
        solveGame();

    }

    public int[][] copyState(int[][] state) {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = state[i][j];
            }
        }
        return result;
    }

    public void display2(int board[][]) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + board[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }

    public void solveGame() {

        if (solve()) {
            System.out.println("Tạo Thành công");
            display2(lastGenome.getState());
            isMakeNewGameSuccess = true;
            System.out.println("Hàm đánh giá: " + calculateFitness(lastGenome.getState()));

        } else {
            System.out.println("Thất bại");
            isMakeNewGameSuccess = false;
        }
        GenomeFamily.clear();
        GenomeResults.clear();
        GenomeReproducers.clear();
        Genomes.clear();
    }

    public boolean isMakeNewGameSuccess() {
        return isMakeNewGameSuccess;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void print() {
        System.out.println("----> Generation: " + generation);
        Collections.sort(Genomes);
        lastGenome = Genomes.get(0);
        Genomes.get(0).toStrings();

    }

    public void makeProblem(int[][] state) {
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                if (i < 8) {
                    for (int j = i + 1; j < 9; j++) {
                        if (state[k][i] == state[k][j]) {
                            state[k][i] = 0;
                        }
                    }
                }
            }
        }
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                if (i < 8) {
                    for (int j = i + 1; j < 9; j++) {
                        if (state[i][k] == state[j][k]) {
                            state[i][k] = 0;
                        }
                    }
                }
            }
        }

        for (int l = 0; l < 5; l++) {
            for (int k = 0; k < 9; k++) {
                int i = 1 + r.nextInt(8);
                state[k][i] = 0;
            }
            for (int k = 0; k < 9; k++) {
                int i = 1 + r.nextInt(8);
                state[i][k] = 0;
            }
        }
    }

    // we check if a possible number is already in a row
    private boolean isInRow(int row, int number) {
        for (int i = 0; i < SIZE; i++) {
            if (lastGenome.getState()[row][i] == number) {
                return true;
            }
        }

        return false;
    }

    // we check if a possible number is already in a column
    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++) {
            if (lastGenome.getState()[i][col] == number) {
                return true;
            }
        }

        return false;
    }

    // we check if a possible number is in its 3x3 box
    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (lastGenome.getState()[i][j] == number) {
                    return true;
                }
            }
        }

        return false;
    }

    // combined method to check if a number possible to a row,col position is ok
    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
    }

    // Solve method. We will use a recursive BackTracking algorithm.
    // we will see better approaches in next video :)
    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                // we search an empty cell
                if (lastGenome.getState()[row][col] == 0) {
                    // we try possible numbers
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            // number ok. it respects sudoku constraints
                            lastGenome.getState()[row][col] = number;

                            if (solve()) { // we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                lastGenome.getState()[row][col] = 0;
                            }
                        }
                    }

                    return false; // we return false
                }
            }
        }

        return true; // sudoku solved
    }

    public float calculateFitness(int state[][]) {
        float fitnesssRows = 0;
        float fitnessColumns = 0;
        float fitnessSquare = 0;
        HashSet<Integer> rowMap = new HashSet<Integer>();
        HashSet<Integer> columnMap = new HashSet<Integer>();
        HashSet<Integer> squareMap = new HashSet<Integer>();
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

        return fitnesssRows * fitnessColumns * fitnessSquare;
    }

}
