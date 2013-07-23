/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.account.model.nomenclature;



public class NmTreeBuilder {
//    
//    private NmTreePrototype prototype;
//
//    public NmTreeBuilder(NmTreePrototype prototype) {
//        this.prototype = prototype;
//    }
//        
//    
//    public NmNode buildGroupTree(){
//
//        NmSimpleNode root = null;        
//        Map<Integer, NmSimpleNode> nodes = new HashMap<>();
//
//        for (Category group : prototype.getGroups().values()) {
//            NmSimpleNode node = new NmSimpleNode(NmNode.NodeType.GROUP, group.getId());
//            nodes.put(group.getId(), node);
//        }
//
//        for (NmSimpleNode node : nodes.values()) {
//            Category group = prototype.getGroups().get(node.getId());
//            if (group.getParentId() == 0) {
//                root = node;
//            } else {
//                node.parent = nodes.get(group.getParentId());
//                nodes.get(group.getParentId()).addChild(node);
//            }
//        }
//        
//        return root;
//    }
//    
//    public NmNode buidComplexTree(){
//
//        NmSimpleNode root = null;
//        Map<Integer, NmSimpleNode> nodes = new HashMap<>();
//        
//        for (Category group : prototype.getGroups().values()) {
//            NmSimpleNode node = new NmSimpleNode(NmNode.NodeType.GROUP, group.getId());
//            nodes.put(group.getId(), node);
//
//            for(Article article : prototype.getArticles().values()){
//                if( article.getClassificationId() == node.id){
//                    NmSimpleNode child = new NmSimpleNode(NmNode.NodeType.ARTICLE, article.getId());
//                    child.parent = node;
//                    node.addChild(child);
//                }
//            }
//        }
//
//        for (NmSimpleNode node : nodes.values()) {            
//            Category group = prototype.getGroups().get(node.getId());
//            if (group.getParentId() == 0) {
//                root = node;
//            } else {
//                nodes.get(group.getParentId()).addChild(node);
//            }
//        }
//        
//        return root;
//    }
//    
//    public NmNode buidSelectionTree() {
//        
//        for (Category group : prototype.getGroups().values()) {
//            if( !group.hasParent() ){
//                NmSimpleNode node = new NmSimpleNode(NmNode.NodeType.GROUP, group.getId());
//                return node;
//            }
//        } 
//        return null;
//    }
//
//    public void mergeSelectionTree(NmNode root, NmNode node) {
//        
//        NmSimpleNode to = (NmSimpleNode) root;
//        NmSimpleNode from = (NmSimpleNode)node.getSubTree();
//        
//        mergeNode(to, from);
//    }
//
//    private void mergeNode(NmSimpleNode to, NmSimpleNode from) {
//
//        if (from.getChildCount() == 0) {
//            return;
//        }
//
//        for (NmSimpleNode node : from.children) {
//            NmSimpleNode eq = to.getChild(node.type, node.id);
//            if (eq != null) {
//                mergeNode(eq, node);
//            } else {
//                to.addChild(node);
//            }
//        }
//    }
//    
//    private void addChild(NmSimpleNode to, NmSimpleNode from) {
//        NmSimpleNode child = new NmSimpleNode(from.type, from.id, to, null);
//        to.addChild(child);
//        if (!from.isLeaf()) {
//            for (NmSimpleNode node : from.children) {
//                addChild(child, node);
//            }
//        }
//
//    }
//   
//    
//    private class NmSimpleNode implements NmNode {
//
//        private final Enumeration<NmSimpleNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
//        private NmNode.NodeType type;
//        private int id;
//        private NmSimpleNode parent;
//        private ArrayList<NmSimpleNode> children;
//
//        public NmSimpleNode(NmNode.NodeType type, int id) {
//            this.type = type;
//            this.id = id;
//        }
//
//        public NmSimpleNode(NmNode.NodeType type, int id, NmSimpleNode parent, ArrayList<NmSimpleNode> children) {
//            this.type = type;
//            this.id = id;
//            this.parent = parent;
//            this.children = children;
//        }
//
//        @Override
//        public NmSimpleNode getParent() {
//            return parent;
//        }
//
//        @Override
//        public NmNode.NodeType getType() {
//            return type;
//        }
//        
//        //TODO  возможно это лишнее -> для отображения вполне достаточно типа !!!
//        @Override
//        public int getId() {
//            return id;
//        }
//
//        @Override
//        public String getProperty(String propertyName) {
//            switch(propertyName){
//                case "name":
//                    return type == NodeType.GROUP ? 
//                            prototype.getGroup(id).getName() : 
//                            prototype.getArticle(id).getFullName();
//                case "quantity":
//                    return String.valueOf(id);
//                default:
//                    return "Unknown";                        
//            }
//        }
//        
//        
//
//        //TODO завести comparator !!!
//        protected void addChild(NmSimpleNode child) {
//            if (children == null) {
//                children = new ArrayList<>();
//            }
//            children.add(child);
//        }
//
//        @Override
//        public Enumeration<NmSimpleNode> children() {
//            if (children == null) {
//                return EMPTY_ENUMERATION;
//            } else {
//
//                return new Enumeration<NmSimpleNode>() {
//                    Iterator<NmSimpleNode> iterator = children.iterator();
//
//                    @Override
//                    public boolean hasMoreElements() {
//                        return iterator.hasNext();
//                    }
//
//                    @Override
//                    public NmSimpleNode nextElement() {
//                        return iterator.next();
//                    }
//                };
//            }
//        }
//
//        @Override
//        public NmSimpleNode getChildAt(int childIndex) {
//            return children.get(childIndex);
//        }
//
//        @Override
//        public int getChildCount() {
//            return children != null ? children.size() : 0;
//        }
//
//        public NmSimpleNode getChild(NodeType type, int id) {
//            if (children != null) {
//                for (NmSimpleNode node : children) {
//                    if (node.type == type && node.id == id) {
//                        return node;
//                    }
//                }
//            }
//            return null;
//        }
//        
//        @Override
//        public int getIndex(TreeNode node) {
//            if (node == null) {
//                throw new IllegalArgumentException("argument is null");
//            }
//
//            if (!isNodeChild(node)) {
//                return -1;
//            }
//            return children.indexOf(node);
//        }
//
//        @Override
//        public boolean getAllowsChildren() {
//            return true;
//        }
//
//        @Override
//        public boolean isLeaf() {
//            return children == null;
//        }
//
//        @Override
//        public String toString() {
//            return type == NmNode.NodeType.ARTICLE
//                    ? NomenclatureHelper.getARTICLES().get(id).getFullName()
//                    : NomenclatureHelper.getGROUPS().get(id).getName();
//        }
//
//        @Override
//        public boolean identical(NmNode node) {
//
//            if (node == null) {
//                return false;
//            }
//
//            return this.type == node.getType()
//                    && this.id == node.getId();     
//        }
//        
//        public boolean isNodeChild(TreeNode node) {
//            boolean retval;
//
//            if (node == null) {
//                retval = false;
//            } else {
//                if (getChildCount() == 0) {
//                    retval = false;
//                } else {
//                    retval = (node.getParent() == this);
//                }
//            }
//
//            return retval;
//        }
//
//        public NmSimpleNode getSubTree() {
//
//            if (this.parent == null) {
//                return this;
//            }
//            
//            NmSimpleNode node = this;
//            ArrayList<NmSimpleNode> parents = new ArrayList<>();
//            while (node.parent != null) {
//                parents.add(node.parent);
//                node = node.parent;
//            }
//            parents.add(node);
//            Collections.reverse(parents);
//
//            NmSimpleNode root = null;
//            NmSimpleNode parent = null;
//            for (int i = 0; i < parents.size(); ++i) {
//                node = parents.get(i);
//                NmSimpleNode clone = new NmSimpleNode(node.type, node.id, parent, null);
//                if (i == 0) {
//                    root = clone;
//                } else {
//                    parent.addChild(clone);
//                }
//                parent = clone;
//            }
//
//            parent.addChild(new NmSimpleNode(this.type, this.id, parent, this.children));
//            return root;
//        }
//    }
}
