package io.quire.api.model;

import io.swagger.annotations.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Project")
public class Project {
    @XmlElement(name = "id")
    public String getId() {
        return null;
    }

    @XmlElement(name = "name")
    public String getName() {
        return null;
    }
}
