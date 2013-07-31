package ru.sirius.account.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ru.sirius.account.model.entity.Partner;


public class PartnerService {

    public static ArrayList<Partner> readPartner() throws SQLException {
        return readPartner("SELECT * FROM partner WHERE deleted = FALSE ORDER by fio");
    }
    
    public static ArrayList<Partner> readHiddenPartner() throws SQLException{
        return readPartner("SELECT * FROM partner WHERE deleted = TRUE ORDER by fio");
    }
    
    private static ArrayList<Partner> readPartner(String SQL) throws SQLException{
        
        Connection connection = DbUtils.getConnection();

        ArrayList<Partner> partners = new ArrayList<>();

        try (Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SQL)) {

            while (rs.next()) {
                Partner partner = new Partner();
                partner.setId(rs.getInt("partner_id"));
                partner.setFio(rs.getString("fio"));
                partner.setPhone(rs.getString("phone"));
                partner.setEmail(rs.getString("email"));
                partners.add(partner);
            }
            return partners;
        }
    } 

    
    public static void createPartner(Partner partner) throws SQLException {

        Connection connection = DbUtils.getConnection();
        partner.setId(DbUtils.getNextValue());
        String sql = "INSERT INTO partner(partner_id, fio, phone, email) VALUES(?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, partner.getId());
            statement.setString(2, partner.getFio());
            statement.setString(3, partner.getPhone());
            statement.setString(4, partner.getEmail());
            statement.executeUpdate();
        }
        connection.commit();
    }

    public static void updatePartner(Partner partner) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String sql = "UPDATE partner SET fio = ?, phone = ?, email = ? WHERE partner_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, partner.getFio());
            statement.setString(2, partner.getPhone());
            statement.setString(3, partner.getEmail());
            statement.setInt(4, partner.getId());
            statement.executeUpdate();

            connection.commit();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public static void deletePartner(Partner partner) throws SQLException{
        updateDeleted(partner, true);
    }
    
        
    public static void restorePartner(Partner partner) throws SQLException{
        updateDeleted(partner, false);
    }
    
        public static void updateDeleted(Partner partner, boolean deleted) throws SQLException{
                
        Connection connection = DbUtils.getConnection();
        String sql;
        
        try (Statement statement = connection.createStatement()) {

            sql = String.format("UPDATE partner SET deleted = %1$s WHERE partner_id = %2$d",
                    deleted ? "TRUE" : "FALSE", partner.getId());
            statement.executeUpdate(sql);
            
            connection.commit();
            
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

}
