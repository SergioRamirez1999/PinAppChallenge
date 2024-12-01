package com.sergioramirezme.pinapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergioramirezme.pinapp.service.IClientService;
import com.sergioramirezme.pinapp.presentation.dtos.ClientCreateReqDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientFullResDTO;
import com.sergioramirezme.pinapp.presentation.dtos.ClientKPIResDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest
class PinAppApplicationTests {

	private static final String BASE_PATH = "/v1/clients";
	private static final String RESOURCE_PATH = BASE_PATH;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IClientService clientService;

	@Nested
	class GetTests {

		private Pageable pageable;

		private Page<ClientFullResDTO> responsePage;

		private ClientKPIResDTO responseKPI;

		@BeforeEach
		void setup() {

			responsePage = new PageImpl<>(
					List.of(
							ClientFullResDTO.builder()
									.id(1)
									.name("Sergio")
									.lastname("Ramírez")
									.age(25)
									.birthdate(LocalDate.of(1999, 9, 11))
									.estimatedDateOfDeath(LocalDate.of(2069, 9, 11))
									.build()
					)
			);

			pageable = Pageable.ofSize(10);

			responseKPI = ClientKPIResDTO.builder()
					.ageAverage(25.0)
					.standardDeviation(25.0)
					.build();

		}

		@Test
		void testListClients() throws Exception {
			when(clientService.list(pageable)).thenReturn(responsePage);

			mvc.perform(
							MockMvcRequestBuilders.get(RESOURCE_PATH)
									.accept(MediaType.APPLICATION_JSON)
					)
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
					.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
					.andExpect(MockMvcResultMatchers.jsonPath("$.size").value(1))
					.andExpect(MockMvcResultMatchers.jsonPath("$.number").value(0))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Sergio"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastname").value("Ramírez"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].age").value(25))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].birthdate").value("1999-09-11"))
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].estimatedDateOfDeath").value("2069-09-11"));

		}

		@Test
		void testKPI() throws Exception {
			final String KPI_PATH = RESOURCE_PATH + "/kpi";

			when(clientService.kpi()).thenReturn(responseKPI);

			mvc.perform(
							MockMvcRequestBuilders.get(KPI_PATH)
									.accept(MediaType.APPLICATION_JSON)
					)
					.andDo(MockMvcResultHandlers.print())
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("$.ageAverage").value(25))
					.andExpect(MockMvcResultMatchers.jsonPath("$.standardDeviation").value(25));
		}
	}

	@Nested
	class PostTests {

		private ClientCreateReqDTO clientCreateReqDTO;

		@BeforeEach
		void setup() {

			clientCreateReqDTO = ClientCreateReqDTO.builder()
					.name("Sergio")
					.lastname("Ramírez")
					.age(25)
					.birthdate(LocalDate.of(1999, 9, 11))
					.build();

		}

		@Test
		void testCreateClient() throws Exception {
			mvc.perform(
					MockMvcRequestBuilders.post(RESOURCE_PATH)
							.content(objectMapper.writeValueAsString(clientCreateReqDTO))
							.accept(MediaType.APPLICATION_JSON)
							.contentType(MediaType.APPLICATION_JSON)
				)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());
		}
	}

}
