package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tele.doc.project.domain.Doctor;
import tele.doc.project.domain.Status;
import tele.doc.project.repositories.DoctorRepository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class SearchSystem {
    @Autowired
    DoctorRepository dr;
    public Set<Doctor> filterDoctorList(String str)
    {
        Set<Doctor> allDoctors = dr.findByStatus(Status.approved);
        Set<Doctor> searched = new HashSet<>();
        Iterator<Doctor> it = allDoctors.iterator();

        while(it.hasNext())
        {
            Doctor d = it.next();
            if(d.getName().contains(str))
            {
                searched.add(d);
            }
        }

        return searched;
    }
}
