package com.lewis.master.common.utils.beanUitl;

import com.lewis.master.common.core.Preconditon;
import com.lewis.master.common.utils.*;
import com.lewis.master.domain.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public final class BeanUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private BeanUtil() {
    }

    public static Map<String, String> bean2Map(Object obj) {
        Map<String, String> retMap = MapUtil.newHashMap();
        if (obj != null) {
            try {
                PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(obj.getClass());
                if (ArrayUtil.isNotEmpty(propertyDescriptors)) {
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        if (!propertyDescriptor.getName().equals("class")) {
                            Method readMethod = propertyDescriptor.getReadMethod();
                            if (readMethod != null) {
                                Object value = readMethod.invoke(obj);
                                if (value instanceof String) {
                                    retMap.put(propertyDescriptor.getName(), (String) value);
                                } else {
                                    retMap.put(propertyDescriptor.getName(), JsonUtil.toString(value));
                                }
                            }
                        }
                    }
                }
            } catch (InvocationTargetException e) {
                logger.error("bean2Map occur InvocationTargetException {},param is {}", e.getCause(), JsonUtil.toString(obj));
            } catch (IllegalAccessException e) {
                logger.error("bean2Map occur IllegalAccessException {},param is {}", e.getCause(), JsonUtil.toString(obj));
            }
        }
        return retMap;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class type) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            if (beanInfo != null) {
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                return propertyDescriptors;
            }
        } catch (IntrospectionException e) {
            logger.error("getPropertyDescriptors occur IntrospectionException {},param is {}", e.getCause(), type.getName());
        }
        return new PropertyDescriptor[0];
    }

    public static <T> T map2Bean(Map<String, String> map, Class<T> type) {
        T retValue = null;
        if (MapUtil.isNotEmpty(map) && type != null) {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            try {
                retValue = type.newInstance();
                PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(type);
                while (it.hasNext()) {
                    final Map.Entry<String, String> entry = it.next();
                    PropertyDescriptor pd = SearchUtil.searchOne(Arrays.asList(propertyDescriptors), new Preconditon<PropertyDescriptor>() {
                                public boolean evaluate(PropertyDescriptor propertyDescriptor) {
                                    return propertyDescriptor.getName().equals(entry.getKey());
                                }
                            }
                    );
                    if (pd != null) {
                        Method writeMethod = pd.getWriteMethod();
                        if (writeMethod != null) {
                            Class<?> propertyType = pd.getPropertyType();
                            String value = entry.getValue();
                            if (StringUtil.isNotEmpty(value)) {
                                if (propertyType == String.class) {
                                    writeMethod.invoke(retValue, value);
                                } else {
                                    Object object = JsonUtil.toBean(value, propertyType);
                                    writeMethod.invoke(retValue, object);
                                }
                            }
                        }
                    }
                }
            } catch (InstantiationException e) {
                logger.error("map2Bean occur InstantiationException {},map is {},type is {}", e.getCause(), JsonUtil.toString(map), type.getName());
            } catch (IllegalAccessException e) {
                logger.error("map2Bean occur IllegalAccessException {},map is {},type is {}", e.getCause(), JsonUtil.toString(map), type.getName());
            } catch (InvocationTargetException e) {
                logger.error("map2Bean occur InvocationTargetException {},map is {},type is {}", e.getCause(), JsonUtil.toString(map), type.getName());
            }
        }
        return retValue;
    }


    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj != null && StringUtil.isNotEmpty(fieldName)) {
            Field field = getField(obj.getClass(), fieldName);
            return getFieldValue(obj, field);
        }
        return null;
    }

    public static Field getField(Field[] fields, String fieldName) {
        if (ArrayUtil.isNotEmpty(fields) && StringUtil.isNotEmpty(fieldName)) {
            for (Field field : fields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
        }
        return null;
    }

    public static Object getFieldValue(Object obj, Field field) {
        if (obj != null && field != null) {
            boolean accessible = false;
            try {
                accessible = field.isAccessible();
                if (!accessible) {
                    field.setAccessible(true);
                }
                return field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error("getFieldValue occur {},param is {},field is {}", e.getCause(), JsonUtil.toString(obj), field.getName());
            }finally {
                field.setAccessible(accessible);
            }
        }
        return null;
    }


    private static Field getField(Class type, String fieldName) {
        if (type != null && StringUtil.isNotEmpty(fieldName)) {
            Field[] fields = getFields(type, true);
            if (ArrayUtil.isNotEmpty(fields)) {
                for (Field field : fields) {
                    if (field.getName().equals(fieldName)) {
                        return field;
                    }
                }
            }
        }
        return null;
    }

    public static Field[] getFields(Class type, boolean includeParentClass) {
        List<Field> fieldList = new LinkedList<Field>();
        if (type != null) {
            Field[] declaredFields = type.getDeclaredFields();
            if (ArrayUtil.isNotEmpty(declaredFields)) {
                fieldList.addAll(Arrays.asList(declaredFields));
            }
            if (includeParentClass) {
                fieldList.addAll(Arrays.asList(getFields(type.getSuperclass(), includeParentClass)));
            }
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    public static Method[] getMethods(Class type, boolean includeParentClass) {
        List<Method> methodList = new LinkedList<Method>();
        if (type != null) {
            Method[] declaredMethods = type.getDeclaredMethods();
            if (ArrayUtil.isNotEmpty(declaredMethods)) {
                methodList.addAll(Arrays.asList(declaredMethods));
            }
            if (includeParentClass && type.getSuperclass() != Object.class) {
                methodList.addAll(Arrays.asList(getMethods(type.getSuperclass(), includeParentClass)));
            }
        }
        return methodList.toArray(new Method[methodList.size()]);
    }

    public static void main(String[] args) {
        XiaoStudent student = new XiaoStudent();
        student.setId(1000);
        student.setName("lewis");
        student.setBirthday("2016-12-12");
        student.setGender(1);
        List<String> hobbys = new ArrayList<String>();
        hobbys.add("singing");
        hobbys.add("dancing");
        hobbys.add("reading");
        student.setHobbys(hobbys);
        Pet pet = new Pet();
        pet.setName("diandian");
        pet.setCatalog("dog");
        pet.setId(100);
        List<Pet> pets = new ArrayList<Pet>();
        pets.add(pet);
        student.setPets(pets);
        Map<String, String> map = bean2Map(student);
        System.out.println(map);
        XiaoStudent student1 = map2Bean(map, XiaoStudent.class);
        System.out.println(student1);

    }

    static class XiaoStudent extends Student {
        private int xiaoxiao;
        private String dada;
        private List<Pet> pets;

        public int getXiaoxiao() {
            return xiaoxiao;
        }

        public void setXiaoxiao(int xiaoxiao) {
            this.xiaoxiao = xiaoxiao;
        }

        public String getDada() {
            return dada;
        }

        public void setDada(String dada) {
            this.dada = dada;
        }

        public List<Pet> getPets() {
            return pets;
        }

        public void setPets(List<Pet> pets) {
            this.pets = pets;
        }

        @Override
        public String toString() {
            return "XiaoStudent{" +
                    "xiaoxiao=" + xiaoxiao +
                    ", dada='" + dada + '\'' +
                    ", pets=" + pets +
                    '}';
        }
    }

    static class Pet {
        private int id;

        private String name;

        private String catalog;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        @Override
        public String toString() {
            return "Pet{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", catalog='" + catalog + '\'' +
                    '}';
        }
    }
}
