package org.ghazoui.commandeservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class Commande {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCommande;
    private String status;
    private Double montantTotal;
    private String userId;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        this.dateCommande = new Date();
        this.status = "EN_ATTENTE";
    }

    public void addLigneCommande(LigneCommande ligne) {
        ligneCommandes.add(ligne);
        ligne.setCommande(this);
    }

    public void calculerMontantTotal() {
        this.montantTotal = ligneCommandes.stream()
                .mapToDouble(ligne -> ligne.getPrix() * ligne.getQuantite())
                .sum();
    }
}
