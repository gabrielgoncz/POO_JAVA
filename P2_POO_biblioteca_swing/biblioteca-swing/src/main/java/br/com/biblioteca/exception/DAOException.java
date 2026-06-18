package br.com.biblioteca.exception;

/**
 * Exceção de domínio da camada DAO.
 * Encapsula SQLException para que a UI não dependa de java.sql.*.
 */
public class DAOException extends RuntimeException {
    public DAOException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    public DAOException(String mensagem) {
        super(mensagem);
    }
}
