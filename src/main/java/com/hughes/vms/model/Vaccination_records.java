package com.hughes.vms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Vaccination_records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "vaccine_id")
    private Long vaccineId;

    @Column(name = "vaccination_date")
    private java.sql.Timestamp vaccinationDate;

    @Column(name = "vaccination_center_id")
    private Long vaccinationCenterId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(Long vaccineId) {
		this.vaccineId = vaccineId;
	}

	public java.sql.Timestamp getVaccinationDate() {
		return vaccinationDate;
	}

	public void setVaccinationDate(java.sql.Timestamp vaccinationDate) {
		this.vaccinationDate = vaccinationDate;
	}

	public Long getVaccinationCenterId() {
		return vaccinationCenterId;
	}

	public void setVaccinationCenterId(Long vaccinationCenterId) {
		this.vaccinationCenterId = vaccinationCenterId;
	}
}
