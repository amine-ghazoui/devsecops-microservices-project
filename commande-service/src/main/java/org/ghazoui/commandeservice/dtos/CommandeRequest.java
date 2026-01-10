package org.ghazoui.commandeservice.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @Builder
public class CommandeRequest {

    private List<LigneCommandeRequest> ligne;
}
