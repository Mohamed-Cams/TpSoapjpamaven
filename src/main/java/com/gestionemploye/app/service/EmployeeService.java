package com.gestionemploye.app.service;

import com.gestionemploye.app.entity.Employee;
import com.gestionemploye.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee creer(Employee employee) {
        return repository.save(employee);
    }

    public Employee modifier(Employee employee) {
        return repository.save(employee);
    }

    public Employee getParId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public boolean supprimer(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Employee> listerTous() {
        return repository.findAll();
    }
}
