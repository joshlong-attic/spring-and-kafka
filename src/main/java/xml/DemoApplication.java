package xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@EnableIntegration
@ImportResource("/xml/outbound-kafka-integration.xml")
public class DemoApplication {

    private Log log = LogFactory.getLog(getClass());

    @Bean
    @DependsOn("kafkaOutboundChannelAdapter")
    CommandLineRunner kickOff(@Qualifier("inputToKafka") MessageChannel in) {
        return args -> {
            for (int i = 0; i < 1000; i++) {
                in.send(new GenericMessage<>("#" + i));
                log.info("sending message #" + i);
            }
        };
    }


    public static void main(String args[]) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
