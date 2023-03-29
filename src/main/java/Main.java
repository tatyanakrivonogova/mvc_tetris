import controller.Controller;
import model.Model;
import view.GUI;
import view.TUI;
import view.View;

import java.util.Objects;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        System.out.println("BEFORE VIEW");
        System.out.println(Thread.getAllStackTraces());
        View view;
        if (args.length > 0 && (Objects.equals(args[0], "g") || Objects.equals(args[0], "G"))) {
            view = new GUI(controller);
        } else if (args.length > 0 && (Objects.equals(args[0], "t") || Objects.equals(args[0], "T"))) {
            view = new TUI(controller);
        } else {
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to use graphic interface?");
            String reply = scan.nextLine();
            if (reply.equals("Y") || reply.equals("y")) {
                view = new GUI(controller);
            } else {
                view = new TUI(controller);
            }
            scan.close();
        }

        Model game = new Model(view);
        controller.setGame(game);
        game.join();
    }
}