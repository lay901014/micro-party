package edu.sjtu.party.dao.convert;

import edu.sjtu.dao.convert.QueryParameterConvert;

public class ChildBranchCodeLikeConvert implements QueryParameterConvert {

    public final static ChildBranchCodeLikeConvert INSTANCE = new ChildBranchCodeLikeConvert();

    @Override
    public Object convert(Object src) {
        if (src == null) {
            return "__";
        }
        //转义输入中的_和%
        String value = src.toString().replaceAll("[_%]", "\\\\$0");
        if (value == null || value.length() == 0) {
            return "__";
        }
        return value + "__";
    }


}
