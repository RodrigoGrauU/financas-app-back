create table carteiras (
   id bigint not null auto_increment,
   descricao varchar(255),
   nome varchar(255),
   usuario_id bigint,
   primary key (id)
) engine=InnoDB default charset=UTF8;

create table categoria_transacao (
    id bigint not null auto_increment,
    descricao varchar(255),
    nome varchar(255),
    usuario_id bigint,
    primary key (id)
) engine=InnoDB default charset=UTF8;


create table transacoes (
    id bigint not null auto_increment,
    data_alteracao datetime(6),
    data_criacao datetime(6),
    data_transacao date,
    descricao varchar(255),
    tipo_transacao enum ('CREDITO','DEBITO'),
    valor decimal(38,2),
    carteira_id bigint not null,
    categoria_transacao_id bigint,
    primary key (id)
) engine=InnoDB default charset=UTF8;


create table usuario (
    id bigint not null auto_increment,
    email varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB default charset=UTF8;

alter table carteiras add constraint FKo5av4n69vdvlcdl3kd037qsar foreign key (usuario_id) references usuario (id);
alter table transacoes add constraint FK83968mfpcxlvkws05ixc0a9s1 foreign key (carteira_id) references carteiras (id);
alter table transacoes add constraint FK8uq2mi509ii5yixsqxlo5wfsw foreign key (categoria_transacao_id) references categoria_transacao (id);
