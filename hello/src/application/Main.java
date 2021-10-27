package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	private int counter = 0;
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			
			Label lbl = new Label("Counter = ?");
			lbl.setFont(new Font("Arial",30));
			
			Button incBtn = new Button("Inc");
			Button decBtn = new Button("Dec");
			
			incBtn.setOnAction(event -> lbl.setText("Counter = " +(++counter)));
			//shorter way of writing below code. 
/*			
			incBtn.setOnAction(new EventHandler<ActionEvent> () {				
				@Override
				public void handle(ActionEvent event)
				{
					counter += 1;
					lbl.setText("Counter = " + counter);
				}
			}); 
*/			
			decBtn.setOnAction(new EventHandler<ActionEvent> () {				
				@Override
				public void handle(ActionEvent event)
				{
					counter -= 1;
					lbl.setText("Counter = " + counter);
				}
			});
			
			
			HBox buttons = new HBox();
			buttons.setStyle("-fx-background-color: cyan;");
			buttons.getChildren().add(incBtn);
			//set size 
			//incBtn.setPrefWidth(100);
			buttons.getChildren().add(decBtn);
			
			VBox root = new VBox();
			root.getChildren().add(lbl);
			root.getChildren().add(buttons);
			
			
			Scene scene = new Scene(root,400,400);
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
