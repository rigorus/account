
package ru.sirius.account.ui.goods;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import ru.sirius.overheads.model.entity.Position;


public class GoodsTreeTableModel extends AbstractTreeTableModel{

    private DefaultMutableTreeNode realRoot;
    private DefaultMutableTreeNode visibleRoot;         

    public GoodsTreeTableModel(DefaultMutableTreeNode root) {                
        super();
        realRoot = root;
        visibleRoot = root;
    }    
    
    @Override
    public Object getRoot() {
        return visibleRoot;
    }

    public void setRoot(DefaultMutableTreeNode node) {
        
        if ( identical(node,realRoot) ) {
            visibleRoot = realRoot;
        } else {
            visibleRoot = findIdentityNode(node, realRoot);
        }
    }
    
    public static boolean identical(DefaultMutableTreeNode node1, DefaultMutableTreeNode node2){
        Position node1Position = (Position) node1.getUserObject();
        Position node2Position = (Position) node2.getUserObject();

        return node1Position.getId() == node2Position.getId();        
    }
    
    public DefaultMutableTreeNode findIdentityNode(DefaultMutableTreeNode node, DefaultMutableTreeNode root){
        
        Enumeration<DefaultMutableTreeNode> enumeration = root.children();
        while(enumeration.hasMoreElements()){
            root = enumeration.nextElement();
            if( identical(node, root) ){
                return root;
            }else if(!root.isLeaf()){
                findIdentityNode(node, root);                
            }
        }
        
        return null;        
    }
    
    @Override
    public boolean isLeaf(Object node) {
        DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
        return treenode.isLeaf();
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(Object object, int columnIndex) {
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)object;
        Position position = (Position) node.getUserObject();
        
        switch(columnIndex){
            case 0: 
                return position.getName();
            case 1:
                return position.getId();
            default:
                return "Unknown";
        }        
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

    @Override
    public Object getChild(Object parent, int index) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent;
        return node.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent;
        return node.getChildCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent;
        return node.getIndex((DefaultMutableTreeNode)child);
    }
}
