package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.model.Obra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObraDAO implements GenericDAO<Obra, Long> {

    @Override
    public Obra inserir(Obra o) {
        String sql = "INSERT INTO obra(titulo,autor,editora,ano,isbn,categoria) VALUES (?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getTitulo()); ps.setString(2, o.getAutor());
            ps.setString(3, o.getEditora());
            if (o.getAno() != null) ps.setInt(4, o.getAno()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, o.getIsbn()); ps.setString(6, o.getCategoria());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) o.setId(rs.getLong(1)); }
            return o;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir obra", e);
        }
    }

    @Override
    public void atualizar(Obra o) {
        String sql = "UPDATE obra SET titulo=?, autor=?, editora=?, ano=?, isbn=?, categoria=? WHERE id=?";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, o.getTitulo()); ps.setString(2, o.getAutor());
            ps.setString(3, o.getEditora());
            if (o.getAno() != null) ps.setInt(4, o.getAno()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, o.getIsbn()); ps.setString(6, o.getCategoria());
            ps.setLong(7, o.getId()); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar obra", e);
        }
    }

    @Override
    public void remover(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM obra WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover obra", e);
        }
    }

    @Override
    public Optional<Obra> buscarPorId(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM obra WHERE id=?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar obra", e);
        }
    }

    @Override
    public List<Obra> listarTodos() {
        List<Obra> out = new ArrayList<>();
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM obra ORDER BY titulo");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar obras", e);
        }
    }

    static Obra map(ResultSet rs) throws SQLException {
        return new Obra(
                rs.getLong("id"), rs.getString("titulo"), rs.getString("autor"),
                rs.getString("editora"), (Integer) rs.getObject("ano"),
                rs.getString("isbn"), rs.getString("categoria")
        );
    }
}
