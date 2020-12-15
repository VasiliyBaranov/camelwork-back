package camelwork.back.kernel.model.workbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkBookRepositories extends JpaRepository<WorkBook, UUID> {

    WorkBook findWorkBookByFirstName(String name);
}
