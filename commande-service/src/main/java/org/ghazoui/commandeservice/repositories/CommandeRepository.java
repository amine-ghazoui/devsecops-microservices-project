package org.ghazoui.commandeservice.repositories;

import org.ghazoui.commandeservice.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByUserId(String userId);
}
