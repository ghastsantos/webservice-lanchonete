package br.pucpr.lanchonete.controller;

import br.pucpr.lanchonete.dto.ItemPedidoDTO;
import br.pucpr.lanchonete.service.ItemPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/itens-pedido")
@Tag(name = "Itens do Pedido")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Criar item de pedido")
    public ResponseEntity<ItemPedidoDTO> criar(@Valid @RequestBody ItemPedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemPedidoService.salvar(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Listar itens de pedido")
    public ResponseEntity<List<ItemPedidoDTO>> listar() {
        return ResponseEntity.ok(itemPedidoService.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Buscar item de pedido por id")
    public ResponseEntity<ItemPedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemPedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @Operation(summary = "Atualizar item de pedido")
    public ResponseEntity<ItemPedidoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ItemPedidoDTO dto) {
        return ResponseEntity.ok(itemPedidoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir item de pedido")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        itemPedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

