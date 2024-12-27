package com.example.spring.unit;

import com.example.spring.api.CityRestController;
import com.example.spring.domain.City;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.city.CitySaveOrUpdateRequest;
import com.example.spring.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class CityRestControllerTest {

    @Mock
    CityService cityService;

    @InjectMocks
    CityRestController cityRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private City city;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cityRestController).build();
        objectMapper = new ObjectMapper();
        city = new City("Minsk");
        city.setId(1);
    }

    @Test
    void createCityTest() throws Exception {
        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(city.getId());
        cityResponse.setName(city.getName());

        when(cityService.create(isA(CitySaveOrUpdateRequest.class))).thenReturn(cityResponse);
        String strCityResponse = objectMapper.writeValueAsString(cityResponse);
        mockMvc.perform(post("/city").contentType(MediaType.APPLICATION_JSON).content(strCityResponse))
                .andExpect(status().isCreated());
        verify(cityService, times(1)).create(isA(CitySaveOrUpdateRequest.class));
    }

/*    @Test
    void deleteCityTest() throws Exception {

        when(cityService.delete(city.getId())).(cityResponse);
        mockMvc.perform(post("/city")).andExpect(status().isNoContent());
                .andExpect(status().is(201));
        verify(cityService, times(1)).create(isA(CitySaveOrUpdateRequest.class));
    }*/
}
