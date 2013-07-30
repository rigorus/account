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
    
    

}
