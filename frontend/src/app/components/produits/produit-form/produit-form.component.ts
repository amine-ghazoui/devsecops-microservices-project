import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProduitService } from '../../../services/produit.service';
import { Produit } from '../../../models/produit.model';

@Component({
  selector: 'app-produit-form',
  standalone: false,
  templateUrl: './produit-form.component.html',
  styleUrls: ['./produit-form.component.css']
})
export class ProduitFormComponent implements OnInit {
  produitForm: FormGroup;
  isEditMode: boolean = false;
  produitId?: number;
  loading: boolean = false;
  submitted: boolean = false;

  constructor(
    private fb: FormBuilder,
    private produitService: ProduitService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.produitForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      prix: ['', [Validators.required, Validators.min(0.01)]],
      quantiteStock: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.produitId = +params['id'];
        this.loadProduit(this.produitId);
      }
    });
  }

  loadProduit(id: number): void {
    this.loading = true;
    this.produitService.getProduitById(id).subscribe({
      next: (produit) => {
        this.produitForm.patchValue(produit);
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement du produit:', err);
        alert('Erreur lors du chargement du produit');
        this.router.navigate(['/produits']);
      }
    });
  }

  get f() {
    return this.produitForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.produitForm.invalid) {
      return;
    }

    this.loading = true;
    const produit: Produit = this.produitForm.value;

    if (this.isEditMode && this.produitId) {
      this.produitService.updateProduit(this.produitId, produit).subscribe({
        next: () => {
          this.router.navigate(['/produits']);
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour:', err);
          alert('Erreur lors de la mise à jour du produit');
          this.loading = false;
        }
      });
    } else {
      this.produitService.createProduit(produit).subscribe({
        next: () => {
          this.router.navigate(['/produits']);
        },
        error: (err) => {
          console.error('Erreur lors de la création:', err);
          alert('Erreur lors de la création du produit');
          this.loading = false;
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/produits']);
  }
}
