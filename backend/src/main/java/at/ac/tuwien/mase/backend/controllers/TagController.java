package at.ac.tuwien.mase.backend.controllers;

import at.ac.tuwien.mase.backend.models.Tag;
import at.ac.tuwien.mase.backend.repositories.interfaces.ITagRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xvinci on 11/14/15.
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private static final Logger logger = LogManager.getLogger(TagController.class);

    private ITagRepository repository;

    @Autowired
    public TagController(ITagRepository repo) {this.repository = repo;}

    @RequestMapping(method= RequestMethod.GET)
    public List<String>  getSimilarTags(@RequestParam("q") String query) {
        logger.debug("Getting similar tags for tag "+query);

        List<Tag> qresult = repository.findAll(new Sort(Sort.Direction.DESC, "count")).subList(0, 4);

        List<String> result = qresult.stream().map(Tag::getName).collect(Collectors.toList());

        return result;
    }
}
