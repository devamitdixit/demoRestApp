package com.tul;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tul.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class TestProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void testGetAllProducsSuccess() throws Exception {

        List<testCase> testCases = new ArrayList<testCase>(){{
           add(new testCase("getProductsSuccess", "/items/v1/products", HttpStatus.OK.value(), "[{\"productId\":1,\"productName\":\"iPhone\",\"price\":70000.0},{\"productId\":2,\"productName\":\"appo\",\"price\":10000.0}]"));
            add(new testCase("getProduct", "/items/v1/products/1",HttpStatus.OK.value(), "{\"productId\":1,\"productName\":\"iPhone\",\"price\":70000.0}"));
            add(new testCase("getProduct", "/items/v1/products/5",HttpStatus.NO_CONTENT.value(), "{\"errorCode\":\"204 NO_CONTENT\",\"errorMessage\":\"Product Not found\",\"statusCode\":\"204 NO_CONTENT\"}"));
        }};

        for (testCase tc:testCases) {
            String uri = tc.url;
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(tc.expcetedStatus, status);
            assertEquals(tc.expectedResponse, mvcResult.getResponse().getContentAsString());
        }
    }

    class testCase{
        String testCase;
        String url;
        int expcetedStatus;
        String expectedResponse;
        public testCase(String testCase, String url, int expcetedStatus, String expectedResponse) {
            this.testCase = testCase;
            this.url = url;
            this.expcetedStatus = expcetedStatus;
            this.expectedResponse = expectedResponse;
        }

    }
}
