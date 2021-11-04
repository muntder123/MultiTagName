package com.muntder.tagname.api;

import com.muntder.tagname.api.other.Disableable;
import lombok.Getter;
import lombok.Setter;

public abstract class TagNameAPI implements Disableable {

    @Getter @Setter
    private static TagNameAPI instance;

    public abstract EntityManager getEntityManager();

}
