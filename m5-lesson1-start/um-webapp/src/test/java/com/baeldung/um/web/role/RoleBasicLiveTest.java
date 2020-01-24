package com.baeldung.um.web.role;

import static com.baeldung.common.spring.util.Profiles.CLIENT;
import static com.baeldung.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.spring.CommonTestConfig;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmLiveTestConfig;
import com.baeldung.um.util.Um;
import com.google.common.collect.Sets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UmLiveTestConfig.class, UmClientConfig.class, CommonTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleBasicLiveTest {

    private final static String JSON = MediaType.APPLICATION_JSON.toString();
    private final static String URI = "http://localhost:8082/um-webapp/api/roles";

    @Test
    public void whenAllRolesAreRetrieved_then200OK(){
//        final String uri  = "http://localhost:8082/um-webapp/api/roles";
        final RequestSpecification basicAuth = RestAssured.given().auth().preemptive().basic(Um.ADMIN_EMAIL,Um.ADMIN_PASS);
        final Response response = basicAuth.accept(ContentType.JSON).get(URI);

        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
    }

    @Test
    public void whenAllRolesAreRetrieved_thenAtLeastOneRoleExists() {
        final Response response = RestAssured.given().auth().preemptive().basic(Um.ADMIN_EMAIL,Um.ADMIN_PASS).accept(ContentType.JSON).get(URI);
        final List<Role> roles = response.as(List.class);

        Assert.assertThat(roles, not(Matchers.empty()));
    }

}
