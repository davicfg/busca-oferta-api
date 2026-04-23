# Guia de Testes via CURL - Busca Oferta API

Este guia contém comandos `curl` para validar as funcionalidades implementadas nos Casos de Uso **UC04 (Consulta de Catálogo)** e **UC12 (Histórico de Preços)**.

## 📋 Pré-requisitos
- A aplicação deve estar rodando em `http://localhost:8080`.
- Certifique-se de ter o `curl` instalado no seu terminal.

---

## 🏗️ 1. Preparação de Dados (Massa de Teste)
Antes de testar os filtros e o histórico, certifique-se de que existem produtos e supermercados cadastrados.

### Criar um Supermercado
```bash
curl -X POST http://localhost:8080/supermercados \
     -H "Content-Type: application/json" \
     -d '{
       "nome": "Supermercado Central",
       "endereco": "Rua das Flores, 123"
     }'
```

### Criar um Produto
```bash
curl -X POST http://localhost:8080/produtos \
     -H "Content-Type: application/json" \
     -d '{
       "nome": "Arroz Integral 5kg",
       "categoria": "ALIMENTOS"
     }'
```

### Cadastrar Preços (Histórico)
*Execute estes 3 comandos em sequência para gerar um histórico para o Produto ID 1:*

**Preço Antigo:**
```bash
curl -X POST http://localhost:8080/precos \
     -H "Content-Type: application/json" \
     -d '{"produtoId": 1, "supermercadoId": 1, "valor": 15.50, "dataInicio": "2026-01-01", "dataFim": "2026-01-31"}'
```

**Preço Intermediário:**
```bash
curl -X POST http://localhost:8080/precos \
     -H "Content-Type: application/json" \
     -d '{"produtoId": 1, "supermercadoId": 1, "valor": 18.90, "dataInicio": "2026-02-01", "dataFim": "2026-02-28"}'
```

**Preço Atual:**
```bash
curl -X POST http://localhost:8080/precos \
     -H "Content-Type: application/json" \
     -d '{"produtoId": 1, "supermercadoId": 1, "valor": 22.00, "dataInicio": "2026-04-01", "dataFim": "2026-05-01"}'
```

---

## 🔍 2. UC04 - Consultar Catálogo de Ofertas (Filtros Avançados)

### Listar todas as ofertas
```bash
curl -X GET "http://localhost:8080/precos"
```

### Filtrar por Nome do Produto
```bash
curl -X GET "http://localhost:8080/precos?nome=Arroz"
```

### Filtrar por Categoria e Faixa de Preço
```bash
curl -X GET "http://localhost:8080/precos?categoria=ALIMENTOS&precoMin=10.00&precoMax=30.00"
```

### Filtrar por Supermercado
```bash
curl -X GET "http://localhost:8080/precos?supermercado=Central"
```

### Filtrar por Ofertas Válidas Hoje
```bash
curl -X GET "http://localhost:8080/precos?dataReferencia=2026-04-23"
```

---

## 📈 3. UC12 - Histórico de Preços

### Consultar Evolução de Preços de um Produto
*Este comando deve retornar os preços do mais recente para o mais antigo.*
```bash
curl -X GET "http://localhost:8080/monitoramento/historico/1"
```

### Testar Produto Inexistente (Erro 404)
```bash
curl -X GET "http://localhost:8080/monitoramento/historico/999"
```

---

## 🛠️ Dicas Úteis
- Para visualizar o JSON formatado no terminal Linux, adicione ` | jq` ao final do comando (se tiver o `jq` instalado).
- Se estiver no Windows PowerShell, use `Invoke-RestMethod` ou mantenha o `curl.exe` se disponível.
