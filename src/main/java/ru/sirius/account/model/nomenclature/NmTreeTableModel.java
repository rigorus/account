
package ru.sirius.account.model.nomenclature;

import java.util.Enumeration;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import ru.sirius.account.model.nomenclature.NmNode;


public class NmTreeTableModel extends AbstractTreeTableModel{

    private NmNode realRoot;
    private NmNode visibleRoot;         

    public NmTreeTableModel(NmNode root) {                
        super();
        realRoot = root;
        visibleRoot = root;
    }    
    
    @Override
    public Object getRoot() {
        return visibleRoot;
    }
    
    public void setRoot(NmNode node){
        
        if (node.identical(realRoot)) {
            visibleRoot = realRoot;
        }else{
            visibleRoot =  findIdentityNode(node, realRoot);
        }
    }       
    
    public NmNode findIdentityNode(NmNode node, NmNode root){
        
        Enumeration<NmNode> enumeration = root.children();
        while(enumeration.hasMoreElements()){
            root = enumeration.nextElement();
            if( node.identical(root) ){
                return root;
            }else if(!root.isLeaf()){
                findIdentityNode(node, root);                
            }
        }
        
        return null;        
    }
    
    @Override
    public boolean isLeaf(Object node) {
        NmNode treenode = (NmNode) node;
        return treenode.isLeaf();
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(Object object, int columnIndex) {
        
        NmNode node = (NmNode)object;
        
        switch(columnIndex){
            case 0: 
                return node.getProperty("name");
            case 1:
                return node.getProperty("quantity");
            default:
                return "Unknown";
        }        
    }

    @Override
    public Object getChild(Object parent, int index) {
        NmNode node = (NmNode) parent;
        return node.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        NmNode node = (NmNode) parent;
        return node.getChildCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        NmNode node = (NmNode) parent;
        return node.getIndex((NmNode) child);
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Наименование";
            case 1:
                return "Количество";
            default:
                return "Unknown";
        }
    }
}
