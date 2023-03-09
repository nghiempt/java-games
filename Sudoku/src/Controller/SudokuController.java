package Controller;

import Models.SudokuModel;
import Views.SudokuGame;

public class SudokuController implements Controller {

    SudokuModel model;
    SudokuGame view;

    public SudokuController(SudokuModel model) {
        this.model = model;
        view = new SudokuGame(this, model);
    }

    @Override
    public void makeNewGame() {
        model.makeNewGame();
    }

}
