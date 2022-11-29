package jsalazar.inventory_c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
/**
 * This is the main class. This is where the program starts.
 * @author John Salazar<br><br>
 *
 * <b>FUTURE ENHANCEMENT - Rubric Item B</b><br>
 * While testing the project I wanted to improve the inventory system
 * by decrementing and incrementing the Inventory levels with each added part's
 * inventory values, and to track the inventory as parts are added to products.<br>
 * Alerts if the inventory level falls below minimum.<br><br>
 * <b>LOGICAL/ RUNTIME ERROR</b> located on ModifyPartController<br><br>
 * <b>JAVADOCS</b> located at: C482\INVENTORY_C482\JavaDocs_c482
 */
public class Main extends Application {
    /**
     * Start method generates the Inventory's Main Menu.
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 900, 450));
        primaryStage.show();
    }

    /**
     * Main method starts the program - Navigate to <b>MainMenuController</b> and
     * assign <b>mainReturns</b> attribute to 0 (zero) to generate test data;
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}