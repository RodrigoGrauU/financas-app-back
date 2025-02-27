SET FOREIGN_KEY_CHECKS=0;

delete from carteiras;
delete from categoria_transacao;
delete from transacoes;
delete from authorities;
delete from usuarios;

insert into usuarios(id, email, username, password, ativo) values (1, 'testedata@data.com', 'root', '{noop}1234', 1);
insert into authorities(user_id, authority) values (1, 'ROLE_USER');
insert into categoria_transacao(id, descricao, nome, usuario_id) values (1, 'Gastos com Pets', 'Pets', 1);
insert into categoria_transacao(id, descricao, nome, usuario_id) values (2, 'Gastos com Pets', 'Supermercado', 1);

insert into carteiras(descricao, nome, usuario_id) VALUES ('Banco ABC', 'Banco ABC', 1);

insert into transacoes(data_alteracao, data_criacao, data_transacao, descricao, tipo_transacao, valor, carteira_id, categoria_transacao_id)
VALUES('2025-01-10', '2025-01-10', '2025-01-10', 'Gastos supermercado', 'DEBITO', 550.57, 1, 2);
insert into transacoes(data_alteracao, data_criacao, data_transacao, descricao, tipo_transacao, valor, carteira_id, categoria_transacao_id)
VALUES('2025-01-10', '2025-01-10', '2025-01-10', 'Areia Gatos 10Kg', 'DEBITO', 85.00, 1, 2);
