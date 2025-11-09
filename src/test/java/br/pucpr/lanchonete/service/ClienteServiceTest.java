package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.ClienteDTO;
import br.pucpr.lanchonete.model.Cliente;
import br.pucpr.lanchonete.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        clienteService = new ClienteService(clienteRepository, new ModelMapper());
    }

    @Test
    void deveSalvarClienteComSucesso() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNome("João");
        dto.setEmail("joao@email.com");
        dto.setTelefone("41999999999");

        when(clienteRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            cliente.setId(1L);
            return cliente;
        });

        ClienteDTO resultado = clienteService.salvar(dto);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("João");

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("joao@email.com");
    }

    @Test
    void deveBuscarClientePorId() {
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        cliente.setTelefone("41988887777");

        when(clienteRepository.findById(2L)).thenReturn(Optional.of(cliente));

        ClienteDTO resultado = clienteService.buscarPorId(2L);

        assertThat(resultado.getNome()).isEqualTo("Maria");
    }
}

