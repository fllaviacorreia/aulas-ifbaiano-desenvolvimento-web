create table if not exists client(
	id serial,
    nome varchar(200) not null,
    email varchar(250) unique not null,
    telefone varchar(16) not null,

    primary key (id)
);