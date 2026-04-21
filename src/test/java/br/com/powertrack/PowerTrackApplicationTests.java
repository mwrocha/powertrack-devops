package br.com.powertrack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = PowerTrackApplication.class)
@ActiveProfiles("test")
class PowerTrackApplicationTests {

    @Test
    void contextLoads() {
    }
}