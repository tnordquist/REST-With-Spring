package com.baeldung.um.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.baeldung.client.marshall.IMarshaller;
import com.baeldung.common.spring.util.Profiles;
import com.baeldung.common.util.QueryConstants;
import com.baeldung.common.web.WebConstants;
import com.baeldung.um.client.UmPaths;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.util.Um;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Component
@Profile(Profiles.CLIENT)
public final class RoleSimpleApiClient extends GenericSimpleApiClient<Role>{

    public RoleSimpleApiClient() {
        super(Role.class);
    }

    // API

    @Override
    public final String getUri() {
        return paths.getRoleUri();
    }
}