package org.ghazoui.commandeservice.dtos;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder
public class LigneCommandeRequest {

    Long idProduit;
    Integer quantite;
}
