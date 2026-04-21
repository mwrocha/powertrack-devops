// ============================================================
// PowerTrack - MongoDB Init Script
// Cria usuários e dados iniciais ao subir o container
// ============================================================

db = db.getSiblingDB('powertrack');

// Limpa antes de inserir para evitar duplicatas
db.users.deleteMany({});
db.equipments.deleteMany({});
db.energy_meters.deleteMany({});

db.users.insertMany([
  { username: "admin", password: "123", role: "ADMIN", },
  { username: "user", password: "123", role: "USER", }
]);

db.equipments.insertMany([
  { name: "Chiller Principal",  location: "Climatização" },
  { name: "Compressor 75",      location: "Compressão"   },
  { name: "Iluminação Externa", location: "Pátio"        },
  { name: "Bomba de Água",      location: "Subsolo"      },
  { name: "Servidor TI",        location: "Data Center"  }
]);

db.energy_meters.insertMany([
  { serialNumber: "SN1001", installationDate: new Date() },
  { serialNumber: "SN1002", installationDate: new Date() },
  { serialNumber: "SN1003", installationDate: new Date() },
  { serialNumber: "SN1004", installationDate: new Date() },
  { serialNumber: "SN1005", installationDate: new Date() }
]);

print("✅ PowerTrack: dados iniciais inseridos com sucesso!");