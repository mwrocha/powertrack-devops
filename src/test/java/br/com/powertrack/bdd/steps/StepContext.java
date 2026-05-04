package br.com.powertrack.bdd.steps;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
public class StepContext {
    public Response response;
    public String currentUser;
    public String currentPassword;
    public boolean authenticated = true;
    public String savedEquipmentId;
    public String savedMeterId;
}