package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.EnderecoDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Cliente;
import br.pucpr.lanchonete.model.Endereco;
import br.pucpr.lanchonete.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteService clienteService;
    private final ModelMapper modelMapper;

    public EnderecoDTO salvar(EnderecoDTO dto) {
        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        endereco.setCliente(cliente);
        Endereco salvo = enderecoRepository.save(endereco);
        return toDTO(salvo);
    }

    public List<EnderecoDTO> listar() {
        return enderecoRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public EnderecoDTO buscarPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ENDERECO_NOT_FOUND", "Endereço não encontrado"));
        return toDTO(endereco);
    }

    public EnderecoDTO atualizar(Long id, EnderecoDTO dto) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("ENDERECO_NOT_FOUND", "Endereço não encontrado"));
        Cliente cliente = clienteService.obterEntidade(dto.getClienteId());

        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setCliente(cliente);

        Endereco salvo = enderecoRepository.save(endereco);
        return toDTO(salvo);
    }

    public void excluir(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new BusinessException("ENDERECO_NOT_FOUND", "Endereço não encontrado");
        }
        enderecoRepository.deleteById(id);
    }

    private EnderecoDTO toDTO(Endereco endereco) {
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        dto.setClienteId(endereco.getCliente().getId());
        return dto;
    }
}

