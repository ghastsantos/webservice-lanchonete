package br.pucpr.lanchonete.service;

import br.pucpr.lanchonete.dto.UserDTO;
import br.pucpr.lanchonete.exception.BusinessException;
import br.pucpr.lanchonete.model.Role;
import br.pucpr.lanchonete.model.User;
import br.pucpr.lanchonete.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserDTO criarUsuario(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail())
                .ifPresent(user -> {
                    throw new BusinessException("USER_EMAIL_EXISTS", "Já existe usuário com este email");
                });

        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            userDTO.setRoles(Set.of(Role.ROLE_USER));
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        User saved = userRepository.save(user);
        return toSafeDTO(saved);
    }

    public List<UserDTO> listarUsuarios() {
        return userRepository.findAll().stream()
                .map(this::toSafeDTO)
                .toList();
    }

    public UserDTO buscarPorId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "Usuário não encontrado"));
        return toSafeDTO(user);
    }

    public UserDTO atualizarUsuario(Long id, UserDTO userDTO) {
        User existente = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "Usuário não encontrado"));

        if (!existente.getEmail().equals(userDTO.getEmail())) {
            userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
                throw new BusinessException("USER_EMAIL_EXISTS", "Já existe usuário com este email");
            });
        }

        existente.setNome(userDTO.getNome());
        existente.setEmail(userDTO.getEmail());
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            existente.setRoles(existente.getRoles());
        } else {
            existente.setRoles(userDTO.getRoles());
        }
        if (userDTO.getSenha() != null && !userDTO.getSenha().isBlank()) {
            existente.setSenha(passwordEncoder.encode(userDTO.getSenha()));
        }

        User salvo = userRepository.save(existente);
        return toSafeDTO(salvo);
    }

    public void deletarUsuario(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("USER_NOT_FOUND", "Usuário não encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private UserDTO toSafeDTO(User user) {
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        dto.setSenha(null);
        return dto;
    }
}

