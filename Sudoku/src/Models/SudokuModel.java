package Models;

public class SudokuModel {

    Population population;

    public SudokuModel() {
        population = new Population();
    }

    public void makeNewGame() {
        population.makeNewGame();
    }

    public Genome getGenome() {
        return population.getGenome();
    }

    public boolean isSuccess() {
        return population.isMakeNewGameSuccess();
    }

    public static void main(String[] args) {
        SudokuModel s = new SudokuModel();
        s.makeNewGame();
    }
}
