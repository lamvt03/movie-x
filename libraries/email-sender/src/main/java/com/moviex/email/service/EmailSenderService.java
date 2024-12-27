package com.moviex.email.service;

import com.moviex.email.exception.SendEmailException;
import com.moviex.email.model.EmailMessage;

public interface EmailSenderService <T extends EmailMessage> {
  
  void sendEmail(T message) throws SendEmailException;
}
