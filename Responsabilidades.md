## Roteiro de Autoria (versão enxuta)

### Gastão — Clientes e Categorias
- **Adicionar nova coluna**
  1. Alterar entidade em `src/main/java/br/pucpr/lanchonete/model/Cliente.java` ou `Categoria.java`.
  2. Adicionar o campo correspondente nos DTOs em `src/main/java/br/pucpr/lanchonete/dto/ClienteDTO.java` ou `CategoriaDTO.java`, incluindo validações.
  3. Ajustar os serviços em `src/main/java/br/pucpr/lanchonete/service/ClienteService.java` ou `CategoriaService.java` para popular o novo valor.
  4. Verificar se o controller (`src/main/java/br/pucpr/lanchonete/controller/ClienteController.java` ou `CategoriaController.java`) e os testes precisam de payload novo.
  5. Executar `.\mvnw.cmd test` para confirmar que tudo continua passando.

- **Testes sob responsabilidade**
  - `src/test/java/br/pucpr/lanchonete/controller/ClienteControllerTest.java`: checa se `POST /api/v1/clientes` responde `201 Created` para entrada válida.
  - `src/test/java/br/pucpr/lanchonete/service/ClienteServiceTest.java`: valida salvamento (gera ID/e-mail correto) e busca por ID retornando DTO preenchido.

### Caio — Produtos e Endereços
- **Adicionar nova coluna**
  1. Alterar entidades em `src/main/java/br/pucpr/lanchonete/model/Produto.java` e `Endereco.java`.
  2. Propagar para os DTOs `ProdutoDTO.java` e `EnderecoDTO.java`.
  3. Ajustar serviços em `ProdutoService.java` e `EnderecoService.java` (relação com categoria/cliente).
  4. Atualizar controllers (`ProdutoController.java`, `EnderecoController.java`) e payloads de exemplo nos testes.
  5. Rodar `.\mvnw.cmd test` para garantir que os fluxos continuam válidos.

- **Testes sob responsabilidade**
  - `src/test/java/br/pucpr/lanchonete/controller/ProdutoControllerTest.java`: garante `201 Created` ao criar produto via MockMvc.
  - `src/test/java/br/pucpr/lanchonete/service/ProdutoServiceTest.java`: valida persistência, vínculo com categoria e regras de preço.

### Caetano — Pedidos e Itens de Pedido
- **Adicionar nova coluna**
  1. Alterar `src/main/java/br/pucpr/lanchonete/model/Pedido.java` e `ItemPedido.java`.
  2. Propagar para `src/main/java/br/pucpr/lanchonete/dto/PedidoRequestDTO.java`, `PedidoResponseDTO.java` e `ItemPedidoDTO.java`.
  3. Ajustar serviços correspondentes em `PedidoService.java` e `ItemPedidoService.java` (ligações com cliente/produto e cálculos).
  4. Revisar controllers (`PedidoController.java`, `ItemPedidoController.java`) e payloads nos testes.
  5. Rodar a bateria de testes após ajustes.
  
- **Testes sob responsabilidade**
  - `src/test/java/br/pucpr/lanchonete/controller/PedidoControllerTest.java`: valida criação de pedidos via MockMvc.
  - `src/test/java/br/pucpr/lanchonete/service/PedidoServiceTest.java`: assegura montagem de itens, associações e cálculo do total.

### Dicas Gerais
- Sempre gerar token com `POST /api/v1/auth/login` e usar na aba **Authorize** do Swagger.
- Usar `admin@lanchonete.com / admin123` (login padrão).
- Revisar `GlobalExceptionHandler` para responder perguntas sobre tratamento de erro.
- Confirmar, antes da apresentação, que `.\mvnw.cmd test` passa sem falhas.
- `.\mvnw.cmd spring-boot:run` comando para rodar projeto.
- Rodar testes específicos quando necessário:
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.controller.ClienteControllerTest test`
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.service.ClienteServiceTest test`
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.controller.ProdutoControllerTest test`
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.service.ProdutoServiceTest test`
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.controller.PedidoControllerTest test`
  - `.\mvnw.cmd -Dtest=br.pucpr.lanchonete.service.PedidoServiceTest test`
