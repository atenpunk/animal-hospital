/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.TypeDao;
import co.th.aten.hospital.model.TypeModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public class TypeManagerBean implements TypeManager {

    private TypeDao typeDao;

    public List<TypeModel> getTypeList(){
        return typeDao.getTypeList();
    }

    public TypeDao getTypeDao() {
        return typeDao;
    }

    public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }


}
