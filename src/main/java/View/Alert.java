package View;

public class Alert {

    public Alert() {
    }

    public void errorAlert(String header, String context){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}