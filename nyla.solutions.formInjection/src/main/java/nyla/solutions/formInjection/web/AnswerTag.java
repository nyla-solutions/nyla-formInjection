package nyla.solutions.formInjection.web;
import javax.servlet.jsp.JspException;

import nyla.solutions.formInjection.data.Form;
import nyla.solutions.formInjection.data.FormQuestion;
import nyla.solutions.formInjection.formatter.html.FormQuestionView;
import nyla.solutions.formInjection.formatter.html.HTMLDecorator;
import nyla.solutions.global.util.Debugger;



/**
 * <pre>
 * AnswerTag JSP tag to render a answer
 * </pre> 
 * @author Gregory Green
 * @version 1.0
 */
public class AnswerTag extends FormTag
{

   /**
    * Constructor for AnswerTag initalizes internal 
    * data settings.
    * 
    */
   public AnswerTag()
   {
      super();
   }//--------------------------------------------
   /**
    * 
    * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      try
      {
         Form form = this.getForm();
         FormQuestion formQuestion = form.findQuestionByID(new Integer(this.getId()));
//         FormAnswer answer = formQuestion.getAnswer();
//         FormAnswerView view = new FormAnswerView(answer);
         FormQuestionView view = new FormQuestionView(formQuestion);
         view.setReadOnly(isReadOnlyBool());
         
         HTMLDecorator.print(view, pageContext.getOut(),getContextPath());
         return EVAL_BODY_INCLUDE;
      }
      catch (Exception e)
      {
        throw new JspException(Debugger.stackTrace(e));
      }
      
   }//--------------------------------------------
   
   /**
    * @param readOnly The readOnly to set.
    */
   public void setReadOnly(String readOnly)
   {
      this.readOnly = readOnly;
   }//--------------------------------------------
   public String getReadOnly() {
       return readOnly;
   }
   private String readOnly = null;

   /**
    * @return Returns the readOnly.
    */
   public boolean isReadOnlyBool()
   {
      return Boolean.valueOf(readOnly).booleanValue();
   }//--------------------------------------------
   static final long serialVersionUID = AnswerTag.class.getName().hashCode();
}
