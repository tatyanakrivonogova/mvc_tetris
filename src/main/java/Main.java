import controller.Controller;
import exceptions.factoryexceptions.FactoryException;
import model.Model;
import view.GUI;
import view.View;
public class Main {
    public static void main(String[] args) {
        Model game = new Model();
        View view = new GUI(game);
        game.setView(view);
        Controller controller = new Controller(game, view);
        view.setController(controller);
        controller.go();
    }
}