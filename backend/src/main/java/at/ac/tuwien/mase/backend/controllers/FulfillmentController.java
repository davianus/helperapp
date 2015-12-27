package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.controllers.exceptions.ControllerException;
import at.ac.tuwien.mase.backend.models.Fulfillment;
import at.ac.tuwien.mase.backend.models.Request;
import at.ac.tuwien.mase.backend.models.User;
import at.ac.tuwien.mase.backend.repositories.interfaces.IFulfillmentRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.IRequestRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.IUserRepository;
import at.ac.tuwien.mase.backend.viewmodels.FulfillmentEdit;
import at.ac.tuwien.mase.backend.viewmodels.FulfillmentRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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

    @Autowired
    private IRequestRepository requestRepository;

    @RequestMapping(method= RequestMethod.GET)
    public List<FulfillmentRead> readAll(@AuthenticationPrincipal User principal) throws ControllerException {
        List<Fulfillment> fulfillments = fulfillmentRepository.findByUser(principal)
                .stream().filter((Fulfillment f) -> !f.isDone()).collect(Collectors.toList());
        return fulfillments.stream().map(FulfillmentRead::new).collect(Collectors.toList());
    }

    @RequestMapping(method=RequestMethod.POST)
    public FulfillmentRead create(@AuthenticationPrincipal User principal, @RequestBody FulfillmentEdit fulfillmentEdit) throws ControllerException {
        if (fulfillmentEdit.getAmount() == null ||
                fulfillmentEdit.getRequestId() == null ||
                fulfillmentEdit.getUntil() == null) {
            throw new ControllerException("Not all required attributes given.");
        }
        Request request = requestRepository.findOne(fulfillmentEdit.getRequestId());
        if (request == null) throw new ControllerException("Request not found.");
        Fulfillment fulfillment = new Fulfillment();
        fulfillment.setAmount(fulfillmentEdit.getAmount());
        fulfillment.setUntil(fulfillmentEdit.getUntil());
        fulfillment.setUser(principal);
        fulfillment.setRequest(request);
        fulfillmentRepository.save(fulfillment);
        return new FulfillmentRead(fulfillment);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public FulfillmentRead read(@AuthenticationPrincipal User principal, @PathVariable("id") long id) throws ControllerException {
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment == null || (fulfillment.getUser().getId() != principal.getId() &&
                fulfillment.getRequest().getUser().getId() != principal.getId()))
            throw new ControllerException("Fulfillment not found.");
        return new FulfillmentRead(fulfillment);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public FulfillmentRead edit(@AuthenticationPrincipal User principal, @PathVariable("id") long id, @RequestBody FulfillmentEdit fulfillmentEdit) throws ControllerException {
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment == null || (fulfillment.getUser().getId() != principal.getId() &&
                fulfillment.getRequest().getUser().getId() != principal.getId()))
            throw new ControllerException("Fulfillment not found.");
        if (fulfillmentEdit.getAmount() != null) fulfillment.setAmount(fulfillmentEdit.getAmount());
        if (fulfillmentEdit.getUntil() != null) fulfillment.setUntil(fulfillmentEdit.getUntil());
        if (fulfillmentEdit.getDone() != null) fulfillment.setDone(fulfillmentEdit.getDone());
        fulfillmentRepository.save(fulfillment);
        return new FulfillmentRead(fulfillment);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@AuthenticationPrincipal User principal, @PathVariable("id") long id) throws ControllerException {
        Fulfillment fulfillment = fulfillmentRepository.findOne(id);
        if (fulfillment != null && fulfillment.getUser().getId() == principal.getId())
            fulfillmentRepository.delete(id);
    }
}
