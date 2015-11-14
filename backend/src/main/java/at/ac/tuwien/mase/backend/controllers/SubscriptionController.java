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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<SubscriptionRead> readAll(@PathVariable("username") String username) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        List<Subscription> subscriptions = subscriptionRepository.findByUser(user);
        return subscriptions.stream().map(SubscriptionRead::new).collect(Collectors.toList());
    }

    @RequestMapping(method=RequestMethod.POST)
    public SubscriptionRead create(@PathVariable("username") String username, SubscriptionEdit subscriptionCreate) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
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
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public SubscriptionRead read(@PathVariable("username") String username, @PathVariable("id") long id) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription == null ) throw new ControllerException("Subscription not found.");
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public SubscriptionRead edit(@PathVariable("username") String username, @PathVariable("id") long id, SubscriptionEdit subscriptionEdit) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null ) throw new ControllerException("User not found.");
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription == null || subscription.getUser().getId() != user.getId()) throw new ControllerException("Subscription not found.");
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
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
        return new SubscriptionRead(subscription);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("username") String username, @PathVariable("id") long id) throws ControllerException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ControllerException("User not found.");
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription != null && subscription.getUser().getId() == user.getId())
            subscriptionRepository.delete(id);
    }
}
