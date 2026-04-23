-- Inserir Usuários
INSERT INTO usuarios (nome, email, senha) VALUES ('Davi Carvalho', 'davicfg@gmail.com', '123456');
INSERT INTO usuarios (nome, email, senha) VALUES ('Miguel Xavier', 'miguel@gmail.com', '654321');

-- Inserir Supermercados
INSERT INTO supermercados (nome, endereco, horario_funcionamento) VALUES ('Supermercado BH - Savassi', 'Rua Fernandes Tourinho, 123', 'Seg-Sab: 08:00-21:00');
INSERT INTO supermercados (nome, endereco, horario_funcionamento) VALUES ('Carrefour - Centro', 'Av. Afonso Pena, 456', 'Seg-Dom: 07:00-22:00');
INSERT INTO supermercados (nome, endereco, horario_funcionamento) VALUES ('Verdemar - Sion', 'Av. Nossa Sra. do Carmo, 789', 'Seg-Sab: 07:00-22:00');

-- Inserir Produtos
INSERT INTO produtos (nome, categoria) VALUES ('Arroz Parboilizado 5kg', 'ALIMENTOS');
INSERT INTO produtos (nome, categoria) VALUES ('Feijão Carioca 1kg', 'ALIMENTOS');
INSERT INTO produtos (nome, categoria) VALUES ('Cerveja Heineken 330ml', 'BEBIDAS');
INSERT INTO produtos (nome, categoria) VALUES ('Detergente Líquido', 'LIMPEZA');
INSERT INTO produtos (nome, categoria) VALUES ('Sabonete Dove', 'HIGIENE_PESSOAL');

-- Inserir Preços (Ofertas)
-- Ofertas Ativas (Hoje é 23/04/2026)
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (1, 1, 24.90, '2026-04-20', '2026-04-30'); -- Arroz no BH
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (1, 2, 22.50, '2026-04-22', '2026-04-28'); -- Arroz no Carrefour (Mais barato!)
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (2, 1, 8.50, '2026-04-20', '2026-04-30'); -- Feijão no BH
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (3, 3, 5.99, '2026-04-23', '2026-04-25'); -- Cerveja no Verdemar

-- Ofertas Expiradas
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (4, 2, 1.99, '2026-03-01', '2026-03-15'); -- Sabão expirado

-- Ofertas Futuras
INSERT INTO precos (produto_id, supermercado_id, valor, data_inicio, data_fim) VALUES (5, 1, 3.50, '2026-05-01', '2026-05-05'); -- Sabonete oferta futura
