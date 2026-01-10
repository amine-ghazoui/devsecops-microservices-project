package org.ghazoui.produitservice.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

//DTO pour les requêtes de création et modification de produits

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class ProduitRequestDTO {

    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteStock;
}
