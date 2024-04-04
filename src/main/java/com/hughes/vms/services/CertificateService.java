
package com.hughes.vms.services;

import com.hughes.vms.model.Patients;
import com.hughes.vms.repository.PatientsRepository;
import com.hughes.vms.model.Vaccination_records;
import com.hughes.vms.repository.VaccinationRecordsRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.lowagie.text.Image;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
// Other necessary imports

@Service
public class CertificateService {

	@Autowired
	private VaccinationRecordsRepository vaccinationRecordsRepository;

	@Autowired
	private PatientsRepository patientRepository;

	public Vaccination_records getVaccinationData(Long patientId) {
		return vaccinationRecordsRepository.findByPatientId(patientId);
	}

	public byte[] generateCertificatePDF(Vaccination_records vaccinationData) throws CertificateGenerationException {
		// Validate input data
		if (vaccinationData == null || vaccinationData.getPatientId() == null
				|| vaccinationData.getVaccinationDate() == null || vaccinationData.getVaccineId() == null) {
			throw new IllegalArgumentException("Invalid vaccination data provided.");
		}

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			Document document = new Document();
			PdfWriter.getInstance(document, outputStream);

			document.open();

			// Retrieve patient information from the repository
			Patients patient = patientRepository.findById(vaccinationData.getPatientId()).orElse(null);
			if (patient == null) {
				throw new IllegalArgumentException("Patient not found.");
			}

			Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
			Font semiboldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
			Image govLogo = Image.getInstance(new ClassPathResource("/static/logo.jpeg").getURL());
			govLogo.setAlignment(Element.ALIGN_CENTER);
			govLogo.scaleToFit(100f, 100f);
			document.add(govLogo);

			// Add title with bold font
			Paragraph title = new Paragraph("Certificate for COVID-19 Vaccination", boldFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			
			Paragraph subtitle = new Paragraph("Ministry of Health and Family Welfare", semiboldFont);
			subtitle.setAlignment(Element.ALIGN_CENTER);
			document.add(subtitle);
			
			Paragraph subheading = new Paragraph("Government of India", semiboldFont);
			subheading.setAlignment(Element.ALIGN_CENTER);
			document.add(subheading);

			// Add patient information
			Paragraph patientName = new Paragraph(
					"Patient Name: " + patient.getFirstName() + " " + patient.getLastName());
			document.add(patientName);
			Paragraph patientDob = new Paragraph("Date Of Birth: " + patient.getDob());
			document.add(patientDob);

			// Calculate age from DOB
			Date dob = patient.getDob();
			Instant instant = dob.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDate localDate = instant.atZone(zoneId).toLocalDate();

			LocalDate now = LocalDate.now();
			int age = Period.between(localDate, now).getYears();

			Paragraph patientAge = new Paragraph("Age: " + age);
			document.add(patientAge);
			Paragraph patientGender = new Paragraph("Gender: " + patient.getGender());
			document.add(patientGender);
			Paragraph patientAddress = new Paragraph("Address: " + patient.getAddress());
			document.add(patientAddress);

			// Add vaccination details
			Paragraph vaccinationInfo = new Paragraph("Vaccination Date: " + vaccinationData.getVaccinationDate());
			document.add(vaccinationInfo);
			Paragraph vaccineType = new Paragraph("Type of Vaccine: " + vaccinationData.getVaccineId());
			document.add(vaccineType);

			Paragraph congrats = new Paragraph("Congratulations! You are now vaccinated.");
			congrats.setAlignment(Element.ALIGN_CENTER);
			document.add(congrats);

			// Add a QR code for verification (sample implementation)
			String qrCodeData = "Vaccination ID: " + vaccinationData.getRecordId();

			byte[] qrCodeImage = generateQRCode(qrCodeData, 200, 200);
			Image image = Image.getInstance(qrCodeImage);
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);
			Paragraph qrtitle = new Paragraph("Scan this QR code to get your vaccinated id ", semiboldFont);
			qrtitle.setAlignment(Element.ALIGN_CENTER);
			document.add(qrtitle);

			document.close();

			return outputStream.toByteArray();
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
			throw new CertificateGenerationException("Error occurred while generating certificate.", e);
		}
	}

	// Helper method to generate a QR code image
//    private Image generateQRCodeImage(String data) throws DocumentException, IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        BitMatrix matrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
//        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
//        Image qrCodeImage = Image.getInstance(outputStream.toByteArray());
//        return qrCodeImage;
//    }
	public byte[] generateQRCode(String data, int width, int height) throws CertificateGenerationException {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

			// Convert BitMatrix to byte array
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

			return outputStream.toByteArray();
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			throw new CertificateGenerationException("Error occurred while generating QR code.", e);
		}
	}
}
