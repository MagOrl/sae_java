import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Group; 
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider ;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.geometry.Orientation;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;
import javafx.event.EventHandler;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import java.util.Arrays;

public class App extends Application{
    private TextField id;
    private TextField mdp;
    public App(){
    }
    @Override
    public void init(){
        // cette méthode est utilisée pour initialiser les éléments 
        // non graphiques et événtuellement graphiques autres que la Scène et le Stage
        this.id = new TextField("");
        this.mdp = new TextField("");
    }    
    
    @Override
    public void start(Stage stage){
        // Cette méthode est le point d'entrée principal pour toutes les applications JavaFX.
        // La méthode start est appelée après le retour de la méthode init()
        // Elle doit créer la Scène et le Stage
        Vbox root = new Vbox();
        
        
    }
    private GridPane gridPane() {
        GridPane var1 = new GridPane();
        var1.add(new Label("Entrez l'identifiant"), 0, 0);
        var1.add(new Label("Entrez le mot de passe"), 0, 1);
        var1.add(new Label("Résultat : "), 0, 2);
        var1.add(this.id, 1, 0);
        var1.add(this.mdp, 1, 1);
        var1.setHgap(50.0);
        var1.setVgap(20.0);
        return var1;
     }

    
}
