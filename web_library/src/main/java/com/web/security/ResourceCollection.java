package com.web.security;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An application-scoped bean that holds a central, read-only collection of
 * protected application resources (URIs) and their user-friendly labels.
 *
 * This class is designed to be thread-safe and is initialized once at application startup.
 * It is primarily used by security filters and other parts of the application to
 * check for protected pages and retrieve their descriptive names.
 *
 * Example Usage:
 * - A security filter checks if a requested URI is in this collection.
 * - If access is denied, the filter can use the label from this collection
 *   to show a user-friendly message like "You do not have permission to access 'Sales Reports'".
 */
@Named("resourceCollection")
@ApplicationScoped
public class ResourceCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The core data structure holding the protected resources.
     * Key: The URI pattern (e.g., "/app/reports/sales.xhtml")
     * Value: The user-friendly label (e.g., "Sales Reports")
     *
     * We use ConcurrentHashMap to ensure thread safety, as this bean is application-scoped
     * and will be accessed by multiple request threads.
     */
    private Map<String, String> protectedResources;

    /**
     * This method is automatically called by the container after the bean is constructed.
     * It's the ideal place to initialize our collection of resources.
     * In a real-world application, this data might be loaded from a database or a configuration file.
     * For this example, we will hardcode the values.
     */
    @PostConstruct
    public void init() {
        // Use a ConcurrentHashMap for thread safety
        protectedResources = new ConcurrentHashMap<>();

        // --- Populate the map with your application's protected resources ---

        // Administrative Pages
        protectedResources.put("/website/admin/users.xhtml", "/User-Management");
        protectedResources.put("/website/admin/services.xhtml", "/Service-Management");
        protectedResources.put("/website/admin/product.xhtml", "/Product-Management");
        protectedResources.put("/website/admin/testimonials.xhtml", "/Testimonials-Management");
        protectedResources.put("/website/admin/pricing.xhtml", "/Pricing-Management");
        protectedResources.put("/website/admin/contacts.xhtml", "/Contacts-Management");
        protectedResources.put("/website/admin/partners.xhtml", "/Partners-Management");
        protectedResources.put("/website/admin/faq.xhtml", "/Faq-Management");
        protectedResources.put("/website/admin/team-members.xhtml", "/Team-Members-Management");
        protectedResources.put("/website/admin/ImpactStat.xhtml", "/Impact-Statistics-Management");
        protectedResources.put("/website/admin/content-edit.xhtml", "/Content-Block-Management");
        protectedResources.put("/website/admin/blog.xhtml", "/Blog-Management");

        // Add all other protected pages here...
        protectedResources.put("/website/public/index.xhtml", "/Home");
        protectedResources.put("/website/public/about.xhtml", "/About");
        protectedResources.put("/website/public/service.xhtml", "/Services");
        protectedResources.put("/website/public/pricing.xhtml", "/Pricing");
        protectedResources.put("/website/public/partners.xhtml", "/Partners");
        protectedResources.put("/website/public/faq.xhtml", "/FAQ");
        protectedResources.put("/website/public/contact.xhtml", "/Contact-Us");
        protectedResources.put("/website/public/blog.xhtml", "/Blog");
        protectedResources.put("/website/public/product.xhtml", "/Product");
        protectedResources.put("/website/public/login.xhtml", "/Login");
        protectedResources.put("/website/admin/dashboard.xhtml", "/Dashboard");
        protectedResources.put("/website/public/privacy.xhtml", "/Privacy");
        protectedResources.put("/website/public/terms.xhtml", "/Terms");

        System.out.println("ResourceCollection initialized with " + protectedResources.size() + " protected resources.");
    }

    /**
     * Checks if a given URI corresponds to a protected resource.
     *
     * @param requestURI The URI to check (e.g., from HttpServletRequest.getRequestURI()).
     *                   This method expects the URI to include the context path.
     * @return true if the URI is a key in the protected resources map, false otherwise.
     */
    public boolean isResourceProtected(String requestURI) {
        if (requestURI == null || requestURI.isBlank()) {
            return false;
        }
        // This performs a direct key lookup, which is very fast.
        return protectedResources.containsKey(requestURI);
    }

    /**
     * Retrieves the user-friendly label for a given protected URI.
     *
     * @param requestURI The protected URI.
     * @return An Optional containing the label if the URI is found, otherwise an empty Optional.
     */
    public String getResourceLabel(String requestURI) {
        if (requestURI == null) {
            return "No Resource Found";
        }
        // 'get' on a map returns null if the key doesn't exist.
        // Optional.ofNullable handles this gracefully.
        return protectedResources.get(requestURI);
    }

    public String getKeyByValue(String value) {
        for (Map.Entry<String, String> entry : protectedResources.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null; // or throw exception / handle not found case
    }

    /**
     * Returns an unmodifiable view of the entire resource map.
     * This is useful for debugging or for displaying all protected resources,
     * while preventing any modifications to the map itself.
     *
     * @return An unmodifiable Map of all protected resources.
     */
    public Map<String, String> getAllProtectedResources() {
        return Map.copyOf(protectedResources);
    }
}
