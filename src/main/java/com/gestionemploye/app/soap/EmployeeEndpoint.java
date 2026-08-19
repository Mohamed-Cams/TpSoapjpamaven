package com.gestionemploye.app.soap;

import com.gestionemploye.app.entity.Employee;
import com.gestionemploye.app.service.EmployeeService;
import com.gestionemploye.app.soap.generated.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "http://exemple.com/gestionemploye/employees";

    @Autowired
    private EmployeeService service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "employeRequest")
    @ResponsePayload
    public EmployeResponse getEmploye(@RequestPayload EmployeRequest request) {
        Employee employee = service.getParId(request.getId());
        EmployeResponse response = new EmployeResponse();
        response.setEmploye(toXml(employee));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "creerEmployeRequest")
    @ResponsePayload
    public EmployeResponse creerEmploye(@RequestPayload CreerEmployeRequest request) {
        Employee employee = toEntity(request.getEmploye());
        Employee saved = service.creer(employee);
        EmployeResponse response = new EmployeResponse();
        response.setEmploye(toXml(saved));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifierEmployeRequest")
    @ResponsePayload
    public EmployeResponse modifierEmploye(@RequestPayload ModifierEmployeRequest request) {
        Employee employee = toEntity(request.getEmploye());
        Employee updated = service.modifier(employee);
        EmployeResponse response = new EmployeResponse();
        response.setEmploye(toXml(updated));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "supprimerEmployeRequest")
    @ResponsePayload
    public SupprimerEmployeResponse supprimerEmploye(@RequestPayload SupprimerEmployeRequest request) {
        boolean succes = service.supprimer(request.getId());
        SupprimerEmployeResponse response = new SupprimerEmployeResponse();
        response.setSucces(succes);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listeEmployesRequest")
    @ResponsePayload
    public ListeEmployesResponse listerEmployes(@RequestPayload ListeEmployesRequest request) {
        ListeEmployesResponse response = new ListeEmployesResponse();
        service.listerTous().forEach(e -> response.getEmploye().add(toXml(e)));
        return response;
    }

    // Mapping manuel entre l'entité JPA et les classes générées du XSD
    private Employe toXml(Employee e) {
        if (e == null) return null;
        com.gestionemploye.app.soap.generated.Employe xml = new com.gestionemploye.app.soap.generated.Employe();
        xml.setId(e.getId());
        xml.setNom(e.getNom());
        xml.setPrenom(e.getPrenom());
        xml.setEmail(e.getEmail());
        xml.setPoste(e.getPoste());
        return xml;
    }

    private Employee toEntity(com.gestionemploye.app.soap.generated.Employe xml) {
        Employee e = new Employee();
        e.setId(xml.getId());
        e.setNom(xml.getNom());
        e.setPrenom(xml.getPrenom());
        e.setEmail(xml.getEmail());
        e.setPoste(xml.getPoste());
        return e;
    }
}