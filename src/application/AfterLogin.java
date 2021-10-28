package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AfterLogin {
	
	@FXML
	private Button logout;
	
	public void userLogOut(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("Sample.fxml");
	}

}
