package org.ghazoui.produitservice.dtos;

import lombok.*;

//DTO pour les réponses contenant les informations d'un produit

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class ProduitResponseDTO {

    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteStock;
    private Boolean disponible;
}
