import controller.Controller;
import model.Model;
import view.GUI;
import view.TUI;
import view.View;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new GUI(controller);

//        Scanner scan = new Scanner(System.in);
//        System.out.println("Do you want to use graphic interface?");
//        String reply = scan.nextLine();
//        if (reply.equals("Y") || reply.equals("y")) {
//            view = new GUI(controller);
//        } else {
//            view = new TUI(controller);
//        }
//        scan.close();

        Model game = new Model(view);
        controller.setGame(game);
//        game.run();
        //new Thread(game,"Game").start();
        System.out.println("AFTER");
    }
}