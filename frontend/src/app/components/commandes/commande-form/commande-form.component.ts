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
    standalone: false,
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
        this.addLigneCommande(); // Add one line by default
    }

    loadProduits(): void {
        this.produitService.getAllProduits().subscribe({
            next: (data) => {
                this.produits = data;
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

    get f() {
        return this.commandeForm.controls;
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
        if (this.ligneCommandes.length > 1) {
            this.ligneCommandes.removeAt(index);
            this.calculateTotal();
        }
    }

    onProduitChange(index: number): void {
        this.calculateTotal();
    }

    onQuantiteChange(index: number): void {
        this.calculateTotal();
    }

    getProduitPrix(produitId: string): number {
        if (!produitId) return 0;
        const produit = this.produits.find(p => p.id === +produitId);
        return produit ? produit.prix : 0;
    }

    calculateTotal(): void {
        this.montantTotal = 0;
        this.ligneCommandes.controls.forEach(ligne => {
            const idProduit = ligne.get('idProduit')?.value;
            const quantite = ligne.get('quantite')?.value || 0;
            const prix = this.getProduitPrix(idProduit);
            this.montantTotal += prix * quantite;
        });
    }

    onSubmit(): void {
        this.submitted = true;

        if (this.commandeForm.invalid || this.ligneCommandes.length === 0) {
            return;
        }

        this.loading = true;

        const commandeData = {
            userId: this.commandeForm.value.userId,
            ligneCommandes: this.ligneCommandes.value.map((ligne: any) => ({
                idProduit: +ligne.idProduit,
                quantite: ligne.quantite
            }))
        };

        this.commandeService.createCommande(commandeData).subscribe({
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
