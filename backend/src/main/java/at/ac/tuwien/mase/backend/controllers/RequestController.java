package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.controllers.exceptions.ControllerException;
import at.ac.tuwien.mase.backend.models.Location;
import at.ac.tuwien.mase.backend.models.Request;
import at.ac.tuwien.mase.backend.models.Tag;
import at.ac.tuwien.mase.backend.repositories.interfaces.ILocationRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.IRequestRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.ITagRepository;
import at.ac.tuwien.mase.backend.viewmodels.RequestEdit;
import at.ac.tuwien.mase.backend.viewmodels.RequestRead;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @RequestMapping(method=RequestMethod.GET)
    public List<RequestRead> search(@RequestParam String tags,
                       @RequestParam(value="start", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                       @RequestParam(value="end", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        if (start == null) start = new Date();
        if (end == null) {
            Calendar c = new GregorianCalendar();
            c.setTime(start);
            c.add(Calendar.YEAR, 1);
            end = c.getTime();
        }
        String[] tagList = tags.split(",");
        List<RequestRead> rs = new LinkedList<RequestRead>();
        for (Request r : requestRepository.findByDate(start, end)) {
            RequestRead rr = new RequestRead(r);
            if (rr.getTags().containsAll(Arrays.asList(tagList)) && rr.getAmountDone() < rr.getAmount())
                rs.add(rr);
            if (rs.size() == 10)
                break;
        }
        return rs;
    }

    @RequestMapping(method=RequestMethod.POST)
    public RequestRead create(RequestEdit requestCreate) throws ControllerException {
        if (requestCreate.getTags() == null || requestCreate.getTags().isEmpty() ||
                requestCreate.getLocation() == null ||
                requestCreate.getAmount() == null ||
                requestCreate.getDescription() == null ||
                requestCreate.getStartDate() == null ||
                requestCreate.getEndDate() == null) {
            throw new ControllerException("Not all required attributes given.");
        }
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
        Location l = locationRepository.findOne(requestCreate.getLocation().getId());
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
        requestRepository.save(request);
        return new RequestRead(request);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public RequestRead read(@PathVariable("id") long id) throws ControllerException {
        Request request = requestRepository.findOne(id);
        if (request == null) {
            throw new ControllerException("Request not found.");
        }
        return new RequestRead(request);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public RequestRead edit(@PathVariable("id") long id, RequestEdit requestEdit) throws ControllerException {
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
            Location l = locationRepository.findOne(requestEdit.getLocation().getId());
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
        return new RequestRead(request);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) throws ControllerException {
        Request request = requestRepository.findOne(id);
        if (request != null) {
            requestRepository.delete(id);
        }
    }
}
