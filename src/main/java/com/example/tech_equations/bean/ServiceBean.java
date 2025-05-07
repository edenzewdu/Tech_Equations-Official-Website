//package com.example.tech_equations.bean;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.faces.view.ViewScoped;
//import jakarta.inject.Named;
//
//import java.io.Serializable;
//import java.util.List;
//
//@Named("serviceBean")
//@ViewScoped
//public class ServiceBean implements Serializable {
//
//    private List<Service> services;
//
//    @PostConstruct
//    public void init() {
//        services = List.of(
//                new Service("Social Media", "/image/v113_57.png", "Get more website traffic for the next 10 years"),
//                new Service("Website Design", "/image/v113_57.png", "Your website has to impress people"),
//                new Service("Social Media2", "/image/v113_57.png", "Get more website traffic for the next 10 years"),
//                new Service("Website Design2", "/image/v113_57.png", "Your website has to impress people"),
//                new Service("Social Media3", "/image/v113_57.png", "Get more website traffic for the next 10 years"),
//                new Service("Website Design3", "/image/v113_57.png", "Your website has to impress people")
//        // Add more services here
//
//        );
//        System.out.println("Services initialized: " + services.size());  // Debugging line
//    }
//
//    public List<Service> getServices() {
//        return services;
//    }
//
//    public static class Service implements Serializable {
//        private String title;
//        private String imageUrl;
//        private String description;
//
//        public Service(String title, String imageUrl, String description) {
//            this.title = title;
//            this.imageUrl = imageUrl;
//            this.description = description;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public String getImageUrl() {
//            return imageUrl;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//    }
//}
