package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metier.SingletonConnection;
import metier.entities.Moto;

public class MotoDaoImpl implements IMotoDao {

    @Override
    public Moto save(Moto m) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO MOTO(NOM_MOTO, PRIX) VALUES(?, ?)");
            ps.setString(1, m.getNomMoto());
            ps.setDouble(2, m.getPrix());
            ps.executeUpdate();
            PreparedStatement ps2 = conn.prepareStatement("SELECT MAX(ID_MOTO) as MAX_ID FROM MOTO");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                m.setIdMoto(rs.getLong("MAX_ID"));
            }
            ps.close();
            ps2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public List<Moto> motosParMC(String mc) {
        List<Moto> motos = new ArrayList<>();
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM MOTO WHERE NOM_MOTO LIKE ?");
            ps.setString(1, "%" + mc + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Moto m = new Moto();
                m.setIdMoto(rs.getLong("ID_MOTO"));
                m.setNomMoto(rs.getString("NOM_MOTO"));
                m.setPrix(rs.getDouble("PRIX"));
                motos.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return motos;
    }

    @Override
    public Moto getMoto(Long id) {
        Connection conn = SingletonConnection.getConnection();
        Moto moto = new Moto();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM MOTO WHERE ID_MOTO = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                moto.setIdMoto(rs.getLong("ID_MOTO"));
                moto.setNomMoto(rs.getString("NOM_MOTO"));
                moto.setPrix(rs.getDouble("PRIX"));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moto;
    }

    @Override
    public Moto updateMoto(Moto moto) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE MOTO SET NOM_MOTO=?, PRIX=? WHERE ID_MOTO=?");
            ps.setString(1, moto.getNomMoto());
            ps.setDouble(2, moto.getPrix());
            ps.setLong(3, moto.getIdMoto());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moto;
    }

    @Override
    public void deleteMoto(Long id) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM MOTO WHERE ID_MOTO = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}