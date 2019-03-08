package graphas;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import graphas.model.AsProperties;

public interface AsPropertiesRepository extends JpaRepository<AsProperties, Long> {

	@Query("SELECT a FROM AsProperties a WHERE a.name = :name")
	Optional<AsProperties> getByName(@Param("name") String name);

}
