package org.ghazoui.produitservice.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ghazoui.produitservice.dtos.ProduitRequestDTO;
import org.ghazoui.produitservice.dtos.ProduitResponseDTO;
import org.ghazoui.produitservice.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@AllArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    public List<ProduitResponseDTO> getAllProduits() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ProduitResponseDTO getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProduitResponseDTO createProduit(@Valid @RequestBody ProduitRequestDTO produitRequestDTO) {
        return produitService.createProduit(produitRequestDTO);
    }

    @PutMapping("/{id}")
    public ProduitResponseDTO updateProduit(@PathVariable Long id,
            @Valid @RequestBody ProduitRequestDTO produitRequestDTO) {
        return produitService.updateProduit(id, produitRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
    }

    @GetMapping("/{id}/disponibilite")
    public Boolean checkDisponibilite(@PathVariable Long id, @RequestParam("quantite") Integer quantite) {
        return produitService.checkDisponibilite(id, quantite);
    }

}
