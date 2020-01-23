package util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Notification implements Serializable{
	
	private String sender;
	private String reciever;
	private String message;
	
	public Notification(String sender, String reciever, String message) {
		this.sender = sender;
		this.reciever = reciever;
		this.message = message;
	}
	
	public void show(NotificationManager nm, ArrayList<Notification> toRemove) {
		Stage notificationStage = new Stage();
		notificationStage.initStyle(StageStyle.UNDECORATED);
		notificationStage.initModality(Modality.APPLICATION_MODAL);
		
		HBox layout = new HBox(30);
		layout.setAlignment(Pos.CENTER);
		layout.setPrefSize(600, 130);
		layout.setStyle("-fx-background-color: #074F76");
		
		ImageView alertIV = new ImageView();
		alertIV.setFitHeight(70);
		alertIV.setFitWidth(70);
		alertIV.setPreserveRatio(true);
		alertIV.setImage(new Image("resources" + File.separator + "alert.png"));
		
		Label message = new Label(this.getMessage());
		Font fnt = new Font(32);
		message.setStyle("-fx-text-fill: White");
		message.setFont(fnt);
		
		FlatButton okBtn = new FlatButton("Ok");
		okBtn.setStyle("-fx-font-size: 20");
		
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				toRemove.add(Notification.this);
				notificationStage.close();
			}
		});
		
		layout.getChildren().addAll(alertIV, message, okBtn);
		Scene notificationScene = new Scene(layout, 600, 130);
		notificationStage.setScene(notificationScene);
		notificationStage.showAndWait();
	}

	public String getSender() { return sender; }
	public void setSender(String sender) { this.sender = sender; }

	public String getReciever() { return reciever; }
	public void setReciever(String reciever) { this.reciever = reciever; }

	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	
}
