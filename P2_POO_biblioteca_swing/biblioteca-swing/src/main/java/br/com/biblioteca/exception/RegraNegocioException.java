package br.com.biblioteca.exception;

/** Violação de regra de negócio (ex.: cópia indisponível). */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) { super(mensagem); }
}
