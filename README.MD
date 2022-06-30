# Da Bagunça Para Hexagonal

### Contexto
Esta aplicação é responsável por gerenciar (cadastrar, alterar, consultar e descontinuar) os produtos de um e-commerce, porém foi desenvolvida às pressas, faltando regras importantes de negócio e sem nenhuma preocupação com as boas práticas. E o pior, todas as responsabilidades foram colocadas dentro de uma mesma camada.

### Objetivo
Utilizando os princípios **S.O.L.I.D** e **Arquitetura Hexagonal**, refatorar a aplicação de forma que ela se torne fácil de manter. Utilize para isso o artigo e os vídeos colocados nas [Referências](#referencias).

### Regras de negócios
* Um produto (entidade *Product*) contém as seguintes propriedades:
  * id: identificador único (estamos usando UUID)
  * name: nem necessita explicação :)
  * price: nem necessita explicação :)
  * quantity: quantidade em estoque
  * status: 
    * ENABLED: indica que o produto está ativo e pode ser comercializado
    * DISABLED: indica que o produto foi descontinuado e não deve ser comercializado
* Todo novo produto já tem seu identificador (*id*), nome (*name*) e preço (*price*) definidos. Porém deve ter sua quantidade (*quantity*) igual à zero e *status* igual à ENABLED
* Um produto só pode ser descontinuado (*status* igual à DISABLED) depois de vendido todo estoque (*quantity* igual à zero) 

### Referências
[Netflix Hexagonal Architecture](https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)

[SOLID fica FÁCIL com Essas Ilustrações - Filipe Deschamps](https://www.youtube.com/watch?v=6SfrO3D4dHM)

[Como criar e desacoplar camadas usando SOLID - Live - Rodrigo Branas](https://www.youtube.com/watch?v=tetVY6jmlnM)

### Extras
* Diagrama de classes da solução já em arquitetura hexagonal? Antes de implementar, é claro! É sempre bom relembrar **UML** da época da faculdade, pois nunca se sabe quando precisaremos utilizá-la!

### Dicas
* O Banco de Dados **H2** está com o console habilitado e pode ser acessado em *http://localhost:8080/h2-console*