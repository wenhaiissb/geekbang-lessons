package org.geektimes.rest.demo;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.*;

public class RestClientDemo {

    public static void main(String[] args) {
//        Client client = ClientBuilder.newClient();
//        Response response = client
//                .target("http://127.0.0.1:8080/hello/world")      // WebTarget
//                .request() // Invocation.Builder
//                .get();                                     //  Response

//        String content = response.readEntity(String.class);
//        System.out.println(content);
        Entity<User> entity = Entity.entity(new User(1L, "文海"), APPLICATION_JSON);
        Client client = ClientBuilder.newClient();

        Response response = client
                .target("http://127.0.0.1:8080/user")      // WebTarget
                .request()
                .header("Content-Type",APPLICATION_JSON) //  Invocation.Builder
                .post(entity);                                     //  Response

        User user = response.readEntity(User.class);

        System.out.println(user);

    }
}
