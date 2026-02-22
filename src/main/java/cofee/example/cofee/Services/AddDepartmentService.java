package cofee.example.cofee.Services;


import cofee.example.cofee.Entity.AddDepartment;
import cofee.example.cofee.Repository.AddDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddDepartmentService {
@Autowired
    private AddDepartmentRepository repository;



    // ADD Department
    public AddDepartment addDepartment(AddDepartment dept) {
        return repository.save(dept);
    }

    // GET All Departments
    public List<AddDepartment> getAllDepartments() {
        return repository.findAll();
    }

    // UPDATE Department
    public AddDepartment updateDepartment(Long id, AddDepartment updatedDept) {
        return repository.findById(id).map(dept -> {
            dept.setDepartmentname(updatedDept.getDepartmentname());
            dept.setSlug(updatedDept.getSlug());
            dept.setDescription(updatedDept.getDescription());
            return repository.save(dept);
        }).orElse(null);
    }

    // DELETE Department
    public String deleteDepartment(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Department deleted successfully";
        }
        return "Department not found";
    }
}