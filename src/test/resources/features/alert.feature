# language: pt

Funcionalidade: Gerenciamento de Alertas ESG
  Como administrador do PowerTrack
  Quero gerenciar alertas de consumo energético anormal
  Para identificar desperdícios e garantir conformidade com metas ESG

  Contexto:
    Dado que o sistema está rodando
    E que estou autenticado como "admin" com senha "123"

  Cenário: Listar alertas com sucesso
    Quando eu faço uma requisição GET para "/api/alerts"
    Então o status da resposta deve ser 200
    E a resposta deve ser uma lista JSON
    E o schema da resposta deve ser válido para "alert-list"

  Cenário: Criar um novo alerta com sucesso
    Dado que existe um equipamento cadastrado
    Quando eu crio um alerta com mensagem "Consumo acima do limite ESG" e tipo "WARNING"
    Então o status da resposta deve ser 201
    E a resposta deve conter o campo "id"
    E a resposta deve conter o campo "alertMessage" com valor "Consumo acima do limite ESG"
    E a resposta deve conter o campo "alertType" com valor "WARNING"
    E o schema da resposta deve ser válido para "alert"

  Cenário: Tentar criar alerta sem autenticação
    Dado que não estou autenticado
    Dado que existe um equipamento cadastrado
    Quando eu crio um alerta com mensagem "Teste" e tipo "INFO"
    Então o status da resposta deve ser 401

  Cenário: Tentar listar alertas sem autenticação
    Dado que não estou autenticado
    Quando eu faço uma requisição GET para "/api/alerts"
    Então o status da resposta deve ser 401
