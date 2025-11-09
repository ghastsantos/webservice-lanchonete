package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.ClienteDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Cliente;
import br.pucpr.lanchonete.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    public ClienteDTO salvar(ClienteDTO dto) {
        clienteRepository.findByEmail(dto.getEmail()).ifPresent(cliente -> {
            throw new BusinessException("CLIENTE_EMAIL_EXISTS", "Já existe cliente com este email");
        });
        Cliente cliente = modelMapper.map(dto, Cliente.class);
        Cliente salvo = clienteRepository.save(cliente);
        return modelMapper.map(salvo, ClienteDTO.class);
    }

    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .toList();
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CLIENTE_NOT_FOUND", "Cliente não encontrado"));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CLIENTE_NOT_FOUND", "Cliente não encontrado"));

        if (!existente.getEmail().equals(dto.getEmail())) {
            clienteRepository.findByEmail(dto.getEmail()).ifPresent(cliente -> {
                throw new BusinessException("CLIENTE_EMAIL_EXISTS", "Já existe cliente com este email");
            });
        }

        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());

        Cliente salvo = clienteRepository.save(existente);
        return modelMapper.map(salvo, ClienteDTO.class);
    }

    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new BusinessException("CLIENTE_NOT_FOUND", "Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }

    public Cliente obterEntidade(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("CLIENTE_NOT_FOUND", "Cliente não encontrado"));
    }
}

