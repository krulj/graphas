package graphas;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neovisionaries.i18n.CountryCode;

import graphas.model.ASInfo;

interface AsInfoRepository extends JpaRepository<ASInfo, Long> {

	@Query("SELECT a FROM ASInfo a WHERE a.number = :asn")
	Optional<ASInfo> getByAsNumber(@Param("asn") Long asn);

	@Query("SELECT a FROM ASInfo a WHERE a.country = :c")
	List<ASInfo> getByCountry(@Param("c") CountryCode countryCode);
	
	@Query("SELECT DISTINCT country FROM ASInfo")
	List<CountryCode> getAllCountries();
}
