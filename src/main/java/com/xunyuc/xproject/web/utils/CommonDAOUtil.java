package com.xunyuc.xproject.web.utils;

import com.xunyuc.xproject.web.entity.UserInfo;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by chenwh1 on 2017/7/12.
 */
public class CommonDAOUtil {


    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentReferenceHashMap<Class<?>, Field[]>(256);
    private static final Field[] NO_FIELDS = {};
    private static final List<Class> entityFieldTypes;

    static {
        entityFieldTypes = new ArrayList<Class>();
        entityFieldTypes.add(String.class);
        entityFieldTypes.add(Integer.class);
        entityFieldTypes.add(Long.class);
        entityFieldTypes.add(Double.class);
        entityFieldTypes.add(int.class);
        entityFieldTypes.add(long.class);
        entityFieldTypes.add(double.class);
        entityFieldTypes.add(BigInteger.class);
        entityFieldTypes.add(BigDecimal.class);
    }

    public static void main(String[] args) {
        System.out.println(CommonDAOUtil.generateInsertSql(UserInfo.class));
        System.out.println(CommonDAOUtil.generateUpdateSql(UserInfo.class));
    }

    public static String generateInsertSql(Class clazz) {
        EntityMeta entityMeta = getEntityMeta(clazz);

        StringBuffer sql = new StringBuffer();
        sql.append("insert into ").append(entityMeta.getTableName()).append(" (")
                .append(org.apache.commons.lang3.StringUtils.join(entityMeta.getTableFieldNames(), ","))
                .append(") values(").append(org.apache.commons.lang3.StringUtils.join(entityMeta.getEntityFieldNames(), ","))
                .append(")");

        return sql.toString();
    }

    public static String generateUpdateSql(Class clazz) {
        EntityMeta entityMeta = getEntityMeta(clazz);
        String tableName = entityMeta.getTableName();
        List<String> tableFieldNames = entityMeta.getTableFieldNames();
        List<String> entityFieldNames = entityMeta.getEntityFieldNames();

        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(entityMeta.getTableName()).append(" set ");
        for (int i = 0; i < tableFieldNames.size(); i++) {
            if (i == 0) {
                sql.append(tableFieldNames.get(i)).append("=").append(entityFieldNames.get(i));
            } else {
                sql.append(",").append(tableFieldNames.get(i)).append("=").append(entityFieldNames.get(i));
            }
        }
        // TODO where
//        sql.append(" where ");

        return sql.toString();
    }

    private static EntityMeta getEntityMeta(Class clazz) {
        EntityMeta entityMeta = new EntityMeta();

        List<String> tableFieldNames = new ArrayList();
        List<String> entityFieldNames = new ArrayList();
        // 获取table name
        String tableName = underscoreName(clazz.getSimpleName());
        if (clazz.isAnnotationPresent(Entity.class)) {
            Entity entity = (Entity) clazz.getAnnotation(Entity.class);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(entity.name())) {
                tableName = entity.name();
            }
        }

        // 获取table field name & entity field name
        Field[] result = CommonDAOUtil.getDeclaredFields(clazz);
        Column fieldColumn;
        for (Field field : result) {
            // 不用注解
//            if (entityFieldTypes.contains(field.getType())) {
//                tableFieldNames.add(underscoreName(field.getName()));
//                entityFieldNames.add(":" + field.getName());
//            }
            // 使用注解
            if (field.isAnnotationPresent(Column.class)) {
                fieldColumn = field.getAnnotation(Column.class);
                tableFieldNames.add(fieldColumn.name());
                entityFieldNames.add(":" + field.getName());
            }
        }

        entityMeta.setTableName(tableName);
        entityMeta.setTableFieldNames(tableFieldNames);
        entityMeta.setEntityFieldNames(entityFieldNames);

        return entityMeta;
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            System.out.println("*********************************");
            declaredFieldsCache.put(clazz, (result.length == 0 ? NO_FIELDS : result));
        }
        return result;
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case.
     * Any upper case letters are converted to lower case with a preceding underscore.
     *
     * @param name the string containing original name
     * @return the converted name
     */
    private static String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(name.substring(0, 1).toLowerCase());
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = s.toLowerCase();
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }

    public static void reflectionPerformanceTest() {
        //        Class mappedClass = UserInfo.class;
//
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            mappedClass.getDeclaredFields();
//        }
//        System.out.println(System.currentTimeMillis() - start);

//        long start2 = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            getDeclaredFields(mappedClass);
//        }
//        System.out.println(System.currentTimeMillis() - start2);
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName("1234567890");
//        Field[] fields = getDeclaredFields(mappedClass);
//        Field fileKeyField = null;
//        for (Field field : fields) {
//            if (field.getName().equals("name")) {
//                fileKeyField = field;
//                break;
//            }
//        }
//        long start3 = System.currentTimeMillis();
//        for (int i = 0; i < 10000000; i++) {
//            fileKeyField.setAccessible(true);
//            try {
//                fileKeyField.get(userInfo);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(System.currentTimeMillis() - start3);
    }

    private static class EntityMeta {
        private String tableName;
        private List<String> tableFieldNames;
        private List<String> entityFieldNames;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public List<String> getTableFieldNames() {
            return tableFieldNames;
        }

        public void setTableFieldNames(List<String> tableFieldNames) {
            this.tableFieldNames = tableFieldNames;
        }

        public List<String> getEntityFieldNames() {
            return entityFieldNames;
        }

        public void setEntityFieldNames(List<String> entityFieldNames) {
            this.entityFieldNames = entityFieldNames;
        }

    }

}
