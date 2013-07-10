/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.account.model.nomenclature;


import java.util.Map;
import ru.sirius.account.model.Article;
import ru.sirius.account.model.Group;

//Отсюда идет вся информация об артиклах и группах !!!
public interface NmTreePrototype {

    public Map<Integer,Group> getGroups();

    public Map<Integer, Article> getArticles();
    
    public Group getGroup(int id);

    public Article getArticle(int id);


}
