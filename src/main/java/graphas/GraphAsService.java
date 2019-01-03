package graphas;

import java.util.List;

public interface GraphAsService {

	List<ASInfo> getAll();

	ASInfo getById(Long id);

	ASInfo getByASNumber(Long asNumber);

	ASInfo save(ASInfo asInfo);

}
