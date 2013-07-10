-- схема номенклатура для централизованного ведения

-- create schema nomenclature;

-- create table nomenclature.article(
--     article_id int auto_increment(1,1) primary key,
--     full_name varchar(1000) not null,
--     classification_id int not null,
--     enabled boolean not null default = true,
--     short_name varchar(500),
--     description varchar(4000),
--     comment varchar(2000),
--     constraint acticle__uk unique key (full_name),
--     constraint acticle__classification__fk foreign key (classification_id) references nomenclature.classification(classification_id)
-- );

-- create table nomenclature.classification(
--     classification_id int auto_increment(1,1) primary key,    
--     parent_id int,   
--     classification_name varchar(200),
--     breadth_index int not null,
--     depth_index int not null,
--     comment varchar(2000),
--     constraint classification_tree__parent__fk foreign key (parent_id) references nomenclature.classification(classification_id)
-- );


-- create table nomenclature.acticle_classification(
--     article_id int not null,
--     classification_id int not null,
--     constraint acticle_classification__fk primary key (article_id, classification_id),
--     constraint acticle_classification__article__fk foreign key (article_id) references nomenclature.article(article_id),
--     constraint acticle_classification__classification__fk foreign key (classification_id) references nomenclature.classification(classification_id)
-- );

-- 
-- update nomenclature.ARTICLE a set a.classification_id = 
-- (select classification_id from nomenclature.acticle_classification ac 
--  where ac.ARTICLE_ID = a.ARTICLE_ID);

-- select ARTICLE_ID, count(1) from nomenclature.acticle_classification  group by ARTICLE_ID having count(1) > 1;


select * from NOMENCLATURE.ARTICLE