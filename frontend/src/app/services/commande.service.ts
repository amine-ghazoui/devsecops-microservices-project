import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Commande } from '../models/commande.model';

@Injectable({
    providedIn: 'root'
})
export class CommandeService {
    private apiUrl = 'http://localhost:8888/api/commandes';

    constructor(private http: HttpClient) { }

    getAllCommandes(): Observable<Commande[]> {
        return this.http.get<Commande[]>(this.apiUrl);
    }

    getCommandeById(id: number): Observable<Commande> {
        return this.http.get<Commande>(`${this.apiUrl}/${id}`);
    }

    createCommande(commande: Commande): Observable<Commande> {
        return this.http.post<Commande>(this.apiUrl, commande);
    }

    getMesCommandes(): Observable<Commande[]> {
        return this.http.get<Commande[]>(`${this.apiUrl}/mes-commandes`);
    }
}
