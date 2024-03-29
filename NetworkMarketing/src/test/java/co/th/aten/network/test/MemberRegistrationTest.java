package co.th.aten.network.test;


import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
//        return ShrinkWrap.create(WebArchive.class, "test.war")
//                .addClasses(Member.class, MemberRegistration.class, MemberRepository.class, MemberRepositoryProducer.class)
//                // addAsManifestResource is incorrectly targeting /META-INF
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    	return null;
    }

//    @Inject
//    MemberRegistration memberRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
//        Member newMember = memberRegistration.getNewMember();
//        newMember.setName("Jane Doe");
//        newMember.setEmail("jane@mailinator.com");
//        newMember.setPhoneNumber("2125551234");
//        memberRegistration.register();
//        assertNotNull(newMember.getId());
//        log.info(newMember.getName() + " was persisted with id " + newMember.getId());
    }

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
