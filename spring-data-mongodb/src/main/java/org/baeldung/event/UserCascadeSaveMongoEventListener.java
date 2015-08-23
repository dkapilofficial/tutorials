package org.baeldung.event;

import org.baeldung.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

public class UserCascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(final Object source) {
        if (source instanceof User && ((User) source).getEmailAddress() != null) {
            mongoOperations.save(((User) source).getEmailAddress());
        }
    }
}
