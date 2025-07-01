
package com.web.security;

import com.web.jsf.util.JsfUtil;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.ResourceHandler;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"}, servletNames = {"Faces Servlet"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @EJB
    com.web.session.AbstractFacadeQuerySavvy ejbFacade;

//    @Inject
//    private LoginView loginView;

    private boolean authorisedLink = true;

    public boolean isAuthorisedLink() {
        return authorisedLink;
    }

    public void setAuthorisedLink(boolean authorisedLink) {
        this.authorisedLink = authorisedLink;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private Map<String, String> uriArray = new HashMap<>();

    @PostConstruct
    public void init() {
        uriArray.put("/website/public/about.xhtml", "About");
    }

    public Map<String, String> getUriArray() {
        return uriArray;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            HttpSession session = reqt.getSession(false);
            String contextPath = reqt.getContextPath();
            String reqURI = reqt.getRequestURI();
            String uri = reqURI.substring(4);
            if(uri.contains(".xhtml")) {
                if(shouldSkipFilter(uri) || isSpecialRequest(uri)) {
                    // Skip filter for certain requests
                    chain.doFilter(request, response);
                    return;
                }
              String lable = resourceCollection.getResourceLabel(uri);
if(lable == null) {
                    lable = "No Resource Found";
                }
                handleNoCacheHeaders(reqt, resp);
                reqt.setAttribute("linkLable", lable);
                if(lable.equalsIgnoreCase("About")) {
                    reqt.getRequestDispatcher("/website/public/about.xhtml").forward(request, response);
                    return;
                }
                authorisedLink = false;
              if(lable.equalsIgnoreCase("No Resource Found")){
                    resp.sendRedirect(reqt.getContextPath() + "/404.xhtml");
                 //   JsfUtil.addWarningMessage("The requested page does not exist or is not accessible.");
                    return;
              } else if (adminResource(lable)) {
                    // If the resource is an admin resource, check if the user is logged in
                    if (isLoggedIn(session)) {
                        authorisedLink = true;
                    } else {
                        authorisedLink = false;
                    }
                } else {
                    authorisedLink = true; // Public resources are always accessible
                }
                if(!authorisedLink) {
                    resp.sendRedirect(reqt.getContextPath() + "/signin.xhtml");
                    return;
              }
                chain.doFilter(request, response);
                return;
            }
            if(shouldSkipFilter(uri) || isSpecialRequest(uri)) {
                // Skip filter for certain requests
                chain.doFilter(request, response);
                return;
            }
            if(uri.equals("/") || uri.equals("")) { // When the request is for the root context
                resp.sendRedirect(reqt.getContextPath() + "/Home");
            }else
            if(publicResource(uri)) {
              //  String uri = reqURI.substring(5);
                String link = uri;

                if (link == null) {
                    resp.sendRedirect(reqt.getContextPath() + "/Home");
                    return;
                }
                //  request.setAttribute("linkLable", lable);
                //  chain.doFilter(reqt, resp);
                String linkToForward = resourceCollection.getKeyByValue(link);
                if(linkToForward == null) {
                    resp.sendRedirect(reqt.getContextPath() + "/404.xhtml");
                    JsfUtil.addWarningMessage("The requested page does not exist or is not accessible.");
                    return;
                }
                reqt.getRequestDispatcher(linkToForward).forward(request, response);

                return;
            }else if(adminResource(uri)) {
                //We will consider the rest of the resources as protected
                if (isLoggedIn(session)) {
                    handleLoggedInRequest(reqURI, session, reqt, resp, chain, request, response);
                } else {
                    resp.sendRedirect(reqt.getContextPath() + "/signin.xhtml");
                    return;
                }

            }else{

                resp.sendRedirect(reqt.getContextPath() + "/404.xhtml");
                    return;

            }
//            String loginURI = contextPath + "/signin.xhtml";
//            boolean loginRequest = reqURI.equals(loginURI);
            // Forward request but display the correct link label


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean publicResource(String reqURI) {

        return  reqURI.contains("/Home")
                || reqURI.contains("/About")
                || reqURI.contains("/Services")
                || reqURI.contains("/Pricing")
                || reqURI.contains("/Partners")
                || reqURI.contains("/FAQ")
                || reqURI.contains("/Blog")
                || reqURI.contains("/Contact-Us")
                || reqURI.contains("/Privacy")
                || reqURI.contains("/Terms");
    }

    public boolean adminResource(String reqURI) {
        return  reqURI.contains("/User-Management")
                || reqURI.contains("/Service-Management")
                || reqURI.contains("/Product-Management")
                || reqURI.contains("/Testimonials-Management")
                || reqURI.contains("/Pricing-Management")
                || reqURI.contains("/Contacts-Management")
                || reqURI.contains("/Partners-Management")
                || reqURI.contains("/Faq-Management")
                || reqURI.contains("/Team-Members-Management")
                || reqURI.contains("/Impact-Statistics-Management")
                || reqURI.contains("/Content-Block-Management")
                || reqURI.contains("/Blog-Management");
    }

    private boolean shouldSkipFilter(String reqURI) {
        return reqURI.contains("/signin.xhtml") || reqURI.contains("/confirmation.xhtml")
                || reqURI.contains("/500.xhtml") || reqURI.contains("/400.xhtml")
                || reqURI.contains("/websocket") || reqURI.contains("/api/")



                || reqURI.contains("/updateTaskLabel") || reqURI.contains("/addTask")
                || reqURI.contains("/resources") || reqURI.contains("/activityUpdate")
                || reqURI.contains("/notifications")
                || reqURI.contains("/404.xhtml") || reqURI.contains("/403.xhtml")
                || reqURI.contains(".docx")
                || reqURI.contains(".csv") || reqURI.contains(".xlx")
                || reqURI.contains(".pdf") || reqURI.contains(".pptx") || reqURI.contains(".xlsx")
                || reqURI.contains("/logoImageServlet")
                || reqURI.contains("/register.xhtml");
    }

    private void handleNoCacheHeaders(HttpServletRequest reqt, HttpServletResponse resp) {
        if (!reqt.getRequestURI().startsWith(reqt.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);
        }
    }

    private boolean isSpecialRequest(String reqURI) {
        return reqURI.contains("EmailServlet")
                || reqURI.contains("jakarta.faces.resource");
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("username") != null;
    }

    @Inject
    private ResourceCollection resourceCollection;

    private void handleLoggedInRequest(String reqURI, HttpSession session, HttpServletRequest reqt, HttpServletResponse resp, FilterChain chain, ServletRequest request, ServletResponse response) throws IOException, ServletException {
        boolean authorised = false;

        //   request.setAttribute("linkLable", loginView.getUserLinkPrivileges().get(reqURI));
        if (reqURI.contains(".xhtml")) {
            chain.doFilter(request, response);
            return;
        }
        String uri = reqURI.substring(5);
        String link = uri;

        if (link == null) {
            resp.sendRedirect(reqt.getContextPath() + "/website/public/index.xhtml");
            return;
        }
        //   request.setAttribute("linkLable", lable);
        //  chain.doFilter(reqt, resp);
        String linkToForward = resourceCollection.getKeyByValue(link);
        if(linkToForward == null) {
            JsfUtil.addWarningMessage("The requested page does not exist or is not accessible.");

            return;
        }
        reqt.getRequestDispatcher(linkToForward).forward(request, response);


    }

    @Override
    public void destroy() {

        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
