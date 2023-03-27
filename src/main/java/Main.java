import controller.Controller;
import model.Model;
import view.GUI;
import view.TUI;
import view.View;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        View view;
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you want to use graphic interface?");
        String reply = scan.nextLine();
        if (reply.equals("Y") || reply.equals("y")) {
            view = new GUI(controller);
        } else {
            view = new TUI(controller);
        }
        scan.close();

        Model game = new Model(view);
        controller.setGame(game);
        game.join();
        System.exit(0);
    }
}