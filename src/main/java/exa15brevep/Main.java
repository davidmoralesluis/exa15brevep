package exa15brevep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;

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
    public static void exe(Connection connection){
        ResultSet rs = null;
        String codp=null;
        String nomep="",nomep1="",nomep2="",nomep3="",nomep4="";
        float graxaTotal1=0,graxaTotal2=0,graxaTotal3=0,graxaTotal4 =0;
        int peso;
        int graxa;
        try {
            rs = connection.createStatement().executeQuery("select codp,nomec,graxa, peso from componentes right JOIN composicion ON componentes.codc = composicion.codc;");
            while(rs.next()){
                codp=rs.getString(1);
                nomep=rs.getString(2);
                graxa=rs.getInt(3);
                peso=rs.getInt(4);
                if(codp.equalsIgnoreCase("p1")){
                    nomep1=nomep1+nomep+" ";
                    graxaTotal1+=peso/100*graxa;
                }
                if(codp.equalsIgnoreCase("p2")){
                    nomep2=nomep2+nomep+" ";
                    graxaTotal2+=peso/100*graxa;
                }
                if(codp.equalsIgnoreCase("p3")){
                    nomep3=nomep3+nomep+" ";
                    graxaTotal3+=peso/100*graxa;
                }
                if(codp.equalsIgnoreCase("p4")){
                    nomep4=nomep4+nomep+" ";
                    graxaTotal4+=peso/100*graxa;
                }

            }

            System.out.print("\nP1\n"+nomep1+"\n"+"grasas Total: "+graxaTotal1);
            System.out.print("\nP2\n"+nomep2+"\n"+"grasas Total: "+graxaTotal2);
            System.out.print("\nP3\n"+nomep3+"\n"+"grasas Total: "+graxaTotal3);
            System.out.print("\nP4\n"+nomep4+"\n"+"grasas Total: "+graxaTotal4);
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

