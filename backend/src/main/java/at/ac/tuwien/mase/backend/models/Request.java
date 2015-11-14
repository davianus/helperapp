package at.ac.tuwien.mase.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by xvinci on 11/14/15.
 */
@Document(collection = "request")
public class Request {
    private String id;
}
