package org.ghazoui.commandeservice.mappers;

import org.ghazoui.commandeservice.dtos.CommandeResponseDTO;
import org.ghazoui.commandeservice.dtos.LigneCommandeRequest;
import org.ghazoui.commandeservice.dtos.LigneCommandeResponseDTO;
import org.ghazoui.commandeservice.entities.Commande;
import org.ghazoui.commandeservice.entities.LigneCommande;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommandeMapper {

    /**
     * Convertir une entité Commande en CommandeResponseDTO
     */
    public CommandeResponseDTO fromCommande(Commande commande) {
        if (commande == null) {
            return null;
        }

        return CommandeResponseDTO.builder()
                .id(commande.getId())
                .dateCommande(commande.getDateCommande())
                .status(commande.getStatus())
                .montantTotal(commande.getMontantTotal())
                .userId(commande.getUserId())
                .ligneCommandes(commande.getLigneCommandes().stream()
                        .map(this::fromLigneCommande)
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Convertir une entité LigneCommande en LigneCommandeResponseDTO
     */
    public LigneCommandeResponseDTO fromLigneCommande(LigneCommande ligneCommande) {
        if (ligneCommande == null) {
            return null;
        }

        return LigneCommandeResponseDTO.builder()
                .id(ligneCommande.getId())
                .idProduit(ligneCommande.getIdProduit())
                .nomProduit(ligneCommande.getNomProduit())
                .quantite(ligneCommande.getQuantite())
                .prix(ligneCommande.getPrix())
                .build();
    }

    /**
     * Convertir un LigneCommandeRequest en entité LigneCommande
     */
    public LigneCommande fromLigneCommandeRequest(LigneCommandeRequest request) {
        if (request == null) {
            return null;
        }

        return LigneCommande.builder()
                .idProduit(request.getIdProduit())
                .quantite(request.getQuantite())
                .build();
    }
}
