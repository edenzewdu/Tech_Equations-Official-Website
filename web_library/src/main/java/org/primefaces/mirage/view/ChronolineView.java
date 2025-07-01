/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.mirage.view;

import jakarta.annotation.PostConstruct;
import jakarta.el.ELContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ChronolineView  implements Serializable {

//    private final URLBean uRLBean;
    private List<Event> events;
    private List<String> events2;

//    @jakarta.inject.Inject
//    public ChronolineView(@Named("uRLBean") URLBean uRLBean) {
//        this.uRLBean = uRLBean;
//    }

    @PostConstruct
    public void init() {
        events = new ArrayList<>();
        events.add(new Event("Sales Order", "", "pi pi-shopping-cart", "#9C27B0", "game-controller.jpg", "/salesorder/salesOrderDetailFile/List.xhtml"));
        events.add(new Event("Credit Order", "", "pi pi-credit-card", "#9C27B0", "game-controller.jpg", "/salesorder/creditOrdersFromHistory/List.xhtml"));
        events.add(new Event("Quote Order", "", "pi pi-file", "#9C27B0", "game-controller.jpg", "/salesorder/quote/List.xhtml"));
        events.add(new Event("Held Order", "", "pi pi-cog", "#673AB7", "/manageOrder/heldOrders/List.xhtml"));
        events.add(new Event("Sales Report", "", "pi pi-chart-bar", "#607D8B", "/salesorder/ReportChart.xhtml"));
        events2 = new ArrayList<>();
        events2.add("2021");
        events2.add("2021");
        events2.add("2022");
        events2.add("2023");
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<String> getEvents2() {
        return events2;
    }
    public void clickOnEvent(Event event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String eventStatus = context.getExternalContext().getRequestParameterMap().get("eventStatus");  // Retrieve the parameter

        // Now you can access the event status or event details here
        if (eventStatus != null) {
            FacesMessage message = new FacesMessage("Event clicked: " + eventStatus);
            context.addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Event is null");
            context.addMessage(null, message);
        }

        // You can also use the 'event' object as needed
        ELContext elContext = context.getELContext();
//        URLBean urlBean = (URLBean) elContext.getELResolver().getValue(elContext, null, "urlBean");
//        urlBean.encryptedLink(event.getLink());

    }
    public static class Event {
        String status;
        String date;
        String icon;
        String color;
        String image;
        String link;

        public Event() {
        }

        public Event(String status, String date, String icon, String color, String link) {
            this.status = status;
            this.date = date;
            this.icon = icon;
            this.color = color;
            this.link = link;
        }

        public Event(String status, String date, String icon, String color, String image, String link) {
            this.status = status;
            this.date = date;
            this.icon = icon;
            this.color = color;
            this.image = image;
            this.link = link;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

}
