package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.controllers.exceptions.ControllerException;
import at.ac.tuwien.mase.backend.models.Fulfillment;
import at.ac.tuwien.mase.backend.models.User;
import at.ac.tuwien.mase.backend.repositories.interfaces.IFulfillmentRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.IUserRepository;
import at.ac.tuwien.mase.backend.viewmodels.FulfillmentEdit;
import at.ac.tuwien.mase.backend.viewmodels.FulfillmentRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lobmaier on 14.11.2015.
 */
@RestController
@RequestMapping("/user/{username}/fulfillments")
public class FulfillmentController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFulfillmentRepository fulfillmentRepository;

    @RequestMapping(method= RequestMethod.GET)
    public List<FulfillmentRead> readAll(@PathVariable("username") String username) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        List<Fulfillment> fulfillments = fulfillmentRepository.findByUser(user)
                .stream().filter((Fulfillment f) -> !f.isDone()).collect(Collectors.toList());
        return fulfillments.stream().map(FulfillmentRead::new).collect(Collectors.toList());
    }

    @RequestMapping(method=RequestMethod.POST)
    public FulfillmentRead create(@PathVariable("username") String username, @RequestBody FulfillmentEdit fulfillmentEdit) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        if (fulfillmentEdit.getAmount() == null ||
                fulfillmentEdit.getRequestId() == null ||
                fulfillmentEdit.getUntil() == null) {
            throw new ControllerException("Not all required attributes given.");
        }
        Fulfillment fulfillment = new Fulfillment();
        fulfillment.setAmount(fulfillmentEdit.getAmount());
        fulfillment.setUntil(fulfillmentEdit.getUntil());
        fulfillment.setUser(user);
        fulfillmentRepository.save(fulfillment);
        return new FulfillmentRead(fulfillment, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public FulfillmentRead read(@PathVariable("username") String username, @PathVariable("id") long id) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment == null ) throw new ControllerException("Fulfillment not found.");
        return new FulfillmentRead(fulfillment, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public FulfillmentRead edit(@PathVariable("username") String username, @PathVariable("id") long id, @RequestBody FulfillmentEdit fulfillmentEdit) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment == null || fulfillment.getUser().getId() != user.getId()) throw new ControllerException("Fulfillment not found.");
        if (fulfillmentEdit.getAmount() != null) fulfillment.setAmount(fulfillmentEdit.getAmount());
        if (fulfillmentEdit.getUntil() != null) fulfillment.setUntil(fulfillmentEdit.getUntil());
        if (fulfillmentEdit.getDone() != null) fulfillment.setDone(fulfillmentEdit.getDone());
        fulfillmentRepository.save(fulfillment);
        return new FulfillmentRead(fulfillment, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("username") String username, @PathVariable("id") long id) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ControllerException("User not found.");
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment != null && fulfillment.getUser().getId() == user.getId())
            fulfillmentRepository.delete(id);
    }
}
