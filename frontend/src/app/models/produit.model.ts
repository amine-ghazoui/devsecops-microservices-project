export interface Produit {
    id?: number;
    nom: string;
    description: string;
    prix: number;
    quantiteStock: number;
    disponible?: boolean;
}
