package ru.sirius.account.db;

import java.sql.Connection;
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

}
