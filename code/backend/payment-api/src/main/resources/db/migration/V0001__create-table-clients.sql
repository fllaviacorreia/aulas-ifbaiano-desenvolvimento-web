create table if not exists client(
	id serial primary key,
    nome varchar(200) not null,
    cpf varchar(14) unique not null,
    telefone varchar(16) not null,
);