package org.ghazoui.commandeservice.services;

import org.ghazoui.commandeservice.dtos.CommandeRequest;
import org.ghazoui.commandeservice.dtos.CommandeResponseDTO;

import java.util.List;

public interface CommandeService {

    CommandeResponseDTO createCommande(CommandeRequest request);

    List<CommandeResponseDTO> getMesCommandes();

    List<CommandeResponseDTO> getAllCommandes();

    CommandeResponseDTO getCommandeById(Long id);
}
