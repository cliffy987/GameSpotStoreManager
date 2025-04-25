module com.mycompany.gamespotstoremanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.gamespotstoremanager to javafx.fxml;
    exports com.mycompany.gamespotstoremanager;
}
