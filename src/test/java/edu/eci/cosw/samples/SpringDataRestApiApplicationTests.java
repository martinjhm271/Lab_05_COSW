package edu.eci.cosw.samples;


import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import edu.eci.cosw.samples.persistence.PatientsRepository;
import edu.eci.cosw.samples.services.PatientServices;
import edu.eci.cosw.samples.services.ServicesException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.runtime.Debug.id;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestApiApplication.class)
@WebAppConfiguration
@ActiveProfiles("application-test.properties")
@TestPropertySource("/application-test.properties")
public class SpringDataRestApiApplicationTests {
    
    @Autowired
    PatientServices services;
    
    @Autowired
    PatientsRepository pr;
        
	//| Método | Clase de equivalencia        | Prueba           | 
//|-----| ------------- |:-------------:| 
//| getPatient(id,tipoid)| Consulta a paciente que existe      | Registrar un paciente, consultarlo a través de los servcios, y rectificar que sea el mismo | 
//| getPatient(id,tipoid)| Consulta a paciente que no existe      | Consultar a través de los servicios un paciente no registrado, y esperar que se produzca el error | 
//| topPatients(N)| No existen pacientes con N o más consultas      | Registrar un paciente con sólo 1 consulta. Probar usando N=2 como parámetro y esperar una lista vacía.     | 
//| topPatients(N)| Registrar 3 pacientes. Uno sin consultas, otro con una, y el último con dos consultas. Probar usando N=1  y esperar una lista con los dos pacientes correspondientes.| centered      | 

	@Test
	public void contextLoads() {
	}
        
        @Test
        public void patientLoadTest1(){
            Paciente p = new Paciente(new PacienteId(1026585665, "cc"), "Jose Medina", new Date(2018));
            pr.save(p);
            try {
                assertEquals("Ambos usuarios son iguales", p.getId().getId(), services.getPatient(1026585665, "cc").getId().getId());
            } catch (ServicesException ex) {
                Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Test
        public void patientLoadTest2(){
        try {
            services.getPatient(-1, "cc");
        } catch (ServicesException ex) {
            Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals("No existe este usuario,se espera un error!!",ServicesException.class,ex);
        }
        }
        
        @Test
        public void topPatientsLoadTest1(){
            Consulta c =new Consulta(new Date(2018), "estoy en excelente forma");
            Set<Consulta> con=new HashSet<>();con.add(c);
            Paciente p = new Paciente(new PacienteId(1026585666, "cc"), "Marin Test", new Date(2018),con);
            pr.save(p);
            try {
                assertEquals("No existen pacientes con mas de 2 consultas", 0, services.topPatients(2).size());
            } catch (ServicesException ex) {
                Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Test
        public void topPatientsLoadTest2(){
            Paciente p1 = new Paciente(new PacienteId(1026585669, "cc"), "Soy Test", new Date(2018));
            pr.save(p1);
            
            Consulta c1 =new Consulta(new Date(2018), "estoy en excelente forma");
            Set<Consulta> con1=new HashSet<>();con1.add(c1);
            Paciente p2 = new Paciente(new PacienteId(1026585666, "cc"), "Marin Test", new Date(2018),con1);
            pr.save(p2);
            
            Consulta c2 =new Consulta(new Date(2018), "estoy en excelente forma");
            Consulta c3 =new Consulta(new Date(2018), "estoy en excelente forma");
            Set<Consulta> con2=new HashSet<>();con2.add(c2);con2.add(c3);
            Paciente p3 = new Paciente(new PacienteId(1026585645, "cc"), "Hernandez Test", new Date(2018),con2);
            pr.save(p3);
            
            try {
                assertEquals("Existen solo 2 pacientes con mas de 1 consultas", 2, services.topPatients(1).size());
            } catch (ServicesException ex) {
                Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
  

}
