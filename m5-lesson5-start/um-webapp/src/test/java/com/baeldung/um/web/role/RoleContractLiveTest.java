package com.baeldung.um.web.role;

import static com.baeldung.common.spring.util.Profiles.CLIENT;
import static com.baeldung.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import com.baeldung.um.client.template.RoleSimpleApiClientNoBase;
import com.baeldung.um.spring.CommonTestConfig;
import com.baeldung.um.spring.UmClientConfig;
import com.baeldung.um.spring.UmLiveTestConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.CharStreams;
import io.restassured.response.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ActiveProfiles({CLIENT, TEST})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UmLiveTestConfig.class, UmClientConfig.class,
    CommonTestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class RoleContractLiveTest {

  @Autowired
  private RoleSimpleApiClientNoBase api;

  // create

  @Test
  public final void whenResourceIsCreated_then201IsReceived() throws IOException {
    // When
    final Response response = getApi().createAsResponse(createNewResource());

    // Then
    assertThat(response.getStatusCode(), is(201));
    assertNotNull(response.getHeader(HttpHeaders.LOCATION));
  }

  // UTIL

  private final RoleSimpleApiClientNoBase getApi() {
    return api;
  }

  private final String createNewResource() throws IOException {
    final InputStream resourceAsStream = getClass().getResourceAsStream("/data/role_json_01.json");
    final JsonNode rootNode = new ObjectMapper().readTree(resourceAsStream);
    ((ObjectNode)rootNode).set("name", JsonNodeFactory.instance.textNode(randomAlphabetic(8)));
    return rootNode.toString();
  }

}
