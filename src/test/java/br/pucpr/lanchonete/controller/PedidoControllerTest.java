package br.pucpr.lanchonete.controller;

import br.pucpr.lanchonete.dto.PedidoRequestDTO;
import br.pucpr.lanchonete.dto.PedidoResponseDTO;
import br.pucpr.lanchonete.security.JwtAuthFilter;
import br.pucpr.lanchonete.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void deveCriarPedido() throws Exception {
        PedidoRequestDTO request = new PedidoRequestDTO();
        request.setClienteId(1L);
        PedidoRequestDTO.Item item = new PedidoRequestDTO.Item();
        item.setProdutoId(2L);
        item.setQuantidade(1);
        request.getItens().add(item);

        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(10L);
        response.setClienteId(1L);
        response.setStatus("NOVO");
        response.setDataCriacao(LocalDateTime.now());
        PedidoResponseDTO.Item responseItem = new PedidoResponseDTO.Item();
        responseItem.setId(20L);
        responseItem.setProdutoId(2L);
        responseItem.setProdutoNome("Item Teste");
        responseItem.setQuantidade(1);
        response.setItens(List.of(responseItem));

        when(pedidoService.criar(any(PedidoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}

