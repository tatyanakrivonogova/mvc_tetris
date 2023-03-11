import controller.Controller;
import exceptions.factoryexceptions.FactoryException;
import model.Model;
import view.GUI;
import view.View;
public class Main {
    public static void main(String[] args) throws FactoryException {
        Model game = new Model();
        View view = new GUI(game);
        Controller controller = new Controller(game, view);
        controller.go();
    }
}