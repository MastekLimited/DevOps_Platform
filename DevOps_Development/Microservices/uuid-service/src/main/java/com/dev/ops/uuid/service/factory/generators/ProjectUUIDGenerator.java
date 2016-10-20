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

@Component(value = "PROJECT")
public class ProjectUUIDGenerator implements UUIDGenerator {

	@Value("${unique.system.id}")
	private String systemId;

	@Autowired
	@Qualifier("luhnDigitGenerator")
	private CheckDigitGenerator checkDigitGenerator;

	@Autowired
	private UUIDDAO uuidDAO;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(ProjectUUIDGenerator.class);

	@Override
	public String generateUUID() {
		final String uuidSequenceValue = this.getUUIDSequenceValue();
		DIAGNOSTIC_LOGGER.debug(ProjectUUIDGenerator.class.getName() + " sequence value from database - " + uuidSequenceValue);
		final String checkDigit = this.checkDigitGenerator.generateCheckDigit(uuidSequenceValue + this.systemId);
		final String uuid = uuidSequenceValue.concat(this.systemId).concat(checkDigit);
		DIAGNOSTIC_LOGGER.debug(ProjectUUIDGenerator.class.getName() + " Generated UUID:<" + uuid + ">");
		return EntityType.PROJECT.getPrefix() + uuid;
	}

	private String getUUIDSequenceValue() {
		String uuidSequenceValue = this.uuidDAO.getUUIDSequenceValue("project_number_sequence");

		if(uuidSequenceValue.length() < 5) {
			uuidSequenceValue = this.getFiveDigits(uuidSequenceValue);
		}
		return uuidSequenceValue;
	}

	private String getFiveDigits(String uuidSequenceValue) {
		final int sequenceValueLength = uuidSequenceValue.length();
		for(int digitsCounter = 0; digitsCounter < 5 - sequenceValueLength; digitsCounter++) {
			uuidSequenceValue = "0".concat(uuidSequenceValue);
		}
		return uuidSequenceValue;
	}
}