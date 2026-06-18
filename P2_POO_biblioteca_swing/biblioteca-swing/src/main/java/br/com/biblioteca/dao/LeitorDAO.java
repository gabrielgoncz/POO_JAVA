package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.model.Leitor;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeitorDAO implements GenericDAO<Leitor, Long> {

    @Override
    public Leitor inserir(Leitor l) {
        String sqlPessoa = "INSERT INTO pessoa(nome,cpf,email,telefone,tipo) VALUES (?,?,?,?, 'LEITOR')";
        String sqlLeitor = "INSERT INTO leitor(pessoa_id,matricula,data_cadastro,ativo) VALUES (?,?,?,?)";
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, l.getNome());
                ps.setString(2, l.getCpf());
                ps.setString(3, l.getEmail());
                ps.setString(4, l.getTelefone());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) l.setId(rs.getLong(1));
                }
            }
            try (PreparedStatement ps = c.prepareStatement(sqlLeitor)) {
                ps.setLong(1, l.getId());
                ps.setString(2, l.getMatricula());
                ps.setDate(3, Date.valueOf(l.getDataCadastro() == null ? LocalDate.now() : l.getDataCadastro()));
                ps.setBoolean(4, l.isAtivo());
                ps.executeUpdate();
            }
            c.commit();
            return l;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir leitor", e);
        }
    }

    @Override
    public void atualizar(Leitor l) {
        String s1 = "UPDATE pessoa SET nome=?, cpf=?, email=?, telefone=? WHERE id=?";
        String s2 = "UPDATE leitor SET matricula=?, ativo=? WHERE pessoa_id=?";
        try (Connection c = ConnectionFactory.get()) {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(s1)) {
                ps.setString(1, l.getNome()); ps.setString(2, l.getCpf());
                ps.setString(3, l.getEmail()); ps.setString(4, l.getTelefone());
                ps.setLong(5, l.getId()); ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement(s2)) {
                ps.setString(1, l.getMatricula()); ps.setBoolean(2, l.isAtivo());
                ps.setLong(3, l.getId()); ps.executeUpdate();
            }
            c.commit();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar leitor", e);
        }
    }

    @Override
    public void remover(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM pessoa WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover leitor", e);
        }
    }

    @Override
    public Optional<Leitor> buscarPorId(Long id) {
        String sql = "SELECT p.*, l.matricula, l.data_cadastro, l.ativo " +
                     "FROM pessoa p JOIN leitor l ON l.pessoa_id = p.id WHERE p.id = ?";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar leitor", e);
        }
    }

    @Override
    public List<Leitor> listarTodos() {
        String sql = "SELECT p.*, l.matricula, l.data_cadastro, l.ativo " +
                     "FROM pessoa p JOIN leitor l ON l.pessoa_id = p.id ORDER BY p.nome";
        List<Leitor> out = new ArrayList<>();
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar leitores", e);
        }
    }

    static Leitor map(ResultSet rs) throws SQLException {
        return new Leitor(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("telefone"),
                rs.getString("matricula"),
                rs.getDate("data_cadastro").toLocalDate(),
                rs.getBoolean("ativo")
        );
    }
}
