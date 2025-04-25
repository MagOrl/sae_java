// Source code is decompiled from a .class file using FernFlower decompiler.
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
   private TextField id;
   private TextField mdp;

   public App() {
   }

   public void init() {
      this.id = new TextField("");
      this.mdp = new TextField("");

   }

   private GridPane gridPane() {
      GridPane var1 = new GridPane();
      var1.add(new Label("Nombre 1"), 0, 0);
      var1.add(new Label("Nombre 2"), 0, 1);
      var1.add(this.id, 1, 0);
      var1.add(this.mdp, 1, 1);
      var1.setHgap(50.0);
      var1.setVgap(20.0);
      return var1;
   }
   

   private HBox hbox() {
      HBox var1 = new HBox(50.0);
      Button var2 = new Button("Entrer");
      Button var8 = new Button("Quitter");
      var1.getChildren().addAll(new Node[]{var2, var8});
      var1.setAlignment(Pos.CENTER);
    //   var2.setOnAction(new ControleurEntrer(this));
      var8.setOnAction(new ControleurQuitter(this));
      return var1;
   }

   private HBox root() {
      HBox var1 = new HBox(10.0);
      VBox var2 = new VBox(50.0);
      var2.getChildren().addAll(new Node[]{this.gridPane(), this.hbox()});
      var2.setPrefWidth(400.0);
      HBox.setMargin(var2, new Insets(30.0));
      var1.getChildren().add(var2);
      return var1;
   }

   public void start(Stage var1) {
      Scene var2 = new Scene(this.root());
      var1.setTitle("Librairies");
      var1.setScene(var2);
      var1.show();
   }


   public void quitte() {
      Platform.exit();
   }

}
