/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sirius.account.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import ru.sirius.account.model.nomenclature.NmTreePrototype;
import java.util.List;
import java.util.Map;
import ru.sirius.account.db.NomenclatureProvider;
import ru.sirius.account.model.Article;
import ru.sirius.account.model.Group;


// Класс для работы с деревом артикулов -> редактирования ...  
// при каждом редактировании version увеличивается на 1
public class NomenclatureService {
    
    private List<Article> articles;
    private List<Group> groups;
    private NomenclatureTreePrototypeImp prototype;
    private long version;
    
    private NomenclatureService() {        
        articles = NomenclatureProvider.getArticles();
        groups = NomenclatureProvider.getGroups();
    }
    
    public static NomenclatureService getInstance() {
        return NomenclatureServiceHolder.INSTANCE;
    }
    
    private static class NomenclatureServiceHolder {
        private static final NomenclatureService INSTANCE = new NomenclatureService();
    }
    
    
    public NmTreePrototype getPrototype(){
        
        synchronized(this){
            if(prototype != null && prototype.version == version) return prototype;              
            prototype = new NomenclatureTreePrototypeImp(articles, groups, version);
        }
        
        prototype.makeTree();
            
        return prototype;                        
    }
    
    
    private static class NomenclatureTreePrototypeImp implements NmTreePrototype{

        private Map<Integer, Article> articles;
        private Map<Integer, Group> groups;
        private long version = 0;

        private NomenclatureTreePrototypeImp(List<Article> articles, List<Group> groups, long version ) {
        
            this.version = version;            
            this.articles = new HashMap<>();
            this.groups = new HashMap<>();
            
            for (Article article : articles) {
                this.articles.put(article.getId(), article.clone());
            }

            for (Group group : groups) {
                this.groups.put(group.getId(), group.clone());
            }
        }

        private void makeTree() {
            // Здесь уже создается само дерево !!! !!! !!!
        }

        
        
        // реализация самих методов интерфейса !!!
        
        //лучше переделать на enumenator + доступ к отдельной группе

        @Override
        public Map<Integer, Group> getGroups() {
            return Collections.unmodifiableMap(groups);
        }

        @Override
        public Map<Integer, Article> getArticles() {
            return articles;
        }
        
        @Override
        public Group getGroup(int id) {
            return groups.get(id);
        }

        @Override
        public Article getArticle(int id) {
            return articles.get(id);
        }        
    }
}
