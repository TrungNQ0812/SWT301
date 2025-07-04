package com.example.oss.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.oss.database.AppDatabase;
import com.example.oss.dao.CategoryDao;
import com.example.oss.entity.Category;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> rootCategories;
    private ExecutorService executor;

    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        categoryDao = database.categoryDao();
        allCategories = categoryDao.getAllCategories();
        rootCategories = categoryDao.getRootCategories();
        executor = Executors.newFixedThreadPool(2);
    }

    // Read operations
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Category>> getRootCategories() {
        return rootCategories;
    }

    public LiveData<List<Category>> getSubCategories(int parentId) {
        return categoryDao.getSubCategories(parentId);
    }

    public LiveData<Category> getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    // Write operations
    public void insertCategory(Category category) {
        executor.execute(() -> categoryDao.insertCategory(category));
    }

    public void updateCategory(Category category) {
        executor.execute(() -> categoryDao.updateCategory(category));
    }

    public void deleteCategory(Category category) {
        executor.execute(() -> categoryDao.deleteCategory(category));
    }

    // Business logic methods
    public void createRootCategory(String name, String description) {
        Category category = new Category(name, description, null);
        insertCategory(category);
    }

    public void createSubCategory(String name, String description, int parentId) {
        Category category = new Category(name, description, parentId);
        insertCategory(category);
    }
}