package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.controllers.exceptions.ControllerException;
import at.ac.tuwien.mase.backend.models.*;
import at.ac.tuwien.mase.backend.repositories.interfaces.*;
import at.ac.tuwien.mase.backend.viewmodels.FulfillmentRead;
import at.ac.tuwien.mase.backend.viewmodels.RequestEdit;
import at.ac.tuwien.mase.backend.viewmodels.RequestRead;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xvinci on 11/14/15.
 */
@RestController
@RequestMapping("/requests")
public class RequestController {
    private static final Logger logger = LogManager.getLogger(RequestController.class);

    @Autowired
    private IRequestRepository requestRepository;

    @Autowired
    private ITagRepository tagRepository;

    @Autowired
    private ILocationRepository locationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    @RequestMapping(method=RequestMethod.GET)
    public List<RequestRead> search(
            @RequestParam(required=false) String filter,
            @RequestParam(required=false) String user,
            @RequestParam(required=false) String tags,
            @RequestParam(value="start", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
            @RequestParam(value="end", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) throws ControllerException {
        User u = userRepository.findByUsername(user);
        if (filter == null) filter = "all";
        if (u == null && !filter.equals("all")) throw new ControllerException("User must be given if filter is not all.");
        if (start == null) start = new Date();
        if (end == null) {
            Calendar c = new GregorianCalendar();
            c.setTime(start);
            c.add(Calendar.YEAR, 1);
            end = c.getTime();
        }
        List<RequestRead> rs = new LinkedList<RequestRead>();
        for (Request r : requestRepository.findByDate(start, end)) {
            RequestRead rr = new RequestRead(r);
            if (rr.getFulfillments() != null && rr.getFulfillments().stream().allMatch((FulfillmentRead f) -> f.isDone())) continue;
            if (rr.getAmountDone() >= rr.getAmount() && !filter.equals("user")) continue;
            if (tags != null && tags != "" && !rr.getTags().containsAll(Arrays.asList(tags.split(",")))) continue;
            if (filter.equals("user") && rr.getUser().getId() != u.getId()) continue;
            if (filter.equals("subscriptions") && !subscriptionRepository.findByUser(u)
                    .stream().anyMatch((Subscription s) -> rr.getTags().containsAll(
                            s.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                    ) && rr.getUser().getId() == u.getId())
                continue;
            rs.add(rr);
            if (rs.size() == 10) break;
        }
        return rs;
    }

    @RequestMapping(method=RequestMethod.POST)
    public RequestRead create(@RequestBody RequestEdit requestCreate) throws ControllerException {
        if (requestCreate.getTags() == null || requestCreate.getTags().isEmpty() ||
                requestCreate.getLocation() == null ||
                requestCreate.getAmount() == null ||
                requestCreate.getDescription() == null ||
                requestCreate.getStartDate() == null ||
                requestCreate.getEndDate() == null ||
                requestCreate.getUser() == null) {
            throw new ControllerException("Not all required attributes given.");
        }
        User user = userRepository.findByUsername(requestCreate.getUser().getUsername());
        Request request = new Request();
        List<Tag> ts = new LinkedList<>();
        for (String tag :requestCreate.getTags()) {
            Tag t = tagRepository.findByName(tag);
            if (t == null) {
                t = new Tag();
                t.setName(tag);
                t.setCount(1);
                tagRepository.save(t);
            } else {
                t.setCount(t.getCount() + 1);
                tagRepository.save(t);
            }
            ts.add(t);
        }
        request.setTags(ts);
        Location l = null;
        if (requestCreate.getLocation().getId() != null) l = locationRepository.findOne(requestCreate.getLocation().getId());
        if (l == null) {
            l = new Location();
            l.setName(requestCreate.getLocation().getName());
            l.setLocation(requestCreate.getLocation().getLocation());
            locationRepository.save(l);
        }
        request.setLocation(l);
        request.setAmount(requestCreate.getAmount());
        request.setDescription(requestCreate.getDescription());
        request.setStartDate(requestCreate.getStartDate());
        request.setEndDate(requestCreate.getEndDate());
        request.setUser(user);
        requestRepository.save(request);
        return new RequestRead(request, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public RequestRead read(@PathVariable("id") long id) throws ControllerException {
        Request request = requestRepository.findOne(id);
        if (request == null) {
            throw new ControllerException("Request not found.");
        }
        return new RequestRead(request, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public RequestRead edit(@PathVariable("id") long id, @RequestBody RequestEdit requestEdit) throws ControllerException {
        Request request = requestRepository.findOne(id);
        if (request == null) {
            throw new ControllerException("Request not found.");
        }
        if (requestEdit.getTags() != null && !requestEdit.getTags().isEmpty()) {
            List<Tag> ts = new LinkedList<>();
            for (String tag :requestEdit.getTags()) {
                Tag t = tagRepository.findByName(tag);
                if (t == null) {
                    t = new Tag();
                    t.setName(tag);
                    t.setCount(1);
                    tagRepository.save(t);
                }
                ts.add(t);
            }
            request.setTags(ts);
        }
        if (requestEdit.getLocation() != null) {
            Location l = null;
            if (requestEdit.getLocation().getId() != null) l = locationRepository.findOne(requestEdit.getLocation().getId());
            if (l == null) {
                l = new Location();
                l.setName(requestEdit.getLocation().getName());
                l.setLocation(requestEdit.getLocation().getLocation());
                locationRepository.save(l);
            }
            request.setLocation(l);
        }
        if (requestEdit.getAmount() != null) request.setAmount(requestEdit.getAmount());
        if (requestEdit.getDescription() != null) request.setDescription(requestEdit.getDescription());
        if (requestEdit.getStartDate() != null) request.setStartDate(requestEdit.getStartDate());
        if (requestEdit.getEndDate() != null) request.setEndDate(requestEdit.getEndDate());
        requestRepository.save(request);
        return new RequestRead(request, true);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws ControllerException {
        Request request = requestRepository.findOne(id);
        if (request != null) {
            requestRepository.delete(id);
        }
    }
}
