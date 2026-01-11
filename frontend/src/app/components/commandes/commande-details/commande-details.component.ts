import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommandeService } from '../../../services/commande.service';
import { Commande } from '../../../models/commande.model';

@Component({
  selector: 'app-commande-details',
  standalone: false,
  templateUrl: './commande-details.component.html',
  styleUrls: ['./commande-details.component.css']
})
export class CommandeDetailsComponent implements OnInit {
  commande?: Commande;
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private commandeService: CommandeService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      this.loadCommande(id);
    });
  }

  loadCommande(id: number): void {
    this.loading = true;
    this.commandeService.getCommandeById(id).subscribe({
      next: (data) => {
        this.commande = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de la commande:', err);
        this.errorMessage = 'Impossible de charger la commande';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/commandes']);
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'EN_ATTENTE':
        return 'bg-warning';
      case 'VALIDEE':
        return 'bg-success';
      case 'ANNULEE':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }

  getTotalQuantity(): number {
    if (!this.commande || !this.commande.ligneCommandes) {
      return 0;
    }
    return this.commande.ligneCommandes.reduce((sum, ligne) => sum + ligne.quantite, 0);
  }
}
