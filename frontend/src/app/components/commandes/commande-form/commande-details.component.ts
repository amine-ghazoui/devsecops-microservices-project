import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CommandeService } from '../../../services/commande.service';
import { ProduitService } from '../../../services/produit.service';
import { Produit } from '../../../models/produit.model';
import { Commande } from '../../../models/commande.model';
import { LigneCommande } from '../../../models/ligne-commande.model';

@Component({
  selector: 'app-commande-form',
  templateUrl: './commande-form.component.html',
  styleUrls: ['./commande-form.component.css']
})
export class CommandeFormComponent implements OnInit {
  commandeForm: FormGroup;
  produits: Produit[] = [];
  loading: boolean = false;
  submitted: boolean = false;
  montantTotal: number = 0;

  constructor(
    private fb: FormBuilder,
    private commandeService: CommandeService,
    private produitService: ProduitService,
    private router: Router
  ) {
    this.commandeForm = this.fb.group({
      userId: ['', Validators.required],
      ligneCommandes: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.loadProduits();
    this.addLigneCommande();
  }

  loadProduits(): void {
    this.produitService.getAllProduits().subscribe({
      next: (data) => {
        this.produits = data.filter(p => p.disponible);
      },
      error: (err) => {
        console.error('Erreur lors du chargement des produits:', err);
        alert('Erreur lors du chargement des produits');
      }
    });
  }

  get ligneCommandes(): FormArray {
    return this.commandeForm.get('ligneCommandes') as FormArray;
  }

  createLigneCommande(): FormGroup {
    return this.fb.group({
      idProduit: ['', Validators.required],
      quantite: [1, [Validators.required, Validators.min(1)]]
    });
  }

  addLigneCommande(): void {
    this.ligneCommandes.push(this.createLigneCommande());
  }

  removeLigneCommande(index: number): void {
    this.ligneCommandes.removeAt(index);
    this.calculateTotal();
  }

  onProduitChange(index: number): void {
    this.calculateTotal();
  }

  onQuantiteChange(index: number): void {
    this.calculateTotal();
  }

  calculateTotal(): void {
    this.montantTotal = 0;
    this.ligneCommandes.controls.forEach(control => {
      const idProduit = control.get('idProduit')?.value;
      const quantite = control.get('quantite')?.value || 0;

      if (idProduit) {
        const produit = this.produits.find(p => p.id === +idProduit);
        if (produit) {
          this.montantTotal += produit.prix * quantite;
        }
      }
    });
  }

  getProduitPrix(idProduit: number): number {
    const produit = this.produits.find(p => p.id === idProduit);
    return produit ? produit.prix : 0;
  }

  get f() {
    return this.commandeForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.commandeForm.invalid || this.ligneCommandes.length === 0) {
      alert('Veuillez remplir tous les champs requis et ajouter au moins un produit');
      return;
    }

    this.loading = true;

    const lignes: LigneCommande[] = this.ligneCommandes.value.map((ligne: any) => {
      const produit = this.produits.find(p => p.id === +ligne.idProduit);
      return {
        idProduit: +ligne.idProduit,
        nomProduit: produit?.nom,
        quantite: ligne.quantite,
        prix: produit?.prix || 0
      };
    });

    const commande: Commande = {
      userId: this.commandeForm.value.userId,
      ligneCommandes: lignes
    };

    this.commandeService.createCommande(commande).subscribe({
      next: () => {
        this.router.navigate(['/commandes']);
      },
      error: (err) => {
        console.error('Erreur lors de la création de la commande:', err);
        alert('Erreur lors de la création de la commande');
        this.loading = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/commandes']);
  }
}
