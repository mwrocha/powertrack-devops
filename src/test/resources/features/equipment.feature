# language: pt

Funcionalidade: Gerenciamento de Equipamentos
  Como usuário autenticado do PowerTrack
  Quero gerenciar equipamentos industriais
  Para monitorar o consumo energético e contribuir com práticas ESG

  Contexto:
    Dado que o sistema está rodando
    E que estou autenticado como "admin" com senha "123"

  Cenário: Listar equipamentos com sucesso
    Quando eu faço uma requisição GET para "/api/equipment"
    Então o status da resposta deve ser 200
    E a resposta deve ser uma lista JSON
    E o schema da resposta deve ser válido para "equipment-list"

  Cenário: Criar um novo equipamento com sucesso
    Quando eu faço uma requisição POST para "/api/equipment" com o corpo:
      """
      {
        "name": "Chiller Teste BDD",
        "location": "Sala de Testes"
      }
      """
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "id"
    E a resposta deve conter o campo "name" com valor "Chiller Teste BDD"
    E a resposta deve conter o campo "location" com valor "Sala de Testes"
    E o schema da resposta deve ser válido para "equipment"

  Cenário: Tentar listar equipamentos sem autenticação
    Dado que não estou autenticado
    Quando eu faço uma requisição GET para "/api/equipment"
    Então o status da resposta deve ser 401
