package ru.sirius.account.ui.partner;

import java.sql.SQLException;
import java.util.ArrayList;
import ru.sirius.account.db.PartnerService;
import ru.sirius.account.model.entity.Partner;
import ru.sirius.account.ui.utils.multispan.AttributiveCellTableModel;
import ru.sirius.account.ui.utils.multispan.DefaultCellAttribute;


public class RBPartnerController {

    private AttributiveCellTableModel model;
    private DefaultCellAttribute attribute;
    private ArrayList<Partner> partners = new ArrayList<>();
    private ArrayList<Partner> deleted;

    
    RBPartnerController(AttributiveCellTableModel model) {
        
        this.model = model;

        this.model.addColumn("ФИО");
        this.model.addColumn("Телефон");
        this.model.addColumn("E-mail");

        this.attribute = (DefaultCellAttribute) model.getCellAttribute();
    }

    void initialize() throws SQLException{
        partners = PartnerService.readPartner();        
        deleted = PartnerService.readHiddenPartner();
        for( Partner partner : partners){
            model.addRow(new Object[]{partner.getFio(), partner.getPhone(), partner.getEmail()});
        }
    }
        
    public void createPartner(Partner partner) throws SQLException {
        PartnerService.createPartner(partner);
        partners.add(partner);
        model.addRow(new Object[]{partner.getFio(), partner.getPhone(), partner.getEmail()});
    }

    public void updatePartner(Partner partner) throws SQLException{
        PartnerService.updatePartner(partner);
        int position = partners.indexOf(partner);
        model.removeRow(position);
        model.insertRow(position, new Object[]{partner.getFio(), partner.getPhone(), partner.getEmail()});
    }

    public void deletePartner(Partner partner, boolean showDeleted) throws SQLException{
        PartnerService.deletePartner(partner);
        int position = partners.indexOf(partner);
        partners.remove(position);
        model.removeRow(position);
        deleted.add(partner);
    }
    
    public void restorePartner(int position) throws SQLException{
        if( position < 1 || position > partners.size() + deleted.size() ){
            return;
        }        
        
        Partner partner = deleted.remove(position - partners.size() - 1);
        PartnerService.restorePartner(partner);
        partners.add(partner);
        model.addRow(new Object[]{partner.getFio(), partner.getPhone(), partner.getEmail()});       
    }
    
    public Partner getPartner(int index) {
        return index >= 0 && index < partners.size() ?  partners.get(index) : null;
    }
    
    

}
