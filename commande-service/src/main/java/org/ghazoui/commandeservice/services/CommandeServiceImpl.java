package org.ghazoui.commandeservice.services;

import lombok.RequiredArgsConstructor;
import org.ghazoui.commandeservice.dtos.CommandeRequest;
import org.ghazoui.commandeservice.dtos.CommandeResponseDTO;
import org.ghazoui.commandeservice.entities.Commande;
import org.ghazoui.commandeservice.entities.LigneCommande;
import org.ghazoui.commandeservice.feign.ProduitRestClient;
import org.ghazoui.commandeservice.mappers.CommandeMapper;
import org.ghazoui.commandeservice.model.ProduitDto;
import org.ghazoui.commandeservice.repositories.CommandeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final ProduitRestClient produitRestClient;

    @Override
    public CommandeResponseDTO createCommande(CommandeRequest request) {
        Commande commande = Commande.builder()
                .userId("user-temp") // TODO: Récupérer depuis le contexte de sécurité
                .build();

        // Créer les lignes de commande avec les informations des produits
        request.getLigne().forEach(ligneRequest -> {
            System.out
                    .println("🔍 [DEBUG] CommandeService -> ProduitService (ID: " + ligneRequest.getIdProduit() + ")");
            // Récupérer les informations du produit via Feign
            ProduitDto produit = produitRestClient.getProduitById(ligneRequest.getIdProduit());

            if (produit == null) {
                System.err
                        .println("❌ [DEBUG] produit-service a retourné NULL pour l'ID: " + ligneRequest.getIdProduit());
                throw new RuntimeException("Produit " + ligneRequest.getIdProduit() + " non trouvé.");
            }

            System.out.println("📦 [DEBUG] Produit récupéré: " + produit.getNom());

            LigneCommande ligne = LigneCommande.builder()
                    .idProduit(produit.getId())
                    .nomProduit(produit.getNom())
                    .quantite(ligneRequest.getQuantite())
                    .prix(produit.getPrix())
                    .build();

            commande.addLigneCommande(ligne);
        });

        // Calculer le montant total
        commande.calculerMontantTotal();

        // Sauvegarder la commande
        Commande savedCommande = commandeRepository.save(commande);

        return commandeMapper.fromCommande(savedCommande);
    }

    @Override
    public List<CommandeResponseDTO> getMesCommandes() {
        // TODO: Filtrer par userId depuis le contexte de sécurité
        return commandeRepository.findAll().stream()
                .map(commandeMapper::fromCommande)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeResponseDTO> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::fromCommande)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeResponseDTO getCommandeById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
        return commandeMapper.fromCommande(commande);
    }
}
