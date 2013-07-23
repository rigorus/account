package ru.sirius.account.service;

import java.util.ArrayList;
import java.util.Collection;
import ru.sirius.account.model.entity.Article;

/**
 * TODO иметь 2 списка товаров один для редактирования другой только для чтения !!!
 * @author MorozovIA
 */
public class GoodsService {
    
    public static GoodsService getInstance() {
        return GoodsService.GoodsServiceHolder.INSTANCE;
    }
    
    private static class GoodsServiceHolder {
        private static final GoodsService INSTANCE = new GoodsService();
    }

    private ArrayList<Article> goods;    
    private long version; 
        
    private GoodsService() {        
//        goods = GoodsProvider.getGoods();
    }
    
    public void getRoot(){
    }
        
    public void addGoods(Article goods){
        
    }
    
    public void removeGoods(Article goods){
        
    }
    
    public void replaceGoods(Article goods, Article parent){
        
    }
    
    public void replaceGoods(Collection<Article> goods, Article parent){
        
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
//        private Map<Integer, Category> groups;
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
//            for (Category group : groups) {
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
//        public Map<Integer, Category> getGroups() {
//            return Collections.unmodifiableMap(groups);
//        }
//
//        @Override
//        public Map<Integer, Article> getArticles() {
//            return articles;
//        }
//        
//        @Override
//        public Category getGroup(int id) {
//            return groups.get(id);
//        }
//
//        @Override
//        public Article getArticle(int id) {
//            return articles.get(id);
//        }        
//    }
}
