package ru.clear.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clear.model.operation.operation_category.OperationCategory;
import ru.clear.repository.OperationCategoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final OperationCategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(OperationCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<OperationCategory> getCategory() {
        return categoryRepository.findAll();
    }
}
