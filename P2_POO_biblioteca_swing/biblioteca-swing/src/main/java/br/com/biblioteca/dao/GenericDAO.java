package br.com.biblioteca.dao;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico de acesso a dados.
 * Toda implementação DAO converte SQLException em DAOException.
 */
public interface GenericDAO<T, ID> {
    T inserir(T entidade);
    void atualizar(T entidade);
    void remover(ID id);
    Optional<T> buscarPorId(ID id);
    List<T> listarTodos();
}
