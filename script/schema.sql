
create table goods(
    goods_id int not null auto_increment(1,1),
    full_name varchar(1000), 
    short_name varchar(400), -- заполняется для групп
    price decimal(10,2),
    description varchar(4000),
    is_deleted boolean not null default false,
    is_group boolean not null default false,
    parent_id int,
    depth_index int not null,
    breadth_index int not null,    
    constraint goods__pk primary key (goods_id),
    constraint goods__full_name__uk unique key (full_name),
    constraint goods__parent__fk foreign key (parent_id) references goods(goods_id)
);

insert into goods(short_name,is_group,depth_index,breadth_index) values('Все наименования',true,1,1);

create table template(
    template_id int auto_increment(1,1) primary key,
    template_name varchar(200) not null,
    description varchar(4000),
    constraint template__pk primary key (template_id),
    constraint template__name__uk unique key (template_name)
);

create table template_position(
    template_id int not null,
    goods_id int not null,
    constraint template_position__pk primary key (template_id, goods_id),
    constraint template_position__template__fk foreign key (template_id) references template(template_id) 
    constraint template_position__goods__fk foreign key (goods_id) references goods(goods_id) 
);


create table partner(
    partner_id int auto_increment(1,1),
    fio varchar(500),
    email varchar(200),
    phone varchar(20),
    template_id int,
    constraint partner__pk primary key (partner_id),
    constraint partner__template__fk foreign key (template_id) references template(template_id)
);

create table payment(
    payment_id int not null auto_increment(1,1),
    partner_id int not null,
    payment_date timestamp not null default current_timestamp,
    amount decimal(10,2) not null,   
    description varchar(4000) not null,
    constraint payment__pk primary key (payment_id),
    constraint payment__partner__fk foreign key (partner_id) references partner(partner_id)
);

create table expenses(
    expenses_id int not null auto_increment(1,1),
    expenses_date date not null default today,
    amount decimal(10,2) not null,   
    description varchar(4000) not null,
    constraint expenses__pk primary key (expenses_id)
);

create table invoice(
    invoice_id int auto_increment(1,1) primary key,
    partner_id int not null,
    invoice_date date not null default today,
    description varchar(4000),
    constraint invoice__pk primary key (invoice_id),
    constraint invoice__partner__fk foreign key (partner_id) references partner(partner_id)
);

create table invoice_position(
    invoice_id int not null,
    goods_id int not null,
    quantity int not null,
    price decimal(10,2) not null,
    constraint invoice_position__pk primary key (invoice_id, goods_id),
    constraint invoice_position__invoice__fk foreign key (invoice_id) references invoice(invoice_id),
    constraint invoice_position__goods__fk foreign key (goods_id) references goods(goods_id) 
);


create table delivery(
    delivery_id int auto_increment(1,1) primary key,
    delivery_date date not null default today,
    description varchar(4000),
    constraint delivery__pk primary key (delivery_id)
);

create table delivery_position(
    delivery_id int not null,
    goods_id int not null,
    quantity int not null,
    price decimal(10,2) not null,
    is_closed boolean not null default false,
    constraint delivery_position__pk primary key (delivery_id, goods_id),
    constraint delivery_position__delivery__fk foreign key (delivery_id) references delivery(delivery_id),
    constraint delivery_position__goods__fk foreign key (goods_id) references goods(goods_id) 
);
