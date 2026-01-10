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
	CommandLineRunner initTestData(CommandeService commandeService) {
		return args -> {
			System.out.println("🚀 Initialisation des données de test pour les commandes...");

			try {
				// Commande 1 : Achat de plusieurs produits tech
				CommandeRequest commande1 = CommandeRequest.builder()
						.ligne(Arrays.asList(
								LigneCommandeRequest.builder()
										.idProduit(1L) // Dell XPS 15
										.quantite(2)
										.build(),
								LigneCommandeRequest.builder()
										.idProduit(6L) // AirPods Pro 2
										.quantite(3)
										.build()))
						.build();

				commandeService.createCommande(commande1);
				System.out.println("✅ Commande 1 créée avec succès");

				// Commande 2 : Achat de smartphones
				CommandeRequest commande2 = CommandeRequest.builder()
						.ligne(Arrays.asList(
								LigneCommandeRequest.builder()
										.idProduit(2L) // iPhone 15 Pro
										.quantite(1)
										.build(),
								LigneCommandeRequest.builder()
										.idProduit(3L) // Samsung Galaxy S24
										.quantite(1)
										.build()))
						.build();

				commandeService.createCommande(commande2);
				System.out.println("✅ Commande 2 créée avec succès");

				// Commande 3 : Setup bureau complet
				CommandeRequest commande3 = CommandeRequest.builder()
						.ligne(Arrays.asList(
								LigneCommandeRequest.builder()
										.idProduit(4L) // MacBook Pro M3
										.quantite(1)
										.build(),
								LigneCommandeRequest.builder()
										.idProduit(8L) // Logitech MX Master 3S
										.quantite(1)
										.build(),
								LigneCommandeRequest.builder()
										.idProduit(9L) // Clavier Keychron K2
										.quantite(1)
										.build(),
								LigneCommandeRequest.builder()
										.idProduit(10L) // Moniteur LG UltraWide
										.quantite(1)
										.build()))
						.build();

				commandeService.createCommande(commande3);
				System.out.println("✅ Commande 3 créée avec succès");

				System.out.println("🎉 Toutes les commandes de test ont été créées avec succès!");

			} catch (Exception e) {
				System.err.println("❌ Erreur lors de la création des commandes de test: " + e.getMessage());
				System.err.println("💡 Assurez-vous que le produit-service est démarré et accessible.");
			}
		};
	}

}
