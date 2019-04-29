package nyla.solutions.formInjection.dao.jdo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import nyla.solutions.dao.jdo.JDODAO;
import nyla.solutions.dao.jdo.JDOQueryBuilder;
import nyla.solutions.formInjection.BRE;
import nyla.solutions.formInjection.bre.ExpressionBluePrint;
import nyla.solutions.formInjection.bre.OperationBluePrint;
import nyla.solutions.formInjection.bre.Rule;
import nyla.solutions.formInjection.dao.BreDAO;
import nyla.solutions.formInjection.data.Form;
import nyla.solutions.formInjection.data.FormType;
import nyla.solutions.formInjection.data.Questionaire;
import nyla.solutions.global.data.Data;
import nyla.solutions.global.exception.NoDataFoundException;
import nyla.solutions.global.security.data.SecurityCredential;

/**
 * @author Gregory Green
 * @version 1.0
 *
 * <b>BREDAO</b> business rule data access object  
 * 
 */
public class BreJDODAO extends QuestionJDODAO implements BreDAO
{
   
   /**
    * Constructor for BreDAO initalizes internal 
    * data settings.
    * 
    */
   protected BreJDODAO()
   {
      super();
   }//--------------------------------------------
   /**
    * Constructor for BreDAO initializes internal 
    * data settings.
    * @param aDAO the JDODAO
    */
   protected BreJDODAO(JDODAO aDAO)
   {
      super(aDAO);
   }//--------------------------------------------
   /**
    * Constructor for BreDAO initalizes internal 
    * data settings.
    * @param aUser
    */
   protected BreJDODAO(SecurityCredential aUser)
   {
      super(aUser);
   }//--------------------------------------------
   /**
    * 
    * @return the a instance of the BreDAO
    */
   //public static BreDAO getBreDAOInstance()
   //{
   //   return new BreDAO();
   //}//--------------------------------------------
   /**
    * Retrieve an instance for BRE dao
    * @param aSecurityCredential the user
    * @return instance of BreDAO
    */
   public static BreDAO getBreDAOInstance(SecurityCredential aSecurityCredential)
   {
      return new BreJDODAO(aSecurityCredential);
   }//--------------------------------------------
   /**
    * Retrieve an instance for BRE dao
    * @param aSecurityCredential the user
    * @return instance of BreDAO
    */
   public static BreDAO getBreDAOInstance()
   {
      return new BreJDODAO();
   }//--------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#selectExpressionByPK(java.lang.Integer)
    */
   public ExpressionBluePrint selectExpressionByPK(Integer aExpressionPK)
   throws NoDataFoundException, SQLException
   {
      try
      {
        JDOQueryBuilder query = this.createQueryBuilder(ExpressionBluePrint.class);
       
         query = query.getColumn("primaryKey").equal(aExpressionPK);
         return (ExpressionBluePrint)((Collection)super.select(query)).iterator().next();
      }
      catch(NoDataFoundException e)
      {
         throw new NoDataFoundException("Logical Expression PK="+aExpressionPK+" "+e.getMessage());
      }
   }//--------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#selectRulesByFormTypeCode(String)
    */
   public Collection selectRulesByFormTypeCode(String aFormTypeCode)
   throws NoDataFoundException, SQLException
   {
      JDOQueryBuilder query = this.createQueryBuilder(Rule.class);
      query = query.getColumn("executeOrderNumber").descending();
      
      JDOQueryBuilder deletedFilter = query.getColumn("deletedCode").equal(Data.NO);
      JDOQueryBuilder rulesQuery = query.getColumn("formTypeCode").equal(aFormTypeCode)
                                 .and(deletedFilter);  
      
      return (Collection)this.select(rulesQuery);
   }//-------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#selectOperationBluePrintByPK(java.lang.Integer)
    */
   public OperationBluePrint selectOperationBluePrintByPK(Integer aOperationPK)
   throws NoDataFoundException, SQLException
   {
      JDOQueryBuilder query = this.createQueryBuilder(OperationBluePrint.class);
      JDOQueryBuilder deletedFilter = query.getColumn("deletedCode").equal(Data.NO);
      query = query.getColumn("primaryKey").equal(aOperationPK).and(deletedFilter);      
      
      return (OperationBluePrint)((Collection)this.select(query)).iterator().next();      
   }//--------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#constructBRE(int, nyla.solutions.formInjection.data.Questionaire)
    */
   public BRE constructBRE(String aFormTypeCode, Questionaire aQuestionaire)
   throws NoDataFoundException, SQLException
   {
      //check cache
      
      BRE bre = null;
      
      Collection rules = null;      
      try
      {
         rules = selectRulesByFormTypeCode(aFormTypeCode);
         
      }
      catch(NoDataFoundException e)
      {
         logger.warn("No rules found for aFormTypeCode="+aFormTypeCode);
         
         //create empty rules
         rules = new ArrayList();
      }
      
      if(aQuestionaire == null)
         aQuestionaire = this.constructQuestioniareByFormTypeCode(aFormTypeCode);
      
      //instance of BRE with rules
      bre = BRE.getInstance(rules,aQuestionaire);
                 
      return bre;
   }//--------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#constructBRE(nyla.solutions.formInjection.data.Form)
    */
   public BRE constructBRE(Form aForm)
   throws NoDataFoundException, SQLException
   {
      return constructBRE(aForm.getFormTypeCode(),aForm.getQuestionaire()); 
   }//--------------------------------------------
   /**
    * 
    * @see nyla.solutions.formInjection.dao.BreDAO#selectFormTypes()
    */
   public Collection selectFormTypes()
   throws NoDataFoundException, SQLException
   {
      JDOQueryBuilder formTypeQuery = this.createQueryBuilder(FormType.class);
      
      JDOQueryBuilder maxVersion = createMaxFormTypeQuery(formTypeQuery);
      return (Collection)super.select(maxVersion);
   }//--------------------------------------------

}