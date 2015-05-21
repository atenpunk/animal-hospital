/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.dao.BreedDao;
import co.th.aten.hospital.dao.TypeDao;
import co.th.aten.hospital.model.BreedModel;
import co.th.aten.hospital.model.TypeModel;
import java.util.List;

/**
 *
 * @author Atenpunk
 */
public interface TypeManager {

    public List<TypeModel> getTypeList();

    public TypeDao getTypeDao();

    public void setTypeDao(TypeDao typeDao);
}
