/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.service;

import co.th.aten.hospital.model.PetModel;
import co.th.aten.hospital.model.UserModel;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Aten
 */
public class SessionManagerBean implements Serializable, SessionManager {

    private final Log logger = LogFactory.getLog(getClass());
    private UserModel user;
    private PetModel petModel;

    public SessionManagerBean() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public PetModel getPetModel() {
        return petModel;
    }

    public void setPetModel(PetModel petModel) {
        this.petModel = petModel;
    }


}
