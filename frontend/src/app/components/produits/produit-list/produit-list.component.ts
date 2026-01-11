import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProduitService } from '../../../services/produit.service';
import { Produit } from '../../../models/produit.model';

@Component({
  selector: 'app-produit-list',
  standalone: false,
  templateUrl: './produit-list.component.html',
  styleUrls: ['./produit-list.component.css']
})
export class ProduitListComponent implements OnInit {
  produits: Produit[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private produitService: ProduitService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadProduits();
  }

  loadProduits(): void {
    this.loading = true;
    this.produitService.getAllProduits().subscribe({
      next: (data) => {
        this.produits = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des produits:', err);
        this.errorMessage = 'Impossible de charger les produits';
        this.loading = false;
      }
    });
  }

  editProduit(id: number): void {
    this.router.navigate(['/produits/edit', id]);
  }

  deleteProduit(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
      this.produitService.deleteProduit(id).subscribe({
        next: () => {
          this.loadProduits();
        },
        error: (err) => {
          console.error('Erreur lors de la suppression:', err);
          alert('Erreur lors de la suppression du produit');
        }
      });
    }
  }

  createProduit(): void {
    this.router.navigate(['/produits/new']);
  }
}
