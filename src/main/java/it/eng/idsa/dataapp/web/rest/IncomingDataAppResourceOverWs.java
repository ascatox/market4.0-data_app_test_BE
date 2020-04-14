package it.eng.idsa.dataapp.web.rest;

import it.eng.idsa.dataapp.service.MultiPartMessageService;
import it.eng.idsa.streamer.WebSocketServerManager;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Antonio Scatoloni
 */

public class IncomingDataAppResourceOverWs implements PropertyChangeListener {

    @Autowired
    private MultiPartMessageService multiPartMessageService;

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setResponseMessage((String) evt.getNewValue());
        WebSocketServerManager.getMessageWebSocketResponse().sendResponse(createDummyResponse(getResponseMessage()));
    }


    //TODO
    private String createDummyResponse(String responseMessage) {
        String responseString = null;
        try {
            String header = multiPartMessageService.getHeader(responseMessage);
            // Put check sum in the payload
            String payload = "{\"checksum\":\"ABC123\"}";
            // prepare multipart message.
            HttpEntity entity = multiPartMessageService.createMultipartMessage(header, payload);
            responseString = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            //TODO Rejection
        }
        return responseString;
    }


}