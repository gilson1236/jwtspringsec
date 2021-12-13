create table usuarios (
	id int not null primary key,
    login varchar(50) not null unique,
	senha varchar(100) not null
);
insert into usuarios (id, login, senha)
values (1, 'admin', 'admin'),
	   (2, 'comum', 'comum');
create table tarefas (
	id serial not null primary key,
	descricao varchar(500) not null,
	criada_em timestamp with time zone not null,
	usuario_id int not null,

	constraint fk_tarefas_usuario
	  foreign key (usuario_id)
	  references usuarios (id)
);
insert into tarefas (descricao, created_at, usuario_id)
values ('Excluir o usuário "samuel".', '2005-03-18 11:59:58', 1),
		('Excluir o usuário "admin".', '2005-03-18 11:59:59', 2);
-- select * from usuarios;
-- select * from tarefas;