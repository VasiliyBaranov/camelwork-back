package camelwork.back.kernel.model.workbook;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkBookService {
    @Autowired
    private final WorkBookRepositories repository;

    public List<WorkBook> findAllWorkBooks() {
        return  repository.findAll();
    }

    public WorkBook findWorkBookByName(String name) {
        return repository.findWorkBookByFirstName(name);
    }

    public WorkBook addWorkBook(WorkBook book) {
        return repository.save(book);
    }

    public void removeWorkBook(UUID bookId) {
        repository.deleteById(bookId);
    }
}

