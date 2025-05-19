package com.example.tech_equations.controller;

import com.example.tech_equations.model.Service;
import com.example.tech_equations.repository.ServiceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static org.primefaces.component.focus.FocusBase.PropertyKeys.context;


@Named("serviceController")
@ApplicationScoped
public class ServiceController implements Serializable {

    private final ServiceRepository serviceRepository;

    private String name;
    private String description;
    private String searchTerm;
    private Service tempEditedService;

    private Service selectedService;

    private List<Service> services;
    private List<Service> filteredServices;
    private Service service = new Service();
    private boolean showCreateForm = false;

    @Inject
    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @PostConstruct
    public void init() {
        loadServices();
    }

    public void loadServices() {
        this.services = serviceRepository.getAllServices();
    }

    public List<Service> getServices() {
        return services;
    }

    public List<Service> searchServices() {
        services = serviceRepository.searchServices(searchTerm);
        return services;
    }

    public Service findServiceById(String id) {
        return serviceRepository.getServiceById(id);
    }

    public String createService() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (name == null || description == null || name.isEmpty() || description.isEmpty()) {
            return "Error: Name and description are required"; // Name and description are required
        }

        try {
            this.service = new Service(); // Reset the Service form
            Service newService = new Service();
            newService.setName(name);
            newService.setDescription(description);
            newService.setCreatedAt(LocalDateTime.now());
            newService.setUpdatedAt(LocalDateTime.now());

            boolean created = serviceRepository.saveService(newService);

            if (created) {
                context.addMessage(null, new FacesMessage("Service created successfully"));
                loadServices(); // Refresh the list of services
                showCreateForm = false;
                return "service-list?faces-redirect=true";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Service creation failed", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Optionally replace with logger
            return "Error: Unexpected error";
        }
    }

    // CRUD Operations
    public void deleteService() {
        try {
            if (selectedService != null) {
                serviceRepository.deleteServiceById(selectedService.getId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Service deleted successfully"));
                loadServices();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Service not selected", "Please select a service first"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Delete failed", "Could not delete service"));
        }
        serviceRepository.deleteServiceById(service.getId());
        loadServices();
    }

    public void save() {
        if (service.getId() == null) {
            service.setCreatedAt(LocalDateTime.now());
            service.setUpdatedAt(LocalDateTime.now());
            serviceRepository.saveService(service);
        } else {
            service.setUpdatedAt(LocalDateTime.now());
            serviceRepository.updateService(service);
        }
        clearForm();
        loadServices();
    }

    public void onRowEdit(RowEditEvent<Service> event) {
        tempEditedService = event.getObject(); // Store temporarily
    }

    public void confirmEdit() {
        if (tempEditedService != null) {
            tempEditedService.setUpdatedAt(LocalDateTime.now());
            serviceRepository.updateService(tempEditedService);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User edited successfully"));
            loadServices(); // Refresh list
            tempEditedService = null;
        }
    }
    public void cancelEdit() {
        this.services = serviceRepository.getAllServices();
        // Optionally reload data or reset table
        tempEditedService = null;
    }

    public void clearForm() {
        this.service = new Service();
    }

    public void prepareDelete(Service service) {
        this.selectedService = service;
    }

    public void edit(Service selectedService) {
        this.service = selectedService;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
}
