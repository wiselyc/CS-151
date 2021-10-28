package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class Login {
	
	public Login() {
}

@FXML
private Button button;

@FXML
private Label wronglogin;

@FXML
private TextField username;

@FXML
private PasswordField password;


public void userLogin(ActionEvent event) throws IOException{
	checkLogin();
}

private void checkLogin() throws IOException{
	Main m = new Main();
	if(username.getText().toString().equals("wisely")&& password.getText().toString().equals("123")) {
		wronglogin.setText("Success!");
		
		m.changeScene("AfterLogin.fxml");
	}
	
	else if(username.getText().isEmpty() && password.getText().isEmpty()) {
		wronglogin.setText("Please enter your data.");
	}
	
	else {
		wronglogin.setText("Wrong username or password");
	}
}
}

