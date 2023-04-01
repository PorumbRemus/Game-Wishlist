module com.wishlist.wishlist {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.wishlist.wishlist to javafx.fxml;
    exports com.wishlist.wishlist;
}