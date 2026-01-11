import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit.model';

@Injectable({
    providedIn: 'root'
})
export class ProduitService {
    private apiUrl = 'http://localhost:8888/api/produits';

    constructor(private http: HttpClient) { }

    getAllProduits(): Observable<Produit[]> {
        return this.http.get<Produit[]>(this.apiUrl);
    }

    getProduitById(id: number): Observable<Produit> {
        return this.http.get<Produit>(`${this.apiUrl}/${id}`);
    }

    createProduit(produit: Produit): Observable<Produit> {
        return this.http.post<Produit>(this.apiUrl, produit);
    }

    updateProduit(id: number, produit: Produit): Observable<Produit> {
        return this.http.put<Produit>(`${this.apiUrl}/${id}`, produit);
    }

    deleteProduit(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    checkDisponibilite(id: number, quantite: number): Observable<boolean> {
        return this.http.get<boolean>(`${this.apiUrl}/${id}/disponibilite?quantite=${quantite}`);
    }
}
