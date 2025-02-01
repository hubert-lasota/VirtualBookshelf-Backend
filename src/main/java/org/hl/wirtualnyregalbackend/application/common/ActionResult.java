package org.hl.wirtualnyregalbackend.application.common;


public record ActionResult(boolean success, ApiError error) {
}
