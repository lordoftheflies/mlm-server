/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dd.mlm.topn.auth.service;

/**
 *
 * @author lordoftheflies
 */
interface UaaMailSender {
    
    void sendForgottenPasswordEmail(String email, String userName, String applicationName, String organizationName, String passwordResetLink, String locale);
    
    void sendRegistrationActivationEmail(String inviter, String email, String userName, String applicationName, String organizationName, String registrationActivationLink, String androidInstallerLink, String locale);
}
