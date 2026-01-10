package org.ghazoui.commandeservice.dtos;

import lombok.*;

// DTO pour les réponses contenant les informations d'une ligne de commande

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LigneCommandeResponseDTO {

    private Long id;
    private Long idProduit;
    private String nomProduit;
    private Integer quantite;
    private Double prix;
}
