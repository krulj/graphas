package graphas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import graphas.model.AsConnection;

@Transactional(readOnly = true) 
public interface AsConnectionRepository extends JpaRepository<AsConnection, Long> {

	@Query("SELECT a FROM AsConnection a WHERE a.from = :asn")
	List<AsConnection> getByAsNumber(@Param("asn") Long asn);

}
