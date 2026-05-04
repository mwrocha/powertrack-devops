package br.com.powertrack.bdd.steps;

import io.cucumber.java.pt.*;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

public class SpecificSteps {

    @Autowired
    private StepContext context;

    @Quando("eu crio uma leitura de medidor com energyKwh {double}")
    public void criarLeitura(double energyKwh) {
        String body = String.format(
                "{\"meterId\":\"%s\",\"equipmentId\":\"%s\",\"energyKwh\":%s}",
                context.savedMeterId, context.savedEquipmentId, energyKwh
        );
        RequestSpecification req = given().contentType("application/json").body(body);
        if (context.authenticated && context.currentUser != null) {
            req = req.auth().basic(context.currentUser, context.currentPassword);
        }
        context.response = req.when().post("/api/meter-readings");
    }

    @Quando("eu crio um alerta com mensagem {string} e tipo {string}")
    public void criarAlerta(String message, String type) {
        String body = String.format(
                "{\"equipmentId\":\"%s\",\"message\":\"%s\",\"type\":\"%s\"}",
                context.savedEquipmentId != null ? context.savedEquipmentId : "invalid-id", message, type
        );
        RequestSpecification req = given().contentType("application/json").body(body);
        if (context.authenticated && context.currentUser != null) {
            req = req.auth().basic(context.currentUser, context.currentPassword);
        }
        context.response = req.when().post("/api/alerts");
    }
}