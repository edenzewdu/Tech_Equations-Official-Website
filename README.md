# 💼 Tech Equations – Professional SaaS Website

> 📅 Version 1.0 • 📄 Specification Date: April 24, 2525 G.C  
> 🎯 Jakarta EE 10 • 🚀 Deployed on Payara Server

Tech Equations is a modern, scalable, and secure professional website built for a SaaS company. It combines public-facing marketing pages with a role-based admin backend, supporting content management, user authentication, and lead generation through contact forms.

---

## 🔗 Live Demo

🌐 [View the Live Website](https://techequations.com/web/)  
🔐 [Admin Panel]

---

## 🧩 Key Features

### 🌍 Public Website Pages
- Home page with hero section, pricing summary, product teasers
- Pricing page with comparative layout
- Product detail pages with visuals & CTAs
- Testimonials and Partners pages
- About Us, FAQs, and Contact (with CAPTCHA + Email)

### 🔐 User Authentication
- Secure login & logout
- Optional registration
- Password reset with email token
- Session timeout, bcrypt hashing, brute-force protection

### 🛠️ Admin Backend
- Role-restricted dashboard
- Manage Users, Pricing Plans, Testimonials, FAQs, Partners
- View contact submissions
- Dashboard statistics widgets

### ✍️ CMS Functionality
- Admin-editable homepage and about page content
- WYSIWYG or Markdown editor (TinyMCE / PrimeFaces Extensions)

### 📧 Email Integration
- Contact form notification
- Registration welcome
- Password reset instructions

---

## 🏗️ System Architecture

```
[ JSF + PrimeFaces UI ]
        ↓
[ CDI Controllers ]
        ↓
[ EJB/Service Layer ]
        ↓
[ JPA Repositories ]
        ↓
[ PostgreSQL/MySQL DB ]
```

**Stack Overview:**

| Layer       | Technology                                     |
|-------------|------------------------------------------------|
| UI          | JSF 4.0 + PrimeFaces                           |
| Backend     | Jakarta EE 10 (CDI, EJB, JPA, Bean Validation) |
| Security    | Jakarta Security 3.0                           |
| Mail        | Jakarta Mail 2.1                               |
| Deployment  | Payara Server 6 / 7                            |
| Build Tool  | Apache Maven 3.8+                              |
| Database    | PostgreSQL 14+ or MySQL 8+                     |
| Java        | JDK 21+                                        |

---

## 🧪 Screenshots

> *(Replace with real screenshots or mockups as needed)*

### 🏠 Home Page  
![Home Page](<Screenshot (3)(1)(1).png>)

### 🛠️ Admin Dashboard  
![Admin Panel]()


---

## ⚙️ How to Run Locally

### 1. Clone the Repo
```bash
git clone https://github.com/edenzewdu/Tech_Equations-Official-Website
cd Tech_Equations-Official-Website
```

### 2. Configure Database
- Create a PostgreSQL or MySQL database.
- Run the SQL initialization script:
  ```bash
  psql -U your_user -d your_db -f sql/init.sql
  ```
- Update DB credentials in `payara-resources.xml` or via Payara Admin Console.

### 3. Build the Project
```bash
mvn clean package
```

### 4. Deploy to Payara Server
- Deploy `target/web.war`:
  - via Payara Admin Console
  - or use:
    ```bash
    asadmin deploy target/web.war
    ```

### 5. Access Locally
```bash
http://localhost:8080/tech-equations/
```

---

## 📁 Project Structure

```
Tech_Equations-Official-Website/
|web/
├── ├── src/
│   |   ├── main/
│   |   │   ├── webapp/          
│   |   │   │   ├── website          
│   |   │   │   │   ├── admin / # Admin-facing pages    
│   |   │   │   │   ├── public / # Public-facing pages    
|web_library/
├── ├── src/
│   |   ├── main/
│   |   │   ├── java/          
│   |   │   │   ├── com/web/          
│   |   │   │   │   ├── entity     
│   |   │   │   │   ├── security       
│   |   │   │   │   ├── service        
│   |   │   │   │   ├── session        
├── sql/                  # DB init scripts
├── pom.xml               # Maven build file

```

---

## ✅ Acceptance Criteria (Examples)

- ✅ Responsive home page and contact form (AC-1, AC-2)
- ✅ Role-based admin access and dashboard (AC-3)
- ✅ CRUD for Testimonials (AC-4)
- ✅ CMS editing works and saves changes (AC-5)
- ✅ Password reset via email (AC-6)
- ✅ Deployed WAR runs on Payara without errors (AC-9)

---

## 🧩 Optional Features

- 🌍 Multilingual support via JSF resource bundles
- 📊 Basic analytics dashboard
- 🔐 Google reCAPTCHA integration
- 🧱 REST API (JAX-RS) for mobile clients

---

## 🙋 Contributing

1. Fork the repository
2. Create a feature branch  
   ```bash
   git checkout -b feature/my-feature
   ```
3. Commit your changes  
   ```bash
   git commit -m "Add my feature"
   ```
4. Push to GitHub and open a Pull Request

---

## 📜 License

GNU License © 2025 Tech Equations Team

---

## 📞 Contact

📧 [contact@techequations.com  ](https://techequations.com/web/Contact-Us)
🌐 [www.techequations.com](https://www.techequations.com)