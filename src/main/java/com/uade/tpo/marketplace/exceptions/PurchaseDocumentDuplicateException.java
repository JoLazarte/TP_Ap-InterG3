package com.uade.tpo.marketplace.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La categoria que se intenta agregar esta duplicada")
public class PurchaseDocumentDuplicateException extends Exception {
}
