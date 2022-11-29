package exa15brevep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {//Fecha en 28-8-1984
        Connection conn = Conexion();
//        crearTablas(conn);
        //exe(conn);
        leerPlatoss();
        conn.close();
    }

    public static Connection Conexion(){
        Connection conn;
        String driver = "jdbc:postgresql:";
        String host = "//localhost:"; // tamen poderia ser una ip como "192.168.1.14"
        String porto = "5432";
        String sid = "postgres";
        String usuario = "davidmoralesluis";
        String password = "";
        String url = driver + host+ porto + "/" + sid;
        try {
            conn = DriverManager.getConnection(url,usuario,password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    static void leerPlatoss() throws IOException, ClassNotFoundException {
        File ficheiro=new File(System.getProperty("user.dir")+"/src/main/java/exa15brevep/platoss");

//        ObjectOutputStream obxWrite=new ObjectOutputStream(new FileOutputStream(ficheiro));
        ObjectInputStream obxRead=new ObjectInputStream(new FileInputStream(ficheiro));


        Platos p=null;
        while ((p=(Platos)obxRead.readObject())!=null){
            System.out.println(p);
        }
        obxRead.close();
    }
    }

