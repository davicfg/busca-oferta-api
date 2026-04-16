# Funcionalidades - Busca Oferta API

## Versão Atual (Simplificada)

### API Endpoints Implementados

#### Produtos
- ✅ **POST /produtos** - Criar novo produto
- ✅ **GET /produtos** - Listar todos os produtos (com filtro opcional por categoria)
- ✅ **GET /produtos/{id}** - Buscar produto por ID

#### Preços
- ✅ **POST /precos** - Criar novo preço para um produto
- ✅ **GET /precos/{id}** - Buscar preço por ID
- ✅ **GET /precos/produto/{produtoId}** - Listar todos os preços de um produto
- ✅ **GET /precos** - Listar todos os preços do sistema

### Categorias de Produtos
- ✅ ALIMENTOS
- ✅ BEBIDAS
- ✅ LIMPEZA
- ✅ HIGIENE_PESSOAL
- ✅ ELETRODOMESTICOS
- ✅ ELETRONICOS
- ✅ OUTROS

### Relacionamentos
- ✅ Produto → Preco (1:N com cascade delete)
- ✅ Histórico de preços com datas de validade

---

## Versão Futura (Planejado)

### Entidades a Adicionar
- ⏳ **Usuário** - Suporte a múltiplos usuários (Admin, Cliente)
- ⏳ **Marca** - Gerenciamento de marcas de produtos
- ⏳ **Unidade** - Unidades de medida (kg, L, ml, etc.)
- ⏳ **Loja** - Lojas/supermercados (se necessário suporte multi-store)
- ⏳ **Oferta** - Ofertas específicas por loja (se necessário)

### Features Planejadas

#### Admin Web
- Gerenciar Produtos (CRUD)
- Gerenciar Preços (CRUD com histórico)
- Gerenciar Marcas (CRUD)
- Gerenciar Unidades (CRUD)
- Gerenciar Categorias (Read-only, pré-definidas)
- Consultar histórico de preços
- Gerenciar Lojas (CRUD) - opcional

#### API para Clientes
- ⏳ Consultar produtos com preços vigentes
- ⏳ Favoritar produtos
- ⏳ Gerenciar favoritos (CRUD)
- ⏳ Receber notificações de mudanças de preço em produtos favoritos
- ⏳ Filtrar produtos por múltiplos critérios (categoria, marca, range de preço)

#### Sistema de Notificações
- ⏳ Monitoramento de preços
- ⏳ Alertas de produtos favoritos
- ⏳ Notificações de promoções

---

## Tecnologia Atual
- Spring Boot
- Spring Data JPA
- PostgreSQL (ou H2 para testes)
- Lombok
- Maven

## Próximas Prioridades
1. Implementar autenticação e autorização (JWT)
2. Adicionar testes unitários e integração
3. Implementar tratamento de exceções customizadas
4. Adicionar validação nos DTOs
5. Integração com encartes/ofertas de supermercados

