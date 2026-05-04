# language: pt

Funcionalidade: Gerenciamento de Leituras de Medidores
  Como usuário autenticado do PowerTrack
  Quero registrar leituras de consumo energético
  Para monitorar e controlar o uso de energia dos equipamentos industriais

  Contexto:
    Dado que o sistema está rodando
    E que estou autenticado como "admin" com senha "123"

  Cenário: Listar leituras de medidores com sucesso
    Quando eu faço uma requisição GET para "/api/meter-readings"
    Então o status da resposta deve ser 200
    E a resposta deve ser uma lista JSON
    E o schema da resposta deve ser válido para "meter-reading-list"

  Cenário: Criar uma nova leitura de medidor com sucesso
    Dado que existe um equipamento cadastrado
    E que existe um medidor cadastrado
    Quando eu crio uma leitura de medidor com energyKwh 45.5
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "id"
    E a resposta deve conter o campo "energyKwh"
    E o schema da resposta deve ser válido para "meter-reading"

  Cenário: Listar leituras por equipamento
    Dado que existe um equipamento cadastrado
    Quando eu faço uma requisição GET para "/api/meter-readings/by-equipment/{equipmentId}"
    Então o status da resposta deve ser 200
    E a resposta deve ser uma lista JSON

  Cenário: Tentar listar leituras sem autenticação
    Dado que não estou autenticado
    Quando eu faço uma requisição GET para "/api/meter-readings"
    Então o status da resposta deve ser 401
