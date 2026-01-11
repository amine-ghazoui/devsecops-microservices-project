import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../../services/produit.service';
import { CommandeService } from '../../services/commande.service';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  totalProduits: number = 0;
  totalCommandes: number = 0;
  loading: boolean = true;

  constructor(
    private produitService: ProduitService,
    private commandeService: CommandeService
  ) { }

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.produitService.getAllProduits().subscribe({
      next: (produits) => {
        this.totalProduits = produits.length;
      },
      error: (err) => console.error('Erreur chargement produits:', err)
    });

    this.commandeService.getAllCommandes().subscribe({
      next: (commandes) => {
        this.totalCommandes = commandes.length;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur chargement commandes:', err);
        this.loading = false;
      }
    });
  }
}
