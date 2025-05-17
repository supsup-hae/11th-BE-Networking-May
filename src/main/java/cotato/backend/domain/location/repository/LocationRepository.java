package cotato.backend.domain.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cotato.backend.domain.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
	boolean existsByName(String name);
}