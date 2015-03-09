package org.openmrs.module.pharmacy.web.controller;

import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: elly
 * Date: 2/11/15
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class DrugDetailsControllerTest extends BaseModuleContextSensitiveTest{
    @Test
      public void whatIsReturned() throws Exception{
        DrugDetailsController drugDetailsController=new DrugDetailsController();
        assertEquals("10 x 5 must be 50", (long)10, (long)drugDetailsController.multiply(50, 5));
      }

}
