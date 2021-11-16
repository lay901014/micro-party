package edu.sjtu.party.dao.hibernate;

/**
 * Created by gwang on 19/7/30.
 */
import org.hibernate.type.SingleColumnType;
import org.hibernate.type.StringType;

public class PersistentStringEnum extends PersistentEnum {

    protected PersistentStringEnum() {
    }

    protected PersistentStringEnum(String code, String name) {
        super(code, name);
    }

    @Override
    public int compareTo(PersistentEnum other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if (other == this) {
            return 0;
        }
        if (!(other instanceof PersistentStringEnum)) {
            throw new RuntimeException("Cannot compare PersistentStringEnum with " + other.getClass().getName());
        }
        return ((String) getEnumCode()).compareTo((String) other.getEnumCode());
    }

    protected SingleColumnType getHibernateType() {
        return StringType.INSTANCE;
    }

    public String getCode() {
        return (String) this.enumCode;
    }
}