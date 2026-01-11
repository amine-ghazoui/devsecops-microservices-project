package org.ghazoui.commandeservice.feign;

import org.ghazoui.commandeservice.model.ProduitDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "produit-service")
public interface ProduitRestClient {

    @GetMapping("/api/produits")
    List<ProduitDto> getAllProduits();

    @GetMapping("/api/produits/{id}")
    ProduitDto getProduitById(@PathVariable("id") Long id);

    @GetMapping("/api/produits/{id}/disponibilite")
    Boolean checkDisponibilite(@PathVariable("id") Long id, @RequestParam("quantite") Integer quantite);
}
