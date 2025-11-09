package br.pucpr.lanchonete.controller;

import br.pucpr.lanchonete.dto.ClienteDTO;
import br.pucpr.lanchonete.security.JwtAuthFilter;
import br.pucpr.lanchonete.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void deveCriarCliente() throws Exception {
        ClienteDTO request = new ClienteDTO();
        request.setNome("Fulano");
        request.setEmail("fulano@email.com");
        request.setTelefone("41999999999");

        ClienteDTO response = new ClienteDTO();
        response.setId(1L);
        response.setNome("Fulano");
        response.setEmail("fulano@email.com");
        response.setTelefone("41999999999");

        when(clienteService.salvar(any(ClienteDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}

