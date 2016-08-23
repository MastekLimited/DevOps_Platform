package com.dev.ops.uuid.service.factory.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.uuid.service.core.CheckDigitGenerator;
import com.dev.ops.uuid.service.dao.UUIDDAO;
import com.dev.ops.uuid.service.domain.EntityType;

@Component(value = "EMPLOYEE")
public class EmployeeUUIDGenerator implements UUIDGenerator {

	@Value("${unique.system.id}")
	private String systemId;

	@Autowired
	@Qualifier("luhnDigitGenerator")
	private CheckDigitGenerator checkDigitGenerator;

	@Autowired
	private UUIDDAO uuidDAO;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(EmployeeUUIDGenerator.class);

	@Override
	public String generateUUID() {
		final String uuidSequenceValue = this.getUUIDSequenceValue();
		DIAGNOSTIC_LOGGER.debug(EmployeeUUIDGenerator.class.getName() + " sequence value from database - " + uuidSequenceValue);
		final String checkDigit = this.checkDigitGenerator.generateCheckDigit(uuidSequenceValue + this.systemId);
		final String uuid = uuidSequenceValue.concat(this.systemId).concat(checkDigit);
		DIAGNOSTIC_LOGGER.debug(EmployeeUUIDGenerator.class.getName() + " Generated UUID:<" + uuid + ">");
		return EntityType.EMPLOYEE.getPrefix() + uuid;
	}

	private String getUUIDSequenceValue() {
		String uuidSequenceValue = this.uuidDAO.getUUIDSequenceValue("employee_number_sequence");

		if(uuidSequenceValue.length() < 9) {
			uuidSequenceValue = this.getNineDigits(uuidSequenceValue);
		}
		return uuidSequenceValue;
	}

	private String getNineDigits(String uuidSequenceValue) {
		final int sequenceValueLength = uuidSequenceValue.length();
		for(int digitsCounter = 0; digitsCounter < 9 - sequenceValueLength; digitsCounter++) {
			uuidSequenceValue = "0".concat(uuidSequenceValue);
		}
		return uuidSequenceValue;
	}
}