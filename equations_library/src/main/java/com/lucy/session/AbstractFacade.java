///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.lucy.session;
//
//import com.lucy.jsf.util.JsfUtil;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.stream.Collectors;
//import jakarta.inject.Inject;
//import jakarta.persistence.*;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
///**
// * @param <T>
// * @author Alazar
// */
//public abstract class AbstractFacade<T> {
//
//    private final Class<T> entityClass;
//    //    private List<T> resultList = new ArrayList<>();
//    private final List<T> resultList1 = new ArrayList<>();
//    private int id;
//    private Integer fromRows;
//    private Integer fromId;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public AbstractFacade(Class<T> entityClass) {
//        this.entityClass = entityClass;
//    }
//
//    protected abstract EntityManager getEntityManager();
//
//    public void create(T entity) {
//        try {
//            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//            Validator validator = factory.getValidator();
//            Set<ConstraintViolation<T>> constraintViolation = validator.validate(entity);
//            if (constraintViolation.size() > 0) {
//                Iterator<ConstraintViolation<T>> iterator = constraintViolation.iterator();
//                while (iterator.hasNext()) {
//                    ConstraintViolation<T> constraintViolations = iterator.next();
//                    System.err.println(constraintViolations.getRootBeanClass().getName() + "." + constraintViolations.getPropertyPath() + " " + constraintViolations.getMessage());
//                    JsfUtil.addErrorMessage(constraintViolations.getRootBeanClass().getSimpleName() + "." + constraintViolations.getPropertyPath() + " " + constraintViolations.getMessage());
//                }
//            } else {
//                getEntityManager().persist(entity);
//                getEntityManager().flush();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void create1(T entity) {
//        try {
//            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//            Validator validator = factory.getValidator();
//            Set<ConstraintViolation<T>> constraintViolation = validator.validate(entity);
//            if (constraintViolation.size() > 0) {
//                Iterator<ConstraintViolation<T>> iterator = constraintViolation.iterator();
//                while (iterator.hasNext()) {
//                    ConstraintViolation<T> constraintViolations = iterator.next();
//                    System.err.println(constraintViolations.getRootBeanClass().getName() + "." + constraintViolations.getPropertyPath() + " " + constraintViolations.getMessage());
//                    JsfUtil.addErrorMessage(constraintViolations.getRootBeanClass().getSimpleName() + "." + constraintViolations.getPropertyPath() + " " + constraintViolations.getMessage());
//                }
//            } else {
//                getEntityManager().persist(entity);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void upload(T entity, FileInputStream file) {
//        try {
//            file = new FileInputStream(new File("howtodoinjava_demo.xlsx"));
//
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//            //Get first/desired sheet from the workbook
//            XSSFSheet sheet = workbook.getSheetAt(0);
//
//            //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                //For each row, iterate through all the columns
//                Iterator<Cell> cellIterator = row.cellIterator();
//
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    //Check the cell type and format accordingly
//
//                    if (cell.getCellType().name().equals("NUMERIC")) {
//
//                    } else if (cell.getCellType().name().equals("STRING")) {
//
//                    }
//
//                }
//                System.out.println("");
//            }
//            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            JsfUtil.addErrorMessage("Export Failed!");
//        }
//    }
//
//    public void flush() {
//        getEntityManager().flush();
//    }
//
//    public void edit(T entity) {
//        getEntityManager().merge(entity);
//        getEntityManager().flush();
//
//    }
//
//    public void removeCollection(Collection<T> c) {
//        for (T e : c) {
//            getEntityManager().remove(getEntityManager().merge(e));
//        }
//    }
//
//    private Object getIdValue(Object entity, String idAttributeName) throws IllegalAccessException {
//        Field field;
//        try {
//            field = entity.getClass().getDeclaredField(idAttributeName);
//            field.setAccessible(true);
//            return field.get(entity);
//        } catch (NoSuchFieldException e) {
//            // Handle exception or log it
//            return null;
//        }
//    }
//
//    public <E> void removeList(List<E> aList) {
//        if (aList != null) {
//            aList = aList.stream()
//                    .filter(it -> {
//                        try {
//                            String idAttributeName = getIdAttributeName((Class<T>) it.getClass());
//                            if (idAttributeName == null) {
//                                return false;
//                            }
//                            Object id = getIdValue(it, idAttributeName);
//                            return id != null;
//                        } catch (IllegalAccessException e) {
//                            // Handle exception or log it
//                            return false;
//                        }
//                    })
//                    .collect(Collectors.toList());
//            if (!aList.isEmpty()) {
//                removeCollection((Collection<T>) aList);
//            }
//        }
//    }
//
//    public void clearAllCatch() {
//        getEntityManager().getEntityManagerFactory().getCache().evictAll();
//    }
//
//    public void clearCach(Class<T> entityClass) {
//        getEntityManager().getEntityManagerFactory().getCache().evict(entityClass);
//    }
//
//    public void clearRowCach(Class<T> entityClass) {
//        getEntityManager().getEntityManagerFactory().getCache().evict(entityClass);
//    }
//
//    public void merge(T entity) {
//        getEntityManager().merge(entity);
//    }
//
//    public void remove(T entity) {
//        getEntityManager().remove(getEntityManager().merge(entity));
//    }
//
//    public T find(Object id) {
//        return getEntityManager().find(entityClass, id);
//    }
//
//    // General method to find by any field name and value
//    public List<T> findByField(String fieldName, Object value) {
//        TypedQuery<T> query = getEntityManager().createQuery(
//                "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :value",
//                entityClass
//        );
//        query.setParameter("value", value);
////        query.setFirstResult(firstResult); // For pagination - starting row
////        query.setMaxResults(maxResults);   // Number of results to fetch in a batch
//        return query.getResultList(); // Returns a list of results
//    }
//
//    // Optional method to return only one result or null if not found
//    public T findOneByField(String fieldName, Object value) {
//        List<T> results = findByField(fieldName, value);
//        return results.isEmpty() ? null : results.get(0); // Returns the first result or null if empty
//    }
//
//
//
//    public List<T> findAll1(Character backOrFront) {
//        // Process security override rows
//        // List<String> rows = securityWorkbenchTableController.processSecurityOverrideRows();
//
//        // Create CriteriaBuilder and CriteriaQuery
//        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
//        Root<T> root = cq.from(entityClass);
//        List<T> resultList = new ArrayList<>();
//
////        // Set rows per page based on the backOrFront parameter
////        Integer rowsPerPage = backOrFront == 'F' ? (resultList1.isEmpty() ? 10 : securityWorkbenchTableController.getRowsPerPage()) : 0;
////        if (backOrFront == 'F' && resultList1.isEmpty()) {
////            securityWorkbenchTableController.setRowsPerPage(rowsPerPage);
////        }
////
//        // Add ordering condition to the query
//        String idAttributeName = getIdAttributeName(entityClass);
//        if (idAttributeName != null) {
//            cq.orderBy(criteriaBuilder.desc(root.get(idAttributeName)));
//            resultList = getEntityManager().createQuery(cq).getResultList();
//        }
////
////        // Add filtering conditions based on backOrFront parameter
////        if (backOrFront == 'F' && fromRows != null && fromId != null && rowsPerPage != 10) {
////            cq.where(criteriaBuilder.lessThan(root.get(idAttributeName), fromId));
////        }
////
////        // Execute the query and set pagination
////        TypedQuery<T> query = getEntityManager().createQuery(cq);
////        if (backOrFront == 'F') {
////            if (fromRows != null && rowsPerPage.equals(fromRows)) {
////                return resultList1;
////            }
////            query.setMaxResults(rowsPerPage);
////            if (fromRows != null) {
////                query.setFirstResult(fromRows - 1);
////            }
////        }
////        resultList = query.getResultList();
////
////        // Update fromId and fromRows if resultList is not empty
////        if (backOrFront == 'F' && !resultList.isEmpty()) {
////            T lastEntity = resultList.get(resultList.size() - 1);
////            fromId = (Integer) getEntityManager().getEntityManagerFactory()
////                    .getPersistenceUnitUtil().getIdentifier(lastEntity);
////            fromRows = rowsPerPage;
////        }
//
//        // Filter resultList based on security override rows
//        // Filter resultList based on security override rows
//
//
////        // Add the filtered resultList to resultList1 only if they are different
////        if (!resultList.isEmpty()) {
////            Set<T> uniqueSet = new HashSet<>(resultList1); // Create a set with existing elements
////            for (T entity : resultList) {
////                if (uniqueSet.add(entity)) { // Only add if the element is not already present
////                    resultList1.add(entity);
////                }
////            }
////        }
//        return resultList;
//    }
//
//
//
//    public List<T> findAll1() {
//        // Process security override rows
//        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        return getEntityManager().createQuery(cq).getResultList();
//    }
//
//    private String getIdAttributeName(Class<T> entityClass) {
//        for (Field field : entityClass.getDeclaredFields()) {
//            if (field.isAnnotationPresent(Id.class)) {
//                return field.getName();
//            }
//        }
//        return null; // Return null if the ID attribute is not found
//    }
//
//    public List<T> findRange(int[] range) {
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        cq.select(cq.from(entityClass));
//        Query q = getEntityManager().createQuery(cq);
//        q.setMaxResults(range[1] - range[0] + 1);
//        q.setFirstResult(range[0]);
//        return q.getResultList();
//    }
//
//    public int count() {
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<T> rt = cq.from(entityClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
//        Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
//    }
//
//}
