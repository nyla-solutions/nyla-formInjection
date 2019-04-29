package nyla.solutions.formInjection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nyla.solutions.formInjection.data.Answer;
import nyla.solutions.formInjection.data.AnswerProperty;
import nyla.solutions.formInjection.data.Application;
import nyla.solutions.formInjection.data.Form;
import nyla.solutions.formInjection.data.FormType;
import nyla.solutions.formInjection.data.Question;
import nyla.solutions.formInjection.data.Questionaire;
import nyla.solutions.formInjection.data.ResponseType;
import nyla.solutions.global.exception.RequiredException;

/**
 * <pre>
 * ApplicationBuilder builts concrete form objects
 * </pre> 
 * @author Gregory Green
 * @version 1.0
 */
public class ApplicationBuilder extends FormBuilder
{
   /**
    * 
    * Constructor for ApplicationBuilder initalizes internal 
    * data settings.
    * @param aForm
    */
   public void buildForm(int aFormID, String aFormTypeCode, List formProps )
   {
      form = new Application();
      form.setFormId(new Integer(aFormID));
      
      FormType ft = new FormType();
      ft.setFormTypeCode(aFormTypeCode);
      
      form.setFormType(ft);
      
      if(formProps != null && !formProps.isEmpty())
         form.setProperties(formProps);
   }// --------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.FormBuilder#buildFormType(nyla.solutions.formInjection.data.FormType)
    */
   public void buildQuestion(String aQuestionText, 
                             String aFormTypeCode, int aQuestionID, 
                             int questionNumber, 
                             String responseTypeCode,
                             int sectionNumber, 
                             Map attributeMap,
                             Map choices)
   {
      if(this.form == null)
         throw new RequiredException("Built Form");
      
      Question q = new Question();
      q.setQuestionId(new Integer(aQuestionID));
      q.setQuestionText(aQuestionText);
      q.setQuestionNumber(new Integer(questionNumber));
      
      ResponseType responseType  = new ResponseType();
      responseType.setCode(responseTypeCode);
      q.setResponseType(responseType);
      
      q.setSectionNumber(new Integer(sectionNumber));
      q.setChoiceMap(choices);
      q.setAttributeMap(attributeMap);
      
      
      questions.add(q);
      
   }// --------------------------------------------

   /**
    * 
    * 
    * @see nyla.solutions.formInjection.FormBuilder#buildAnswer(java.lang.String, java.util.List, int, java.lang.Integer, java.lang.Integer, java.lang.Integer)
    */
   public void buildAnswer(String aAnswerValue,  List aProperties,
                           int aQuestionId, Integer aResponseTableId,Integer aColumnNumber, Integer aRowNumber)
   {
      Answer answer = new Answer();
      answer.setValue(aAnswerValue);
      
      answer.setQuestionId(new Integer(aQuestionId));
      if(aResponseTableId != null && aColumnNumber != null && aRowNumber != null)
      {
         answer.setResponseTableId(aResponseTableId);
         answer.setRow(aRowNumber);
         answer.setCol(aColumnNumber);
      }
      
      if(aProperties != null)
      {
         AnswerProperty answerProperty = null;
         for (Iterator i = aProperties.iterator(); i.hasNext();)
         {
            answerProperty = (AnswerProperty) i.next();
            answer.addProperty(answerProperty);
         }
      }
      
      answers.add(answer);
   }// --------------------------------------------
   
   /**
    * 
    * @return the created form object
    */
   public Form getForm()
   {
      if(form == null )
         throw new RequiredException("build form");
      
      Questionaire questionaire = new Questionaire(form.getFormId(),questions);
      form.setQuestionaire(questionaire);
      
      if(answers != null && !answers.isEmpty())
      {
         form.addAnswers(answers);
      }
      
      return form;
   }// --------------------------------------------

   private List questions = new ArrayList();
   private List answers = new ArrayList();
   private Form form = null;
}
