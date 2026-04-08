Usuários
  - id
  - e-mail
  - nome
  - telefone

Produtos
  - is
  - nome
  - marca_id
  - unidade_id
  - categoria_id

<!-- unidade de venda do produto, kg, 1l, 500g, 500ml... -->
Unidade
  - id
  - nome

<!-- Marca dos produtos, mauricia, omu, -->
Marca
  - id
  - nome


<!-- Açouge, lavanderia, cereais... -->
Categoria
  - id
  - nome

Precos
 - id
 - produto_id
 - loja_id
 - inicio_validade
 - fim_validade

Supermercado
  - id
  - nome
  
Lojas
  - id
  - nome
  - endereco
  - horario_funcionamento
  
Categorias
  - id
  - nome

Produtos_favoritos
  - id
  - usuario_id
  - produto_id
