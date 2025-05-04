---

# Design e Arquitetura de Software

## Aula 26/02/2025
**Livro: Engenharia de Software Moderna - Capítulo 7**  
[Link para o capítulo](https://engsoftmoderna.info/cap7.html)

Quando falamos em arquitetura de software, estamos lidando com o planejamento de alto nível de um sistema. O foco vai além da organização de classes individuais e passa a considerar unidades maiores, como pacotes, componentes, módulos, subsistemas, camadas ou serviços. A arquitetura não se limita a decisões de alto nível, mas também abrange a organização de elementos mais próximos do código, como pacotes, classes, banco de dados, dispositivos, casos de uso e outros.

- **Pacote**: Agrupamento de elementos que trabalham juntos para um propósito comum. É a forma como os arquivos são organizados e interagem entre si.
- **Componentes de Software**: Bibliotecas ou ferramentas reutilizáveis que evitam a necessidade de programar tudo do zero.
- **Módulos**: Aplicações separadas que se comunicam por meio de interfaces (APIs).
- **Serviços**: Camadas que contêm lógica importante para realizar tarefas específicas.
- **Interface**: Meio de comunicação entre componentes de software (ex.: APIs).
- **Camadas**: Divisões como "front-end", "back-end" e "banco de dados".

---

## Aula 27/02/2025
Os padrões arquiteturais ajudam a organizar sistemas de software em um nível mais alto, definindo módulos principais e suas relações. Essas relações podem, por exemplo, restringir ou permitir que um módulo utilize os serviços de outro. É comum combinar diferentes padrões arquiteturais para atender às necessidades do projeto.

- **Arquitetura em Camadas**: Uma das mais tradicionais, separa responsabilidades entre front-end, back-end e banco de dados.
- **Model-View-Controller (MVC)**:
  - **Model**: Representa os dados persistidos.
  - **View**: Responsável por desenhar a interface.
  - **Controller**: Gerencia a lógica e a interação entre a View e o Model.
  - O objetivo do MVC é separar responsabilidades, evitando que a lógica fique concentrada na View ou no Controller.
- **Microsserviços**: Partes do software que realizam tarefas específicas, geralmente expostas por APIs REST. Mesmo microsserviços podem adotar camadas, mostrando que diferentes arquiteturas podem coexistir.
- **Monolito**: O oposto dos microsserviços. Todo o código está em um único repositório, utilizando uma única tecnologia, com deploy e execução como um único sistema.

---

## Aula 05/03/2025
**Padrão vs Estilo Arquitetural**  
- **Padrão**: Solução para problemas específicos (ex.: MVC).  
- **Estilo**: Organização geral do código ou projeto.

- **Big Ball of Mud**: Falta de padrões arquiteturais, resultando em um sistema desorganizado.
- **Arquitetura em Camadas**:
  - **Camada de Apresentação**: Interface gráfica.
  - **Camada de Lógica de Negócio**: Centraliza regras e escalabilidade.
  - **Camada de Persistência**: Gerencia o banco de dados.

---

## Aula 06/03/2025
**Artigo: Who Needs an Architect?**  
[Link para o artigo](https://martinfowler.com/ieeeSoftware/whoNeedsArchitect.pdf)

- **O que é arquitetura?**  
  É um entendimento compartilhado entre os desenvolvedores sobre como o sistema é dividido em componentes e como eles se comunicam.

- **Tipos de Arquitetos**:
  - **Arquiteto "Matrix"**: Centraliza decisões e tenta impor regras rígidas.
  - **Arquiteto Ideal**: Colabora com a equipe, monitora o sistema e resolve problemas antes que eles aconteçam. Atua como um guia, explicando o sistema para todos os envolvidos.

---

## Aula 03/04/2025
**Filas (Queues)**  
- Funcionam no modelo FIFO (First In, First Out).  
- Permitem desacoplar componentes temporais da aplicação.  
- Acumulam mensagens em caso de falhas.  
  - **Producer**: Gera mensagens.  
  - **Consumer**: Consome mensagens.

---

## Aula 09/04/2025
**Fundamentos da Arquitetura de Software - Capítulo 4**  

- **Backup**: Mais barato, mas lento para restaurar.  
- **Réplica**: Recuperação rápida, porém mais cara e sujeita a desincronização.

**Características da Arquitetura**:
- **Operacionais**: Desempenho, escalabilidade, elasticidade, disponibilidade e confiabilidade.  
- **Estruturais**: Modularidade, acoplamento controlado, código legível.  
- **Transversais**: Restrições externas, como requisitos legais.

**Trade-offs**: Decisões arquiteturais sempre envolvem compromissos. Ex.: Aumentar a segurança pode impactar o desempenho.

---

## Aula 23/04/2025
**Débito Técnico**  
O custo de decisões rápidas e mal planejadas no desenvolvimento.

---

## Aula 24/04/2025
**Estilo de Arquitetura em Camadas**  
- **Lei de Conway**: A estrutura do software reflete a organização da empresa.  
- Idealmente, cada camada deve se comunicar apenas com a adjacente.  
  - Exemplo: Apresentação → Comercial → Persistência → Banco de Dados.  
- Com o crescimento do software, é comum que essa separação se deteriore, prejudicando o sistema.

---
