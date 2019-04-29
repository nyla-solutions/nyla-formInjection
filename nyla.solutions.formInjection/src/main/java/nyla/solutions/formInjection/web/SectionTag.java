package nyla.solutions.formInjection.web;

import java.io.BufferedWriter;

import javax.servlet.jsp.JspException;

import nyla.solutions.formInjection.data.Form;
import nyla.solutions.formInjection.data.FormSection;
import nyla.solutions.formInjection.formatter.html.FormSectionView;
import nyla.solutions.formInjection.formatter.html.HTMLDecorator;
import nyla.solutions.global.util.Debugger;


/**
 * <pre>
 * SectionTag JSP tag to render an entire form section
 * </pre> 
 * @author Gregory Green
 * @version 1.0
 */
public class SectionTag extends FormTag
{

   /*
    * Constructor for SectionTag initalizes internal 
    * data settings.
    * 
    */
   public SectionTag()
   {
      super();
   }//--------------------------------------------
   /**
    * 
    * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      try
      {
         Form form = this.getForm();
         
         FormSection section = form.findSectionByNumber(this.getId());
         FormSectionView view = new FormSectionView(section);
         view.setStyleClass(getStyleClass());
         HTMLDecorator.print(view, new BufferedWriter(bodyContent.getEnclosingWriter()),this.getContextPath());
         
         return EVAL_BODY_INCLUDE;
      }
      catch(Exception e)
      {
         throw new JspException(Debugger.stackTrace(e));
      }
   }//--------------------------------------------
   static final long serialVersionUID = SectionTag.class.getName().hashCode();
}
