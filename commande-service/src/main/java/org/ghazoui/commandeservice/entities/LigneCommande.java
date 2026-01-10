package org.ghazoui.commandeservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class LigneCommande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduit;
    private String nomProduit;
    private Integer quantite;
    private Double prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Commande commande;
}
