package tele.doc.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tele.doc.project.domain.MedicalHistoryRecord;
import tele.doc.project.repositories.MedicalHistoryRecordRepository;
import tele.doc.project.repositories.PatientRepository;
import tele.doc.project.systems.others.MedicalHistoryUploader;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class PatientController {
    private final PatientRepository pr;
    private final MedicalHistoryRecordRepository mhrr;
    MedicalHistoryUploader mhu;

    PatientController(PatientRepository pr, MedicalHistoryRecordRepository mhrr)
    {
        this.pr = pr;
        this.mhrr = mhrr;
    }


    @PostMapping(value = "/patient-add-history", consumes = {"*/*"})
    public String addHistory(@ModelAttribute("medicalhistoryrecord") MedicalHistoryRecord mhr, @RequestParam("date") String date, @RequestParam("filer") MultipartFile f) throws IOException, ParseException {
        mhu = new MedicalHistoryUploader(pr, mhrr);
        if(!mhu.upload(date, f, mhr))
        {
            return "redirect:/problem";
        }
        return "patient/uploadHistory";
    }
}

