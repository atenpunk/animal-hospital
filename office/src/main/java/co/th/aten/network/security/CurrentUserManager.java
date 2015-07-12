/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.th.aten.network.security;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;


/**
 * Exposes the currently logged in user
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
@SessionScoped
@Named("currentUserManager")
public class CurrentUserManager implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7016685883035435434L;
	@Inject
	Logger log;
	
	@Inject
	@DBDefault
	private EntityManager em;
	
	private String check;
	private boolean check1;
	private boolean check2;
	private boolean check3;
	
	private UserLogin currentUser;

    @Produces
    @Authenticated
    @Named("currentUser")
    public UserLogin getCurrentAccount() {
        return currentUser;
    }

    // Injecting HttpServletRequest instead of HttpSession as the latter conflicts with a Weld bean on GlassFish 3.0.1
    public void onLogin(@Observes @Authenticated UserLogin user, HttpServletRequest request) {
        currentUser = user;
        log.infov("set current UserLogin,userId={0},loginName={1},userName={2}",currentUser.getUserId(),currentUser.getLoginName(),currentUser.getUserName());
        
        checkPermission(currentUser.getUserId());
        // reward authenticated users with a longer session
        // default is kept short to prevent search engines from driving up # of sessions
       // request.getSession().setMaxInactiveInterval(3600);
    }
    
	public void checkPermission(Integer id){
		
		log.info("--------------- id "+id);
		check1 = true;
		check2 = false;
		check3 = false;
//		try {
//			String qr = " select ur.role_id " +
//					" from user u " +
//					" left join user_role ur on(ur.user_id = u.user_id) " +
//					" left join role ro on(ro.role_id = ur.role_id) " +
//					" where u.user_id = "+ id ;
//			
//			check = em.createNativeQuery(qr).getSingleResult().toString();
//			log.info("--------------- check "+check);
//			if(check.equals("1")){
//				
//				check1 = true;
//				check2 = false; // test
//				check3 = false;
//				
//			}else if(check.equals("2")){
//				
//				check1 = false;
//				check2 = true; // test
//				check3 = false;
//				
//			}else if(check.equals("3")){
//				
//				check1 = false;
//				check2 = false; // test
//				check3 = true;
//			}
//			
//			
//			log.info(check1);
//			log.info(check2);
//			log.info(check3);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
		
	}

	public boolean isCheck1() {
		return check1;
	}

	public void setCheck1(boolean check1) {
		this.check1 = check1;
	}

	public boolean isCheck2() {
		return check2;
	}

	public void setCheck2(boolean check2) {
		this.check2 = check2;
	}

	public boolean isCheck3() {
		return check3;
	}

	public void setCheck3(boolean check3) {
		this.check3 = check3;
	}
	
	
    
}
