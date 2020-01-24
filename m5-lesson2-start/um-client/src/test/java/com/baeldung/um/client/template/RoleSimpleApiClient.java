package com.baeldung.um.client.template;

import com.google.common.base.Preconditions;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.baeldung.common.spring.util.Profiles;
import com.baeldung.um.client.UmPaths;
import com.baeldung.um.util.Um;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@Component
@Profile(Profiles.CLIENT)
public final class RoleSimpleApiClient {

    private final static String JSON = MediaType.APPLICATION_JSON.toString();

    @Autowired
    protected UmPaths paths;

    // API

    // find - one
    public final Role findOne(final long id) {
        final Response findOneResponse = findOneAsResponse(id);
        Preconditions.checkState(findOneResponse.getStatusCode()==200, "Find One operation didn't result in a 200 OK");
        return findOneAsResponse(id).as(Role.class);
    }

    public final Response findOneAsResponse(final long id) {
        return givenAuthenticated().accept(JSON).get(getUri() + "/" + id);
    }

    // UTIL

    public final String getUri() {
        return paths.getRoleUri();
    }

    public final RequestSpecification givenAuthenticated() {
        final Pair<String, String> credentials = getDefaultCredentials();
        return RestAssured.given().auth().preemptive().basic(credentials.getLeft(), credentials.getRight());
    }

    private final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<>(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}
