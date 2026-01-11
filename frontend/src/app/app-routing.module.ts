import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProduitListComponent } from './components/produits/produit-list/produit-list.component';
import { ProduitFormComponent } from './components/produits/produit-form/produit-form.component';
import { CommandeListComponent } from './components/commandes/commande-list/commande-list.component';
import { CommandeDetailsComponent } from './components/commandes/commande-details/commande-details.component';
import { CommandeFormComponent } from './components/commandes/commande-form/commande-form.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'produits', component: ProduitListComponent },
  { path: 'produits/new', component: ProduitFormComponent },
  { path: 'produits/edit/:id', component: ProduitFormComponent },
  { path: 'commandes', component: CommandeListComponent },
  { path: 'commandes/new', component: CommandeFormComponent },
  { path: 'commandes/:id', component: CommandeDetailsComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
