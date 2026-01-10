package org.ghazoui.commandeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter @Builder
public class ProduitDto {

    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteStock;
}
