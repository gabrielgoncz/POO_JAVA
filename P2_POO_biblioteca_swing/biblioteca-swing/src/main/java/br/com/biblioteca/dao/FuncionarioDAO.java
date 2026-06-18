package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO implements GenericDAO<Funcionario, Long> {

    @Override
    public Funcionario inserir(Funcionario f) {
        String s1 = "INSERT INTO pessoa(nome,cpf,email,telefone,tipo) VALUES (?,?,?,?, 'FUNCIONARIO')";
        String s2 = "INSERT INTO funcionario(pessoa_id,matricula,cargo,salario,data_admissao) VALUES (?,?,?,?,?)";
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(s1, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, f.getNome()); ps.setString(2, f.getCpf());
                ps.setString(3, f.getEmail()); ps.setString(4, f.getTelefone());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) f.setId(rs.getLong(1)); }
            }
            try (PreparedStatement ps = c.prepareStatement(s2)) {
                ps.setLong(1, f.getId()); ps.setString(2, f.getMatricula());
                ps.setString(3, f.getCargo()); ps.setBigDecimal(4, f.getSalario());
                ps.setDate(5, Date.valueOf(f.getDataAdmissao()));
                ps.executeUpdate();
            }
            c.commit();
            return f;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir funcionário", e);
        }
    }

    @Override
    public void atualizar(Funcionario f) {
        String s1 = "UPDATE pessoa SET nome=?, cpf=?, email=?, telefone=? WHERE id=?";
        String s2 = "UPDATE funcionario SET matricula=?, cargo=?, salario=? WHERE pessoa_id=?";
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(s1)) {
                ps.setString(1, f.getNome()); ps.setString(2, f.getCpf());
                ps.setString(3, f.getEmail()); ps.setString(4, f.getTelefone());
                ps.setLong(5, f.getId()); ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement(s2)) {
                ps.setString(1, f.getMatricula()); ps.setString(2, f.getCargo());
                ps.setBigDecimal(3, f.getSalario()); ps.setLong(4, f.getId());
                ps.executeUpdate();
            }
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar funcionário", e);
        }
    }

    @Override
    public void remover(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM pessoa WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover funcionário", e);
        }
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        String sql = "SELECT p.*, f.matricula, f.cargo, f.salario, f.data_admissao " +
                     "FROM pessoa p JOIN funcionario f ON f.pessoa_id = p.id WHERE p.id=?";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar funcionário", e);
        }
    }

    @Override
    public List<Funcionario> listarTodos() {
        String sql = "SELECT p.*, f.matricula, f.cargo, f.salario, f.data_admissao " +
                     "FROM pessoa p JOIN funcionario f ON f.pessoa_id = p.id ORDER BY p.nome";
        List<Funcionario> out = new ArrayList<>();
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar funcionários", e);
        }
    }

    static Funcionario map(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getLong("id"), rs.getString("nome"), rs.getString("cpf"),
                rs.getString("email"), rs.getString("telefone"),
                rs.getString("matricula"), rs.getString("cargo"),
                rs.getBigDecimal("salario"), rs.getDate("data_admissao").toLocalDate()
        );
    }
}
