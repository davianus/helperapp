package at.ac.tuwien.mase.backend.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xvinci on 11/14/15.
 */
@RestController
@RequestMapping("/streaming")
public class RequestController {
    private static final Logger logger = LogManager.getLogger(RequestController.class);

    @RequestMapping(method= RequestMethod.GET)
    public void  test() {
        logger.debug("Testlog");
    }
}
