package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.model.Copia;
import br.com.biblioteca.model.Copia.Estado;
import br.com.biblioteca.model.Obra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CopiaDAO implements GenericDAO<Copia, Long> {

    @Override
    public Copia inserir(Copia c) {
        String sql = "INSERT INTO copia(obra_id, codigo_tombo, estado) VALUES (?,?,?)";
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getObra().getId());
            ps.setString(2, c.getCodigoTombo());
            ps.setString(3, c.getEstado().name());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) c.setId(rs.getLong(1)); }
            return c;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir cópia", e);
        }
    }

    @Override
    public void atualizar(Copia c) {
        String sql = "UPDATE copia SET codigo_tombo=?, estado=? WHERE id=?";
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCodigoTombo());
            ps.setString(2, c.getEstado().name());
            ps.setLong(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar cópia", e);
        }
    }

    public void atualizarEstado(Long copiaId, Estado estado) {
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement("UPDATE copia SET estado=? WHERE id=?")) {
            ps.setString(1, estado.name()); ps.setLong(2, copiaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar estado da cópia", e);
        }
    }

    @Override
    public void remover(Long id) {
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement("DELETE FROM copia WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover cópia", e);
        }
    }

    @Override
    public Optional<Copia> buscarPorId(Long id) {
        String sql = "SELECT c.*, o.titulo, o.autor, o.editora, o.ano, o.isbn, o.categoria " +
                     "FROM copia c JOIN obra o ON o.id = c.obra_id WHERE c.id=?";
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar cópia", e);
        }
    }

    @Override
    public List<Copia> listarTodos() {
        String sql = "SELECT c.*, o.titulo, o.autor, o.editora, o.ano, o.isbn, o.categoria " +
                     "FROM copia c JOIN obra o ON o.id = c.obra_id ORDER BY o.titulo, c.codigo_tombo";
        List<Copia> out = new ArrayList<>();
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar cópias", e);
        }
    }

    public List<Copia> listarDisponiveisPorObra(Long obraId) {
        String sql = "SELECT c.*, o.titulo, o.autor, o.editora, o.ano, o.isbn, o.categoria " +
                     "FROM copia c JOIN obra o ON o.id = c.obra_id " +
                     "WHERE c.obra_id=? AND c.estado='DISPONIVEL' ORDER BY c.codigo_tombo";
        List<Copia> out = new ArrayList<>();
        try (Connection con = ConnectionFactory.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, obraId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(map(rs));
            }
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar cópias disponíveis", e);
        }
    }

    static Copia map(ResultSet rs) throws SQLException {
        Obra obra = new Obra(
                rs.getLong("obra_id"), rs.getString("titulo"), rs.getString("autor"),
                rs.getString("editora"), (Integer) rs.getObject("ano"),
                rs.getString("isbn"), rs.getString("categoria"));
        return new Copia(rs.getLong("id"), obra, rs.getString("codigo_tombo"),
                Estado.valueOf(rs.getString("estado")));
    }
}
