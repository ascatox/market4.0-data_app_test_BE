package it.eng.idsa.dataapp.web.rest;

import it.eng.idsa.dataapp.service.impl.MultiPartMessageServiceImpl;
import it.eng.idsa.streamer.WebSocketClientManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

/**
 * @author Antonio Scatoloni
 */

@RestController
@EnableAutoConfiguration
@RequestMapping({"/"})
public class FileExecutorResource {
    private static final Logger logger = LogManager.getLogger(FileExecutorResource.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    MultiPartMessageServiceImpl multiPartMessageService;

    @PostMapping("/executeFile")
    @ResponseBody
    public String executeFile(@RequestHeader("Forward-To") String forwardTo, @RequestBody String fileName) throws Exception {
        Resource resource = resourceLoader.getResource("classpath:examples-multipart-messages/" + fileName);
        return WebSocketClientManager.getMessageWebSocketSender().sendMultipartMessageWebSocketOverHttps(resource.getFile(), forwardTo);
    }

}
