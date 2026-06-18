package br.com.biblioteca.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fábrica de conexões JDBC (Singleton de configuração).
 * Centraliza o acesso ao banco para que a camada DAO não conheça detalhes
 * de driver/URL.
 */
public final class ConnectionFactory {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConnectionFactory.class
                .getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties não encontrado no classpath");
            }
            PROPS.load(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionFactory() {}

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password"));
    }
}
