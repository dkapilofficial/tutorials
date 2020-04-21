package com.baeldung.modelmapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import java.util.ArrayList;
import java.util.List;

/**
 * UserPropertyMap class instantiates the converter to map the data from the user list to the user name list.
 * In the configuration method, we call a converter to do the mapping.
 * @author sasam0320
 */
public class UserPropertyMap extends PropertyMap<UserList, UserListDTO> {


    Converter<List<User>, List<String>> converter = new AbstractConverter<List<User>, List<String>>() {

        List<String> usernames = new ArrayList<>();

        protected List<String> convert(List<User> users) {

            users.forEach(user -> usernames.add(user.getUserName()));
            return usernames;
        }

    };

    @Override
    protected void configure() {
        using(converter).map(source.getUsers(), destination.getUsernames());
    }
}
