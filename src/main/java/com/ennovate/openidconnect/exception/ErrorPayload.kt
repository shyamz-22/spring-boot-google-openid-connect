package com.ennovate.openidconnect.exception

data class ErrorPayload(val error: String,
                        var error_description: String? = null)