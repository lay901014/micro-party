package edu.sjtu.party.dao.convert;

import edu.sjtu.dao.convert.LikeConvert;
import edu.sjtu.dao.convert.QueryParameterConvert;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lay
 * @create: 2020/02/22 14:10
 **/
public class CompositeLikeQueryConvert implements QueryParameterConvert {

    private LikeConvert likeConvert = new LikeConvert();
    private int paraNum;

    public CompositeLikeQueryConvert(int paraNum) {
        this.paraNum = paraNum;
    }

    @Override
    public Object convert(Object src) {
        List<Object> ret = new ArrayList<>();
        if(src.getClass().isArray()){
            for(int i=0;i<paraNum;i++){
                ret.add(likeConvert.convert(((Object[])src)[i]));
            }
        }else{
            for(int i=0;i<paraNum;i++){
                ret.add(likeConvert.convert(((String)src)));
            }
        }
        Object[] result = new Object[ret.size()];
        ret.toArray(result);
        return  result;
    }
}
