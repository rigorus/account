package ru.sirius.account.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import ru.sirius.account.db.GoodsProvider;
import ru.sirius.account.model.Article;
import ru.sirius.account.model.Group;
import ru.sirius.account.model.entity.Goods;
import ru.sirius.account.model.nomenclature.NmTreePrototype;


public class GoodsService {
    
    public static GoodsService getInstance() {
        return GoodsService.GoodsServiceHolder.INSTANCE;
    }
    
    private static class GoodsServiceHolder {
        private static final GoodsService INSTANCE = new GoodsService();
    }

    private List<Goods> goods;    
    private long version; 
        
    private GoodsService() {        
        goods = GoodsProvider.getGoods();
    }
        
    public void addGoods(Goods goods){
        
    }
    
    public void removeGoods(Goods goods){
        
    }
    
    public void replaceGoods(Goods goods, Goods parent){
        
    }
    
    public void replaceGoods(Collection<Goods> goods, Goods parent){
        
    }
    

//    
//    public NmTreePrototype getPrototype(){
//        
//        synchronized(this){
//            if(prototype != null && prototype.version == version) return prototype;              
//            prototype = new NomenclatureService.NomenclatureTreePrototypeImp(articles, groups, version);
//        }
//        
//        prototype.makeTree();
//            
//        return prototype;                        
//    }
//    
//    
//    private static class NomenclatureTreePrototypeImp implements NmTreePrototype{
//
//        private Map<Integer, Article> articles;
//        private Map<Integer, Group> groups;
//        private long version = 0;
//
//        private NomenclatureTreePrototypeImp(List<Article> articles, List<Group> groups, long version ) {
//        
//            this.version = version;            
//            this.articles = new HashMap<>();
//            this.groups = new HashMap<>();
//            
//            for (Article article : articles) {
//                this.articles.put(article.getId(), article.clone());
//            }
//
//            for (Group group : groups) {
//                this.groups.put(group.getId(), group.clone());
//            }
//        }
//
//        private void makeTree() {
//            // Здесь уже создается само дерево !!! !!! !!!
//        }
//
//        
//        
//        // реализация самих методов интерфейса !!!
//        
//        //лучше переделать на enumenator + доступ к отдельной группе
//
//        @Override
//        public Map<Integer, Group> getGroups() {
//            return Collections.unmodifiableMap(groups);
//        }
//
//        @Override
//        public Map<Integer, Article> getArticles() {
//            return articles;
//        }
//        
//        @Override
//        public Group getGroup(int id) {
//            return groups.get(id);
//        }
//
//        @Override
//        public Article getArticle(int id) {
//            return articles.get(id);
//        }        
//    }
}
