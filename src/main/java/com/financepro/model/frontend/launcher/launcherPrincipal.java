package com.financepro.model.frontend.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class launcherPrincipal extends Application {
    private static Stage stagePrincipal;

    public void start(Stage primeirostage) throws Exception {
        stagePrincipal=primeirostage;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/viewPrincipal.fxml"));
        Scene scene = new Scene(loader.load());

        primeirostage.setResizable(false);
        primeirostage.setScene(scene);
        primeirostage.show();

    }

    public  static void iniciartela2(String nometela) throws Exception{
        FXMLLoader loader = new FXMLLoader(launcherPrincipal.class.getResource(nometela));
        Scene novaCena = new Scene(loader.load());
        stagePrincipal.setScene(novaCena);
    }

    public static void iniciartela3(String nometela3) throws Exception{
        FXMLLoader loader=new FXMLLoader(launcherPrincipal.class.getResource(nometela3));
        Scene novaCena=new Scene(loader.load());
        stagePrincipal.setScene(novaCena);
    }





    public static void iniciartela1( String nometela1) throws Exception {
        FXMLLoader loader=new FXMLLoader(launcherPrincipal.class.getResource(nometela1));
        Scene novaCene=new Scene(loader.load());
        stagePrincipal.setScene(novaCene);
    }


    public static void main(String[] args) {
        launch();
    }
}
