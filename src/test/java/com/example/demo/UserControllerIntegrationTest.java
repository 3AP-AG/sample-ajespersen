package com.example.demo;

import java.nio.charset.Charset;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

  @MockitoBean private UserService userService;

  @Autowired private MockMvc mockMvc;

  @Test
  public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
    MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
    String user = "{\"firstName\": \"fds\", \"lastName\" : \"bobbydomain\"}";
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/users/registerCustomer")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
  }

  @Test
  public void whenPostRequestToUsersAndInValidUser_thenCorrectResponse() throws Exception {
    String user = "{\"firstName\": \"\", \"lastName\" : \"bobbydomain\"}";
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/users/registerCustomer")
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Is.is("firstName is mandatory")))
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }
}
