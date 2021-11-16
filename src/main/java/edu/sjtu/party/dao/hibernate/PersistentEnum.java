package edu.sjtu.party.dao.hibernate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SingleColumnType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * Kanged by marstone on 2015/10/16, migrate from Hibernate3 to Hibernate4
 *
 * http://www.hibernate.org/203.html Provides a base class for implementations
 * of persistable, type-safe, comparable and serializable enums with a custom
 * persisted representation.
 *
 * <p>
 * The subclass must provide a compareTo(Object) and getNullableType()
 * implementation.
 * </p>
 *
 * <p>
 * <code>
 * $Id: PersistentEnum.java,v 1.1 2015/02/11 15:22:59 gwang Exp $
 * </pre></p>
 *
 * @version $Revision: 1.1 $
 * @author &Oslash;rjan Nygaard Austvold
 */


/**
 * http://www.hibernate.org/203.html Provides a base class for implementations
 * of persistable, type-safe, comparable and serializable enums with a custom
 * persisted representation.
 *
 * <p>
 * The subclass must provide a compareTo(Object) and getNullableType()
 * implementation.
 * </p>
 *
 * <p>
 * <code>
 * $Id: PersistentEnum.java,v 1.1 2015/02/11 15:22:59 gwang Exp $
 * </pre></p>
 *
 * @version $Revision: 1.1 $
 * @author &Oslash;rjan Nygaard Austvold
 */
public abstract class PersistentEnum implements Comparable<PersistentEnum>, Serializable, UserType {
    /**
     * <code>Map</code> where key is of class name, value is of
     * <code>Map</code>. where key is of enumCode and value is of enum
     * instance.
     */
    private static final Map<Class<PersistentEnum>, Map<Serializable, PersistentEnum>> enumClasses = new HashMap<>();

    /**
     * The identifying enum code.
     */
    @Expose
    @SerializedName("code")
    protected Serializable enumCode;

    /**
     * The name of the enumeration. Used as toString result.
     */
    @Expose
    protected String name;

    /**
     * The hashcode representation of the enum.
     */
    protected transient int hashCode;

    protected PersistentEnum() {
    }

    /**
     * Constructs a new enumeration instance with the given name and persisted
     * representation of enumCode.
     *
     * @param enumCode
     *            persisted enum representation.

     * @param name
     *            name of the enum instance.
     */
    protected PersistentEnum(Serializable enumCode, String name) {
        this.name = name;
        this.enumCode = enumCode;
        hashCode = 7 + returnedClass().hashCode() + 3 * enumCode.hashCode();

        Class<? extends PersistentEnum> enumClass = returnedClass();
        Map<Serializable, PersistentEnum> entries = enumClasses.get(enumClass);
        if (entries == null) {
            entries = new HashMap<>();
            enumClasses.put((Class<PersistentEnum>) enumClass, entries);

        }
        if (entries.containsKey(enumCode)) {
            throw new IllegalArgumentException("The enum code must be unique, '" + enumCode + "' has already been added");
        }
        entries.put(enumCode, this);
    }

    /**
     * @see Object#hashCode()
     */
    public final int hashCode() {
        return hashCode;
    }

    /**
     * @see Object#equals(Object)
     */
    public final boolean equals(Object other) {

        // @author J.Brown 31.08.2004
        // An instanceof check is probably wise..

        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (!(other instanceof PersistentEnum)) {
            return false;
        }

        if (((PersistentEnum) other).returnedClass().getName().equals(returnedClass().getName())) {
            // different classloaders
            try {
                // try to avoid reflection
                return enumCode.equals(((PersistentEnum) other).enumCode);

            } catch (ClassCastException ex) {
                // use reflection
                try {
                    Method mth = other.getClass().getMethod("getEnumCode", (Class<?>[]) null);
                    Serializable enumCode = (Serializable) mth.invoke(other, (Object[]) null);
                    return this.enumCode.equals(enumCode);
                } catch (Exception ignore) { // NoSuchMethod-,
                    // IllegalAccess-,
                    // InvocationTargetException
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Gets the persistable enum code of this enum.
     *
     * @return the enum code.
     */
    public final Serializable getEnumCode() {
        return enumCode;
    }

    /**
     * Resolves this enumeration into a already staticly instantiated enum.
     *
     * @return the type-safe enum equivalent to this enumeration.
     */
    protected Object readResolve() {
        Map<Serializable, PersistentEnum> entries = enumClasses.get(returnedClass());
        return (entries != null) ? entries.get(enumCode) : null;
    }

    /**
     * Gets the collection of enumeration instances of a given enumeration
     * class.
     *
     * @param enumClass
     *            enumeration class type.
     * @return collection of enumerations of the given class.
     */
    @SuppressWarnings("unchecked")
    protected static <T extends PersistentEnum> Collection<T> getEnumCollection(Class<T> enumClass) {
        Map<Serializable, PersistentEnum> entries = enumClasses.get(enumClass);
        return (entries != null) ? Collections.unmodifiableCollection((Collection<T>) entries.values()) : Collections.EMPTY_LIST;
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    public abstract int compareTo(PersistentEnum other);

    /**
     * Gets the Hibernate type of the persisted representation of the enum.
     *
     * @return the Hibernate type.
     */
    protected abstract SingleColumnType getHibernateType();

    @Override
    public int[] sqlTypes() {
        return new int[] { getHibernateType().sqlType() };
    }

    @Override
    public String toString() {
        return this.enumCode.toString();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        // Enums are immutable - nothing to be done to deeply clone it
        return value;
    }

    @Override
    public boolean isMutable() {
        // Enums are immutable
        return false;
    }

    @Override
    public Class<? extends PersistentEnum> returnedClass() {
        return this.getClass();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        } else if (x == null || y == null) {
            return false;
        } else {
            return getHibernateType().isEqual(x, y, null);
        }
    }


    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Serializable enumCode = (Serializable) getHibernateType().nullSafeGet(rs, names[0], session);
        Map<Serializable, PersistentEnum> entries = enumClasses.get(returnedClass());
        return ((entries != null) ? entries.get(enumCode) : null);
    }


    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        // make sure the received value is of the right type
        if ((value != null) && !returnedClass().isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Received value is not a [" + returnedClass().getName() + "] but [" + value.getClass() + "]");
        }

        if (value == null) {
            st.setNull(index, getHibernateType().sqlType());
        } else {
            // convert the enum into its persistence format
            Serializable enumCode = ((PersistentEnum) value).getEnumCode();

            // set the value into the resultset
            st.setObject(index, enumCode, getHibernateType().sqlType());
        }
    }

    public String getLabel() {
        return this.name;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

    public static <T extends PersistentEnum> List<T> getEnumList(Class<T> clazz) {
        Collection<T> c = getEnumCollection(clazz);
        List<T> list = new LinkedList<T>();
        list.addAll(c);
        Collections.sort(list);
        return list;
    }

    public static <T extends PersistentEnum> T fromEnumCode(Class<T> clazz, Serializable enumCode) {
        Map<Serializable, T> entries = (Map<Serializable, T>) enumClasses.get(clazz);
        return (entries != null) ? entries.get(enumCode) : null;
    }
}