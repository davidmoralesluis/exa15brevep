package exa15brevep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {//Fecha en 28-8-1984
        Connection conn = Conexion();
//        crearTablas(conn);
        exe(conn);
        conn.close();
    }

    public static Connection Conexion(){
        Connection conn;
        String driver = "jdbc:postgresql:";
        String host = "//localhost:"; // tamen poderia ser una ip como "192.168.1.14"
        String porto = "5432";
        String sid = "postgres";
        String usuario = "dam2a";
        String password = "castelao";
        String url = driver + host+ porto + "/" + sid;
        try {
            conn = DriverManager.getConnection(url,usuario,password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }



    public static void exe(Connection connection)throws IOException, ClassNotFoundException{
        ResultSet rs = null;
        String codp=null;
        String nomep="";
        float graxaTotal=0;
        int peso;
        int graxa;
        File ficheiro=new File(System.getProperty("user.dir")+"/src/main/java/exa15brevep/platoss");
        ObjectInputStream obxRead=new ObjectInputStream(new FileInputStream(ficheiro));

        Platos p=null;
        while ((p=(Platos)obxRead.readObject())!=null){
            try {

                rs = connection.createStatement().executeQuery("select codp,graxa, peso from componentes right JOIN composicion ON componentes.codc = composicion.codc where codp='"+p.getCodigop()+"';");
                while(rs.next()){

                    codp=rs.getString(1);
                    graxa=rs.getInt(2);
                    peso=rs.getInt(3);

                    graxaTotal+=peso/100*graxa;
                }
                System.out.print("\n"+codp+"\n"+p.getNomep()+"\n"+"grasas Total: "+graxaTotal+"\n");
                nomep="";
                graxaTotal=0;


                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        obxRead.close();

    }



    public static void crearTablas( Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "drop table if exists composicion cascade;" +
                "drop table if exists componentes cascade;" +
                "create table composicion(codp varchar(3),codc varchar(3),peso integer, primary key (codp,codc));" +
                "insert into composicion values ('p1','c1',400);" +
                "insert into composicion values ('p1','c3',600);" +
                "insert into composicion values ('p2','c2',600);" +
                "insert into composicion values ('p2','c3',300);" +
                "insert into composicion values ('p2','c4',200);" +
                "insert into composicion values ('p3','c1',100);" +
                "insert into composicion values ('p3','c2',200);" +
                "insert into composicion values ('p4','c1',200);" +
                "insert into composicion values ('p4','c3',200);" +
                "create table componentes(codc varchar(3),nomec varchar(15),graxa integer, primary key (codc));" +
                "insert into componentes values ('c1','vacuno',5);" +
                "insert into componentes values ('c2','ovino',20);" +
                "insert into componentes values ('c3','avicola',10);" +
                "insert into componentes values ('c4','avicola',5);";

        stmt.executeUpdate(sql);
    }


    }

