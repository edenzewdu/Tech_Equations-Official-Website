package com.web.jsf.util;

import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ELResolver;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsfCrudELResolver extends ELResolver {
    public static final String JSFCRUD_CLASS = "jsfcrud_class";
    public static final String JSFCRUD_METHOD = "jsfcrud_method";
    public static final String JSFCRUD_PARAMS = "jsfcrud_params";
    public static final String JSFCRUD_INVOKE = "jsfcrud_invoke";
    public static final String JSFCRUD_TRANSFORM = "jsfcrud_transform";
    public static final String JSFCRUD_NULL = "jsfcrud_null";
    
    /**
     * {@inheritDoc}
     */
    public Object getValue(ELContext context, Object base, Object property) {
        if(context == null) {
            throw new NullPointerException();
        }
        
        String propertyName = null;
        if (property != null) {
            propertyName = property.toString();
        }

        if (JSFCRUD_NULL.equals(propertyName) &&
                base == null) {
            context.setPropertyResolved(true);
            return JSFCRUD_NULL;
        }
        
        if (JSFCRUD_NULL.equals(propertyName) &&
                ! (base instanceof JsfCrudMethod) &&
                ! (base instanceof JsfCrudParameterizedMethod) &&
                ! (base instanceof JsfCrudTransform) ) {
            throw new ELException(JSFCRUD_NULL + " expects a base of type JsfCrudMethod, JsfCrudParameterizedMethod, or JsfCrudTransform; received " + base);
        }
        
        Object result = null;
        
        if (JSFCRUD_CLASS.equals(propertyName)) {
            if (base != null) {
                throw new ELException(JSFCRUD_CLASS + " expects a null base; received " + base);
            }
            result = new JsfCrudClass();
            context.setPropertyResolved(true);
            return result;
        } else if (JSFCRUD_METHOD.equals(propertyName)) {
            if (base == null) {
                throw new ELException(JSFCRUD_METHOD + " expects a non-null (possibly JsfCrudClass) base; received null");
            }
            result = new JsfCrudMethod(base);
            context.setPropertyResolved(true);
            return result;
        } else if (JSFCRUD_PARAMS.equals(propertyName)) {
            if (! (base instanceof JsfCrudMethod)) {
                throw new ELException(JSFCRUD_PARAMS + " expects a base of type JsfCrudMethod; received " + base);
            }
            result = new JsfCrudParameterizedMethod((JsfCrudMethod)base);
            context.setPropertyResolved(true);
            return result;
        } else if (JSFCRUD_INVOKE.equals(propertyName)) {
            JsfCrudParameterizedMethod parameterizedMethod = getParameterizedMethodToInvoke(base);
            result = parameterizedMethod.invoke();
            context.setPropertyResolved(true);
            return result;
        } else if (JSFCRUD_TRANSFORM.equals(propertyName)) {
            if (base == null) {
                throw new ELException(JSFCRUD_TRANSFORM + " expects a non-null base; received null");
            }
            result = new JsfCrudTransform(base);
            context.setPropertyResolved(true);
            return result;
        } else if (base instanceof JsfCrudClass) {
            ((JsfCrudClass)base).setType(propertyName);
            result = base;
            context.setPropertyResolved(true);
            return result;
        } else if (base instanceof JsfCrudMethod) {
            JsfCrudMethod baseAsMethod = (JsfCrudMethod)base;
            if (baseAsMethod.getMethodName() == null) {
                baseAsMethod.setMethodName(propertyName);
                result = base;
                context.setPropertyResolved(true);
                return result;
            }
            else {
                //already have a method name. start adding parameters.
                JsfCrudParameterizedMethod pMethod = new JsfCrudParameterizedMethod(baseAsMethod);
                pMethod.addParameter(property);
                result = pMethod;
                context.setPropertyResolved(true);
                return result;
            }
        } else if (base instanceof JsfCrudParameterizedMethod) {
            ((JsfCrudParameterizedMethod)base).addParameter(property);
            result = base;
            context.setPropertyResolved(true);
            return result;
        } else if (base instanceof JsfCrudTransform) {
            if (JSFCRUD_NULL.equals(propertyName)) {
                ((JsfCrudTransform)base).addNullMethod();
                result = base;
                context.setPropertyResolved(true);
                return result;
            } else if (property instanceof JsfCrudMethod) {
                ((JsfCrudTransform)base).addMethod((JsfCrudMethod)property);
                result = base;
                context.setPropertyResolved(true);
                return result;
            }
            else {
                result = ((JsfCrudTransform)base).getProperty(propertyName);
                context.setPropertyResolved(true);
                return result;
            }
        } 
        
        return null;
    }
    
    private JsfCrudParameterizedMethod getParameterizedMethodToInvoke(Object base) {
        JsfCrudParameterizedMethod parameterizedMethod = null;
        if (base instanceof JsfCrudParameterizedMethod) {
            parameterizedMethod = (JsfCrudParameterizedMethod)base;
        } else if (base instanceof JsfCrudMethod) {
            parameterizedMethod = new JsfCrudParameterizedMethod((JsfCrudMethod)base);
        }
        if (parameterizedMethod == null) {
            throw new ELException(JSFCRUD_INVOKE + " expects a base of type JsfCrudParameterizedMethod or JsfCrudMethod; received " + base);
        }
        return parameterizedMethod;
    }
    
    /**
     * {@inheritDoc}
     */
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if(context == null) {
            throw new NullPointerException();
        }
        
        String propertyName = null;
        if (property != null) {
            propertyName = property.toString();
        }

        if ( base instanceof JsfCrudClass || 
             base instanceof JsfCrudMethod ||
             base instanceof JsfCrudParameterizedMethod ||
             (base instanceof JsfCrudTransform && property instanceof JsfCrudMethod) ||
             JSFCRUD_CLASS.equals(propertyName) ||
             JSFCRUD_METHOD.equals(propertyName) ||
             JSFCRUD_PARAMS.equals(propertyName) ||
             JSFCRUD_INVOKE.equals(propertyName) ||
             JSFCRUD_TRANSFORM.equals(propertyName) ||
             JSFCRUD_NULL.equals(propertyName) ||
             property == null ) {
            throw new ELException("setValue was called with base " + base + ", property " + property + ", and value " + value + "; expected a base of type JsfCrudTransform and a valid property of the JsfCrudTransform's own base");
        }
        
        if (base instanceof JsfCrudTransform) {
            ((JsfCrudTransform)base).setProperty(propertyName, value);
            context.setPropertyResolved(true);
        }
    }
    
   /**
     * {@inheritDoc}
     */
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if(context == null) {
            throw new NullPointerException();
        }
        
        String propertyName = null;
        if (property != null) {
            propertyName = property.toString();
        }

        if (JSFCRUD_CLASS.equals(propertyName) && base == null) {
            context.setPropertyResolved(true);
        } else if (JSFCRUD_METHOD.equals(propertyName) && base != null) {
            context.setPropertyResolved(true);
        } else if (JSFCRUD_PARAMS.equals(propertyName) && (base instanceof JsfCrudMethod)) {
            context.setPropertyResolved(true);
        } else if (JSFCRUD_INVOKE.equals(propertyName) && (base instanceof JsfCrudParameterizedMethod || base instanceof JsfCrudMethod)) {
            context.setPropertyResolved(true);
        } else if (JSFCRUD_TRANSFORM.equals(propertyName) && base != null) {
            context.setPropertyResolved(true);
        } else if (base instanceof JsfCrudClass) {
            context.setPropertyResolved(true);
        } else if (base instanceof JsfCrudMethod) {
            context.setPropertyResolved(true);
        } else if (base instanceof JsfCrudParameterizedMethod) {
            context.setPropertyResolved(true);
        } else if (base instanceof JsfCrudTransform) {
            if (property instanceof JsfCrudMethod) {
                context.setPropertyResolved(true);
            }
            else {
                context.setPropertyResolved(true);
                return false;
            }
        } else {
            return false;
        }
        
        return true;
    }
    
        /**
     * {@inheritDoc}
     */
    public Class<?> getType(ELContext context, Object base, Object property) {
        if(context == null) {
            throw new NullPointerException();
        }
        
        String propertyName = null;
        if (property != null) {
            propertyName = property.toString();
        }

        if (JSFCRUD_CLASS.equals(propertyName) && base == null) {
            context.setPropertyResolved(true);
            return JsfCrudClass.class;
        } else if (JSFCRUD_METHOD.equals(propertyName) && base != null) {
            context.setPropertyResolved(true);
            return JsfCrudMethod.class;
        } else if (JSFCRUD_PARAMS.equals(propertyName) && (base instanceof JsfCrudMethod)) {
            context.setPropertyResolved(true);
            return JsfCrudParameterizedMethod.class;
        } else if (JSFCRUD_INVOKE.equals(propertyName) && (base instanceof JsfCrudParameterizedMethod || base instanceof JsfCrudMethod)) {
            JsfCrudParameterizedMethod parameterizedMethod = getParameterizedMethodToInvoke(base);
            Class<?> result = parameterizedMethod.getReturnType();
            context.setPropertyResolved(true);
            return result;
        } else if (JSFCRUD_TRANSFORM.equals(propertyName) && base != null) {
            context.setPropertyResolved(true);
            return JsfCrudTransform.class;
        } else if (base instanceof JsfCrudClass) {
            context.setPropertyResolved(true);
            return JsfCrudClass.class;
        } else if (base instanceof JsfCrudMethod) {
            JsfCrudMethod baseAsMethod = (JsfCrudMethod)base;
            if (baseAsMethod.getMethodName() == null) {
                context.setPropertyResolved(true);
                return JsfCrudMethod.class;
            }
            else {
                context.setPropertyResolved(true);
                return JsfCrudParameterizedMethod.class;
            }
        } else if (base instanceof JsfCrudParameterizedMethod) {
            context.setPropertyResolved(true);
            return JsfCrudParameterizedMethod.class;
        } else if (base instanceof JsfCrudTransform) {
            if (property instanceof JsfCrudMethod) {
                context.setPropertyResolved(true);
                return JsfCrudTransform.class;
            }
            else {
                Class<?> result = ((JsfCrudTransform)base).getPropertyType(propertyName);
                context.setPropertyResolved(true);
                return result;
            }
        }
        
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        //todo: implement
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Class getCommonPropertyType(ELContext context,
                                                Object base) {
        if (context == null) {
            throw new NullPointerException();
        }
        
        if (base == null || base instanceof JsfCrudClass) {
            return String.class;
        } else if (base instanceof JsfCrudMethod) {
            JsfCrudMethod baseAsMethod = (JsfCrudMethod)base;
            if (baseAsMethod.getMethodName() == null) {
                return String.class;
            }
            else {
                return Object.class; //could be a param Object, JSFCRUD_NULL, or JSFCRUD_INVOKE
            }
        } else if (base instanceof JsfCrudParameterizedMethod) {
            return Object.class; //could be a param Object, JSFCRUD_NULL, or JSFCRUD_INVOKE
        } else if (base instanceof JsfCrudTransform) {
            JsfCrudTransform baseAsTransform = (JsfCrudTransform)base;
            boolean[] tma = baseAsTransform.transformMethodsAssigned;
            if (!tma[0] || !tma[1]) {
                return Object.class; //could be a JsfCrudMethod or JSFCRUD_NULL
            }
            else {
                return String.class; //the tailing propertyName
            }
        } else if (base != null) {
            return String.class;    //a catch-all
        }
        
        return null;
    }
    
    private class JsfCrudClass {
        private Class<?> type;
        public Class<?> getType() {
            return type;
        }
        public void setType(String typeName) {
            try {
                type = Class.forName(typeName);
            } catch (ClassNotFoundException e){
                throw new ELException(e);
            }
        }
        @Override
        public String toString() {
            return "JsfCrudClass[" + type + "]";
        }
    }
    
    private class JsfCrudMethod {
        private Object base;    //can be an JsfCrudClass instance, or an arbitrary Object
        private String methodName;
        public JsfCrudMethod(Object base) {
            this.base = base;
        }
        public Object getBase() {
            return base;
        }
        public String getMethodName() {
            return methodName;
        }
        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
        @Override
        public String toString() {
            return "JsfCrudMethod[base=" + base + ",methodName=" + methodName + "]";
        }
    }
    
    private class JsfCrudParameterizedMethod {
        private JsfCrudMethod method;
        private List<Object> actualParams;
        private Method methodToInvoke;
        
        public JsfCrudParameterizedMethod(JsfCrudMethod method) {
            this.method = method;
            actualParams = new ArrayList<Object>();
        }
        public JsfCrudMethod getMethod() {
            return method;
        }
        public void addParameter(Object param) {
            if (JsfCrudELResolver.JSFCRUD_NULL.equals(param)) {
                param = null;
            }
            actualParams.add(param);
        }
        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer("JsfCrudParameterizedMethod[method=");
            sb.append(method);
            sb.append(",params=List[");
            int i = 0;
            for (Object param : actualParams) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(param);
                i++;
            }
            sb.append("]]");
            return sb.toString();
        }
        public Object invoke() {
            findMethodToInvoke();
            Object methodBase = method.getBase();
            Object instance = methodBase instanceof JsfCrudClass ? null : methodBase;
            Object[] paramArray = actualParams.toArray();
            try {
                return methodToInvoke.invoke(instance, paramArray);
            } catch (IllegalAccessException e) {
                throw new ELException(e);
            } catch (InvocationTargetException e) {
                throw new ELException(e);
            }
        }
        
        public Class<?> getReturnType() {
            findMethodToInvoke();
            return methodToInvoke.getReturnType();
        }
        
        private void findMethodToInvoke() {
            if (methodToInvoke != null) {
                return;
            }
            
            Object methodBase = method.getBase();
            JsfCrudClass staticMethodBase = null;
            if (methodBase instanceof JsfCrudClass) {
                staticMethodBase = (JsfCrudClass)methodBase;
            }
            Class<?> type;
            Method[] methodsOfType;
            if (staticMethodBase == null) {
                type = methodBase.getClass();
                methodsOfType = type.getMethods();
            }
            else {
                type = staticMethodBase.getType();
                methodsOfType = type.getDeclaredMethods();
            }
            methodsOfTypeLoop:
            for (Method methodOfType : methodsOfType) {
                String methodName = method.getMethodName();
                String methodOfTypeName = methodOfType.getName();
                if (!methodName.equals(methodOfTypeName)) {
                    continue;
                }
                if (staticMethodBase == null) {
                    if (Modifier.isStatic(methodOfType.getModifiers())) {
                        continue;
                    }
                }
                else {
                    if (!Modifier.isStatic(methodOfType.getModifiers())) {
                        continue;
                    }
                }
                Class<?>[] methodOfTypeFormalParams = methodOfType.getParameterTypes();
                if (methodOfTypeFormalParams.length != actualParams.size()) {
                    continue;
                }
                for (int i = 0; i < methodOfTypeFormalParams.length; i++) {
                    Object param = actualParams.get(i);
                    if (param == null) {
                        if (methodOfTypeFormalParams[i].isPrimitive()) {
                            continue methodsOfTypeLoop;
                        }
                    } else {
                        Class<?> paramType = param.getClass();
                        if (!methodOfTypeFormalParams[i].isAssignableFrom(paramType)) {
                            continue methodsOfTypeLoop;
                        }
                    }
                }
                methodToInvoke = methodOfType;
                break;
            }
            if (methodToInvoke == null) {
                throw new ELException("could not find method to invoke; no appropriate method found in type " +  type + ". JsfCrudParameterizedMethod was " + this);
            }
        }
    }
    
    private class JsfCrudTransform {
        private Object base;
        private JsfCrudMethod[] transformMethods;
        private boolean[] transformMethodsAssigned;
        public JsfCrudTransform(Object base) {
            this.base = base;
            transformMethods = new JsfCrudMethod[2];
            transformMethodsAssigned = new boolean[2];
        }
        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("JsfCrudTransform=[base=");
            sb.append(base);
            sb.append(",transformMethod0=");
            sb.append(transformMethods[0]);
            sb.append(",transformMethod1=");
            sb.append(transformMethods[1]);
            sb.append(",transformMethodsAssigned0=");
            sb.append(transformMethodsAssigned[0]);
            sb.append(",transformMethodsAssigned1=");
            sb.append(transformMethodsAssigned[1]);
            sb.append("]");
            return sb.toString();
        }
        public Object getBase() {
            return base;
        }
        public void addMethod(JsfCrudMethod method) {
            if (!transformMethodsAssigned[0]) {
                transformMethods[0] = method;
                transformMethodsAssigned[0] = true;
            }
            else {
                if (transformMethodsAssigned[1]) {
                    throw new ELException("attempt to add more than two methods to a JsfCrudTransform; additional JsfCrudMethod was " + method);
                }
                transformMethods[1] = method;
                transformMethodsAssigned[1] = true;
            }
        }
        public void addNullMethod() {
            if (!transformMethodsAssigned[0]) {
                transformMethodsAssigned[0] = true;
            }
            else {
                if (transformMethodsAssigned[1]) {
                    throw new ELException("attempt to add more than two methods to a JsfCrudTransform; additional JsfCrudMethod was null");
                }
                transformMethodsAssigned[1] = true;
            }
        }
        public Class<?> getPropertyType(String propertyName) {
            if (transformMethods[0] == null) {
                PropertyDescriptor pd = getPropertyDescriptor(propertyName);
                return pd.getPropertyType();
            }
            JsfCrudParameterizedMethod parameterizedMethod = getParameterizedTransformationMethod(propertyName);
            return parameterizedMethod.getReturnType();
        }
        private Object getUntransformedProperty(String propertyName) {
            PropertyDescriptor pd = getPropertyDescriptor(propertyName);
            if (pd == null) {
                throw new ELException("could not get untransformed property " + propertyName + " of base object " + base + ": base object has no such property");
            }
            Method readMethod = pd.getReadMethod();
            Object rawResult;
            try {
                rawResult = readMethod.invoke(base);
            } catch (IllegalAccessException e) {
                throw new ELException(e);
            } catch (InvocationTargetException e) {
                throw new ELException(e);
            } 
            return rawResult;
        }
        private JsfCrudParameterizedMethod getParameterizedTransformationMethod(String propertyName) {
            Object rawResult = getUntransformedProperty(propertyName);
            JsfCrudParameterizedMethod parameterizedMethod = new JsfCrudParameterizedMethod(transformMethods[0]);
            parameterizedMethod.addParameter(rawResult);
            return parameterizedMethod;
        }
        public Object getProperty(String propertyName) {
            if (transformMethods[0] == null) {
                return getUntransformedProperty(propertyName);
            }
            JsfCrudParameterizedMethod parameterizedMethod = getParameterizedTransformationMethod(propertyName);
            return parameterizedMethod.invoke();
        }
        public void setProperty(String propertyName, Object value) {
            PropertyDescriptor pd = getPropertyDescriptor(propertyName);
            if (pd == null) {
                throw new ELException("could not set property " + propertyName + " of base object " + base + " with raw value " + value + ": base object has no such property");
            }
            Object transformedOrUntransformedValue = null;
            if (transformMethods[1] == null) {
                transformedOrUntransformedValue = value;
            }
            else {
                JsfCrudParameterizedMethod parameterizedMethod = new JsfCrudParameterizedMethod(transformMethods[1]);
                parameterizedMethod.addParameter(value);
                transformedOrUntransformedValue = parameterizedMethod.invoke();
            }
            Method writeMethod = pd.getWriteMethod();
            try {
                writeMethod.invoke(base, transformedOrUntransformedValue);
            } catch (IllegalAccessException e) {
                throw new ELException(e);
            } catch (InvocationTargetException e) {
                throw new ELException(e);
            }
        }
        private PropertyDescriptor getPropertyDescriptor(String propertyName) {
            Class<?> baseType = base.getClass();
            BeanInfo info;
            try {
                info = Introspector.getBeanInfo(baseType);
            } catch (IntrospectionException ie) {
                throw new ELException(ie);
            }
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (propertyName.equals(pd.getName())) {
                    return pd;
                }
            }
            return null;
        }
    }
}
