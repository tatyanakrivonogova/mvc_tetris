import controller.Controller;
import model.Model;
import view.GUI;
import view.View;
public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new GUI(controller);
        Model game = new Model(view);
        controller.setGame(game);

        controller.go();
    }
}