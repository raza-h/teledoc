package tele.doc.project.systems.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import tele.doc.project.domain.MedicalHistoryRecord;
import tele.doc.project.domain.Patient;
import tele.doc.project.domain.Visitor;
import tele.doc.project.repositories.DoctorRepository;
import tele.doc.project.repositories.MedicalHistoryRecordRepository;
import tele.doc.project.repositories.PatientRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


public class MedicalHistoryUploader {

    private final PatientRepository pr;
    private final MedicalHistoryRecordRepository mhrr;

    public MedicalHistoryUploader(PatientRepository pr, MedicalHistoryRecordRepository mhrr)
    {
        this.pr = pr;
        this.mhrr = mhrr;
    }
    public boolean upload(String date, MultipartFile f, MedicalHistoryRecord mhr) throws IOException, ParseException {
        if (f == null || f.isEmpty() || date.equals("")  || mhr.getName().equals("") || mhr.getMedication().equals("") || mhr.getDuration().equals(""))
        {
            Visitor.errorMessage="Field left empty";
            return false;
        }

        byte[] bytes = f.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(bytes);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(date);
        Patient p = pr.findByUsername(Visitor.currentUser);
        MedicalHistoryRecord mhr1 = new MedicalHistoryRecord();
        mhr1.setName(mhr.getName());
        mhr1.setDuration(mhr.getDuration());
        mhr1.setMedication(mhr.getMedication());
        mhr1.setDateDiagnosed(parsed);
        mhr1.setFile(bytes);
        mhr1.setPatient(p);
        mhrr.save(mhr1);
        return true;
    }
}
