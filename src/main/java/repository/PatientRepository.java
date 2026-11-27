
package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import connexion.Connexion;
import model.PatientModel;

public class PatientRepository {

    public List<PatientModel> findAll() throws SQLException {

        List<PatientModel> patients = new ArrayList<>();

        String sql = "SELECT patient.id, patient.nom, patient.date_naissance, "
                   + "patient.lieu_naissance, patient.adresse, patient.id_sexe, "
                   + "sexe.genre AS sexe FROM patient "
                   + "LEFT JOIN sexe ON patient.id_sexe = sexe.id";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(mapPatient(rs));
            }
        }

        return patients;
    }

    public PatientModel findById(int id) throws SQLException {
        String sql = "SELECT patient.id, patient.nom, patient.date_naissance, "
                   + "patient.lieu_naissance, patient.adresse, patient.id_sexe, "
                   + "sexe.genre AS sexe FROM patient "
                   + "LEFT JOIN sexe ON patient.id_sexe = sexe.id "
                   + "WHERE patient.id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapPatient(rs) : null;
            }
        }
    }

    public Map<Integer, String> findAllSexes() throws SQLException {
        Map<Integer, String> sexes = new LinkedHashMap<>();
        String sql = "SELECT id, genre FROM sexe ORDER BY id";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sexes.put(rs.getInt("id"), rs.getString("genre"));
            }
        }
        return sexes;
    }

    public void create(PatientModel patient) throws SQLException {
        String sql = "INSERT INTO patient "
                   + "(nom, date_naissance, lieu_naissance, adresse, id_sexe) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            remplirParametres(ps, patient);
            ps.executeUpdate();
        }
    }

    public boolean update(PatientModel patient) throws SQLException {
        String sql = "UPDATE patient SET nom = ?, date_naissance = ?, "
                   + "lieu_naissance = ?, adresse = ?, id_sexe = ? WHERE id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            remplirParametres(ps, patient);
            ps.setInt(6, patient.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (Connection con = Connexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private PatientModel mapPatient(ResultSet rs) throws SQLException {
        PatientModel patient = new PatientModel();
        java.sql.Date date = rs.getDate("date_naissance");

        patient.setId(rs.getInt("id"));
        patient.setNom(rs.getString("nom"));
        patient.setDate_naissance(date == null ? null : date.toLocalDate());
        patient.setLieu_naissance(rs.getString("lieu_naissance"));
        patient.setAdresse(rs.getString("adresse"));
        patient.setId_sexe(rs.getInt("id_sexe"));
        patient.setSexe(rs.getString("sexe"));
        return patient;
    }

    private void remplirParametres(PreparedStatement ps, PatientModel patient)
            throws SQLException {
        ps.setString(1, patient.getNom());
        ps.setDate(2, java.sql.Date.valueOf(patient.getDate_naissance()));
        ps.setString(3, patient.getLieu_naissance());
        ps.setString(4, patient.getAdresse());
        ps.setInt(5, patient.getId_sexe());
    }
}
