# Casos de Uso - Busca Oferta API

## Versão Atual (Simplificada)

### Implementados ✅

#### UC01 - Cadastro de Produtos
- **Ator**: Admin da API
- **Descrição**: Criar um novo produto no catálogo
- **Endpoint**: POST /produtos
- **Parâmetros**: nome, categoria
- **Resultado**: Produto criado com ID

#### UC02 - Listar Produtos
- **Ator**: Qualquer usuário da API
- **Descrição**: Consultar todos os produtos ou filtrar por categoria
- **Endpoint**: GET /produtos?categoria={categoria}
- **Parâmetros**: categoria (opcional)
- **Resultado**: Lista de produtos

#### UC03 - Buscar Produto por ID
- **Ator**: Qualquer usuário da API
- **Descrição**: Obter detalhes completos de um produto
- **Endpoint**: GET /produtos/{id}
- **Resultado**: Detalhes do produto

#### UC04 - Cadastro de Preços
- **Ator**: Admin da API
- **Descrição**: Cadastrar um preço para um produto com período de validade
- **Endpoint**: POST /precos
- **Parâmetros**: produtoId, valor, dataInicio, dataFim
- **Resultado**: Preço criado com ID

#### UC05 - Listar Preços de um Produto
- **Ator**: Qualquer usuário da API
- **Descrição**: Consultar histórico de preços de um produto
- **Endpoint**: GET /precos/produto/{produtoId}
- **Resultado**: Lista de preços com datas de validade

#### UC06 - Listar Todos os Preços
- **Ator**: Admin da API
- **Descrição**: Consultar todos os preços do sistema
- **Endpoint**: GET /precos
- **Resultado**: Lista completa de preços

#### UC07 - Buscar Preço por ID
- **Ator**: Qualquer usuário da API
- **Descrição**: Obter detalhes de um preço específico
- **Endpoint**: GET /precos/{id}
- **Resultado**: Detalhes do preço

---

## Versão Futura (Planejado)

### UC08 - Cadastro de Usuário
- **Ator**: Novo usuário
- **Descrição**: Registrar na plataforma (Admin ou Cliente)
- **Status**: ⏳ Planejado
- **Endpoint**: POST /usuarios (quando implementado)

### UC09 - Autenticação e Autorização
- **Ator**: Usuário registrado
- **Descrição**: Realizar login e obter token de acesso
- **Status**: ⏳ Planejado
- **Endpoint**: POST /auth/login (quando implementado)

### UC10 - Gerenciar Favoritos
- **Ator**: Cliente autenticado
- **Descrição**: Adicionar/remover produtos dos favoritos
- **Status**: ⏳ Planejado
- **Endpoints**: 
  - POST /favoritos
  - DELETE /favoritos/{id}
  - GET /favoritos

### UC11 - Consultar Preços Vigentes
- **Ator**: Cliente da API
- **Descrição**: Obter preços ativos no período atual para um produto
- **Status**: ⏳ Planejado
- **Logica**: Filtrar preços onde hoje está entre dataInicio e dataFim

### UC12 - Sistema de Alerta de Preço Alvo
- **Ator**: Cliente autenticado
- **Descrição**: Receber alertas quando o preço de um produto favorito atinge um valor alvo
- **Status**: ⏳ Planejado
- **Relacionado**: Monitoramento de preços

### UC13 - Histórico de Preços
- **Ator**: Admin/Cliente
- **Descrição**: Visualizar evolução histórica de preços de um produto
- **Status**: ⏳ Planejado / Parcialmente Implementado
- **Nota**: Dados já persistidos, falta endpoint otimizado para análise

### UC14 - Gerenciar Marcas (Futuro)
- **Ator**: Admin
- **Descrição**: Criar, listar, atualizar e deletar marcas
- **Status**: ⏳ Planejado
- **Nota**: Será adicionado quando decidido sobre necessidade

### UC15 - Gerenciar Unidades (Futuro)
- **Ator**: Admin
- **Descrição**: Criar, listar, atualizar e deletar unidades de medida
- **Status**: ⏳ Planejado
- **Nota**: Será adicionado quando decidido sobre necessidade

### UC16 - Capturar e Processar Encartes
- **Ator**: Sistema automático
- **Descrição**: Integração com sistemas de encartes de supermercados
- **Status**: ⏳ Planejado
- **Nota**: Futura integração com APIs de supermercados

---

## Diagrama de Relacionamentos Simplificado

```
Produto (1)────────────(N) Preco
  ├─ id                  ├─ id
  ├─ nome                ├─ valor
  ├─ categoria           ├─ dataInicio
  └─ precos[]            ├─ dataFim
                         └─ produto_id
```

---

## Observações

- A arquitetura foi simplificada para focar no MVP (Catálogo de Produtos com Histórico de Preços)
- As entidades Loja e Oferta foram removidas para reduzir complexidade
- Podem ser re-adicionadas quando suporte multi-loja for necessário
- Prioridade atual: Testes, Validações, Tratamento de Exceções Customizadas
- Próximas prioridades: Autenticação, Monitoramento, Notificações
