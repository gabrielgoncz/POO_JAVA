package br.com.biblioteca.dao;

import br.com.biblioteca.db.ConnectionFactory;
import br.com.biblioteca.exception.DAOException;
import br.com.biblioteca.model.Leitor;
import br.com.biblioteca.model.Obra;
import br.com.biblioteca.model.Reserva;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservaDAO implements GenericDAO<Reserva, Long> {

    @Override
    public Reserva inserir(Reserva r) {
        if (r.getDataReserva() == null) r.setDataReserva(LocalDate.now());
        if (r.getDataValidade() == null) r.setDataValidade(r.getDataReserva().plusDays(3));
        String sql = "INSERT INTO reserva(leitor_id,obra_id,data_reserva,data_validade,status) VALUES (?,?,?,?, 'ATIVA')";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getLeitor().getId());
            ps.setLong(2, r.getObra().getId());
            ps.setDate(3, Date.valueOf(r.getDataReserva()));
            ps.setDate(4, Date.valueOf(r.getDataValidade()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) r.setId(rs.getLong(1)); }
            return r;
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir reserva", e);
        }
    }

    public void cancelar(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("UPDATE reserva SET status='CANCELADA' WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao cancelar reserva", e);
        }
    }

    @Override public void atualizar(Reserva r) { throw new UnsupportedOperationException(); }

    @Override
    public void remover(Long id) {
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement("DELETE FROM reserva WHERE id=?")) {
            ps.setLong(1, id); ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover reserva", e);
        }
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        String sql = baseQuery() + " WHERE r.id=?";
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar reserva", e);
        }
    }

    @Override
    public List<Reserva> listarTodos() {
        List<Reserva> out = new ArrayList<>();
        try (Connection c = ConnectionFactory.get();
             PreparedStatement ps = c.prepareStatement(baseQuery() + " ORDER BY r.data_reserva DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
            return out;
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar reservas", e);
        }
    }

    private static String baseQuery() {
        return "SELECT r.*, " +
               " pl.id AS l_id, pl.nome AS l_nome, pl.cpf AS l_cpf, l.matricula AS l_mat, l.data_cadastro AS l_dc, l.ativo AS l_at, " +
               " o.id AS o_id, o.titulo AS o_titulo, o.autor AS o_autor " +
               "FROM reserva r " +
               "JOIN leitor l ON l.pessoa_id = r.leitor_id JOIN pessoa pl ON pl.id = l.pessoa_id " +
               "JOIN obra o ON o.id = r.obra_id ";
    }

    private static Reserva map(ResultSet rs) throws SQLException {
        Leitor l = new Leitor(rs.getLong("l_id"), rs.getString("l_nome"), rs.getString("l_cpf"),
                null, null, rs.getString("l_mat"), rs.getDate("l_dc").toLocalDate(), rs.getBoolean("l_at"));
        Obra o = new Obra(rs.getLong("o_id"), rs.getString("o_titulo"), rs.getString("o_autor"),
                null, null, null, null);
        return new Reserva(rs.getLong("id"), l, o,
                rs.getDate("data_reserva").toLocalDate(),
                rs.getDate("data_validade").toLocalDate(),
                Reserva.Status.valueOf(rs.getString("status")));
    }
}
