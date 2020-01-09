package dk.miracle.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class TestApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    public void setUpMockMVC(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    public void addTestUserToRegistry() throws Exception{
        setUpMockMVC();
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Jarl")
                .param("lastName", "Due"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));
    }

    @Test
    void contextLoads() throws Exception{
        setUpMockMVC();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("itemList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("items"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void PostPerson() throws Exception {
        setUpMockMVC();
        addTestUserToRegistry();

        Item expectedPerson = new Item();
        expectedPerson.key = 1L;
        expectedPerson.name = "Jarl";
        expectedPerson.lastName = "Due";

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("itemList"))
                .andExpect(MockMvcResultMatchers.model().attribute("items", hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("items", contains(samePropertyValuesAs(expectedPerson))));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void UpdatePerson() throws Exception {
        setUpMockMVC();
        addTestUserToRegistry();

        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("key", "1")
                .param("name", "Jarl")
                .param("lastName", "Kirkensgaard Due"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        Item expectedPerson = new Item();
        expectedPerson.key = 1L;
        expectedPerson.name = "Jarl";
        expectedPerson.lastName = "Kirkensgaard Due";

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("itemList"))
                .andExpect(MockMvcResultMatchers.model().attribute("items", hasSize(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("items", contains(samePropertyValuesAs(expectedPerson))));
    }
}
