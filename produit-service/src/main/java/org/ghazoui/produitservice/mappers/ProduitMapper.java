package org.ghazoui.produitservice.mappers;

import org.ghazoui.produitservice.dtos.ProduitRequestDTO;
import org.ghazoui.produitservice.dtos.ProduitResponseDTO;
import org.ghazoui.produitservice.entities.Produit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class ProduitMapper {


    public Produit fromProduitRequestDTO(ProduitRequestDTO produitRequestDTO) {
        Produit produit = new Produit();
        BeanUtils.copyProperties(produitRequestDTO, produit);
        return produit;
    }


    public ProduitResponseDTO fromProduit(Produit produit) {
        ProduitResponseDTO produitResponseDTO = new ProduitResponseDTO();
        BeanUtils.copyProperties(produit, produitResponseDTO);
        // Calcul de la disponibilité
        produitResponseDTO.setDisponible(produit.getQuantiteStock() != null && produit.getQuantiteStock() > 0);
        return produitResponseDTO;
    }


    public void updateProduitFromDTO(Produit produit, ProduitRequestDTO produitRequestDTO) {
        BeanUtils.copyProperties(produitRequestDTO, produit, "id");
    }

}
