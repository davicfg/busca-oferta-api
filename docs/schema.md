## Banco de Dados - Estrutura Simplificada

### Produtos
- id (Primary Key)
- nome (String, NOT NULL)
- categoria (Enum: ALIMENTOS, BEBIDAS, LIMPEZA, HIGIENE_PESSOAL, ELETRODOMESTICOS, ELETRONICOS, OUTROS)

### Precos
- id (Primary Key)
- produto_id (Foreign Key → Produtos, NOT NULL)
- valor (BigDecimal, NOT NULL) - Valor do produto
- data_inicio (LocalDate, NOT NULL) - Data de início da validade do preço
- data_fim (LocalDate, NOT NULL) - Data de fim da validade do preço

### Usuarios (Planejado para futuro)
- id (Primary Key)
- email (String, NOT NULL, UNIQUE)
- nome (String, NOT NULL)
- telefone (String)

## Relacionamentos

- **Produtos → Precos**: 1:N (Um produto pode ter múltiplos preços com datas de validade diferentes)
- **Cascade Delete**: Quando um produto é deletado, todos os seus preços são deletados automaticamente
- **Orphan Removal**: Preços órfãos são removidos automaticamente

## Observações

- A arquitetura foi simplificada para focar no catálogo de produtos e histórico de preços
- As entidades Loja e Oferta foram removidas (podem ser re-adicionadas conforme necessário)
- Suporte a múltiplas marcas e unidades pode ser adicionado no futuro
- A entidade Usuario é reservada para funcionalidades futuras como favoritos e monitoramento

