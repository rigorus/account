
create sequence seq_id start with 1 increment by 1 cache 1;

create table category(
    category_id int not null,
    category_name varchar(400),  
    weight int not null,  
    constraint category__pk primary key (category_id)
);

create table article(
    article_id int not null,
    full_name varchar(1000), 
    short_name varchar(400),
    price decimal(10,2),
    description varchar(4000),
    category_id int,
    weight int not null,
    constraint article__pk primary key (article_id),
    constraint article__full_name__uk unique key (full_name),
    constraint article__category__fk foreign key (category_id) references category(category_id)    
);

create table template(
    template_id int not null,
    template_name varchar(200) not null,
    description varchar(4000),
    constraint template__pk primary key (template_id),
    constraint template__name__uk unique key (template_name)
);

create table template_position(
    template_id int not null,
    article_id int not null,
    constraint template_position__pk primary key (template_id, article_id),
    constraint template_position__template__fk foreign key (template_id) references template(template_id) 
    constraint template_position__article__fk foreign key (article_id) references article(article_id) 
);


create table partner(
    partner_id int not null,
    fio varchar(500),
    email varchar(200),
    phone varchar(20),
    template_id int,
    deleted boolean not null default false,
    constraint partner__pk primary key (partner_id),
    constraint partner__fio__uk unique key (fio),
    constraint partner__template__fk foreign key (template_id) references template(template_id)
);

create table payment(
    payment_id int not null,
    partner_id int not null,
    payment_date timestamp not null default current_timestamp,
    amount decimal(10,2) not null,   
    description varchar(4000) not null,
    constraint payment__pk primary key (payment_id),
    constraint payment__partner__fk foreign key (partner_id) references partner(partner_id)
);

create table expenses(
    expenses_id int not null,
    expenses_date date not null default today,
    amount decimal(10,2) not null,   
    description varchar(4000) not null,
    constraint expenses__pk primary key (expenses_id)
);

create table invoice(
    invoice_id int not null,
    partner_id int not null,
    invoice_date date not null default today,
    description varchar(4000),
    constraint invoice__pk primary key (invoice_id),
    constraint invoice__partner__fk foreign key (partner_id) references partner(partner_id)
);

create table invoice_position(
    invoice_id int not null,
    article_id int not null,
    quantity int not null,
    price decimal(10,2) not null,
    constraint invoice_position__pk primary key (invoice_id, article_id),
    constraint invoice_position__invoice__fk foreign key (invoice_id) references invoice(invoice_id),
    constraint invoice_position__article__fk foreign key (article_id) references article(article_id) 
);


create table delivery(
    delivery_id int not null,
    delivery_date date not null default today,
    description varchar(4000),
    constraint delivery__pk primary key (delivery_id)
);

create table delivery_position(
    delivery_id int not null,
    article_id int not null,
    quantity int not null,
    price decimal(10,2) not null,
    is_closed boolean not null default false,
    constraint delivery_position__pk primary key (delivery_id, article_id),
    constraint delivery_position__delivery__fk foreign key (delivery_id) references delivery(delivery_id),
    constraint delivery_position__article__fk foreign key (article_id) references article(article_id) 
);
