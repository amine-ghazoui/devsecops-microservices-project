import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommandeService } from '../../../services/commande.service';
import { Commande } from '../../../models/commande.model';

@Component({
  selector: 'app-commande-list',
  standalone: false,
  templateUrl: './commande-list.component.html',
  styleUrls: ['./commande-list.component.css']
})
export class CommandeListComponent implements OnInit {
  commandes: Commande[] = [];
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private commandeService: CommandeService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCommandes();
  }

  loadCommandes(): void {
    this.loading = true;
    this.commandeService.getAllCommandes().subscribe({
      next: (data) => {
        this.commandes = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des commandes:', err);
        this.errorMessage = 'Impossible de charger les commandes';
        this.loading = false;
      }
    });
  }

  viewDetails(id: number): void {
    this.router.navigate(['/commandes', id]);
  }

  createCommande(): void {
    this.router.navigate(['/commandes/new']);
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
}
