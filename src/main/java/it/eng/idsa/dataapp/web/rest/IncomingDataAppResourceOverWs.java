package it.eng.idsa.dataapp.web.rest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.eng.idsa.dataapp.service.MultiPartMessageService;
import it.eng.idsa.multipart.builder.MultipartMessageBuilder;
import it.eng.idsa.multipart.domain.MultipartMessage;
import it.eng.idsa.multipart.processor.MultipartMessageProcessor;
import it.eng.idsa.streamer.WebSocketServerManager;

/**
 * @author Antonio Scatoloni
 */

public class IncomingDataAppResourceOverWs implements PropertyChangeListener {
    private static final Logger logger = LogManager.getLogger(IncomingDataAppResourceOverWs.class);

    @Autowired
    private MultiPartMessageService multiPartMessageService;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String responseMessage = (String) evt.getNewValue();
        WebSocketServerManager.getMessageWebSocketResponse().sendResponse(createDummyResponse(responseMessage));
    }


    //TODO
    private String createDummyResponse(String responseMessageInput) {
        String responseMessageString = null;
        try {
            logger.info("Message arrived from ECC Consumer: "+ responseMessageInput.substring(0, 600));
            logger.info("END of Message arrived from ECC Consumer");
            String header = multiPartMessageService.getHeader(responseMessageInput);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String payload="{\"checksum\":\"ABC123 " + dateFormat.format(date) + "\"}";
            // prepare multipart message.
            MultipartMessage responseMessage = new MultipartMessageBuilder()
    				.withHeaderContent(header)
    				.withPayloadContent(payload)
    				.build();
    		responseMessageString = MultipartMessageProcessor.multipartMessagetoString(responseMessage, false);
            
        } catch (Exception e) {
            logger.error("Error encountered in creating Response for WSS communication with stack: " + e.getMessage());
        }
        return responseMessageString;
    }


}
