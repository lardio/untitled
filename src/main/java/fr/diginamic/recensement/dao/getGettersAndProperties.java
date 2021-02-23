package fr.diginamic.recensement.dao;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Retourne tous les getters et setters d'une classe.
 * Utilisé pour mais une mise a jour / creation generique de classe en recuperant les données a envoyer en base.
 *  * @author Sylvain
 */
public class getGettersAndProperties {

    static ArrayList<Method> findGetters(Class<?> c) {

        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isGetter(method))
                list.add(method);

        Method[] methodsParents = c.getSuperclass().getDeclaredMethods();
        for (Method method : methodsParents)
            if (isGetter(method))
                list.add(method);
        return list;
    }

    static ArrayList<Method> findSetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isSetter(method))
                list.add(method);

        Method[] methodsParents = c.getSuperclass().getDeclaredMethods();
        for (Method method : methodsParents)
            if (isSetter(method))
                list.add(method);
        return list;
    }

    public static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers()) &&
                method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*") &&
                    !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches("^is[A-Z].*") &&
                    method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }

    public static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
                method.getReturnType().equals(void.class) &&
                method.getParameterTypes().length == 1 &&
                method.getName().matches("^set[A-Z].*");
    }

}
