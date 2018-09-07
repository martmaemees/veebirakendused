package ee.mmaemees.viinavaatlus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main class for the application.
 */
@RestController
@SpringBootApplication
public class DemoApplication {

    /**
     * GET resource for testing.
     * @return String "Hello world!"
     */
    @RequestMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }

    /**
     * Main function for the application. Starts the Spring application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
