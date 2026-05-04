# language: pt

Funcionalidade: Gerenciamento de Medidores de Energia
  Como usuário autenticado do PowerTrack
  Quero gerenciar medidores de energia
  Para registrar e monitorar o consumo energético dos equipamentos

  Contexto:
    Dado que o sistema está rodando
    E que estou autenticado como "admin" com senha "123"

  Cenário: Listar medidores de energia com sucesso
    Quando eu faço uma requisição GET para "/api/energy-meter"
    Então o status da resposta deve ser 200
    E a resposta deve ser uma lista JSON
    E o schema da resposta deve ser válido para "energy-meter-list"

  Cenário: Criar um novo medidor de energia com sucesso
    Quando eu faço uma requisição POST para "/api/energy-meter" com o corpo:
      """
      {
        "serialNumber": "SN-BDD-001",
        "installationDate": "2025-01-15"
      }
      """
    Então o status da resposta deve ser 200
    E a resposta deve conter o campo "id"
    E a resposta deve conter o campo "serialNumber" com valor "SN-BDD-001"
    E o schema da resposta deve ser válido para "energy-meter"

  Cenário: Tentar listar medidores sem autenticação
    Dado que não estou autenticado
    Quando eu faço uma requisição GET para "/api/energy-meter"
    Então o status da resposta deve ser 401
