package guru.springframework.sfgjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

    public static void main(String[] args) throws Exception {

//        Remove the embedded server initialisation, Instead use the Docker ActiveMQ
//        Initialize the embedded JMS
//        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
//        .setPersistenceEnabled(false)
//        .setJournalDirectory("target/data/journal")
//        .setSecurityEnabled(false)
//        .addAcceptorConfiguration("invm", "vm://0"));
//
//        server.start();

        SpringApplication.run(SfgJmsApplication.class, args);
    }

}
