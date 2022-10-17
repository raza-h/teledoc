package tele.doc.project.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tele.doc.project.repositories.*;

@Component
public class bootStrapData implements CommandLineRunner {

    private final AdminRepository ar;
    private final AppointmentRepository apr;
    private final DoctorRepository dr;
    private final EducationRepository er;
    private final MedicalHistoryRecordRepository mhrr;
    private final PatientRepository pr;
    private final ReviewRepository rr;
    private final SuperAdminRepository sar;

    public bootStrapData(AdminRepository ar, AppointmentRepository apr, DoctorRepository dr, EducationRepository er, MedicalHistoryRecordRepository mhrr, PatientRepository pr, ReviewRepository rr, SuperAdminRepository sar) {
        this.ar = ar;
        this.apr = apr;
        this.dr = dr;
        this.er = er;
        this.mhrr = mhrr;
        this.pr = pr;
        this.rr = rr;
        this.sar = sar;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
