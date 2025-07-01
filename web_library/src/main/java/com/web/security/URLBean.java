/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.web.security;

//import com.web.entity.PrevilageTable;
import com.web.jsf.util.URLUtils;
import com.web.session.AbstractFacadeQuerySavvy;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Lucy
 */
@Named(value = "uRLBean")
@RequestScoped
public class URLBean {

    /**
     * Creates a new instance of URLBean
     */
    public URLBean() {
    }

    @EJB
    AbstractFacadeQuerySavvy ejbFacade;
    /**
     * Pass without the ERP name
     *
     * @param aLink
     * @return
     */
    public String encodedLink(String aLink) {
        if (aLink != null && !aLink.equalsIgnoreCase("")) {
            String encodedLink = URLUtils.encode(aLink);
            String redirectUrl = "/pos/savvypos?enc=" + encodedLink;
            return redirectUrl;
        }
        return null;
    }

    /**
     * Pass without the ERP name
     *
     * @param aLink
     */
    public void encryptedLink(String aLink) {
        if (aLink != null && !aLink.equalsIgnoreCase("")) {
            String redirectUrl = encodedLink(aLink);
//            FacesContext context = FacesContext.getCurrentInstance();
//            ELContext elContext = context.getELContext();
//            SalesOrderModule salesOrderModule = (SalesOrderModule) elContext.getELResolver().getValue(elContext, null, "salesOrderModule");
//
//            ReportChartsTypeInfoController reportChartsTypeInfoController = (ReportChartsTypeInfoController) elContext.getELResolver().getValue(elContext, null, "reportChartsTypeInfoController");
//            reportChartsTypeInfoController.setReportChartInfos(new ReportChartsInformation[20]);
//            reportChartsTypeInfoController.setReportChartTypes(new ReportChartsTypeInfo[20]);
//            salesOrderModule.redirectToReport();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
//            return str + "/inventory/xyz?encoded=" + encodedLink + "?faces-redirect=true";
        }
    }
    private Map<String, String> uriArray = new HashMap<>();

    @PostConstruct
    public void init() {
        uriArray.put("/website/public/about.xhtml", "About");
    }

    public Map<String, String> getUriArray() {
        return uriArray;
    }

    @Inject
    private ResourceCollection resourceCollection; // Ensure this is injected
    public void redirectWithLabel(String uri) {
        if (uri != null && !uri.trim().isEmpty()) {
            try {
                ejbFacade.clearCatch(); // Ensure this is injected
                String targetUri = uriArray.get(uri);
                String targetUriLable = String.valueOf(resourceCollection.getResourceLabel(uri));
                if (targetUriLable != null && !targetUriLable.trim().isEmpty()) {
                    String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath  + targetUriLable);
                } else {
                    System.out.println("URI label not found in map: " + uri);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Log it properly
            }
        }
    }

}
