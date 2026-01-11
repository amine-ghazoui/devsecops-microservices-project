package org.ghazoui.commandeservice;

import org.ghazoui.commandeservice.dtos.CommandeRequest;
import org.ghazoui.commandeservice.dtos.LigneCommandeRequest;
import org.ghazoui.commandeservice.services.CommandeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
public class CommandeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandeServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initTestData(CommandeService commandeService,
			org.ghazoui.commandeservice.feign.ProduitRestClient produitRestClient) {
		return args -> {
			System.out.println("🚀 Tentative d'initialisation des données de test pour les commandes...");

			// Attendre un peu que le produit-service soit prêt et enregistré dans Eureka
			int maxRetries = 5;
			int retryCount = 0;
			boolean success = false;

			while (retryCount < maxRetries && !success) {
				try {
					var produits = produitRestClient.getAllProduits();

					if (produits != null && !produits.isEmpty()) {
						System.out.println("📦 " + produits.size() + " produits trouvés. Création des commandes...");

						// Commande 1 : Les deux premiers produits
						if (produits.size() >= 2) {
							CommandeRequest commande1 = CommandeRequest.builder()
									.ligne(Arrays.asList(
											LigneCommandeRequest.builder()
													.idProduit(produits.get(0).getId())
													.quantite(2)
													.build(),
											LigneCommandeRequest.builder()
													.idProduit(produits.get(1).getId())
													.quantite(3)
													.build()))
									.build();
							commandeService.createCommande(commande1);
							System.out.println("✅ Commande 1 créée");
						}

						// Commande 2 : Un produit aléatoire
						if (produits.size() >= 3) {
							CommandeRequest commande2 = CommandeRequest.builder()
									.ligne(Arrays.asList(
											LigneCommandeRequest.builder()
													.idProduit(produits.get(2).getId())
													.quantite(1)
													.build()))
									.build();
							commandeService.createCommande(commande2);
							System.out.println("✅ Commande 2 créée");
						}

						System.out.println("🎉 Initialisation des commandes terminée avec succès!");
						success = true;
					} else {
						System.out.println("⚠️ Aucun produit trouvé. Nouvelle tentative dans 5s... (" + (retryCount + 1)
								+ "/" + maxRetries + ")");
						Thread.sleep(5000);
						retryCount++;
					}
				} catch (Exception e) {
					System.err.println("❌ Erreur lors de la tentative " + (retryCount + 1) + ": " + e.getMessage());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
					retryCount++;
				}
			}

			if (!success) {
				System.err.println("❌ Échec de l'initialisation des commandes après " + maxRetries + " tentatives.");
				System.err.println("💡 Vérifiez que 'produit-service' est bien démarré et accessible via Eureka.");
			}
		};
	}

}
