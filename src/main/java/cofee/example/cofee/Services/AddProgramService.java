package cofee.example.cofee.Services;

import cofee.example.cofee.Entity.Program;
import cofee.example.cofee.Repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddProgramService {

    @Autowired
    private ProgramRepository repository;

    // CREATE Program
    public Program addProgram(Program program) {
        program.setId(null); // Ensure it's always new
        return repository.save(program);
    }

    // GET All Programs
    public List<Program> getAllPrograms() {
        return repository.findAll();
    }

    // UPDATE Program
    public Program updateProgram(Long id, Program updatedProgram) {
        Optional<Program> optional = repository.findById(id);
        if (optional.isPresent()) {
            Program program = optional.get();
            program.setName(updatedProgram.getName());
            program.setDepartment(updatedProgram.getDepartment());
            program.setSemester(updatedProgram.getSemester());
            program.setDuration(updatedProgram.getDuration());
            program.setDescription(updatedProgram.getDescription());
            return repository.save(program);
        }
        return null;
    }

    // DELETE Program
    public String deleteProgram(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Program deleted successfully";
        }
        return "Program not found";
    }
}