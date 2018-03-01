/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2106913
 */
@Service
public interface PatientsRepository extends JpaRepository<Paciente, PacienteId> {
    
    //@Query("SELECT p.id, p.tipo_id,p.nombre,p.fecha_nacimiento FROM Paciente AS p WHERE (SELECT COUNT(*) FROM Consulta WHERE Consulta.PACIENTES_id = p.id)>=?1")
    @Query("SELECT p FROM Paciente AS p WHERE p.consultas.size>=?1")
    public List<Paciente> findPacienteByNConsults(int N);
}
