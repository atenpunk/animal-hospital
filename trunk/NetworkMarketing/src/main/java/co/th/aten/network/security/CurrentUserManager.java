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

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MasterConfigkey;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.StringUtil;


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
	private UserLogin currentUser;

	private double moneyUsd;

	@PostConstruct
	public void init(){
		try{
			MasterConfigkey config = em.find(MasterConfigkey.class, "MONEY_USD");
			moneyUsd = 30;
			if(config!=null){
				moneyUsd = Double.parseDouble(StringUtil.n2b(config.getValue01()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

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

	public double getMoneyUsd() {
		return moneyUsd;
	}

	public void setMoneyUsd(double moneyUsd) {
		this.moneyUsd = moneyUsd;
	}    

}
