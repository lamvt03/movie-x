package com.moviex.utils;

import jakarta.servlet.http.HttpSession;

public class AlertUtils {
  
  private static final String TOAST_ICON_FIELD = "toastIcon";
  private static final String TOAST_MESSAGE_FIELD = "toastMessage";
  
  private static final String DIALOG_ICON_FIELD = "dialogIcon";
  private static final String DIALOG_TITLE_FIELD = "dialogTitle";
  private static final String DIALOG_MESSAGE_FIELD = "dialogMessage";
  
  private static final String ICON_SUCCESS = "success";
  private static final String ICON_WARNING = "warning";
  private static final String ICON_ERROR = "error";
  private static final String ICON_INFO = "info";
  
  public static void prepareToastSuccessMessage(HttpSession session, String message) {
    session.setAttribute(TOAST_ICON_FIELD, ICON_SUCCESS);
    session.setAttribute(TOAST_MESSAGE_FIELD, message);
  }
  
  public static void prepareToastWarningMessage(HttpSession session, String message) {
    session.setAttribute(TOAST_ICON_FIELD, ICON_WARNING);
    session.setAttribute(TOAST_MESSAGE_FIELD, message);
  }
  
  public static void prepareToastErrorMessage(HttpSession session, String message) {
    session.setAttribute(TOAST_ICON_FIELD, ICON_ERROR);
    session.setAttribute(TOAST_MESSAGE_FIELD, message);
  }
  
  public static void prepareDialogSuccessMessage(HttpSession session, String title, String message) {
    session.setAttribute(DIALOG_ICON_FIELD, ICON_SUCCESS);
    session.setAttribute(DIALOG_TITLE_FIELD, title);
    session.setAttribute(DIALOG_MESSAGE_FIELD, message);
  }
  
  public static void prepareDialogWarningMessage(HttpSession session, String title, String message) {
    session.setAttribute(DIALOG_ICON_FIELD, ICON_WARNING);
    session.setAttribute(DIALOG_TITLE_FIELD, title);
    session.setAttribute(DIALOG_MESSAGE_FIELD, message);
  }
  
  public static void prepareDialogErrorMessage(HttpSession session, String title, String message) {
    session.setAttribute(DIALOG_ICON_FIELD, ICON_ERROR);
    session.setAttribute(DIALOG_TITLE_FIELD, title);
    session.setAttribute(DIALOG_MESSAGE_FIELD, message);
  }
  
  public static void prepareDialogInfoMessage(HttpSession session, String title, String message) {
    session.setAttribute(DIALOG_ICON_FIELD, ICON_INFO);
    session.setAttribute(DIALOG_TITLE_FIELD, title);
    session.setAttribute(DIALOG_MESSAGE_FIELD, message);
  }
}
