package edu.sjtu.party.dao.hibernate;

/**
 * Created by marstone on 10/16/15.
 */
import org.hibernate.type.IntegerType;
import org.hibernate.type.SingleColumnType;

public class PersistentIntegerEnum extends PersistentEnum {

    protected PersistentIntegerEnum() {
    }

    protected PersistentIntegerEnum(int code, String name) {
        super(new Integer(code), name);
    }

    @Override
    public int compareTo(PersistentEnum other) {
        if (other == null) {
            throw new NullPointerException();
        }
        if (other == this) {
            return 0;
        }
        if (!(other instanceof PersistentIntegerEnum)) {
            throw new RuntimeException("Cannot compare PersistentIntegerEnum with " + other.getClass().getName());
        }
        return ((Integer) getEnumCode()).compareTo((Integer) other.getEnumCode());
    }

    protected SingleColumnType getHibernateType() {
        return IntegerType.INSTANCE;
    }

    public int getCode() {
        return (int) this.enumCode;
    }

}