package org.ghazoui.commandeservice.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;

// DTO pour les réponses contenant les informations d'une commande

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CommandeResponseDTO {

    private Long id;
    private Date dateCommande;
    private String status;
    private Double montantTotal;
    private String userId;
    private List<LigneCommandeResponseDTO> ligneCommandes;
}
