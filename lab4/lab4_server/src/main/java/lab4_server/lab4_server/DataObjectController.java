package lab4_server.lab4_server;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataObjectController {
    @RequestMapping(value = "/data/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DataObject findById(@PathVariable long id) {
        return new DataObject(id, RandomStringUtils.randomAlphanumeric(10));
    }
}