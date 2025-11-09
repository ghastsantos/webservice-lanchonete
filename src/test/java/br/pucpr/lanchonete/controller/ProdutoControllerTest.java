package br.pucpr.lanchonete.controller;

import br.pucpr.lanchonete.dto.ProdutoDTO;
import br.pucpr.lanchonete.security.JwtAuthFilter;
import br.pucpr.lanchonete.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void deveCriarProduto() throws Exception {
        ProdutoDTO request = new ProdutoDTO();
        request.setNome("Batata Frita");
        request.setDescricao("Batata palito 200g");
        request.setPreco(BigDecimal.valueOf(15.5));
        request.setCategoriaId(5L);

        ProdutoDTO response = new ProdutoDTO();
        response.setId(1L);
        response.setNome("Batata Frita");
        response.setDescricao("Batata palito 200g");
        response.setPreco(BigDecimal.valueOf(15.5));
        response.setCategoriaId(5L);

        when(produtoService.salvar(any(ProdutoDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}

