package nyla.solutions.formInjection;

import java.rmi.RemoteException;
import java.util.Collection;

import nyla.solutions.formInjection.data.Application;
import nyla.solutions.formInjection.data.Form;
import nyla.solutions.formInjection.data.FormStatus;
import nyla.solutions.formInjection.data.FormType;
import nyla.solutions.formInjection.data.Questionaire;
import nyla.solutions.global.exception.NoDataFoundException;
import nyla.solutions.global.patterns.Delegate;
import nyla.solutions.global.patterns.servicefactory.ServiceFactory;
import nyla.solutions.global.security.data.SecurityCredential;


/**
 * 
 * <pre>
 * <pre>FormDelegate</b> is a Business Delegate for the Form service object. </pre>
 * </pre> 
 * @author Gregory Green
 * @version 1.0
 */
public class FormDelegate extends Delegate
{
   /**
    * Constructor for FormDelegate initializes internal 
    * data settings.
    * @param aUser
    */
   public FormDelegate(SecurityCredential aUser)
   {
      super(aUser);
   }//--------------------------------------------
   /**
    * The originator can request to edit submitted forms that have not 
    * been completed, approved, rejected, or not file in ESF. The forms 
    * cannot be edited if it has been submitted to ESF.
    * 
    * Users will be able to create a new version of a report that
    *  has been filed in ESF. The new version of the form is a copy 
    * of the original form. The answers and questions from the original 
    * form are transfers to the new version. Only the originator can renew 
    * a completed form.
    * @param aForm the form the renew
    * @return the renewed form
    */
   public Form retrieveNewForm(String formTypeCode)
   throws Exception
   {
    
      
      return getFormService().retrieveNewForm(formTypeCode);
   }//--------------------------------------------
   /**
    * @param aForm
    * @throws Exception
    * @throws RemoteException
    */
   public Form applyBusinessRules(Form aForm)
   throws Exception
   {      
      
      return getFormService().applyBusinessRules(aForm);
   }//--------------------------------------------
   /**
    * @param aFormTypePK
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public Questionaire constructQuestioniare(int aFormTypePK)
   throws Exception
   {
      return getFormService().constructQuestioniare(aFormTypePK);
   }//--------------------------------------------
   /**
    * 
    * @param form
    * @return
    */
   public Form save(Form form)
   throws Exception
   {
      if(form.isNew())
      {
         return getFormService().create(form);
      }
      else
      {
         return getFormService().edit(form);
      }
   }//--------------------------------------------
   /**
    * @param aFormTypeName
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public Questionaire constructQuestioniare(String aFormTypeName)
    throws Exception
   {
      return getFormService().constructQuestioniare(aFormTypeName);
   }
   /**
    * @param aForm
    * @return
    * @throws Exception
    * @throws RemoteException
    */
   public Form create(Form aForm) 
   throws Exception, RemoteException
   {
      
      //TODO: aForm.setCreateUserID(this.getUser().getLoginID();
      aForm.setAccessUser(this.getUser());
      return getFormService().create(aForm);
   }//--------------------------------------------
   /**
    * @param aForm
    * @return
    * @throws Exception
    * @throws RemoteException
    */
   public Form edit(Form aForm) throws Exception, RemoteException
   {
      return getFormService().edit(aForm);
   }//--------------------------------------------
   
   /**
    * @param aForm
    * @return edited forms
    * @throws Exception
    * @throws RemoteException
    */
   public Application[] edits(Application[] aForms) throws Exception, RemoteException
   {  
      return getFormService().edits(aForms, this.getUser());
   }//--------------------------------------------
   /**
    * @param aForm
    * @return
    * @throws Exception
    * @throws RemoteException
    */
   public Form removeForm(Form aForm)
   throws Exception, RemoteException
   {
      
      return getFormService().removeForm(aForm);
   }//--------------------------------------------
   /**
    * @param aFormStatusName
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public FormStatus retrieveFormStatus(String aFormStatusName)
   throws Exception
   {
      return getFormService().retrieveFormStatus(aFormStatusName, this.getUser());
   }//--------------------------------------------
   /**
    * @param aFormTypeID
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public FormType retrieveFormType(int aFormTypeID)
   throws Exception
   {
      return getFormService().retrieveFormType(aFormTypeID, this.getUser());
   }//--------------------------------------------
   /**
    * @param aFormTypeName
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public FormType retrieveFormType(String aFormTypeName)
   throws Exception
   {
      return getFormService().retrieveFormType(aFormTypeName, this.getUser());
   }//--------------------------------------------
   /**
    * @return
    * @throws Exception
    * @throws NoDataFoundException
    * @throws RemoteException
    */
   public Collection retrieveFormTypes() 
   throws Exception
   {
      return getFormService().retrieveFormTypes(this.getUser());
   }//--------------------------------------------
   /**
    * Retrieve form by its unique ID
    * @param aFormID the form ID
    * @return form with ID
    * @throws Exception
    */
   public Form retrieveForm(Integer aFormID)
   throws Exception
   {
      return getFormService().retrieveForm(aFormID);
   }//--------------------------------------------   
   /**
    * Retrieve all non deleted form statuses
    * @param aUser the user
    * @return Collection of FormStatus objects
    * @throws EJBException
    * @throws NoDataFoundException
    */
   public Collection retrieveFormStatuses()
   throws Exception   
   {
      return getFormService().retrieveFormStatuses(this.getUser());
   }//--------------------------------------------
   /**
    * Retrieve clinical form overviews for user of given form type name
    * @param aUser the create user /originator
    * @param aFormTypeName the form type name
    * @return collection of form types
    * @throws Exception
    */
//   public Collection retrieveFormsForUserOfType(SecurityCredential aUser, String aFormTypeName)
//   throws Exception
//   {
//      return getFormService().findForms(FormSearchCriteria.searchForCreateUserAndType(aUser,aFormTypeName));
//   }//--------------------------------------------
   /**
    * 
    * @return instance of the FormBeanRemote
    * @throws Exception
    */
   private FormService getFormService()
   throws Exception
   {
      if(this.service == null)
         this.service = (FormService)ServiceFactory.getInstance().create(FormService.class);
      
      return service;
   }//-------------------------------------------- 
   

   
   private FormService service = null;
}
