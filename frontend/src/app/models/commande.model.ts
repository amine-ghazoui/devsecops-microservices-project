import { LigneCommande } from './ligne-commande.model';

export interface Commande {
    id?: number;
    dateCommande?: Date;
    status?: string;
    montantTotal?: number;
    userId: string;
    ligneCommandes: LigneCommande[];
}
