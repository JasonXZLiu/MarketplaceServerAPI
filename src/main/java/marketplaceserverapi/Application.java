package marketplaceserverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    /**
     * Starts the SpringApplication which starts Tomcat Server
     * to host the application.
     * */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}