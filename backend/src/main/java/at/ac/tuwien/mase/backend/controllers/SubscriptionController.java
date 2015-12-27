package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.controllers.exceptions.ControllerException;
import at.ac.tuwien.mase.backend.models.Subscription;
import at.ac.tuwien.mase.backend.models.Tag;
import at.ac.tuwien.mase.backend.models.User;
import at.ac.tuwien.mase.backend.repositories.interfaces.ISubscriptionRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.ITagRepository;
import at.ac.tuwien.mase.backend.repositories.interfaces.IUserRepository;
import at.ac.tuwien.mase.backend.viewmodels.SubscriptionEdit;
import at.ac.tuwien.mase.backend.viewmodels.SubscriptionRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lobmaier on 14.11.2015.
 */
@RestController
@RequestMapping("/user/{username}/subscriptions")
public class SubscriptionController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    @Autowired
    private ITagRepository tagRepository;

    @RequestMapping(method=RequestMethod.GET)
    public List<SubscriptionRead> readAll(@AuthenticationPrincipal User principal) throws ControllerException {
        List<Subscription> subscriptions = subscriptionRepository.findByUser(principal);
        return subscriptions.stream().map(SubscriptionRead::new).collect(Collectors.toList());
    }

    @RequestMapping(method=RequestMethod.POST)
    public SubscriptionRead create(@AuthenticationPrincipal User principal, @RequestBody SubscriptionEdit subscriptionCreate) throws ControllerException {
        if (subscriptionCreate.getStart() == null ||
                subscriptionCreate.getEnd() == null ||
                subscriptionCreate.getTags() == null ||
                subscriptionCreate.getTags().isEmpty()) {
            throw new ControllerException("Not all required attributes given.");
        }
        Subscription subscription = new Subscription();
        subscription.setStart(subscriptionCreate.getStart());
        subscription.setEnd(subscriptionCreate.getEnd());
        List<Tag> ts = new LinkedList<>();
        for (String tag : subscriptionCreate.getTags()) {
            Tag t = tagRepository.findByName(tag);
            if (t == null) {
                t = new Tag();
                t.setName(tag);
                t.setCount(1);
                tagRepository.save(t);
            }
            ts.add(t);
        }
        subscription.setTags(ts);
        subscription.setUser(principal);
        subscriptionRepository.save(subscription);
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public SubscriptionRead read(@AuthenticationPrincipal User principal, @PathVariable("id") long id) throws ControllerException {
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription == null || subscription.getUser().getId() != principal.getId()) throw new ControllerException("Subscription not found.");
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public SubscriptionRead edit(@AuthenticationPrincipal User principal, @PathVariable("id") long id, @RequestBody SubscriptionEdit subscriptionEdit) throws ControllerException {
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription == null || subscription.getUser().getId() != principal.getId()) throw new ControllerException("Subscription not found.");
        if (subscriptionEdit.getStart() != null) subscription.setStart(subscriptionEdit.getStart());
        if (subscriptionEdit.getEnd() != null) subscription.setEnd(subscriptionEdit.getEnd());
        if (subscriptionEdit.getTags() != null && !subscriptionEdit.getTags().isEmpty()) {
            List<Tag> ts = new LinkedList<>();
            for (String tag : subscriptionEdit.getTags()) {
                Tag t = tagRepository.findByName(tag);
                if (t == null) {
                    t = new Tag();
                    t.setName(tag);
                    t.setCount(1);
                    tagRepository.save(t);
                }
                ts.add(t);
            }
            subscription.setTags(ts);
        }
        subscription.setUser(principal);
        subscriptionRepository.save(subscription);
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@AuthenticationPrincipal User principal, @PathVariable("id") long id) throws ControllerException {
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription != null && subscription.getUser().getId() == principal.getId())
            subscriptionRepository.delete(id);
    }
}
