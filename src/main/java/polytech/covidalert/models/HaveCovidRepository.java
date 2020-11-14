package polytech.covidalert.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HaveCovidRepository extends JpaRepository<HaveCovid,Long> {

    List<HaveCovid> findAllByUserId(Long id);
}
