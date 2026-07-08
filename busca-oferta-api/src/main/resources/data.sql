-- Inserir Usuários
INSERT INTO usuarios (nome, email, senha) VALUES ('Davi Carvalho', 'davicfg@gmail.com', '123456');
INSERT INTO usuarios (nome, email, senha) VALUES ('Miguel Xavier', 'miguel@gmail.com', '654321');

-- 1. Cria os Supermercados primeiro (eles não herdam do framework, então não têm as colunas novas)
INSERT INTO supermercados (id, nome) VALUES (1, 'Supermercado A');

-- 2. Cria TODOS os Produtos atualizados com as colunas do framework
INSERT INTO produtos (id, nome, categoria, data_processamentoia, nome_arquivo_origem) 
VALUES (1, 'Arroz Parboilizado 5kg', 'ALIMENTOS', CURRENT_TIMESTAMP, 'carga_inicial_script');

INSERT INTO produtos (id, nome, categoria, data_processamentoia, nome_arquivo_origem) 
VALUES (2, 'Feijão Carioca 1kg', 'ALIMENTOS', CURRENT_TIMESTAMP, 'carga_inicial_script');

-- 3. Só agora insere os preços, referenciando os IDs que foram criados com sucesso acima
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) 
VALUES (1, 1, 25.90, '2026-04-20', '2026-04-30');

INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) 
VALUES (2, 1, 8.50, '2026-04-20', '2026-04-30');

ALTER TABLE produtos ALTER COLUMN id RESTART WITH 100;
ALTER TABLE supermercados ALTER COLUMN id RESTART WITH 100;
ALTER TABLE precos ALTER COLUMN id RESTART WITH 100;