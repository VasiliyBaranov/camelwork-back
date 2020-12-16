package camelwork.back.kernel.model.workbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkBookRepositories extends JpaRepository<WorkBook, UUID> {

    WorkBook findWorkBookByFirstName(String name);


    @Query(value = "SELECT * FROM work_book WHERE first_name = :firstName AND last_name = :lastName",nativeQuery = true)
    WorkBook findWorkBookByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
