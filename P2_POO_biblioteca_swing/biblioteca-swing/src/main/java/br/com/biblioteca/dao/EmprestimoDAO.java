package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.exception.RegraNegocioException;
import br.com.biblioteca.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoDAO implements GenericDAO<Emprestimo, Long> {

    private static final int DIAS_EMPRESTIMO_PADRAO = 7;

    @Override
    public Emprestimo inserir(Emprestimo e) {
        if (e.getDataEmprestimo() == null) e.setDataEmprestimo(LocalDate.now());
        if (e.getDataPrevista() == null)
            e.setDataPrevista(e.getDataEmprestimo().plusDays(DIAS_EMPRESTIMO_PADRAO));

        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try {
                // 1) checa estado da cópia
                try (PreparedStatement ps = c.prepareStatement(
                        "SELECT estado FROM copia WHERE id=? FOR UPDATE")) {
                    ps.setLong(1, e.getCopia().getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new RegraNegocioException("Cópia inexistente.");
                        if (!"DISPONIVEL".equals(rs.getString(1)))
                            throw new RegraNegocioException("Cópia não está disponível.");
                    }
                }
                // 2) insere empréstimo
                String sql = "INSERT INTO emprestimo(leitor_id,copia_id,funcionario_id," +
                             "data_emprestimo,data_prevista,multa,status) VALUES (?,?,?,?,?,?, 'ATIVO')";
                try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, e.getLeitor().getId());
                    ps.setLong(2, e.getCopia().getId());
                    ps.setLong(3, e.getFuncionarioResponsavel().getId());
                    ps.setDate(4, Date.valueOf(e.getDataEmprestimo()));
                    ps.setDate(5, Date.valueOf(e.getDataPrevista()));
                    ps.setBigDecimal(6, e.getMulta());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) e.setId(rs.getLong(1));
                    }
                }
                // 3) marca cópia como EMPRESTADA
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE copia SET estado='EMPRESTADA' WHERE id=?")) {
                    ps.setLong(1, e.getCopia().getId());
                    ps.executeUpdate();
                }
                c.commit();
                return e;
            } catch (RuntimeException ex) {
                c.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new DAOException("Erro ao registrar empréstimo", ex);
        }
    }

    /** Devolução: grava data, multa e libera a cópia. */
    public void devolver(Long emprestimoId) {
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try {
                Emprestimo emp = buscarPorIdInterno(c, emprestimoId)
                        .orElseThrow(() -> new RegraNegocioException("Empréstimo não encontrado."));
                if (emp.getStatus() == Emprestimo.Status.DEVOLVIDO)
                    throw new RegraNegocioException("Empréstimo já devolvido.");

                emp.setDataDevolucao(LocalDate.now());
                emp.setMulta(emp.calcularMulta());
                emp.setStatus(Emprestimo.Status.DEVOLVIDO);

                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE emprestimo SET data_devolucao=?, multa=?, status='DEVOLVIDO' WHERE id=?")) {
                    ps.setDate(1, Date.valueOf(emp.getDataDevolucao()));
                    ps.setBigDecimal(2, emp.getMulta());
                    ps.setLong(3, emp.getId());
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE copia SET estado='DISPONIVEL' WHERE id=?")) {
                    ps.setLong(1, emp.getCopia().getId());
                    ps.executeUpdate();
                }
                c.commit();
            } catch (RuntimeException ex) {
                c.rollback(); throw ex;
            }
        } catch (SQLException ex) {
            throw new DAOException("Erro ao devolver empréstimo", ex);
        }
    }

    @Override public void atualizar(Emprestimo e) { throw new UnsupportedOperationException(); }
    @Override public void remover(Long id) { throw new UnsupportedOperationException(); }

    @Override
    public Optional<Emprestimo> buscarPorId(Long id) {
        try (Connection c = ConnectionFactory.get()) {
            return buscarPorIdInterno(c, id);
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar empréstimo", e);
        }
    }

    private Optional<Emprestimo> buscarPorIdInterno(Connection c, Long id) throws SQLException {
        String sql = baseQuery() + " WHERE e.id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public List<Emprestimo> listarTodos() {
        List<Emprestimo> out = new ArrayList<>();
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(baseQuery() + " ORDER BY e.data_emprestimo DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException ex) {
            throw new DAOException("Erro ao listar empréstimos", ex);
        }
    }

    private static String baseQuery() {
        return "SELECT e.id, e.data_emprestimo, e.data_prevista, e.data_devolucao, e.multa, e.status, " +
               "       l.pessoa_id  AS l_id,  pl.nome  AS l_nome, pl.cpf AS l_cpf, l.matricula AS l_mat, l.data_cadastro AS l_dc, l.ativo AS l_at, " +
               "       f.pessoa_id  AS f_id,  pf.nome  AS f_nome, pf.cpf AS f_cpf, f.matricula AS f_mat, f.cargo AS f_cargo, f.salario AS f_sal, f.data_admissao AS f_da, " +
               "       c.id AS c_id, c.codigo_tombo AS c_tombo, c.estado AS c_estado, " +
               "       o.id AS o_id, o.titulo AS o_titulo, o.autor AS o_autor " +
               "FROM emprestimo e " +
               "JOIN leitor l ON l.pessoa_id = e.leitor_id JOIN pessoa pl ON pl.id = l.pessoa_id " +
               "JOIN funcionario f ON f.pessoa_id = e.funcionario_id JOIN pessoa pf ON pf.id = f.pessoa_id " +
               "JOIN copia c ON c.id = e.copia_id JOIN obra o ON o.id = c.obra_id ";
    }

    private static Emprestimo map(ResultSet rs) throws SQLException {
        Leitor leitor = new Leitor(rs.getLong("l_id"), rs.getString("l_nome"), rs.getString("l_cpf"),
                null, null, rs.getString("l_mat"), rs.getDate("l_dc").toLocalDate(), rs.getBoolean("l_at"));
        Funcionario func = new Funcionario(rs.getLong("f_id"), rs.getString("f_nome"), rs.getString("f_cpf"),
                null, null, rs.getString("f_mat"), rs.getString("f_cargo"), rs.getBigDecimal("f_sal"),
                rs.getDate("f_da").toLocalDate());
        Obra obra = new Obra(rs.getLong("o_id"), rs.getString("o_titulo"), rs.getString("o_autor"),
                null, null, null, null);
        Copia copia = new Copia(rs.getLong("c_id"), obra, rs.getString("c_tombo"),
                Copia.Estado.valueOf(rs.getString("c_estado")));
        Date dev = rs.getDate("data_devolucao");
        return new Emprestimo(rs.getLong("id"), leitor, copia, func,
                rs.getDate("data_emprestimo").toLocalDate(),
                rs.getDate("data_prevista").toLocalDate(),
                dev != null ? dev.toLocalDate() : null,
                rs.getBigDecimal("multa"),
                Emprestimo.Status.valueOf(rs.getString("status")));
    }
}
