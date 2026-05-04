package br.com.powertrack.bdd.steps;

import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import br.com.powertrack.model.Equipment;
import br.com.powertrack.model.EnergyMeter;
import br.com.powertrack.repository.EquipmentRepository;
import br.com.powertrack.repository.EnergyMeterRepository;
import br.com.powertrack.repository.UserRepository;
import br.com.powertrack.model.User;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommonSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EnergyMeterRepository energyMeterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StepContext context;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "";

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("123");
            user.setRole("USER");
            userRepository.save(user);
        }

        context.authenticated = true;
        context.currentUser = null;
        context.currentPassword = null;
        context.response = null;
        context.savedEquipmentId = null;
        context.savedMeterId = null;
    }

    @Dado("que o sistema está rodando")
    public void sistemaRodando() {
        Response health = given()
                .auth().basic("admin", "123")
                .when().get("/actuator/health");
        assertEquals(200, health.getStatusCode());
    }

    @Dado("que estou autenticado como {string} com senha {string}")
    public void autenticado(String user, String password) {
        context.currentUser = user;
        context.currentPassword = password;
        context.authenticated = true;
    }

    @Dado("que não estou autenticado")
    public void naoAutenticado() {
        context.authenticated = false;
        context.currentUser = null;
        context.currentPassword = null;
    }

    @Dado("que existe um equipamento cadastrado")
    public void equipamentoCadastrado() {
        Equipment eq = new Equipment();
        eq.setName("Equipamento BDD Test");
        eq.setLocation("Lab Testes");
        Equipment saved = equipmentRepository.save(eq);
        context.savedEquipmentId = saved.getId();
    }

    @Dado("que existe um medidor cadastrado")
    public void medidorCadastrado() {
        EnergyMeter meter = new EnergyMeter();
        meter.setSerialNumber("SN-TEST-" + System.currentTimeMillis());
        meter.setInstallationDate(LocalDate.now());
        EnergyMeter saved = energyMeterRepository.save(meter);
        context.savedMeterId = saved.getId();
    }

    @Quando("eu faço uma requisição GET para {string}")
    public void getRequest(String path) {
        path = path.replace("{equipmentId}", context.savedEquipmentId != null ? context.savedEquipmentId : "invalid-id");
        RequestSpecification req = given().contentType("application/json");
        if (context.authenticated && context.currentUser != null) {
            req = req.auth().basic(context.currentUser, context.currentPassword);
        }
        context.response = req.when().get(path);
    }

    @Quando("eu faço uma requisição POST para {string} com o corpo:")
    public void postRequest(String path, String body) {
        RequestSpecification req = given().contentType("application/json").body(body);
        if (context.authenticated && context.currentUser != null) {
            req = req.auth().basic(context.currentUser, context.currentPassword);
        }
        context.response = req.when().post(path);
    }

    @Então("o status da resposta deve ser {int}")
    public void statusCode(int expectedStatus) {
        assertEquals(expectedStatus, context.response.getStatusCode(),
                "Status esperado: " + expectedStatus + " | Recebido: " + context.response.getStatusCode()
                        + " | Body: " + context.response.getBody().asString());
    }

    @Então("a resposta deve ser uma lista JSON")
    public void respostaLista() {
        assertTrue(context.response.getBody().asString().trim().startsWith("["));
    }

    @Então("a resposta deve conter o campo {string}")
    public void contemCampo(String field) {
        context.response.then().body(field, notNullValue());
    }

    @Então("a resposta deve conter o campo {string} com valor {string}")
    public void contemCampoValor(String field, String value) {
        context.response.then().body(field, equalTo(value));
    }

    @Então("o schema da resposta deve ser válido para {string}")
    public void validaSchema(String schemaName) {
        context.response.then().body(matchesJsonSchemaInClasspath("schemas/" + schemaName + "-schema.json"));
    }
}