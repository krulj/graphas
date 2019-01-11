package graphas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.neovisionaries.i18n.CountryCode;

interface ASInfoRepository extends JpaRepository<ASInfo, Long> {

	@Query("SELECT a FROM ASInfo a WHERE a.number = :asn")
	Optional<ASInfo> getByAsNumber(@Param("asn") Long asn);

	@Query("SELECT a FROM ASInfo a WHERE a.country = :c")
	List<ASInfo> getByCountry(@Param("c") CountryCode countryCode);
}
