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
			new Thread(() -> {
				System.out.println("🚀 [DEBUG] Démarrage du thread d'initialisation des données...");

				int maxRetries = 12;
				int retryCount = 0;
				boolean success = false;

				while (retryCount < maxRetries && !success) {
					try {
						System.out.println("🔍 [DEBUG] Tentative d'appel à produit-service (getAllProduits)...");
						var produits = produitRestClient.getAllProduits();

						if (produits != null && !produits.isEmpty()) {
							System.out.println(
									"📦 [DEBUG] Connexion réussie ! " + produits.size() + " produits récupérés.");

							// Commande 1
							if (produits.size() >= 1) {
								System.out.println("📝 [DEBUG] Tentative de création de la commande de test 1...");
								CommandeRequest commande1 = CommandeRequest.builder()
										.ligne(Arrays.asList(
												LigneCommandeRequest.builder()
														.idProduit(produits.get(0).getId())
														.quantite(2)
														.build()))
										.build();
								commandeService.createCommande(commande1);
								System.out.println("✅ [DEBUG] Commande 1 créée avec succès.");
							}

							System.out.println("🎉 [DEBUG] Initialisation terminée.");
							success = true;
						} else {
							System.out.println("⚠️ [DEBUG] produit-service a répondu mais la liste est vide. Tentative "
									+ (retryCount + 1));
							Thread.sleep(5000);
							retryCount++;
						}
					} catch (Exception e) {
						System.err.println("❌ [DEBUG] ÉCHEC de communication avec produit-service : " + e.getMessage());
						// Log plus de détails sur l'exception si possible
						if (e.getCause() != null)
							System.err.println("   Cause: " + e.getCause().getMessage());

						try {
							Thread.sleep(8000);
						} catch (InterruptedException ie) {
							Thread.currentThread().interrupt();
						}
						retryCount++;
					}
				}
			}).start();
		};
	}

}
