package com.hughes.vms.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hughes.vms.model.Vaccination_records;
import com.hughes.vms.services.CertificateGenerationException;
import com.hughes.vms.services.CertificateService;

@RestController
@RequestMapping("/api")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/{patientId}/download")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long patientId) {
        try {
            // Fetch vaccination data for the patient from the service
            Vaccination_records vaccinationData = certificateService.getVaccinationData(patientId);

            // Generate PDF certificate using vaccination data
            byte[] certificateBytes = certificateService.generateCertificatePDF(vaccinationData);

            // Prepare response with PDF content
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "vaccination_certificate.pdf");
            headers.setContentLength(certificateBytes.length);

            return new ResponseEntity<>(certificateBytes, headers, HttpStatus.OK);
        } catch (CertificateGenerationException e) {
            e.printStackTrace();
            // Handle certificate generation exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
