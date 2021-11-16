package edu.sjtu.party.dao.convert;

import edu.sjtu.dao.convert.QueryParameterConvert;

public class StartWithConvert implements QueryParameterConvert {

    public final static StartWithConvert INSTANCE = new StartWithConvert();

    @Override
    public Object convert(Object src) {
        if (src == null) {
            return "%";
        }
        //转义输入中的_和%
        String value = src.toString().replaceAll("[_%]", "\\\\$0");
        if (value == null || value.length() == 0) {
            return "%";
        }
        return value + '%';
    }


}
