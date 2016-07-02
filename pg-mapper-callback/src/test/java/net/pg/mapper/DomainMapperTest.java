package net.pg.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.Assert;

import net.pg.mapper.DomainMapper;

import org.apache.sling.commons.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import test.net.pg.domain.HatsPropertiesInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:net/pg/mapper/test-appcontext.xml")
public class DomainMapperTest {

   @Autowired
   private DomainMapper target;

   @Before
   public void before() {
   }

   @Test
   public void testMap() throws Exception {
      testRbkHostInquiry();
      testGtdHostInquiry();
   }

   private void testGtdHostInquiry() throws Exception {
      JSONObject messageJSON = new JSONObject();
      HatsPropertiesInput hatsPropertiesInput = new HatsPropertiesInput();
      messageJSON.put("holdcode", "1");
      messageJSON.put("tdNo", "123");
      target.map("gtd_host_inquiry", messageJSON.toString(), hatsPropertiesInput);
      
      String tdNo = hatsPropertiesInput.getTdNo();
      Assert.assertEquals(tdNo, "000123");
      String holdcode = hatsPropertiesInput.getHoldCode();
      Assert.assertEquals(holdcode, " 1");
      
      isAllFieldNull(hatsPropertiesInput, "holdcode", "tdNo");
   }
   
   private void testRbkHostInquiry() throws Exception {
      JSONObject messageJSON = new JSONObject();
      HatsPropertiesInput hatsPropertiesInput = new HatsPropertiesInput();
      messageJSON.put("accountNo", "1234567891");
      messageJSON.put("tdNo", "123");
      messageJSON.put("holdcode", "1");
      target.map("rbk_host_inquiry", messageJSON.toString(), hatsPropertiesInput);

      String accountNo = hatsPropertiesInput.getAccountNo();
      Assert.assertEquals(accountNo, "123-456-789-1");
      String tdNO = hatsPropertiesInput.getTdNO();
      Assert.assertEquals(tdNO, "000123");
      String holdcode = hatsPropertiesInput.getHoldCode();
      Assert.assertEquals(holdcode, "01");
      isAllFieldNull(hatsPropertiesInput, "tdNO", "accountNo", "holdcode");
   }

   private void isAllFieldNull(HatsPropertiesInput hatsInput, String... exceptFieldNames) throws Exception {
      Field[] fields = hatsInput.getClass().getDeclaredFields();
      for (Field field : fields) {
         boolean excepted = false;
         for (String exceptFieldName : exceptFieldNames) {
            if (field.getName().equalsIgnoreCase(exceptFieldName)) {
               excepted = true;
               break;
            }
         }
         if (excepted) {
            continue;
         }
         Method getter = null;
         try {
            getter = hatsInput.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
         }
         catch (Exception e) {
         }
         if (getter != null) {
            Assert.assertNull(getter.invoke(hatsInput));
         }
      }
   }
}
