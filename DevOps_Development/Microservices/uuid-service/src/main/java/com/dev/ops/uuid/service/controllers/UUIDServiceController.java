package com.dev.ops.uuid.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.common.thread.local.ContextThreadLocal;
import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.uuid.service.domain.EntityType;
import com.dev.ops.uuid.service.services.UUIDService;

@Controller
@RequestMapping("/uuid")
public class UUIDServiceController {

	@Autowired
	private UUIDService uuidService;

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(UUIDServiceController.class);

	@RequestMapping(value = "/{entityType}", method = RequestMethod.GET)
	@ResponseBody
	public String getUUID(@PathVariable final EntityType entityType, @RequestHeader("context") final String context) throws DefaultWrappedException {
		ContextThreadLocal.set(ContextInfo.toContextInfo(context));
		final String uuid = this.uuidService.generateUUID(entityType);
		DIAGNOSTIC_LOGGER.info("The UUID generated for entit type " + entityType + " is : " + uuid);
		return uuid;
	}
}