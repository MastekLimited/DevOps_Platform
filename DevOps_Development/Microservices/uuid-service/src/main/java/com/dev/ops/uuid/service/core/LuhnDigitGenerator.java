package com.dev.ops.uuid.service.core;

import org.springframework.stereotype.Component;

/**
 * Generates last character (of 12 digit sequence) of universal unique identifier.
 */
@Component
public class LuhnDigitGenerator implements CheckDigitGenerator {

	@Override
	public String generateCheckDigit(final String sequence) {
		return String.valueOf(this.generateChecksumDigit(sequence));
	}

	public int generateChecksumDigit(final String sequence) {
		final int[] digits = this.getDigitsFromString(sequence);

		int sum = 0;
		final int length = digits.length;

		for(int counter = 0; counter < length; counter++) {
			// get digits in reverse order
			int digit = digits[length - counter - 1];
			// multiply every 2nd number with 2
			if(counter % 2 == 1) {
				digit *= 2;
			}
			sum += digit > 9 ? digit - 9 : digit;
		}
		return sum % 10;
	}

	private int[] getDigitsFromString(final String sequence) {
		final char[] charDigits = sequence.toCharArray();
		final int[] digits = new int[charDigits.length];

		for(int counter = 0; counter < charDigits.length; counter++) {
			digits[counter] = charDigits[counter] - '0';
		}
		return digits;
	}
}
