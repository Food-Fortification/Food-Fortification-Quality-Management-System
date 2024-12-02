package org.path.iam.utils;

import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.CredentialRepresentation;

import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {

    @Test
    void testCreatePasswordCredentials() {
        String password = "mySecurePassword";

        CredentialRepresentation credential = Credentials.createPasswordCredentials(password);

        assertNotNull(credential, "The credential should not be null");
        assertEquals(CredentialRepresentation.PASSWORD, credential.getType(), "The credential type should be PASSWORD");
        assertEquals(password, credential.getValue(), "The credential value should match the input password");
        assertFalse(credential.isTemporary(), "The credential should not be temporary");
    }

    @Test
    void testCreatePasswordCredentialsWithNullPassword() {
        String password = null;

        CredentialRepresentation credential = Credentials.createPasswordCredentials(password);

        assertNotNull(credential, "The credential should not be null");
        assertEquals(CredentialRepresentation.PASSWORD, credential.getType(), "The credential type should be PASSWORD");
        assertNull(credential.getValue(), "The credential value should be null when input password is null");
        assertFalse(credential.isTemporary(), "The credential should not be temporary");
    }

    @Test
    void testCreatePasswordCredentialsWithEmptyPassword() {
        String password = "";

        CredentialRepresentation credential = Credentials.createPasswordCredentials(password);

        assertNotNull(credential, "The credential should not be null");
        assertEquals(CredentialRepresentation.PASSWORD, credential.getType(), "The credential type should be PASSWORD");
        assertEquals(password, credential.getValue(), "The credential value should be an empty string when input password is empty");
        assertFalse(credential.isTemporary(), "The credential should not be temporary");
    }
}
