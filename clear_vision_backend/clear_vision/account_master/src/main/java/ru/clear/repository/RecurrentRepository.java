package ru.clear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clear.model.recurrent.Recurrent;

import java.util.UUID;

@Repository
public interface RecurrentRepository extends JpaRepository<Recurrent, UUID> {
}
