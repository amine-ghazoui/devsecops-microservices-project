package org.ghazoui.produitservice.services;

import lombok.RequiredArgsConstructor;
import org.ghazoui.produitservice.dtos.ProduitRequestDTO;
import org.ghazoui.produitservice.dtos.ProduitResponseDTO;
import org.ghazoui.produitservice.entities.Produit;
import org.ghazoui.produitservice.mappers.ProduitMapper;
import org.ghazoui.produitservice.repositories.ProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    @Override
    public List<ProduitResponseDTO> getAllProduits() {
        return produitRepository.findAll()
                .stream()
                .map(produitMapper::fromProduit)
                .collect(Collectors.toList());
    }

    @Override
    public ProduitResponseDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
        return produitMapper.fromProduit(produit);
    }

    @Override
    public ProduitResponseDTO createProduit(ProduitRequestDTO produitRequestDTO) {
        Produit produit = produitMapper.fromProduitRequestDTO(produitRequestDTO);
        Produit savedProduit = produitRepository.save(produit);
        return produitMapper.fromProduit(savedProduit);
    }

    @Override
    public ProduitResponseDTO updateProduit(Long id, ProduitRequestDTO produitRequestDTO) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        produitMapper.updateProduitFromDTO(produit, produitRequestDTO);
        Produit updatedProduit = produitRepository.save(produit);
        return produitMapper.fromProduit(updatedProduit);
    }

    @Override
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
        produitRepository.delete(produit);
    }

    @Override
    public Boolean checkDisponibilite(Long id, Integer quantite) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));

        return produit.getQuantiteStock() != null && produit.getQuantiteStock() >= quantite;
    }

}
