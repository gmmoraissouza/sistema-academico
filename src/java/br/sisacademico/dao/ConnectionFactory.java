package br.sisacademico.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    //para nos conectarmos a qualquer BD precisamos de 4 coisas...
    //1° - URL da base (servidor)
    //2° - Nome do Banco(neste caso ficará junto da URL)
    //3° - Usuário
    //4° - Senha
    
    private static final String urlDB = "jdbc:derby://localhost:1527/sisacademico";
    private static final String user = "gmms";
    private static final String password = "gmms";
    
    
    //SQLException => Qualquer erro na execução, retornará um erro
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        return DriverManager.getConnection(urlDB, user, password);
    }
}
