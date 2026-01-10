package org.ghazoui.commandeservice.web;

import lombok.RequiredArgsConstructor;
import org.ghazoui.commandeservice.dtos.CommandeRequest;
import org.ghazoui.commandeservice.dtos.CommandeResponseDTO;
import org.ghazoui.commandeservice.services.CommandeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommandeResponseDTO createCommande(@RequestBody CommandeRequest request) {
        return commandeService.createCommande(request);
    }

    @GetMapping
    public List<CommandeResponseDTO> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/mes-commandes")
    public List<CommandeResponseDTO> getMesCommandes() {
        return commandeService.getMesCommandes();
    }

    @GetMapping("/{id}")
    public CommandeResponseDTO getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id);
    }
}
