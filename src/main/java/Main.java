import controller.Controller;
import model.Model;
import view.GUI;
import view.View;
public class Main {
    public static void main(String[] args) {
        Model game = new Model();
        Controller controller = new Controller(game);
        View view = new GUI(game, controller);
        game.setView(view);

        controller.go();
    }
}