package com.example.prehack.service;

public interface SendMailService {
    /**
     * Email the specified recipient with the given subject and content.
     *
     * @param email The email address of the recipient.
     * @param theme The subject or title of the email.
     * @param text The content or body of the email.
     */
    void sendEmail(String email, String theme, String text);
}
