package com.dev.ops.uuid.service.core;

/**
 * Generates check digit string sequence based on input sequence.
 */
public interface CheckDigitGenerator {

	String generateCheckDigit(String sequence);
}
