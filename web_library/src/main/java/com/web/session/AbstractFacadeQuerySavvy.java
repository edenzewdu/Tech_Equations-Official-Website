/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.session;


import com.web.entity.MicroProducts;
import com.web.jsf.util.JsfUtil;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;

/**
 * @author Alish
 */
@Stateless
public class AbstractFacadeQuerySavvy extends AbstractFacadeSavvy<Object> {

    /**
     * Creates a new instance of AbstractFacadeQueryPOS
     */
    @PersistenceContext(unitName = "com.web_library_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    private String clause = null;
    private StringBuilder queryStringBuilder = new StringBuilder();
    private Map<String, Object> parameterMap = new HashMap<>();
    private List<String> integerParamQueries = new ArrayList<>();
    private List<String> stringParamQueries = new ArrayList<>();
    private List<String> dateParamQueries = new ArrayList<>();
    private List<String> doubleParamQueries = new ArrayList<>();
    private List<String> characterParamQueries = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private List<Integer> integerValues = new ArrayList<>();
    private List<String> stringValues = new ArrayList<>();
    private List<Date> dateValues = new ArrayList<>();
    private List<Double> doubleValues = new ArrayList<>();
    private List<Character> characterValues = new ArrayList<>();

    //    private StringBuilder queryStringGeneral = new StringBuilder();
    private Map<String, Object> parameterQueriesGeneral = new LinkedHashMap<>();
    private Map<String, String> queryStringGeneral = new LinkedHashMap<>();
    private Map<String, String> operatorGeneral = new HashMap<>();
    private String defaultOperator = "AND";
    private Map<String, Object> parameterValueFinal = new LinkedHashMap<>();

//    @Inject
//    private LoginView loginView;

    public StringBuilder getQueryStringBuilder() {
        return queryStringBuilder;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public void setQueryStringBuilder(StringBuilder queryStringBuilder) {
        this.queryStringBuilder = queryStringBuilder;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public List<String> getIntegerParamQueries() {
        return integerParamQueries;
    }

    public void setIntegerParamQueries(List<String> integerParamQueries) {
        this.integerParamQueries = integerParamQueries;
    }

    public List<String> getStringParamQueries() {
        return stringParamQueries;
    }

    public void setStringParamQueries(List<String> stringParamQueries) {
        this.stringParamQueries = stringParamQueries;
    }

    public List<String> getDateParamQueries() {
        return dateParamQueries;
    }

    public void setDateParamQueries(List<String> dateParamQueries) {
        this.dateParamQueries = dateParamQueries;
    }

    public List<String> getDoubleParamQueries() {
        return doubleParamQueries;
    }

    public void setDoubleParamQueries(List<String> doubleParamQueries) {
        this.doubleParamQueries = doubleParamQueries;
    }

    public List<String> getCharacterParamQueries() {
        return characterParamQueries;
    }

    public void setCharacterParamQueries(List<String> characterParamQueries) {
        this.characterParamQueries = characterParamQueries;
    }

    public List<String> getOperators() {
        return operators;
    }

    public void setOperators(List<String> operators) {
        this.operators = operators;
    }

    public List<Integer> getIntegerValues() {
        return integerValues;
    }

    public void setIntegerValues(List<Integer> integerValues) {
        this.integerValues = integerValues;
    }

    public List<String> getStringValues() {
        return stringValues;
    }

    public void setStringValues(List<String> stringValues) {
        this.stringValues = stringValues;
    }

    public List<Date> getDateValues() {
        return dateValues;
    }

    public void setDateValues(List<Date> dateValues) {
        this.dateValues = dateValues;
    }

    public List<Double> getDoubleValues() {
        return doubleValues;
    }

    public void setDoubleValues(List<Double> doubleValues) {
        this.doubleValues = doubleValues;
    }

    public List<Character> getCharacterValues() {
        return characterValues;
    }

    public void setCharacterValues(List<Character> characterValues) {
        this.characterValues = characterValues;
    }

    //    public StringBuilder getQueryStringGeneral() {
//        return queryStringGeneral;
//    }
//
//    public void setQueryStringGeneral(StringBuilder queryStringGeneral) {
//        this.queryStringGeneral = queryStringGeneral;
//    }
    public Map<String, String> getQueryStringGeneral() {
        return queryStringGeneral;
    }

    public void setQueryStringGeneral(Map<String, String> queryStringGeneral) {
        this.queryStringGeneral = queryStringGeneral;
    }

    public Map<String, String> getOperatorGeneral() {
        return operatorGeneral;
    }

    public void setOperatorGeneral(Map<String, String> operatorGeneral) {
        this.operatorGeneral = operatorGeneral;
    }

    public Map<String, Object> getParameterQueriesGeneral() {
        return parameterQueriesGeneral;
    }

    public void setParameterQueriesGeneral(Map<String, Object> parameterQueriesGeneral) {
        this.parameterQueriesGeneral = parameterQueriesGeneral;
    }

    public AbstractFacadeQuerySavvy() {
        super(Object.class);
    }

    public List<?> findById(String queryString, String parameterQuery, int parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return query.getResultList();
    }

    public List<?> findByCharacterAndtwoDates(String queryString, String parameterQuery1, String parameterQuery2, Character parameterValue1, Date parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndDouble(String queryString, String parameterQuery, String parameterQuery1, int parameterValue, double parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return query.getResultList();
    }

    public List<?> findByCharacter(String queryString, String parameterQuery, Character parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);

        return query.getResultList();
    }

    public List<?> findByTwoCharacter(String queryString, String parameterQuery, String parameterQuery1, Character parameterValue, Character parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);

        return query.getResultList();
    }

    public List<?> findByIntTwoCharacter(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, Integer parameterValue, Character parameterValue1, Character parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);

        return query.getResultList();
    }

    public final List<?> findDistinctList(String queryString) {
        Query query = em.createNamedQuery(queryString);
        return query.getResultList();
    }

    public List<?> findMaximum(String queryString) {
        Query query = em.createNamedQuery(queryString);
        return query.getResultList();
    }

    public List<?> findByThreeIntStringAndTwoDate(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, int parameterValue1, int parameterValue2, int parameterValue3, String parameterValue4, Date parameterValue5, Date parameterValue6) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        query.setParameter(parameterQuery6, parameterValue6);
        return query.getResultList();
    }

    public List<?> findByIdAndString(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndCharacter(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, char parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndId(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, int parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIntAndTwoString(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, int parameterValue, String parameterValue2, String parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByThreeIdAndString(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue1, int parameterValue2, String parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByThreeIdAndTwoString(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, int parameterValue, int parameterValue1, int parameterValue2, String parameterValue3, String parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByIdAndStringAndMaxResult(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, String parameterValue2, int maxResult) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    public Integer findByIdAndStringReturnInteger(String queryString, String parameterQuery, String parameterQuery1, Integer parameterValue, String parameterValue1) {
        try {
            Query query = em.createNamedQuery(queryString);
            query.setParameter(parameterQuery, parameterValue);
            query.setParameter(parameterQuery1, parameterValue1);
            System.out.println("Executing named query: " + queryString);
            System.out.println("Executing named query: " + query);

            System.out.println("Executing named query: " + query.getResultList());

            return ((Number) query.getSingleResult()).intValue();
        } catch (NoResultException e) {
            return null; // Or return a default value
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("Expected single result but found multiple", e);
        } catch (Exception e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

    public List<?> findNewNotificationsPaginated(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, String parameterValue2, int offset, int limit) {
        // Ensure your Named Query "NotificationTable.findByToAddressNumberAndStatusOrdered" exists
        // (as defined in the previous answer, including ORDER BY n.dateSent DESC)
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setFirstResult(offset); // Set the starting point (offset)
        query.setMaxResults(limit);  // Set the maximum number of rows (page size)
        return query.getResultList();
    }

    public List<?> findByFourIdAndString(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, int parameterValue, int parameterValue1, int parameterValue2, int parameterValue3, String parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByIntAndString(String queryString, String parameterQuery, String parameterQuery1, int parameterValue, String parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndString(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, int parameterValue, int parameterValue1, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndCharacter(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, int parameterValue, int parameterValue1, Character parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndFourString(String queryString, String parameterId, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, int parameterValue, String parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterQuery, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        query.setParameter(parameterQuery3, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByIdAndFiveString(String queryString, String parameterId, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, int parameterValue, String parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterQuery, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        query.setParameter(parameterQuery3, parameterValue4);
        query.setParameter(parameterQuery4, parameterValue5);
        return query.getResultList();
    }

    public List<?> findByIdAndDAte(String queryString, String parameterQuery, String parameterQuery1, int parameterValue, Date parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return query.getResultList();
    }

    public List<?> findByIdAndInt(String queryString, String parameterQuery, String parameterQuery1, int parameterValue, int parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return query.getResultList();
    }

    public Class<?> findByIdSingleData(String queryString, String parameterQuery, int parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return (Class<?>) query.getSingleResult();
    }

    public List<?> findByTwoIdSingleData(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, int parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByThreeInt(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue2, int parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByStringId(String queryString, String parameterQuery, String parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return query.getResultList();
    }

    public List<?> findBetweenDoubles(String queryString, String parameterQuery, Double parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return query.getResultList();
    }

    public String findByIdReturnString(String queryString, String parameterQuery, int parameterValue) {
        try {
            Query query = em.createNamedQuery(queryString);
            query.setParameter(parameterQuery, parameterValue);

            Object result = query.getSingleResult();
            return result != null ? result.toString() : null;
        } catch (NoResultException e) {
            return null; // Or return a default value
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("Expected single result but found multiple", e);
        } catch (Exception e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

    public List<String> findByIdReturnStringMaximumLimited(String queryString, String parameterQuery, int parameterValue, int max) {
        try {
            Query query = em.createNamedQuery(queryString);
            query.setParameter(parameterQuery, parameterValue);

            return query.setMaxResults(max).getResultList();
        } catch (NoResultException e) {
            return null; // Or return a default value
        }
    }

    public String findByStringReturnString(String queryString, String parameterQuery, String parameterValue) {
        try {
            Query query = em.createNamedQuery(queryString);
            query.setParameter(parameterQuery, parameterValue);

            Object result = query.getSingleResult();
            return result != null ? result.toString() : null;
        } catch (NoResultException e) {
            return null; // Or return a default value
        } catch (NonUniqueResultException e) {
            throw new RuntimeException("Expected single result but found multiple", e);
        } catch (Exception e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

    public List<?> findBetweenDoublesAndId(String queryString, String parameterId, String parameterQuery, int parameterIdValue, Double parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterIdValue);
        query.setParameter(parameterQuery, parameterValue);
        return query.getResultList();
    }

    public List<?> findBetweenTwoDoubles(String queryString, String parameterQuery1, String parameterQuery2, Double parameterValue1, Double parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findBetweenTwoDoublesExcludeId(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer id, Double parameterValue1, Double parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, id);
        return query.getResultList();
    }

    public List<?> findByTwoStringAndCharacter(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterValue1, String parameterValue2, Character parameterValue3) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntOneStringDateAndTwoDouble(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, String parameter7, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4, Date parameterValue5, Double parameterValue6, Double parameterValue7) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameter7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntOneStringDateAndTwoDouble(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, String parameter7, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, Date parameterValue6, Double parameterValue7) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameter7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQuerySixiNTS(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoStringTwoIntTwoDate(String queryString, String parameter1,
                                                      String parameter2, String parameter3, String parameter4, String parameter5, String parameter6,
                                                      String parameterValue1, String parameterValue2, Integer parameterValue3, Integer parameterValue4,
                                                      Date parameterValue5, Date parameterValue6) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTreeIntAndDate(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntAndTwoDate(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4, Date parameterValue5) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourInts(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                        Integer parameterValue1, Integer parameterValue2,
                                        Integer parameterValue3, Integer parameterValue4) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != null) {
            queryString += "p.purchaseOrderHeader.id = :" + parameterValue1;
        }
        if (parameterValue2 != null) {
            queryString += "p.businessUnit.bUMNumber = :" + parameterValue2;
        }
        if (parameterValue3 != null) {
            queryString += "p.statusCodeNext.id = :" + parameterValue3;
        }
        if (parameterValue4 != null) {
            queryString += "p.statusCodeLast.id = :" + parameterValue4;
        }

        Query query = em.createQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> findBetweenTwoDoubleAndId(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer id, Double parameterValue1, Double parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, id);
        query.setParameter(parameterQuery2, parameterValue1);
        query.setParameter(parameterQuery3, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByBetweenIntsAndBetweenDates(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                    Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByStringTwoParameter(String queryString, String parameterQuery1, String parameterQuery2, String parameterValue1, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);

        return query.getResultList();
    }

    public List<?> findByStringTwoParameterIdAndString(String queryString, String parameterQuery1, String parameterQuery2, int parameterValue1, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);

        return query.getResultList();
    }

    public List<?> findBetweenDates(String queryString, String parameterId, String parameterDate1, String parameterDate2, int employeeId, Date startDate, Date endDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterDate2, endDate);
        query.setParameter(parameterId, employeeId);
        return query.getResultList();
    }

    public List<?> findBetweenFourDates(String queryString, String parameterId, String parameterDate1, String parameterDate2, String parameterDate3, String parameterDate4, int companyId, Date dateEndofPeriod01, Date dateEndofPeriod02, Date dateEndofPeriod03, Date dateEndofPeriod04) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, dateEndofPeriod01);
        query.setParameter(parameterDate2, dateEndofPeriod02);
        query.setParameter(parameterDate3, dateEndofPeriod03);
        query.setParameter(parameterDate4, dateEndofPeriod04);
        query.setParameter(parameterId, companyId);
        return query.getResultList();
    }

    public List<?> findBetweenDatesAndTwoId(String queryString, String parameterId, String parameterId2, String parameterDate1, String parameterDate2, int employeeId, int id2, Date startDate, Date endDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterDate2, endDate);
        query.setParameter(parameterId, employeeId);
        query.setParameter(parameterId2, id2);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndBetweenDates(String queryString, String parameterId, String parameterId2, String parameterDate1, String parameterDate2, int employeeId, int id2, Date startDate, Date endDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterDate2, endDate);
        query.setParameter(parameterId, employeeId);
        query.setParameter(parameterId2, id2);
        return query.getResultList();
    }

    public List<?> findByThreeIdAndBetweenDates(String queryString, String parameterId, String parameterId2, String parameterId3, String parameterDate1, String parameterDate2, int employeeId, int id2, int id3, Date startDate, Date endDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterDate2, endDate);
        query.setParameter(parameterId, employeeId);
        query.setParameter(parameterId2, id2);
        query.setParameter(parameterId2, id3);
        return query.getResultList();
    }

    public List<?> findByThreeIdAndDates(String queryString, String parameterId, String parameterId2, String parameterId3, String parameterDate1, int id1, int id2, int id3, Date startDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterId, id1);
        query.setParameter(parameterId2, id2);
        query.setParameter(parameterId2, id3);
        return query.getResultList();
    }

    public List<?> findByTwoInt(String queryString, String parameterId, String parameterId2, int id1, int id2) {

        Query query = em.createNamedQuery(queryString);

        query.setParameter(parameterId, id1);
        query.setParameter(parameterId2, id2);
        return query.getResultList();
    }

    public List<?> findByIdAndBetweenDates(String queryString, String parameterId, String parameterDate1, int id, Date myDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, id);
        query.setParameter(parameterDate1, myDate);
        return query.getResultList();
    }

    public List<?> findByIdAndBetweenTwoDates(String queryString, String parameterId, String parameterId2, String parameterDate1, String parameterDate2, int id, int id2, Date myDate1, Date myDate2) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, id);
        query.setParameter(parameterId2, id2);
        query.setParameter(parameterDate1, myDate1);
        query.setParameter(parameterDate2, myDate2);
        return query.getResultList();
    }

    public List<?> findByIntAndTwoDates(String queryString, String parameterId, String parameterDate1, String parameterDate2, int id, Date myDate1, Date myDate2) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, id);
        query.setParameter(parameterDate1, myDate1);
        query.setParameter(parameterDate2, myDate2);
        return query.getResultList();
    }

    public long recordCounter(String theClass, String theField, Object theValue) {
        String queryString = "SELECT COUNT(d) FROM " + theClass + " d WHERE d." + theField + " = :" + theField;
        return em.createNamedQuery(queryString, Long.class).setParameter(theField, theValue).getSingleResult();
    }

    public List<?> dynamicQueryTwoInt(String theClass, String parameterQuery1, String parameterQuery2, Integer parameterValue1, Integer parameterValue2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            queryString += " p." + parameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            queryString += " AND p.rebateAgreement.rebateStatus = :" + parameterQuery1;
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> findBetweenDates2(String queryString, String parameterDate1, Date myDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, myDate);
        return query.getResultList();
    }

    public List<?> findBetweenDates3(String queryString, String parameterDate1, String parameterDate2, Date startDate, Date endDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterDate2, endDate);
        return query.getResultList();
    }

    public List<?> findByTwoIntAndAfterDate(String queryString, String parameterId1, String parameterId2, String parameterDate, int parametervalue1, int parameterValue2, Date parameterValue3) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parametervalue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterDate, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByTwoIntAndString(String queryString, String parameterId1, String parameterId2, String parameterId3, int parametervalue1, int parameterValue2, String parameterValue3) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parametervalue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByIdAndDateAndString(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, int parameterValue, Date parameterValue1, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByTwoId(String queryString, String parameterId, String parameterId2, int parameterValue, int parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId2, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndTwoString(String queryString, String parameterId, String parameterQuery, String parameterQuery1, int parameterValue, String parameterValue1, String parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterQuery, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        return query.getResultList();
    }

    public List<?> findByIdAndThreeString(String queryString, String parameterId, String parameterQuery, String parameterQuery1, String parameterQuery2, int parameterValue, String parameterValue1, String parameterValue2, String parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterQuery, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndTwoString(String queryString, String parameterId, String parameterId1, String parameterQuery1, String parameterQuery2, int parameterValue, int parameterValue1, String parameterValue2, String parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndThreeString(String queryString, String parameterId, String parameterId1, String parameterQuery1, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        query.setParameter(parameterQuery3, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByTwoIdAndThreeInt(String queryString, String parameterId, String parameterId1, String parameterQuery1, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue1, int parameterValue2, int parameterValue3, int parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterQuery1, parameterValue2);
        query.setParameter(parameterQuery2, parameterValue3);
        query.setParameter(parameterQuery3, parameterValue4);
        return query.getResultList();
    }

    public List<?> findByThreeId(String queryString, String parameterId, String parameterId2, String parameterId3, int parameterValue, int parameterValue2, int parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByFourId(String queryString, String parameterId, String parameterId2, String parameterId3, String parameterId4, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId, parameterValue);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByTwoString(String queryString, String parameterId1, String parameterId2, String parameterValue1, String parameterValue2) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);

        return query.getResultList();
    }

    public List<?> findByThreeString(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterValue1, String parameterValue2, String parameterValue3) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);

        return query.getResultList();
    }

    public List<?> findByFourString(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterId4, String parameterValue1, String parameterValue2, String parameterValue3, Character parameterValue4) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        query.setParameter(parameterId4, parameterValue4);

        return query.getResultList();
    }

    public List<?> findByStringtwoDouble(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterValue1, double parameterValue2, double parameterValue3) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);

        return query.getResultList();
    }

    public List<?> findBySevenInt(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterId4, String parameterId5, String parameterId6, String parameterId7, int parameterValue1, int parameterValue2, int parameterValue3, int parameterValue4, int parameterValue5, int parameterValue6, int parameterValue7) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        query.setParameter(parameterId4, parameterValue4);
        query.setParameter(parameterId5, parameterValue5);
        query.setParameter(parameterId6, parameterValue6);
        query.setParameter(parameterId7, parameterValue7);

        return query.getResultList();
    }

    public List<?> findByEightInt(String queryString, String parameterId1, String parameterId2, String parameterId3, String parameterId4, String parameterId5, String parameterId6, String parameterId7, String parameterId8, int parameterValue1, int parameterValue2, int parameterValue3, int parameterValue4, int parameterValue5, int parameterValue6, int parameterValue7, int parameterValue8) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterId1, parameterValue1);
        query.setParameter(parameterId2, parameterValue2);
        query.setParameter(parameterId3, parameterValue3);
        query.setParameter(parameterId4, parameterValue4);
        query.setParameter(parameterId5, parameterValue5);
        query.setParameter(parameterId6, parameterValue6);
        query.setParameter(parameterId7, parameterValue7);
        query.setParameter(parameterId8, parameterValue8);

        return query.getResultList();
    }

    public List<?> findByIntAndBigDecimal(String queryString, String parameterQuery, String parameterQuery2, int parameterValue, BigDecimal parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);

        return query.getResultList();
    }

    public List<?> findByTwoIntAndBigDecimal(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue2, BigDecimal parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeInts(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                         Integer parameterValue1, Integer parameterValue2,
                                         Integer parameterValue3) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != null) {
            queryString += "p.purchaseOrderHeader.id = :" + parameterQuery1;
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "p.businessUnit.bUMNumber = :" + parameterQuery2;
            } else {
                queryString += " AND p.businessUnit.bUMNumber = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "p.itemNumber.TMNumber = :" + parameterQuery3;
            } else {
                queryString += " AND p.itemNumber.TMNumber = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsSupplier(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3
    ) {

        //The general query
        String queryString = "SELECT p FROM " + theClass + " p WHERE p.addressNumber.addressNumber = :" + parameterQuery3 + ") AND ";

        if (parameterValue1 != null) {
            queryString += "p.purchaseOrderHeader.id = :" + parameterQuery1;
        }

        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "p.itemNumber.TMNumber = :" + parameterQuery2;
            } else {
                queryString += " AND p.itemNumber.TMNumber = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> findByDate(String queryString, String parameterQuery, Date parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return (List<?>) query.getResultList();
    }

    public List<?> findByIntAndDate(String queryString, String parameterQuery, String parameterQuery1, int parameterValue, Date parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndIntAndDate(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterValue, int parameterValue1, Date parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndInt(String queryString, String parameterQuery, String parameterQuery1, String parameterValue, int parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> findByTwoStringAndTwoInt(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterValue, String parameterValue1, int parameterValue2, int parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return (List<?>) query.getResultList();
    }

    public List<?> findByTwoStringInt(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterValue, String parameterValue1, int parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);

        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndDate(String queryString, String parameterQuery, String parameterQuery1, String parameterValue, Date parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndTwoInt(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterValue, int parameterValue1, int parameterValue2) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndThreeInt(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterValue, int parameterValue1, int parameterValue2, int parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndFiveInt(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterValue, int parameterValue1, int parameterValue2, int parameterValue3, int parameterValue4, int parameterValue5) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndThreeIntAndDate(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterValue, int parameterValue1, int parameterValue2, int parameterValue3, Date parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringAndTwoIntAndDate(String queryString, String parameterQuery, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterValue, int parameterValue1, int parameterValue2, Date parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return (List<?>) query.getResultList();
    }

    public List<?> findByStringDate(String queryString, String parameterQuery, String parameterQuery1, String parameterValue, Date parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> findByTwoIntAndLong(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, int parameterValue, int parameterValue2, long parameterValue3) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        return query.getResultList();
    }

    public List<?> findByThreeIntAndLong(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, int parameterValue, int parameterValue2, int parameterValue3, long parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);

        return query.getResultList();
    }

    public List<?> findByFourIntAndLong(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, int parameterValue, int parameterValue2, int parameterValue3, long parameterValue4, int parameterValue5) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);

        return query.getResultList();
    }

    public List<?> findByThreeIdAndDates(String queryString, String parameterId, String parameterId2, String parameterId3, String parameterDate1, String parameterDate2, int id1, int id2, int id3, Date startDate) {

        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterDate1, startDate);
        query.setParameter(parameterId, id1);
        query.setParameter(parameterId2, id2);
        query.setParameter(parameterId2, id3);
        return query.getResultList();
    }

    public List<?> findByFourIntAndDate(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4, Date parameterValue5) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);

        return query.getResultList();
    }

    public List<?> findByFourIntAndTwoString(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4, String parameterValue5, String parameterValue6) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        query.setParameter(parameterQuery6, parameterValue6);

        return query.getResultList();
    }

    public List<?> findByFourInt(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);

        return query.getResultList();
    }

    public List<?> findByFiveInt(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4, int parameterValue5) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        return query.getResultList();
    }

    public List<?> findByFiveIntAndStringWithDate(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4, String parameterValue5, Date parameterValue6) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        query.setParameter(parameterQuery6, parameterValue6);
        return query.getResultList();
    }

    public List<?> findBySevenIntAndDoubleWithDate(String queryString, String parameterQuery, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, int parameterValue, int parameterValue2, int parameterValue3, int parameterValue4, int parameterValue5, int parameterValue6, int parameterValue7, Double parameterValue8, Date parameterValue9) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery2, parameterValue2);
        query.setParameter(parameterQuery3, parameterValue3);
        query.setParameter(parameterQuery4, parameterValue4);
        query.setParameter(parameterQuery5, parameterValue5);
        query.setParameter(parameterQuery6, parameterValue6);
        query.setParameter(parameterQuery7, parameterValue7);
        query.setParameter(parameterQuery8, parameterValue8);
        query.setParameter(parameterQuery9, parameterValue9);
        return query.getResultList();
    }

    public List<?> findByDouble(String queryString, String parameterQuery, double parameterValue) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        return query.getResultList();
    }

    public List<?> findByStringAndDouble(String queryString, String parameterQuery, String parameterQuery1, String parameterValue, double parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> findByTwoDouble(String queryString, String parameterQuery, String parameterQuery1, double parameterValue, double parameterValue1) {
        Query query = em.createNamedQuery(queryString);
        query.setParameter(parameterQuery, parameterValue);
        query.setParameter(parameterQuery1, parameterValue1);
        return (List<?>) query.getResultList();
    }

    public List<?> dynamicQueryStringAndFiveInt(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterValue1, int parameterValue2,
                                                int parameterValue3, int parameterValue4, int parameterValue5, int parameterValue6) {

        //The general query
        String queryString = "SELECT e FROM " + theClass + " e WHERE e.status = :" + parameterQuery1;

        if (parameterValue2 != 0) {
            queryString += " AND e.businessUnit.bUMNumber = :" + parameterQuery2;
        }
        if (parameterValue3 != 0) {
            queryString += " AND e.purchaseOrderHeader.holdOrdersCode.id = :" + parameterQuery3;
        }
        if (parameterValue4 != 0) {
            queryString += " AND e.purchaseOrderHeader.holdOrdersCode.id IN (SELECT f.holdCode.id FROM OrderHoldInformation f WHERE f.personResponsible.addressNumber = :" + parameterQuery4 + ")";
        }

        if (parameterValue5 != 0) {
            queryString += " AND e.addressNumber.addressNumber = :" + parameterQuery5;
        }
        if (parameterValue6 != 0) {
            queryString += " AND e.purchaseOrderHeader.id = :" + parameterQuery6;
        }
        Query query = em.createQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndThreeIntWithDate(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                         String parameterQuery4, String parameterQuery5, String parameterValue1, int parameterValue2,
                                                         int parameterValue3, int parameterValue4, Date parameterValue5) {

        //The general query
        String queryString = "SELECT w FROM " + theClass + " w WHERE w.calendarName = :" + parameterQuery1;

        if (parameterValue2 != 0) {
            queryString += " AND w.branch.bUMNumber = :" + parameterQuery2;
        }
        if (parameterValue3 != 0) {
            queryString += " AND w.UdcRecord.calendarType.id = :" + parameterQuery3;
        }
        if (parameterValue4 != 0) {
            queryString += " AND w.UdcRecord.shiftCode.id = :" + parameterQuery4;
        }
        if (parameterValue5 != null) {
            queryString += null;
        }

        Query query = em.createQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndFiveInt1(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                 String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterValue1, Integer parameterValue2,
                                                 Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6) {

        //The general query
        Character queryExistsFor5 = 'N';
        Character queryExistsFor6 = 'N';
        String queryString = "SELECT p FROM " + theClass + " p WHERE p.status = :" + parameterQuery1;

        if (parameterValue1.equals("QueuedForApproval")) {
            parameterValue1 = "Submitted";
            if (parameterValue2 != 0) {

                queryString += " AND p.purchaseOrderHeader.approvalRouting.id IN (SELECT al.codeApprovalRouting.id FROM ApprovalLevels al WHERE al.personResponsible.addressNumber = :" + parameterQuery2 + ")";

            }
            if (parameterValue3 != 0) {
                queryString += " AND p.businessUnit.bUMNumber = :" + parameterQuery3;
            }
            if (parameterValue4 != 0) {
                queryString += " AND p.orderType.id = :" + parameterQuery4;
            }
            if (parameterValue6 != 0) {
                queryString += " AND p.purchaseOrderHeader.dateOrderTransaction <= :" + parameterQuery6;
                queryExistsFor6 = 'Y';
            }
            if (parameterValue5 != 0) {
                queryString += "";

            }

        } else if (parameterValue1.equals("WaitingApproval")) {
            parameterValue1 = "Submitted";
            if (parameterValue2 != 0) {
                queryString += " AND p.purchaseOrderHeader.orderedBy.addressNumber = :" + parameterQuery2;
            }
            if (parameterValue3 != 0) {
                queryString += " AND p.businessUnit.bUMNumber = :" + parameterQuery3;
            }
            if (parameterValue4 != 0) {
                queryString += " AND p.orderType.id = :" + parameterQuery4;
            }
            if (parameterValue5 != 0 && parameterValue6 == 0) {

                queryString += " AND p.purchaseOrderHeader.dateOrderTransaction <= :" + parameterQuery5;
                queryExistsFor5 = 'Y';
            }
            if (parameterValue6 != 0 && parameterValue5 == 0) {
                queryString += " AND p.purchaseOrderHeader.dateOrderTransaction <= :" + parameterQuery6;
                queryExistsFor6 = 'Y';
            }
            if (parameterValue6 != 0 && parameterValue5 != 0) {
                queryString += "";
            }
        } else if (parameterValue1.equals("Approved")) {

            if (parameterValue2 != 0) {
                queryString += " AND p.id IN(SELECT aa.lineNumber.id FROM ApprovalActivity aa WHERE aa.approveReject = :" + parameterQuery1 + " AND p.purchaseOrderHeader.orderedBy.addressNumber = :" + parameterQuery2 + ")";
            }
            if (parameterValue3 != 0) {
                queryString += " AND p.businessUnit.bUMNumber = :" + parameterQuery3;
            }
            if (parameterValue4 != 0) {
                queryString += " AND p.orderType.id = :" + parameterQuery4;
            }
            if (parameterValue5 != 0) {
                queryString += " AND p.purchaseOrderHeader.dateOrderTransaction <= :" + parameterQuery5;
                queryExistsFor5 = 'Y';
            }
            if (parameterValue6 != 0) {
                queryString += "";
            }
        } else if (parameterValue1.equals("Rejected")) {
            parameterValue1 = "Rejected";
            if (parameterValue2 != 0) {
                queryString += " AND p.id IN(SELECT aa.lineNumber.id FROM ApprovalActivity aa WHERE aa.approveReject = :" + parameterQuery1 + " AND p.purchaseOrderHeader.orderedBy.addressNumber = :" + parameterQuery2 + ")";
            }
            if (parameterValue3 != 0) {
                queryString += " AND p.businessUnit.bUMNumber = :" + parameterQuery3;
            }
            if (parameterValue4 != 0) {
                queryString += " AND p.orderType.id = :" + parameterQuery4;
            }
            if (parameterValue5 != 0) {
                queryString += " AND p.purchaseOrderHeader.dateOrderTransaction <= :" + parameterQuery5;
                queryExistsFor5 = 'Y';
            }
            if (parameterValue6 != 0) {
                queryString += "";
            }
        }

        Query query = em.createQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0 && queryExistsFor5 == 'Y') {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0 && queryExistsFor6 == 'Y') {
            query.setParameter(parameterQuery6, parameterValue6);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndDate(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                              Integer parameterValue1, Integer parameterValue2, Date parameterValue3
    ) {

        //The general query
        String queryString = "SELECT b FROM " + theClass + " b WHERE ";

        if (parameterValue1 != null) {
            queryString += "b.segmentID.id = :" + parameterQuery1;
        }

        if (parameterValue2 != null) {
            queryString += "b.burdenCategory.id = :" + parameterQuery2;
        }
        if (parameterValue3 != null) {
            queryString += null;
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndTwoDatePoolDefinition(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                               String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4
    ) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "=p.segmentID.id = :" + parameterQuery1;
            } else {
                queryString += " AND p.segmentID.id = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "p.burdenCategory.id = :" + parameterQuery2;
            } else {
                queryString += " AND p.burdenCategory.id = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            queryString += null;
        }
        if (parameterValue4 != null) {
            queryString += null;
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndTwoDatePoolGroupingCode(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                                 String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4
    ) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "=p.burdenPoolGroupingCode.id = :" + parameterQuery1;
            } else {
                queryString += " AND p.burdenPoolGroupingCode.id = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "p.segmentID.id = :" + parameterQuery2;
            } else {
                queryString += " AND p.segmentID.id = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            queryString += null;
        }
        if (parameterValue4 != null) {
            queryString += null;
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndDate(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4
    ) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != null) {
            queryString += "=p.segmentID.id = :" + parameterQuery1;
        }

        if (parameterValue2 != null) {
            queryString += "p.burdenCategory.id = :" + parameterQuery2;
        }
        if (parameterValue3 != null) {
            queryString += "p.burdenRateType.id = :" + parameterQuery3;
        }
        if (parameterValue4 != null) {
            queryString += null;
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFoureInt(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                        String parameterQuery4, int parameterValue1, int parameterValue2,
                                        int parameterValue3, int parameterValue4) {

        //The general query
        String queryString = "SELECT p FROM " + theClass + " p WHERE p.businessUnit.company.id = :" + parameterQuery1;

        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.electronicFormatIndicator.id = :" + parameterQuery2;
            } else {
                queryString += " AND b.electronicFormatIndicator.id = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.externalTransactionType.id = :" + parameterQuery3;
            } else {
                queryString += " AND b.externalTransactionType.id = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.bankStatementTransactionCode.id = :" + parameterQuery4;
            } else {
                queryString += " AND b.bankStatementTransactionCode.id = :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);
        query.setParameter(parameterQuery1, parameterValue1);

        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterQuery2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeInt(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                        int parameterValue1, int parameterValue2, int parameterValue3) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.electronicFormatIndicator.id = :" + parameterQuery1;
            } else {
                queryString += " AND b.electronicFormatIndicator.id = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.externalTransactionType.id = :" + parameterQuery2;
            } else {
                queryString += " AND b.externalTransactionType.id = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " b.bankStatementTransactionCode.id = :" + parameterQuery3;
            } else {
                queryString += " AND b.bankStatementTransactionCode.id = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterQuery2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveInts(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                        Integer parameterValue1, Integer parameterValue2,
                                        Integer parameterValue3, Integer parameterValue4, Integer parameterValue5) {

        String queryString = "SELECT i FROM " + theClass + " i WHERE ";

        if (parameterValue1 != null) {
            queryString += "i.businessUnit.bUMNumber = :" + parameterValue1;
        }
        if (parameterValue2 != null) {
            queryString += "i.itemNumberShort.tMNumber = :" + parameterValue2;
        }
        if (parameterValue3 != null) {
            queryString += "i.itemPriceGroup.id = :" + parameterValue3;
        }
        if (parameterValue4 != null) {
            queryString += "i.customerNumber.addressNumber = :" + parameterValue4;
        }
        if (parameterValue5 != null) {
            queryString += "i.customerPriceGroups.id = :" + parameterValue5;
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();

    }

//    public List<?> dynamicQueryForProcessingOption(String theClass) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        ELContext elContext = context.getELContext();
//        InteractiveVersionsController interactiveVersionsController = (InteractiveVersionsController) elContext.getELResolver().getValue(elContext, null, "interactiveVersionsController");
//        LoginView loginView = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
//
//        String queryString = "SELECT p FROM " + theClass + " p WHERE p.businessUnit.company.id = :company AND p.version.id = :version";
//        Query query = em.createQuery(queryString);
//        query.setParameter("company", loginView.getAuthenticatedUser().getCompany().getId());
//        query.setParameter("version", interactiveVersionsController.getSelected().getId());
//
//        return query.getResultList();
//    }

//    public List<?> dynamicQueryForBatchOption(String theClass) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        ELContext elContext = context.getELContext();
//        BatchVersionsController batchVersionsController = (BatchVersionsController) elContext.getELResolver().getValue(elContext, null, "batchVersionsController");
//        LoginView loginView = (LoginView) elContext.getELResolver().getValue(elContext, null, "loginView");
//
//        String queryString = "SELECT p FROM " + theClass + " p WHERE p.businessUnit.company.id = :company AND p.version.id = :version";
//        Query query = em.createQuery(queryString);
//        query.setParameter("company", loginView.getAuthenticatedUser().getCompany().getId());
//        query.setParameter("version", batchVersionsController.getSelected().getId());
//
//        return query.getResultList();
//    }

    public List<?> dynamicQueryForThreeIntAndCharacter(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Character parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoInt(String theClass, String entityParameterQuery1, String entityParameterQuery2,
                                         String parameterQuery1, String parameterQuery2, Integer parameterValue1, Integer parameterValue2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndString(String theClass, String entityParameterQuery1, String entityParameterQuery2,
                                                  String parameterQuery1, String parameterQuery2, String parameterQuery2Of1,
                                                  Integer parameterValue1, String oddGLRCItemNumber, String evenGLRCItemNumber) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }

        if (oddGLRCItemNumber.substring(0, 5).equalsIgnoreCase("GLRC")) {
            if (evenGLRCItemNumber.substring(0, 5).equalsIgnoreCase("GLRC")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery2 + " BETWEEN :" + parameterQuery2 + " AND :" + parameterQuery2Of1;
                } else {
                    queryString += " AND d." + entityParameterQuery2 + " BETWEEN :" + parameterQuery2 + " AND :" + parameterQuery2Of1;
                }
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!oddGLRCItemNumber.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, oddGLRCItemNumber);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForIntAndString(String theClass, String entityParameterQuery1, String entityParameterQuery2,
                                               String parameterQuery1, String parameterQuery2, Integer parameterValue1, String parameterValue2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndCharacter(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, Character parameterValue3) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndTwoDateWithCharacter(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4, Character parameterValue5) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForIntAndTwoDate(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, Character parameterValue3) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForThreeInt(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                           String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                           Integer parameterValue1, Integer parameterValue2, Integer parameterValue3) {

        String queryString = "SELECT dFROM " + theClass + " d ";
        if (parameterValue1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndStringWithComparison(String theClass, String entityParameterQuery1, String entityParameterQuery2,
                                                                String parameterQuery1, String parameterQuery2, Integer parameterValue1, Integer parameterValue2, String parameterValue3) {

        String queryString = "SELECT dFROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }

        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " " + parameterValue3 + " :" + parameterQuery2;

            } else {
                queryString += " AND d." + entityParameterQuery2 + " " + parameterValue3 + " :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForSixIntAndDateWithString(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6, String entityParameterQuery7,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7Of1, String parameterQuery8,
                                                          Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Date beginningDate, Date endingDate, String parameterValue8) {

        String queryString = "SELECT dFROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " " + parameterValue8 + " :" + parameterQuery6;

            } else {
                queryString += " AND d." + entityParameterQuery6 + " " + parameterValue8 + " :" + parameterQuery6;
            }
        }
        if (beginningDate != null) {
            if (endingDate != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7Of1;

                } else {
                    queryString += " AND d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7Of1;
                }
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (beginningDate != null) {
            query.setParameter(parameterQuery6, beginningDate);
        }
        if (endingDate != null) {
            query.setParameter(parameterQuery7Of1, endingDate);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndDate(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery3Of1,
                                                Integer parameterValue1, Integer parameterValue2, Date beginningDate, Date endingDate) {

        String queryString = "SELECT dFROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (beginningDate != null) {
            if (endingDate != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3Of1;

                } else {
                    queryString += " AND d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3Of1;
                }
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        if (beginningDate != null) {
            query.setParameter(parameterQuery3, beginningDate);
        }
        if (endingDate != null) {
            query.setParameter(parameterQuery3Of1, endingDate);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAnd14thDate(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6,
                                                    String entityParameterQuery7, String entityParameterQuery8, String entityParameterQuery9, String entityParameterQuery10, String entityParameterQuery11, String entityParameterQuery12, String entityParameterQuery13, String entityParameterQuery14,
                                                    String entityParameterQuery15, String entityParameterQuery16,
                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                    String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10, String parameterQuery11, String parameterQuery12, String parameterQuery13, String parameterQuery14, String parameterQuery15, String parameterQuery16,
                                                    Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4, Date parameterValue5, Date parameterValue6,
                                                    Date parameterValue7, Date parameterValue8, Date parameterValue9, Date parameterValue10, Date parameterValue11, Date parameterValue12, Date parameterValue13, Date parameterValue14, Date parameterValue15, Date parameterValue16
    ) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;

            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityParameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityParameterQuery8 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityParameterQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND d." + entityParameterQuery10 + " = :" + parameterQuery10;
            }
        }
        if (parameterValue11 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery11 + " = :" + parameterQuery11;
            } else {
                queryString += " AND d." + entityParameterQuery11 + " = :" + parameterQuery11;
            }
        }
        if (parameterValue12 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery12 + " = :" + parameterQuery12;
            } else {
                queryString += " AND d." + entityParameterQuery12 + " = :" + parameterQuery12;
            }
        }
        if (parameterValue13 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery13 + " = :" + parameterQuery13;
            } else {
                queryString += " AND d." + entityParameterQuery13 + " = :" + parameterQuery13;
            }
        }
        if (parameterValue14 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery14 + " = :" + parameterQuery14;
            } else {
                queryString += " AND d." + entityParameterQuery14 + " = :" + parameterQuery14;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue9 != null) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != null) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != null) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        if (parameterValue12 != null) {
            query.setParameter(parameterQuery12, parameterValue12);
        }
        if (parameterValue13 != null) {
            query.setParameter(parameterQuery13, parameterValue13);
        }
        if (parameterValue14 != null) {
            query.setParameter(parameterQuery14, parameterValue14);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryForThreeIntAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoStringAndTwoIntAndBetweenTwoIntAndBetweenTwoDouble(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5of1, String parameterQuery6, String parameterQuery6of1,
                                                                                        String parameterValue1, String parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue5of1, Double parameterValue6, Double parameterValue6of1) {

        String queryString = "SELECT l FROM " + theClass + " l WHERE ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " l.lotOrSerialNumber = :" + parameterQuery1;
            } else {
                queryString += " AND l.lotOrSerialNumber = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " l.supplierLotNumber = :" + parameterQuery2;
            } else {
                queryString += " AND l.supplierLotNumber = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " l.itemNumberShort.tMNumber = :" + parameterQuery3;
            } else {
                queryString += " AND l.itemNumberShort.tMNumber = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " l.businessUnit.bUMNumber = :" + parameterQuery4;
            } else {
                queryString += " AND l.businessUnit.bUMNumber = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (parameterValue5of1 != 0) {
                if (queryString.endsWith("d ")) {
                    queryString += " l.lotGrade.id BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                } else {
                    queryString += " AND l.lotGrade.id BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                }
            }
        }
        if (parameterValue6 != 0.0) {
            if (parameterValue6of1 != 0.0) {
                if (queryString.endsWith("d ")) {
                    queryString += " l.lotPotency BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                } else {
                    queryString += " AND l.lotPotency BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                }
            }
        }
        Query query = em.createQuery(queryString);
        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue5of1 != 0) {
            query.setParameter(parameterQuery5of1, parameterValue5of1);
        }
        if (parameterValue6 != 0.0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue6of1 != 0.0) {
            query.setParameter(parameterQuery6of1, parameterValue6of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndStringAndBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery4of1,
                                                                    Integer parameterValue1, Integer parameterValue2, String parameterValue3, Date parameterValue4, Date parameterValue4of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (parameterQuery4of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                } else {
                    queryString += " AND d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue4of1 != null) {
            query.setParameter(parameterQuery4of1, parameterValue4of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryFourIntAndStringAndBetweenStringAndBetweenDate(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6, String entityParameterQuery7,
                                                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery6of1, String parameterQuery7, String parameterQuery7of1,
                                                                              Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, String parameterValue6, String parameterValue6of1, Date parameterValue7, Date parameterValue7of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }

        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (!parameterValue6of1.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                } else {
                    queryString += " AND d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                }
            }
        }
        if (parameterValue7 != null) {
            if (parameterValue7of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                } else {
                    queryString += " AND d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue6of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6of1, parameterValue6of1);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue7of1 != null) {
            query.setParameter(parameterQuery7of1, parameterValue7of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryForIntAndCharacter(String theClass, String entityParameterQuery1, String entityParameterQuery2, String parameterQuery1, String parameterQuery2, Integer parameterValue1, Character parameterValue2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryThreeIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String parameterQuery1,
                                                String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String parameterQuery1,
                                              String parameterQuery2, Integer parameterValue1, Integer parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryForAccountLedgerReconciliation(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6,
                                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5Of1, String parameterQuery6,
                                                              Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date beginningDate, Date endingDate, String parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d WHERE d.autoOffsetMethodSYorB IN (:autoOffsetMethodY, :autoOffsetMethodS) ";
        if (parameterValue1 != 0) {
            queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
        }
        if (parameterValue3 != 0) {
            queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
        }
        if (parameterValue4 != 0) {
            queryString += " AND d." + entityParameterQuery4 + " =:" + parameterQuery4;
        }
        if (beginningDate != null) {
            if (endingDate != null) {
                queryString += " WHERE d." + entityParameterQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5Of1;

            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            queryString += " AND d." + entityParameterQuery6 + " =:" + parameterQuery6;
        }
        Query query = em.createQuery(queryString);
        query.setParameter("autoOffsetMethodY", 'Y');
        query.setParameter("autoOffsetMethodS", 'S');

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (beginningDate != null) {
            query.setParameter(parameterQuery5, beginningDate);
        }
        if (endingDate != null) {
            query.setParameter(parameterQuery5Of1, endingDate);
        }
        if (parameterQuery6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();

    }

    //New 2:
    public List<?> dynamicQueryIntAndFiveCharGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                     Integer parameterValue1, Character parameterValue2, Character parameterValue3, Character parameterValue4, Character parameterValue5, Character parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndDoubleGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Double parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0.0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                      String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, String parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                      String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, String parameterValue2, String parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndDateGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                 String parameterQuery1, String parameterQuery2, Integer parameterValue1, Date parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                              Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String parameterQuery1,
                                                   String parameterQuery2, Integer parameterValue1, String parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d";
        boolean whereClauseAdded = false;

        if (parameterValue1 != null && parameterValue1 != 0) {
            queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            whereClauseAdded = true;
        }

        if (parameterValue2 != null && !parameterValue2.isEmpty()) {
            if (whereClauseAdded) {
                queryString += " AND d." + entityQuery2 + " LIKE :param2";
            } else {
                queryString += " WHERE d." + entityQuery2 + " LIKE :param2";
                whereClauseAdded = true;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null && parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }

        if (parameterValue2 != null && !parameterValue2.isEmpty()) {
            query.setParameter("param2", parameterValue2 + "%");
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndStringAndDateGeneral(
            String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
            String parameterQuery1, String parameterQuery2, String parameterQuery3,
            Integer parameterValue1, String parameterValue2, Date parameterValue3) {

        String queryString = "SELECT d FROM " + theClass + " d";
        boolean whereClauseAdded = false;

        // Integer Condition
        if (parameterValue1 != null && parameterValue1 != 0) {
            queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            whereClauseAdded = true;
        }

        // String Condition
        if (parameterValue2 != null && !parameterValue2.isEmpty()) {
            if (whereClauseAdded) {
                queryString += " AND d." + entityQuery2 + " LIKE :param2";
            } else {
                queryString += " WHERE d." + entityQuery2 + " LIKE :param2";
                whereClauseAdded = true;
            }
        }

        // Double Condition
        if (parameterValue3 != null) {
            if (whereClauseAdded) {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);

        // Set Parameters
        if (parameterValue1 != null && parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }

        if (parameterValue2 != null && !parameterValue2.isEmpty()) {
            query.setParameter("param2", parameterValue2 + "%");
        }

        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                     Integer parameterValue1, Integer parameterValue2, Date parameterValue3) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

//    public List<MicroProducts> getMicroProductsWithSubsByProductId(Integer productId) {
//        String query = "SELECT mp.id AS micro_id,  mp.name AS micro_name,  mp.description AS micro_description, mp.price, mp.image_url, mp.stock, smp.id AS sub_id, smp.name AS sub_name,  smp.description AS sub_description FROM  micro_products mp LEFT JOIN sub_micro_products smp  ON mp.id = smp.micro_product_id WHERE mp.product_id = ?";
//
//        Query query1s = em.createNamedQuery(query);
//        Map<Long, MicroProducts> microProductMap = new LinkedHashMap<>();
//        ResultSet rs = query1s;
//
//        while (rs.next()) {
//            long microId = rs.getLong("micro_id");
//            MicroProducts micro = microProductMap.get(microId);
//
//            if (micro == null) {
//                micro = new MicroProducts();
//                micro.setId((int) microId);
//                micro.setName(rs.getString("micro_name"));
//                micro.setDescription(rs.getString("micro_description"));
//                micro.setPrice(rs.getDouble("price"));
//                micro.setImageUrl(rs.getString("image_url"));
//                micro.setStock(rs.getInt("stock"));
//                micro.setSubMicroProductsCollection(new ArrayList<>());
//                microProductMap.put(microId, micro);
//            }
//
//            long subId = rs.getLong("sub_id");
//            if (!rs.wasNull()) {
//                SubMicroProduct sub = new SubMicroProduct();
//                sub.setId((int) subId);
//                sub.setName(rs.getString("sub_name"));
//                sub.setDescription(rs.getString("sub_description"));
//                micro.getSubMicroProducts().add(sub);
//            }
//        }
//
//        return new ArrayList<>(microProductMap.values());
//    }

    public List<?> dynamicQueryTwoIntsAndTwoStringAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                                 Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, Date parameterValue5) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                               String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();

    }

    public List<?> dynamicQuerySixIntsAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, String parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryStringAndTwoIntAndBetweenIntGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                                   String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery4of1,
                                                                   String parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue4of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (parameterValue4of1 != 0) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                } else {
                    queryString += " AND d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue4of1 != 0) {
            query.setParameter(parameterQuery4of1, parameterValue4of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryStringAndEightIntGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6, String entityParameterQuery7, String entityParameterQuery8, String entityParameterQuery9,
                                                        String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9,
                                                        String parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityParameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityParameterQuery8 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityParameterQuery9 + " = :" + parameterQuery9;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryThreeIntsAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTowDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Date parameterValue2, Date parameterValue3) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQuerySixIntAndTwoDates(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, String parameter7, String parameter8, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Date date1, Date date2) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }
        if (date1 != null) {
            query.setParameter(parameter7, date1);
        }
        if (date2 != null) {
            query.setParameter(parameter8, date2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntOneStringOneDate(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, Date parameterValue6) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (!parameterValue5.isEmpty()) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue6);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTenIntGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10, String entityQuery11,
                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10, String parameterQuery11,
                                             Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9, Integer parameterValue10, Integer parameterValue11) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + parameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + parameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + parameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + parameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + parameterQuery8 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + parameterQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND d." + parameterQuery10 + " = :" + parameterQuery10;
            }
        }
        if (parameterValue11 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery11 + " = :" + parameterQuery11;
            } else {
                queryString += " AND d." + parameterQuery11 + " = :" + parameterQuery11;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }

        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryForAccountPayable(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery8, String entityQuery9, String entityQuery11,
                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery8, String parameterQuery9, String parameterQuery11,
                                                 Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date date, Date date1, String parameterValue7, String parameterValue8, String parameterValue9, String parameterValue10, Double parameterValue11) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (date != null && date1 != null && parameterValue7.equalsIgnoreCase("invoice")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery6;

            } else {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery6;
            }
        } else if (date != null && date1 != null && parameterValue7.equalsIgnoreCase("gl")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery6;

            } else {
                queryString += " WHERE d." + entityQuery6 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery6;
            }
        }

        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue11 != 0.0 && !parameterValue10.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery11 + " " + parameterValue10 + " :" + parameterQuery11;
            } else {
                queryString += " AND d." + entityQuery11 + " " + parameterValue10 + " :" + parameterQuery11;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        if (date != null) {
            query.setParameter(parameterQuery5, date);
        }
        if (date1 != null) {
            query.setParameter(parameterQuery6, date1);
        }

        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue11 != 0.0) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery6Of1,
                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Date fromDate, Date thruDate) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (fromDate != null) {
            if (thruDate != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6Of1;
                } else {
                    queryString += " AND d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6Of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery6 + " >= :" + parameterQuery6;
                } else {
                    queryString += " AND d." + entityQuery6 + " >= :" + parameterQuery6;
                }
            }
        } else if (thruDate != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " <= :" + parameterQuery6Of1;
            } else {
                queryString += " AND d." + entityQuery6 + " <= :" + parameterQuery6Of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (fromDate != null) {
            query.setParameter(parameterQuery6, fromDate);
        }
        if (thruDate != null) {
            query.setParameter(parameterQuery6Of1, thruDate);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryElevenIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10, String entityQuery11,
                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10, String parameterQuery11,
                                                 Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9, Integer parameterValue10, Integer parameterValue11) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + parameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + parameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + parameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + parameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + parameterQuery8 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + parameterQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND d." + parameterQuery10 + " = :" + parameterQuery10;
            }
        }
        if (parameterValue11 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery11 + " = :" + parameterQuery11;
            } else {
                queryString += " AND d." + parameterQuery11 + " = :" + parameterQuery11;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }

        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndTwoCharacterGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                               String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Character parameterValue4, Character parameterValue5) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeStringGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                                  String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                  String parameterValue1, String parameterValue2, String parameterValue3) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQuerySevenIntsAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8,
                                                         Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, String parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryFourIntAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndTwoInt(String queryString, String parameter1, String parameter2, String parameter3, String parameterValue1, Integer parameterValue2, Integer parameterValue3) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndThreeStringWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5Of1, String parameterQuery6, String parameterQuery7, String parameterQuery7Of1, String parameterQuery8,
                                                                    Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String invoiceNoFrom, String invoiceNoThru, String parameterValue6, Date dateFrom, Date dateThru, Character parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery4;
            }
        }
        if (!invoiceNoFrom.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        } else if (!invoiceNoThru.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5Of1;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5Of1;
            }
        } else if (!invoiceNoFrom.equalsIgnoreCase("") && !invoiceNoThru.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5Of1;
            } else {
                queryString += " AND d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5Of1;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (dateFrom != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        } else if (dateThru != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7Of1;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7Of1;
            }
        } else if (dateFrom != null && dateThru != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7Of1;
            } else {
                queryString += " AND d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7Of1;
            }
        }
        if (parameterValue8.equals('Y')) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " IS NOT NULL :";
            } else {
                queryString += " AND d." + entityQuery8 + " IS NOT NULL :";
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!invoiceNoFrom.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, invoiceNoFrom);
        }
        if (!invoiceNoThru.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5Of1, invoiceNoThru);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (dateFrom != null) {
            query.setParameter(parameterQuery7, dateFrom);
        }
        if (dateThru != null) {
            query.setParameter(parameterQuery7Of1, dateThru);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntGeneral(String theClass, String entityQuery1, String parameterQuery1, Integer parameterValue1) {
        StringBuilder queryString = new StringBuilder("SELECT d FROM ").append(theClass).append(" d ");
        if (parameterValue1 != 0) {
            queryString.append("WHERE d.").append(entityQuery1).append(" = :").append(parameterQuery1);
        }

        TypedQuery<?> query = em.createQuery(queryString.toString(), Object.class);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForAAIGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, String parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.startsWith("RC")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntsAndStringAndBetweenStringAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                                               String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7of1, String parameterQuery8,
                                                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6, String parameterValue7, String parameterValue7of1, Date parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (!parameterValue7of1.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                } else {
                    queryString += " AND d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
                } else {
                    queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
                }
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue7of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7of1, parameterValue7of1);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryFourIntsAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                      String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                      Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date parameterValue5) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryTwoIntAndStringAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                             Integer parameterValue1, Integer parameterValue2, String parameterValue3, Date parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }

        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();

    }

    public List<?> dynamicQuerySixIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                              Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryIntAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                        String parameterQuery1, String parameterQuery2, String parameterQuery2of1, Integer parameterValue1, Date parameterValue2, Date parameterValue2of1) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (parameterValue2of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND " + parameterQuery2of1;
                } else {
                    queryString += " AND d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND " + parameterQuery2of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
                } else {
                    queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
                }
            }
        } else if (parameterValue2of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2of1;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue2of1 != null) {
            query.setParameter(parameterQuery2of1, parameterValue2of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndTwoDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                          Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Date parameterValue4, Date parameterValue5) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            queryString += " WHERE d. " + entityParameterQuery1 + " = :" + parameterQuery1;
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d. " + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndThreeIntsGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                         String parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryFourIntAndTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                          Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, String parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsWithRelationGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String oprand1, String oprand2, String oprand3, String relation1, String relation2, String relation3) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " " + relation1 + " :" + parameterQuery1;
            } else if (!relation1.equalsIgnoreCase("")) {
                queryString += " " + oprand1 + " d." + entityQuery1 + " " + relation1 + " :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " " + relation2 + " :" + parameterQuery2;
            } else if (!relation2.equalsIgnoreCase("")) {
                queryString += " " + oprand2 + " d." + entityQuery2 + " " + relation2 + " :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " " + relation3 + " :" + parameterQuery3;
            } else if (!relation3.equalsIgnoreCase("")) {
                queryString += " " + oprand3 + " d." + entityQuery3 + " " + relation3 + " :" + parameterQuery3;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryFiveIntAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE  d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND  d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE  d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND  d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndDoubleAndDateAndBetweenDate(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery3of1, Integer parameterValue1, Double parameterValue2, Date parameterValue3, Date parameterValue3of1, Boolean boolean1) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (boolean1 == false) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " IS NULL";
            } else {
                queryString += " AND d." + entityQuery3 + " IS NULL";
            }
        }
        if (parameterValue3 != null) {
            if (parameterValue3of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery4 + " BETWEEN :" + parameterQuery3 + " AND " + parameterQuery3of1;
                } else {
                    queryString += " AND d." + entityQuery4 + " BETWEEN :" + parameterQuery3 + " AND " + parameterQuery3of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery4 + " >= :" + parameterQuery3;
                } else {
                    queryString += " AND d." + entityQuery4 + " >= :" + parameterQuery3;
                }
            }
        } else if (parameterValue3of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " <= :" + parameterQuery3of1;
            } else {
                queryString += " AND d." + entityQuery4 + " <= :" + parameterQuery3of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0.0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue3of1 != null) {
            query.setParameter(parameterQuery3of1, parameterValue3of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoStringsGeneral(String theClass, String entityQuery1, String entityQuery2, String parameterQuery1,
                                                 String parameterQuery2, String parameterValue1, String parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndCharacterAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5of1,
                                                                          Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Character parameterValue4, Date parameterValue5, Date parameterValue5of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (parameterValue5of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += "WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                } else {
                    queryString += " AND d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                }
            } else if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        } else if (parameterValue5of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5of1;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5of1;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue5of1 != null) {
            query.setParameter(parameterQuery5of1, parameterValue5of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTenIntAndFourStringAndBetweeenDouble(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                    String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10,
                                                                    String parameterQuery11, String parameterQuery12, String parameterQuery13, String parameterQuery14, String parameterQuery15, String parameterQuery15of1,
                                                                    Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6,
                                                                    Integer parameterValue7, Integer parameterValue8, Integer parameterValue9, Integer parameterValue10, String parameterValue11,
                                                                    String parameterValue12, String parameterValue13, String parameterValue14, Double parameterValue15, Double parameterValue15of1) {

        String queryString = "SELECT i FROM " + theClass + " i ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.businessUnit.bUMNumber = :" + parameterQuery1;
            } else {
                queryString += " AND i.businessUnit.bUMNumber = :" + parameterQuery1;
            }
        }

        if (parameterValue2 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.itemNumberShort.tMNumber = :" + parameterQuery2;
            } else {
                queryString += " AND i.itemNumberShort.tMNumber = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.uomPrimary.id = :" + parameterQuery3;
            } else {
                queryString += " AND i.uomPrimary.id = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.uomSecondary.id = :" + parameterQuery4;
            } else {
                queryString += " AND i.uomSecondary.id = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.categoryCode01.id = :" + parameterQuery5;
            } else {
                queryString += " AND i.categoryCode01.id = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.categoryCode02.id = :" + parameterQuery6;
            } else {
                queryString += " AND i.categoryCode02.id = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.categoryCode03.id = :" + parameterQuery7;
            } else {
                queryString += " AND i.categoryCode03.id = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.gradeRange.id = :" + parameterQuery8;
            } else {
                queryString += " AND i.gradeRange.id = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.location.lMNumber = :" + parameterQuery9;
            } else {
                queryString += " AND i.location.lMNumber = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.lotstatuscodesLSCNumber.id = :" + parameterQuery10;
            } else {
                queryString += " AND i.lotstatuscodesLSCNumber.id = :" + parameterQuery10;
            }
        }
        if (!parameterValue11.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.memoLotSerial1 = :" + parameterQuery11;
            } else {
                queryString += " AND i.memoLotSerial1 = :" + parameterQuery11;
            }
        }
        if (!parameterValue12.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.memoLotSerial2 = :" + parameterQuery12;
            } else {
                queryString += " AND i.memoLotSerial2 = :" + parameterQuery12;
            }
        }
        if (!parameterValue13.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.lotSerialNumber = :" + parameterQuery13;
            } else {
                queryString += " AND i.lotSerialNumber = :" + parameterQuery13;
            }
        }
        if (!parameterValue14.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.toLotSerialNumber = :" + parameterQuery14;
            } else {
                queryString += " AND i.toLotSerialNumber = :" + parameterQuery14;
            }
        }
        if (parameterValue15 != 0.0) {
            if (parameterValue15of1 != 0.0) {
                if (queryString.endsWith("i ")) {
                    queryString += "WHERE i.potencyRange BETWEEN :" + parameterQuery15 + " AND " + parameterQuery15of1;
                } else {
                    queryString += " AND i.potencyRange BETWEEN :" + parameterQuery15 + " AND " + parameterQuery15of1;
                }
            }
            if (queryString.endsWith("i ")) {
                queryString += "WHERE i.potencyRange = :" + parameterQuery15;
            } else {
                queryString += " AND i.potencyRange = :" + parameterQuery15;
            }

        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (!parameterValue11.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        if (!parameterValue12.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery12, parameterValue12);
        }
        if (!parameterValue13.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery13, parameterValue13);
        }
        if (!parameterValue14.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery14, parameterValue14);
        }
        if (parameterValue15 != 0.0) {
            query.setParameter(parameterQuery15, parameterValue15);
        }
        if (parameterValue15of1 != 0.0) {
            query.setParameter(parameterQuery15of1, parameterValue15of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoStringAndTwoInt(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4) {

        Query query = em.createQuery(queryString);
        if (!parameterValue1.isEmpty()) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (!parameterValue4.isEmpty()) {
            query.setParameter(parameter4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndSkipToIntGeneral(String theClass, String entityQuery1, String entityQuery2, String parameterQuery1,
                                                      String parameterQuery2, Integer parameterValue1, Integer parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " >= :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " >= :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryForTwoIntAndStringBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery4Of1,
                                                                    Integer parameterValue1, Integer parameterValue2, String parameterValue3, Date parameterValue4, Date parameterValue4Of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        if (parameterValue4 != null && parameterValue4Of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND  :" + parameterValue4Of1;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND  :" + parameterValue4Of1;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntsAndBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5of1,
                                                             Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date parameterValue5, Date parameterValue5of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (parameterValue5of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                } else {
                    queryString += " AND d." + entityParameterQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue5of1 != null) {
            query.setParameter(parameterQuery5of1, parameterValue5of1);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryTwoIntsAndBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3,
                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery3of1,
                                                            Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue3of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }

        if (parameterValue3 != null) {
            if (parameterValue3of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3of1;
                } else {
                    queryString += " AND d." + entityParameterQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue3of1 != null) {
            query.setParameter(parameterQuery3of1, parameterValue3of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryForThreeIntAndStringGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForThreeIntAndExcludedStringGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                                   String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                   Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " <> :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " <> :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryForFourIntAndDate(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                 Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date parameterValue5) {

        String queryString = "SELECT dFROM " + theClass + " d ";
        if (parameterValue1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTenCharGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10, String entityQuery11,
                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10, String parameterQuery11,
                                                    Integer parameterValue1, Character parameterValue2, Character parameterValue3, Character parameterValue4, Character parameterValue5, Character parameterValue6, Character parameterValue7, Character parameterValue8, Character parameterValue9, Character parameterValue10, Character parameterValue11) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND d." + entityQuery10 + " = :" + parameterQuery10;
            }
        }
        if (parameterValue11 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery11 + " = :" + parameterQuery11;
            } else {
                queryString += " AND d." + entityQuery11 + " = :" + parameterQuery11;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue9 != null) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != null) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != null) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndThreeStringAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6,
                                                                  String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery4Of1, String parameterQuery5, String parameterQuery6, String parameterQuery6Of1,
                                                                  Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, String parameterValue4Of1, String parameterValue5, Date parameterValue6, Date parameterValue6Of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue4Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4Of1;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4Of1;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("") && !parameterValue4Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " IN (:parameterQuery4, :parameterQuery4Of1) :";
            } else {
                queryString += " AND d." + entityParameterQuery4 + " IN (:parameterQuery4, :parameterQuery4Of1) :";
            }
        }

        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null && parameterValue6Of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " BETWEEN :" + parameterQuery6 + " AND  :" + parameterValue6Of1;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " BETWEEN :" + parameterQuery6 + " AND  :" + parameterValue6Of1;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue4Of1.equalsIgnoreCase("")) {
            query.setParameter(parameterValue4Of1, parameterValue4Of1);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntAndStringAndDateBetweenGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5,
                                                                      String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5Of1,
                                                                      Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4, Date parameterValue5, Date parameterValue5Of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }

        if (parameterValue5 != null && parameterValue5Of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " BETWEEN :" + parameterQuery5 + " AND  :" + parameterValue5Of1;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " BETWEEN :" + parameterQuery5 + " AND  :" + parameterValue5Of1;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQuerySixIntsAndThreeStringAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10,
                                                                   String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10,
                                                                   Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, String parameterValue7, String parameterValue8, String parameterValue9, Date parameterValue10) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        if (parameterValue10 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != null) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryIntAndFourStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                       Integer parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntsAndCharacterAndTwoDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                                     Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Character parameterValue5, Date parameterValue6, Date parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryNineIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9,
                                               String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9,
                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + parameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + parameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + parameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + parameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + parameterQuery8 + " = :" + parameterQuery8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + parameterQuery9 + " = :" + parameterQuery9;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }

        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndBetweenIntGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery2of1, Integer parameterValue1, Integer parameterValue2, Integer parameterValue2of1) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (parameterValue2of1 != 0) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND :" + parameterQuery2of1;
                } else {
                    queryString += " AND d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND :" + parameterQuery2of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
                } else {
                    queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
                }
            }
        } else if (parameterValue2of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2of1;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue2of1 != 0) {
            query.setParameter(parameterQuery2of1, parameterValue2of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndTwoDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                        Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndStringWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, String parameterValue2, Date parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryEightIntsGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8,
                                                Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + parameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + parameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + parameterQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + parameterQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + parameterQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + parameterQuery8 + " = :" + parameterQuery8;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntsAndCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                           Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Character parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();

    }

    public List<?> dynamicQuerySixIntsAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                     Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Date parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();

    }

    public List<?> dynamicQueryTwoIntAndCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, Integer parameterValue1, Integer parameterValue2, Character parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String parameterQuery1,
                                                      String parameterQuery2, Integer parameterValue1, Character parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryCharacterGeneral(String theClass, String entityQuery2, String parameterQuery1,
                                                String parameterQuery2, Character parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndTwoStringAndBetweenTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5Of1, String parameterQuery6, String parameterQuery6Of1,
                                                                             Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue5Of1, String parameterValue6, String parameterValue6Of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("") && !parameterValue5Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + "AND :" + parameterQuery5Of1;
            } else {
                queryString += " AND d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + "AND :" + parameterQuery5Of1;
            }
        } else if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " >= :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " >= :" + parameterQuery5;
            }
        } else if (!parameterValue5Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " <= :" + parameterQuery5Of1;
            } else {
                queryString += " AND d." + entityQuery5 + " <= :" + parameterQuery5Of1;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("") && !parameterValue6Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + "AND :" + parameterQuery6Of1;
            } else {
                queryString += " AND d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + "AND :" + parameterQuery6Of1;
            }
        } else if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " >= :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " >= :" + parameterQuery6;
            }
        } else if (!parameterValue6Of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " <= :" + parameterQuery6Of1;
            } else {
                queryString += " AND d." + entityQuery6 + " <= :" + parameterQuery6Of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue5Of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5Of1, parameterValue5Of1);
        }
        if (!parameterValue6Of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6Of1, parameterValue6Of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryStringGeneral(String theClass, String entityQuery1, String parameterQuery1, String parameterValue1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE  d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND  d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTenStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10,
                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10,
                                                String parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6,
                                                String parameterValue7, String parameterValue8, String parameterValue9, String parameterValue10) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND  d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND  d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND  d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND  d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND  d." + entityQuery10 + " = :" + parameterQuery10;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery10, parameterValue10);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndTwoIntsAndBetweenIntAndBetweenDoubleGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                                                    String parameterQuery4, String parameterQuery4of2, String parameterQuery5, String parameterQuery5of2, String parameterValue1, Integer parameterValue2, Integer parameterValue3,
                                                                                    Integer parameterValue4, Integer parameterValue4of2, Double parameterValue5, Double parameterValue5of2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND  d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }

        if (parameterValue4 != 0) {
            if (parameterValue4of2 != 0) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery4 + " BETWEEN :" + parameterQuery4 + " AND  :" + parameterQuery4of2;
                } else {
                    queryString += " AND d." + entityQuery4 + " BETWEEN :" + parameterQuery4 + " AND  :" + parameterQuery4of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
                } else {
                    queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
                }

            }
        }

        if (parameterValue5 != 0.0) {
            if (parameterValue5of2 != 0.0) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND  :" + parameterQuery5of2;
                } else {
                    queryString += " AND  d." + entityQuery4 + " BETWEEN :" + parameterQuery5 + " AND  :" + parameterQuery5of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " d." + entityQuery5 + " = :" + parameterQuery5;
                } else {
                    queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
                }

            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue4of2 != 0) {
            query.setParameter(parameterQuery4of2, parameterValue4of2);
        }
        if (parameterValue5 != 0.0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue5of2 != 0.0) {
            query.setParameter(parameterQuery5of2, parameterValue5of2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndStringAndBetweenStringAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                                     String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7of2, String parameterQuery8, String parameterQuery8of2, Integer parameterValue1, Integer parameterValue2,
                                                                                     Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6, String parameterValue7, String parameterValue7of2, Date parameterValue8, Date parameterValue8of2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND  d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (!parameterValue7of2.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND  :" + parameterQuery7of2;
                } else {
                    queryString += " AND d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND  :" + parameterQuery7of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
                } else {
                    queryString += " AND  d." + entityQuery7 + " = :" + parameterQuery7;
                }
            }
        }
        if (parameterValue8 != null) {
            if (parameterValue8of2 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND  :" + parameterQuery8of2;
                } else {
                    queryString += " AND d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND  :" + parameterQuery8of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
                } else {
                    queryString += " AND  d." + entityQuery8 + " = :" + parameterQuery8;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue7of2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7of2, parameterValue7of2);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue8of2 != null) {
            query.setParameter(parameterQuery8of2, parameterValue8of2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndDoubleWithRelationGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                                     String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Double parameterValue4, String parameterValue) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND  d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0.0) {
            if (queryString.endsWith("d ")) {
                if (!parameterValue.equalsIgnoreCase("")) {
                    queryString += " WHERE d." + entityQuery4 + parameterValue + " :" + parameterQuery4;
                }
            } else {
                if (!parameterValue.equalsIgnoreCase("")) {
                    queryString += " AND d." + entityQuery4 + parameterValue + " :" + parameterQuery4;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0.0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryDataSelection(String theClass, String dataSelectionQueryString, String[] paramerterQery, Integer[] parameterValueInteger, Double[] parameterValueDouble, String[] parameterValueString, Character[] parameterValueCharacter, Date[] parameterValueDate) {
        String queryString = "SELECT d FROM " + theClass + " d " + dataSelectionQueryString;
        Query query = em.createQuery(queryString);
        for (int i = 0; i < paramerterQery.length; i++) {
            if (parameterValueInteger[i] != 0) {
                query.setParameter(paramerterQery[i], parameterValueInteger[i]);
            } else if (parameterValueDouble[i] != 0.0) {
                query.setParameter(paramerterQery[i], parameterValueDouble[i]);
            } else if (!parameterValueString[i].equalsIgnoreCase("")) {
                query.setParameter(paramerterQery[i], parameterValueString[i]);
            } else if (parameterValueCharacter[i] != null) {
                query.setParameter(paramerterQery[i], parameterValueCharacter[i]);
            } else if (parameterValueDate[i] != null) {
                query.setParameter(paramerterQery[i], parameterValueDate[i]);
            }
        }
        return query.getResultList();
    }

    /**
     * LUCY ERP GENERAL QUERIES
     * ---------------------------------------------------------------- The
     * following Methods are used for General Query filtering methods.
     *
     *
     */
    /**
     * It is a createNamedQuery to filter a List
     * ---------------------------------------------------------------- The
     * parameterQueriesGeneral should be mapped its Entity Query Name with its
     * Value, and The value Should NOT be NULL
     * ----------------------------------------------------------------
     *
     * @param queryString
     * @return List
     */
    public List<?> findByQuery(String queryString) {
        Query query = em.createNamedQuery(queryString);
        for (Map.Entry<String, Object> entry : parameterQueriesGeneral.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        parameterQueriesGeneral.clear();
        return query.getResultList();
    }

    /**
     * Executes a dynamic query based on the given class and dynamic query
     * parameters.
     * ----------------------------------------------------------------
     *
     * @param theClass The class name on which the query will be executed.
     * @return A list of results from the dynamic query.
     */
    public List<?> dynamicQueryGeneral(String theClass) {
//        try {
        // Construct the initial query string
        String queryString = "SELECT d FROM " + theClass + " d ";

        // Add any specified join clauses
//        for (Map.Entry<String, String> entry : queryStringGeneral.entrySet()) {
//            String joinClause = entry.getKey();
//            queryString += " " + joinClause + " ";
//        }
//
//        // Add any specified conditions
//        boolean isFirstCondition = true;
//        for (Map.Entry<String, String> entry : queryStringGeneral.entrySet()) {
//            String condition = entry.getValue();
//            if (isFirstCondition) {
//                queryString += "WHERE " + condition + " ";
//                isFirstCondition = false;
//            } else {
//                queryString += "AND " + condition + " ";
//            }
//        }
        // Iterate over the general query string entries to customize the query
        for (Map.Entry<String, String> entry : queryStringGeneral.entrySet()) {
            String parameterQuery = entry.getKey();
            queryString = checkIfUnaryBinaryMultiParameter(queryString, parameterQuery);
        }

        // Create a query object using the modified query string
        Query query = em.createQuery(queryString);

        // System.out.println(queryString);
        /**
         * Set parameter values in the query Each key is a parameter query,
         * which is corresponding to its value
         */
        for (Map.Entry<String, Object> entry : parameterValueFinal.entrySet()) {
            String parameterQuery = entry.getKey();
            query.setParameter(parameterQuery, entry.getValue());
        }

        // Clear operator, query string, and parameter maps for future use
        clearingGeneralQueryObjects();

        // Execute the query and return the results
        return query.getResultList();
//        } catch (Exception e) {
//            clearingGeneralQueryObjects();
//            e.printStackTrace();
//            JsfUtil.errorMessage();
//            return new ArrayList<>();
//        }
    }

    /**
     * Clears an Abstract Facade Query objects used for general query:
     */
    public void clearingGeneralQueryObjects() {
        // Clear operator, query string, and parameter maps for future use
        operatorGeneral.clear();
        queryStringGeneral.clear();
        parameterQueriesGeneral.clear();
        parameterValueFinal.clear();
    }

    /**
     * Determines the query structure based on the type of dynamic parameter.
     *
     * @param queryString The current query string.
     * @param key         The parameter key.
     * @return Modified query string based on parameter type.
     */
    private String checkIfUnaryBinaryMultiParameter(String queryString, String key) {
        String queyStr = queryStringGeneral.get(key);

        /**
         * Check whether the query parameter take NO OPERATOR (i.e. IS NULL, IS
         * NOT NULL) Check whether the query parameter take UNARY OPERATOR (i.e.
         * >, <, =, >=, <=, <>, LIKE) Check whether the query parameter take
         * BINARY OPERATORS (i.e. BETWEEN, NOT BETWEEN) Check whether the query
         * parameter take MULTI OPERATORS (i.e. IN, NOT IN)
         *
         */
        List<Integer> indece = charOccurenceIndeces(queyStr, ':');
        if (!queyStr.contains(":")) {
            return noOperandQuery(queryString, key);
        } else if (indece.size() == 1 || queyStr.contains(">") || queyStr.contains("<") || queyStr.contains("=") || queyStr.contains(" LIKE ")) {
            return unaryParameter(queryString, key);
        } else if (indece.size() == 2 && queyStr.contains(" BETWEEN ")) {
            return binaryParameter(queryString, key);
        } else if (indece.size() >= 2 && queyStr.contains(" IN ")) {
            return multiParameter(queryString, key);
        } else {
            return queryString;
        }
    }

    /**
     * Constructs a query string for IS NULL condition.
     *
     * @param queryString The current query string.
     * @param key         The parameter key.
     * @return Modified query string with IS NULL condition.
     */
    private String noOperandQuery(String queryString, String key) {
        if (queryString.endsWith("d ")) {
            queryString += "WHERE ";
        } else {
            queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
        }
        queryString += queryStringGeneral.get(key);
        return queryString;
    }

    /**
     * Constructs a query string for unary parameter conditions.
     *
     * @param queryString The current query string.
     * @param key         The parameter key.
     * @return Modified query string with unary parameter conditions.
     */
    private String unaryParameter(String queryString, String key) {
        if (!shouldSkipParameter(parameterQueriesGeneral.get(key))) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE ";
            } else {
                queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
            }
            queryString += queryStringGeneral.get(key);
            parameterValueFinal.put(key, parameterQueriesGeneral.get(key));
        }
        return queryString;
    }

    /**
     * Constructs a query string for binary parameter conditions.
     *
     * @param queryString The current query string.
     * @param key         The parameter key.
     * @return Modified query string with binary parameter conditions.
     */
    private String binaryParameter(String queryString, String key) {
        List<Integer> indece = charOccurenceIndeces(queryStringGeneral.get(key), ':');
        String key2 = queryStringGeneral.get(key).substring(indece.get(1) + 1);
        int index = queryStringGeneral.get(key).indexOf(" ");
        String entityQuery = queryStringGeneral.get(key).substring(0, index);
        if (!shouldSkipParameter(parameterQueriesGeneral.get(key))
                && !shouldSkipParameter(parameterQueriesGeneral.get(key2))) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE ";
            } else {
                queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
            }
            queryString += queryStringGeneral.get(key);
            parameterValueFinal.put(key, parameterQueriesGeneral.get(key));
            parameterValueFinal.put(key2, parameterQueriesGeneral.get(key2));
        } else if (!shouldSkipParameter(parameterQueriesGeneral.get(key))
                && shouldSkipParameter(parameterQueriesGeneral.get(key2))) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE ";
            } else {
                queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
            }
            queryString += entityQuery + " >= " + key;
            parameterValueFinal.put(key, parameterQueriesGeneral.get(key));
        } else if (shouldSkipParameter(parameterQueriesGeneral.get(key))
                && !shouldSkipParameter(parameterQueriesGeneral.get(key2))) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE ";
            } else {
                queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
            }
            queryString += entityQuery + " <= " + key2;
            parameterValueFinal.put(key2, parameterQueriesGeneral.get(key2));
        }
        return queryString;
    }

    /**
     * Constructs a query string for multi-parameter conditions.
     *
     * @param queryString The current query string.
     * @param key         The parameter key.
     * @return Modified query string with multi-parameter conditions.
     */
    private String multiParameter(String queryString, String key) {
        if (!checkMultipleOperandValue(key)) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE ";
            } else {
                queryString += " " + operatorGeneral.getOrDefault(key, defaultOperator) + " ";
            }
            queryString += queryStringGeneral.get(key);

            List<Integer> indece = charOccurenceIndeces(queryStringGeneral.get(key), ':');
            List<Integer> indeceComa = charOccurenceIndeces(queryStringGeneral.get(key), ',');
            for (Integer i : indece) {
                if (i == indece.get(0)) {
                    parameterValueFinal.put(key, parameterQueriesGeneral.get(key));
                } else {
                    int beginEndex = i + 1;
                    int endIndex = indexOfParameter(beginEndex, indeceComa);
                    int endIndex2 = queryStringGeneral.get(key).indexOf(")");
                    String key2 = endIndex > 0 ? queryStringGeneral.get(key).substring(beginEndex, endIndex)
                            : queryStringGeneral.get(key).substring(beginEndex, endIndex2);
                    parameterValueFinal.put(key2, parameterQueriesGeneral.get(key2));
                }

            }
        }
        return queryString;
    }

    private int indexOfParameter(int ref, List<Integer> intList) {
        int x = -1;
        if (!intList.isEmpty()) {
            for (Integer i : intList) {
                if (i > ref) {
                    x = i;
                    break;
                }
            }
        }
        return x;
    }

    private boolean checkMultipleOperandValue(String key) {
        List<Integer> indece = charOccurenceIndeces(queryStringGeneral.get(key), ':');
        List<Integer> indeceComa = charOccurenceIndeces(queryStringGeneral.get(key), ',');
        boolean anyParameterIsNull = false;
        for (Integer i : indece) {
            if (i == indece.get(0)) {
                anyParameterIsNull = shouldSkipParameter(parameterQueriesGeneral.get(key));
            } else {
                int beginEndex = i + 1;
                int endIndex = indexOfParameter(beginEndex, indeceComa);
                int endIndex2 = queryStringGeneral.get(key).indexOf(")");
                String key2 = endIndex > 0 ? queryStringGeneral.get(key).substring(beginEndex, endIndex)
                        : queryStringGeneral.get(key).substring(beginEndex, endIndex2);
                anyParameterIsNull = shouldSkipParameter(parameterQueriesGeneral.get(key2));
            }
            if (anyParameterIsNull) {
                break;
            }

        }
        return anyParameterIsNull;
    }

    /**
     * TRUNCATE:
     * ----------------------------------------------------------------
     * <p>
     * Used To Truncate a database Table
     * ----------------------------------------------------------------
     * <p>
     * Note: The table record should not be used in other table. The table name
     * should be the qualified database table.
     *
     * @param tableName
     */
    public void truncateTable(String tableName) {
        try {
            // Create a native SQL query to truncate the table
            String sql = "TRUNCATE TABLE " + tableName;

            // Execute the query using the EntityManager
            em.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("An error occurred, please contact product vendor");
        }
    }

    /**
     * Processing Options Filter Method:
     * ----------------------------------------------------------------
     *
     * Used For Processing Options
     *
     * @param theClass
     * @return
     */
//    public List<?> dynamicQueryProcessingOptionGeneral(String theClass) {
//        String queryString = "SELECT p FROM " + theClass + " p WHERE (p.businessUnit.company.id = :company OR p.businessUnit.company.name.searchType.code = :searchType)";
//        Query query = em.createQuery(queryString);
//        query.setParameter("company", loginView.getAuthenticatedUser().getBusinessUnit().getCompany().getId());
//        query.setParameter("searchType", "PC");
//
//        return query.getResultList();
//    }

    /**
     * company Filter Method:
     * ----------------------------------------------------------------
     * <p>
     * Used to get value of company or default
     *
     * @param theClass
     * @return
     */
//    public List<?> dynamicQueryCompanyGeneral(String theClass) {
//        String queryString = "SELECT c FROM " + theClass + " c WHERE (c.id = :ids OR c.name.searchType.code = :searchType)";
//        Query query = em.createQuery(queryString);
//        query.setParameter("company", loginView.getAuthenticatedUser().getCompany().getId());
//        query.setParameter("searchType", "PC");
//
//        return query.getResultList();
//    }
    public List<?> dynamicQueryFiveIntAndTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                          Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6, String parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE  d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND  d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE  d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND  d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }

        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND  d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndFourDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8,
                                                         Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date parameterValue5, Date parameterValue6, Date parameterValue7, Date parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryEightIntAndTwoStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9, String entityQuery10,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10,
                                                           Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, String parameterValue9, String parameterValue10) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " = :" + parameterQuery9;
            } else {
                queryString += " AND d." + entityQuery9 + " = :" + parameterQuery9;
            }
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery10 + " = :" + parameterQuery10;
            } else {
                queryString += " AND d." + entityQuery10 + " = :" + parameterQuery10;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndTwoDoubleAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8,
                                                                 Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Double parameterValue6, Double parameterValue7, Date parameterValue8) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0.0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue6 != 0.0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndSkipToIntAndDoubleAndStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8,
                                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Double parameterValue7, String parameterValue8) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " >= :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " >= :" + parameterQuery8;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0.0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryEighteenIntsAndBetweenIntAndStringAndTwoDate(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9,
                                                                            String parameterQuery10, String parameterQuery11, String parameterQuery12, String parameterQuery13, String parameterQuery14, String parameterQuery15, String parameterQuery16, String parameterQuery17, String parameterQuery18,
                                                                            String parameterQuery19of1, String parameterQuery19of2, String parameterQuery20, String parameterQuery21, String parameterQuery22,
                                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9,
                                                                            Integer parameterValue10, Integer parameterValue11, Integer parameterValue12, Integer parameterValue13, Integer parameterValue14, Integer parameterValue15, Integer parameterValue16, Integer parameterValue17, Integer parameterValue18,
                                                                            Integer parameterValue19of1, Integer parameterValue19of2, String parameterValue20, Date parameterValue21, Date parameterValue22) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "itemNumberShort.tMNumber" + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + "itemNumberShort.tMNumber" + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "orderType.id" + " = :" + parameterValue2;
            } else {
                queryString += " AND d." + "orderType.id" + " = :" + parameterValue2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "businessUnit.bUMNumber" + " = :" + parameterValue3;
            } else {
                queryString += " AND d." + "businessUnit.bUMNumber" + " = :" + parameterValue3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "addressNumberOriginator.id" + " = :" + parameterValue4;
            } else {
                queryString += " AND d." + "addressNumberOriginator.id" + " = :" + parameterValue4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "reasonforECO.id" + " = :" + parameterValue5;
            } else {
                queryString += " AND d." + "reasonforECO.id" + " = :" + parameterValue5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "priorityWO.id" + " = :" + parameterValue6;
            } else {
                queryString += " AND d." + "priorityWO.id" + " = :" + parameterValue6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "typeWO.id" + " = :" + parameterValue7;
            } else {
                queryString += " AND d." + "typeWO.id" + " = :" + parameterValue7;
            }
        }
        if (parameterValue8 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "phaseIn.id" + " = :" + parameterValue8;
            } else {
                queryString += " AND d." + "phaseIn.id" + " = :" + parameterValue8;
            }
        }
        if (parameterValue9 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder01.id" + " = :" + parameterValue9;
            } else {
                queryString += " AND d." + "categoriesWorkOrder01.id" + " = :" + parameterValue9;
            }
        }
        if (parameterValue10 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder02.id" + " = :" + parameterValue10;
            } else {
                queryString += " AND d." + "categoriesWorkOrder02.id" + " = :" + parameterValue10;
            }
        }
        if (parameterValue11 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder03.id" + " = :" + parameterValue11;
            } else {
                queryString += " AND d." + "categoriesWorkOrder03.id" + " = :" + parameterValue11;
            }
        }
        if (parameterValue12 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder04.id" + " = :" + parameterValue12;
            } else {
                queryString += " AND d." + "categoriesWorkOrder04.id" + " = :" + parameterValue12;
            }
        }
        if (parameterValue13 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder05.id" + " = :" + parameterValue13;
            } else {
                queryString += " AND d." + "categoriesWorkOrder05.id" + " = :" + parameterValue13;
            }
        }
        if (parameterValue14 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "consultingExperienceLevel.id" + " = :" + parameterValue14;
            } else {
                queryString += " AND d." + "consultingExperienceLevel.id" + " = :" + parameterValue14;
            }
        }
        if (parameterValue15 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "enterpriseOneConsultingServiceType.id" + " = :" + parameterValue15;
            } else {
                queryString += " AND d." + "enterpriseOneConsultingServiceType.id" + " = :" + parameterValue15;
            }
        }
        if (parameterValue16 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "enterpriseOneConsultingSkillType.id" + " = :" + parameterValue16;
            } else {
                queryString += " AND d." + "enterpriseOneConsultingSkillType.id" + " = :" + parameterValue16;
            }
        }
        if (parameterValue17 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "enterpriseOneConsultingStatus.id" + " = :" + parameterValue17;
            } else {
                queryString += " AND d." + "enterpriseOneConsultingStatus.id" + " = :" + parameterValue17;
            }
        }
        if (parameterValue18 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "categoriesWorkOrder10.id" + " = :" + parameterValue18;
            } else {
                queryString += " AND d." + "categoriesWorkOrder10.id" + " = :" + parameterValue18;
            }
        }
        if (parameterValue19of1 != 0) {
            if (parameterValue19of2 != 0) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + "statusCodeWO.id" + " BETWEEN :" + parameterValue19of1 + " AND :" + parameterValue19of2;
                } else {
                    queryString += " AND d." + "statusCodeWO.id" + " BETWEEN :" + parameterValue19of1 + " AND :" + parameterValue19of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + "statusCodeWO.id" + " = :" + parameterValue19of1;
                } else {
                    queryString += " AND d." + "statusCodeWO.id" + " = :" + parameterValue19of1;
                }
            }
        } else if (parameterValue19of2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "statusCodeWO.id" + " = :" + parameterValue19of2;
            } else {
                queryString += " AND d." + "statusCodeWO.id" + " = :" + parameterValue19of2;
            }
        }
        if (!parameterValue20.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "numberParentWONumber" + " = :" + parameterValue20;
            } else {
                queryString += " AND d." + "numberParentWONumber" + " = :" + parameterValue20;
            }
        }
        if (parameterValue21 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "dateScheduledTickler" + " = :" + parameterValue21;
            } else {
                queryString += " AND d." + "dateScheduledTickler" + " = :" + parameterValue21;
            }
        }
        if (parameterValue22 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + "dateWOPlannedCompleted" + " = :" + parameterValue22;
            } else {
                queryString += " AND d." + "dateWOPlannedCompleted" + " = :" + parameterValue22;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue9 != 0) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        if (parameterValue12 != 0) {
            query.setParameter(parameterQuery12, parameterValue12);
        }
        if (parameterValue13 != 0) {
            query.setParameter(parameterQuery13, parameterValue13);
        }
        if (parameterValue14 != 0) {
            query.setParameter(parameterQuery14, parameterValue14);
        }
        if (parameterValue15 != 0) {
            query.setParameter(parameterQuery15, parameterValue15);
        }
        if (parameterValue16 != 0) {
            query.setParameter(parameterQuery16, parameterValue16);
        }
        if (parameterValue17 != 0) {
            query.setParameter(parameterQuery17, parameterValue17);
        }
        if (parameterValue18 != 0) {
            query.setParameter(parameterQuery18, parameterValue18);
        }
        if (parameterValue19of1 != 0) {
            query.setParameter(parameterQuery19of1, parameterValue19of1);
        }
        if (parameterValue19of2 != 0) {
            query.setParameter(parameterQuery19of2, parameterValue19of2);
        }
        if (!parameterValue20.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery20, parameterValue20);
        }
        if (parameterValue21 != null) {
            query.setParameter(parameterQuery21, parameterValue21);
        }
        if (parameterValue22 != null) {
            query.setParameter(parameterQuery22, parameterValue22);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTenStringAndThreeInt(String theClass, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                    String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery9, String parameterQuery10,
                                                    String parameterQuery11, String parameterQuery12, String parameterQuery13, String parameterValue1, String parameterValue2, String parameterValue3,
                                                    String parameterValue4, String parameterValue5, String parameterValue6, String parameterValue7, String parameterValue8, String parameterValue9, String parameterValue10,
                                                    Integer parameterValue11, Integer parameterValue12, Integer parameterValue13) {

        String queryString = "SELECT i FROM " + theClass + " i ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            queryString += " WHERE i.segment1 = :" + parameterQuery1;
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " i.segment2 = :" + parameterQuery2;
            } else {
                queryString += " AND i.segment2 = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment3 = :" + parameterQuery3;
            } else {
                queryString += " AND i.segment3 = :" + parameterQuery3;
            }
        }

        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment4 = :" + parameterQuery4;
            } else {
                queryString += " AND i.segment4 = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment5 = :" + parameterQuery5;
            } else {
                queryString += " AND i.segment5 = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment6 = :" + parameterQuery6;
            } else {
                queryString += " AND i.segment6 = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment7 = :" + parameterQuery7;
            } else {
                queryString += " AND i.segment7 = :" + parameterQuery7;
            }
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment8 = :" + parameterQuery7;
            } else {
                queryString += " AND i.segment8 = :" + parameterQuery7;
            }
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment9 = :" + parameterQuery9;
            } else {
                queryString += " AND i.segment9 = :" + parameterQuery9;
            }
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.segment10 = :" + parameterQuery10;
            } else {
                queryString += " AND i.segment10 = :" + parameterQuery10;
            }
        }
        if (parameterValue11 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.businessUnit.bUMNumber = :" + parameterQuery11;
            } else {
                queryString += " AND i.businessUnit.bUMNumber = :" + parameterQuery11;
            }
        }
        if (parameterValue12 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.template.tMNumber = :" + parameterQuery12;
            } else {
                queryString += " AND i.template.tMNumber = :" + parameterQuery12;
            }
        }
        if (parameterValue13 != 0) {
            if (queryString.endsWith("i ")) {
                queryString += " WHERE i.unitofmeasureUOMNumber.id = :" + parameterQuery13;
            } else {
                queryString += " AND i.unitofmeasureUOMNumber.id = :" + parameterQuery13;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue8.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (!parameterValue9.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (!parameterValue10.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery10, parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter(parameterQuery11, parameterValue11);
        }
        if (parameterValue12 != 0) {
            query.setParameter(parameterQuery12, parameterValue12);
        }
        if (parameterValue13 != 0) {
            query.setParameter(parameterQuery13, parameterValue13);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntsAndDoubleWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                               Integer parameterValue1, Integer parameterValue2, Double parameterValue3, Date parameterValue4) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndTwoDoubleAndCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                     String parameterValue1, Double parameterValue2, Double parameterValue3, Character parameterValue4) {

        //The general query
        String queryString = "SELECT d FROM " + theClass + " d ";

        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

//    public List<?> dynamicQueryThirtyEightIntsAndTwoBetweenIntsAndBetweenDoubleAndFourStringsAndBetweenStringAndFiveBetweenDates(Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4,
//            Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8,
//            Integer parameterValue9, Integer parameterValue10, Integer parameterValue11, Integer parameterValue12, Integer parameterValue13, Integer parameterValue14, Integer parameterValue15, Integer parameterValue16,
//            Integer parameterValue17, Integer parameterValue18, Integer parameterValue19, Integer parameterValue20, Integer parameterValue21, Integer parameterValue22, Integer parameterValue23, Integer parameterValue24,
//            Integer parameterValue25, Integer parameterValue26, Integer parameterValue27, Integer parameterValue28, Integer parameterValue29, Integer parameterValue30, Integer parameterValue31, Integer parameterValue32,
//            Integer parameterValue33, Integer parameterValue34, Integer parameterValue35, Integer parameterValue36, Integer parameterValue37, Integer parameterValue38,
//            Integer parameterValue39, Integer parameterValue39of1, Integer parameterValue40, Integer parameterValue40of1,
//            Double parameterValue41, Double parameterValue41of1,
//            String parameterValue42, String parameterValue43, String parameterValue44, String parameterValue45,
//            String parameterValue46, String parameterValue46of1,
//            Date parameterValue47, Date parameterValue47of1, Date parameterValue48, Date parameterValue48of1, Date parameterValue49, Date parameterValue49of1,
//            Date parameterValue50, Date parameterValue50of1, Date parameterValue51, Date parameterValue51of1) {
//        String queryString = "SELECT d FROM WorkOrderMasterFile d ";
//        if (parameterValue1 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.documentOrderNoInvoiceetc = :documentOrderNoInvoiceetc";
//            } else {
//                queryString += " AND d.documentOrderNoInvoiceetc = :documentOrderNoInvoiceetc";
//            }
//        }
//        if (parameterValue2 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.businessUnit.bUMNumber = :businessUnit";
//            } else {
//                queryString += " AND d.businessUnit.bUMNumber = :businessUnit";
//            }
//        }
//        if (parameterValue3 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.siteAddressNumber.aBWWlinenumber = :siteAddressNumber";
//            } else {
//                queryString += " AND d.siteAddressNumber.aBWWlinenumber = :siteAddressNumber";
//            }
//        }
//        if (parameterValue4 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.itemNumberShort.tMNumber = :itemNumberShort";
//            } else {
//                queryString += " AND d.itemNumberShort.tMNumber = :itemNumberShort";
//            }
//        }
//
//        if (parameterValue5 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.assetItemNumber.id = :assetItemNumber";
//            } else {
//                queryString += " AND d.assetItemNumber.id = :assetItemNumber";
//            }
//        }
//        if (parameterValue6 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.unitorTagNumber.id = :unitorTagNumber";
//            } else {
//                queryString += " AND d.unitorTagNumber.id = :unitorTagNumber";
//            }
//        }
//        if (parameterValue7 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.addressNumber.addressNumber = :addressNumber";
//            } else {
//                queryString += " AND d.addressNumber.addressNumber = :addressNumber";
//            }
//        }
//        if (parameterValue8 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.addressNumberManager.id = :addressNumberManager";
//            } else {
//                queryString += " AND d.addressNumberManager.id = :addressNumberManager";
//            }
//        }
//        if (parameterValue9 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.supervisor.id = :supervisor";
//            } else {
//                queryString += " AND d.supervisor.id = :supervisor";
//            }
//        }
//        if (parameterValue10 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.addressNumberOriginator.id = :addressNumberOriginator";
//            } else {
//                queryString += " AND d.addressNumberOriginator.id = :addressNumberOriginator";
//            }
//        }
//        if (parameterValue11 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.contactNumber.cTNumber = :contactNumber";
//            } else {
//                queryString += " AND d.contactNumber.cTNumber = :contactNumber";
//            }
//        }
//        if (parameterValue12 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder01.id = :categoriesWorkOrder01";
//            } else {
//                queryString += " AND d.categoriesWorkOrder01.id = :categoriesWorkOrder01";
//            }
//        }
//        if (parameterValue13 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder02.id = :categoriesWorkOrder02";
//            } else {
//                queryString += " AND d.categoriesWorkOrder02.id = :categoriesWorkOrder02";
//            }
//        }
//        if (parameterValue14 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder03.id = :categoriesWorkOrder03";
//            } else {
//                queryString += " AND d.categoriesWorkOrder03.id = :categoriesWorkOrder03";
//            }
//        }
//
//        if (parameterValue15 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder04.id = :categoriesWorkOrder04";
//            } else {
//                queryString += " AND d.categoriesWorkOrder04.id = :categoriesWorkOrder04";
//            }
//        }
//        if (parameterValue16 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder05.id = :categoriesWorkOrder05";
//            } else {
//                queryString += " AND d.categoriesWorkOrder05.id = :categoriesWorkOrder05";
//            }
//        }
//        if (parameterValue17 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.enterpriseOneConsultingStatus.id = :enterpriseOneConsultingStatus";
//            } else {
//                queryString += " AND d.enterpriseOneConsultingStatus.id = :enterpriseOneConsultingStatus";
//            }
//        }
//        if (parameterValue18 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.enterpriseOneConsultingServiceType.id = :enterpriseOneConsultingServiceType";
//            } else {
//                queryString += " AND d.enterpriseOneConsultingServiceType.id = :enterpriseOneConsultingServiceType";
//            }
//        }
//        if (parameterValue19 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.enterpriseOneConsultingSkillType.id = :enterpriseOneConsultingSkillType";
//            } else {
//                queryString += " AND d.enterpriseOneConsultingSkillType.id = :enterpriseOneConsultingSkillType";
//            }
//        }
//        if (parameterValue20 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.consultingExperienceLevel.id = :consultingExperienceLevel";
//            } else {
//                queryString += " AND d.consultingExperienceLevel.id = :consultingExperienceLevel";
//            }
//        }
//        if (parameterValue21 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder10.id = :categoriesWorkOrder10";
//            } else {
//                queryString += " AND d.categoriesWorkOrder10.id = :categoriesWorkOrder10";
//            }
//        }
//        if (parameterValue22 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder11.id = :categoriesWorkOrder11";
//            } else {
//                queryString += " AND d.categoriesWorkOrder11.id = :categoriesWorkOrder11";
//            }
//        }
//
//        if (parameterValue23 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder12.id = :categoriesWorkOrder12";
//            } else {
//                queryString += " AND d.categoriesWorkOrder12.id = :categoriesWorkOrder12";
//            }
//        }
//        if (parameterValue24 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder13.id = :categoriesWorkOrder13";
//            } else {
//                queryString += " AND d.categoriesWorkOrder13.id = :categoriesWorkOrder13";
//            }
//        }
//        if (parameterValue25 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder14.id = :categoriesWorkOrder14";
//            } else {
//                queryString += " AND d.categoriesWorkOrder14.id = :categoriesWorkOrder14";
//            }
//        }
//        if (parameterValue26 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder15.id = :categoriesWorkOrder15";
//            } else {
//                queryString += " AND d.categoriesWorkOrder15.id = :categoriesWorkOrder15";
//            }
//        }
//        if (parameterValue27 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder16.id = :categoriesWorkOrder16";
//            } else {
//                queryString += " AND d.categoriesWorkOrder16.id = :categoriesWorkOrder16";
//            }
//        }
//        if (parameterValue28 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder17.id = :categoriesWorkOrder17";
//            } else {
//                queryString += " AND d.categoriesWorkOrder17.id = :categoriesWorkOrder17";
//            }
//        }
//        if (parameterValue29 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder18.id = :categoriesWorkOrder18";
//            } else {
//                queryString += " AND d.categoriesWorkOrder18.id = :categoriesWorkOrder18";
//            }
//        }
//        if (parameterValue30 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder19.id = :categoriesWorkOrder19";
//            } else {
//                queryString += " AND d.categoriesWorkOrder19.id = :categoriesWorkOrder19";
//            }
//        }
//        if (parameterValue31 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.categoriesWorkOrder20.id = :categoriesWorkOrder20";
//            } else {
//                queryString += " AND d.categoriesWorkOrder20.id = :categoriesWorkOrder20";
//            }
//        }
//        if (parameterValue32 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.orderType.id = :orderType";
//            } else {
//                queryString += " AND d.orderType.id = :orderType";
//            }
//        }
//        if (parameterValue33 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.typeWO.id = :typeWO";
//            } else {
//                queryString += " AND d.typeWO.id = :typeWO";
//            }
//        }
//        if (parameterValue34 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.priorityWO.id = :priorityWO";
//            } else {
//                queryString += " AND d.priorityWO.id = :priorityWO";
//            }
//        }
//        if (parameterValue35 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.leadCraft.id = :leadCraft";
//            } else {
//                queryString += " AND d.leadCraft.id = :leadCraft";
//            }
//        }
//        if (parameterValue36 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.contractNumber = :contractNumber";
//            } else {
//                queryString += " AND d.contractNumber = :contractNumber";
//            }
//        }
//        if (parameterValue37 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.documentType.id = :documentType";
//            } else {
//                queryString += " AND d.documentType.id = :documentType";
//            }
//        }
//        if (parameterValue38 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.caseNumber = :caseNumber";
//            } else {
//                queryString += " AND d.caseNumber = :caseNumber";
//            }
//        }
//        if (parameterValue39 != 0) {
//            if (parameterValue39of1 != 0) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.estimatedDowntimeHours BETWEEN :estimatedDowntimeHoursFrom AND :estimatedDowntimeHoursThru";
//                } else {
//                    queryString += " AND d.estimatedDowntimeHours BETWEEN :estimatedDowntimeHoursFrom AND :estimatedDowntimeHoursThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.estimatedDowntimeHours >= :estimatedDowntimeHoursFrom";
//                } else {
//                    queryString += " AND d.estimatedDowntimeHours >= :estimatedDowntimeHoursFrom";
//                }
//            }
//        } else if (parameterValue39of1 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.estimatedDowntimeHours <= :estimatedDowntimeHoursThru";
//            } else {
//                queryString += " AND d.estimatedDowntimeHours <= :estimatedDowntimeHoursThru";
//            }
//        }
//        if (parameterValue40 != 0) {
//            if (parameterValue40of1 != 0) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.hoursEstimated BETWEEN :hoursEstimatedFrom AND :hoursEstimatedThru";
//                } else {
//                    queryString += " AND d.hoursEstimated BETWEEN :hoursEstimatedFrom AND :hoursEstimatedThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.hoursEstimated >= :hoursEstimatedFrom";
//                } else {
//                    queryString += " AND d.hoursEstimated >= :hoursEstimatedFrom";
//                }
//            }
//        } else if (parameterValue40of1 != 0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.hoursEstimated <= :hoursEstimatedThru";
//            } else {
//                queryString += " AND d.hoursEstimated <= :hoursEstimatedThru";
//            }
//        }
//        if (parameterValue41 != 0.0) {
//            if (parameterValue40of1 != 0.0) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.percentComplete BETWEEN :percentCompleteFrom AND :percentCompleteThru";
//                } else {
//                    queryString += " AND d.percentComplete BETWEEN :percentCompleteFrom AND :percentCompleteThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.percentComplete >= :percentCompleteFrom";
//                } else {
//                    queryString += " AND d.percentComplete >= :percentCompleteFrom";
//                }
//            }
//        } else if (parameterValue41of1 != 0.0) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.percentComplete <= :percentCompleteThru";
//            } else {
//                queryString += " AND d.percentComplete <= :percentCompleteThru";
//            }
//        }
//        if (!parameterValue42.equalsIgnoreCase("")) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.subsidiary = :subsidiary";
//            } else {
//                queryString += " AND d.subsidiary = :subsidiary";
//            }
//        }
//        if (!parameterValue43.equalsIgnoreCase("")) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.numberParentWONumber = :numberParentWONumber";
//            } else {
//                queryString += " AND d.numberParentWONumber = :numberParentWONumber";
//            }
//        }
//        if (!parameterValue44.equalsIgnoreCase("")) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.reference = :reference";
//            } else {
//                queryString += " AND d.reference = :reference";
//            }
//        }
//        if (!parameterValue45.equalsIgnoreCase("")) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.reference2 = :reference2";
//            } else {
//                queryString += " AND d.reference2 = :reference2";
//            }
//        }
//        if (!parameterValue46.equalsIgnoreCase("")) {
//            if (!parameterValue46of1.equalsIgnoreCase("")) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.statusCodeWO.code BETWEEN :statusCodeWOFrom AND :statusCodeWOThru";
//                } else {
//                    queryString += " AND d.statusCodeWO.code BETWEEN :statusCodeWOFrom AND :statusCodeWOThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.statusCodeWO.code >= :statusCodeWOFrom";
//                } else {
//                    queryString += " AND d.statusCodeWO.code >= :statusCodeWOFrom";
//                }
//            }
//        } else if (!parameterValue46of1.equalsIgnoreCase("")) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.statusCodeWO.code <= :statusCodeWOThru";
//            } else {
//                queryString += " AND d.statusCodeWO.code <= :statusCodeWOThru";
//            }
//        }
//        if (parameterValue47 != null) {
//            if (parameterValue47of1 != null) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateOrderTransaction BETWEEN :dateOrderTransactionFrom AND :dateOrderTransactionThru";
//                } else {
//                    queryString += " AND d.dateOrderTransaction BETWEEN :dateOrderTransactionFrom AND :dateOrderTransactionThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateOrderTransaction >= :dateOrderTransactionFrom";
//                } else {
//                    queryString += " AND d.dateOrderTransaction >= :dateOrderTransactionFrom";
//                }
//            }
//        } else if (parameterValue47of1 != null) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.dateOrderTransaction <= :dateOrderTransactionThru";
//            } else {
//                queryString += " AND d.dateOrderTransaction <= :dateOrderTransactionThru";
//            }
//        }
//        if (parameterValue48 != null) {
//            if (parameterValue48of1 != null) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateStartJulian BETWEEN :dateStartJulianFrom AND :dateStartJulianThru";
//                } else {
//                    queryString += " AND d.dateStartJulian BETWEEN :dateStartJulianFrom AND :dateStartJulianThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateStartJulian >= :dateStartJulianFrom";
//                } else {
//                    queryString += " AND d.dateStartJulian >= :dateStartJulianFrom";
//                }
//            }
//        } else if (parameterValue48of1 != null) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.dateStartJulian <= :dateStartJulianThru";
//            } else {
//                queryString += " AND d.dateStartJulian <= :dateStartJulianThru";
//            }
//        }
//        if (parameterValue49 != null) {
//            if (parameterValue49of1 != null) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateWOPlannedCompleted BETWEEN :dateWOPlannedCompletedFrom AND :dateWOPlannedCompletedThru";
//                } else {
//                    queryString += " AND d.dateWOPlannedCompleted BETWEEN :dateWOPlannedCompletedFrom AND :dateWOPlannedCompletedThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateWOPlannedCompleted >= :dateWOPlannedCompletedFrom";
//                } else {
//                    queryString += " AND d.dateWOPlannedCompleted >= :dateWOPlannedCompletedFrom";
//                }
//            }
//        } else if (parameterValue49of1 != null) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.dateWOPlannedCompleted <= :dateWOPlannedCompletedThru";
//            } else {
//                queryString += " AND d.dateWOPlannedCompleted <= :dateWOPlannedCompletedThru";
//            }
//        }
//        if (parameterValue50 != null) {
//            if (parameterValue50of1 != null) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateRequested BETWEEN :dateRequestedFrom AND :dateRequestedThru";
//                } else {
//                    queryString += " AND d.dateRequested BETWEEN :dateRequestedFrom AND :dateRequestedThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateRequested >= :dateRequestedFrom";
//                } else {
//                    queryString += " AND d.dateRequested >= :dateRequestedFrom";
//                }
//            }
//        } else if (parameterValue50of1 != null) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.dateRequested <= :dateRequestedThru";
//            } else {
//                queryString += " AND d.dateRequested <= :dateRequestedThru";
//            }
//        }
//        if (parameterValue51 != null) {
//            if (parameterValue51of1 != null) {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateCompletionJulian BETWEEN :dateCompletionJulianFrom AND :dateCompletionJulianThru";
//                } else {
//                    queryString += " AND d.dateCompletionJulian BETWEEN :dateCompletionJulianFrom AND :dateCompletionJulianThru";
//                }
//            } else {
//                if (queryString.endsWith("d ")) {
//                    queryString += " WHERE d.dateCompletionJulian >= :dateCompletionJulianFrom";
//                } else {
//                    queryString += " AND d.dateCompletionJulian >= :dateCompletionJulianFrom";
//                }
//            }
//        } else if (parameterValue51of1 != null) {
//            if (queryString.endsWith("d ")) {
//                queryString += " WHERE d.dateCompletionJulian <= :dateCompletionJulianThru";
//            } else {
//                queryString += " AND d.dateCompletionJulian <= :dateCompletionJulianThru";
//            }
//        }
//
//        Query query = em.createQuery(queryString);
//
//        if (parameterValue1 != 0) {
//            query.setParameter("documentOrderNoInvoiceetc", parameterValue1);
//        }
//        if (parameterValue2 != 0) {
//            query.setParameter("businessUnit", parameterValue2);
//        }
//        if (parameterValue3 != 0) {
//            query.setParameter("siteAddressNumber", parameterValue3);
//        }
//        if (parameterValue4 != 0) {
//            query.setParameter("itemNumberShort", parameterValue4);
//        }
//        if (parameterValue5 != 0) {
//            query.setParameter("assetItemNumber", parameterValue5);
//        }
//        if (parameterValue6 != 0) {
//            query.setParameter("unitorTagNumber", parameterValue6);
//        }
//        if (parameterValue7 != 0) {
//            query.setParameter("addressNumber", parameterValue7);
//        }
//        if (parameterValue8 != 0) {
//            query.setParameter("addressNumberManager", parameterValue8);
//        }
//        if (parameterValue9 != 0) {
//            query.setParameter("supervisor", parameterValue9);
//        }
//        if (parameterValue10 != 0) {
//            query.setParameter("addressNumberOriginator", parameterValue10);
//        }
//        if (parameterValue11 != 0) {
//            query.setParameter("contactNumber", parameterValue11);
//        }
//        if (parameterValue12 != 0) {
//            query.setParameter("categoriesWorkOrder01", parameterValue12);
//        }
//        if (parameterValue13 != 0) {
//            query.setParameter("categoriesWorkOrder02", parameterValue13);
//        }
//        if (parameterValue14 != 0) {
//            query.setParameter("categoriesWorkOrder03", parameterValue14);
//        }
//        if (parameterValue15 != 0) {
//            query.setParameter("categoriesWorkOrder04", parameterValue15);
//        }
//        if (parameterValue16 != 0) {
//            query.setParameter("categoriesWorkOrder05", parameterValue16);
//        }
//        if (parameterValue17 != 0) {
//            query.setParameter("enterpriseOneConsultingStatus", parameterValue17);
//        }
//        if (parameterValue18 != 0) {
//            query.setParameter("enterpriseOneConsultingServiceType", parameterValue18);
//        }
//        if (parameterValue19 != 0) {
//            query.setParameter("enterpriseOneConsultingSkillType", parameterValue19);
//        }
//        if (parameterValue20 != 0) {
//            query.setParameter("consultingExperienceLevel", parameterValue20);
//        }
//        if (parameterValue21 != 0) {
//            query.setParameter("categoriesWorkOrder10", parameterValue11);
//        }
//        if (parameterValue22 != 0) {
//            query.setParameter("categoriesWorkOrder11", parameterValue22);
//        }
//        if (parameterValue23 != 0) {
//            query.setParameter("categoriesWorkOrder12", parameterValue23);
//        }
//        if (parameterValue24 != 0) {
//            query.setParameter("categoriesWorkOrder13", parameterValue24);
//        }
//        if (parameterValue25 != 0) {
//            query.setParameter("categoriesWorkOrder14", parameterValue25);
//        }
//        if (parameterValue26 != 0) {
//            query.setParameter("categoriesWorkOrder15", parameterValue26);
//        }
//        if (parameterValue27 != 0) {
//            query.setParameter("categoriesWorkOrder16", parameterValue27);
//        }
//        if (parameterValue28 != 0) {
//            query.setParameter("categoriesWorkOrder17", parameterValue28);
//        }
//        if (parameterValue29 != 0) {
//            query.setParameter("categoriesWorkOrder18", parameterValue29);
//        }
//        if (parameterValue30 != 0) {
//            query.setParameter("categoriesWorkOrder19", parameterValue30);
//        }
//        if (parameterValue31 != 0) {
//            query.setParameter("categoriesWorkOrder20", parameterValue31);
//        }
//        if (parameterValue32 != 0) {
//            query.setParameter("orderType", parameterValue32);
//        }
//        if (parameterValue33 != 0) {
//            query.setParameter("typeWO", parameterValue33);
//        }
//        if (parameterValue34 != 0) {
//            query.setParameter("priorityWO", parameterValue34);
//        }
//        if (parameterValue35 != 0) {
//            query.setParameter("leadCraft", parameterValue35);
//        }
//        if (parameterValue36 != 0) {
//            query.setParameter("contractNumber", parameterValue36);
//        }
//        if (parameterValue37 != 0) {
//            query.setParameter("documentType", parameterValue37);
//        }
//        if (parameterValue38 != 0) {
//            query.setParameter("caseNumber", parameterValue38);
//        }
//        if (parameterValue39 != 0) {
//            query.setParameter("estimatedDowntimeHoursFrom", parameterValue39);
//        }
//        if (parameterValue39of1 != 0) {
//            query.setParameter("estimatedDowntimeHoursThru", parameterValue39of1);
//        }
//        if (parameterValue40 != 0) {
//            query.setParameter("hoursEstimatedFrom", parameterValue40);
//        }
//        if (parameterValue40of1 != 0) {
//            query.setParameter("hoursEstimatedThru", parameterValue40of1);
//        }
//        if (parameterValue41 != 0.0) {
//            query.setParameter("percentCompleteFrom", parameterValue41);
//        }
//        if (parameterValue41of1 != 0.0) {
//            query.setParameter("percentCompleteThru", parameterValue41of1);
//        }
//        if (!parameterValue42.equalsIgnoreCase("")) {
//            query.setParameter("subsidiary", parameterValue42);
//        }
//        if (!parameterValue43.equalsIgnoreCase("")) {
//            query.setParameter("numberParentWONumber", parameterValue43);
//        }
//        if (!parameterValue44.equalsIgnoreCase("")) {
//            query.setParameter("reference", parameterValue44);
//        }
//        if (!parameterValue45.equalsIgnoreCase("")) {
//            query.setParameter("reference2", parameterValue45);
//        }
//        if (!parameterValue46.equalsIgnoreCase("")) {
//            query.setParameter("statusCodeWOFrom", parameterValue46);
//        }
//        if (!parameterValue46of1.equalsIgnoreCase("")) {
//            query.setParameter("statusCodeWOThru", parameterValue46of1);
//        }
//        if (parameterValue47 != null) {
//            query.setParameter("dateOrderTransactionFrom", parameterValue47);
//        }
//        if (parameterValue47of1 != null) {
//            query.setParameter("dateOrderTransactionThru", parameterValue47of1);
//        }
//        if (parameterValue48 != null) {
//            query.setParameter("dateStartJulianFrom", parameterValue48);
//        }
//        if (parameterValue48of1 != null) {
//            query.setParameter("dateStartJulianThru", parameterValue48of1);
//        }
//        if (parameterValue49 != null) {
//            query.setParameter("dateWOPlannedCompletedFrom", parameterValue49);
//        }
//        if (parameterValue49of1 != null) {
//            query.setParameter("dateWOPlannedCompletedThru", parameterValue49of1);
//        }
//        if (parameterValue50 != null) {
//            query.setParameter("dateRequestedFrom", parameterValue50);
//        }
//        if (parameterValue50of1 != null) {
//            query.setParameter("dateRequestedThru", parameterValue50of1);
//        }
//        if (parameterValue51 != null) {
//            query.setParameter("dateCompletionJulianFrom", parameterValue51);
//        }
//        if (parameterValue51of1 != null) {
//            query.setParameter("dateCompletionJulianThru", parameterValue51of1);
//        }
//        return query.getResultList();
//    }

    public List<?> dynamicQueryFourIntsAndStringAndBetweenStringAndSkipToStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery6of1, String parameterQuery7,
                                                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, String parameterValue6, String parameterValue6of1, String parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (!parameterValue6of1.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                } else {
                    queryString += " AND d." + entityQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery6 + " <= :" + parameterQuery6;
                } else {
                    queryString += " AND d." + entityQuery6 + " <= :" + parameterQuery6;
                }
            }
        } else if (!parameterValue6of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " >= :" + parameterQuery6of1;
            } else {
                queryString += " AND d." + entityQuery6 + " >= :" + parameterQuery6of1;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " >= :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " >= :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue6of1.equalsIgnoreCase("")) {
            query.setParameter(parameterValue6of1, parameterValue6of1);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntsAndSkipToIntGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " >= :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " >= :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryBetweenDateGeneral(String theClass, String entityQuery1, String parameterQuery1, String parameterQuery1of2,
                                                  Date parameterValue1, Date parameterValue1of2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != null) {
            if (parameterValue1of2 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery1 + " BETWEEN :" + parameterQuery1 + " AND :" + parameterQuery1of2;
                } else {
                    queryString += " AND d." + entityQuery1 + " BETWEEN :" + parameterQuery1 + " AND :" + parameterQuery1of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
                } else {
                    queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
                }
            }
        } else if (parameterValue1of2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1of2;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1of2;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue1of2 != null) {
            query.setParameter(parameterQuery1of2, parameterValue1of2);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFindAllGeneral(String theClass) {
        String queryString = "SELECT d FROM " + theClass + " d";
        Query query = em.createQuery(queryString);
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndThruDateGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                     String parameterQuery1, String parameterQuery2, Integer parameterValue1, Date parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " BETWEEN :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " BETWEEN :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQuerySixIntsAndDoubleGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                       String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                       Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Double parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";

        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();

    }

    public List<?> dynamicQuerySevenInts(String queryString, String parameter1, String parameter2, String parameter3, String parameter4, String parameter5, String parameter6, String parameter7, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != null) {
            query.setParameter(parameter1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameter2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameter3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameter4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameter5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameter6, parameterValue7);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameter7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndStringAndBetweenStringAndTwoBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8, String entityQuery9,
                                                                                        String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                                        String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7of2, String parameterQuery8, String parameterQuery8of2, String parameterQuery9, String parameterQuery9of2,
                                                                                        Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6, String parameterValue7, String parameterValue7of2, Date parameterValue8, Date parameterValue8of2, Date parameterValue9, Date parameterValue9of2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND  d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND  d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND  d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND  d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND  d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (!parameterValue7of2.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND  :" + parameterQuery7of2;
                } else {
                    queryString += " AND d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND  :" + parameterQuery7of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " >= :" + parameterQuery7;
                } else {
                    queryString += " AND  d." + entityQuery7 + " >= :" + parameterQuery7;
                }
            }
        } else if (!parameterValue7of2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " <= :" + parameterQuery7of2;
            } else {
                queryString += " AND  d." + entityQuery7 + " <= :" + parameterQuery7of2;
            }
        }
        if (parameterValue8 != null) {
            if (parameterValue8of2 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND  :" + parameterQuery8of2;
                } else {
                    queryString += " AND d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND  :" + parameterQuery8of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " >= :" + parameterQuery8;
                } else {
                    queryString += " AND  d." + entityQuery8 + " >= :" + parameterQuery8;
                }
            }
        } else if (parameterValue8of2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " <= :" + parameterQuery8of2;
            } else {
                queryString += " AND  d." + entityQuery8 + " <= :" + parameterQuery8of2;
            }
        }
        if (parameterValue9 != null) {
            if (parameterValue9of2 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery9 + " BETWEEN :" + parameterQuery9 + " AND  :" + parameterQuery9of2;
                } else {
                    queryString += " AND d." + entityQuery9 + " BETWEEN :" + parameterQuery9 + " AND  :" + parameterQuery9of2;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery9 + " >= :" + parameterQuery9;
                } else {
                    queryString += " AND  d." + entityQuery9 + " >= :" + parameterQuery9;
                }
            }
        } else if (parameterValue9of2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery9 + " <= :" + parameterQuery9of2;
            } else {
                queryString += " AND  d." + entityQuery9 + " <= :" + parameterQuery9of2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue7of2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7of2, parameterValue7of2);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue8of2 != null) {
            query.setParameter(parameterQuery8of2, parameterValue8of2);
        }
        if (parameterValue9 != null) {
            query.setParameter(parameterQuery9, parameterValue9);
        }
        if (parameterValue9of2 != null) {
            query.setParameter(parameterQuery9of2, parameterValue9of2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntAndDoubleAndTwoDateGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Double parameterValue4, Date parameterValue5, Date parameterValue6) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0.0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndStringAndBetweenStringAndBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6, String entityParameterQuery7,
                                                                                     String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery6of1, String parameterQuery7, String parameterQuery7of1,
                                                                                     Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, String parameterValue6, String parameterValue6of1, Date parameterValue7, Date parameterValue7of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }

        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (!parameterValue6of1.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                } else {
                    queryString += " AND d." + entityParameterQuery6 + " BETWEEN :" + parameterQuery6 + " AND :" + parameterQuery6of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery6 + " >= :" + parameterQuery6;
                } else {
                    queryString += " AND d." + entityParameterQuery6 + " >= :" + parameterQuery6;
                }
            }
        } else if (!parameterValue6of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " <= :" + parameterQuery6of1;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " <= :" + parameterQuery6of1;
            }
        }
        if (parameterValue7 != null) {
            if (parameterValue7of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                } else {
                    queryString += " AND d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery7 + " >= :" + parameterQuery7;
                } else {
                    queryString += " AND d." + entityParameterQuery7 + " >= :" + parameterQuery7;
                }
            }
        } else if (parameterValue7of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery7 + " <= :" + parameterQuery7of1;
            } else {
                queryString += " AND d." + entityParameterQuery7 + " <= :" + parameterQuery7of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue6of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6of1, parameterValue6of1);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue7of1 != null) {
            query.setParameter(parameterQuery7of1, parameterValue7of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryFourIntsAndDoubleAndAsOfDateSpecific(String queryString,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                                    Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Double parameterValue5, Date parameterValue6) {
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0.0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryEighteenIntsAndBetweenIntAndStringAndTwoDate(String queryString,
                                                                            Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8, Integer parameterValue9,
                                                                            Integer parameterValue10, Integer parameterValue11, Integer parameterValue12, Integer parameterValue13, Integer parameterValue14, Integer parameterValue15, Integer parameterValue16, Integer parameterValue17, Integer parameterValue18,
                                                                            Integer parameterValue19of1, Integer parameterValue19of2, String parameterValue20, Date parameterValue21, Date parameterValue22) {

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter("itemNumberShort", parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter("orderType", parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter("businessUnit", parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter("addressNumberOriginator", parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter("reasonforECO", parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter("priorityWO", parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter("typeWO", parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter("phaseIn", parameterValue8);
        }
        if (parameterValue9 != 0) {
            query.setParameter("categoriesWorkOrder01", parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter("categoriesWorkOrder02", parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter("categoriesWorkOrder03", parameterValue11);
        }
        if (parameterValue12 != 0) {
            query.setParameter("categoriesWorkOrder04", parameterValue12);
        }
        if (parameterValue13 != 0) {
            query.setParameter("categoriesWorkOrder05", parameterValue13);
        }
        if (parameterValue14 != 0) {
            query.setParameter("consultingExperienceLevel", parameterValue14);
        }
        if (parameterValue15 != 0) {
            query.setParameter("enterpriseOneConsultingServiceType", parameterValue15);
        }
        if (parameterValue16 != 0) {
            query.setParameter("enterpriseOneConsultingSkillType", parameterValue16);
        }
        if (parameterValue17 != 0) {
            query.setParameter("enterpriseOneConsultingStatus", parameterValue17);
        }
        if (parameterValue18 != 0) {
            query.setParameter("categoriesWorkOrder10", parameterValue18);
        }
        if (parameterValue19of1 != 0) {
            query.setParameter("statusCodeWOFrom", parameterValue19of1);
        }
        if (parameterValue19of2 != 0) {
            query.setParameter("statusCodeWOThru", parameterValue19of2);
        }
        if (!parameterValue20.equalsIgnoreCase("")) {
            query.setParameter("numberParentWONumber", parameterValue20);
        }
        if (parameterValue21 != null) {
            query.setParameter("dateScheduledTickler", parameterValue21);
        }
        if (parameterValue22 != null) {
            query.setParameter("dateWOPlannedCompleted", parameterValue22);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndTwoDateAndBetweenStringGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6, String entityParameterQuery7,
                                                                        String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7of1,
                                                                        Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Date parameterValue5, Date parameterValue6, String parameterValue7, String parameterValue7of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " = :" + parameterQuery6;
            }
        }

        if (!parameterValue7.equalsIgnoreCase("")) {
            if (!parameterValue7of1.equalsIgnoreCase("")) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                } else {
                    queryString += " AND d." + entityParameterQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery7 + " >= :" + parameterQuery7;
                } else {
                    queryString += " AND d." + entityParameterQuery7 + " >= :" + parameterQuery7;
                }
            }
        } else if (!parameterValue7of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery7 + " <= :" + parameterQuery7of1;
            } else {
                queryString += " AND d." + entityParameterQuery7 + " <= :" + parameterQuery7of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (!parameterValue7of1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7of1, parameterValue7of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryTwoIntAndTwoDateGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                 Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQuerySixIntAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery7of1,
                                                           Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Date parameterValue7, Date parameterValue7of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != null) {
            if (parameterValue7of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                } else {
                    queryString += " AND d." + entityQuery7 + " BETWEEN :" + parameterQuery7 + " AND :" + parameterQuery7of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery7 + " >= :" + parameterQuery7;
                } else {
                    queryString += " AND d." + entityQuery7 + " >= :" + parameterQuery7;
                }
            }
        } else if (parameterValue7of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " <= :" + parameterQuery7of1;
            } else {
                queryString += " AND d." + entityQuery7 + " <= :" + parameterQuery7of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue7of1 != null) {
            query.setParameter(parameterQuery7of1, parameterValue7of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQuerySevenIntAndBetweenDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, String parameterQuery8of1,
                                                             Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Date parameterValue8, Date parameterValue8of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (parameterValue7 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (parameterValue8of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND :" + parameterQuery8of1;
                } else {
                    queryString += " AND d." + entityQuery8 + " BETWEEN :" + parameterQuery8 + " AND :" + parameterQuery8of1;
                }
            } else {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityQuery8 + " >= :" + parameterQuery8;
                } else {
                    queryString += " AND d." + entityQuery8 + " >= :" + parameterQuery8;
                }
            }
        } else if (parameterValue8of1 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " <= :" + parameterQuery8of1;
            } else {
                queryString += " AND d." + entityQuery8 + " <= :" + parameterQuery8of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }
        if (parameterValue8of1 != null) {
            query.setParameter(parameterQuery8of1, parameterValue8of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThirtyEightIntsAndThreeBetweenDoubleAndFourStringsAndBetweenStringAndFiveBetweenDates(String queryString, Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4,
                                                                                                                     Integer parameterValue5, Integer parameterValue6, Integer parameterValue7, Integer parameterValue8,
                                                                                                                     Integer parameterValue9, Integer parameterValue10, Integer parameterValue11, Integer parameterValue12, Integer parameterValue13, Integer parameterValue14, Integer parameterValue15, Integer parameterValue16,
                                                                                                                     Integer parameterValue17, Integer parameterValue18, Integer parameterValue19, Integer parameterValue20, Integer parameterValue21, Integer parameterValue22, Integer parameterValue23, Integer parameterValue24,
                                                                                                                     Integer parameterValue25, Integer parameterValue26, Integer parameterValue27, Integer parameterValue28, Integer parameterValue29, Integer parameterValue30, Integer parameterValue31, Integer parameterValue32,
                                                                                                                     Integer parameterValue33, Integer parameterValue34, Integer parameterValue35, Integer parameterValue36, Integer parameterValue37, Integer parameterValue38,
                                                                                                                     Double parameterValue39, Double parameterValue39of1, Double parameterValue40, Double parameterValue40of1, Double parameterValue41, Double parameterValue41of1,
                                                                                                                     String parameterValue42, String parameterValue43, String parameterValue44, String parameterValue45,
                                                                                                                     String parameterValue46, String parameterValue46of1,
                                                                                                                     Date parameterValue47, Date parameterValue47of1, Date parameterValue48, Date parameterValue48of1, Date parameterValue49, Date parameterValue49of1,
                                                                                                                     Date parameterValue50, Date parameterValue50of1, Date parameterValue51, Date parameterValue51of1) {
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter("documentOrderNoInvoiceetc", parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter("businessUnit", parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter("siteAddressNumber", parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter("itemNumberShort", parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter("assetItemNumber", parameterValue5);
        }
        if (parameterValue6 != 0) {
            query.setParameter("unitorTagNumber", parameterValue6);
        }
        if (parameterValue7 != 0) {
            query.setParameter("addressNumber", parameterValue7);
        }
        if (parameterValue8 != 0) {
            query.setParameter("addressNumberManager", parameterValue8);
        }
        if (parameterValue9 != 0) {
            query.setParameter("supervisor", parameterValue9);
        }
        if (parameterValue10 != 0) {
            query.setParameter("addressNumberOriginator", parameterValue10);
        }
        if (parameterValue11 != 0) {
            query.setParameter("contactNumber", parameterValue11);
        }
        if (parameterValue12 != 0) {
            query.setParameter("categoriesWorkOrder01", parameterValue12);
        }
        if (parameterValue13 != 0) {
            query.setParameter("categoriesWorkOrder02", parameterValue13);
        }
        if (parameterValue14 != 0) {
            query.setParameter("categoriesWorkOrder03", parameterValue14);
        }
        if (parameterValue15 != 0) {
            query.setParameter("categoriesWorkOrder04", parameterValue15);
        }
        if (parameterValue16 != 0) {
            query.setParameter("categoriesWorkOrder05", parameterValue16);
        }
        if (parameterValue17 != 0) {
            query.setParameter("enterpriseOneConsultingStatus", parameterValue17);
        }
        if (parameterValue18 != 0) {
            query.setParameter("enterpriseOneConsultingServiceType", parameterValue18);
        }
        if (parameterValue19 != 0) {
            query.setParameter("enterpriseOneConsultingSkillType", parameterValue19);
        }
        if (parameterValue20 != 0) {
            query.setParameter("consultingExperienceLevel", parameterValue20);
        }
        if (parameterValue21 != 0) {
            query.setParameter("categoriesWorkOrder10", parameterValue11);
        }
        if (parameterValue22 != 0) {
            query.setParameter("categoriesWorkOrder11", parameterValue22);
        }
        if (parameterValue23 != 0) {
            query.setParameter("categoriesWorkOrder12", parameterValue23);
        }
        if (parameterValue24 != 0) {
            query.setParameter("categoriesWorkOrder13", parameterValue24);
        }
        if (parameterValue25 != 0) {
            query.setParameter("categoriesWorkOrder14", parameterValue25);
        }
        if (parameterValue26 != 0) {
            query.setParameter("categoriesWorkOrder15", parameterValue26);
        }
        if (parameterValue27 != 0) {
            query.setParameter("categoriesWorkOrder16", parameterValue27);
        }
        if (parameterValue28 != 0) {
            query.setParameter("categoriesWorkOrder17", parameterValue28);
        }
        if (parameterValue29 != 0) {
            query.setParameter("categoriesWorkOrder18", parameterValue29);
        }
        if (parameterValue30 != 0) {
            query.setParameter("categoriesWorkOrder19", parameterValue30);
        }
        if (parameterValue31 != 0) {
            query.setParameter("categoriesWorkOrder20", parameterValue31);
        }
        if (parameterValue32 != 0) {
            query.setParameter("orderType", parameterValue32);
        }
        if (parameterValue33 != 0) {
            query.setParameter("typeWO", parameterValue33);
        }
        if (parameterValue34 != 0) {
            query.setParameter("priorityWO", parameterValue34);
        }
        if (parameterValue35 != 0) {
            query.setParameter("leadCraft", parameterValue35);
        }
        if (parameterValue36 != 0) {
            query.setParameter("contractNumber", parameterValue36);
        }
        if (parameterValue37 != 0) {
            query.setParameter("documentType", parameterValue37);
        }
        if (parameterValue38 != 0) {
            query.setParameter("caseNumber", parameterValue38);
        }
        if (parameterValue39 != 0.0) {
            query.setParameter("estimatedDowntimeHoursFrom", parameterValue39);
        }
        if (parameterValue39of1 != 0.0) {
            query.setParameter("estimatedDowntimeHoursThru", parameterValue39of1);
        }
        if (parameterValue40 != 0.0) {
            query.setParameter("hoursEstimatedFrom", parameterValue40);
        }
        if (parameterValue40of1 != 0.0) {
            query.setParameter("hoursEstimatedThru", parameterValue40of1);
        }
        if (parameterValue41 != 0.0) {
            query.setParameter("percentCompleteFrom", parameterValue41);
        }
        if (parameterValue41of1 != 0.0) {
            query.setParameter("percentCompleteThru", parameterValue41of1);
        }
        if (!parameterValue42.equalsIgnoreCase("")) {
            query.setParameter("subsidiary", parameterValue42);
        }
        if (!parameterValue43.equalsIgnoreCase("")) {
            query.setParameter("numberParentWONumber", parameterValue43);
        }
        if (!parameterValue44.equalsIgnoreCase("")) {
            query.setParameter("reference", parameterValue44);
        }
        if (!parameterValue45.equalsIgnoreCase("")) {
            query.setParameter("reference2", parameterValue45);
        }
        if (!parameterValue46.equalsIgnoreCase("")) {
            query.setParameter("statusCodeWOFrom", parameterValue46);
        }
        if (!parameterValue46of1.equalsIgnoreCase("")) {
            query.setParameter("statusCodeWOThru", parameterValue46of1);
        }
        if (parameterValue47 != null) {
            query.setParameter("dateOrderTransactionFrom", parameterValue47);
        }
        if (parameterValue47of1 != null) {
            query.setParameter("dateOrderTransactionThru", parameterValue47of1);
        }
        if (parameterValue48 != null) {
            query.setParameter("dateStartJulianFrom", parameterValue48);
        }
        if (parameterValue48of1 != null) {
            query.setParameter("dateStartJulianThru", parameterValue48of1);
        }
        if (parameterValue49 != null) {
            query.setParameter("dateWOPlannedCompletedFrom", parameterValue49);
        }
        if (parameterValue49of1 != null) {
            query.setParameter("dateWOPlannedCompletedThru", parameterValue49of1);
        }
        if (parameterValue50 != null) {
            query.setParameter("dateRequestedFrom", parameterValue50);
        }
        if (parameterValue50of1 != null) {
            query.setParameter("dateRequestedThru", parameterValue50of1);
        }
        if (parameterValue51 != null) {
            query.setParameter("dateCompletionJulianFrom", parameterValue51);
        }
        if (parameterValue51of1 != null) {
            query.setParameter("dateCompletionJulianThru", parameterValue51of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryThreeIntAndThreeStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                             String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                             Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndFourStringGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4, String entityParameterQuery5, String entityParameterQuery6,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                          Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityParameterQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityParameterQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndThreeStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                        String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryStringAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2,
                                                    String parameterQuery1, String parameterQuery2,
                                                    String parameterValue1, Date parameterValue2) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryTwoIntAndTwoCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                            String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, Integer parameterValue2, Character parameterValue3, Character parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndBetweenFourStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                                 String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                                 Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("") && !parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " BETWEEN :" + parameterQuery3 + "AND :" + parameterValue4;
            } else {
                queryString += " AND d." + entityQuery3 + " BETWEEN :" + parameterQuery3 + "AND :" + parameterValue4;
            }
        } else if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        } else if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterValue4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterValue4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("") && !parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + "AND :" + parameterValue6;
            } else {
                queryString += " AND d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + "AND :" + parameterValue6;
            }
        } else if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        } else if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterValue6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterValue6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryFourStringAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                        String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                        String parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, Date parameterValue5) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryThreeStringAndDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                         String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                         String parameterValue1, String parameterValue2, String parameterValue3, Date parameterValue4) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityParameterQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryThreeIntAndForStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                           String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                           Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6, String parameterValue7) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndFiveStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                          Integer parameterValue1, Integer parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6, String parameterValue7) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndBetweenStringGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                          String parameterQuery1, String parameterQuery2, String parameterQuery2of1, Integer parameterValue1, String parameterValue2, String parameterValue2of1) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("") && !parameterValue2of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND " + parameterQuery2of1;
            } else {
                queryString += " AND d." + entityQuery2 + " BETWEEN :" + parameterQuery2 + " AND " + parameterQuery2of1;
            }
        } else if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        } else if (!parameterValue2of1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2of1;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2of1;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue2of1 != null) {
            query.setParameter(parameterQuery2of1, parameterValue2of1);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndThreeStringWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                                String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, Integer parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, Date parameterValue5) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (parameterValue5 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndSixStringWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6, String entityQuery7, String entityQuery8,
                                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7, String parameterQuery8, Integer parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6, String parameterValue7, Date parameterValue8) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery7 + " = :" + parameterQuery7;
            } else {
                queryString += " AND d." + entityQuery7 + " = :" + parameterQuery7;
            }
        }
        if (parameterValue8 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery8 + " = :" + parameterQuery8;
            } else {
                queryString += " AND d." + entityQuery8 + " = :" + parameterQuery8;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (!parameterValue7.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        if (parameterValue8 != null) {
            query.setParameter(parameterQuery8, parameterValue8);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTwoStringWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, Integer parameterValue1, String parameterValue2, String parameterValue3, Date parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }

        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryTwoStringsWithDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String parameterQuery1,
                                                         String parameterQuery2, String parameterQuery3, String parameterValue1, String parameterValue2, Date parameterValue3) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoStringsAndTwoDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String parameterQuery1,
                                                           String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterValue1, String parameterValue2, Date parameterValue3, Date parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTwoStringAndBetweenDateGeneral(String theClass, String entityParameterQuery1, String entityParameterQuery2, String entityParameterQuery3, String entityParameterQuery4,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery4of1,
                                                                    Integer parameterValue1, String parameterValue2, String parameterValue3, Date parameterValue4, Date parameterValue4of1) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityParameterQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityParameterQuery2 + " = :" + parameterQuery2;
            }
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityParameterQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityParameterQuery3 + " = :" + parameterQuery3;
            }
        }

        if (parameterValue4 != null) {
            if (parameterValue4of1 != null) {
                if (queryString.endsWith("d ")) {
                    queryString += " WHERE d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                } else {
                    queryString += " AND d." + entityParameterQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
                }
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue4of1 != null) {
            query.setParameter(parameterQuery4of1, parameterValue4of1);
        }

        return query.getResultList();

    }

    public List<?> dynamicQueryFiveIntAndForStringGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                          String parameterQuery1, String parameterQuery1Of1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery5Of1,
                                                          String parameterQuery5Of2, String parameterQuery5Of3, String parameterQuery5Of4, String parameterQuery5Of5, String parameterQuery5Of6, String parameterQuery5Of7,
                                                          Integer parameterValue1, Integer parameterValue1Of1, Character parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue5Of1, String parameterValue5Of2, String parameterValue5Of3, String parameterValue5Of4, String parameterValue5Of5, String parameterValue5Of6, String parameterValue5Of7) {

        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("SELECT d FROM ").append(theClass).append(" d ");

        List<String> conditions = new ArrayList<>();
        List<String> parameters = new ArrayList<>();
        List<Object> parameterValues = new ArrayList<>();

        if (parameterValue1 != 0 && parameterValue1Of1 != 0) {
            conditions.add("d." + entityQuery1 + " BETWEEN :" + parameterQuery1 + " AND :" + parameterQuery1Of1);
            parameters.add(parameterQuery1);
            parameters.add(parameterQuery1Of1);
            parameterValues.add(parameterValue1);
            parameterValues.add(parameterValue1Of1);
        }

        if (parameterValue2 != null) {
            conditions.add("d." + entityQuery2 + " = :" + parameterQuery2);
            parameters.add(parameterQuery2);
            parameterValues.add(parameterValue2);
        }

        if (!parameterValue3.isEmpty()) {
            conditions.add("d." + entityQuery3 + " = :" + parameterQuery3);
            parameters.add(parameterQuery3);
            parameterValues.add(parameterValue3);
        }

        if (conditions.add("d." + entityQuery4 + " IS NOT NULL")) {
            parameters.add(parameterQuery4);
            parameterValues.add(parameterValue4);
        }

        if (!parameterValue5.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5 + ")");
            parameters.add(parameterQuery5);
            parameterValues.add(parameterValue5);
        }

        if (!parameterQuery5Of1.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of1 + ")");
            parameters.add(parameterQuery5Of1);
            parameterValues.add(parameterValue5Of1);
        }

        if (!parameterQuery5Of2.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of2 + ")");
            parameters.add(parameterQuery5Of2);
            parameterValues.add(parameterValue5Of2);
        }

        if (!parameterQuery5Of3.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of3 + ")");
            parameters.add(parameterQuery5Of3);
            parameterValues.add(parameterValue5Of3);
        }

        if (!parameterQuery5Of4.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of4 + ")");
            parameters.add(parameterQuery5Of4);
            parameterValues.add(parameterValue5Of4);
        }

        if (!parameterQuery5Of5.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of5 + ")");
            parameters.add(parameterQuery5Of5);
            parameterValues.add(parameterValue5Of5);
        }

        if (!parameterQuery5Of6.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of6 + ")");
            parameters.add(parameterQuery5Of6);
            parameterValues.add(parameterValue5Of6);
        }

        if (!parameterQuery5Of7.isEmpty()) {
            conditions.add("d." + entityQuery5 + " NOT IN (:" + parameterQuery5Of7 + ")");
            parameters.add(parameterQuery5Of7);
            parameterValues.add(parameterValue5Of7);
        }

        if (!conditions.isEmpty()) {
            queryStringBuilder.append(" WHERE ");
            queryStringBuilder.append(String.join(" AND ", conditions));
        }

        Query query = em.createQuery(queryStringBuilder.toString());

        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(parameters.get(i), parameterValues.get(i));
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndDoubleGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                   String parameterQuery1, String parameterQuery2, Integer parameterValue1, Double parameterValue2) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0.0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }

        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0.0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndDateAndStringGeneral(String theClass, String entityQuery1, String entityQuery2,
                                                          String entityQuery3, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                          String parameterQuery3Of1, Integer parameterValue1, Date parameterValue2, String parameterValue3,
                                                          Character parameterValue3Of1) {

        StringBuilder queryBuilder = new StringBuilder("SELECT d FROM ").append(theClass).append(" d ");
        boolean hasWhereClause = false;

        if (parameterValue1 != null) {
            queryBuilder.append(hasWhereClause ? " AND " : " WHERE ").append("d.").append(entityQuery1).append(" = :").append(parameterQuery1);
            hasWhereClause = true;
        }
        if (parameterValue2 != null) {
            queryBuilder.append(hasWhereClause ? " AND " : " WHERE ").append("d.").append(entityQuery2).append(" = :").append(parameterQuery2);
            hasWhereClause = true;
        }
        if (!parameterValue3.isEmpty()) {
            queryBuilder.append(hasWhereClause ? " AND " : " WHERE ").append("d.").append(entityQuery3).append(" = :").append(parameterQuery3);
            hasWhereClause = true;
        } else if (parameterValue3Of1.equals('Y')) {
            queryBuilder.append(hasWhereClause ? " AND " : " WHERE ").append("d.").append(entityQuery3).append(" = :").append(!parameterQuery3.equalsIgnoreCase("R"));
            hasWhereClause = true;
        }

        Query query = em.createQuery(queryBuilder.toString());

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != null) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.isEmpty()) {
            query.setParameter(parameterQuery3, parameterValue3);
        }

        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndThreeStringAndBetweenDateWithModuleGeneral(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                                                String parameterQuery4, String parameterQuery5, String parameterQuery5Of1, Integer parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, Date parameterValue5, Date parameterValue5Of1) {

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.isEmpty()) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.isEmpty()) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.isEmpty()) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue5Of1 != null) {
            query.setParameter(parameterQuery5Of1, parameterValue5Of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryIntAndTwoStringModuleGeneral(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3,
                                                            Integer parameterValue1, String parameterValue2, String parameterValue3) {

        Query query = em.createQuery(queryString);

        if (parameterValue1 != null) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.isEmpty()) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.isEmpty()) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        return query.getResultList();
    }

    public void clearAllParamQueries(List<String> operators, StringBuilder queryStringBuilder, Map<String, Object> parameterMap, List<String> integerParamQueries, List<String> stringParamQueries, List<String> dateParamQueries, List<String> doubleParamQueries, List<String> characterParamQueries) {
        operators.clear();
        queryStringBuilder.setLength(0);
        parameterMap.clear();
        integerParamQueries.clear();
        stringParamQueries.clear();
        dateParamQueries.clear();
        doubleParamQueries.clear();
        characterParamQueries.clear();
    }

    public void clearAllValues(List<Integer> integerValues, List<String> stringValues, List<Date> dateValues, List<Double> doubleValues, List<Character> characterValues) {
        integerValues.clear();
        stringValues.clear();
        dateValues.clear();
        doubleValues.clear();
        characterValues.clear();
    }

    public void addParametersAndConditions1(List<String> paramQueries, List<?> paramValues) {
        for (int i = 0; i < paramQueries.size(); i++) {
            String parameterName = paramQueries.get(i);
            int haveParameter = parameterName.indexOf(":") + 1;
            if (haveParameter != 0) {

                // Extract the parameter name without the leading colon
                String paramName = parameterName.substring(haveParameter);
                Object parameterValue = paramValues.get(i);
                if (shouldSkipParameter(parameterValue)) {
                    continue; // Skip the parameter if it meets the skip conditions
                }

                parameterMap.put(paramName, parameterValue);
            }

            if (queryStringBuilder.length() == 0) {
                queryStringBuilder.append(" WHERE b.").append(parameterName);
            } else {
                if (operators.isEmpty() || operators.size() <= i) {
                    queryStringBuilder.append(" AND ").append("b.").append(parameterName);
                } else {
                    String operator = operators.get(i);
                    queryStringBuilder.append(" ").append(operator).append(" ").append("b.").append(parameterName);
                }
            }
        }
    }

    public void addParametersAndConditions(List<String> paramQueries, List<?> paramValues) {
        int index = 0;
        for (int i = 0; i < paramQueries.size(); i++) {
            String parameterName = paramQueries.get(i);
            if (parameterName.contains(":")) {
                List<Integer> colonOccurenceIndex = charOccurenceIndeces(parameterName, ':');

                for (int colonIndex : colonOccurenceIndex) {
                    // Extract the parameter name without the leading colon
                    String paramName = extractParamName(parameterName, colonIndex);
                    Object parameterValue = paramValues.get(index++);
                    if (shouldSkipParameter(parameterValue)) {
                        continue; // Skip the parameter if it meets the skip conditions
                    }

                    parameterMap.put(paramName, parameterValue);
                }
            }
            if (queryStringBuilder.length() == 0) {
                queryStringBuilder.append(" WHERE b.").append(parameterName);
            } else {
                if (operators.isEmpty() || operators.size() <= i) {
                    queryStringBuilder.append(" AND ").append("b.").append(parameterName);
                } else {
                    String operator = operators.get(i);
                    queryStringBuilder.append(" ").append(operator).append(" ").append("b.").append(parameterName);
                }
            }
        }
    }

    private String extractParamName(String parameterName, int colonIndex) {
        String paramName1 = parameterName.substring(colonIndex + 1);
        int spaceIndex = paramName1.indexOf(' ');
        int commaIndex = paramName1.indexOf(',');
        int endIndex = (spaceIndex != -1 && spaceIndex < commaIndex) ? spaceIndex
                : (commaIndex != -1) ? commaIndex : paramName1.length();
        return paramName1.substring(0, endIndex);
    }

    private List<Integer> charOccurenceIndeces(String inputString, char tagetChar) {
        return IntStream.range(0, inputString.length()).filter(i -> inputString.charAt(i) == tagetChar).boxed().collect(Collectors.toList());
    }

    private boolean shouldSkipParameter(Object parameterValue) {
        if (parameterValue == null) {
            return true;
        } else if (parameterValue instanceof String && ((String) parameterValue).isEmpty()) {
            return true; // Skip empty string parameters
        } else if (parameterValue instanceof Date && ((Date) parameterValue) == null) {
            return true; // Skip null date parameters (represented as epoch time)
        } else if (parameterValue instanceof Integer && ((Integer) parameterValue) == 0) {
            return true; // Skip zero integer parameters
        } else if (parameterValue instanceof Character && ((Character) parameterValue) == null) {
            return true; // Skip null character parameters (represented as null character)
        } else if (parameterValue instanceof Double && ((Double) parameterValue) == 0.0) {
            return true; // Skip 0.0 Double parameters (represented as 0.0 double)
        } else {
            return false;
        }

    }

    public List<?> dynamicQueryModuleGeneral() {

        String queryString = clause + " " + queryStringBuilder.toString();

        Query query = em.createQuery(queryString);

        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            String parameterName = entry.getKey();
            Object parameterValue = entry.getValue();
            query.setParameter(parameterName, parameterValue);
        }
        clearAllParamQueries(operators, queryStringBuilder, parameterMap, integerParamQueries, stringParamQueries, dateParamQueries, doubleParamQueries, characterParamQueries);
        clearAllValues(integerValues, stringValues, dateValues, doubleValues, characterValues);
        return query.getResultList();

    }

    public List<?> dynamicQueryTwoStringsAndDateAndCharacterGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4,
                                                                    String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4,
                                                                    String parameterValue1, String parameterValue2, Date parameterValue3, Character parameterValue4) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (!parameterValue1.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != null) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }

        Query query = em.createQuery(queryString);

        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndStringAndDateGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5, String entityQuery6,
                                                              String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                              Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5, Date parameterValue6) {

        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " = :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery3;
            }
        }
        if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " = :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " = :" + parameterQuery4;
            }
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " = :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " = :" + parameterQuery5;
            }
        }
        if (parameterValue6 != null) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery6 + " = :" + parameterQuery6;
            } else {
                queryString += " AND d." + entityQuery6 + " = :" + parameterQuery6;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue6 != null) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndBetweenThreeIntGeneral(String theClass, String entityQuery1, String entityQuery2, String entityQuery3, String entityQuery4, String entityQuery5,
                                                               String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery3of1, String parameterQuery4, String parameterQuery4of1, String parameterQuery5, String parameterQuery5of1,
                                                               Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue3of1, Integer parameterValue4, Integer parameterValue4of1, Integer parameterValue5, Integer parameterValue5of1) {
        String queryString = "SELECT d FROM " + theClass + " d ";
        if (parameterValue1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery1 + " = :" + parameterQuery1;
            } else {
                queryString += " AND d." + entityQuery1 + " = :" + parameterQuery1;
            }
        }
        if (parameterValue2 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += "WHERE d." + entityQuery2 + " = :" + parameterQuery2;
            } else {
                queryString += " AND d." + entityQuery2 + " = :" + parameterQuery2;
            }
        }
        if (parameterValue3 != 0 && parameterValue3of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3of1;
            } else {
                queryString += " AND d." + entityQuery3 + " BETWEEN :" + parameterQuery3 + " AND :" + parameterQuery3of1;
            }
        } else if (parameterValue3 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " >= :" + parameterQuery3;
            } else {
                queryString += " AND d." + entityQuery3 + " >= :" + parameterQuery3;
            }
        } else if (parameterValue3of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery3 + " <= :" + parameterQuery3of1;
            } else {
                queryString += " AND d." + entityQuery3 + " <= :" + parameterQuery3of1;
            }
        }
        if (parameterValue4 != 0 && parameterValue4of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " BETWEEN :" + parameterQuery4 + " AND :" + parameterQuery4of1;
            } else {
                queryString += " AND d." + entityQuery4 + " BETWEEN :" + parameterQuery4 + "AND :" + parameterQuery4of1;
            }
        } else if (parameterValue4 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " >= :" + parameterQuery4;
            } else {
                queryString += " AND d." + entityQuery4 + " >= :" + parameterQuery4;
            }
        } else if (parameterValue4of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery4 + " <= :" + parameterQuery4of1;
            } else {
                queryString += " AND d." + entityQuery4 + " <= :" + parameterQuery4of1;
            }
        }
        if (parameterValue5 != 0 && parameterValue5of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + " AND :" + parameterQuery5of1;
            } else {
                queryString += " AND d." + entityQuery5 + " BETWEEN :" + parameterQuery5 + "AND :" + parameterQuery5of1;
            }
        } else if (parameterValue5 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " >= :" + parameterQuery5;
            } else {
                queryString += " AND d." + entityQuery5 + " >= :" + parameterQuery5;
            }
        } else if (parameterValue5of1 != 0) {
            if (queryString.endsWith("d ")) {
                queryString += " WHERE d." + entityQuery5 + " <= :" + parameterQuery5of1;
            } else {
                queryString += " AND d." + entityQuery5 + " <= :" + parameterQuery5of1;
            }
        }
        Query query = em.createQuery(queryString);

        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (parameterValue3of1 != 0) {
            query.setParameter(parameterQuery3of1, parameterValue3of1);
        }
        if (parameterValue4of1 != 0) {
            query.setParameter(parameterQuery4of1, parameterValue4of1);
        }
        if (parameterValue5of1 != 0) {
            query.setParameter(parameterQuery5of1, parameterValue5of1);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFiveIntAndStringAndDateGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6, String parameterQuery7,
                                                                        Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, Integer parameterValue5, String parameterValue6, Date parameterValue7) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != 0) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        if (parameterValue7 != null) {
            query.setParameter(parameterQuery7, parameterValue7);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryTwoIntAndThreeDateGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                                   Integer parameterValue1, Integer parameterValue2, Date parameterValue3, Date parameterValue4, Date parameterValue5) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != null) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != null) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (parameterValue5 != null) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQueryFourIntAndStringGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5,
                                                                 Integer parameterValue1, Integer parameterValue2, Integer parameterValue3, Integer parameterValue4, String parameterValue5) {

        Query query = em.createQuery(queryString);
        if (parameterValue1 != 0) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (parameterValue2 != 0) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (parameterValue3 != 0) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (parameterValue4 != 0) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        return query.getResultList();
    }

    public List<?> dynamicQuerySixStringGeneralWithModule(String queryString, String parameterQuery1, String parameterQuery2, String parameterQuery3, String parameterQuery4, String parameterQuery5, String parameterQuery6,
                                                          String parameterValue1, String parameterValue2, String parameterValue3, String parameterValue4, String parameterValue5, String parameterValue6) {

        Query query = em.createQuery(queryString);
        if (!parameterValue1.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery1, parameterValue1);
        }
        if (!parameterValue2.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery2, parameterValue2);
        }
        if (!parameterValue3.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery3, parameterValue3);
        }
        if (!parameterValue4.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery4, parameterValue4);
        }
        if (!parameterValue5.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery5, parameterValue5);
        }
        if (!parameterValue6.equalsIgnoreCase("")) {
            query.setParameter(parameterQuery6, parameterValue6);
        }
        return query.getResultList();
    }

    public List<?> findAllExeception(String theClass) {
        StringBuilder queryString = new StringBuilder("SELECT d FROM ").append(theClass).append(" d ");

        TypedQuery<?> query = em.createQuery(queryString.toString(), Object.class);

        return query.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
