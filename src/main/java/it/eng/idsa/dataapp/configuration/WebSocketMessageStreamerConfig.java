package it.eng.idsa.dataapp.configuration;

import it.eng.idsa.dataapp.web.rest.IncomingDataAppResourceOverWs;
import it.eng.idsa.streamer.WebSocketServerManager;
import it.eng.idsa.streamer.websocket.receiver.server.FileRecreatorBeanExecutor;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Antonio Scatoloni
 */

@Configuration
public class WebSocketMessageStreamerConfig {

    @Bean
    public FileRecreatorBeanExecutor fileRecreatorBeanExecutor() throws SchedulerException {
        FileRecreatorBeanExecutor fileRecreatorBeanExecutor = WebSocketServerManager.fileRecreatorBeanExecutor();
        fileRecreatorBeanExecutor.setPort(9000); //optional default 9000
        //fileRecreatorBeanExecutor.setKeystorePassword("ssl-server.jks"); //optional default classpath: ssl-server.jks
        //fileRecreatorBeanExecutor.setKeystorePassword("password");
        //fileRecreatorBeanExecutor.setPath("/incoming-data-ws");
        return fileRecreatorBeanExecutor;
    }

    @Bean
    public IncomingDataAppResourceOverWs incomingDataAppResourceOverWs(){
        IncomingDataAppResourceOverWs incomingDataAppResourceOverWs = new IncomingDataAppResourceOverWs();
        WebSocketServerManager.getMessageWebSocketResponse().addPropertyChangeListener(incomingDataAppResourceOverWs);
        return incomingDataAppResourceOverWs;
    }
}
