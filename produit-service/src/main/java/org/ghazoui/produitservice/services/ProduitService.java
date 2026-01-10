package org.ghazoui.produitservice.services;

import org.ghazoui.produitservice.dtos.ProduitRequestDTO;
import org.ghazoui.produitservice.dtos.ProduitResponseDTO;
import java.util.List;

public interface ProduitService {

    List<ProduitResponseDTO> getAllProduits();

    ProduitResponseDTO getProduitById(Long id);

    ProduitResponseDTO createProduit(ProduitRequestDTO produitRequestDTO);

    ProduitResponseDTO updateProduit(Long id, ProduitRequestDTO produitRequestDTO);

    void deleteProduit(Long id);

    Boolean checkDisponibilite(Long id, Integer quantite);

}
