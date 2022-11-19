package tele.doc.project.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tele.doc.project.domain.*;
import tele.doc.project.repositories.*;

import javax.print.Doc;

@Component
public class bootStrapData implements CommandLineRunner {

    private final AdminRepository ar;
    private final AppointmentRepository apr;
    private final DoctorRepository dr;
    private final MedicalHistoryRecordRepository mhrr;
    private final PatientRepository pr;
    private final ReviewRepository rr;
    private final SuperAdminRepository sar;
    private final PrescriptionRepository prer;
    private final EducationRecordRepository edr;
    private final PaymentRepository payr;

    public bootStrapData(AdminRepository ar, AppointmentRepository apr, DoctorRepository dr, MedicalHistoryRecordRepository mhrr, PatientRepository pr, ReviewRepository rr, SuperAdminRepository sar, PrescriptionRepository prer, EducationRecordRepository edr, PaymentRepository payr) {
        this.ar = ar;
        this.apr = apr;
        this.dr = dr;
        this.mhrr = mhrr;
        this.pr = pr;
        this.rr = rr;
        this.sar = sar;
        this.prer = prer;
        this.edr = edr;
        this.payr = payr;
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
